/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.Log;
import com.tl.osv.StorableInterface;
import com.tl.osv.util.StreamTools;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class MeasuringResultRecord implements StorableInterface{
    public double value,sko;
    public String elementName;
    public MeasuringResultRecord(String elementName,double value,double sko){
        this.elementName = elementName;
        this.value = value;
        this.sko = sko;
    }
    
    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        dos.writeDouble(value);
        dos.writeDouble(sko);
        StreamTools.writeString(elementName, dos);
        StreamTools.versionBlockEnd(dos);
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        StreamTools.versionBlockBegin(dis, 1, 1);
        value = dis.readDouble();
        sko = dis.readDouble();
        elementName = StreamTools.readString(dis);
        StreamTools.versionBlockEnd(dis);
    }
    
    class ResultPanel extends JPanel{
        MeasuringResultRecord record;
        public ResultPanel(MeasuringResultRecord record){
            this.record = record;
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
        
        @Override
        public void paint(Graphics g){
            try{
                int w = getWidth();
                int h = getHeight();
                g.setColor(Color.white);
                g.fillRect(0, 0, w, h);
                
            }catch(Exception ex){
                Log.exception(ex);
            }
        }
    }
    
    public ResultPanel getDetailsPanel(){
        return new ResultPanel(this);
    }
}
