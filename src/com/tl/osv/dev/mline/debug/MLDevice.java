/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline.debug;

import com.tl.osv.Common;
import com.tl.osv.analit.LambdaFk;
import com.tl.osv.dev.AbstractDevice;
import com.tl.osv.dev.AbstractMeasuringProgram;
import com.tl.osv.dev.AbstractMethod;
import com.tl.osv.dev.ProbMeasuring;
import com.tl.osv.dev.mline.MLineMeasuringProgram;
import com.tl.osv.dev.mline.MLineMeasuringProgramState;
import com.tl.osv.dev.mline.MeasuringMLine;
import com.tl.osv.dev.mline.MeasuringMLinePreview;
import com.tl.osv.dev.mline.MeasuringMLineRaw;
import java.io.File;

/**
 *
 * @author root
 */
public abstract class MLDevice extends AbstractDevice {
    public MLDevice(String name){
        super(name);
    }
    
    abstract public int[] getSensorSizes();
    
    @Override
    public AbstractMeasuringProgram getDefaultCondition() {
        return new MLineMeasuringProgram(getSensorSizes());
    }

    @Override
    public ProbMeasuring measuring(AbstractMeasuringProgram condition,
            File directory) throws Exception{
        MeasuringMLine measuring = new MeasuringMLine(directory);
        measuring.setLambdaFk(Common.getCurrentLambdaFk());
        MeasuringMLineRaw rawData = new MeasuringMLineRaw(measuring);
        beforeMeasuring();
        condition.start(this);
        while(condition.next(this, measuring, rawData) != null){
        }
        afterMeasuring();
        measuring.setMeasuringProgram((MLineMeasuringProgram)condition);
        measuring.setRawData(rawData);
        measuring.setPreview(new MeasuringMLinePreview(measuring,rawData));
        return measuring;
    }

    @Override
    public AbstractMethod createCalibrMethod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractMethod createSortMethod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public abstract void measuring(boolean gen,boolean gas,int commonTime,
            int[] expositions,MeasuringMLine measuring,
            MLineMeasuringProgramState state,
            MeasuringMLineRaw rawData) throws Exception;
    public abstract void reset() throws Exception;
    
    @Override
    public LambdaFk getDefaultLy(){
        int[] sizes = getSensorSizes();
        return new LambdaFk(sizes);
    }
}
