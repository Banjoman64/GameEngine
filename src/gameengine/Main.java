/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;
import gameengine.Collision.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Random;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author wyatt
 */
public class Main 
{
    public static void main(String [] args)
    {
        try{
        SoundLine s = new SoundLine("chimer", 1.0f);
        
        }catch(Exception e){}
        
        System.out.println(Collisions.collision(new Rectangle(0, 4, 2, 0, 0),new Rectangle(0, 4, 2, 20, 20)));
        
        if(true)
        {
            new GameEngine();
        }
        else
        {
            new LevelBuilder();
        }
        
    }
    
    
}
