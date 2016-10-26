/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

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
    public static String className = "Ball";
    public static BufferedImage icon = Sprite.getSprite(0, 2);
    
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
    
    
    
    public Ball(){
        //super();
        x = 0;
        y = 0;
        
        width = 32;
        height = 32;
    }
    
    public Ball(Integer x, Integer y){
        //super();
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        sprite = walkAnimation;
        sprite.start();
    }
    
    @Override
    public void step() {
        key_right = GameEngine.keyboardInput.keyDown(KeyEvent.VK_RIGHT);
        key_left = GameEngine.keyboardInput.keyDown(KeyEvent.VK_LEFT);
        key_space = GameEngine.keyboardInput.keyDown(KeyEvent.VK_SPACE);
        
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
    public String toString() {
        return className;
    }

    @Override
    public String getName() {
        return className;
    }
    
     @Override
    public BufferedImage getIcon() {
        return icon;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite.getSprite(), (int)(x)-offset.x, (int)(y)-offset.y, null);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(this.id), (int)(x)-offset.x, (int)(y)-offset.y);
    }
}
