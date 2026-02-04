public class TheSilverSlayer {

    public static void main(String[] args) {
        /* Main */

        if (!Log.setupLog()) {

            System.out.println("FATAL: Failed to initialize log file!");
            return;

        }

        Audio.backgroundMusic();

        @SuppressWarnings("unused")
        Menu menu = new Menu();

    }
    
}