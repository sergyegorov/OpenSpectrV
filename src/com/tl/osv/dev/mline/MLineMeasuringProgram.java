/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.Common;
import com.tl.osv.StorableInterface;
import com.tl.osv.dev.AbstractDevice;
import com.tl.osv.dev.AbstractMeasuringProgram;
import com.tl.osv.dev.mline.debug.MLDevice;
import com.tl.osv.panels.Setup;

/**
 *
 * @author root
 */
public class MLineMeasuringProgram extends AbstractMeasuringProgram{
    public MLineMeasuringProgram(){
        
    }
    
    public MLineMeasuringProgram(int[] sensorSizes){
        int[] exp = new int[sensorSizes.length];
        for(int i = 0;i<exp.length;i++)
            exp[i] = 1;
        int divider = Common.panelSetup.data.findAsInteger(Setup.PARAM_DEVICE_DIVIDER);
        addState(new MLineMeasuringProgramState(this,false,false,1,exp,divider));
        addState(new MLineMeasuringProgramState(this,true,false,1,exp,divider));
    }
    
    @Override
    public StorableInterface createInstance() {
        return new MLineMeasuringProgramState(this,false,false,0,null,1);
    }

    @Override
    public void reset(AbstractDevice dev) throws Exception {
        MLDevice mldev = (MLDevice)dev;
        mldev.reset();
    }
}
