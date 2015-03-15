/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline.debug;

import com.tl.osv.OSMain;
import com.tl.osv.dev.mline.MLineMeasuringProgramState;
import com.tl.osv.dev.mline.MeasuringMLine;
import com.tl.osv.dev.mline.MeasuringMLineRaw;
import com.tl.osv.dev.mline.MeasuringMLineRawRecord;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;


/**
 *
 * @author root
 */
public class DeviceDebug extends MLDevice{
    public DeviceDebug(){
        super("DebugDevice3");
    }

    @Override
    public void measuring(boolean gen, boolean gas, int commonTime, 
            int[] expositions, MeasuringMLine measuring,
            MLineMeasuringProgramState state,
            MeasuringMLineRaw rawData) throws Exception {
        Random rnd = new Random();
        Date date = new Date();
        //MeasuringMLineRaw rawData = measuring.getRawData();
        for(int s = 0;s<expositions.length;s++){
            int noise = expositions[s]*10/commonTime;
            short[] ret = new short[2048];
            for(int i = 0;i<ret.length;i++)
                ret[i] = (short)(40+commonTime*5+rnd.nextDouble()*noise+(i%4)*(commonTime*5+50));
            
            if(gen){
                for(int basePixel = 40;basePixel+100 < ret.length;basePixel += 100){
                    for(int line = 0;line<conValues.length;line++){
                        int ampl = conValues[line]*100;
                        int position = basePixel + line*20;
                        ret[position] += ampl;
                        ret[position+1] += ampl/2;
                        ret[position-1] += ampl/2;
                        ret[position+2] += ampl/5;
                        ret[position-2] += ampl/5;
                        ret[position+3] += ampl/10;
                        ret[position-3] += ampl/10;
                        ret[position+4] += ampl/100;
                        ret[position-4] += ampl/100;
                    }
                }
            }
            
            MeasuringMLineRawRecord record = new MeasuringMLineRawRecord(s, date, ret, state);
            rawData.add(record);
        }
    }

    int[] conValues;
    @Override
    public void beforeMeasuring() throws Exception {
        String value = JOptionPane.showInputDialog(OSMain.Main, "Enter concentration 10;1:","10;1");
        String[] values = value.split(";");
        conValues = new int[values.length];
        for(int i = 0;i<conValues.length;i++)
            conValues[i] = Integer.parseInt(values[i].trim());
    }

    @Override
    public void afterMeasuring() throws Exception {
    }

    @Override
    public void reset() throws Exception {
        
    }

    @Override
    public int[] getSensorSizes() {
        return new int[]{2048,2048,2048};
    }
}
