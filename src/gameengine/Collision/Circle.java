package gameengine.Collision;

import java.awt.Graphics;

public class Circle implements Shape{
    public float x,y;
    public int radius;
    public Circle(int r){
        this(r, 0,0);
    }
    
    public Circle(int r, float x, float y){
        this.radius = r;
        this.x = x;
        this.y = y;
    }
    
    public void draw(Graphics g){
        g.drawOval((int)(x - radius), (int)(y - radius), (int)(radius*2), (int)(radius*2));
    }
}
