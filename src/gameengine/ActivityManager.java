package gameengine;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wyatt Baldree
 */
public final class ActivityManager {
    public static void playSound() {
                //File file = new File("freeze.wav");
                getInstance().playSfx(getInstance().getClass().getResourceAsStream("Sounds/freeze.wav"));
    }
    
    private static ActivityManager _activityManager = null;
    
    private final ExecutorService _executorService;
    
    public static ActivityManager getInstance(){
        if (_activityManager == null){
            _activityManager = new ActivityManager();
        }
        return _activityManager;
    }
    
    private ActivityManager(){
        _executorService = Executors.newCachedThreadPool();
    }
    
    public void submit(final Runnable task){
        _executorService.submit(task);
    }
    
    private void playSfx(final InputStream fileStream)
    {
        ActivityManager.getInstance().submit(new Runnable(){
                @Override
                public void run(){
                    try{
                        BufferedInputStream bufferedStream = new BufferedInputStream(fileStream);
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileStream);
                        
                        final int BUFFER_SIZE = 128000;
                        SourceDataLine sourceLine = null;
                        
                        AudioFormat audioFormat = audioInputStream.getFormat();
                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                        
                        sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                        sourceLine.open(audioFormat);
                        
                        if (sourceLine == null){
                            return;
                        }
                        
                        sourceLine.start();
                        int nBytesRead = 0;
                        byte[] abData = new byte[BUFFER_SIZE];
                        while (nBytesRead != -1){
                            try {
                                nBytesRead = bufferedStream.read(abData, 0, abData.length);
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                            if (nBytesRead >= 0){
                                sourceLine.write(abData, 0, nBytesRead);
                            }
                        }
                        
                        sourceLine.drain();
                        sourceLine.close();
                        bufferedStream.close();
                        audioInputStream.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }catch(UnsupportedAudioFileException e){
                        e.printStackTrace();
                    }catch(LineUnavailableException e){
                        e.printStackTrace();
                        System.exit(1);
                    }catch(Exception e){
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            });
    }
}
