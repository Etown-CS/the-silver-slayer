import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import javax.swing.JOptionPane;

public class Save {

    RandomAccessFile saveFile;
    FileChannel fc;
    FileLock lock;
    boolean isSaving = false, loaded = false;

    public Save() throws FileNotFoundException, IOException {

        saveFile = new RandomAccessFile("game/data/tss.txt", "rw");
        fc = saveFile.getChannel();

        try {
            
            lock = fc.tryLock();
            loaded = true;

        } catch (OverlappingFileLockException ex) {

            saveFile.close();
            fc.close();
            JOptionPane.showMessageDialog(null, "Save file is open elsewhere.", "FATAL", JOptionPane.ERROR_MESSAGE);
            

        }

    }

    public boolean saveGame(Player[] all, Player p) throws IOException {
        /*
         * Save the game
         * 
         * p: Array of players
         */

        if (!isSaving) {

            isSaving = true;

            // Reset file
            fc.truncate(0);
            saveFile.seek(0);

            // Players' data
            saveFile.writeChars("PLAYERS\n");
            saveFile.writeChars("\tactive:'" + p.name + "'\n");
            saveFile.writeChars("\tlocation:" + Player.location + '\n');
            saveFile.writeChars("\tsublocation:" + Player.sublocation + '\n');

            for (int c = 0; c < all.length; c++) {
                
                // General stats
                saveFile.writeChars("\tname:'" + all[c].name + "'\n");
                saveFile.writeChars("\t\thp:" + all[c].health + '\n');
                saveFile.writeChars("\t\thp_cap:" + all[c].healthCap + '\n');
                saveFile.writeChars("\t\tatk:" + all[c].attack + '\n');
                saveFile.writeChars("\t\tdef:" + all[c].defense + '\n');
                saveFile.writeChars("\t\tinvcap:" + all[c].invCap + '\n');
                
                // Inventory
                for (int i = 0; i < all[c].inventory.length; i++) {

                    if (all[c].inventory[i] == null) saveFile.writeChars("\t\titem" + i + ":\n");
                    else {

                        saveFile.writeChars("\t\titem" + i + ':' + all[c].inventory[i].name);
                        if (all[c].inventory[i] == all[c].currentArmor) saveFile.writeChars("*Armor*");
                        else if (all[c].inventory[i] == all[c].currentWeapon) saveFile.writeChars("*Weapon*");
                        else if (all[c].inventory[i] == all[c].currentWearable) saveFile.writeChars("*Wearable*");
                        saveFile.writeChar('\n');

                    }

                }

            }

            // Boss data
            saveFile.writeChars("BOSSES\n");
            for (int c = 1; c < Locations.locations.length - 1; c++) {

                if (Locations.enemyIndex[c][Locations.enemyIndex[c].length - 1].health == 0) saveFile.writeChars("\tLocation" + c + ":X\n");
                else saveFile.writeChars("\tLocation" + c + ":_\n");

            }

            isSaving = false;
            return true;
            
        } else return false;

    }

    public void saveQuit(Player[] all, Player p) throws IOException {
        /*
         * Save and then close files in preparation for quit
         * 
         * p: Array of players
         */

        saveGame(all, p);
        lock.release();
        saveFile.close();
        fc.close();

    }
    
}