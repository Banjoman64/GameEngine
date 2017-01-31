/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;
/**
 *
 * @author wyatt
 */
public class Main 
{
    public static void main(String [] args)
    {    
        //set to true to start the test room, false to start the gui level builder
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
