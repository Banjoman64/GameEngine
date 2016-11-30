package gameengine.Collision;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

public class Collisions {
    private static float[][] aLast;
    private static float[][] bLast;
    
    public static void draw(Graphics g){
        g.setColor(Color.BLUE);
        if( aLast != null )
            for( int i = 0 ; i < 4 ; i++ )
            {
                g.drawOval(50+(int)aLast[i][0]-5/2, 50+ (int)aLast[i][1]-5/2, 5, 5);
            }
    }
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
        
        
        float[][] aVectors = a.getVectors();
        float[][] bVectors = b.getVectors();
        
        float[][] vectors = new float[4][2];
        System.arraycopy(a.getVectors(), 0, vectors, 0, 2);
        System.arraycopy(b.getVectors(), 0, vectors, 2, 2);
        
        float[][] aCorners = a.getCorners();
        float[][] bCorners = a.getCorners();
        
        for( int currentAxis = 0 ; currentAxis < 4 ; currentAxis++ )
        {
        
            float[][] aProjections = new float[4][2];
            float[][] bProjections = new float[4][2];
            
            //create all projections
            for( int i = 0 ; i < 4 ; i++ )
            {
                float[] proj = vectorProj(aCorners[i][0], aCorners[i][1], vectors[currentAxis][0], vectors[currentAxis][1]);
                aProjections[i][0] = proj[0];
                aProjections[i][1] = proj[1];
                
                aLast = aProjections.clone();
                
                proj = vectorProj(bCorners[i][0], bCorners[i][1] , vectors[currentAxis][0], vectors[currentAxis][1]);
                bProjections[i][0] = proj[0];
                bProjections[i][1] = proj[1];
                
                bLast = bProjections.clone();
            }
            
            float aMin, aMax, bMin, bMax;
            aMin = aMax = aProjections[0][0] * vectors[currentAxis][0] + aProjections[0][1] * vectors[currentAxis][1];
            bMin = bMax = bProjections[0][0] * vectors[currentAxis][0] + bProjections[0][1] * vectors[currentAxis][1];

            for( int i = 1 ; i < 4 ; i++ )
            {
                float aCurrent = aProjections[i][0] * vectors[currentAxis][0] + aProjections[i][1] * vectors[currentAxis][1];
                float bCurrent = bProjections[i][0] * vectors[currentAxis][0] + bProjections[i][1] * vectors[currentAxis][1];

                if(aCurrent < aMin)         aMin = aCurrent;
                else if(aCurrent > aMax)    aMax = aCurrent;
                
                if(bCurrent < bMin)         bMin = bCurrent;
                else if(bCurrent > bMax)    bMax = bCurrent;
            }
            
            if(!(bMin <= aMax || bMax >= aMin)) return false;
        }
        return true;
    }
    
    private static float[] vectorProj(float cornerX, float cornerY, float axisX, float axisY)
    {
        float[] projection = new float[2];
        projection[0] = ((cornerX*axisX) + (cornerY*axisY))/((axisX*axisX)+(axisY*axisY)*axisX);
        projection[1] = ((cornerX*axisX) + (cornerY*axisY))/((axisX*axisX)+(axisY*axisY)*axisY);
        return projection;
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
    
    
    
    
    //
    
    public static float distance(float x1, float y1, float x2, float y2){
        float a = x2-x1;
        float b = y2-y1;
        
        double c = Math.sqrt((a*a)+(b*b));
        
        return (float) c;
    }
    
}
