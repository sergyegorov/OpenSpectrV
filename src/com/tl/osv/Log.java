/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
<<<<<<< HEAD
 * 
 * Test changes 1
 * Test Brunch 2
 * Test changes for brunch 1
=======
>>>>>>> parent of d6a18fb (Just for fun! :))))
 */
package com.tl.osv;

/**
 *
 * @author root
 */
public class Log {
    public static void exception(Exception ex){
        System.err.println("Exception: "+ex);
        ex.printStackTrace();
    }
    public static void exception(String description,Exception ex){
        System.err.print("Exception:");
        System.err.println(description);
        System.err.println("Exception: "+ex);
        ex.printStackTrace();
    }
}
