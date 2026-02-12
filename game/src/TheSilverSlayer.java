public class TheSilverSlayer {

    public static void main(String[] args) {
        /* Main */
    	
    	Database.makeConnection();
        Log.setupLog();
        Audio.backgroundMusic();
        new Menu();

    }
    
}