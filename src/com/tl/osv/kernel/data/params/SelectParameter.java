/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data.params;

import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.JComponent;

/**
 *
 * @author root
 */
public class SelectParameter extends IntParameter{
    String[] optionNames;
    public SelectParameter(String name,int value,String[] optionNames){
        super(name,value,0,optionNames.length-1);
        this.optionNames = optionNames;
    }
    
    public SelectParameter(){
        super("None",0,0,0);
    }
    
    public String[] getSelectionList(){
        return optionNames;
    }
    
    public void setSelectionList(String list[]){
        optionNames = list;
    }
    @Override
    public JComponent getGui(){
        return new SelectParameterGUI(this);
    }
    
    @Override
    public void save(DataOutputStream stream) throws Exception {
        StreamTools.versionBlockBegin(stream, 1);
        super.save(stream);
        stream.writeInt(optionNames.length);
        for(String line : optionNames){
            StreamTools.writeString(line, stream);
        }
        StreamTools.versionBlockEnd(stream);
    }
    
    @Override
    public void load(DataInputStream stream) throws Exception {
        StreamTools.versionBlockBegin(stream, 1, 1);
        super.load(stream);
        int n = stream.readInt();
        optionNames = new String[n];
        for(int i = 0;i<n;i++){
            optionNames[i] = StreamTools.readString(stream);
        }
        StreamTools.versionBlockEnd(stream);
    }
}
