/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.reflections.Reflections;
/**
 *
 * @author wyatt
 */
public class GameEngine extends JFrame{
    private GamePanel gamePanel;
    public static List<GameObject> objectList = new ArrayList<GameObject>();
    long start_time = System.currentTimeMillis();
    public static KeyboardInput keyboardInput;
    public static MouseInput mouseInput;
    
    //private LevelBuilder lb;
    
    
    int num = 0;
    /**
     * @param args the command line arguments
     */
    //public static void main(String[] args){
    //    new GameEngine();
        
    //}
    
    public GameEngine(){
        //initialize KeyboardInput
        GameEngine.keyboardInput = new KeyboardInput();
        GameEngine.mouseInput = new MouseInput();
        
        //initialize JFrame and JPanel
        gamePanel = new GamePanel(objectList);
        gamePanel.setVisible(true);
        gamePanel.setPreferredSize(new Dimension(640,640));
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(GameEngine.keyboardInput);
        gamePanel.addMouseListener(GameEngine.mouseInput);
        //gamePanel.addKeyListener(keyboardInput);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(gamePanel, java.awt.BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
        setFocusable(true);
        addKeyListener(GameEngine.keyboardInput);
        addMouseListener(GameEngine.mouseInput);
        pack();
        
        
        GameObject.objectList = objectList;
        loadLevel(new String("newLevel.txt"));
        
        
        
        //Begin Loop************************************************************
        boolean gameRunning = true;
        int frames_per_second = 30;
        int skip_ticks = 1000/frames_per_second;
        
        long next_game_tick = getTickCount();
        
        int sleep_time = 0;
        
        while(gameRunning){
            GameEngine.keyboardInput.poll();
            GameEngine.mouseInput.poll();
            
                updateGame();
                displayGame();

            
            
            next_game_tick += skip_ticks;
            sleep_time = (int) (next_game_tick - getTickCount());
            if(sleep_time>=0){
                try{
                    Thread.sleep(sleep_time);
                }catch(Exception e)
                {
                    Thread.currentThread().interrupt();
                }
            }
            
        }
    }
    
    private long getTickCount(){
        return System.currentTimeMillis()-start_time;
    }
    
    private void loadLevel(String name)
    {
        
        List<List<String>> levelStrings = parseLevel(name);
        int size = levelStrings.size();
        String[] names = new String[size];
        int[] xs = new int[size];
        int[] ys = new int[size];
        ClassData currentClass;
        for(int i  = 0 ; i < size ; i++)
        {
            names[i] = levelStrings.get(i).get(0);
            xs[i] = Integer.parseInt(levelStrings.get(i).get(1));
            ys[i] = Integer.parseInt(levelStrings.get(i).get(2));
            
            currentClass = new ClassData(names[i]);
            objectList.add((GameObject)(currentClass).getNewObject(xs[i],ys[i]));
        }
        
    }
    
    private List<List<String>> parseLevel(String name)
    {
        int loop = 0;
        List<List<String>> retval = new ArrayList<List<String>>();
        File file = new File(name);
        BufferedReader br = null;
        
        try{
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                
               retval.add(Arrays.asList(line.split(" ",3)));
               
               System.out.print("dddd");
               line = br.readLine();
               System.out.println(retval.get(loop).get(0));
               loop++;
            }
            
            
        
        return retval;
        }catch(FileNotFoundException e){
            System.out.print("hey");
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private void updateGame(){
        for(GameObject o : objectList){
            o.step();
        }
    }
    
    private void displayGame(){
        gamePanel.repaint();
    }
}
