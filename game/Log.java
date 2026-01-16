import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class Log {

    private static RandomAccessFile logFile;
    private static FileChannel fc;
    private static FileLock lock = null;

    public static boolean setupLog() {

        try {

            logFile = new RandomAccessFile("game/data/tss_log.txt", "rw");
            fc = logFile.getChannel();
            fc.truncate(0);
            logFile.seek(0);

        } catch (IOException ex) {

            return false;

        }

        return true;

    }

    public static boolean logData(String data) {

        try {

            lock = fc.tryLock();

        } catch (OverlappingFileLockException | IOException ex) {

            return false;

        }

        try {

            logFile.writeChars(data + '\n');
            lock.release();

        } catch (IOException ex) {
            
            return false;

        }

        return true;

    }

    public static void closeLog() {

        try {

            logFile.close();
            fc.close();

        } catch (IOException ex) {

            System.out.println("WARN: Failed to properly close log resources.");

        }

    }
    
}