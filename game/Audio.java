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

    public void command(int cmd) {command(cmd, 0);}
    public void command(int cmd, int id) {
        /*
         * Function for managing an instance of Audio
         * 
         * id: The command to be executed
         *      1: Play the sound
         *      Default: Stop the sound
         */

        try {

            switch (cmd) {

                case 1:

                    play(id);
                    break;

                default:

                    stop();
                    break;

            }

        } catch (Exception ex) {System.out.println("WARN: Failed to manage sound with ID " + id);}

    }

    private void play(int id) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
   
        switch (id) {

            default:

                stream = AudioSystem.getAudioInputStream(new File("game/sound/blip.wav").getAbsoluteFile());
                break;

        }

        clip = AudioSystem.getClip();
        clip.open(stream);
        clip.loop(-1);
        clip.start();

    }

    private void stop() throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        clip.stop();
        clip.close();

    }
    
}
