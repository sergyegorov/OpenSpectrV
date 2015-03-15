/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import javax.swing.JComponent;

/**
 *
 * @author root
 */
public abstract class AbstractMeasuringPreview{
    abstract public JComponent getPreviewPanel();
    
    public static AbstractMeasuringPreview getNoView(){
        return null;
    }
}
