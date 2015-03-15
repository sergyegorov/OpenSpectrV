/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data.params;

import com.tl.osv.kernel.data.DataStorageAbstract;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.JComponent;


/**
 *
 * @author root
 */
public interface AbstractParameter {
    void setParent(DataStorageAbstract dataStorage);
    DataStorageAbstract getParent();
    JComponent getGui();
    String getName();
    String getDescription();
    void save(DataOutputStream stream) throws Exception;
    void load(DataInputStream stream) throws Exception;
}
