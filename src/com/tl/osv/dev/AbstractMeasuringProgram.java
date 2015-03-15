/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.StorableInterface;
import com.tl.osv.StorableManufactureInterface;
import com.tl.osv.dev.mline.MeasuringMLineRaw;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public abstract class AbstractMeasuringProgram implements StorableInterface,StorableManufactureInterface{
    private int currentPosition = -1;
    private ArrayList<AbstractMeasuringProgramState> steps = new ArrayList<>();
    
    public abstract void reset(AbstractDevice dev) throws Exception;
        
    final public void start(AbstractDevice dev) throws Exception{
        currentPosition = -1;
        reset(dev);
    }
    
    final public void addState(AbstractMeasuringProgramState state){
        steps.add(state);
    }
    
    final public int stateCount(){
        return steps.size();
    }
    
    final public AbstractMeasuringProgramState next(AbstractDevice dev,ProbMeasuring measuring,MeasuringMLineRaw rawData) throws Exception{
        currentPosition ++;
        if(currentPosition == steps.size()){
            currentPosition = steps.size();
            return null;
        }
        AbstractMeasuringProgramState curStep = steps.get(currentPosition);
        curStep.run(dev,measuring,rawData);
        return curStep;
    }
    
    final public AbstractMeasuringProgramState getCurrentState(){
        return steps.get(currentPosition);
    }

    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        StreamTools.save(dos, steps);
        StreamTools.versionBlockEnd(dos);
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        StreamTools.versionBlockBegin(dis, 1, 1);
        StreamTools.load(dis, steps, this);
        StreamTools.versionBlockEnd(dis);
    }
}
