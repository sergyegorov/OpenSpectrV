/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.dev.AbstractMeasuringPreview;
import com.tl.osv.dev.ProbMeasuring;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author root
 */
public class MeasuringMLine extends ProbMeasuring{
    public MeasuringMLine(File file) throws Exception{
        super(file);
        if(file.exists() == false)
            file.mkdir();
    }

    MeasuringMLinePreview currentPreview;
    @Override
    public AbstractMeasuringPreview getPreview() throws Exception{
        if(currentPreview != null)
            return currentPreview;
        File fl = getFile("preview.bin");
        if(fl.exists() == false)
            return AbstractMeasuringPreview.getNoView();
        try(FileInputStream fis = new java.io.FileInputStream(fl);
                DataInputStream dis = new DataInputStream(fis)){
            currentPreview = new MeasuringMLinePreview(this);
            currentPreview.load(dis);
        }
        return currentPreview;
    }
    
    public MLineMeasuringProgram currentProgram;
    public MLineMeasuringProgram getMeasuringProgram() throws Exception{
        if(currentPreview == null){
            File fl = getFile("condition.bin");
            if(fl.exists()){
                currentProgram = new MLineMeasuringProgram();
                try(FileInputStream fis = new FileInputStream(fl);
                        DataInputStream dis = new DataInputStream(fis);){
                    currentProgram.load(dis);
                }
            }
        }
        return currentProgram;
    }
    
    public void setMeasuringProgram(MLineMeasuringProgram program) throws Exception{
        currentProgram = program;
        File fl = getFile("condition.bin");
        try(FileOutputStream fos = new FileOutputStream(fl);
                DataOutputStream dos = new DataOutputStream(fos);){
            currentProgram.save(dos);
        }
    }
    
    public void setPreview(MeasuringMLinePreview preview) throws Exception{
        if(currentProgram == null)
            throw new Exception("Setup program first...");
        File fl = getFile("preview.bin");
        if(fl.exists() == false && fl.createNewFile() == false)
            throw new Exception("Can't create file "+fl.getPath());
        try(FileOutputStream fos = new java.io.FileOutputStream(fl);
                DataOutputStream dos = new DataOutputStream(fos)){
            preview.save(dos);
        }
        currentPreview = preview;
    }

    
    MeasuringMLineRaw loadedRawData;
    public MeasuringMLineRaw getRawData() throws Exception{
        if(loadedRawData != null)
            return loadedRawData;
        File fl = getFile("raw.bin");
        if(fl.exists() == false)
            return null;
        loadedRawData = new MeasuringMLineRaw(this);
        try(FileInputStream fis = new FileInputStream(fl);
                DataInputStream dis = new DataInputStream(fis)){
            loadedRawData.load(dis);
        }
        return loadedRawData;
    }
    
    public void setRawData(MeasuringMLineRaw rawData) throws Exception{
        File fl = getFile("raw.bin");
        if(fl.exists() == false && fl.createNewFile() == false)
            throw new Exception("Can't create file "+fl.getAbsolutePath());
        try(FileOutputStream fos = new FileOutputStream(fl);
                DataOutputStream dos = new DataOutputStream(fos)){
            rawData.save(dos);
        }
        loadedRawData = rawData;
    }
}
