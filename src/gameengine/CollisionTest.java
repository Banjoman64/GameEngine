/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import gameengine.Collision.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author wyatt
 */
public class CollisionTest extends GameObject implements GameObjectInterface{

    private Rectangle r;
    private Rectangle r2;
    private Circle c1;
    private boolean collision;
    

    private int w = 50, h = 100;
    
    public CollisionTest(){
        this(0,0);
    }
    
    public CollisionTest(Integer x, Integer y){
        className = "Ball";
        icon = Sprite.getSprite(0,0);
        this.depth = 4;
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        r = new Rectangle(13, w, h, 50, 50);
        r2 = new Rectangle((float) (Math.PI/3), w, h, 500, 500);
        c1 = new Circle(50, 750, 250);
    }
    
    @Override
    public void step() {
        r.setLocation(150f, 150f);
        r.setAngle(r.angle+.1f);
        r2.setLocation(GameObject.mouse.mouse_x(), GameObject.mouse.mouse_y());
        r2.setAngle(r.angle+.1f);
        
        if(Collisions.collision(r, r2)) collision = true;
        else if(Collisions.collision(r2, c1)) collision = true;
        else collision = false;
        
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.black);
        r.draw(g);
        r2.draw(g);
        c1.draw(g);
        drawText(g, (collision) ? "There is a collision" : "There is NOT a collision", 20,20);
    }
}
