/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.analit.method;

import java.io.File;

/**
 *
 * @author root
 */
public class MLSimpleCalibrMethod extends AbstractMethod{
    public MLSimpleCalibrMethod(File baseFolder) throws Exception{
        super(baseFolder);
    }
    
    @Override
    public AbstractMethodCalibr getCalibr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractMethodMeasuring getMeasuring() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
