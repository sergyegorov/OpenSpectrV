/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv;

import com.tl.osv.analit.LambdaFk;
import com.tl.osv.dev.AbstractDevice;
import com.tl.osv.dev.DeviceDataBase;
import com.tl.osv.panels.Setup;
import java.io.File;

/**
 *
 * @author root
 */
public class Common {
    public static final int UserLevelLaborant = 1;
    public static final int UserLevelMethodist = 2;
    public static final int UserLevelAdmin = 3;
    public static int userLevel = UserLevelAdmin;
    
    static String startDir;
    static String dataDir;
    public static String getStartDirectory(){
	if(startDir == null)
            startDir = System.getProperty("user.dir");
        return startDir;
    }
    
    public static String getDataDirectory(String name) throws Exception{
        if(dataDir == null){
            dataDir = getStartDirectory()+File.separator+"data"+File.separator;
            File folder = new File(dataDir);
            if(folder.exists() == false && folder.mkdir() == false)
                throw new Exception("Can't create base directory:"+folder.getAbsolutePath());
        }
        if(name != null)
            return dataDir+name;
        return dataDir;
    }

    public static Setup panelSetup;
    public static void init() throws Exception{        
        panelSetup = new Setup();
    }
    
    public static AbstractDevice getDevice(){
        int val = panelSetup.data.findAsInteger(Setup.PARAM_DEVICE_TYPE);
        return DeviceDataBase.getDevice(val);
    }
    
    static LambdaFk currentLambdaFk = null;
    public static LambdaFk getCurrentLambdaFk(){
        if(currentLambdaFk == null)
            return getDevice().getDefaultLy();
        return currentLambdaFk;
    }
}
