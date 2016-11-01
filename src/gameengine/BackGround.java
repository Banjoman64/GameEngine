package gameengine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BackGround extends GameObject implements GameObjectInterface{
    
    public BackGround(){
        this(0,0);
    }
    
    public BackGround(Integer x, Integer y){
        this.depth = 0;
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        className = "BackGround";
        icon = Sprite.getSprite(3, 0);
    }
    
    @Override
    public void step() {
       
    }

    @Override
    public void draw(Graphics g) {
        drawSprite(g, Sprite.getSprite(3, 0), (int)x, (int)y);
    }
}
