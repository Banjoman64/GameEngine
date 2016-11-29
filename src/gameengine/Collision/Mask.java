package gameengine.Collision;

import java.util.ArrayList;
import java.util.Iterator;

public class Mask implements Iterable{
    public ArrayList<Shape> mask;
    
    public Mask(){
        mask = new ArrayList<Shape>();
    }
    
    public Mask(ArrayList<Shape> a){
        mask = new ArrayList<Shape>(a);
    }
    
    public void add(Shape a){
        mask.add(a);
    }
    
    public Shape get(int i){
        return mask.get(i);
    }

    @Override
    public Iterator iterator() {
        return mask.iterator();
    }
}
