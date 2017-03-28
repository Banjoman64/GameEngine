package gameengine.Collision;

import java.awt.Color;
import java.awt.Graphics;

//Circle describes a circular bounding box. Circle implements Shape.
//The circle is described by a radius, and coordinates.
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
    
    //Draw the circle to the screen
    public void draw(Graphics g){
        //Draw the circle
        g.fillOval((int)(x - radius), (int)(y - radius), (int)(radius*2), (int)(radius*2));
        
        //draw the bounding box of the circle
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
    
    //Get the bounds of the circle. The bounds are the leftmost, rightmost,
    //upper, and lower coordinates. These values are used for approximating
    //collisions before doing more detailed collisions.
    //returns a float array
    //bounds[0] = right bound
    //bounds[1] = upper bound
    //bounds[2] = left bound
    //bounds[3] = lower bound
    public float[] getBounds(){
        float[] bounds = new float[4];
        bounds[0] = x + radius;
        bounds[1] = y - radius;
        bounds[2] = x - radius;
        bounds[3] = y + radius;
        return bounds;
    }
}
