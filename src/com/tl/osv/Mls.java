/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv;

/**
 *
 * @author root
 */
public class Mls {
    public static String translate(String msg){
        return msg;
    }
    
    public static String translate(String msg,String ... parameters){
        String translated = translate(msg);
        for(String parameter : parameters){
            translated += " "+parameter;
        }
        return translated;
    }
}
