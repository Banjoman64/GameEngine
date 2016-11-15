/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;
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
        Sound s = new Sound("freeze");
        
        TimeUnit.SECONDS.sleep(2);
        s.loop(-1);
        }catch(Exception e){}
        
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
