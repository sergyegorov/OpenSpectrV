/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author root
 */
public class DataStorageFile extends DataStorageStreamable {
    File file;
    public DataStorageFile(String name, String description,File file) throws Exception {
        super(name, description);
        this.file = file;
        if(file.exists())
            load(createInputStream());
    }

    @Override
    protected DataOutputStream createOuputStream() throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        return new DataOutputStream(fos);
    }

    @Override
    protected DataInputStream createInputStream() throws Exception {
        FileInputStream fis = new FileInputStream(file);
        return new DataInputStream(fis);
    }
}
