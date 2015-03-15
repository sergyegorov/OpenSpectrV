/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data.params;

import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author root
 */
public class StringParameter extends StringBaseParameter{
    String value;
    public StringParameter(String name,String description) {
        super(name, description);
    }
    
    public StringParameter(String name) {
        super(name, name+"Desc");
    }
    
    public StringParameter(){
        super("None","None");
    }
    
    @Override
    public String getAsString() {
        return value;
    }

    @Override
    public void setAsString(String value) {
        this.value = value;
    }

    @Override
    public void save(DataOutputStream stream) throws Exception {
        StreamTools.versionBlockBegin(stream, 1);
        
        StreamTools.writeString(name, stream);
        StreamTools.writeString(description, stream);
        StreamTools.writeString(value, stream);
        
        StreamTools.versionBlockEnd(stream);
    }

    @Override
    public void load(DataInputStream stream) throws Exception {
        StreamTools.versionBlockBegin(stream, 1, 1);
        
        name = StreamTools.readString(stream);
        description = StreamTools.readString(stream);
        value = StreamTools.readString(stream);
        
        StreamTools.versionBlockEnd(stream);
    }
    
    @Override
    public String validate(String candidate){
        return null;
    }
}
