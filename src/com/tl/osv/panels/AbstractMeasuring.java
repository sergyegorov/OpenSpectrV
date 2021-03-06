/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.panels;

import com.tl.osv.AbstractPanel;
import com.tl.osv.Common;
import com.tl.osv.Log;
import com.tl.osv.Mls;
import com.tl.osv.OSMain;
import com.tl.osv.StorableInterface;
import com.tl.osv.dev.AbstractDevice;
import com.tl.osv.dev.AbstractMeasuringPreview;
import com.tl.osv.dev.AbstractMeasuringProgram;
import com.tl.osv.dev.MeasuringResult;
import com.tl.osv.dev.MeasuringResultRecord;
import com.tl.osv.dev.ProbMeasuring;
import com.tl.osv.dev.ProbMeasuringSet;
import com.tl.osv.dev.mline.MeasuringMLineSet;
import com.tl.osv.panels.comp.ResultTableListener;
import com.tl.osv.util.StreamTools;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class AbstractMeasuring  extends AbstractPanel 
        implements ResultTableListener {
    String directory;
    /**
     * Creates new form MeasuringUnknown
     * @param name
     * @param directory
     */
    public AbstractMeasuring(String name,String directory) throws Exception {
        super(name);
        this.directory = directory;
        if(this.directory.endsWith(File.separator) == false)
            this.directory += File.separator;
        initComponents();
        File folder = getFile("");
        if(folder.exists() == false && folder.mkdir() == false)
            throw new IOException("Can't create folder "+folder.getAbsolutePath());
        resultTable.setListener(this);
        JPanel pan = getAditionalPanel();
        if(pan != null){
            jSplitPane3.setRightComponent(pan);
            jSplitPane3.setDividerLocation(0.8);
        } else {
            pan = new JPanel();
            pan.setPreferredSize(new Dimension(1,1));
            jSplitPane3.setRightComponent(pan);
            jSplitPane3.setDividerLocation(0.999);
        }
        pan = getAdditionalTools();
        if(pan == null)
            pan = new JPanel();
        panelAdditionalTool.add(pan);
    }

    /**
     * This method is called from within the constructor to initialize the form.

* WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonCreateProb = new javax.swing.JButton();
        buttonMeasuring = new javax.swing.JButton();
        panelAdditionalTool = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        calcDetailsView1 = new com.tl.osv.panels.comp.CalcDetailsView();
        jPanel4 = new javax.swing.JPanel();
        spectrView1 = new com.tl.osv.panels.comp.SpectrView();
        jPanel5 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        resultTable = new com.tl.osv.panels.comp.ResultTable();

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 30));

        buttonCreateProb.setText("#ProbCreate");
        buttonCreateProb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateProbActionPerformed(evt);
            }
        });

        buttonMeasuring.setText("#Measuring");
        buttonMeasuring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMeasuringActionPerformed(evt);
            }
        });

        panelAdditionalTool.setMinimumSize(new java.awt.Dimension(0, 0));
        panelAdditionalTool.setPreferredSize(new java.awt.Dimension(0, 35));
        panelAdditionalTool.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(buttonCreateProb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonMeasuring)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAdditionalTool, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCreateProb)
                    .addComponent(buttonMeasuring))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(panelAdditionalTool, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);

        jSplitPane2.setResizeWeight(0.35);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(calcDetailsView1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(calcDetailsView1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spectrView1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spectrView1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jSplitPane1.setBottomComponent(jPanel2);

        jSplitPane3.setResizeWeight(0.99);
        jSplitPane3.setLeftComponent(resultTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private File getFile(String name) throws Exception{
        File fl;
        if(name != null)
            fl = new File(directory+name);
        else
            fl = new File(directory);
        return fl;
    }
    
    private void buttonCreateProbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateProbActionPerformed
        try{
            String txt = JOptionPane.showInputDialog(OSMain.Main, 
                    Mls.translate("#AbstractMeasuringEnterProbName"), 
                    Mls.translate("#AbstractMeasuringNewProb"), 
                    JOptionPane.QUESTION_MESSAGE);
            if(txt == null)
                return;
            if(txt.endsWith(ProbMeasuringSet.PROB_EXTENTION) == false)
                txt += ProbMeasuringSet.PROB_EXTENTION;
            File fl = getFile(txt);
            if(fl.exists()){
                JOptionPane.showMessageDialog(OSMain.Main, 
                        Mls.translate("#AbstractMeasuringFile%sExistsRemeasuring?",fl.getAbsolutePath()), 
                        Mls.translate("#AbstractMeasuringProbExists"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            MeasuringMLineSet set = new MeasuringMLineSet(fl);
            resultTable.updateView();
        }catch(Exception ex){
            Log.exception(ex);
        }
    }//GEN-LAST:event_buttonCreateProbActionPerformed

    private void buttonMeasuringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMeasuringActionPerformed
        try{
            ProbMeasuringSet prob = resultTable.getSelectedMeasuringProb();
            if(prob == null){
                JOptionPane.showMessageDialog(OSMain.Main, 
                        Mls.translate("#AbstractMeasuringSelectProbNameFirst."), 
                        Mls.translate("#AbstractMeasuringProbExists"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            AbstractDevice device = Common.getDevice();
            AbstractMeasuringProgram program;
            File cfl = getFile("condition.bin");
            if(cfl.exists()){
                try(FileInputStream fis = new java.io.FileInputStream(cfl);
                        DataInputStream dis = new java.io.DataInputStream(fis);){
                    program = device.getDefaultCondition();
                    program.load(dis);
                }
            } else {
                program = device.getDefaultCondition();
                cfl.createNewFile();
                try(FileOutputStream fos = new java.io.FileOutputStream(cfl);
                        DataOutputStream dos = new DataOutputStream(fos)){
                    program.save(dos);
                }
            }
            File fl = getFile(prob.getName()+File.separator+
                    (new Date()).getTime()+ProbMeasuring.SP_EXTENTION+File.separator);
            ProbMeasuring result = device.measuring(program, fl);
            File methodFile = getFile("method.bin");
            if(methodFile.exists()){
                StorableInterface method = StreamTools.loadStorable(methodFile);
            }
            
            resultTable.updateView();
        }catch(Exception ex){
            Log.exception(ex);
        }
    }//GEN-LAST:event_buttonMeasuringActionPerformed

    public JPanel getAditionalPanel(){
        return null;
    }
    
    public JPanel getAdditionalTools(){
        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCreateProb;
    private javax.swing.JButton buttonMeasuring;
    private com.tl.osv.panels.comp.CalcDetailsView calcDetailsView1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JPanel panelAdditionalTool;
    private com.tl.osv.panels.comp.ResultTable resultTable;
    private com.tl.osv.panels.comp.SpectrView spectrView1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void closeOnExit() throws Exception {
    }

    @Override
    protected void init() throws Exception {
        resultTable.setupView(getFile(null));
    }

    @Override
    public void showed() {
    }

    @Override
    public boolean hiding() throws Exception {
        return true;
    }

    @Override
    public void selectProb(ProbMeasuringSet probSet, ProbMeasuring probMeasuring, int element, String[] elementNames) {
        try{
            if(probMeasuring != null){
                AbstractMeasuringPreview spView = probMeasuring.getPreview();
                spectrView1.showComp(spView,probMeasuring.getLambdaFk());
                if(elementNames != null && element >= 0 && element < elementNames.length){
                    String elementName = elementNames[element];
                    MeasuringResult mr = probMeasuring.getResult();
                    MeasuringResultRecord mrr = mr.getByName(elementName);
                    calcDetailsView1.showComp(mrr);
                } else {
                    calcDetailsView1.showComp(null);
                }
            } else {
                spectrView1.showComp(null,null);
                calcDetailsView1.showComp(null);
            }
            OSMain.Main.pack();
            spectrView1.repaint();
            calcDetailsView1.repaint();
        }catch(Exception ex){
            Log.exception(ex);
        }
    }
}
