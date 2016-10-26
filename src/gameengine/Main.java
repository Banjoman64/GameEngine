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
        
        
                
        tree.addNode(50);
        tree.addNode(25);
        tree.addNode(75);
        tree.addNode(15);
        tree.addNode(35);
        tree.addNode(65);
        tree.addNode(85);
        tree.addNode(14);
        tree.addNode(16);
        
        tree.printTree();
        
        tree.delete(75);
        
        tree.printTree();
        tree.delete(85);
        
        tree.printTree();

        /*
        
        if(true)
        {
            new GameEngine();
        }else
        {
            new LevelBuilder();
        }
        */
    }
    
    
}
