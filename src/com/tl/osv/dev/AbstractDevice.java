/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.analit.LambdaFk;
import java.io.File;

/**
 *
 * @author root
 */
public abstract class AbstractDevice {
    String name;
    protected AbstractDevice(String name){
        this.name = name;
    }
    
    abstract public AbstractMeasuringProgram getDefaultCondition();
    
    abstract public void beforeMeasuring() throws Exception;
    abstract public ProbMeasuring measuring(AbstractMeasuringProgram condition,
            File directory) throws Exception;
    abstract public void afterMeasuring() throws Exception;
    abstract public LambdaFk getDefaultLy();
    
    abstract public AbstractMethod createCalibrMethod();
    abstract public AbstractMethod createSortMethod();
}
