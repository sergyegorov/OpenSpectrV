/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data;

/**
 *
 * @author root
 */
public class DataStorageFolder extends DataStorageAbstract {
    public DataStorageFolder(){
        super("None","None");
    }
    
    public DataStorageFolder(String name) {
        super(name, name+"Description");
    }

    @Override
    protected void commitInternal() throws Exception {
        throw new UnsupportedOperationException("You must call it for ROOT");
    }
}
