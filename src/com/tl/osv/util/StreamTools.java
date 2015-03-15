package com.tl.osv.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import com.tl.osv.Log;
import com.tl.osv.StorableInterface;
import com.tl.osv.StorableManufactureInterface;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;

public class StreamTools {
    public static final StorableInterface loadStorable(File fl) throws Exception{
        FileInputStream fis = new FileInputStream(fl);
        DataInputStream dis = new DataInputStream(fis);
        return loadStorable(dis);
    }
    
    public static final void saveStorable(DataOutputStream dos,StorableInterface toStore) throws Exception{
        versionBlockBegin(dos, 1);
        String name = toStore.getClass().getName();
        writeString(name, dos);
        toStore.save(dos);
        versionBlockEnd(dos);
    }
    
    public static final StorableInterface loadStorable(DataInputStream dis) throws Exception{
        versionBlockBegin(dis, 1, 1);
        String className = readString(dis);
        
        Class<?> clazz = Class.forName(className);
        Constructor<?> ctor = clazz.getConstructor();
        StorableInterface object = (StorableInterface)ctor.newInstance();
        
        object.load(dis);
        
        versionBlockEnd(dis);
        
        return object;
    }
    
    public static final void save(DataOutputStream dos,ArrayList<? extends StorableInterface> array) throws Exception{
        dos.writeByte(1);
        dos.writeInt(array.size());
        for(StorableInterface value : array){
            value.save(dos);
        }
        dos.writeByte(34);
    }
    
    public static final void load(DataInputStream dis,
            ArrayList<? extends StorableInterface> array,
            StorableManufactureInterface manufacture) throws Exception{
        byte ver = dis.readByte();
        if(ver != 1)
            throw new Exception("Wrong version of array list");
        int size = dis.readInt();
        for(int i = 0;i<size;i++){
            StorableInterface object = manufacture.createInstance();
            object.load(dis);
        }
        ver = dis.readByte();
        if(ver != 34)
            throw new Exception("Wrong end of the block");
    }
    
    public static final void save(Properties properties,DataOutputStream dos) throws IOException{
        versionBlockBegin(dos, 1);
        Set<Object> keys = properties.keySet();
        dos.writeInt(keys.size());
        for(Object key : keys){
                writeString((String)key, dos);
                writeString((String)properties.getProperty((String)key),dos);
        }
        versionBlockEnd(dos);
    }

    public static final void load(Properties properties,DataInputStream dis) throws IOException{
        versionBlockBegin(dis, 1, 1);
        int num = dis.readInt();
        for(int i = 0;i<num;i++){
                String key = readString(dis);
                String value = readString(dis);
                properties.put(key, value);
        }
        versionBlockEnd(dis);
    }

    public static final void writeText(File fl,String txt){
        if(txt == null)
            txt = "";
        try {
            if(fl.exists())
                Files.write(fl.toPath(), txt.getBytes(Charset.forName("UTF-8")));
            else
                Files.write(fl.toPath(), txt.getBytes(Charset.forName("UTF-8")), StandardOpenOption.CREATE_NEW);
        } catch (IOException ex) {
            Log.exception("Can't write text file "+fl.getAbsolutePath(), ex);
        }
    }
    
    public static final void writeText(File fl,ArrayList<String> txt){
        try {
            if(fl.exists())
                fl.delete();
            if(txt == null)
                return;
            FileOutputStream os = new FileOutputStream(fl);
            for(String line : txt)
                os.write(line.getBytes(Charset.forName("UTF-8")));
                //Files.write(fl.toPath(), line.getBytes(Charset.forName("UTF-8")), StandardOpenOption.CREATE_NEW);
            os.flush();
            os.close();
        } catch (IOException ex) {
        	Log.exception("Can't write text file "+fl.getAbsolutePath(), ex);
        }
    }
    
    public static final String readText(File fl){
        if(fl.exists() == false)
            return "";
        try {
            byte[] buf = Files.readAllBytes(fl.toPath());
            String ret = new String(buf, Charset.forName("UTF-8"));
            return ret;
        } catch (IOException ex) {
        	Log.exception("Can't write text file "+fl.toString(), ex);
        }
        return "";
    }
    
    // Tested by GUIParameterCollectionTest
    public static final void writeString(String val,DataOutputStream os)throws IOException{
        os.write(12);
        if(val == null){
            os.writeInt(-1);
            return;
        }
        os.writeInt(val.length());
        for(int i = 0;i<val.length();i++)
            os.writeChar(val.charAt(i));
    }

