/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author wyatt
 */
public class GamePanel extends JPanel{
    private List<GameObject> objectList;
    
    public GamePanel(List<GameObject> objectList){
        this.objectList = objectList;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent( g );
        clearDisplay(g);
        drawGameObjects(g);
    }
    
    private void clearDisplay(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
    }
    
    private void drawGameObjects(Graphics g){
        for(GameObject o : objectList){
            o.draw(g);
        }
    }
}
