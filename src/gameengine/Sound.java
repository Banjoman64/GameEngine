/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author wyatt
 */
public class Sound {
    public void start() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
        
        InputStream inputStream;
        inputStream = getClass().getResourceAsStream("Sounds/freeze.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        
        AudioFormat audioFormat = audioInputStream.getFormat();
        
        DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
        Clip clip = (Clip) AudioSystem.getLine(info);

        clip.open(audioInputStream);
        
        clip.addLineListener(new LineListener(){
            public void update(LineEvent event){
                if(event.getType() == LineEvent.Type.STOP){
                    event.getLine().close();
                    System.exit(0);
                }
            }
        });
        
        clip.start();
        TimeUnit.SECONDS.sleep(3);
        clip.start();
    }
}
