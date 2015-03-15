/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.dev.ProbMeasuring;
import com.tl.osv.dev.ProbMeasuringSet;
import java.io.File;

/**
 *
 * @author root
 */
public class MeasuringMLineSet extends ProbMeasuringSet{
    public MeasuringMLineSet(File baseFolder) throws Exception{
        super(baseFolder,MeasuringMLine.class);
    }
    
    @Override
    protected ProbMeasuring createMeasuringInstance(File folder) throws Exception {
        return new MeasuringMLine(folder);
    }
}
