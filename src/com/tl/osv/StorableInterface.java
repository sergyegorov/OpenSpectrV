/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author root
 */
public interface StorableInterface {
    void save(DataOutputStream dos) throws Exception;
    void load(DataInputStream dis) throws Exception;
}
