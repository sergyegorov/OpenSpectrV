/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import java.io.File;

/**
 *
 * @author root
 */
public abstract class ProbMeasuringAbstract {
    protected abstract File getFile(String fileName);
        
    final public File getResultFile(){
        File ret = getFile("result.csv");
        return ret;
    }
    
    MeasuringResult result;
    public final MeasuringResult getResult() throws Exception{
        if(result == null){
            File ret = getResultFile();
            result = new MeasuringResult(ret);
        }
        return result;
    }
    
    public final void setResult(MeasuringResult result) throws Exception{
        this.result = result;
        result.save(getResultFile());
    }
}
