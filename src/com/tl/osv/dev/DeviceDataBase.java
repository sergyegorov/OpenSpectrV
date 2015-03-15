/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.dev.mline.debug.DeviceDebug;

/**
 *
 * @author root
 */
public class DeviceDataBase {
    final static AbstractDevice[] allKnownDevices = new AbstractDevice[]{
        new DeviceDebug()
    };
    
    public static AbstractDevice getDevice(int deviceIndex){
        return allKnownDevices[deviceIndex];
    }
    
    final static String[] deviceList = {
        "CalibrDebug",
        "E2048_1",
        "E2048_2",
        "E2048_3",
        "E2048_4",
        "E2048_5",
        "E2048_6",
        "E2048_7",
        "E2048_8",
        "E3560_1",
        "E3560_2",
        "E3560_3",
        "E3560_4",
        "E3560_5",
        "E3560_6",
        "E3560_7",
        "E3560_8",
        "E3560_9",
        "E3560_10",
        "U3560_1",
        "U3560_2",
        "U3560_3",
        "U3560_4",
        "U3560_5",
        "U3560_6",
        "U3560_7",
        "U3560_8",
        "U3560_9",
        "U3560_10",
        "U3560_11",
        "U3560_12",
        "U3560_13",
        "U3560_14",
        "U3560_15"
    };
    public static String[] getDeviceList(){
        return deviceList;
    }
}
