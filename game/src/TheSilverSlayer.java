public class TheSilverSlayer {

    // Use this to toggle debug mode
    public static final boolean debug = true;

    public static void main(String[] args) {
        /* Main */
    	
        Log.setupLog();
        Database.makeConnection();
        Story.initStory();
        Audio.backgroundMusic();
        new Menu();

    }

    public static void shutdownNow() {
        /*
        * Use this when a fatal error occurs to close all resources and terminate the application
        */

        if (Audio.activeTrack != null) Audio.activeTrack.stop();
        Database.closeConnection();
        Log.closeLog();
        System.exit(1);

    }
    
}