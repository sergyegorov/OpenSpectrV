/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.serv;

import com.tl.osv.OSMain;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author root
 */
public class GuiDecorator {
    public static Image getImage(String name){
        try{
            Image img = ImageIO.read(OSMain.Main.getClass().getResource("resources/"+name+".bmp"));
            return img;
        } catch(Exception ex){
            Image img = new java.awt.image.BufferedImage(32, 32, BufferedImage.TYPE_3BYTE_BGR);
            Graphics g = img.getGraphics();
            g.setColor(java.awt.Color.lightGray);
            g.fillRect(0, 0, img.getWidth(null), img.getHeight(null));
            g.setColor(Color.black);
            g.drawString("No", 5, 15);
            return img;
        }
    }
    
    public static void decorateMainMenu(JButton button,String name){
        Image img = getImage(name);
        button.setIcon(new javax.swing.ImageIcon(img));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.TOP);
        button.setVerticalAlignment(SwingConstants.TOP);
    }
}
