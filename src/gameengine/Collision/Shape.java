package gameengine.Collision;

import java.awt.Graphics;

//Shape is an interface that describes both circles and retangles which
//are used for collisions. 
public interface Shape{
    public void draw(Graphics g);
    public void setX(float x);
    public void setY(float y);
    public void setLocation(float x, float y);
    public void setAngle(float angle);
    public float getX();
    public float getY();
    public float getAngle();
    
    //Return the up, down, left, right bounds of the shape.
    //These bounds are used for approximating collisions before doing more
    //accurate collisions
    public float[] getBounds();
}
