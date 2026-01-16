public class TheSilverSlayer {

    public static void main(String[] args) {
        /* Main */

        if (!Log.setupLog()) {

            System.out.println("ERROR: Failed to initialize log file!");
            return;

        }

        if (!Log.logData("-= The Silver Slayer v0.1-beta =-")) {

            System.out.println("ERROR: Immediately failed to write to log!");
            return;

        }

        Audio.music();
        Log.logData("Initialized BG music thread.");

        @SuppressWarnings("unused")
        Menu menu = new Menu();

    }
    
}