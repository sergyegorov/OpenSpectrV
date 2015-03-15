/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.analit.LambdaFk;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author root
 */
public abstract class ProbMeasuring extends ProbMeasuringAbstract{
    private final File baseFolder;
    final static private String CLASS_NAME_FILE = "class.bin";
    public final static String SP_EXTENTION = ".sp";
    public ProbMeasuring(File baseFolder) throws Exception{
        String name = baseFolder.getAbsolutePath();
        if(name.endsWith(SP_EXTENTION) == false)
            name += SP_EXTENTION;
        this.baseFolder = new File(name);
        if(this.baseFolder.exists() == false && this.baseFolder.mkdir() == false)
            throw new Exception("Can't create folder "+name);
        try(FileOutputStream fos = new FileOutputStream(getFile(CLASS_NAME_FILE));
            DataOutputStream dos = new DataOutputStream(fos);){
            StreamTools.writeString(getClass().getName(), dos);
        }
    }

    final static String DISPERS_FILE = "dispers.bin";
    LambdaFk fk;
    public void setLambdaFk(LambdaFk fk) throws Exception{
        this.fk = new LambdaFk(fk);
        try(FileOutputStream fos = new FileOutputStream(getFile(DISPERS_FILE));
            DataOutputStream dos = new DataOutputStream(fos);){
            fk.save(dos);
        }
    }
    
    public LambdaFk getLambdaFk() throws Exception{
        if(this.fk == null){
            this.fk = new LambdaFk();
            try(FileInputStream fis = new FileInputStream(getFile(DISPERS_FILE));
                DataInputStream dis = new DataInputStream(fis);){
                this.fk.load(dis);
            }
        }
        return this.fk;
    }
    
    public abstract AbstractMeasuringPreview getPreview() throws Exception;
    
    final public String getName(){
        return baseFolder.getName();
    }
    
    @Override
    protected final File getFile(String fileName){
        return new File(baseFolder.getAbsolutePath()+File.separator+fileName);
    }
}
