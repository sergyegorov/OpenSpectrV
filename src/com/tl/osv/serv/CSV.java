/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.serv;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author root
 */
public abstract class CSV {
    String[][] values;
    String txt;
    public CSV(File fl) throws Exception{
        txt = new String(Files.readAllBytes(Paths.get(fl.getAbsolutePath())));
        String rows[] = txt.split("\n");
        values = new String[rows.length][];
        for(int row = 0;row<rows.length;row++){
            String[] fields = rows[row].split(";");
            this.values[row] = new String[fields.length];
            for(int col = 0;col<fields.length;col++){
                this.values[row][col] = checkFormat(fields[col]);
            }
        }
        txt = "<html>"+txt+"<html>";
        txt = txt.replaceAll("\n", "<p>");
    }
    
    public abstract String checkFormat(String cond);
    
    public int getRowCount(){
        return  values.length;
    }
    
    public int getColumnCount(){
        return values[0].length;
    }
    
    public String getAsString(int row,int col){
        return values[row][col];
    }
    
    public double getAsDouble(int row,int col){
        try{
            return Double.parseDouble(getAsString(row,col));
        } catch(Exception ex){
            return Double.NaN;
        }
    }
    
    @Override
    public String toString(){
        return txt;
    }
}
