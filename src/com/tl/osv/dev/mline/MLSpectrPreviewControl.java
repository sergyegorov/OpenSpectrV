/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.Log;
import com.tl.osv.analit.LambdaFk;
import com.tl.osv.analit.MinMax2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class MLSpectrPreviewControl extends javax.swing.JPanel {
    SpectrView view;
    /**
     * Creates new form MLSpectrPreviewControl
     */
    public MLSpectrPreviewControl() {
        initComponents();
        view = new SpectrView();
        add(view,BorderLayout.CENTER);
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        view.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                try{
                    
                } catch(Exception ex){
                    Log.exception(ex);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                try{
                    double x = view.getVirtualByScreenX(e.getX());
                    double y = view.getVirtualByScreenX(e.getY());
                    String txt = String.format("X:%1$4.1f Y:%2$4.1f sn:%3$d", x,y,1);
                    labelCursorInfo.setText(txt);
                } catch(Exception ex){
                    Log.exception(ex);
                }
            }
        });
        view.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                try{
                    double x0 = e.getX();
                    double wPanel = getWidth();
                    double left = x0 / wPanel;
                    double right = (wPanel-x0)/wPanel;
                    
                    double midle = viewMinMax.minX + viewMinMax.getWidth() * x0 / wPanel;
                    int n = e.getWheelRotation();
                    
                    double step = viewMinMax.getWidth()*n/6;
                    double new_w = viewMinMax.getWidth()+step;
                    viewMinMax.minX = midle - new_w * left;
                    viewMinMax.maxX = midle + new_w * right;
                    if(viewMinMax.minX < minMax.minX)
                        viewMinMax.minX = minMax.minX;
                    if(viewMinMax.maxX > minMax.maxX)
                        viewMinMax.maxX = minMax.maxX;
                    view.repaint();
                    setupScrollBars();
                }catch(Exception ex){
                    Log.exception(ex);
                }
            }
        });
    }

    MeasuringMLinePreview preview;
    LambdaFk lambdaFk;
    MinMax2D minMax;
    MinMax2D viewMinMax;
    public void show(MeasuringMLinePreview preview,LambdaFk lambdaFk){
        this.preview = preview;
        this.lambdaFk = lambdaFk;
        minMax = lambdaFk.getLyDimenstion(null);
        minMax.addValueY(preview.data);
        if(viewMinMax == null)
            viewMinMax = new MinMax2D(minMax);
        else
            viewMinMax.intersect(minMax);
        view.repaint();
        setupScrollBars();
    }
    
    void setupScrollBars(){
        int value = (int)(viewMinMax.minX);
        int ext = (int)(viewMinMax.getWidth());
        int min = (int)(minMax.minX);
        int max = (int)(minMax.maxX);
        hScrollBar.setValues(value, ext, min, max);
    }
    
    class SpectrView extends JPanel{
        public double getVirtualByScreenX(double x){
            return x/kx+viewMinMax.minX;
        }
        
        public double getVirtualByScreenY(double y){
            return (getHeight() - y)/ky+viewMinMax.minY;
        }
        
        double kx = 1,ky = 1;
        @Override
        public void paint(Graphics g){
            try{
                int width = getWidth();
                int height = getHeight();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);
                kx = (double)(width/viewMinMax.getWidth());
                ky = (double)((height-10)/viewMinMax.getHeight());
                for(int sn = 0;sn<preview.data.length;sn++){
                    short[] data = preview.data[sn];
                    int prevx = (int)((lambdaFk.getLambdaForSensor(sn, 0)-viewMinMax.minX)*kx);
                    int prevy = (int)(height - (data[0]-viewMinMax.minY)*ky);
                    if(sn%2 == 0)
                        g.setColor(Color.pink);
                    else
                        g.setColor(Color.red);
                    for(int pixel = 0;pixel < data.length;pixel++){
                        int x = (int)((lambdaFk.getLambdaForSensor(sn, pixel)-viewMinMax.minX)*kx);
                        int y = (int)(height - (data[pixel]-viewMinMax.minY)*ky);
                        g.drawLine(x, y, prevx, prevy);
                        prevx = x;
                        prevy = y;
                    }
                }
            }catch(Exception ex){
                Log.exception(ex);
            }
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labelCursorInfo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        buttonAll = new javax.swing.JButton();
        vScrollBar = new javax.swing.JScrollBar();
        hScrollBar = new javax.swing.JScrollBar();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(256, 17));
        jPanel2.setLayout(new java.awt.BorderLayout());

        labelCursorInfo.setText("-");
        jPanel2.add(labelCursorInfo, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        buttonAll.setText("<html>All</html>");
        buttonAll.setMargin(new java.awt.Insets(2, 4, 2, 4));
        buttonAll.setMaximumSize(new java.awt.Dimension(100, 23));
        buttonAll.setPreferredSize(new java.awt.Dimension(45, 20));
        buttonAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAllActionPerformed(evt);
            }
        });
        jPanel5.add(buttonAll, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5, java.awt.BorderLayout.WEST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
        add(vScrollBar, java.awt.BorderLayout.LINE_END);

        hScrollBar.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        hScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                hScrollBarAdjustmentValueChanged(evt);
            }
        });
        add(hScrollBar, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void hScrollBarAdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_hScrollBarAdjustmentValueChanged
        try{
            viewMinMax.minX = hScrollBar.getValue();
            viewMinMax.maxX = viewMinMax.minX+hScrollBar.getVisibleAmount();
            if(viewMinMax.maxX > minMax.maxX)
                viewMinMax.maxX = minMax.maxX;
            if(viewMinMax.minX < minMax.minX)
                viewMinMax.minX = minMax.minX;
            view.repaint();
        }catch(Exception ex){
            Log.exception(ex);
        }
    }//GEN-LAST:event_hScrollBarAdjustmentValueChanged

    private void buttonAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAllActionPerformed
        try{
            viewMinMax = new MinMax2D(minMax);
            view.repaint();
        }catch(Exception ex){
            Log.exception(ex);
        }
    }//GEN-LAST:event_buttonAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAll;
    private javax.swing.JScrollBar hScrollBar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel labelCursorInfo;
    private javax.swing.JScrollBar vScrollBar;
    // End of variables declaration//GEN-END:variables
}
