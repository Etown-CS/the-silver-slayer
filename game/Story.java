import java.util.HashMap;

public class Story {

    // The idea for this class is to store all the story elements here
    // This way they won't get in the way of the code

    private HashMap<Integer, String> start = new HashMap<>();
    private HashMap<Integer, String> village = new HashMap<>();
    private HashMap<Integer, String> lake = new HashMap<>();
    private HashMap<Integer, String> mountain = new HashMap<>();
    // private HashMap<Integer, String> cave = new HashMap<>();     // Probably doesn't need to be here, but included just in case
    // private HashMap<Integer, String> mine = new HashMap<>();     // Same as previous
    private HashMap<Integer, String> desert = new HashMap<>();
    private HashMap<Integer, String> swamp = new HashMap<>();
    private HashMap<Integer, String> fracture = new HashMap<>();
    private HashMap<Integer, String> lair = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String>> events = new HashMap<Integer, HashMap<Integer, String>>();

    public Story() {
        /* Constructor */

        // Populating top-level map
        events.put(1, start);
        events.put(2, village);
        events.put(3, lake);
        events.put(4, mountain);
        events.put(5, desert);
        events.put(6, swamp);
        events.put(7, fracture);
        events.put(8, lair);

        // START
        start.put(100, "You step up to the gate. Any lock has long since worn away, and you're able to push the gate open with little resistance. The doors swing wide with a long, metallic screech, and you step through onto the path that leads to the village.");
        start.put(110, "epic story event");

        // VILLAGE
        village.put(200, "village moment");
        
        // LAKE

        // MOUNTAIN

        // DESERT

        // SWAMP

        // FRACTURE

        // LAIR

    }

    public String getEvent(int locationID, int eventID) {
        /*
         * Get a story event
         * 
         * locationID: The ID of the location to pull the event from
         * eventID: A three digit integer referencing the specific event
         */

        return events.get(locationID).get(eventID);

    }
    
}