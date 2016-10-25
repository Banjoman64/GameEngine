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
        
        AVLTree<Integer> tree = new AVLTree<Integer>();

        
                
        tree.addNode(15);
        tree.addNode(22);
        tree.addNode(53);
        tree.addNode(91);
        tree.addNode(2);
        tree.addNode(4);
        tree.addNode(72);
        tree.addNode(85);
        tree.addNode(18);
        tree.addNode(81);
        tree.addNode(40);
        tree.addNode(60);
        tree.addNode(95);
        tree.addNode(88);
        tree.addNode(33);
        
        tree.printTree();
        
        
        
        
        if(true)
        {
            new GameEngine();
        }else
        {
            new LevelBuilder();
        }
    }
    
    
}
