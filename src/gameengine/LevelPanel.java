/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.FocusManager;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author wyatt
 */
class LevelPanel extends JPanel{
    private AVLTree<GameObject> objectList;
    
    private JLabel coordinateLabel;
    
    //private int other_x;
    private boolean snapToGrid = true;
    
    private int gridX = 0;
    private int gridY = 0;
    
    private Point offset = new Point(0,0);
    
    private int panOffsetX = 0;
    private int panOffsetY = 0;
    
    private int leftClickLeftX = 0,leftClickLeftY = 0,leftClickRightX = 0,leftClickRightY = 0;
    private int rightClickLeftX = 0,rightClickLeftY = 0,rightClickRightX = 0,rightClickRightY = 0;
    private boolean selecting = false;
    private boolean panning = false;
    private boolean showGrid = false;
    
    private MouseInput mouse;
    private KeyboardInput keyboard;
    
    private Double zoom = 1.0;
    
    public LevelPanel(AVLTree<GameObject> objectList){
        this.objectList = objectList;
        
        mouse = new MouseInput();
        mouse.setOffsetInstance(offset);
        mouse.setZoomInstance(zoom);
        keyboard = new KeyboardInput();
        
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        
        GameObject.setInput(keyboard, mouse);
        
        //requestFocus();
        //requestFocusInWindow();
    }
    
    @Override
    public void paintComponent(Graphics g){

        super.paintComponent( g );
        
        clearDisplay(g);
        
        Graphics2D g2 = (Graphics2D) g;
        int w = this.getWidth();// real width of canvas
        int h = this.getHeight();// real height of canvas
        // Translate used to make sure scale is centered
        //g2.translate(w/2, h/2);
        g2.scale(zoom, zoom);
        //g2.translate(-w/2, -h/2);     
        
        
        drawGUI(g);
        drawGameObjects(g);
        
        g.setColor(Color.red);
        
        
    }
    
    public void setCoordinateLabel(JLabel l){
        coordinateLabel = l;
    }
    
    private void clearDisplay(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
    }
    
    private void drawGameObjects(Graphics g){
        for(GameObject o : objectList){
            o.draw(g);
        }
    }
    
    private void drawGUI(Graphics g){
        g.setColor(Color.BLACK);
        drawGridLines(g);
        g.drawLine((int)(0-offset.x/zoom), (int)(-1000-offset.y/zoom), (int)(0-offset.x/zoom), (int)(1000-offset.y/zoom));
        
        if(selecting){
            g.drawRect(leftClickLeftX-(int)offset.getX(), leftClickLeftY-(int)offset.getY(), mouse.mouse_x() - leftClickLeftX, mouse.mouse_y() - leftClickLeftY);
        }
        //g.drawRect(mouse_x-2,mouse_y-2, 4,4);
    }
    
    public void drawGridLines(Graphics g){
        if (showGrid)
        {
            if (gridX > 1 )
            {
                int gridOffset = ((int)offset.getX()+panOffsetX)%gridX;
                for(int i = (-3*gridX)-gridOffset ; i < getWidth() ; i+=gridX)
                    g.drawLine(i, -1, i, getHeight()+1);
            }

            if (gridY > 1 )
            {
                int gridOffset = ((int)offset.getY()+panOffsetY)%gridY;
                for(int i = (-3*gridY)-gridOffset ; i < getHeight() ; i+=gridY)
                    g.drawLine(-1, i, getWidth()+1, i);
            }
        }
    }
    
    public void update(){
        keyboard.poll();
        mouse.poll();
        
        coordinateLabel.setText("(" + mouse.mouse_x() + "," + mouse.mouse_y() + ")");
        
        if(mouse.hasScrolled())
        {
            zoom-=(.2*mouse.scroll());
            GameObject.setZoom(zoom);
            mouse.setZoom(zoom);
        }
        
        if(keyboard.keyDown(KeyEvent.VK_CONTROL))
            {
                System.out.println("YOOO");
            }
        
        if(mouse.buttonPressed(MouseEvent.BUTTON1)){
            
            if(keyboard.keyDown(KeyEvent.VK_CONTROL))
            {
                System.out.println("YOOO");
                List<GameObject> del = GameObject.collisionPointList(GameObject.class, mouse.mouse_x(), mouse.mouse_y());
                for(GameObject o : del){
                    System.out.println(o.toString());
                    objectList.delete(o);
                }
            }else{
                leftClickLeftX = mouse.mouse_x();
                leftClickLeftY = mouse.mouse_y();
                if(LevelBuilder.chosenPiece!=null){
                    if(snapToGrid)
                    {
                        objectList.add((GameObject)(new ClassData(LevelBuilder.chosenPiece, alignToGrid(mouse.mouse_x(), gridX), alignToGrid(mouse.mouse_y(), gridY)).getGameObject()));
                    }else{
                        objectList.add((GameObject)(new ClassData(LevelBuilder.chosenPiece, mouse.mouse_x(), mouse.mouse_y()).getGameObject()));
                    }

                }
                selecting = true;
            }
        }else if(mouse.buttonReleased(MouseEvent.BUTTON1)){
            selecting = false;
            leftClickRightX = mouse.mouse_x();
            leftClickRightY = mouse.mouse_y();
        }
        
        if(mouse.buttonPressed(MouseEvent.BUTTON3)){
            rightClickLeftX = mouse.mouse_x();
            rightClickLeftY = mouse.mouse_y();
            panning = true;
        }else if(mouse.buttonReleased(MouseEvent.BUTTON3)){
            panning = false;
            offset.setLocation( (int)offset.getX()+panOffsetX, (int)offset.getY()+panOffsetY);
            panOffsetX = 0;
            panOffsetY = 0;
        }
        if(panning)
        {
            panOffsetX = -(mouse.mouse_x() - rightClickLeftX);
            panOffsetY = -(mouse.mouse_y() - rightClickLeftY);
        }
        
        GameObject.setOffset(new Point((int)offset.getX()+panOffsetX,(int)offset.getY()+panOffsetY));
    }
    
    public void toggleShowGrid()
    {
        showGrid = (showGrid == true) ? false : true;
    }
    
    public void setGridX(int x)
    {
        this.gridX = x;
    }
    
    public void setGridY(int y)
    {
        this.gridY = y;
    }
    
    private int alignToGrid(int x, int g){
    if(g>1) return x - x%g;
        else return x;
    }
}
