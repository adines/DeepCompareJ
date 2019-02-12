package com.deepcomparej;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author adines
 */
public class Evaluate {

    public static void main(String[] args) {
        JDialog dialogMain = null;
        JDialog dMain = null;
        try {
            EvaluatorFrame ef = new EvaluatorFrame();
            JOptionPane jop = new JOptionPane(ef, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            dialogMain = jop.createDialog("Select input");
            dialogMain.setVisible(true);
            Object selectedValue2 = jop.getValue();
            if (selectedValue2 instanceof Integer) {
                int selected2 = ((Integer) selectedValue2).intValue();
                if (selected2 == 0) {
                    //Realizar la evaluación y mostrar la tablita
                    JSONObject obj = new JSONObject();
                    JSONArray arr = new JSONArray();
                    JSONObject item;

                    Object selectedModels[] = ef.getPredictors();
                    for (Object o : selectedModels) {
                        item = new JSONObject();
                        String arguments[] = ((String) o).split(" -> ");
                        item.put("framework", arguments[0]);
                        item.put("model", arguments[1]);
                        arr.add(item);
                    }

                    JSONArray arrMeasures = new JSONArray();
                    Object selectedMeasures[] = ef.getMeasures();

                    for (Object o : selectedMeasures) {
                        arrMeasures.add((String) o);
                    }

                    obj.put("predictors", arr);
                    obj.put("measures", arrMeasures);
                    obj.put("readDataset", ef.getReadDataset());
                    obj.put("pathDataset", ef.getPathDataset());
                    obj.put("pathLabels", ef.getPathClasses());

                    FileWriter file = new FileWriter("config.json");
                    file.write(obj.toJSONString());
                    file.flush();
                    dialogMain.dispose();

                    String comando = "deepclas4bio-evaluate config.json";
                    Process p = Runtime.getRuntime().exec(comando);
                    p.waitFor();
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));

                    JTable table;
                    DefaultTableModel tableModel = new DefaultTableModel(selectedModels, selected2);
                    Set set = jsonObject.keySet();
                    Set set3 = new HashSet();
                    set3.add("Model");
                    for (Object o : set) {
                        String s = (String) o;

                        JSONObject jitem = (JSONObject) jsonObject.get(s);

                        Set set2 = jitem.keySet();
                        Object row[] = new Object[set2.size() + 1];
                        row[0] = s;
                        int cont = 1;

                        for (Object o2 : set2) {
                            String s2 = (String) o2;
                            System.out.println(s2);
                            set3.add(s2);
                            String jitem2 = String.valueOf((Double) jitem.get(s2));
                            row[cont] = jitem2;
                            cont++;

                        }
                        tableModel.setColumnIdentifiers(set3.toArray());
                        tableModel.addRow(row);
                    }
                    String filas[][] = new String[tableModel.getRowCount()][tableModel.getColumnCount()];
                    for (int j = 0; j < tableModel.getRowCount(); j++) {
                        for (int k = 0; k < tableModel.getColumnCount(); k++) {
                            filas[j][k] = (String) tableModel.getValueAt(j, k);
                        }
                    }
                    table = new JTable(filas, set3.toArray());

                    JScrollPane result = new JScrollPane(table);

                    JOptionPane jresult = new JOptionPane(result, JOptionPane.PLAIN_MESSAGE, JOptionPane.CANCEL_OPTION);
                    dMain = jresult.createDialog("Result");
                    dMain.setVisible(true);
                    dMain.dispose();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dMain != null) {
                dMain.dispose();
            }
            if (dialogMain != null) {
                dialogMain.dispose();
            }
        }
    }

}
