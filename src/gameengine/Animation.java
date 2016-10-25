/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wyatt
 */
public class Animation {
    private List<Frame> frames = new ArrayList<Frame>();
    
    private int frameDelay; //wait time on each frame(higher means slower animations)
    private int currentFrame; //current fram of the animation;
    private int totalFrames; //total number of frames in the animation;
    private int frameCount; //counts up to go through frames;
    
    private int animationDirection; //-1 for reversed animation 1 for normal
    private boolean stopped;
    
    public Animation(BufferedImage[] frames, int frameDelay){
        this.frameDelay = frameDelay;
        this.stopped = true;
        
        for(int i = 0 ; i < frames.length ; i++){
            addFrame(frames[i], frameDelay);
        }
        
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
        this.frameCount = 0;
    }
    
    public void addFrame(BufferedImage frame, int duration){
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }
    
    public void start(){
        if(!stopped){
            return;
        }
        
        if(frames.size() == 0){
            return;
        }
        
        stopped = false;
    }
    
    public void stop(){
        if(stopped){
            return;
        }
        
        if(frames.size() == 0){
            return;
        }
        
        stopped = true;
    }
    
    public void restart(){
        if(frames.size() == 0){
            return;
        }
        
        stopped = false;
        currentFrame = 0;
    }
    
    public void reset(){
        stopped = true;
        currentFrame = 0;
        frameCount = 0;
    }
    
    public BufferedImage getSprite(){
        return frames.get(currentFrame).getFrame();
    }
    
    public void update(){
    if(!stopped){
        frameCount++;
        if (frameCount > frameDelay){
            frameCount = 0;
            currentFrame+=animationDirection;
            if(currentFrame > totalFrames - 1){
                currentFrame = 0;
            }
            if(currentFrame < 0){
                currentFrame = totalFrames - 1;
            }
        }
    }
    }
}
