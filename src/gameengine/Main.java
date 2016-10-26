/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;
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
        
        tree.printTree();
        
        tree.delete(75);
        
        tree.printTree();
        tree.delete(85);
        
        tree.printTree();

        
        
        if(false)
        {
            new GameEngine();
        }else
        {
            new LevelBuilder();
        }
        
    }
    
    
}
