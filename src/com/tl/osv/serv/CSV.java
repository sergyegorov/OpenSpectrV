/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.serv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public abstract class CSV extends DefaultTableModel{
    String txt;
    File srcFile;
    public CSV(File fl) throws Exception{
        srcFile = fl;
        txt = new String(Files.readAllBytes(Paths.get(fl.getAbsolutePath())));
        String rows[] = txt.split("\r\n");
        for(int row = 0;row<rows.length;row++){
            String[] fields = rows[row].split(";");
            for(int i = 0;i<fields.length;i++)
                fields[i] = checkFormat(fields[i]);
            if(row == 0){
                for(String field : fields)
                    addColumn(field);
            } else
                addRow(fields);
        }
        txt = "<html>"+txt+"<html>";
        txt = txt.replaceAll("\n", "<p>");
        resetModified();
    }
    
    public abstract String checkFormat(String cond);
    
    public String getAsString(int row,int col){
        return (String)getValueAt(row, col);
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
    
    private boolean Editable;
    public void setEditable(boolean flag){
        Editable = flag;
    }
 
    @Override
    public boolean isCellEditable(int row, int column){
        return Editable;
    }
    
    private boolean Modified;
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        super.setValueAt(aValue, rowIndex, columnIndex);
        Modified = true;
    }
    
    @Override
    public void addColumn(Object obj){
        super.addColumn(obj);
        Modified = true;
    }
    
    @Override
    public void addRow(Object[] row){
        super.addRow(row);
        Modified = true;
    }
    
    @Override
    public void removeRow(int index){
        super.removeRow(index);
        Modified = true;
    }
    
    public boolean isModefied(){
        return Modified;
    }
    
    public void modified(){
        Modified = true;
    }
    
    public void resetModified(){
        Modified = false;
    }
    
    public void commit() throws FileNotFoundException{
        Modified = false;
        PrintWriter pw = new PrintWriter(srcFile);
        for(int c = 0;c<getColumnCount();c++){
            pw.write((String)getColumnName(c));
            pw.write(';');
        }
        pw.write("\r\n");
        for(int r = 0;r<getRowCount();r++){
            for(int c = 0;c<getColumnCount();c++){
                pw.write((String)getValueAt(r, c));
                pw.write(';');
            }
            pw.write("\r\n");
        }
        pw.flush();
        pw.close();
    }
}
