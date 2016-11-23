package gameengine.Collision;

import java.lang.Math;

public class Collisions {
    //class used to determine collisions between rotated rectangles and circles.
    //class uses the mask class, which contains circle and rectangles class instances
    
    //general case
    public static boolean collision(Shape a, Shape b)
    {
        if(a.getClass() == Circle.class)
        {
            if(b.getClass() == Circle.class)
                return collision((Circle)a, (Circle)b);
            return collision((Circle)a, (Rectangle)b);
        }
        else
        {
            if(b.getClass() == Rectangle.class)
                return collision((Rectangle) a, (Rectangle) b);
            return collision((Rectangle) a, (Circle) b);
        }
    }
    
    //circle circle collision
    public static boolean collision(Circle a, Circle b)
    {
        System.out.println("circle circle collision");
        if( distance(a.x, a.y, b.x, b.y) < (a.radius+b.radius))
            return true;
        return false;
    }
    
    //rectangle rectangle collision
    public static boolean collision(Rectangle a, Rectangle b)
    {
        System.out.println("rectangle rectangle collision");
        return true;
    }
    
    //circle and rectangle collision
    public static boolean collision(Circle a, Rectangle b)
    {
        System.out.println("circle rectangle collision");
        return true;
    }
    
    public static boolean collision(Rectangle a, Circle b)
    {
        return collision(b, a);
    }
    
    
    
    
    //2 Circles
    
    public static float distance(float x1, float y1, float x2, float y2){
        float a = x2-x1;
        float b = y2-y1;
        
        double c = Math.sqrt((a*a)+(b*b));
        
        return (float) c;
    }
    
}
