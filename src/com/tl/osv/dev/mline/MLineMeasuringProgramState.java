/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.dev.AbstractDevice;
import com.tl.osv.dev.ProbMeasuringSet;
import com.tl.osv.dev.AbstractMeasuringProgramState;
import com.tl.osv.dev.ProbMeasuring;
import com.tl.osv.dev.mline.debug.MLDevice;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author root
 */
public class MLineMeasuringProgramState extends AbstractMeasuringProgramState{
    MLineMeasuringProgram master;
    boolean isGenOn,isGazOn;
    int common;
    int[] expositions;
    int divider;
    public MLineMeasuringProgramState(MLineMeasuringProgram master,
            boolean gen,boolean gaz,int common,int[] exp,int divider){
        this.master = master;
        
        this.isGenOn = gen;
        this.isGazOn = gaz;
        this.common = common;
        this.expositions = exp;
        this.divider = divider;
    }
    
    @Override
    public void run(AbstractDevice dev,ProbMeasuring measuring,MeasuringMLineRaw rawData) throws Exception {
        MLDevice mldev = (MLDevice)dev;
        mldev.measuring(isGenOn, isGazOn, common, expositions, (MeasuringMLine)measuring,this,rawData);
    }

    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        dos.writeBoolean(isGenOn);
        dos.writeBoolean(isGazOn);
        dos.writeInt(common);
        StreamTools.writeIntArray(expositions, dos);
        dos.writeInt(divider);
        StreamTools.versionBlockEnd(dos);
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        StreamTools.versionBlockBegin(dis, 1, 1);
        isGenOn = dis.readBoolean();
        isGazOn = dis.readBoolean();
        common = dis.readInt();
        expositions = StreamTools.readIntArray(dis);
        divider = dis.readInt();
        StreamTools.versionBlockEnd(dis);
    }
}
