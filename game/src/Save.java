import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Save {

    private static final String key = "SILVERY", alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890:_ \t\n";
    private static RandomAccessFile saveFile;
    private static FileChannel fc;
    private static FileLock lock;

    public static boolean saveGame(Player[] players, Player playerRef) {
        /*
         * Save the game
         * all: Array of all players
         * p: Active player
         */

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

        try {

            saveFile = new RandomAccessFile("tss_save_" + LocalDateTime.now().format(fmt) + ".txt", "rw");
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

        contents.append("\nCHARACTERS\n\n");
        for (int c = 0; c < players.length; c++) {

            if (players[c] == playerRef) contents.append('*');
            contents.append(players[c].name + ": ");
            contents.append(players[c].health + "/" + players[c].healthDefault + ", ");
            contents.append(players[c].attack + "/" + players[c].attackDefault + ", ");
            contents.append(players[c].defense + "/" + players[c].defenseDefault + ", ");
            contents.append(players[c].statuses + "\n");

        }

        contents.append("\nLocation: " + Player.location + "/" + Player.sublocation + ", ");
        contents.append("Bits: " + Player.bits + ", ");
        contents.append("Swaps: " + Player.pSwaps + ", ");
        contents.append("Mountain searches: " + Player.mountainPathSearches + ", ");
        contents.append("Mirror moves: " + Player.fractureMirrorMoves + "\n\n");
        // In combat/bossfight values aren't saved because you can't save the game while in combat anyway

        contents.append("INVENTORY\n\n");
        for (int c = 0; c < Player.invCap; c++) {

            contents.append(c + ": ");
            if (Player.inventory[c] == null) contents.append('\n');
            else {

                Item tmp = Player.inventory[c];
                contents.append("{name=" + tmp.name + ", desc='" + tmp.description + "', type=" + tmp.type.toString() + ", value=" + tmp.value + ", mag=" + tmp.magnitude + ", user=" + tmp.user + ", consumable=" + tmp.consumable + "}\n");

            }

        }

        contents.append("\nBOSSES\n\n");
        for (int c = 0; c < Locations.enemyIndex.length; c++) {

            contents.append(Locations.enemyIndex[c][Locations.enemyIndex[c].length - 1].name + ": ");
            if (Locations.enemyIndex[c][Locations.enemyIndex[c].length - 1].health == 0) contents.append(" y\n");
            else contents.append("n\n");

        }

        // TODO: Update this value dynamically
        contents.append("SkeleTON: n\nLast Prospector: n");

        try {

            //saveFile.writeChars(encrypt(contents.toString()));
            saveFile.writeUTF(contents.toString());

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

    @SuppressWarnings("unused")
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