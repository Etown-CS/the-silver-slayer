import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class Save {

    private RandomAccessFile saveFile;
    private FileChannel fc;
    private FileLock lock;
    private String key = "SILVERY", alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890:_' \t\n";
    boolean isSaving = false, loaded = false;

    public Save() throws FileNotFoundException, IOException {
        /* Constructor */

        saveFile = new RandomAccessFile("game/data/tss.txt", "rw");
        fc = saveFile.getChannel();

        try {
            
            lock = fc.tryLock();
            loaded = true;

        } catch (OverlappingFileLockException ex) {

            saveFile.close();
            fc.close();
            System.out.println("FATAL: Save file is locked!");

        }

    }

    public boolean saveGame(Player[] all, Player p) throws IOException {
        /*
         * Save the game
         * 
         * all: Array of all players
         * p: Active player
         */

        if (!isSaving) {

            isSaving = true;
            String contents = "";

            // Reset file
            fc.truncate(0);
            saveFile.seek(0);

            // Players' data
            contents += "PLAYERS\n";
            contents += "\tactive:'" + p.name + "'\n";
            contents += "\tlocation:" + Player.location + '\n';
            contents += "\tsublocation:" + Player.sublocation + '\n';

            for (int c = 0; c < all.length; c++) {
                
                // General stats
                contents += "\tname:'" + all[c].name + "'\n";
                contents += "\t\thp:" + all[c].health + '\n';
                contents += "\t\thp_cap:" + all[c].healthCap + '\n';
                contents += "\t\tatk:" + all[c].attack + '\n';
                contents += "\t\tdef:" + all[c].defense + '\n';
                contents += "\t\tinvcap:" + all[c].invCap + '\n';
                
                // Inventory
                for (int i = 0; i < all[c].inventory.length; i++) {

                    if (all[c].inventory[i] == null) contents += "\t\titem" + i + ":\n";
                    else {

                        contents += "\t\titem" + i + ':' + all[c].inventory[i].name;
                        if (all[c].inventory[i] == all[c].currentArmor) contents += "*Armor*";
                        else if (all[c].inventory[i] == all[c].currentWeapon) contents += "*Weapon*";
                        else if (all[c].inventory[i] == all[c].currentWearable) contents += "*Wearable*";
                        contents += '\n';

                    }

                }

            }

            // Boss data
            contents += "BOSSES\n";
            for (int c = 2; c < Locations.locations.length - 1; c++) {

                if (Locations.enemyIndex[c][Locations.enemyIndex[c].length - 1].health == 0) contents += "\tLocation" + c + ":X\n";
                else contents += "\tLocation" + c + ":_\n";

            }

            saveFile.writeChars(encrypt(contents));
            isSaving = false;
            return true;
            
        } else return false;

    }

    public void saveQuit(Player[] all, Player p) throws IOException {
        /*
         * Save and then close files in preparation for quit
         * 
         * all: Array of all players
         * p: Active player
         */

        saveGame(all, p);
        lock.release();
        saveFile.close();
        fc.close();

    }

    private String encrypt(String contents) {

        String result = "";
        int pos = 0;

        for (char c : contents.toCharArray()) {

            char k = key.charAt(pos % key.length());
            int cIdx = alphabet.indexOf(c);
            int kIdx = alphabet.indexOf(k);

            if (cIdx >= 0) {

                if (kIdx >= 0) result += alphabet.charAt((cIdx + kIdx + 1) % alphabet.length());
                pos++;

            } else result += c;

        }

        return result;

    }

    private String decrypt(String contents) {

        String result = "";
        int pos = 0;

        for (char c : contents.toCharArray()) {

            char k = key.charAt(pos % key.length());
            int cIdx = alphabet.indexOf(c);
            int kIdx = alphabet.indexOf(k);

            if (cIdx >= 0) {

                int nIdx = cIdx - kIdx - 1;
                if (nIdx < 0) nIdx = alphabet.length() + nIdx;
                if (kIdx >= 0) result += alphabet.charAt(nIdx);
                pos++;

            } else result += c;

        }

        return result;

    }
    
}