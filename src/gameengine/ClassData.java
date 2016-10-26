/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

/**
 *
 * @author wyatt
 */
class ClassData{
    private String label;
    private String text;
    private BufferedImage icon;
    private String className;
    private Class<?> cla;
    private Object obj;
    private GameObjectInterface mobj;
    
    public ClassData(String pieceName){
        this(pieceName, 0, 0);
    }
    
    public ClassData(String pieceName, int x, int y){
        try{
            className = getPieceAddress(pieceName);   
            cla = Class.forName(className);
            obj = cla.getDeclaredConstructor(Integer.class, Integer.class).newInstance(x,y);
            mobj = (GameObjectInterface)obj;

            text =  mobj.getName();
            icon = mobj.getIcon();
            label = pieceName;
            
        }catch(Exception e){
            System.out.println("Couldn't find class " + pieceName);
            text = null;
            icon = null;
        }
    }
    
    private String getPieceAddress(String pieceName){
        return("gameengine."+pieceName);
    }
    
    public String getLabel(){
        return label;
    }
    
    public String getText(){
        return text;
    }
    
    public BufferedImage getIcon(){
        return icon;
    }
    
    public String getClassName(){
        return className;
    }
    
    public Class getClassType(){
        return cla;
    }
    
    public Object getObject(){
        return obj;
    }
    
    public GameObjectInterface getGameObject(){
        return mobj;
    }
    
    
    
    /*public GameObjectInterface getNewObject(int x,int y){
        try{
            obj = cla.getDeclaredConstructor(Integer.class, Integer.class).newInstance(x,y);
            mobj = (GameObjectInterface)obj;
            return mobj;
            
            //return oi;
        }catch(Exception e){
            System.out.println("woops");
            return null;
        }
    }*/
}
