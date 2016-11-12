/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    
    public static ArrayList<View> views = new ArrayList<View>();
    
    public GamePanel(AVLTree<GameObject> objectList){
        this.objectList = objectList;
        
        mouse = new MouseInput();
        keyboard = new KeyboardInput();
        
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        GameObject.setInput(keyboard, mouse);
        initializeViews();
    }
    
    public void initializeViews()
    {
        View v = new View(0, 0, getWidth(), getHeight()/2, this);
        views.add(v);
        v = new View(0, getHeight()/2, getWidth(), getHeight()/2, this);
        views.add(v);
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
        
        //v2.setRoomX(1200);
        //v2.setXScale(2);
        //v2.setYScale(2);
        /*allGraphicsImage = null;
        Dimension d = new Dimension(getWidth(),getWidth());
        
        allGraphicsImage = new BufferedImage();
        allGraphics = allGraphicsImage.getGraphics();
        
        clearDisplay(allGraphics);
        
        drawGameObjects(allGraphics);
        
        view = allGraphicsImage.getSubimage(64, 0, getWidth(), getHeight());*/
                
        //Image view1 = allGraphicsImage.getSubImage();
        
        views.get(1).setY(getHeight()/2);
        
        for(View v : views){
            v.setW(getWidth());
            v.setH(getHeight()/2);
            v.draw(g, this);
        }
        
        //new View(0, 0, getWidth(), getHeight()/2, this).draw(g, this);
        //new View(0, getHeight()/2, getWidth(), getHeight()/2, this).draw(g, this);
        //clearDisplay(g);
        
        //drawGameObjects(g);
        
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
