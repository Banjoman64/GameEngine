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
        //calculate corner coordinates, axis vectors, and start angles
        update();
    }
    
    //calculate the angle from the x axis to each corner of the unrotated 
    //rectangle
    public void update(){
        //distance from the center to each corner
        distance = Collisions.distance(0, 0, width/2, height/2);
        
        //start angles represents the angle from the x axis to a line pointing 
        //to each cornerfor an unrotated rectangle. Used for calculating the 
        //position of each corner after rotations
        startAngle = new double[4];
        //up right
        startAngle[0] = Math.atan(((double)height/2) / ((double)width/2));// + Math.PI/2;
        //up left
        startAngle[1] = Math.PI + Math.atan(((double)height/2) / (-(double)width/2));// + Math.PI/2;
        //bottom left
        startAngle[2] = -startAngle[1];
        //bottom right
        startAngle[3] = -startAngle[0];
        
        updateCorners();
    }
    
    //calculate the coordinates of each corner of the rectangle
    //and calculate the axis vectors used for calculating collisions
    public void updateCorners()
    {
        
        //calculate the corner coordinates
        corners = new float[4][2];
        for( int i = 0 ; i < 4 ; i++ )
        {
            corners[i][0] = x + (float)(distance*Math.cos(-angle+startAngle[i]));
            corners[i][1] = y + (float)(distance*Math.sin(-angle+startAngle[i]));
        }
        
        
        //calculate the axis vectors
        vectors = new float[2][2];
        vectors[0][0] = corners[0][0] - corners[1][0];
        vectors[0][1] = corners[0][1] - corners[1][1];
        vectors[1][0] = corners[1][0] - corners[2][0];
        vectors[1][1] = corners[1][1] - corners[2][1];
        
    }
    
    public void setAngle(float a)
    {
        angle = a;
        //changing the angle means that the corner coordinates need to be
        //updated
        updateCorners();
    }
    
    public void setWidth(int w)
    {
        width = w;
        //changing the width of the rectangle means that the start angles need
        //to be updated
        update();
    }
    
    public void setHeight(int h)
    {
        height = h;
        //changing the height of the rectangle means that the start angles need
        //to be updated
        update();
    }
    
    public void setLocation(float x, float y)
    {   
        setX(x);
        setY(y);
    }
    
    public void setX(float x) {
        if(this.x != x)
        {
            float dx = x - this.x;
            this.x = x;
            //after changing the x value, we must update our corner coordniates as well
            for(int i = 0 ; i < corners.length ; i++)
                corners[i][0] += dx;
        }
    }

    public void setY(float y) {
        if(this.y != y)
        {
            float dy = y - this.y;
            this.y = y;
            //after changing the y value, we must update our corner coordniates as well
            for(int i = 0 ; i < corners.length ; i++)
                corners[i][1] += dy;
        }
    }
    
    public float[][] getVectors()
    {
        return vectors;
    }
    
    public float[][] getCorners()
    {
        return corners;
    }
    
    //draw the rectangle
    public void draw(Graphics g)
    {   
        //draw the rectangle
        Polygon rectangle = new Polygon();
        for( int i = 0 ; i < 4 ; i++ )
        {
            rectangle.addPoint((int)corners[i][0], (int)corners[i][1]);
        }
        g.fillPolygon(rectangle);
        
        //draw points at the corners of the rectangle
        g.setColor(Color.YELLOW);
        for( int i = 0 ; i < 4 ; i++ ){
            g.fillOval((int)corners[i][0]-5/2, (int)corners[i][1]-5/2, 5, 5);
            g.drawString(Integer.toString(i), (int)corners[i][0], (int)corners[i][1]);
            
        }
        
        //draw the axis of the rectangle
        g.setColor(Color.RED);
        g.drawLine((int)x, (int)y, (int)(x+vectors[0][0]), (int)(y+vectors[0][1]));

        g.setColor(Color.BLUE);
        g.drawLine((int)x, (int)y, (int)(x+vectors[1][0]), (int)(y+vectors[1][1]));
        
        //draw a cross at the center of the rectangle
        g.setColor(Color.red);
        g.drawLine((int)x,(int)y-5,(int)x,(int)y+5);
        g.drawLine((int)x-5,(int)y,(int)x+5,(int)y);
        
        //draw the bounds of the rectangle
        g.setColor(Color.BLACK);
        float[] b = new float[4];
        b = getBounds();
        g.drawRect((int)b[2], (int)b[1], (int)(b[0] - b[2]), (int)(b[3] - b[1]));
    } 

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }
    
    //Get the bounds of the rectangle. The bounds are the leftmost, rightmost,
    //upper, and lower coordinates. These values are used for approximating
    //collisions before doing more detailed collisions.
    //returns a float array
    //bounds[0] = right bound
    //bounds[1] = upper bound
    //bounds[2] = left bound
    //bounds[3] = lower bound
    public float[] getBounds(){
        float[] bounds = new float[4];
        bounds[0] = corners[0][0];
        bounds[1] = corners[0][1];
        bounds[2] = corners[0][0];
        bounds[3] = corners[0][1];
        
        for(int i  = 1 ; i < 4 ; i++){
            if(corners[i][0] > bounds[0])
                bounds[0] = corners[i][0];
            if(corners[i][0] < bounds[2])
                bounds[2] = corners[i][0];
            if(corners[i][1] < bounds[1])
                bounds[1] = corners[i][1];
            if(corners[i][1] > bounds[3])
                bounds[3] = corners[i][1];
        }
            
        
        return bounds;
    }
}
