import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class Log {

    private static RandomAccessFile logFile;
    private static FileChannel fc;
    private static FileLock lock = null;
    private static long timer;

    public static void setupLog() {

        try {

            logFile = new RandomAccessFile("tss_log.txt", "rw");
            fc = logFile.getChannel();
            fc.truncate(0);
            logFile.seek(0);

        } catch (IOException ex) {
        	
        	ex.printStackTrace();
            TheSilverSlayer.shutdownNow();

        }

        timer = System.currentTimeMillis();
        if (!logData("-= The Silver Slayer v0.1-beta =-\nStart time: " + timer)) {

            System.out.println("FATAL: Log hasn't started properly! Is it open somewhere?");
            TheSilverSlayer.shutdownNow();

        }

    }

    public static boolean logData(String data) {

        try {

            lock = fc.tryLock();

        } catch (OverlappingFileLockException | IOException ex) {

            System.out.println(ex.toString());
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

        logData("Session time: " + (System.currentTimeMillis() - timer));

        try {

            logFile.close();
            fc.close();

        } catch (IOException ex) {

            System.out.println("WARN: Failed to properly close log resources.");

        }

    }
    
}