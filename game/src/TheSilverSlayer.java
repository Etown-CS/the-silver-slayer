public class TheSilverSlayer {

    public static void main(String[] args) {
        /* Main */
    	
        Log.setupLog();
    	Database.makeConnection(true);
        Audio.backgroundMusic();
        new Menu();

    }

    public static void shutdownNow() {
        /*
        * Use this when a fatal error occurs to close all resources and terminate the application
        */

        Log.closeLog();
        Database.closeConnection();
        if (Audio.activeTrack != null) Audio.activeTrack.stop();
        System.exit(1);

    }
    
}