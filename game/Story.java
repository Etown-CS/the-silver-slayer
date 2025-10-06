public class Story {

    // The idea for this class is to store all the story elements here
    // This way it won't get in the way of the code

    private String[] start = {"Someone write something with mad aura here for the first event\nAlso I found this goofy song to use for testing"};
    private String[] village = {};
    private String[] lake = {};
    private String[] mountain = {};
    private String[] cave = {};      // Will probably be empty but here just in case
    private String[] mine = {};      // Same deal as cave
    private String[] desert = {};
    private String[] swamp = {};
    private String[] fracture = {};
    private String[] lair = {};
    private String[][] locations = {start, village, lake, mountain, cave, mine, desert, swamp, fracture, lair};

    public String getEvent(int locationID, int eventID) {

        return locations[locationID][eventID];

    }
    
}