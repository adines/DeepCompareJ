package com.deepcomparej;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author adines
 */
public class EvaluatorFrame extends javax.swing.JPanel {

    
    public EvaluatorFrame() {
        try {
//            this.pathAPI = pathAPI;
//            String so = System.getProperty("os.name");
//
//            if (so.contains("Windows")) {
//                python = "python ";
//            } else {
//                python = "python3 ";
//            }

            modelModel = new DefaultListModel<>();
            measureModel = new DefaultListModel<>();
            rDatasets = new HashMap<String, String>();
            cbmFramework=new DefaultComboBoxModel<>();

            //Frameworks
            String comando = "deepclas4bio-listFrameworks";
            Process p = Runtime.getRuntime().exec(comando);
            p.waitFor();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
            JSONArray frameworks = (JSONArray) jsonObject.get("frameworks");

            //Modelos
            comando ="deepclas4bio-listModels Keras";
            p = Runtime.getRuntime().exec(comando);
            p.waitFor();
            JSONParser parser2 = new JSONParser();
            JSONObject jsonObject2 = (JSONObject) parser2.parse(new FileReader("data.json"));
            JSONArray models = (JSONArray) jsonObject2.get("models");

            //Medidas
            comando ="deepclas4bio-listMeasures";
            p = Runtime.getRuntime().exec(comando);
            p.waitFor();
            parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
            JSONArray measures = (JSONArray) jsonObject.get("measures");

            //ReadDataset
            comando ="deepclas4bio-listReadDatasets";
            p = Runtime.getRuntime().exec(comando);
            p.waitFor();
            parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
            JSONArray readDatasets = (JSONArray) jsonObject.get("readDatasets");

            initComponents();

            taReadDataset.setText("");
            for (Object o : frameworks) {
                cbmFramework.addElement((String) o);
            }
            cbFramework.setModel(cbmFramework);
            cbFramework.setSelectedItem("Keras");

            for (Object o : models) {
                cbModel.addItem((String) o);
            }

            for (Object o : measures) {
                cbMeasure.addItem((String) o);
            }

            for (Object o : readDatasets) {
                String s = (String) ((JSONObject) o).get("name");
                String desc = (String) ((JSONObject) o).get("description");
                rDatasets.put(s, desc);
                cbReadDataset.addItem(s);
            }

            lPathDataset.setText("");
            lClasses.setText("");

            datasetFileChooser = new JFileChooser();
            datasetFileChooser.setCurrentDirectory(new java.io.File("."));
            datasetFileChooser.setDialogTitle("Select the path of the Dataset");
            datasetFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            classesFileChooser = new JFileChooser();
            classesFileChooser.setCurrentDirectory(new java.io.File("."));
            classesFileChooser.setDialogTitle("Select the labels file");
            classesFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
        } catch (IOException ex) {
            Logger.getLogger(EvaluatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvaluatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EvaluatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Object[] getPredictors()
    {
        return this.modelModel.toArray();
    }
    
    public Object [] getMeasures()
    {
        return this.measureModel.toArray();
    }
    
    public String getReadDataset()
    {
        return (String)this.cbReadDataset.getSelectedItem();
    }
    
    public String getPathDataset()
    {
        return this.lPathDataset.getText();
    }
    
    public String getPathClasses()
    {
        return this.lClasses.getText();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bAddModel = new javax.swing.JButton();
        bDeleteModel = new javax.swing.JButton();
        bDeleteAllModel = new javax.swing.JButton();
        cbFramework = new javax.swing.JComboBox<>();
        cbModel = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        lModel = new javax.swing.JList<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        cbMeasure = new javax.swing.JComboBox<>();
        bAddMeasure = new javax.swing.JButton();
        bDeleteMeasure = new javax.swing.JButton();
        bDeleteAllMeasures = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lMeasure = new javax.swing.JList<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        cbReadDataset = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        taReadDataset = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        bPathDataset = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        bClasses = new javax.swing.JButton();
        lPathDataset = new javax.swing.JLabel();
        lClasses = new javax.swing.JLabel();

        jLabel1.setText("Framework");

        jLabel2.setText("Model");

        bAddModel.setText("Add model");
        bAddModel.setMaximumSize(new java.awt.Dimension(150, 29));
        bAddModel.setMinimumSize(new java.awt.Dimension(150, 29));
        bAddModel.setPreferredSize(new java.awt.Dimension(150, 29));
        bAddModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddModelActionPerformed(evt);
            }
        });

        bDeleteModel.setText("Delete model");
        bDeleteModel.setMaximumSize(new java.awt.Dimension(150, 29));
        bDeleteModel.setMinimumSize(new java.awt.Dimension(150, 29));
        bDeleteModel.setPreferredSize(new java.awt.Dimension(150, 29));
        bDeleteModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteModelActionPerformed(evt);
            }
        });

        bDeleteAllModel.setText("Delete all models");
        bDeleteAllModel.setMaximumSize(new java.awt.Dimension(150, 29));
        bDeleteAllModel.setMinimumSize(new java.awt.Dimension(150, 29));
        bDeleteAllModel.setPreferredSize(new java.awt.Dimension(150, 29));
        bDeleteAllModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteAllModelActionPerformed(evt);
            }
        });

        cbFramework.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbFrameworkItemStateChanged(evt);
            }
        });

        lModel.setModel(modelModel);
        lModel.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lModel);

        jLabel3.setText("Measures");

        bAddMeasure.setText("Add measure");
        bAddMeasure.setMaximumSize(new java.awt.Dimension(150, 29));
        bAddMeasure.setMinimumSize(new java.awt.Dimension(150, 29));
        bAddMeasure.setPreferredSize(new java.awt.Dimension(150, 29));
        bAddMeasure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddMeasureActionPerformed(evt);
            }
        });

        bDeleteMeasure.setText("Delete measure");
        bDeleteMeasure.setMaximumSize(new java.awt.Dimension(150, 29));
        bDeleteMeasure.setMinimumSize(new java.awt.Dimension(150, 29));
        bDeleteMeasure.setPreferredSize(new java.awt.Dimension(150, 29));
        bDeleteMeasure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteMeasureActionPerformed(evt);
            }
        });

        bDeleteAllMeasures.setText("Delete all measures");
        bDeleteAllMeasures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteAllMeasuresActionPerformed(evt);
            }
        });

        lMeasure.setModel(measureModel);
        jScrollPane2.setViewportView(lMeasure);

        jLabel4.setText("Select a way to read a dataset");

        cbReadDataset.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbReadDatasetItemStateChanged(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane3.setEnabled(false);
        jScrollPane3.setOpaque(false);

        taReadDataset.setEditable(false);
        taReadDataset.setColumns(20);
        taReadDataset.setForeground(new java.awt.Color(76, 76, 76));
        taReadDataset.setLineWrap(true);
        taReadDataset.setRows(5);
        taReadDataset.setWrapStyleWord(true);
        taReadDataset.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        taReadDataset.setDisabledTextColor(java.awt.SystemColor.textInactiveText);
        taReadDataset.setFocusable(false);
        jScrollPane3.setViewportView(taReadDataset);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(cbReadDataset, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(cbMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bAddMeasure, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(bDeleteMeasure, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bDeleteAllMeasures, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbModel, 0, 123, Short.MAX_VALUE)
                            .addComponent(cbFramework, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bDeleteModel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(bAddModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bDeleteAllModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(bAddModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFramework, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cbModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bDeleteModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bDeleteAllModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(bAddMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bDeleteMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bDeleteAllMeasures))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(cbReadDataset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel6.setText("Select the dataset");

        bPathDataset.setText("Select");
        bPathDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPathDatasetActionPerformed(evt);
            }
        });

        jLabel7.setText("Select the classes");

        bClasses.setText("Select");
        bClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClassesActionPerformed(evt);
            }
        });

        lPathDataset.setText("jLabel8");
        lPathDataset.setMaximumSize(new java.awt.Dimension(200, 17));
        lPathDataset.setMinimumSize(new java.awt.Dimension(200, 17));
        lPathDataset.setPreferredSize(new java.awt.Dimension(200, 17));

        lClasses.setText("jLabel9");
        lClasses.setMaximumSize(new java.awt.Dimension(200, 17));
        lClasses.setMinimumSize(new java.awt.Dimension(200, 17));
        lClasses.setPreferredSize(new java.awt.Dimension(200, 17));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lPathDataset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bPathDataset, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lClasses, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(bClasses, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bPathDataset)
                    .addComponent(bClasses)
                    .addComponent(lPathDataset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lClasses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bAddModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddModelActionPerformed
        String framework = (String) cbFramework.getSelectedItem();
        String model = (String) cbModel.getSelectedItem();

        if (!modelModel.contains(framework + " -> " + model)) {
            modelModel.addElement(framework + " -> " + model);
        }
    }//GEN-LAST:event_bAddModelActionPerformed

    private void bDeleteModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteModelActionPerformed
        if (!lModel.isSelectionEmpty()) {
            modelModel.removeElement(lModel.getSelectedValue());
        }
    }//GEN-LAST:event_bDeleteModelActionPerformed

    private void bDeleteAllModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteAllModelActionPerformed
        modelModel.clear();
    }//GEN-LAST:event_bDeleteAllModelActionPerformed

    private void bAddMeasureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddMeasureActionPerformed
        String measure = (String) cbMeasure.getSelectedItem();

        if (!measureModel.contains(measure)) {
            measureModel.addElement(measure);
        }
    }//GEN-LAST:event_bAddMeasureActionPerformed

    private void bDeleteMeasureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteMeasureActionPerformed
        if (!lMeasure.isSelectionEmpty()) {
            measureModel.removeElement(lMeasure.getSelectedValue());
        }
    }//GEN-LAST:event_bDeleteMeasureActionPerformed

    private void bDeleteAllMeasuresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteAllMeasuresActionPerformed
        measureModel.clear();
    }//GEN-LAST:event_bDeleteAllMeasuresActionPerformed

    private void cbFrameworkItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbFrameworkItemStateChanged
        try {
            String frameworkSelected = (String) cbFramework.getSelectedItem();
            String comando ="deepclas4bio-listModels " + frameworkSelected;
            Process p = Runtime.getRuntime().exec(comando);
            p.waitFor();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("data.json"));
            JSONArray frameworks = (JSONArray) jsonObject.get("models");
            cbModel.removeAllItems();
            for (Object o : frameworks) {
                cbModel.addItem((String) o);
            }
            cbModel.doLayout();
        } catch (InterruptedException ex) {
            Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Evaluate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbFrameworkItemStateChanged

    private void cbReadDatasetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbReadDatasetItemStateChanged
        String rDatasetSelected = (String) cbReadDataset.getSelectedItem();
        taReadDataset.setText(rDatasets.get(rDatasetSelected));
    }//GEN-LAST:event_cbReadDatasetItemStateChanged

    private void bPathDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPathDatasetActionPerformed
        if (datasetFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            lPathDataset.setText(datasetFileChooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_bPathDatasetActionPerformed

    private void bClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClassesActionPerformed
        if (classesFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            lClasses.setText(classesFileChooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_bClassesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EvaluatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EvaluatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EvaluatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EvaluatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            /* Create and display the form */
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EvaluatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(EvaluatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EvaluatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(EvaluatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EvaluatorFrame().setVisible(true);
            }
        });
    }

    private DefaultListModel<String> modelModel;
    private DefaultListModel<String> measureModel;
    Map<String, String> rDatasets;
    JFileChooser datasetFileChooser;
    JFileChooser classesFileChooser;
    DefaultComboBoxModel<String> cbmFramework;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddMeasure;
    private javax.swing.JButton bAddModel;
    private javax.swing.JButton bClasses;
    private javax.swing.JButton bDeleteAllMeasures;
    private javax.swing.JButton bDeleteAllModel;
    private javax.swing.JButton bDeleteMeasure;
    private javax.swing.JButton bDeleteModel;
    private javax.swing.JButton bPathDataset;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbFramework;
    private javax.swing.JComboBox<String> cbMeasure;
    private javax.swing.JComboBox<String> cbModel;
    private javax.swing.JComboBox<String> cbReadDataset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lClasses;
    private javax.swing.JList<String> lMeasure;
    private javax.swing.JList<String> lModel;
    private javax.swing.JLabel lPathDataset;
    private javax.swing.JTextArea taReadDataset;
    // End of variables declaration//GEN-END:variables
}
