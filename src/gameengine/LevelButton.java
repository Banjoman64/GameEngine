/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

/**
 *
 * @author wyatt
 */
public class LevelButton extends JButton{
    public ClassData buttonData;
    
    public LevelButton(ClassData buttonData){
        super("");
        this.buttonData = buttonData;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension originalSize = super.getSize();
        int hGap = (int)(originalSize.width*.2);
        int wGap = (int)(originalSize.height*.33);
        
        int textWidth = g.getFontMetrics().stringWidth(buttonData.getLabel()); 
        
        int textX = (originalSize.width/2) - (textWidth/2);
        int iconX = (originalSize.width/2)-16;
        int textY = wGap;
        int iconY = wGap*2-16;
        
        
        
        g.setColor(Color.BLACK);
        g.drawString(buttonData.getLabel(),textX, textY);
        g.drawImage(buttonData.getIcon(), iconX, iconY, 32, 32, null);
    }
    
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        return size;
    }
    
    public String getName(){
        return buttonData.getLabel();
    }
    
    public void setButtonData(ClassData buttonData){
        this.buttonData = buttonData;
    }
}
