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
        //SoundLine s = new SoundLine("Jump Radio EQ", 0.7f);
        
        new Rectangle(3.14f/4.0f, 2, 2, 1, 1);
        
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
