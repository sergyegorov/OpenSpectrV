package com.tl.osv.analit;

import com.tl.osv.Common;
import com.tl.osv.Log;
import com.tl.osv.StorableInterface;
import com.tl.osv.dev.AbstractDevice;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class LambdaFk implements StorableInterface {
    ArrayList<Function> functions = new ArrayList<>();
    int[] sensorSizes;
    int[] basePixels;
    public LambdaFk(){
        AbstractDevice device = Common.getDevice();
        init(device.getDefaultLy());
    }
    
    public MinMax2D getLyDimenstion(MinMax2D ret){
        if(ret == null)
            ret = new MinMax2D();
        for(int sn = 0;sn<functions.size();sn++){
            ret.addValueX(getLambdaForSensor(sn, 0));
            ret.addValueX(getLambdaForSensor(sn, sensorSizes[sn]));
        }
        return ret;
    }
    
    public MinMax2D getPixelDimenstion(MinMax2D ret){
        if(ret == null) 
            ret = new MinMax2D();
        ret.addValueX(0);
        int size = basePixels.length-1;
        ret.addValueX(basePixels[size]+sensorSizes[size]);
        return ret;
    }
    
    public LambdaFk(int[] sensorSizes){
        int basePixel = 0;
        functions.clear();
        basePixels = new int[sensorSizes.length];
        for(int i = 0;i<sensorSizes.length;i++){
            basePixels[i] = basePixel;
            int sSize = sensorSizes[i];
            double[] n = {0,sSize};
            double[] la = {basePixel,basePixel+sSize};
            boolean[] en = {true,true};
            try {
                functions.add(new Function(Function.FType.Poly1, en, n, la));
            } catch (OS4AnalitException ex) {
                Log.exception(ex);
                throw new RuntimeException("Unexpected exception...");
            }
            basePixel += sSize;
        }
        this.sensorSizes = (int[])sensorSizes.clone();
    }


    public double getLambdaForSensor(int sn,double pixel){
        return functions.get(sn).calcDirect(pixel);
    }

    public double getPixelForSensor(int sn,double lambda){
        return functions.get(sn).calcRev(lambda);
    }

    final public void init(LambdaFk fromSoruce){
        functions.clear();
        for(Function fk : fromSoruce.functions){
            if(fk == null)
                functions.add(null);
            else
                functions.add(new Function(fk));
        }
        sensorSizes = (int[])fromSoruce.sensorSizes.clone();
        basePixels = (int[])fromSoruce.basePixels.clone();
    }
    
    public LambdaFk(LambdaFk fromSoruce){
        init(fromSoruce);
    }

    public void setFunction(int sensorIndex,Function fk){
        while(sensorIndex > functions.size())
            functions.add(null);
        if(sensorIndex == functions.size())
            functions.add(fk);
        else
            functions.set(sensorIndex, fk);
    }

    public Function getFunction(int index){
        return functions.get(index);
    }

    public void save(File file) throws Exception{
        try(FileOutputStream fos = new FileOutputStream(file);
                DataOutputStream dos = new DataOutputStream(fos);){
            save(dos);
        }
    }
    
    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        dos.writeInt(functions.size());
        for(Function fk : functions){
            if(fk != null){
                dos.writeBoolean(true);
                fk.save(dos);
            } else
                dos.writeBoolean(false);
        }
        StreamTools.writeIntArray(basePixels, dos);
        StreamTools.writeIntArray(sensorSizes, dos);
        StreamTools.versionBlockEnd(dos);
    }

    public void load(File file) throws Exception{
        try(FileInputStream fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);){
            load(dis);
        }
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        functions.clear();
        StreamTools.versionBlockBegin(dis, 1, 1);
        int n = dis.readInt();
        for(int i = 0;i<n;i++){
            if(dis.readBoolean() == false)
                functions.add(null);
            else{
                Function fk = new Function();
                fk.load(dis);
                functions.add(fk);
            }
        }
        basePixels = StreamTools.readIntArray(dis);
        sensorSizes = StreamTools.readIntArray(dis);
        StreamTools.versionBlockEnd(dis);
    }
}
