/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

/**
 *
 * @author root
 */
public class MLineAlgorithm {
    public static short[][] calc(MeasuringMLineRaw raw){
        int count = 0;
        for(MeasuringMLineRawRecord record : raw.records){
            if(record.sn > count)
                count = record.sn;
        }
        count ++;
        short[][] ret = new short[count][];
        for(int i = 0;i<count;i++)
            ret[i] = calc(i,raw);
        return ret;
    }
    
    public static short[] calc(int sn,MeasuringMLineRaw raw){
        int size = 0;
        for(int i = 0;i<raw.records.size();i++)
            if(raw.records.get(i).sn == sn){
                size = raw.records.get(i).data.length;
            }
        short[] ret = new short[size];
        short[] nul = new short[size];
        for(MeasuringMLineRawRecord data : raw.records){
            if(sn == data.sn && data.state.isGenOn == false){
                nul = data.data;
                break;
            }
        }
        for(MeasuringMLineRawRecord data : raw.records){
            if(sn == data.sn && data.state.isGenOn){
                ret = data.data;
                break;
            }
        }
        for(int i = 0;i<ret.length;i++)
            ret[i] = (short)(ret[i] - nul[i]);
        return ret;
    }
}
