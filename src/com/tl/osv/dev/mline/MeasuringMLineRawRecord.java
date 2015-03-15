/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.StorableInterface;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Date;

/**
 *
 * @author root
 */
public class MeasuringMLineRawRecord  implements StorableInterface{
    public Date measuringTime;
    public int sn;
    public short[] data;
    public MLineMeasuringProgramState state;
    public MeasuringMLineRawRecord(int sn,Date measuringTime,short[] data,
            MLineMeasuringProgramState state){
        this.sn = sn;
        this.measuringTime = measuringTime;
        this.data = data;
        this.state = state;
    }
    
    public short[] getData(){
        return data;
    }

    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        dos.writeInt(sn);
        dos.writeLong(measuringTime.getTime());
        StreamTools.writeShortArray(data, dos);
        StreamTools.versionBlockEnd(dos);
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        StreamTools.versionBlockBegin(dis, 1,1);
        sn = dis.readInt();
        measuringTime = new Date(dis.readLong());
        StreamTools.readShortArray(dis);
        StreamTools.versionBlockEnd(dis);
    }
}
