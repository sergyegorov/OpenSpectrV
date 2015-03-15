/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public class MainFooter extends javax.swing.JPanel {

    /**
     * Creates new form MainFooter
     */
    public MainFooter() {
        initComponents();
    }
    
    OSMain panelMain;
    public void initBy(OSMain panelMain){
        this.panelMain = panelMain;
    }

    ArrayList<AbstractPanel> panelList = new ArrayList<>();
    public void show(AbstractPanel panel) throws Exception{
        if(panelList.size() > 0){
            AbstractPanel currentPanel = panelList.get(panelList.size()-1);
            if(currentPanel.hiding()== false)
                return;
        }
        panel.showing();
        panelList.add(panel);
        panelMain.showPanel(panel);
        checkButton();
    }
    
    void checkButton(){
        if(panelList.size() < 2){
            buttonPrevious.setText("<<");
            buttonPrevious.setEnabled(false);
        } else {
            AbstractPanel currentPanel = panelList.get(panelList.size()-2);
            buttonPrevious.setText(Mls.translate(currentPanel.getPanelName()));
            buttonPrevious.setEnabled(true);
        }
    }
    
    public void goBack() throws Exception{
        if(panelList.size() == 1)
            throw new Exception("You can't remove last panel");
        
        AbstractPanel currentPanel = panelList.get(panelList.size()-1);
        if(currentPanel.hiding()== false)
            return;
        panelList.remove(currentPanel);

        AbstractPanel prevPanel = panelList.get(panelList.size()-1);
        panelMain.showPanel(prevPanel);
        prevPanel.showing();
        
        checkButton();
    }
    
    public void closeAll(){
        for(AbstractPanel panel : panelList)
            try{
                panel.closeOnExit();
            } catch(Exception ex){
                Log.exception(ex);
            }
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
        buttonPrevious = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(592, 25));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        buttonPrevious.setText("<<");
        buttonPrevious.setEnabled(false);
        buttonPrevious.setPreferredSize(new java.awt.Dimension(150, 23));
        buttonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });
        jPanel1.add(buttonPrevious, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("-");
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 14));
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("-");
        jPanel3.add(jLabel2, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreviousActionPerformed
        try{
            goBack();
        }catch(Exception ex){
            Log.exception(ex);
        }
    }//GEN-LAST:event_buttonPreviousActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPrevious;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}