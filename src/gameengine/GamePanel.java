/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author wyatt
 */
public class GamePanel extends JPanel{
    private KeyboardInput keyboard;
    private MouseInput mouse;
    private AVLTree<GameObject> objectList;
    
    public GamePanel(AVLTree<GameObject> objectList){
        this.objectList = objectList;
        
        mouse = new MouseInput();
        keyboard = new KeyboardInput();
        
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        GameObject.setInput(keyboard, mouse);
        
    }
    
    public void update()
    {
        keyboard.poll();
        mouse.poll();
        
        for(GameObject o : objectList){
            o.step();
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent( g );
        clearDisplay(g);
        drawGameObjects(g);
    }
    
    private void clearDisplay(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(0,0,getWidth(),getHeight());
    }
    
    private void drawGameObjects(Graphics g){
        for(GameObject o : objectList){
            o.draw(g);
        }
    }
}
