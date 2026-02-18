import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.security.SecureRandom;

public class Audio {

    private AudioInputStream stream;
    private Clip clip;
    private String filePath;
    public static Audio activeTrack = null;

    public Audio(String fileName) {
        
        filePath = "sound/" + fileName + ".wav";

    }

    public void play(boolean loop) {

        try {

            stream = AudioSystem.getAudioInputStream(getClass().getResource(filePath));
            clip = AudioSystem.getClip();
            clip.open(stream);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {

            Log.logData("WARN: Failed to play sound/music: " + filePath);
            stop();

        }
        
        if (loop) clip.loop(-1);
        else clip.start();

    }

    public void stop() {

        if (clip != null) {

            clip.stop();
            clip.close();

        }

        if (stream != null) {

            try {

                stream.close();

            } catch (IOException ex) {
                
                Log.logData("WARN: Failed to properly close audio stream: " + filePath);

            }

        }

    }

    public static void backgroundMusic() {

        SecureRandom r = new SecureRandom();
        Audio[][] bgSongs = {
            {new Audio("music_start0"), new Audio("music_start1"), new Audio("music_start2")},
            {new Audio("music_village0"), new Audio("music_village1"), new Audio("music_village2")},
            {new Audio("music_lake0"), new Audio("music_lake1"), new Audio("music_lake2")},
            {new Audio("music_mountain0"), new Audio("music_mountain1")},
            {new Audio("music_desert0"), new Audio("music_desert1"), new Audio("music_desert2")},
            {new Audio("music_swamp0"), new Audio("music_swamp1"), new Audio("music_swamp2")},
            {new Audio("music_fracture0"), new Audio("music_fracture1"), new Audio("music_fracture2")},
            {new Audio("music_lair0"), new Audio("music_lair1"), new Audio("music_lair2")}};
        
        Log.logData("Initializing BG music thread.");
        new Thread(() -> {

            while (true) {

                try {

                    Thread.sleep(r.nextInt(0, 100)); // TODO: Change this in production

                } catch (InterruptedException ex) {

                    Log.logData("WARN: Background music delay interrupted.");

                }

                if (!Player.inBossfight) {

                    activeTrack = bgSongs[Player.location - 1][r.nextInt(bgSongs[Player.location - 1].length)];
                    activeTrack.play(false);
                    Log.logData("Playing song: " + activeTrack.filePath);

                }

                while (activeTrack.clip.isActive()) {

                    try {

                        Thread.sleep(1000);

                    } catch (InterruptedException ex) {

                        Log.logData("WARN: Background music sleep interrupted.");

                    }

                }

                activeTrack.stop();

            }

        }).start();

    }
    
}