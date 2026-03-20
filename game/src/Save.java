import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class Save {

    private static final String key = "SILVERY", alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890:_ \t\n";
    private static RandomAccessFile saveFile;
    private static FileChannel fc;
    private static FileLock lock;

    public static boolean saveGame(Player[] all, Player p) {
        /*
         * Save the game
         * all: Array of all players
         * p: Active player
         */

        try {

            saveFile = new RandomAccessFile("tss_save.txt" + System.currentTimeMillis(), "rw");
            fc = saveFile.getChannel();

        } catch (FileNotFoundException ex) {

            Log.logData("WARN: Failed to init save file!");
            return false;

        }

        try {

            lock = fc.tryLock();

        } catch (OverlappingFileLockException | IOException ex) {

            Log.logData("WARN: Save file is locked.");
            return false;

        }

        StringBuilder contents = new StringBuilder(1024);

        // Players' data
        contents.append("PLAYERS\n");
        contents.append("\tactive:'" + p.name + "'\n");
        contents.append("\tlocation:" + Player.location + '\n');
        contents.append("\tsublocation:" + Player.sublocation + '\n');
        contents.append("\tinvcap:" + Player.invCap + '\n');

        for (int c = 0; c < all.length; c++) {
            
            // General stats
            contents.append("\tname:" + all[c].name + '\n');
            contents.append("\t\thp:" + all[c].health + '\n');
            contents.append("\t\thp_cap:" + all[c].healthDefault + '\n');
            contents.append("\t\tatk:" + all[c].attack + '\n');
            contents.append("\t\tdef:" + all[c].defense + '\n');
            
            // Inventory
            for (int i = 0; i < Player.invCap; i++) {

                if (all[c].inventory[i] == null) contents.append("\t\titem" + i + ":null\n");
                else {

                    contents.append("\t\titem" + i + ":[" + all[c].inventory[i].name + ',' + all[c].inventory[i].type + ',' +all[c].inventory[i].description + ',' + all[c].inventory[i].magnitude + ',' + all[c].inventory[i].consumable + ']');
                    if (all[c].inventory[i] == all[c].currentArmor || all[c].inventory[i] == all[c].currentWeapon || all[c].inventory[i] == all[c].currentWearable) contents.append('*');
                    contents.append('\n');

                }

            }

        }

        // Boss data
        contents.append("BOSSES_BEATEN\n");
        for (int c = 2; c < Locations.locations.length; c++) {

            if (Locations.enemyIndex[c][Locations.enemyIndex[c].length - 1].health == 0) contents.append("\tLocation" + c + ":true\n");
            else contents.append("\tLocation" + c + ":false\n");

        }

        try {

            saveFile.writeChars(encrypt(contents.toString()));

        } catch (IOException ex) {

            Log.logData("WARN: Failed to write save data to file!");
            return false;

        }

        try {

            lock.release();
            fc.close();
            saveFile.close();

        } catch (IOException ex) {

            Log.logData("WARN: Resource(s) did not close properly.");

        }

        return true;

    }

    private static String encrypt(String contents) {

        StringBuilder result = new StringBuilder(1024);
        int pos = 0;

        for (char c : contents.toCharArray()) {

            char k = key.charAt(pos % key.length());
            int cIdx = alphabet.indexOf(c);
            int kIdx = alphabet.indexOf(k);

            if (cIdx >= 0) {

                if (kIdx >= 0) result.append(alphabet.charAt((cIdx + kIdx + 1) % alphabet.length()));
                pos++;

            } else result.append(c);

        }

        return result.toString();

    }

    @SuppressWarnings("unused")
    private static String decrypt(String contents) {

        StringBuilder result = new StringBuilder(1024);
        int pos = 0;

        for (char c : contents.toCharArray()) {

            char k = key.charAt(pos % key.length());
            int cIdx = alphabet.indexOf(c);
            int kIdx = alphabet.indexOf(k);

            if (cIdx >= 0) {

                int nIdx = cIdx - kIdx - 1;
                if (nIdx < 0) nIdx = alphabet.length() + nIdx;
                if (kIdx >= 0) result.append(alphabet.charAt(nIdx));
                pos++;

            } else result.append(c);

        }

        return result.toString();

    }
    
}