/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import gameengine.Collision.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author wyatt
 */
public class CollisionTest extends GameObject implements GameObjectInterface{

    
    private Mask m1, m2;
    private Rectangle r1;
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
        r1 = new Rectangle(13, w, h, 50, 50);
        r2 = new Rectangle((float) (Math.PI/3), w, h, 500, 500);
        c1 = new Circle(50, 750, 250);
        m1 = new Mask(null, -16, -16);
        m1.add(new Rectangle(0, 32, 32));
        
        m2 = new Mask();
        m2.add(new Circle(30, 30, 30));
    }
    
    @Override
    public void step() {
        boolean l, r, u, d, s, a;
        l = GameObject.keyboard.keyDown(KeyEvent.VK_LEFT);
        r = GameObject.keyboard.keyDown(KeyEvent.VK_RIGHT);
        u = GameObject.keyboard.keyDown(KeyEvent.VK_UP);
        d = GameObject.keyboard.keyDown(KeyEvent.VK_DOWN);
        s = GameObject.keyboard.keyDown(KeyEvent.VK_SPACE);
        a = GameObject.keyboard.keyDown(KeyEvent.VK_ALT);
        
        r1.setLocation(150f, 150f);
        r1.setAngle(r1.angle+.1f);
        m1.setLocation(GameObject.mouse.mouse_x(), GameObject.mouse.mouse_y());
        r2.setAngle(r1.angle+.1f);
        
        if(l) m2.setLocation(m2.getX() - 2, m2.getY());
        if(r) m2.setLocation(m2.getX() + 2, m2.getY());
        if(u) m2.setLocation(m2.getX(), m2.getY() - 2);
        if(d) m2.setLocation(m2.getX(), m2.getY() + 2);
        
        if(s) m1.setOffsetX(m1.getOffsetx()+2);
        if(a) m1.setAngle(m1.getAngle()+.02f);
        
        if(Collisions.collision(m1, m2)) collision = true;
        else if(Collisions.collision(r2, c1)) collision = true;
        else collision = false;
        
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.black);
        r1.draw(g);
        r2.draw(g);
        c1.draw(g);
        m1.draw(g);
        m2.draw(g);
        drawText(g, (collision) ? "There is a collision" : "There is NOT a collision", 20,20);
    }
}
