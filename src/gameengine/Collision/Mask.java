package gameengine.Collision;

import java.util.ArrayList;
import java.util.Iterator;

public class Mask implements Iterable{
    public ArrayList<Shape> mask;
    public float x, y, angle;
    
    public Mask(){
        this(null);
    }
    
    public Mask(ArrayList<Shape> a){
        mask = new ArrayList<Shape>(a);
        x = y = angle = 0;
    }
    
    public void add(Shape a){
        mask.add(a);
    }
    
    public Shape get(int i){
        return mask.get(i);
    }
    
    public void setX(float x){
        float dx = x - this.x;
        
        this.x = x;
        
        for(Shape s : mask)  s.setX(s.getX() + dx);
    }
    
    public void setY(float y){
        float dy = y - this.y;
        
        this.y = y;
        
        for(Shape s : mask)  s.setY(s.getY() + dy);
    }
    
    public void setLocation(float x, float y){
        setX(x);
        setY(y);
    }
    
    public void setAngle(float angle){
        
    }

    public Iterator iterator() {
        return mask.iterator();
    }
}
