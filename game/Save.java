import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class Save {

    RandomAccessFile saveFile;
    FileChannel fc;
    FileLock lock;
    boolean isSaving = false;

    public Save() throws FileNotFoundException, IOException {

        saveFile = new RandomAccessFile("game/data/tss.txt", "rw");
        fc = saveFile.getChannel();

        try {
            
            lock = fc.tryLock();

        } catch (OverlappingFileLockException ex) {

            saveFile.close();
            fc.close();

            //TODO: Error report

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
            saveFile.writeChars("active:" + p.name + '\n');
            saveFile.writeChars("location:" + Player.location + '\n');
            saveFile.writeChars("sublocation:" + Player.sublocation + '\n');

            for (int c = 0; c < all.length; c++) {
                
                // General stats
                saveFile.writeChars("name:" + all[c].name + '\n');
                saveFile.writeChars("\thp:" + all[c].health + '\n');
                saveFile.writeChars("\thp_cap:" + all[c].healthCap + '\n');
                saveFile.writeChars("\tatk:" + all[c].attack + '\n');
                saveFile.writeChars("\tdef:" + all[c].defense + '\n');
                saveFile.writeChars("\tinvcap:" + all[c].invCap + '\n');
                
                // Inventory
                for (int i = 0; i < all[c].inventory.length; i++) {

                    if (all[c].inventory[i] == null) saveFile.writeChars("\titem" + i + ":\n");
                    else {

                        saveFile.writeChars("\titem" + i + ':' + all[c].inventory[i].name);
                        if (all[c].inventory[i] == all[c].currentArmor) saveFile.writeChars("*Armor*");
                        else if (all[c].inventory[i] == all[c].currentWeapon) saveFile.writeChars("*Weapon*");
                        else if (all[c].inventory[i] == all[c].currentWearable) saveFile.writeChars("*Wearable*");
                        saveFile.writeChar('\n');

                    }

                }

            }

            // Boss data
            //TODO: Save bosses

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