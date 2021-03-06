/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import static gameengine.Collision.Collisions.collision;
import gameengine.Collision.Mask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author wyatt
 */
abstract class GameObject implements Comparable<GameObject>, GameObjectInterface
{
    protected String className = "NoName";
    protected BufferedImage icon = Sprite.getSprite(2, 0);
    static KeyboardInput keyboard;
    static MouseInput mouse;
    static HashMap<Integer, GameObject> objectMap = new HashMap<Integer, GameObject>();
    static int nextId = 0;
    protected int id;
    static AVLTree<GameObject> objectList = new AVLTree<GameObject>();
    static Point offset = new Point(0,0);
    static Double xZoom = 1.0;
    static Double yZoom = 1.0;
    protected double x, y;
    protected int depth;
    protected Mask mask = null;

    protected double width, height;
    protected List<String> tags = new ArrayList<String>();

    public abstract void step();
    public abstract void draw(Graphics g);
    
    public GameObject()
    {
        this.x = this.y = 0;
        this.width = this.height = 0;
        this.depth = 1000;
        this.id = nextId;
        
        GameObject o = this;
        objectMap.put(this.id, o);
        
        nextId++;
    }
    
    public static void setInput(KeyboardInput k, MouseInput m)
    {
        keyboard = k;
        mouse = m;
    }
    
    public static void setZoom(double x, double y)
    {
        xZoom = x;
        yZoom = y;
    }
    
    public static void setZoomX(double x)
    {
        xZoom = x;
    }
    
    public static void setZoomY(double y)
    {  
        yZoom = y;
    }
        
