/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Graphics;
import java.awt.Point;
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
    static List<GameObject> objectList;
    static Point offset = new Point(0,0);
    protected double x, y;
    protected double depth;

    protected double width, height;
    protected List<String> tags = new ArrayList<String>();

    public abstract void step();
    public abstract void draw(Graphics g);
    
    public GameObject()
    {
        this.x = this.y = 0;
        this.width = this.height = 0;
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
    }
    public void setY(double y){
        this.y = y;
    }
    public void setWidth(double width){
        this.width = width;
    }
    public void setHeight(double height){
        this.height = height;
    }
    
    public void drawSprite(Graphics g, BufferedImage sprite, int x, int y)
    {
        g.drawImage(sprite, (int)(x)-offset.x, (int)(y)-offset.y, null);
    }
    
    public void drawText(Graphics g, String s, int x, int y)
    {
        g.drawString(s, x-offset.x, y-offset.y);
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
        if(x < o.getX()+o.getWidth()  &&  x> o.getX()  &&  y < o.getY()+o.getHeight()  &&  y> o.getY())
            return o;
        return null;
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
    //CollisionSquare
    public GameObject collisionSquare(String s){
        GameObject result;
        for(GameObject o : objectList){
            if(o.tags.contains(s)){
                result = collisionSquare(o);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }
    
    

    public GameObject collisionSquare(Class c){
        GameObject result;
        for(GameObject o : objectList){
            if(c.isInstance(o)){
                result = collisionSquare(o);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }

    public GameObject collisionSquare(GameObject o){
        //if we're not the same instance
        if(!(o.equals(this))){
            if(x < o.getX()+o.getWidth()  &&  x + width > o.getX()  &&  y < o.getY()+o.getHeight()  &&  y + height > o.getY()){
                return o;
            }else{
                return null;
            }
        }
        return null;
    }
    
    //CollisionSquareList****************************************************************
    public List<GameObject> collisionSquareList(String s){
        List<GameObject> objList = new ArrayList<GameObject>();
        GameObject result;
        for(GameObject o : objectList){
            if(o.tags.contains(s)){
                result = collisionSquare(o);
                if(result != null){
                    objList.add(result);
                }
            }
        }
        return objList;
    }

    public List<GameObject> collisionSquareList(Class c){
        List<GameObject> objList = new ArrayList<GameObject>();
        GameObject result;
        for(GameObject o : objectList){
            if(c.isInstance(o)){
                result = collisionSquare(o);
                if(result != null){
                    objList.add(result);
                }
            }
        }
        return objList;
    }
    
    //placeCollisionSquare**************************************************************
    public GameObject placeCollisionSquare(Class c, double x, double y){
        double lastX = this.x;
        double lastY = this.y;
        this.x = x;
        this.y = y;
        GameObject ret = collisionSquare(c);
        this.x = lastX;
        this.y = lastY;
        return ret;
    }

    public GameObject placeCollisionSquare(String s, double x, double y){
        double lastX = this.x;
        double lastY = this.y;
        this.x = x;
        this.y = y;
        GameObject ret = collisionSquare(s);
        this.x = lastX;
        this.y = lastY;
        return ret;
    }
    
    //placeCollisionSquareList***************************************************
     public List<GameObject> placeCollisionSquareList(String s){
        double lastX = this.x;
        double lastY = this.y;
        this.x = x;
        this.y = y;
        List<GameObject> objList = collisionSquareList(s);
        this.x = lastX;
        this.y = lastY;
        return objList;
    }

    public List<GameObject> placeCollisionSquareList(Class c){
        double lastX = this.x;
        double lastY = this.y;
        this.x = x;
        this.y = y;
        List<GameObject> objList = collisionSquareList(c);
        this.x = lastX;
        this.y = lastY;
        return objList;
    }

    public static void setOffset(Point p)
    {
        offset = p;
    }

    @Override
    public int compareTo(GameObject t) {
        return (int) (this.depth - t.depth);
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
