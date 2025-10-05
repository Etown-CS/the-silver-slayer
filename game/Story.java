public class Story {

    // The idea for this class is to store all the story elements here
    // This way it won't get in the way of the code

    public String[] start = {};
    public String[] village = {};
    public String[] lake = {};
    public String[] mountain = {};
    public String[] cave = {};      // Will probably be empty but here just in case
    public String[] mine = {};      // Same deal as cave
    public String[] desert = {};
    public String[] swamp = {};
    public String[] fracture = {};
    public String[] lair = {};
    public String[][] locations = {start, village, lake, mountain, cave, mine, desert, swamp, fracture, lair};

    private Menu menuRef;

    public Story(Menu main) {

        menuRef = main;

    }

    public String getEvent(int locationID, int eventID) {

        return locations[locationID][eventID];

    }
    
}