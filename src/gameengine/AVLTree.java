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
        addNode(data, this.root);
    }
    
    /***************************************************************************
    * Called by addNode. Recursively finds the the correct position of the new
    * node
    ***************************************************************************/
    private void addNode(T data, Node<T> top){
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
            else addNode(data, top.getLeft());
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
            else addNode(data, top.getRight());
            //right
        }
    }
    
    private void fixTree(int height, Node<T> x, Node<T> y, Node<T> z){
        if(x == null) return;

        
        x.setHeight(calcHeight(x));
        
        if(balance(x) > 1)
        {
            Node<T> parent = x.getRoot();
            rebalance(x, y, z);
            fixHeight(parent);
            
            return;
            
        }
        fixTree(height+1, x.root, x, y);
    }
    
    private void fixHeight(Node<T> n)
    {
        if(n == null) return;
        
        n.setHeight(calcHeight(n));
        fixHeight(n.getRoot());
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
    
    private int balance(Node<T> n)
    {
        return Math.abs(( ((n.getLeft() == null)? -1 : n.getLeft().getHeight() ) - ((n.getRight() == null)? -1 : n.getRight().getHeight())));
    }
    
    public boolean contains(T data){
        return findNode(data) != null;
    }
    
    private Node<T> findNode(T data)
    {
        return findNode(data, this.root);
    }
    
    private Node<T> findNode(T data, Node<T> top)
    {
        if(top == null){
            return null;
        }
        if(data.compareTo(top.getData()) == 0){
            return top;
        }else if(data.compareTo(top.getData()) < 0){
            return findNode(data, top.getLeft());
        }else{
            return findNode(data, top.getRight());
        }
    }
    
    public boolean delete(T data)
    {
        Node<T> deleteNode = findNode(data);
        if(deleteNode == null) return false;
        delete(deleteNode);
        return true;
    }
    
    private void delete(Node<T> n)
    {
        Node<T> parent = n.getRoot();
        //2 children
        if(n.getLeft() != null && n.getRight() != null)
        {
            //complicated double child stuff
            //get replacement node
            Node<T> leftMost = leftMost(n.getRight());
            n.setData(leftMost.getData());
            delete(leftMost);
        }
        //only left child
        else if(n.getLeft() != null)
        {
            //replace with left node
            Node<T> left = n.getLeft();
            
            if(n.getDirection() == 0)
            {
                root = left;
                left.setDirection(0);
            }else if(n.getDirection() == -1)
            {
                parent.setLeft(left);
                left.setDirection(-1);
            }else if(n.getDirection() == 1)
            {
                parent.setRight(left);
                left.setDirection(1);
            }
            left.setRoot(parent);
            deleteFix(parent);
        }
        //only right child
        else if(n.getRight() != null)
        {
            //replace with right node
            Node<T> right = n.getRight();
            
            if(n.getDirection() == 0)
            {
                root = right;
                right.setDirection(0);
            }else if(n.getDirection() == -1)
            {
                parent.setLeft(right);
                right.setDirection(-1);
            }else if(n.getDirection() == 1)
            {
                parent.setRight(right);
                right.setDirection(1);
            }
            right.setRoot(parent);
            deleteFix(parent);
        }
        else
        {
            //no children
            //just delete

            if(n.getDirection() == 0)
            {
                root = null;
            }else if(n.getDirection() == -1)
            {
                parent.setLeft(null);
            }else if(n.getDirection() == 1)
            {
                parent.setRight(null);
            }
            deleteFix(parent);
        }
    }
    
    private Node<T> maxHeight(Node<T> n)
    {
        int leftHeight = (n.getLeft() == null) ? -1 : n.getLeft().getHeight();
        int rightHeight = (n.getRight() == null) ? -1 : n.getRight().getHeight();
        if(leftHeight > rightHeight) return n.getLeft();
        return n.getRight();
    }
    
    private void deleteFix(Node<T> top)
    {
        if(top == null) return;
        Node<T> parent = top.getRoot();
        top.setHeight(calcHeight(top));
        if(balance(top)>1)
        {
            deleteBalance(top);
        }
        deleteFix(parent);
    }
    
    private void deleteBalance(Node<T> x)
    {
        Node<T> y, z;
        y = maxHeight(x);
        z = maxHeight(y);
        
        rebalance(x,y,z);
    }
    
    private Node<T> leftMost(Node<T> top)
    {
        if(top.getLeft() == null) return top;
        else return leftMost(top.getLeft());
    }
    
    private void rebalance(Node<T> x, Node<T> y, Node<T> z)
    {
        
        
        if(y.getDirection() == z.getDirection())
        {
            Node<T> upperRoot = x.getRoot();
            if(y.getDirection() == 1)
            {
                //right right
                //N right becomes y left
                Node<T> yLeft = y.getLeft();
                
                x.setRight(yLeft);
                
                if(yLeft!=null)
                {
                    yLeft.setRoot(x);
                    yLeft.setDirection(1);
                }
                //recalculate N height
                
                //set y to the new root
                if (x.direction == 1)
                    upperRoot.right = y;
                else if (x.direction == -1)
                    upperRoot.left = y;
                else
                    root = y;
                y.setDirection(x.direction);
                y.setRoot(upperRoot);
                
                //then N becomes new y left
                    
                y.setLeft(x);
                x.setRoot(y);
                x.setDirection(-1);
                
                //then recalcutlate y height
                //then move up the tree and fix the remaining heights
                
                x.setHeight(calcHeight(x));
                y.setHeight(calcHeight(y));
                
                //fixTree(y.getHeight()+1,y.getRoot(),null,null);
                
            }
            else
            {
                //left left
                Node<T> yRight = y.getRight();
                
                x.setLeft(yRight);
                
                if(yRight!=null)
                {
                    yRight.setRoot(x);
                    yRight.setDirection(-1);
                }
                
                
                
                if (x.direction == 1)
                    upperRoot.setRight(y);
                else if (x.direction == -1)
                    upperRoot.setLeft(y);
                else
                    root = y;
                y.setDirection(x.direction);
                y.setRoot(upperRoot);
                
                y.setRight(x);
                x.setRoot(y);
                x.setDirection(1);
                
                
                x.setHeight(calcHeight(x));
                y.setHeight(calcHeight(y));
                
                //fixTree(y.getHeight()+1,y.getRoot(),null,null);
            }
        }
        else
        {
            if(y.getDirection() == 1)
            {
                //right left
                //A = N
                //C = y
                //D = z
                
                //Right of z becomes left of y
                //Right of N becomes z
                //Right of z becoms y
                
                Node<T> zRight = z.getRight();
                y.setLeft(zRight);
                
                if(zRight!=null)
                {
                    zRight.setDirection(-1);
                    zRight.setRoot(y);
                }
                
                x.setRight(z);
                z.setDirection(1);
                z.setRoot(x);
                
                z.setRight(y);
                y.setDirection(1);
                y.setRoot(z);
                
                y.setHeight(calcHeight(y));
                z.setHeight(calcHeight(z));
                x.setHeight(calcHeight(x));
                
                rebalance(x, z, y);
            }
            else
            {
                //left right
                Node<T> zLeft = z.getLeft();
                y.setRight(zLeft);
                
                if(zLeft!=null)
                {
                    zLeft.setDirection(1);
                    zLeft.setRoot(y);
                }
                
                x.setLeft(z);
                z.setDirection(-1);
                z.setRoot(x);
                
                z.setLeft(y);
                y.setDirection(-1);
                y.setRoot(z);
                
                y.setHeight(calcHeight(y));
                z.setHeight(calcHeight(z));
                x.setHeight(calcHeight(x));
                
                rebalance(x, z, y);
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
    
    @Override
    public String toString(){
        return data.toString();
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
