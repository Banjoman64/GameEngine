/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.image.BufferedImage;

/**
 *
 * @author wyatt
 */
public class Frame {
    BufferedImage frame;
    int duration;
    
    public Frame(BufferedImage frame, int duration){
        this.frame = frame;
        this.duration = duration;
    }
    
    public BufferedImage getFrame() {
        return frame;
    }

    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
