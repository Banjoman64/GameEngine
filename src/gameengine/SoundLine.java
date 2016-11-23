/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.err;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author wyatt
 */
public class SoundLine implements Runnable{
    private SourceDataLine line;
    private String string;
    private float pitchShift;
    private boolean play = true;
    public SoundLine(String string){
        this(string, 1f);
    }
    
    public SoundLine(String string, float pitchShift){
        this.string = string;
        this.pitchShift = pitchShift;
        new Thread(this).start();
    }
    
    private AudioFormat changePitch(AudioFormat af, float shift){
        af = new AudioFormat(af.getEncoding(), af.getSampleRate()*shift, af.getSampleSizeInBits(), af.getChannels(), af.getFrameSize(), af.getFrameRate(), af.isBigEndian());
        
        return af;
    }
    
    public void end(){
        line.stop();
        line.drain();
        line.close();
    }
    
    @Override
    public void run() {
        try{
            InputStream inputStream;
            inputStream = getClass().getResourceAsStream("Sounds/"+string+".wav");

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);

            AudioFormat audioFormat = audioInputStream.getFormat();
            
            audioFormat = changePitch(audioFormat, pitchShift);

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

            line = (SourceDataLine) AudioSystem.getLine(info);

            line.open(audioFormat);

            line.start();

            final int BUFFER_SIZE = 4096;

            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while((bytesRead = audioInputStream.read(bytesBuffer)) != -1){
                line.write(bytesBuffer, 0, bytesRead);
            }

            line.drain();
            line.close();
        
        }catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(LineUnavailableException e){
            e.printStackTrace();
        }
    }
}
