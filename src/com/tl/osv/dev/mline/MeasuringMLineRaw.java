/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.StorableInterface;
import com.tl.osv.StorableManufactureInterface;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class MeasuringMLineRaw implements StorableInterface,StorableManufactureInterface{
    MeasuringMLine master;
    public MeasuringMLineRaw(MeasuringMLine master){
        this.master = master;
    }
    
    public ArrayList<MeasuringMLineRawRecord> records = new ArrayList<>();
    public MeasuringMLineRawRecord getRecord(int record){
        return records.get(record);
    }
    
    public int size(){
        return records.size();
    }
    
    public void clear(){
        records.clear();
    }
    
    public void add(MeasuringMLineRawRecord record){
        records.add(record);
    }

    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        StreamTools.save(dos,records);
        StreamTools.versionBlockEnd(dos);
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        StreamTools.versionBlockBegin(dis, 1, 1);
        StreamTools.load(dis,records,new MeasuringMLineRaw(master));
        StreamTools.versionBlockEnd(dis);
    }

    @Override
    public StorableInterface createInstance() {
        return new MeasuringMLineRaw(master);
    }
}
