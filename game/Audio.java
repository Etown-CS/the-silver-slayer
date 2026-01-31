import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.HashMap;
import java.security.SecureRandom;

public class Audio {

    private AudioInputStream stream;
    private Clip clip;
    private String filePath;
    public static Audio activeBG;

    public Audio(String fileName) {

        filePath = "game/sound/" + fileName + ".wav";

    }

    public void command() {command(0);}
    public void command(int cmd) {
        /*
        * Function for managing an instance of Audio
        * 
        * id: The command to be executed
        *      1: Play the sound and loop
        *      2: Play the sound once
        *      Default: Stop the sound
        */

        switch (cmd) {

            case 1:

                play(true);
                break;

            case 2:

                play(false);
                break;

            default:

                stop();
                break;

        }

    }

    private void play(boolean loop) {

        try {

            stream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(stream);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {

            Log.logData("WARN: Failed to play sound/music: " + filePath);
            stop();

        }
        
        if (loop) clip.loop(-1);
        else clip.start();

    }

    private void stop() {

        if (clip == null) return;
        clip.stop();
        clip.close();

        try {

            stream.close();

        } catch (IOException ex) {
            
            Log.logData("WARN: Failed to close audio stream for " + filePath);

        }

    }

    public static void music() {

        SecureRandom r = new SecureRandom();
        Audio[] startMusic = {new Audio("music_start0")},
                villageMusic = {new Audio("music_village0"), new Audio("music_village1")},
                lakeMusic = {new Audio("music_lake0")},
                mountainMusic = {new Audio("music_mountain0"), new Audio("music_mountain1")},
                desertMusic = {new Audio("music_desert0")},
                swampMusic = {new Audio("music_swamp1")}, // swamp0 unused
                fractureMusic = {new Audio("music_fracture0"), new Audio("music_fracture1")},
                lairMusic = {new Audio("music_lair0"), new Audio("music_lair1"), new Audio("music_lair2")};

        HashMap<Integer, Audio[]> music = new HashMap<Integer, Audio[]>();
        music.put(1, startMusic);
        music.put(2, villageMusic);
        music.put(3, lakeMusic);
        music.put(4, mountainMusic);
        music.put(5, desertMusic);
        music.put(6, swampMusic);
        music.put(7, fractureMusic);
        music.put(8, lairMusic);

        while (true) {
            
            try {

                Thread.sleep(r.nextInt(1, 100));

            } catch (InterruptedException ex) {
                
                Log.logData("WARN: BG Music sleep was interrupted.");

            }

            if (!Player.inBossfight) {

                activeBG = music.get(Player.location)[r.nextInt(music.get(Player.location).length)];
                activeBG.command(2);
                Log.logData("Playing song: " + activeBG.filePath.substring(11));

            } else Log.logData("Skipping BG music because bossfight is active.");

            while (activeBG.clip.isActive()) {

                try {

                    Thread.sleep(1000);
                    
                } catch (InterruptedException ex) {
                    
                    Log.logData("WARN: Audio sleep while song is active was interrupted.");

                }

            }

        }

    }
    
}