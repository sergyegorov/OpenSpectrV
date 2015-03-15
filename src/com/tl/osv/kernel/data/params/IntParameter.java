/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data.params;

import com.tl.osv.Mls;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author root
 */
public class IntParameter extends StringBaseParameter{
    int value;
    int validFrom,validTo;
    public IntParameter(String name, String description,
            int value,int validFrom,int validTo) {
        super(name, description);
        this.value = value;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    public IntParameter(String name,int value,int validFrom,int validTo) {
        super(name, name+"Desc");
        this.value = value;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    
    public IntParameter(){
        super("None", "None");
    }

    public int getAsInteger(){
        return value;
    }
    
    public void setAsInteger(int value){
        this.value = value;
    }
    
    @Override
    public String getAsString() {
        return Integer.toString(value);
    }

    @Override
    public void setAsString(String value) {
        this.value = Integer.parseInt(value);
    }

    @Override
    public String validate(String candidate) {
        try{
            int val = Integer.parseInt(candidate);
            if(val < validFrom)
                return Mls.translate("#IntParameterErrorOutOfRangeLow_VALID_VALUE", 
                        Integer.toString(validFrom),Integer.toString(val));
            if(val > validTo)
                return Mls.translate("#IntParameterErrorOutOfRangeHi_VALID_VALUE", 
                        Integer.toString(validTo),Integer.toString(val));
            return null;
        } catch(Exception ex) {
            return ex.toString();
        }
    }

    @Override
    public void save(DataOutputStream stream) throws Exception {
        StreamTools.versionBlockBegin(stream, 1);
        
        StreamTools.writeString(name, stream);
        StreamTools.writeString(description, stream);
        stream.writeInt(value);
        stream.writeInt(validFrom);
        stream.writeInt(validTo);
        
        StreamTools.versionBlockEnd(stream);
    }

    @Override
    public void load(DataInputStream stream) throws Exception {
        StreamTools.versionBlockBegin(stream, 1, 1);
        
        name = StreamTools.readString(stream);
        description = StreamTools.readString(stream);
        value = stream.readInt();
        validFrom = stream.readInt();
        validTo = stream.readInt();
        
        StreamTools.versionBlockEnd(stream);
    }
}
