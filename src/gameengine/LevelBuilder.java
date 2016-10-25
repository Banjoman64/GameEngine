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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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
    LevelPanel lp;
    private JScrollPane sp;
    private JPanel jp;
    private JPanel gb;
    private JPanel tb;
    private JTextField fieldGridX;
    private JTextField fieldGridY;
    private JButton tempButton;
    private JButton showGridButton;
    private JButton exportButton;
    private List<String> pieceList = new ArrayList<String>();
    public static List<GameObject> objectList = new ArrayList<GameObject>();
    public static String chosenPiece = "Ball";
    long start_time = System.currentTimeMillis();
    public static KeyboardInput keyboardInput;
    public static MouseInput mouseInput;
    
    
    public LevelBuilder(){
        LevelBuilder.keyboardInput = new KeyboardInput();
        LevelBuilder.mouseInput = new MouseInput();
        
        pieceList.add("Ball");
        pieceList.add("Ball");
        pieceList.add("Wall");
        pieceList.add("Ball");
        pieceList.add("Wall");
        pieceList.add("Ball");
        pieceList.add("Ball");
        pieceList.add("Wall");
        pieceList.add("Ball");
        pieceList.add("Wall");
        pieceList.add("Ball");
        pieceList.add("Ball");
        pieceList.add("Wall");
        pieceList.add("Ball");
        pieceList.add("Wall");
        
        setFocusable(true);
        addKeyListener(LevelBuilder.keyboardInput);
        addMouseListener(LevelBuilder.mouseInput);
        initializeFrame();
        
    }
    
    private void initializeFrame(){
        
        
        
                
        
        //Grid Bag Layout
        GridBagLayout gbl = new GridBagLayout();
        gb = new JPanel(gbl);
        gb.setPreferredSize(new Dimension(110,640));
        GridBagConstraints c = new GridBagConstraints();
        gb.setFocusable(true);
        gb.addKeyListener(LevelBuilder.keyboardInput);
        gb.addMouseListener(LevelBuilder.mouseInput);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        
        //Add Buttons To The Grid
        /*for(int i  = 0 ; i < 30 ; i++){
            tempButton = new JButton("Button " + i);
            gbl.setConstraints(tempButton, c);
            gb.add(tempButton);
            gb.setPreferredSize(new Dimension(110,32*i));;
        }*/
        for(int i  = 0 ; i < pieceList.size() ; i++){
            ClassData classData = new ClassData((String)pieceList.get(i));
            tempButton = new LevelButton(classData);
            tempButton.setFocusable(false);
            gbl.setConstraints(tempButton, c);
            gb.add(tempButton);
            gb.setPreferredSize(new Dimension(110,72*i));
            
            tempButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    pieceButtonActionPerformed(evt);
                }
            });
        }
        
        //Scroll Pane For Grid
        sp = new JScrollPane(gb);////////////////////////////////////
        sp.setPreferredSize(new Dimension(128,640));
        sp.setFocusable(true);
        sp.addKeyListener(LevelBuilder.keyboardInput);
        sp.addMouseListener(LevelBuilder.mouseInput);
        //Level Display
        lp = new LevelPanel(objectList);
        lp.setVisible(true);
        lp.setPreferredSize(new Dimension(640,640));
        lp.setFocusable(true);
        lp.addKeyListener(LevelBuilder.keyboardInput);
        lp.addMouseListener(LevelBuilder.mouseInput);
        
         //top bar
        tb = new JPanel();
        tb.setFocusable(false);
        //tb.setPreferredSize(new Dimension(640,48));
        
        
        JLabel jl = new JLabel("Grid X: ");
        jl.setFocusable(false);
        
        tb.add(jl);
        
        fieldGridX = new JTextField(4);
        //fieldGridX.setFocusable(false);
        fieldGridX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });
        tb.add(fieldGridX);
        
        jl = new JLabel("Grid Y: ");
        jl.setFocusable(false);
        tb.add(jl);
        
        fieldGridY = new JTextField(4);
        //fieldGridY.setFocusable(false);
        fieldGridY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });
        tb.add(fieldGridY);
        
        showGridButton = new JButton("Show Grid");
        showGridButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        
        tb.add(showGridButton);
        
        exportButton = new JButton("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        
        tb.add(exportButton);

        //Add It All Together
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(lp, java.awt.BorderLayout.CENTER);
        getContentPane().add(sp, java.awt.BorderLayout.WEST);
        getContentPane().add(tb, java.awt.BorderLayout.NORTH);
        setResizable(true);
        setVisible(true);
        setFocusable(true);
        setAlwaysOnTop(true);
        pack();
        
        GameObject.objectList = objectList;
        
        //Begin Loop************************************************************
        boolean gameRunning = true;
        int frames_per_second = 30;
        int skip_ticks = 1000/frames_per_second;
        
        long next_game_tick = getTickCount();
        
        int sleep_time = 0;
        
        while(gameRunning){
            LevelBuilder.keyboardInput.poll();
            LevelBuilder.mouseInput.poll();
            
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
        /*for(GameObject o : objectList){
            o.step();
        }*/
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