/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author wyatt
 */
public class Wall extends GameObject implements GameObjectInterface{
    public static BufferedImage sprite = Sprite.getSprite(2, 0);
    
    private Color c = Color.BLACK;
    public Wall(){
        this(0,0);
    }
    
    public Wall(Integer x, Integer y){
        icon = sprite;
        className = "Wall";
        this.depth = 1;
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        tags.add("solid");
        sprite = icon;
    }
    
    @Override
    public void draw(Graphics g) {
        drawSprite(g, sprite, (int)(x), (int)(y), 0, 0, 0);
        g.setColor(Color.WHITE);
        drawText(g, Integer.toString(this.id), (int)(x), (int)(y));
    }


    @Override
    public void step() {
        
    }
}
