/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener{
    private static final int key_count = 256;
    
    private enum KeyState{
        RELEASED, //the key was just released;
        PRESSED,  //the key was just pressed;
        DOWN, //the key is being pressed;
        UP; //the key is not being pressed;
    }
    
    //current state of the keyboard;
    private boolean[] currentKeys = null;
    
    //Polled keyboard state
    private KeyState[] keys = null;
    
    public KeyboardInput(){
        currentKeys = new boolean[key_count];
        keys = new KeyState[key_count];
        
        for(int i = 0 ; i < key_count ; i++){
            keys[i] = KeyState.RELEASED;
        }
        
    }
    
    public synchronized void poll(){
        for(int i = 0 ; i < key_count ; i++){
            //set key state
            if(currentKeys[i]){
                if(keys[i]==KeyState.RELEASED || keys[i]==KeyState.UP){
                    keys[i] = KeyState.PRESSED;
                }else{
                    keys[i] = KeyState.DOWN;
                }
            }else{
                if(keys[i]==KeyState.PRESSED || keys[i]==KeyState.DOWN){
                    keys[i] = KeyState.RELEASED;
                }else{
                    keys[i] = KeyState.UP;
                }
                
            }
        }
    }
    
    public boolean keyPressed(int keyCode){
        System.out.println("OOOOH");
        return keys[keyCode] == KeyState.PRESSED;
    }
    
    public boolean keyReleased(int keyCode){
        return keys[keyCode] == KeyState.RELEASED;
    }
    
    public boolean keyDown(int keyCode){
        return (keys[keyCode] == KeyState.PRESSED || keys[keyCode] == KeyState.DOWN);
    }
    
    
    @Override
    public synchronized void keyTyped(KeyEvent e) {
        
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if(keyCode>=0 && keyCode < key_count){
            currentKeys[keyCode] = true;
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if(keyCode>=0 && keyCode < key_count){
            currentKeys[keyCode] = false;
        }
    }
    
}
