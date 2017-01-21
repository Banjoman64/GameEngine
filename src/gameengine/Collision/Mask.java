package gameengine.Collision;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

public class Mask implements Iterable{
    public ArrayList<Shape> mask;
    public ArrayList<Point> positions;
    private float x, y, angle, offsetx, offsety;
    
    public Mask(){
        this(null, 0, 0);
        
    }
    
    public Mask(ArrayList<Shape> a)
    {
        this(a, 0, 0);
    }
    
    public Mask(ArrayList<Shape> a, float offsetx, float offsety)
    {
        positions = new ArrayList<Point>();
        if(a!=null)
        {
            mask = new ArrayList<Shape>(a);
            for(Shape s: mask)
            {
                positions.add(new Point(s.getX() - x, s.getY() - y));
            }
        }else
        {
            mask = new ArrayList<Shape>();
        }
        x = y = angle = 0;
        setOffset(offsetx, offsety);
    }
    
    public void add(Shape s){
        mask.add(s);
        positions.add(new Point(s.getX() - x, s.getY() - y));
    }
    
    public Shape get(int i){
        return mask.get(i);
    }
    
    public void setX(float x){
        this.x = x;
        
        int c = 0;
        for(Shape s : mask)
            s.setX(x - offsetx + positions.get(c++).getX());
        
        setAngle(angle);
    }
    
    public float getX(){
        return x;
    }
    
    public void setY(float y){
        this.y = y;
        
        int c = 0;
        for(Shape s : mask)
            s.setY(y - offsety + positions.get(c++).getY());
        
        setAngle(angle);
    }
    
    public float getY(){
        return y;
    }
    
    public void setLocation(float x, float y){
        setX(x);
        setY(y);
    }
    
    public void setAngle(float angle){
        angle = -angle;
        
        float da = angle - this.angle;
        this.angle = angle;

        int c = 0;
        for(Shape s : mask){
            s.setAngle(s.getAngle() - da);
            //a_x2 = (float) (Math.cos(angle) * (a_x - b_x) - Math.sin(angle) * (a_y - b_y) + b_x);
            s.setX((float)(Math.cos(angle) * (positions.get(c).getX()-offsetx) - Math.sin(angle) * (positions.get(c).getY()-offsety) + x));
            //(Math.sin(angle) * (a_x - b_x) + Math.cos(angle) * (a_y - b_y) + b_y);
            s.setY((float)(Math.sin(angle) * (positions.get(c).getX()-offsetx) + Math.cos(angle) * (positions.get(c).getY()-offsety) + y));
            c++;
        }
        
    }
    
    public float getAngle(){
        return angle;
    }
    
    public void setOffsetX(float offsetx){

        this.offsetx = offsetx;

    }
    
    public float getOffsetx(){
        return offsetx;
    }
    
    public void setOffsetY(float offsety){
        this.offsety = offsety;
    }
    
    public float getOffsety(){
        return y;
    }
    
    public void setOffset(float offsetx, float offsety){
        setOffsetX(offsetx);
        setOffsetY(offsety);
    }
    
    public Iterator iterator() {
        return mask.iterator();
    }
    
    public void draw(Graphics g){
        for(Shape s: mask) s.draw(g);
        
        g.setColor(Color.WHITE);
        
        g.drawLine((int) x, (int) y-10, (int) x, (int) y+10);
        g.drawLine((int) x-10, (int) y, (int) x+10, (int) y);
        
        g.drawLine((int)( x - offsetx), (int)( y - offsety-10 ), (int)( x - offsetx), (int)( y - offsety+10 ));
        g.drawLine((int)( x - offsetx-10 ), (int)( y - offsety), (int)( x - offsetx+10 ), (int)( y - offsety));
    }
}

class Point{
    private float x, y;
    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }
    float getX(){
        return x;
    }
    float getY(){
        return y;
    }
    void setX(float x){
        this.x = x;
    }
    void setY(float y){
        this.y = y;
    }
    
}
