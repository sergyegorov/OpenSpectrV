/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.panels;

import com.tl.osv.AbstractPanel;
import com.tl.osv.Common;
import com.tl.osv.dev.DeviceDataBase;
import com.tl.osv.kernel.data.DataStorageFile;
import com.tl.osv.kernel.data.DataStorageFolder;
import com.tl.osv.kernel.data.params.SelectParameter;
import com.tl.osv.kernel.data.params.StringParameter;
import java.io.File;

/**
 *
 * @author root
 */
public class Setup extends AbstractPanel {
    public DataStorageFile data;
    
    public static final String PARAM_INSTALL_DESCRIPTION    = "#InstalationDescription";
    public static final String PARAM_DEVICE_GROUP           = "#DeviceParameters";
    public static final String PARAM_DEVICE_TYPE            = "#DeviceType";
    public static final String PARAM_DEVICE_DIVIDER         = "#DeviceDivider";
    
    /**
     * Creates new form Setup
     */
    public Setup() throws Exception {
        super("#Setup");
        initComponents();
        data = new DataStorageFile("#SetupParameters", "#SetupParameterDescription", 
                new File(Common.getStartDirectory()+File.separator+"config.bin"));
        if(data.getVersion() == -1){
            data.setVersion(0);
            StringParameter strp = new StringParameter(PARAM_INSTALL_DESCRIPTION);
            data.add(strp);
            
            DataStorageFolder devFolder = new DataStorageFolder(PARAM_DEVICE_GROUP);
            data.add(devFolder);
            SelectParameter selp = new SelectParameter(PARAM_DEVICE_TYPE,0,DeviceDataBase.getDeviceList());
            devFolder.add(selp);
            selp = new SelectParameter(PARAM_DEVICE_DIVIDER,1,new String[]{"1","2","4","8"});
            
            devFolder.add(selp);
            
            data.commit();
        }
        data.putValues(panelData);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelData = new javax.swing.JPanel();

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panelData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelData;
    // End of variables declaration//GEN-END:variables

    @Override
    public void closeOnExit() throws Exception {
    }

    @Override
    protected void init() throws Exception {
    }

    @Override
    public void showed() {
    }

    @Override
    public boolean hiding() throws Exception {
        return true;
    }
}
