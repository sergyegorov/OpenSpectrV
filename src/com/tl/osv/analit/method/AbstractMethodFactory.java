/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.analit.method;

import java.io.File;

/**
 *
 * @author root
 */
public interface AbstractMethodFactory {
    AbstractMethod getInstance(File baseFolder) throws Exception;
}
