/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.panels.comp;

import com.tl.osv.dev.ProbMeasuring;
import com.tl.osv.dev.ProbMeasuringSet;

/**
 *
 * @author root
 */
public interface ResultTableListener {
    public void selectProb(ProbMeasuringSet probSet,
            ProbMeasuring probMeasuring,
            int element,String[] elementNames);
}
