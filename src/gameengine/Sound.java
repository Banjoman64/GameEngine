/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Sound implements Runnable
{
    private boolean running = false;
    private Thread thread;

    public Sound()
    {
        this.startS();
    }

    public synchronized void startS()
    {
        if(running)
            return;
        this.thread = new Thread(this);
        this.running = true;
        this.thread.start();
    }

    //
    private boolean playSong = false;
    private AudioInputStream inputStream;
    private String url;
    private Clip clip;

    @Override
    public synchronized void run()
    {
        while(running)
        {
            //System.out.print("");
            if(inputStream == null && playSong)
            {
                
                this.playSong = false;
                try
                {
                    System.out.println(333333);
                    inputStream = AudioSystem.getAudioInputStream(new File(url));
                    
                    System.out.println("SHHHHHHIIIIIIIIT"+inputStream.getFormat());
                    this.clip.open(inputStream);
                    this.clip.loop(0);
                }
                catch(Exception e)
                {
                    System.out.println(111111111);
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void playBackGround(String string) // call to play .wav file
    {
        if(this.clip != null)
        {
            this.clip.stop();
            this.clip.close();
        }
        try
        {
            this.clip = AudioSystem.getClip();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
        url = string + ".wav";
        this.playSong = true;
        this.inputStream = null;
    }

    public synchronized void disposeSound()
    {
        if(this.clip != null)
        {
            this.clip.stop();
            this.clip.close();
        }
        this.clip = null;
        this.playSong = false;
        this.inputStream = null;
    }
}
/*package gameengine;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;


public class Sound implements Runnable{
    private boolean running = false;
    private Thread thread;
    
    public Sound(){
        this.start();
    }
    
    private void start()
    {
        if(running)return;
        this.thread = new Thread(this);
        this.running = true;
        this.thread.start();
    }
    
    private boolean playSound = false;
    private AudioInputStream inputStream;
    private String url;
    private Clip clip;

    @Override
    public void run() {
        while(running)
        {
            if(inputStream == null && playSound)
            {
                this.playSound = false;
                try
                {
                    this.inputStream = AudioSystem.getAudioInputStream(new URL(url));
                    this.clip.open(inputStream);
                    this.clip.loop(10);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("22222222222222222222222222222222");
                }
            }
        }
    }
    
    public void playBackGround(String string)
    {
        if(this.clip != null)
        {
            this.clip.stop();
            this.clip.close();
        }
        try
        {
            this.clip = AudioSystem.getClip();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
            System.out.println("11111111111111111111111111111111111");
        }
        url = string + ".wav";
        this.playSound = true;
        this. inputStream = null;
    }
    
    public void disposeSound()
    {
        if(this.clip != null)
        {
            this.clip.stop();
            this.clip.close();
        }
        this.clip = null;
        this.playSound = false;
        this.inputStream = null;
    }
    
}
*/