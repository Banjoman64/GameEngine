package gameengine.Collision;

import java.awt.Graphics;

public interface Shape{
    public void draw(Graphics g);
    public void setX(float x);
    public void setY(float y);
    public void setLocation(float x, float y);
    public void setAngle(float angle);
    public float getX();
    public float getY();
    public float getAngle();
}
