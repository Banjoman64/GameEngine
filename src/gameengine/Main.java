/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Random;
/**
 *
 * @author wyatt
 */
public class Main 
{
    public static void main(String [] args)
    {
        
        AVLTree<Integer> tree = new AVLTree<Integer>();

        Random rand = new Random();
        
        
                
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(15);
        tree.add(35);
        tree.add(65);
        tree.add(85);
        tree.add(14);
        tree.add(16);
        
        for(int i : tree)
            System.out.println(i);
        
        AVLTree<GameObject> tree2 = new AVLTree<GameObject>();
        
        tree2.add(new Ball(0,0));
        tree2.add(new Ball(0,0));
        tree2.add(new Wall(0,0));
        tree2.add(new Ball(0,0));
        
        for(GameObject o : tree2)
            System.out.println(o.compareValue());
        
        GameObject a, b;
        a = new Wall(0,0);
        b = new Ball(0,0);
        
        System.out.println(a.compareTo(b));
        
        
        

        
        
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
