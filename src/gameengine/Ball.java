/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import gameengine.Collision.*;
import java.awt.Graphics;
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
    private float angle = 0;
    private float offsetx = 0;
    private float offsety = 0;
    
    private boolean moving = false;
    private boolean grounded = true;
    
    private boolean key_left = false;
    private boolean key_right = false;
    private boolean key_space = false;
    
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
        tags.add("sweg");
        mask = new Mask();
        mask.add(new Rectangle(0f, 32, 32));
        mask.add(new Circle(16));
        mask.setX(x);
        mask.setY(y);

    }
    
    @Override
    public void step() {
        if(GameObject.keyboard.keyDown(KeyEvent.VK_R)){
            angle+=.03;
            mask.setAngle(angle);
        }
        
        if(GameObject.keyboard.keyDown(KeyEvent.VK_O)){
            offsetx+=1;
            offsety+=0;
            mask.setOffset(offsetx, offsety);
        }
        
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
        if(placeCollisionMask("solid", x, y+1)!=null){
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
        if(placeCollisionMask("solid", x+hsp, y)!=null){
            int cc = 0;
            while(placeCollisionMask("solid", x+Math.signum(hsp), y)==null){
                setX(x+Math.signum(hsp));
                cc+=1;
            }
            hsp = 0;
        }
        
        setX(x+hsp);
        
        //vertical collisions
        if(placeCollisionMask("solid", x, y+vsp)!=null){
            int cc = 0;
            while(placeCollisionMask("solid", x, y+Math.signum(vsp))==null){
                setY(y+Math.signum(vsp));
                cc+=1;
            }
            if(vsp>0){
                grounded = true;
            }
            vsp = 0;
            
        }
        
        setY(y+vsp);
        
        sprite.update();
        //mask.setLocation((float)x,(float)y);
    }

    @Override
    public void draw(Graphics g) {
        //mask.draw(g);
        drawSprite(g, sprite.getSprite(), (int)x, (int)y, (int)offsetx, (int)offsety, angle);
        
        g.drawLine((int)x,(int)y-5,(int)x,(int)y+5);
        g.drawLine((int)x-5,(int)y,(int)x+5,(int)y);
        //mask.draw(g);
    }
}
