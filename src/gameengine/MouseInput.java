/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author wyatt
 */
public class MouseInput implements MouseListener{
    private static final int button_count = 4;
    
    private enum ButtonState{
        RELEASED,
        PRESSED,
        DOWN,
        UP;
    }
    
    private boolean[] currentButtons = null;
    
    private ButtonState[] buttons = null;
    
    public MouseInput(){
        currentButtons = new boolean[button_count];
        buttons = new ButtonState[button_count];
        
        for(int i = 0 ; i < button_count ; i++){
            buttons[i] = ButtonState.RELEASED;
        }
    }
    
    public synchronized void poll(){
        for(int i = 0 ; i < button_count ; i++){
            if(currentButtons[i]){
                if(buttons[i]==ButtonState.RELEASED || buttons[i]==ButtonState.UP){
                    buttons[i] = ButtonState.PRESSED;
                }else{
                    buttons[i] = ButtonState.DOWN;
                }
            }else{
                if(buttons[i]==ButtonState.PRESSED || buttons[i]==ButtonState.DOWN){
                    buttons[i] = ButtonState.RELEASED;
                }else{
                    buttons[i] = ButtonState.UP;
                }
                
            }
        }
    }
    
    public boolean buttonPressed(int buttonCode){
        return buttons[buttonCode] == ButtonState.PRESSED;
    }
    
    public boolean buttonReleased(int buttonCode){
        return buttons[buttonCode] == ButtonState.RELEASED;
    }
    
    public boolean buttonDown(int buttonCode){
        return (buttons[buttonCode] == ButtonState.PRESSED || buttons[buttonCode] == ButtonState.DOWN);
    }
    
    @Override
    public synchronized void mouseClicked(MouseEvent me) {
    }

    @Override
    public synchronized void mousePressed(MouseEvent me) {
        int buttonCode = me.getButton();
        
        if(buttonCode>=0 && buttonCode < button_count){
            currentButtons[buttonCode] = true;
        }
    }

    @Override
    public synchronized void mouseReleased(MouseEvent me) {
        int buttonCode = me.getButton();
        
        if(buttonCode>=0 && buttonCode < button_count){
            currentButtons[buttonCode] = false;
        }
    }

    @Override
    public synchronized void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public synchronized void mouseExited(MouseEvent me) {
        
    }
    
    
}
