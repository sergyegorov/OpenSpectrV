/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.panels.comp;

import com.tl.osv.analit.LambdaFk;
import com.tl.osv.dev.AbstractMeasuringPreview;
import java.awt.BorderLayout;
import javax.swing.JComponent;

/**
 *
 * @author root
 */
public class SpectrView extends javax.swing.JPanel {

    /**
     * Creates new form SpectrView
     */
    public SpectrView() {
        initComponents();
    }

    public void showComp(AbstractMeasuringPreview view,LambdaFk fk){
        removeAll();
        if(view != null){
            JComponent comp = view.getPreviewPanel();
            if(comp != null)
                add(comp,BorderLayout.CENTER);
        }
        /*OSMain.Main.pack();
        revalidate();
        repaint();*/
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
