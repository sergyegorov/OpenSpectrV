/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.analit.method;

import com.tl.osv.Common;
import com.tl.osv.util.StreamTools;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author root
 */
public abstract class AbstractMethod {
    final private File baseFolder;
    final private static String CLASS_FILE_NAME = "class.bin";
    public AbstractMethod(File baseFolder) throws Exception{
        this.baseFolder = baseFolder;//new File(Common.getDataDirectory(baseFolder.getAbsolutePath())+File.separator);
        if(baseFolder.exists() == false)// && baseFolder.mkdir() == false)
            throw new Exception("Folder '"+baseFolder.getAbsolutePath()+"' does not exists.");
            //throw new Exception("Can't create '"+baseFolder.getAbsolutePath()+"' folder.");
        StreamTools.saveClass(getFile(CLASS_FILE_NAME), this.getClass());
    }
    
    final public static AbstractMethod loadFrom(File baseFolder) throws Exception{
        Class<?> cl = StreamTools.loadClass(new File(baseFolder+File.separator+CLASS_FILE_NAME));
        Constructor<?> ctor = cl.getConstructor(File.class);
        Object object = ctor.newInstance(new Object[] { baseFolder });
        return (AbstractMethod)object;
    }
    
    final protected File getFile(String name){
        if(name == null)
            return baseFolder;
        return new File(baseFolder.getAbsolutePath()+File.separator+name);
    }
    
    final public String getName(){
        return baseFolder.getName();
    }
    
    private final static String DESCRIPTION_FILE_NAME = "description.txt";
    String description;
    public String getDescription() throws IOException{
        if(description == null){
            File fl = getFile(DESCRIPTION_FILE_NAME);
            if(fl.exists() == false)
                StreamTools.writeText(fl, "Just created. "+getClass().getName());
            else
                description =  new String(Files.readAllBytes(
                    Paths.get(getFile(DESCRIPTION_FILE_NAME).getAbsolutePath())),
                    Common.DefaultTextChaset);
        }
        return description;
    }
    
    public void setDescription(String txt) throws IOException{
        byte[] buffer = txt.getBytes(Common.DefaultTextChaset);
        Files.write(
                Paths.get(getFile(DESCRIPTION_FILE_NAME).getAbsolutePath()),
                buffer,
                StandardOpenOption.CREATE
            );
        description = txt;
    }
    abstract public AbstractMethodCalibr getCalibr();
    abstract public AbstractMethodMeasuring getMeasuring();
    
    @Override
    public String toString(){
        return getName();
    }
}
