/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data.params;

import com.tl.osv.kernel.data.DataStorageAbstract;
import javax.swing.JComponent;

/**
 *
 * @author root
 */
public abstract class StringBaseParameter implements AbstractParameter{
    protected String name,description;
    public StringBaseParameter(String name,String description){
        this.name = name;
        this.description = description;
    }

    DataStorageAbstract dataStorage;
    @Override
    public void setParent(DataStorageAbstract dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public JComponent getGui() {
        StringParameterGUI gui = new StringParameterGUI();
        gui.setMaster(this);
        return gui;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public DataStorageAbstract getParent() {
        return dataStorage;
    }
    
    abstract public String getAsString();
    abstract public void setAsString(String value);
    abstract public String validate(String candidate);
}
