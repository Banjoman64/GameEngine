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
public class Ball extends GameObject implements GameObjectInterface{
    
    private BufferedImage[] idleFrames = {Sprite.getSprite(0,1),Sprite.getSprite(1,1)};
    private BufferedImage[] walkFrames = {Sprite.getSprite(0,2),Sprite.getSprite(1,2),Sprite.getSprite(2,2),Sprite.getSprite(3,2),};
    private Animation idleAnimation = new Animation(idleFrames,4);
    private Animation walkAnimation = new Animation(walkFrames,2);
    private Animation sprite = idleAnimation;
    private double vsp = 0;
    private double hsp = 0;
    private double gravity = .5;
    private double move_speed = 2;
    private double friction = .5;
    private boolean moving = false;
    private boolean grounded = true;
    
    private boolean key_left = false;
    private boolean key_right = false;
    private boolean key_space = false;
    
    private Rectangle r;
    private Rectangle r2;
    
    Color colColor = Color.BLACK;

    private int w = 200, h = 400;
    
    public Ball(){
        this(0,0);
    }
    
    public Ball(Integer x, Integer y){
        className = "Ball";
        icon = Sprite.getSprite(0,2);
        this.depth = 4;
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        sprite = idleAnimation;
        sprite.start();
        r = new Rectangle(0, w, h, 50, 50);
        r2 = new Rectangle(3.14f/4f, w, h, 50, 50);
    }
    
    @Override
    public void step() {
        r.setLocation((float)x, (float)y);
        r2.setLocation(GameObject.mouse.mouse_x(), GameObject.mouse.mouse_y());
        
        if(Collisions.collision(r, r2)) colColor = Color.RED;
        else                            colColor = Color.BLACK;
        
        key_right = GameObject.keyboard.keyDown(KeyEvent.VK_RIGHT);
        key_left = GameObject.keyboard.keyDown(KeyEvent.VK_LEFT);
        key_space = GameObject.keyboard.keyDown(KeyEvent.VK_SPACE);
        
        if(key_right){
            hsp = move_speed;
            moving = true;
        }else if(key_left){
            hsp = -move_speed;
            moving = true;
        }else{
            moving = false;
        }
        
        //update grounded
        if(placeCollisionSquare("solid", x, y+1)!=null){
            grounded = true;
        }else{
            grounded = false;
        }
        
        
        if(!grounded){
            //update gravity
            vsp+=gravity;
        }else{
            //jumping
            if(key_space){
                vsp = -12;
            }
            //Friction************************************************************8
            
            if(!moving){
                if(Math.abs(hsp)<friction){
                    hsp = 0;
                }else{
                    hsp+=-Math.signum(hsp)*friction;
                }
            }
        }
        
        //Horizontal Collisions
        if(placeCollisionSquare("solid", x+hsp, y)!=null){
            int cc = 0;
            while(placeCollisionSquare("solid", x+Math.signum(hsp), y)==null){
                x+=Math.signum(hsp);
                cc+=1;
            }
            hsp = 0;
        }
        
        x+=hsp;
        
        //vertical collisions
        if(placeCollisionSquare("solid", x, y+vsp)!=null){
            int cc = 0;
            while(placeCollisionSquare("solid", x, y+Math.signum(vsp))==null){
                y+=Math.signum(vsp);
                cc+=1;
            }
            if(vsp>0){
                grounded = true;
            }
            vsp = 0;
            
        }
        
        y+=vsp;
        
        sprite.update();
    }

    @Override
    public void draw(Graphics g) {
        drawSprite(g, sprite.getSprite(), (int)x, (int)y);
        g.setColor(Color.black);
        drawText(g, "("+x+","+y+")", (int)x, (int)y);
        r.draw(g, colColor);
        r2.draw(g, colColor);
        Collisions.draw(g);
    }
}
