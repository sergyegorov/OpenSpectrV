/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.analit;

import com.tl.osv.Common;
import com.tl.osv.serv.CSV;
import java.io.File;

/**
 *
 * @author root
 */
public class StandardCSV extends CSV{
    private StandardCSV(File fl) throws Exception {
        super(fl);
    }
    
    @Override
    public String checkFormat(String cond) {
        return cond.trim();
    }
    
    private static File getBaseFolder() throws Exception{
        String file = Common.getDataDirectory("library"+File.separator);
        File fl = new File(file);
        if(fl.exists() == false && fl.mkdir() == false)
            throw new Exception("Can't create "+fl.getAbsolutePath()+" directory!");
        return fl;
    }
    
    public static String[] getList() throws Exception{
        File fl = getBaseFolder();
        return fl.list();
    }
    
    public static StandardCSV get(String localName) throws Exception{
        File fl = getBaseFolder();
        fl = new File(fl.getAbsolutePath()+localName);
        return new StandardCSV(fl);
    }
}
