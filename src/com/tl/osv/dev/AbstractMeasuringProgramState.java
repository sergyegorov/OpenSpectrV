/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.dev;

import com.tl.osv.StorableInterface;
import com.tl.osv.dev.mline.MeasuringMLineRaw;

/**
 *
 * @author root
 */
public abstract class AbstractMeasuringProgramState implements StorableInterface{
    public abstract void run(AbstractDevice dev,ProbMeasuring measuring,MeasuringMLineRaw rawData) throws Exception;
}
