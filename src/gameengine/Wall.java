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
    public static String className = "Wall";
    public static BufferedImage icon = Sprite.getSprite(2, 0);
    public static BufferedImage sprite = Sprite.getSprite(2, 0);
    
    private Color c = Color.BLACK;
    public Wall(){
        //super();
        x = 0;
        y = 0;
        width = 32;
        height = 32;
        tags.add("solid");
    }
    
    public Wall(Integer x, Integer y){
        //super();
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        tags.add("solid");
        sprite = icon;
    }
    
    public void draw(Graphics g) {
        g.drawImage(sprite, (int)(x)-offset.x, (int)(y)-offset.y, null);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(this.id), (int)(x)-offset.x, (int)(y)-offset.y);
    }

    @Override
    public void step() {
        if( collisionSquare(Wall.class) != null ){
            c = Color.RED;
        }
        else{
            c = Color.YELLOW;
        }
    }
    
    @Override
    public String toString(){
        return className;
    }

    @Override
    public String getName() {
        return className;
    }
    
    public void wut(){
        System.out.print("yo");
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }
    
}
