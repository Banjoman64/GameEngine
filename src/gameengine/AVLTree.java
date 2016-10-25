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
public class AVLTree<T extends Comparable <T>> {
    //declare/initialize the top node object to null
    Node<T> root = null;
    
    /***************************************************************************
    * Takes a piece of data of type T(declared at tree declaration) and calls
    * addRecursive to add a new node with that data to the tree. Test!
    ***************************************************************************/
    public void addNode(T data)
    {
        //If there is no root, make the new node the root and return
        if (root == null)
        {
            root = new Node<T>(data);
            return;
        }
        
        //recursively add the node
        addRecursive(data, this.root);
    }
    
    /***************************************************************************
    * Called by addNode. Recursively finds the the correct position of the new
    * node
    ***************************************************************************/
    private void addRecursive(T data, Node<T> top){
        if(data.compareTo(top.getData())<=0)
        {
            //left
            if(top.getLeft() == null)
            {
                Node<T> n = new Node<T>(data);
                top.setLeft(n);
                n.setRoot(top);
                n.setDirection(-1);
                
                fixTree(0, n, null, null);
            }
            else addRecursive(data, top.getLeft());
        }
        else
        {
            if(top.getRight() == null)
            {
                Node<T> n = new Node<T>(data);
                top.setRight(n);
                n.setRoot(top);
                n.setDirection(1);
                
                fixTree(0, n, null, null);
            }
            else addRecursive(data, top.getRight());
            //right
        }
    }
    
    private void fixTree(int height, Node<T> n, Node<T> child, Node<T> grandchild){
        if(n == null) return;

        
        n.setHeight(height);
        if(Math.abs(( ((n.getLeft() == null)? -1 : n.getLeft().getHeight() ) - ((n.getRight() == null)? -1 : n.getRight().getHeight())))>1)
        {
            rebalance(n, child, grandchild);
            return;
            
        }
        fixTree(height+1, n.root, n, child);
    }
    
    /*******************************************************************************
    *****Takes a node and returns the height of the node according to the height of it's children
    *******************************************************************************/
    private int calcHeight(Node<T> n){
        Node<T> left = n.getLeft();
        Node<T> right = n.getRight();
        int heightLeft = (left==null)? -1 : n.getLeft().getHeight();
        int heightRight = (right==null)? -1 : n.getRight().getHeight();
        return  Math.max(heightLeft, heightRight)+1;
    }
    
    private void rebalance(Node<T> n, Node<T> child, Node<T> grandchild)
    {
        
        
        if(child.getDirection() == grandchild.getDirection())
        {
            Node<T> upperRoot = n.getRoot();
            if(child.getDirection() == 1)
            {
                //right right
                //N right becomes child left
                Node<T> childLeft = child.getLeft();
                
                n.setRight(childLeft);
                
                if(childLeft!=null)
                {
                    childLeft.setRoot(n);
                    childLeft.setDirection(1);
                }
                //recalculate N height
                
                //set child to the new root
                if (n.direction == 1)
                    upperRoot.right = child;
                else if (n.direction == -1)
                    upperRoot.left = child;
                else
                    root = child;
                child.setDirection(n.direction);
                child.setRoot(upperRoot);
                
                //then N becomes new child left
                    
                child.setLeft(n);
                n.setRoot(child);
                n.setDirection(-1);
                
                //then recalcutlate child height
                //then move up the tree and fix the remaining heights
                
                n.setHeight(calcHeight(n));
                child.setHeight(calcHeight(child));
                
                fixTree(child.getHeight()+1,child.getRoot(),null,null);
                
            }
            else
            {
                //left left
                Node<T> childRight = child.getRight();
                
                n.setLeft(childRight);
                
                if(childRight!=null)
                {
                    childRight.setRoot(n);
                    childRight.setDirection(-1);
                }
                
                
                
                if (n.direction == 1)
                    upperRoot.setRight(child);
                else if (n.direction == -1)
                    upperRoot.setLeft(child);
                else
                    root = child;
                child.setDirection(n.direction);
                child.setRoot(upperRoot);
                
                child.setRight(n);
                n.setRoot(child);
                n.setDirection(1);
                
                
                n.setHeight(calcHeight(n));
                child.setHeight(calcHeight(child));
                
                fixTree(child.getHeight()+1,child.getRoot(),null,null);
            }
        }
        else
        {
            if(child.getDirection() == 1)
            {
                //right left
                //A = N
                //C = child
                //D = grandchild
                
                //Right of grandchild becomes left of child
                //Right of N becomes grandchild
                //Right of grandchild becoms child
                
                Node<T> grandchildRight = grandchild.getRight();
                child.setLeft(grandchildRight);
                
                if(grandchildRight!=null)
                {
                    grandchildRight.setDirection(-1);
                    grandchildRight.setRoot(child);
                }
                
                n.setRight(grandchild);
                grandchild.setDirection(1);
                grandchild.setRoot(n);
                
                grandchild.setRight(child);
                child.setDirection(1);
                child.setRoot(grandchild);
                
                child.setHeight(calcHeight(child));
                grandchild.setHeight(calcHeight(grandchild));
                n.setHeight(calcHeight(n));
                
                rebalance(n, grandchild, child);
            }
            else
            {
                //left right
                Node<T> grandchildLeft = grandchild.getLeft();
                child.setRight(grandchildLeft);
                
                if(grandchildLeft!=null)
                {
                    grandchildLeft.setDirection(1);
                    grandchildLeft.setRoot(child);
                }
                
                n.setLeft(grandchild);
                grandchild.setDirection(-1);
                grandchild.setRoot(n);
                
                grandchild.setLeft(child);
                child.setDirection(-1);
                child.setRoot(grandchild);
                
                child.setHeight(calcHeight(child));
                grandchild.setHeight(calcHeight(grandchild));
                n.setHeight(calcHeight(n));
                
                rebalance(n, grandchild, child);
            }
        }
    }
    
    public void printTree(){
        printTreeRecursive(root);
        System.out.println();
    }
    
    public void printTreeRecursive(Node<T> top){
        if(top.getLeft()!=null)
            printTreeRecursive(top.getLeft());
        
        System.out.println(top.getData().toString() + " H(" + top.getHeight() + ")" + " D("+ top.getDirection() + ")");
        
        if(top.getRight()!=null)
            printTreeRecursive(top.getRight());
    }
}

//NODE CLASS
class Node<T extends Comparable <T>>{
    Node<T> left, right, root;
    T data;
    int height, direction;
    
    //CONSTRUCTOR
    
    Node(T data)
    {
        this.data = data;
        left = right = root = null;
        height = direction = 0;
    }
    
    //SETTERS
    
    public void setLeft(Node<T> n){
        left = n;
    }
    
    public void setRight(Node<T> n){
        right = n;
    }
    
    public void setRoot(Node<T> n){
        root = n;
    }
    
    public void setHeight(int h){
        height = h;
    }
    
    public void setDirection(int d){
        direction = d;
    }
    
    public void setData(T d){
        data = d;
    }
    
    //GETTERS
    
    public Node<T> getLeft(){
        return left;
    }
    
    public Node<T> getRight(){
        return right;
    }
    
    public Node<T> getRoot(){
        return root;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public T getData(){
        return data;
    }
    
}
