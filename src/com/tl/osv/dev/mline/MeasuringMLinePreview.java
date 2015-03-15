/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev.mline;

import com.tl.osv.Log;
import com.tl.osv.StorableInterface;
import com.tl.osv.dev.AbstractMeasuringPreview;
import com.tl.osv.util.StreamTools;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.JComponent;

/**
 *
 * @author root
 */
public class MeasuringMLinePreview extends AbstractMeasuringPreview 
        implements StorableInterface{
    public short[][] data;
    MeasuringMLine master;
    public MeasuringMLinePreview(MeasuringMLine master){
        this.master = master;
    }
    
    public MeasuringMLinePreview(MeasuringMLine master,MeasuringMLineRaw raw){
        this.master = master;
        data = MLineAlgorithm.calc(raw);
    }
    
    @Override
    public JComponent getPreviewPanel() {
        MLSpectrPreviewControl preview = new MLSpectrPreviewControl();
        try {
            preview.show(this,master.getLambdaFk());
        } catch (Exception ex) {
            Log.exception(ex);
        }
        return preview;
    }

    @Override
    public void save(DataOutputStream dos) throws Exception {
        StreamTools.versionBlockBegin(dos, 1);
        dos.writeInt(data.length);
        for(int s = 0;s<data.length;s++){
            StreamTools.writeShortArray(data[s], dos);
        }
        StreamTools.versionBlockEnd(dos);
    }

    @Override
    public void load(DataInputStream dis) throws Exception {
        StreamTools.versionBlockBegin(dis, 1, 1);
        int size = dis.readInt();
        data = new short[size][];
        for(int s = 0;s<size;s++)
            data[s] = StreamTools.readShortArray(dis);
        StreamTools.versionBlockEnd(dis);
    }
}
