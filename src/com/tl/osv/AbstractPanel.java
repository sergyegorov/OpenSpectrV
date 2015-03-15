/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv;

import javax.swing.JPanel;

/**
 *
 * @author root
 */
public abstract class AbstractPanel extends JPanel{
    private String name;
    protected AbstractPanel(String name){
        this.name = name;
    }
    
    public String getPanelName(){
        return this.name;
    }
    
    abstract public void closeOnExit() throws Exception;
    abstract protected void init() throws Exception;
    private boolean inited;
    final public void showing() throws Exception{
        if(inited == false){
            init();
            inited = true;
        }
        showed();
    }
    abstract public void showed();
    abstract public boolean hiding() throws Exception;    
}