    //Getters
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }

    //Setters
    public void setX(double x){
        this.x = x;
        if(mask!=null)
            mask.setX((float)x);
    }
    
    public void setY(double y){
        this.y = y;
        if(mask!=null)
            mask.setY((float)y);
    }
    public void setWidth(double width){
        this.width = width;
    }
    public void setHeight(double height){
        this.height = height;
    }
    
    public void drawSprite(Graphics g, BufferedImage sprite, int x, int y, int offsetx, int offsety, float angle)
    {
        Image img = (Image) sprite;
        double sin = Math.abs(Math.sin(angle)),
               cos = Math.abs(Math.cos(angle));
        
        int w = img.getWidth(null), h = img.getHeight(null);
        
        int neww = (int) Math.floor(w*cos + h*sin),
            newh = (int) Math.floor(h*cos + w*sin);
        
        BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bimg.createGraphics();
        
        g2.translate((neww-w)/2, (newh-h)/2);
        g2.rotate(angle, w/2, h/2);
        g2.drawRenderedImage((BufferedImage)img, null);
        g2.dispose();
        
        float drawLocationX = (float)(Math.cos(angle) * (-offsetx) - Math.sin(angle) * (-offsety) + x);
        float drawLocationY = (float)(Math.sin(angle) * (-offsetx) + Math.cos(angle) * (-offsety) + y);
        
        g.drawImage(bimg, (int)(drawLocationX -(neww/2) - offset.x/xZoom), (int)(drawLocationY - (newh/2) - offset.y/yZoom), null);
        //////////////////////////
        /*float drawLocationX = (float)(Math.cos(angle) * (x-offsetx) - Math.sin(angle) * (y-offsety) + x);
            //(Math.sin(angle) * (a_x - b_x) + Math.cos(angle) * (a_y - b_y) + b_y);
        float drawLocationY = (float)(Math.sin(angle) * (x-offsetx) + Math.cos(angle) * (y-offsety) + y);
        Image image;
        AffineTransform identity = new AffineTransform();
        
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform trans = new AffineTransform();
        trans.setTransform(identity);
        trans.rotate((double)angle);
        g2d.drawImage(image, trans, this);//(int)(drawLocationX-offset.x/xZoom), (int)(drawLocationY-offset.y/yZoom));*/
    }
    
    public void drawText(Graphics g, String s, int x, int y)
    {
        g.drawString(s, (int)(x-offset.x/xZoom), (int)(y-offset.y/yZoom));
    }
    
    //CollisionPoint
    public static GameObject collisionPoint(String s, int x, int y){
        GameObject result;
        for(GameObject o : objectList){
            if(o.tags.contains(s)){
                result = collisionPoint(o, x, y);
                if(result != null){
                    return result;
                }
            }
        }

    return null;
    }

    public static GameObject collisionPoint(Class c, int x, int y){
        GameObject result;
        for(GameObject o : objectList){
            if(c.isInstance(o)){
                result = collisionPoint(o, x, y);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }

    public static GameObject collisionPoint(GameObject o, int x , int y){
        //TO-DO add support for collision point to Mask.java
        if(collision(x, y, o.mask)){
            return o;
        }
        return null;
        /*if(x < o.getX()+o.getWidth()  &&  x> o.getX()  &&  y < o.getY()+o.getHeight()  &&  y> o.getY())
            return o;
        return null;*/
    }
    
    
    
    //CollisionPointList*********************************************************************
    public static List<GameObject> collisionPointList(String s, int x, int y){
        List<GameObject> objList = new ArrayList<GameObject>();
        GameObject result;
        for(GameObject o : objectList){
            if(o.tags.contains(s)){
                result = collisionPoint(o, x, y);
                if(result != null){
                    objList.add(result);
                }
            }
        }

    return objList;
    }

    public static List<GameObject> collisionPointList(Class c, int x, int y){
        List<GameObject> objList = new ArrayList<GameObject>();
        GameObject result;
        System.out.println(objectList);
        for(GameObject o : objectList){
            if(c.isInstance(o)){
                result = collisionPoint(o, x, y);
                if(result != null){
                    objList.add(result);
                }
            }
        }
        return objList;
    }
    //CollisionMask
    public GameObject collisionMask(String s){
        GameObject result;
        for(GameObject o : objectList){
            if(o.tags.contains(s)){
                result = collisionMask(o);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }
    
    

    public GameObject collisionMask(Class c){
        GameObject result;
        for(GameObject o : objectList){
            if(c.isInstance(o)){
                result = collisionMask(o);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }

    public GameObject collisionMask(GameObject o){
        //if we're not the same instance
        if(!(o.equals(this))){
            if(collision(this.mask, o.mask)){
                return o;
            }else{
                return null;
            }
        }
        return null;
    }
    
    //CollisionMaskList****************************************************************
    public List<GameObject> collisionMaskList(String s){
        List<GameObject> objList = new ArrayList<GameObject>();
        GameObject result;
        for(GameObject o : objectList){
            if(o.tags.contains(s)){
                result = collisionMask(o);
                if(result != null){
                    objList.add(result);
                }
            }
        }
        return objList;
    }

    public List<GameObject> collisionMaskList(Class c){
        List<GameObject> objList = new ArrayList<GameObject>();
        GameObject result;
        for(GameObject o : objectList){
            if(c.isInstance(o)){
                result = collisionMask(o);
                if(result != null){
                    objList.add(result);
                }
            }
        }
        return objList;
    }
    
    //placeCollisionMask**************************************************************
    public GameObject placeCollisionMask(Class c, double x, double y){
        double lastX = this.x;
        double lastY = this.y;
        setX(x);
        setY(y);
        GameObject ret = collisionMask(c);
        setX(lastX);
        setY(lastY);
        return ret;
    }

    public GameObject placeCollisionMask(String s, double x, double y){
        double lastX = this.x;
        double lastY = this.y;
        setX(x);
        setY(y);
        GameObject ret = collisionMask(s);
        setX(lastX);
        setY(lastY);
        return ret;
    }
    
    //placeCollisionMaskList***************************************************
     public List<GameObject> placeCollisionMaskList(String s){
        double lastX = this.x;
        double lastY = this.y;
        this.x = x;
        this.y = y;
        List<GameObject> objList = collisionMaskList(s);
        this.x = lastX;
        this.y = lastY;
        return objList;
    }

    public List<GameObject> placeCollisionMaskList(Class c){
        double lastX = this.x;
        double lastY = this.y;
        this.x = x;
        this.y = y;
        List<GameObject> objList = collisionMaskList(c);
        this.x = lastX;
        this.y = lastY;
        return objList;
    }

    public static void setOffset(Point p)
    {
        offset = p;
    }
    
    public long compareValue(){
        long val = this.depth;
        val = val<<32;
        val+=this.id;
        return val;
    }

    @Override
    public int compareTo(GameObject t) {
        if(this.compareValue() < t.compareValue())
            return -1;
        if(this.compareValue() > t.compareValue())
            return 1;
        return 0;
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }
    
    @Override
    public String getName() {
        return className;
    }

    
}
