/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.StorableInterface;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class MeasuringResult implements StorableInterface{
    public MeasuringResult(File path) throws Exception{
        
    }
    
    public void save(File path) throws Exception{
        
    }
    
    ArrayList<MeasuringResultRecord> records = new ArrayList<>();
    public int size(){
        return records.size();
    }
    
    public MeasuringResultRecord getByName(String name){
        for(MeasuringResultRecord record : records){
            if(record.elementName.equals(name)){
                return record;
            }
        }
        return null;
    }
    
    public MeasuringResultRecord get(int index){
        return records.get(index);
    }
    
    public void clear(){
        records.clear();
    }
    
    public void add(MeasuringResultRecord rec){
        records.add(rec);
    }

    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        dos.writeInt(records.size());
        for(MeasuringResultRecord record : records){
            StreamTools.saveStorable(dos, record);
        }
        StreamTools.versionBlockEnd(dos);
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        StreamTools.versionBlockBegin(dis, 1, 1);
        int n = dis.readInt();
        records.clear();
        for(int i = 0;i<n;i++)
            records.add((MeasuringResultRecord)StreamTools.loadStorable(dis));
        StreamTools.versionBlockEnd(dis);
    }
}
