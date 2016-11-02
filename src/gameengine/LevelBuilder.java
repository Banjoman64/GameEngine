/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Class.forName;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import static javax.swing.ScrollPaneConstants.*;
import javax.xml.transform.Source;

/**
 *
 * @author wyatt
 */
public class LevelBuilder extends JFrame{
    private LevelPanel lp;
    
    private JPanel bottomPanel;
    private JScrollPane objectScrollPane;
    private JPanel objectButtonPanel;
    private JPanel topPanel;
    private JTextField fieldGridX;
    private JTextField fieldGridY;
    private JLabel mouseCoord;
    private JButton tempButton;
    private JButton showGridButton;
    private JButton exportButton;
    private List<String> pieceList = new ArrayList<String>();
    public static AVLTree<GameObject> objectList = new AVLTree<GameObject>();
    public static String chosenPiece = "Ball";
    long start_time = System.currentTimeMillis();
    
    public LevelBuilder(){
        
        pieceList.add("Ball");
        pieceList.add("Wall");
        pieceList.add("BackGround");
        
        GameObject.objectList = objectList;
        initializeFrame();
        beginLoop();
        
    }
    
    private void initializeFrame(){        
        
        /***********************************************************************
        * Object Panel
        ***********************************************************************/
        //Grid Bag Layout
        GridBagLayout gbl = new GridBagLayout();
        objectButtonPanel = new JPanel(gbl);
        objectButtonPanel.setPreferredSize(new Dimension(110,640));
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        
        for(int i  = 0 ; i < pieceList.size() ; i++){
            ClassData classData = new ClassData((String)pieceList.get(i));
            tempButton = new LevelButton(classData);
            tempButton.setFocusable(false);
            gbl.setConstraints(tempButton, c);
            objectButtonPanel.add(tempButton);
            objectButtonPanel.setPreferredSize(new Dimension(110,72*i));
            
            tempButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    pieceButtonActionPerformed(evt);
                }
            });
        }
        
        //Scroll Pane For Grid
        objectScrollPane = new JScrollPane(objectButtonPanel);
        objectScrollPane.setPreferredSize(new Dimension(128,640));

        //Level Display
        lp = new LevelPanel(objectList);
        lp.setVisible(true);
        lp.setPreferredSize(new Dimension(640,640));
        
        /***********************************************************************
        * Top Panel
        ***********************************************************************/
        topPanel = new JPanel();
        
        //Grid Buttons
        JLabel jl = new JLabel("Grid X: ");
        topPanel.add(jl);
        
        fieldGridX = new JTextField(4);
        fieldGridX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });
        topPanel.add(fieldGridX);
        
        jl = new JLabel("Grid Y: ");
        topPanel.add(jl);
        
        fieldGridY = new JTextField(4);
        fieldGridY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });
        topPanel.add(fieldGridY);
        
        showGridButton = new JButton("Show Grid");
        showGridButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        
        topPanel.add(showGridButton);
        
        exportButton = new JButton("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        
        topPanel.add(exportButton);
        
        /***********************************************************************
        * Bottom Panel
        ***********************************************************************/
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        
        
        mouseCoord = new JLabel("(0,0)");
        bottomPanel.add(Box.createRigidArea(new Dimension(1,0)));
        bottomPanel.add(mouseCoord);
        mouseCoord.setAlignmentX(RIGHT_ALIGNMENT);
        
        //Add It All Together
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(lp, java.awt.BorderLayout.CENTER);
        getContentPane().add(objectScrollPane, java.awt.BorderLayout.WEST);
        getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);
        getContentPane().add(bottomPanel, java.awt.BorderLayout.SOUTH);
        setResizable(true);
        setUndecorated(true);
        setVisible(true);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        lp.requestFocusInWindow();
        lp.setCoordinateLabel(mouseCoord);
    }
    
    private void beginLoop()
    {
        //Begin Loop************************************************************
        boolean gameRunning = true;
        int frames_per_second = 30;
        int skip_ticks = 1000/frames_per_second;
        
        long next_game_tick = getTickCount();
        
        int sleep_time = 0;
        
        while(gameRunning){         
            updateBuilder();
            displayBuilder();
            
            next_game_tick += skip_ticks;
            sleep_time = (int) (next_game_tick - getTickCount());
            if(sleep_time>=0)
            {
                try
                {
                    Thread.sleep(sleep_time);
                }
                catch(Exception e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    private long getTickCount(){
        return System.currentTimeMillis()-start_time;
    }
    
    public void updateBuilder(){
        lp.update();
    }
    
    public void displayBuilder(){
        lp.repaint();
    }
    
    private void pieceButtonActionPerformed(java.awt.event.ActionEvent evt){
        LevelButton sourceButton = (LevelButton)evt.getSource();
        String name = sourceButton.getName();
        chosenPiece = name;
        lp.repaint();
    }
    
    private void buttonActionPerformed(java.awt.event.ActionEvent evt){
        Object source = evt.getSource();
        if (source == showGridButton) 
        {
            lp.toggleShowGrid();
        }else if (source == exportButton) 
        {
            exportLevel();
        }
        
        lp.requestFocusInWindow();
    }
    
    private void fieldActionPerformed(java.awt.event.ActionEvent evt){
        Object source = evt.getSource();
        if(source == fieldGridX)
        {
            int value  = Integer.parseInt(((JTextField)source).getText());
            lp.setGridX(value);
            System.out.println(((JTextField)source).getText());
        }
        if(source == fieldGridY)
        {
            int value  = Integer.parseInt(((JTextField)source).getText());
            lp.setGridY(value);
            System.out.println(((JTextField)source).getText());
            
        }
        lp.requestFocusInWindow();
            
        lp.repaint();
    }
    
    public static void exportLevel(){
        try{
            File file = new File ("newLevel.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(file)); 

            for(GameObject o : objectList){
                out.write(o.getName() +" "+ (int)(o.getX()) +" "+ (int)(o.getY()));
                out.newLine();
                System.out.print(o.getName() +" "+ (int)(o.getX()) +" "+ (int)(o.getY())+"\n");
            }

            out.close();
        }catch(Exception e){
        
        }
    }
}