    // Tested by GUIParameterCollectionTest
    public static final String readString(DataInputStream is) throws IOException{
        int prefix = is.read();
        if(prefix != 12)
            throw new IOException("Wrong string data block prefix");
        int len = is.readInt();
        if(len < 0)
            return null;
        char[] data = new char[len];
        for(int i = 0;i<len;i++)
            data[i] = is.readChar();
        return new String(data);
    }

    public static final void writeDoubleArray(double[] data,DataOutputStream os) throws IOException{
        os.write(21);
        os.writeInt(data.length);
        for(int i = 0;i<data.length;i++)
            os.writeDouble(data[i]);
        os.write(32);
    }
    
    public static final double[] readDoubleArray(DataInputStream is) throws IOException{
        int prefix = is.read();
        if(prefix != 21) throw new IOException("Wrong Double Array prefix");
        int len = is.readInt();
        double[] ret = new double[len];
        for(int i = 0;i<len;i++)
            ret[i] = is.readDouble();
        int sufix = is.read();
        if(sufix != 32) throw new IOException("Wrong Double Array sufix");
        return ret;
    }
    
    public static final void writeFloatArray(float[] data,DataOutputStream os) throws IOException{
        os.write(22);
        os.writeInt(data.length);
        for(int i = 0;i<data.length;i++)
            os.writeFloat(data[i]);
        os.write(33);
    }
    
    public static final float[] readFloatArray(DataInputStream is) throws IOException{
        int prefix = is.read();
        if(prefix != 22) throw new IOException("Wrong Float Array prefix");
        int len = is.readInt();
        float[] ret = new float[len];
        for(int i = 0;i<len;i++)
            ret[i] = is.readFloat();
        int sufix = is.read();
        if(sufix != 33) throw new IOException("Wrong Float Array sufix");
        return ret;
    }
    
    // Tested by os4.dev.SpectrRawDataTest
    public static final void writeShortArray(short[] data,DataOutputStream os) throws IOException{
        os.write(37);
        os.writeInt(data.length);
        for(int i = 0;i<data.length;i++)
            os.writeShort(data[i]);
        os.write(83);
    }
    
    public static final void writeIntArray(int[] data,DataOutputStream os) throws IOException{
        os.write(38);
        os.writeInt(data.length);
        for(int i = 0;i<data.length;i++)
            os.writeInt(data[i]);
        os.write(83);
    }
    
    // Tested by os4.dev.SpectrRawDataTest
    public static final short[] readShortArray(DataInputStream is) throws IOException{
        int prefix = is.read();
        if(prefix != 37) throw new IOException("Wrong Short Array prefix");
        int len = is.readInt();
        short[] ret = new short[len];
        for(int i = 0;i<len;i++)
            ret[i] = is.readShort();
        int sufix = is.read();
        if(sufix != 83) throw new IOException("Wrong Short Array sufix");
        return ret;
    }
    
    public static final int[] readIntArray(DataInputStream is) throws IOException{
        int prefix = is.read();
        if(prefix != 38) throw new IOException("Wrong Short Array prefix");
        int len = is.readInt();
        int[] ret = new int[len];
        for(int i = 0;i<len;i++)
            ret[i] = is.readInt();
        int sufix = is.read();
        if(sufix != 83) throw new IOException("Wrong Short Array sufix");
        return ret;
    }
    
    // Tested by os4.dev.SpectrRawDataTest
    public static final int versionBlockBegin(DataInputStream dis,int ver_from,int ver_to) throws IOException{
        int mark = dis.readByte();// Int();
        if(mark != 65)//478456324)
            throw new IOException("Version procted block has not start mark");
        int ver = dis.readByte();
        if(ver < ver_from || ver > ver_to)
            throw new IOException("Unsurported version: "+ver+" ["+ver_from+"..."+ver_to+"]");
        return ver;
    }
    
    // Tested by os4.dev.SpectrRawDataTest
    public static final void versionBlockBegin(DataOutputStream dos,int ver) throws IOException{
        dos.writeByte(65);//478456324);
        if(ver > 127)
            throw new IOException("Version is too big:"+ver+"... Must to be less then 128...");
        dos.writeByte(ver);
    }
    
    // Tested by os4.dev.SpectrRawDataTest
    public static final void versionBlockEnd(DataOutputStream dos) throws IOException{
        dos.writeByte(98);
    }
    
    // Tested by os4.dev.SpectrRawDataTest
    public static final void versionBlockEnd(DataInputStream dis) throws IOException{
        int mark = dis.readByte();
        if(mark != 98)
            throw new IOException("Invalid end of version block...");
    }
}
