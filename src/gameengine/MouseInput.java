/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author wyatt
 */
public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener{
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
        //Mouse Buttons
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
        
        //Mouse Coordinates
        mouse_coord.setLocation(mouse_event_coord);
        
        //Mouse Scroll
        if(scroll)
        {
            notches = event_notches;
            hasScrolled = true;
        }
        else
        {
            notches = 0;
            hasScrolled = false;
        }
        scroll = false;
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
    
    
    private Point mouse_event_coord = new Point(0,0);
    private Point mouse_coord = new Point(0,0);
    private Point offset = new Point(0,0);
    private Double zoom = 1.0;
    
     @Override
    public synchronized void mouseDragged(MouseEvent me) {
        updateEventCoord(me);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent me) {
        updateEventCoord(me);
    }
    
    private void updateEventCoord(MouseEvent me){
        mouse_event_coord.setLocation(me.getX(), me.getY());
    }
    
    public int mouse_x()
    {
        return (int)((mouse_coord.getX() + offset.getX())/zoom);
    }
    
    public int mouse_y()
    {
        return (int)((mouse_coord.getY() + offset.getY())/zoom);
    }
    
    public Point mouse_point()
    {
        return new Point((int)(( mouse_coord.getX() + offset.getX())/zoom), (int)(( mouse_coord.getY() + offset.getX() )/zoom));
    }
    
    public int mouse_panel_x(){
        return (int)mouse_coord.getX();
    }
    
    public int mouse_panel_y(){
        return (int)mouse_coord.getY();
    }
    
    public Point mouse_panel_point()
    {
        return mouse_coord;
    }
    
    public void setOffset(Point p)
    {
        offset.setLocation(p);
    }
    
    public void setOffsetX(int x){
        offset.x = x;
    }
    
    public void setOffsetY(int y)
    {
        offset.y = y;
    }
    
    public void setOffsetInstance(Point p)
    {
        offset = p;
    }
    
    public void setZoom(Double z){
        zoom = z;
    }
    
    public void setZoomInstance(Double z){
        zoom = z;
    }
    
    //Scrolling
    
    int notches = 0;
    int event_notches = 0;
    boolean scroll = false;
    boolean hasScrolled = false;
    
    public int scroll(){
        return notches;
    }
    
    public boolean hasScrolled()
    {
        return hasScrolled;
    }
    
    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent mwe) {
        event_notches = mwe.getWheelRotation();
        System.out.println(event_notches);
        scroll = true;
    }
}
