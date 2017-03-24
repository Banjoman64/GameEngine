/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import gameengine.Collision.Circle;
import gameengine.Collision.Mask;
import gameengine.Collision.Rectangle;
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
        mask = new Mask();
        mask.add(new Rectangle(0f, 32, 32));
        mask.add(new Circle(16));
        mask.setX(x);
        mask.setY(y);
        //mask.getBounds();
    }
    
    @Override
    public void draw(Graphics g) {
        //setX(x+.01);
        if(collisionMask("solid")!= null){
            g.setColor(Color.red);
            drawText(g, "shiite", (int)x-100, (int)y-100);
        }else{
            g.setColor(Color.WHITE);
        }
        
        drawSprite(g, sprite, (int)(x), (int)(y), 0, 0, 0);
        drawText(g, Integer.toString(this.id), (int)(x), (int)(y));
        //mask.draw(g);
    }


    @Override
    public void step() {
        
    }
}
