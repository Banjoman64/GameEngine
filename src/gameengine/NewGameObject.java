package gameengine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class NewGameObject extends GameObject implements GameObjectInterface{
    
    private BufferedImage[] frames = {Sprite.getSprite(0,1),Sprite.getSprite(1,1)};
    private Animation animation = new Animation(frames,2);
    private Animation sprite = animation;
    
    public NewGameObject(){
        this(0,0);
    }
    
    public NewGameObject(Integer x, Integer y){
        className = "NewGameObject";
        icon = Sprite.getSprite(0, 2);
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        sprite = animation;
        sprite.start();
    }
    
    @Override
    public void step() {
       
    }

    @Override
    public void draw(Graphics g) {

    }
}
