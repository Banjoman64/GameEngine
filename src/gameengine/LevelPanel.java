/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author wyatt
 */
class LevelPanel extends JPanel implements MouseMotionListener{
    private List<GameObject> objectList;
    private int mouse_x;
    private int mouse_y;
    private int mouse_x_grid;
    private int mouse_y_grid;
    private int mouse_event_x;
    private int mouse_event_y;
    
    //private int other_x;
    private boolean snapToGrid = true;
    
    private int gridX = 0;
    private int gridY = 0;
    
    private int offsetX = 0;
    private int offsetY = 0;
    
    private int panOffsetX = 0;
    private int panOffsetY = 0;
    
    private int leftClickLeftX = 0,leftClickLeftY = 0,leftClickRightX = 0,leftClickRightY = 0;
    private int rightClickLeftX = 0,rightClickLeftY = 0,rightClickRightX = 0,rightClickRightY = 0;
    private boolean selecting = false;
    private boolean panning = false;
    private boolean showGrid = false;
    
    public LevelPanel(List<GameObject> objectList){
        this.objectList = objectList;
        addMouseMotionListener(this);
       // GameObject.objectList = objectList;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent( g );
        clearDisplay(g);
        drawGUI(g);
        drawGameObjects(g);
        
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
        
        if(selecting){
            g.drawRect(leftClickLeftX-offsetX, leftClickLeftY-offsetY, mouse_x - leftClickLeftX, mouse_y - leftClickLeftY);
        }
        //g.drawRect(mouse_x-2,mouse_y-2, 4,4);
    }
    
    public void drawGridLines(Graphics g){
        if (showGrid)
        {
            if (gridX > 1 )
            {
                int gridOffset = (offsetX+panOffsetX)%gridX;
                for(int i = (-3*gridX)-gridOffset ; i < getWidth() ; i+=gridX)
                    g.drawLine(i, -1, i, getHeight()+1);
            }

            if (gridY > 1 )
            {
                int gridOffset = (offsetY+panOffsetY)%gridY;
                for(int i = (-3*gridY)-gridOffset ; i < getHeight() ; i+=gridY)
                    g.drawLine(-1, i, getWidth()+1, i);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        updateMouseEventCoords(me);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent me) {
        updateMouseEventCoords(me);
    }
    private void updateMouseEventCoords(MouseEvent me){
        mouse_event_x = me.getX() + offsetX;
        mouse_event_y = me.getY() + offsetY;
    }
    
    private void updateMouseCoords(){
        mouse_x = mouse_event_x;
        mouse_y = mouse_event_y;
        if(gridX>1) mouse_x_grid = mouse_event_x - mouse_event_x%gridX;
        else mouse_x_grid = mouse_event_x;
        if(gridY>1) mouse_y_grid = mouse_event_y - mouse_event_y%gridY;
        else mouse_y_grid = mouse_event_y;
    }
    
    public void update(){
         if(LevelBuilder.keyboardInput.keyDown(KeyEvent.VK_X)){
            LevelBuilder.exportLevel();
            System.out.println("swwwsww");
        }
        
        updateMouseCoords();
        if(LevelBuilder.mouseInput.buttonPressed(MouseEvent.BUTTON1)){
            
            if(LevelBuilder.keyboardInput.keyDown(KeyEvent.VK_CONTROL))
            {
                List<GameObject> del = GameObject.collisionPointList(GameObject.class, mouse_x, mouse_y);
                for(GameObject o : del){
                    System.out.println(o.toString());
                    objectList.remove(o);
                }
            }else{
                leftClickLeftX = mouse_x;
                leftClickLeftY = mouse_y;
                if(LevelBuilder.chosenPiece!=null){
                    if(snapToGrid)
                    {
                        objectList.add((GameObject)(new ClassData(LevelBuilder.chosenPiece)).getNewObject(mouse_x_grid,mouse_y_grid));
                    }else{
                        objectList.add((GameObject)(new ClassData(LevelBuilder.chosenPiece)).getNewObject(mouse_x,mouse_y));
                    }

                }
                selecting = true;
            }
        }else if(LevelBuilder.mouseInput.buttonReleased(MouseEvent.BUTTON1)){
            selecting = false;
            leftClickRightX = mouse_x;
            leftClickRightY = mouse_y;
        }
        
        if(LevelBuilder.mouseInput.buttonPressed(MouseEvent.BUTTON3)){
            rightClickLeftX = mouse_x;
            rightClickLeftY = mouse_y;
            panning = true;
        }else if(LevelBuilder.mouseInput.buttonReleased(MouseEvent.BUTTON3)){
            panning = false;
            offsetX = offsetX+panOffsetX;
            offsetY = offsetY+panOffsetY;
            panOffsetX = 0;
            panOffsetY = 0;
        }
        if(panning)
        {
            panOffsetX = -(mouse_x - rightClickLeftX);
            panOffsetY = -(mouse_y - rightClickLeftY);
        }
        
        GameObject.setOffset(new Point(offsetX+panOffsetX,offsetY+panOffsetY));
    }
    public int getMouseX(){
        return mouse_x;
    }
    public int getMouseY(){
        return mouse_y;
    }
    
    public void setGridX(int x)
    {
        this.gridX = x;
    }
    
    public void setGridY(int y)
    {
        this.gridY = y;
    }
    
    public void toggleShowGrid()
    {
        showGrid = (showGrid == true) ? false : true;
    }
}
