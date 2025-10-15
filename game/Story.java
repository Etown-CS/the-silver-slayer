import java.util.HashMap;

public class Story {

    // The idea for this class is to store all the story elements here
    // This way it won't get in the way of the code

    private HashMap<Integer, String> start = new HashMap<>();
    private HashMap<Integer, String> village = new HashMap<>();
    private HashMap<Integer, String> lake = new HashMap<>();
    private HashMap<Integer, String> mountain = new HashMap<>();
    private HashMap<Integer, String> cave = new HashMap<>();
    private HashMap<Integer, String> mine = new HashMap<>();
    private HashMap<Integer, String> desert = new HashMap<>();
    private HashMap<Integer, String> swamp = new HashMap<>();
    private HashMap<Integer, String> fracture = new HashMap<>();
    private HashMap<Integer, String> lair = new HashMap<>();
    private HashMap[] events = {start, village, lake, mountain, cave, mine, desert, swamp, fracture, lair};

    public Story() {
        /* Constructor */

        start.put(10, "Song time :D");

    }

    public String getEvent(int locationID, int eventID) {

        return events[locationID].get(eventID).toString();

    }
    
}