/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author wyatt
 */
public class View {
    private int x, y;
    private int roomX = 0, roomY = 0;
    private int w, h;
    private double xScale = 1, yScale = 1;
    
    private JPanel panel = null;
    
    private Image image = null;
    
    public View(int x, int y, int w, int h, JPanel panel)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.panel = panel;
    }
    
    private void drawPanel()
    {
        image = null;
        Graphics graphics = null;
        
        image = panel.createImage(w,h);
        graphics = image.getGraphics();
        
        Graphics2D graphics2d = (Graphics2D)graphics;
        graphics2d.scale(xScale, yScale);
        
        clearDisplay(graphics);
        
        GameObject.setOffset(new Point(roomX, roomY));
        GameObject.setZoom(xScale, yScale);
        drawGameObjects(graphics);
        
        this.image  = image;
    }
    
    public void draw(Graphics g, JPanel j)
    {
        drawPanel();
        g.drawImage(image, x, y, j);
    }
    
    private void clearDisplay(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(0,0,w,h);
    }
    
    private void drawGameObjects(Graphics g){
        
        for(GameObject o : GameObject.objectList){
            o.draw(g);
        }
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void setRoomX(int x)
    {
        this.roomX = x;
    }
    
    public void setRoomY(int y)
    {
        this.roomY = y;
    }
    
    public void setW(int w)
    {
        this.w = w;
    }
    
    public void setH(int h)
    {
        this.h = h;
    }
    
    public void setXScale(int x)
    {
        this.xScale = x;
    }
    
    public void setYScale(int y)
    {
        this.yScale = y;
    }
    
    public Image getImage()
    {
        return image;
    }
    
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
}
