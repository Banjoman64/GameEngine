package gameengine.Collision;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Rectangle implements Shape{
    public float angle, x, y;
    public int width, height;
    public float[][] corners;
    public float distance;
    public double[] startAngle;
    public float [][] vectors;
    
    public Rectangle(float a, int w, int h){
        this(a,w,h,0,0);
    }
    
    
    public Rectangle(float a, int w, int h, float x, float y){
        this.angle = a;
        this.width = w;
        this.height = h;
        this.x = x;
        this.y = y;
        
        
        
        corners = new float[4][2];
        update();
        
        for(int i = 0 ; i < 4 ; i++){
            System.out.println(startAngle[i]/Math.PI);
            System.out.println("C" + i + ": (" + corners[i][0] + "," + corners[i][1] + ")");
        }
    }
    
    public void update(){
        distance = Collisions.distance(0, 0, width/2, height/2);
        
        startAngle = new double[4];
        startAngle[0] = Math.atan(((double)height/2) / ((double)width/2));// + Math.PI/2;
        startAngle[1] = Math.PI + Math.atan(((double)height/2) / (-(double)width/2));// + Math.PI/2;
        startAngle[2] = -startAngle[1];
        startAngle[3] = -startAngle[0];
        
        updateCorners();
    }
    
    public void updateCorners()
    {
        corners = new float[4][2];
        for( int i = 0 ; i < 4 ; i++ )
        {
            corners[i][0] = x + (float)(distance*Math.cos(angle+startAngle[i]));
            corners[i][1] = y + (float)(distance*Math.sin(angle+startAngle[i]));
        }
        
        vectors = new float[2][2];
        for( int i = 0 ; i < 2 ; i++ )
        {
           vectors[i][0] = corners[i][0] - corners[i+1][0];
           vectors[i][1] = corners[i][1] - corners[i+1][1];
        }
    }
    
    public void setAngle(float a)
    {
        angle = a;
        updateCorners();
    }
    
    public void setWidth(int w)
    {
        width = w;
        update();
    }
    
    public void setHeight(int h)
    {
        height = h;
        update();
    }
    
    public void setLocation(float x, float y)
    {
        if(this.x == x && this.y == y)
        {
            return;
        }
        
        this.x = x;
        this.y = y;
        updateCorners();
    }
    
    public float[][] getVectors()
    {
        return vectors;
    }
    
    public float[][] getCorners()
    {
        return corners;
    }
    
    public void draw(Graphics g, Color rectColor)
    {   
        g.setColor(rectColor);
        Polygon rectangle = new Polygon();
        for( int i = 0 ; i < 4 ; i++ )
        {
            rectangle.addPoint((int)corners[i][0], (int)corners[i][1]);
        }
        g.fillPolygon(rectangle);
        g.setColor(Color.YELLOW);
        for( int i = 0 ; i < 4 ; i++ ){
            g.fillOval((int)corners[i][0]-5/2, (int)corners[i][1]-5/2, 5, 5);
            
        }
        g.setColor(Color.RED);
        g.drawLine((int)x, (int)y, (int)(x+vectors[0][0]), (int)(y+vectors[0][1]));
        g.setColor(Color.BLUE);
        g.drawLine((int)x, (int)y, (int)(x+vectors[1][0]), (int)(y+vectors[1][1]));
    }
    
}
