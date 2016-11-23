package gameengine.Collision;

import java.lang.Math;

public class Rectangle implements Shape{
    public float angle, x, y;
    public int width, height;
    public float[][] corners;
    public final float distance;
    public final double[] startAngle;
    
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
        distance = Collisions.distance(0, 0, width/2, height/2);
        
        startAngle = new double[4];
        startAngle[0] = Math.atan((-(double)height/2) / (-(double)width/2)) + Math.PI/2;
        startAngle[1] = Math.atan((-(double)height/2) / ((double)width/2)) + Math.PI/2;
        startAngle[2] = -startAngle[1];
        startAngle[3] = -startAngle[0];
        
        for( int i = 0 ; i < 4 ; i++ ){
            corners[i][0] = x + (float)(distance*Math.cos(angle+startAngle[i]));
            corners[i][1] = y + (float)(distance*Math.sin(angle+startAngle[i]));
        }
        
        for(int i = 0 ; i < 4 ; i++){
            System.out.println(startAngle[i]/Math.PI);
            System.out.println("C" + i + ": (" + corners[i][0] + "," + corners[i][1] + ")");
        }
    }
    
}
