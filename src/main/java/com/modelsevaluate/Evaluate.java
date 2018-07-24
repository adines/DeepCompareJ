package com.modelsevaluate;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
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
        try {
            String so = System.getProperty("os.name");
            String python;
            if (so.contains("Windows")) {
                python = "python ";
            } else {
                python = "python3 ";
            }

            JFileChooser pathAPIFileChooser = new JFileChooser();
            pathAPIFileChooser.setCurrentDirectory(new java.io.File("."));
            pathAPIFileChooser.setDialogTitle("Select the path of the API");
            pathAPIFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            JDialog adAPId = null;
            GridLayout glAPI = new GridLayout(2, 2);
            JPanel apiPanel = new JPanel(glAPI);

            JLabel lPath = new JLabel();
            JButton bPath = new JButton("Select");
            apiPanel.add(new JLabel("Select the path of the API"));
            apiPanel.add(new Label());
            apiPanel.add(lPath);
            apiPanel.add(bPath);

            bPath.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (pathAPIFileChooser.showOpenDialog(apiPanel) == JFileChooser.APPROVE_OPTION) {
                        lPath.setText(pathAPIFileChooser.getSelectedFile().getAbsolutePath());
                    }
                }
            });

            JOptionPane adAPI = new JOptionPane(apiPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION);

            adAPId = adAPI.createDialog("API path");

            adAPId.setVisible(true);
            Object selectedValue = adAPI.getValue();
            if (selectedValue instanceof Integer) {
                int selected = ((Integer) selectedValue).intValue();
                if (selected == 0) {

                    JPanel pMain = new JPanel(new GridLayout(4, 1, 20, 20));

                    String pathAPI = lPath.getText()+"/";
                    adAPId.dispose();
                    String comando = python + pathAPI + "listFrameworks.py";
                    Process p = Runtime.getRuntime().exec(comando);
                    p.waitFor();
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
                    JSONArray frameworks = (JSONArray) jsonObject.get("frameworks");

                    int i = 0;
                    String opcionesFramework[] = new String[frameworks.size()];
                    for (Object o : frameworks) {
                        opcionesFramework[i] = (String) o;
                        i++;
                    }

                    GridLayout gl1 = new GridLayout(1, 3);
                    gl1.setHgap(10);
                    gl1.setVgap(10);

                    JPanel gd1 = new JPanel(gl1);

                    Panel panel1 = new Panel();
                    Choice frameworkChoices = new Choice();

                    for (Object o : frameworks) {
                        frameworkChoices.addItem((String) o);
                    }
                    frameworkChoices.select("Keras");

                    Choice modelChoices = new Choice();

                    panel1.setLayout(new GridLayout(2, 2));
                    panel1.add(new Label("Framework: "));
                    panel1.add(frameworkChoices);
                    panel1.add(new Label("Model: "));
                    panel1.add(modelChoices);

                    JButton bAddModel = new JButton("Add model");
                    JButton bBorrarModel = new JButton("Delete model");
                    JButton bDeleteAll = new JButton("Delete all models");
                    DefaultListModel<String> listModel = new DefaultListModel<String>();
                    JList list = new JList(listModel);
                    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    list.setLayoutOrientation(JList.VERTICAL);
                    list.setVisibleRowCount(5);
                    list.setPrototypeCellValue("Keras -> GoogLeNetKvasir      ");

                    Panel panel = new Panel();
                    panel.setLayout(new GridLayout(3, 1, 5, 5));
                    panel.add(bAddModel);
                    panel.add(bBorrarModel);
                    panel.add(bDeleteAll);

                    Panel panel2 = new Panel();
                    JScrollPane scrollPane2 = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    panel2.add(scrollPane2);

                    bAddModel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            String framework = frameworkChoices.getSelectedItem();
                            String model = modelChoices.getSelectedItem();

                            if (!listModel.contains(framework + " -> " + model)) {
                                listModel.addElement(framework + " -> " + model);
                            }

                        }
                    });

                    bBorrarModel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!list.isSelectionEmpty()) {
                                listModel.removeElement(list.getSelectedValue());
                            }
                        }
                    });

                    bDeleteAll.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            listModel.clear();
                        }
                    });

                    comando = python + pathAPI + "listModels.py -f Keras";
                    p = Runtime.getRuntime().exec(comando);
                    p.waitFor();
                    JSONParser parser2 = new JSONParser();
                    JSONObject jsonObject2 = (JSONObject) parser2.parse(new FileReader("data.json"));
                    JSONArray models = (JSONArray) jsonObject2.get("models");
                    modelChoices.removeAll();
                    for (Object o : models) {
                        modelChoices.add((String) o);
                    }

                    frameworkChoices.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            try {
                                String frameworkSelected = frameworkChoices.getSelectedItem();
                                String comando = python + pathAPI + "listModels.py -f " + frameworkSelected;
                                Process p = Runtime.getRuntime().exec(comando);
                                p.waitFor();
                                JSONParser parser = new JSONParser();
                                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
                                JSONArray frameworks = (JSONArray) jsonObject.get("models");
                                modelChoices.removeAll();
                                for (Object o : frameworks) {
                                    modelChoices.add((String) o);
                                }
                                modelChoices.doLayout();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    gd1.add(panel1);
                    gd1.add(panel);
                    gd1.add(panel2);

                    //Añadir el panel de las medidas
                    comando = python + pathAPI + "listMeasures.py";
                    p = Runtime.getRuntime().exec(comando);
                    p.waitFor();
                    parser = new JSONParser();
                    jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
                    JSONArray measures = (JSONArray) jsonObject.get("measures");

                    GridLayout gl2 = new GridLayout(1, 3);
                    gl1.setHgap(10);
                    gl1.setVgap(10);

                    JPanel jPanelM = new JPanel(gl2);

                    Panel panelM1 = new Panel();
                    panelM1.setLayout(new GridLayout(2, 1));

                    JLabel lMeasures = new JLabel("Measures");
                    Choice measuresChoices = new Choice();

                    for (Object o : measures) {
                        measuresChoices.addItem((String) o);
                    }
                    measuresChoices.select("accuracy");

                    panelM1.add(lMeasures);
                    panelM1.add(measuresChoices);

                    JButton bAddMeasure = new JButton("Add measure");
                    JButton bBorrarMeasure = new JButton("Delete measure");
                    JButton bDeleteAllM = new JButton("Delete all measures");
                    DefaultListModel<String> listMeasures = new DefaultListModel<String>();
                    JList jListMeasures = new JList(listMeasures);
                    jListMeasures.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    jListMeasures.setLayoutOrientation(JList.VERTICAL);
                    jListMeasures.setVisibleRowCount(5);
                    jListMeasures.setPrototypeCellValue("matthewsCorrelation      ");

                    Panel pMeasures = new Panel();
                    pMeasures.setLayout(new GridLayout(3, 1, 5, 5));
                    pMeasures.add(bAddMeasure);
                    pMeasures.add(bBorrarMeasure);
                    pMeasures.add(bDeleteAllM);

                    Panel pMeasures2 = new Panel();
                    JScrollPane scrollPaneM2 = new JScrollPane(jListMeasures, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    pMeasures2.add(scrollPaneM2);

                    bAddMeasure.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            String measure = measuresChoices.getSelectedItem();

                            if (!listMeasures.contains(measure)) {
                                listMeasures.addElement(measure);
                            }

                        }
                    });

                    bBorrarMeasure.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!jListMeasures.isSelectionEmpty()) {
                                listMeasures.removeElement(jListMeasures.getSelectedValue());
                            }
                        }
                    });

                    bDeleteAllM.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            listMeasures.clear();
                        }
                    });

                    jPanelM.add(panelM1);
                    jPanelM.add(pMeasures);
                    jPanelM.add(pMeasures2);

                    //Añadir el selector de readDataset
                    Panel pReadDataset1 = new Panel();
                    pReadDataset1.setLayout(new GridLayout(2, 1));
                    pReadDataset1.add(new JLabel("Selecte a ReadDataset:"));

                    comando = python + pathAPI + "listReadDatasets.py";
                    p = Runtime.getRuntime().exec(comando);
                    p.waitFor();
                    parser = new JSONParser();
                    jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
                    JSONArray readDatasets = (JSONArray) jsonObject.get("readDatasets");

                    Choice readDatasetChoice = new Choice();
                    Map<String, String> rDatasets = new HashMap<String, String>();
                    for (Object o : readDatasets) {
                        String name = (String) ((JSONObject) o).get("name");
                        String desc = (String) ((JSONObject) o).get("description");
                        rDatasets.put(name, desc);
                        readDatasetChoice.addItem(name);
                    }
                    readDatasetChoice.select("ReadDatasetFolders");

                    pReadDataset1.add(readDatasetChoice);

                    Panel pReadDataset2 = new Panel();
                    JLabel lReadDatset = new JLabel(rDatasets.get("ReadDatasetFolders"));
                    pReadDataset2.add(lReadDatset);

                    readDatasetChoice.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            String rDatasetSelected = readDatasetChoice.getSelectedItem();
                            lReadDatset.setText(rDatasets.get(rDatasetSelected));
                            modelChoices.doLayout();

                        }
                    });

                    JPanel jpReadDataset = new JPanel(new GridLayout(2, 1));
                    jpReadDataset.add(pReadDataset1);
                    jpReadDataset.add(pReadDataset2);

                    //Añadir el selector del path de dataset
                    JPanel pSelectors = new JPanel(new GridLayout(2, 4));

                    JFileChooser datasetFileChooser = new JFileChooser();
                    datasetFileChooser.setCurrentDirectory(new java.io.File("."));
                    datasetFileChooser.setDialogTitle("Select the path of the Dataset");
                    datasetFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    JLabel lDatasetFC = new JLabel("Select the dataset");

                    JLabel dsSelected = new JLabel();
                    JButton bDS = new JButton("Select");

                    bDS.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (datasetFileChooser.showOpenDialog(pMain) == JFileChooser.APPROVE_OPTION) {
                                dsSelected.setText(datasetFileChooser.getSelectedFile().getAbsolutePath());
                            }
                        }
                    });

                    //Añadir el selector del path de labels
                    JFileChooser labelstFileChooser = new JFileChooser();
                    labelstFileChooser.setCurrentDirectory(new java.io.File("."));
                    labelstFileChooser.setDialogTitle("Select the labels file");
                    labelstFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                    JLabel lLabelsFC = new JLabel("Select the labels");

                    JLabel labelSelected = new JLabel();
                    JButton bL = new JButton("Select");

                    bL.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (labelstFileChooser.showOpenDialog(pMain) == JFileChooser.APPROVE_OPTION) {
                                labelSelected.setText(labelstFileChooser.getSelectedFile().getAbsolutePath());
                            }
                        }
                    });

                    pSelectors.add(lDatasetFC);
                    pSelectors.add(new JLabel());
                    pSelectors.add(lLabelsFC);
                    pSelectors.add(new JLabel());

                    pSelectors.add(dsSelected);
                    pSelectors.add(bDS);
                    pSelectors.add(labelSelected);
                    pSelectors.add(bL);

                    // Organizar todo
                    pMain.add(gd1);
                    pMain.add(jPanelM);
                    pMain.add(jpReadDataset);
                    pMain.add(pSelectors);
                    pMain.repaint();

                    JOptionPane jmain = new JOptionPane(pMain, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION);

                    JDialog dialogMain = jmain.createDialog("Select input");
                    dialogMain.setVisible(true);
                    Object selectedValue2 = jmain.getValue();
                    if (selectedValue2 instanceof Integer) {
                        int selected2 = ((Integer) selectedValue2).intValue();
                        if (selected == 0) {
                            //Realizar la evaluación y mostrar la tablita
                            JSONObject obj = new JSONObject();
                            JSONArray arr = new JSONArray();
                            JSONObject item;
                            Object selectedModels[] = listModel.toArray();
                            for (Object o : selectedModels) {
                                item = new JSONObject();
                                String arguments[] = ((String) o).split(" -> ");
                                item.put("framework", arguments[0]);
                                item.put("model", arguments[1]);
                                arr.add(item);
                            }

                            JSONArray arrMeasures = new JSONArray();
                            Object selectedMeasures[] = listMeasures.toArray();
                            for (Object o : selectedMeasures) {
                                arrMeasures.add((String) o);
                            }

                            obj.put("predictors", arr);
                            obj.put("measures", arrMeasures);
                            obj.put("readDataset", readDatasetChoice.getSelectedItem());
                            obj.put("pathDataset", dsSelected.getText());
                            obj.put("pathLabels", labelSelected.getText());

                            FileWriter file = new FileWriter("config.json");
                            file.write(obj.toJSONString());
                            file.flush();
                            dialogMain.dispose();

                            comando = python + pathAPI + "evaluate.py -c config.json";
                            p = Runtime.getRuntime().exec(comando);
                            p.waitFor();
                            parser = new JSONParser();
                            jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));

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

                            JOptionPane jop = new JOptionPane(result, JOptionPane.PLAIN_MESSAGE, JOptionPane.CANCEL_OPTION);
                            JDialog dMain = jop.createDialog("Result");
                            dMain.setVisible(true);
                            dMain.dispose();

                        }
                    }

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
        }
    }

}
