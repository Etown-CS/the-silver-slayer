import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {

    AudioInputStream stream;
    Clip clip;
    String filePath;

    public Audio (String path) {

        filePath = path;

    }

    public void command(int cmd) {
        /*
         * Function for managing an instance of Audio
         * 
         * id: The command to be executed
         *      1: Play the sound
         *      Default: Stop the sound
         */

        switch (cmd) {

            case 1:

                try {play();}
                catch (Exception ex) {System.out.println("WARN: Failed to play sound.");}
                break;

            default:

                stop();
                break;

        }

    }

    private void play() throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        stream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(stream);
        clip.loop(-1);
        clip.start();

    }

    private void stop() {

        clip.stop();
        clip.close();

    }
    
}