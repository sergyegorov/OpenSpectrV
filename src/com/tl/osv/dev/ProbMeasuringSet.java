/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public abstract class ProbMeasuringSet extends ProbMeasuringAbstract{
    private final File baseFolder;
    public final static String PROB_EXTENTION = ".prob";
    protected ProbMeasuringSet(File baseFolder,Class measuringClass) throws Exception{
        String name = baseFolder.getAbsolutePath();
        if(name.endsWith(PROB_EXTENTION) == false)
            name += PROB_EXTENTION;
        this.baseFolder = new File(name);
        if(this.baseFolder.exists() == false && this.baseFolder.mkdir() == false)
            throw new Exception("Can't create folder "+name);
        try(FileOutputStream fos = new FileOutputStream(getFile(CLASS_NAME_FILE));
            DataOutputStream dos = new DataOutputStream(fos);){
            StreamTools.writeString(this.getClass().getName(), dos);
        }
        loadMeasuringList();
    }
    
    public String getName(){
        return baseFolder.getName();
    }
    
    final static private String CLASS_NAME_FILE = "class.txt";
    static public ProbMeasuringSet load(File baseFolder) throws Exception{
        String name;
        try(FileInputStream fis = new FileInputStream(baseFolder+File.separator+CLASS_NAME_FILE);
            DataInputStream dis = new java.io.DataInputStream(fis);){
            name = StreamTools.readString(dis);
        }
        Class<?> clazz = Class.forName(name);
        Constructor<?> constr = clazz.getConstructor(File.class);
        Object obj = constr.newInstance(new Object[]{baseFolder});
        return (ProbMeasuringSet)obj;
    }
    
    @Override
    protected final File getFile(String fileName){
        return new File(baseFolder.getAbsolutePath()+File.separator+fileName);
    }
        
    ArrayList<ProbMeasuring> measurings = new ArrayList<>();
    private void loadMeasuringList() throws Exception{
        File fl = getFile("");
        File[] list = fl.listFiles(new java.io.FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return name.endsWith(ProbMeasuring.SP_EXTENTION);
            }
        });
        for(File file : list){
            ProbMeasuring measuring = createMeasuringInstance(file);
            measurings.add(measuring);
        }
    }
    
    public ProbMeasuring get(int index){
        return measurings.get(index);
    }
    
    public int size(){
        return measurings.size();
    }
    
    protected abstract ProbMeasuring createMeasuringInstance(File folder) throws Exception;
}
