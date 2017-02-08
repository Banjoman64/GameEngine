package gameengine.Collision;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

public class Mask implements Iterable{
    public ArrayList<Shape> mask;
    public ArrayList<Point> positions;
    private float x, y, angle, offsetx, offsety;
    public float[] bounds;
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
        bounds = new float[4];
        getBounds();
    }
    
    public void add(Shape s){
        mask.add(s);
        positions.add(new Point(s.getX() - x, s.getY() - y));
        getBounds();
    }
    
    public void getBounds(){
        float[] tempBounds;
        if(mask.isEmpty()){
            tempBounds = new float[4];
            tempBounds[0] = tempBounds[2] = x;
            tempBounds[1] = tempBounds[3] = y;
            bounds = tempBounds;
            return;
        }
        
        float[] maxBounds = mask.get(0).getBounds();
        for(int i = 1 ; i < mask.size() ; i++){
            tempBounds = mask.get(i).getBounds();
            if(tempBounds[0] > maxBounds[0])
                maxBounds[0] = tempBounds[0];
            if(tempBounds[1] < maxBounds[1])
                maxBounds[1] = tempBounds[1];
            if(tempBounds[2] < maxBounds[2])
                maxBounds[2] = tempBounds[2];
            if(tempBounds[3] > maxBounds[3])
                maxBounds[3] = tempBounds[3];
        }
        
        bounds = maxBounds;
    }
    
    public Shape get(int i){
        return mask.get(i);
    }
    
    public void setX(float x){
        float dx = x - this.x;
        this.x = x;
        
        int c = 0;
        for(Shape s : mask)
            s.setX(x - offsetx + positions.get(c++).getX());
        
        setAngle(angle);
        bounds[0]+=dx;
        bounds[2]+=dx;
    }
    
    public float getX(){
        return x;
    }
    
    public void setY(float y){
        float dy = y - this.y;
        this.y = y;
        
        int c = 0;
        for(Shape s : mask)
            s.setY(y - offsety + positions.get(c++).getY());
        
        setAngle(angle);
        getBounds();
        bounds[1]+=dy;
        bounds[3]+=dy;
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
            s.setX((float)(Math.cos(angle) * (positions.get(c).getX()-offsetx) - Math.sin(angle) * (positions.get(c).getY()-offsety) + x));
            s.setY((float)(Math.sin(angle) * (positions.get(c).getX()-offsetx) + Math.cos(angle) * (positions.get(c).getY()-offsety) + y));
            c++;
        }
        getBounds();
    }
    
    public float getAngle(){
        return angle;
    }
    
    public void setOffsetX(float offsetx){
        this.offsetx = offsetx;
        getBounds();
    }
    
    public float getOffsetx(){
        return offsetx;
    }
    
    public void setOffsetY(float offsety){
        this.offsety = offsety;
        getBounds();
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
        
        g.setColor(Color.WHITE);
        if(bounds!=null)
            g.drawRect((int)bounds[2], (int)bounds[1], (int)(bounds[0] - bounds[2]), (int)(bounds[3] - bounds[1]));
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
