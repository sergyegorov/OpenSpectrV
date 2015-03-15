/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author root
 */
public abstract class DataStorageStreamable extends DataStorageAbstract {
    public DataStorageStreamable(String name,String description){
        super(name,description);
    }

    protected abstract DataOutputStream createOuputStream() throws Exception;
    protected abstract DataInputStream createInputStream() throws Exception;
    
    @Override
    protected void commitInternal() throws Exception {
        try(DataOutputStream dos = createOuputStream()){
            save(dos);
        }
    }
}
