package gameengine.Collision;

import java.awt.Color;
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
        g.fillOval((int)(x - radius), (int)(y - radius), (int)(radius*2), (int)(radius*2));
        
        
        g.setColor(Color.BLACK);
        float[] b = new float[4];
        b = getBounds();
        g.drawRect((int)b[2], (int)b[1], (int)(b[0] - b[2]), (int)(b[3] - b[1]));
    }

    public void setX(float x) {
        if(this.x != x)
            this.x = x;
    }

    public void setY(float y) {
        if(this.y != y)
            this.y = y;
    }

    public void setLocation(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setAngle(float angle) {
        
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return 0;
    }
    
    public float[] getBounds(){
        float[] bounds = new float[4];
        bounds[0] = x + radius;
        bounds[1] = y - radius;
        bounds[2] = x - radius;
        bounds[3] = y + radius;
        return bounds;
    }
}
