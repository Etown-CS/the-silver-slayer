public class TheSilverSlayer {

    public static void main(String[] args) {
        /* Main */
    	
        Log.setupLog();
    	Database.makeConnection(true);
        Audio.backgroundMusic();
        new Menu();

    }
    
}