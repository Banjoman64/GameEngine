/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author wyatt
 */
public class Sprite {
    static private BufferedImage spriteSheet = null;
    static private int tileWidth = 32,tileHeight = 32;
    
    public static BufferedImage loadSprite(String fileName){
        BufferedImage sprite = null;
        try{
            sprite = ImageIO.read(new File(fileName+ ".png"));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return sprite;
    }
    
    public static BufferedImage getSprite(int xGrid, int yGrid){
        if(spriteSheet == null){
            spriteSheet = loadSprite("spriteSheet");
        }
        
        return spriteSheet.getSubimage(xGrid*tileWidth, yGrid*tileHeight, tileWidth, tileHeight);
    }
    
    public static void setTileWidth(int w){
        tileWidth = w;
    }
    
    public static void setTileHeight(int h){
        tileHeight = h;
    }
}
