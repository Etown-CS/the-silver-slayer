import java.util.HashMap;

public class Story {

    // The idea for this class is to store all the story elements here
    // This way they won't get in the way of the code

    private HashMap<Integer, String> start = new HashMap<>();
    private HashMap<Integer, String> village = new HashMap<>();
    private HashMap<Integer, String> lake = new HashMap<>();
    private HashMap<Integer, String> mountain = new HashMap<>();
    // private HashMap<Integer, String> cave = new HashMap<>();     // Probably doesn't need to be here
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
        start.put(0, "test event");
        start.put(100, "You step up to the gate. Any lock has long since worn away, and you're able to push the gate open with little resistance. The doors swing wide with a long, metallic screech, and you step through onto the path that leads to the village.");

        // VILLAGE
        village.put(200, "You arrive at the village. Buildings worn from years of neglect and misuse. You don't see any signs of life around. At first glance, it seems like you are alone.");
        village.put(210, "A particular house catches your eye. It seems less worn then the rest of the buildings, so you decide to take a closer look. You go inside.");
        village.put(220, "The village graveyard is vast, with many gravestones in perfect rows. You decide to explore.");
        village.put(230, "In the center of the village there is a well. Must have been the water source for everyone. You decide to take a look inside.");

        // LAKE
        lake.put(300, "You voyage to a great lake. The water is completely still, but yet you can see strange looking creatures moving under the surface");
        lake.put(310, "You have stumbled upon a dock. This would be a good place to fish, but for what?");

        // MOUNTAIN
        mountain.put(400, "You arrive the base of a mountain. A long, steep path looks like it makes it way to the peak, but you cant see the top from here.");

        // DESERT
        desert.put(500, "You travel to the desert. The heat is almost unbearable. In front of you are dunes that go on for what seems like forever.");
        desert.put(510, "You walk to one of the many large dunes. You decide to look around.");
        desert.put(520, "Beside one of the dunes, you notice a town, and decide to check it out.");
        desert.put(530, "A small well is not far from you. Though looking inside, you see it is completely dry.");

        // SWAMP
        swamp.put(600, "You make your way to a swamp. The air is humid. Your feet sink into the soft mud as you walk. Erie sounds come from within the swamp, but you cant tell what is making the noise.");

        // FRACTURE
        fracture.put(700, "You have entered the fracture. The landscape doesn't look real. The ground is cracked and broken. Looking into these cracks in the ground, you see strange shapes that can only be described as supernatural. The sky seems to be constantly changing color.");
        fracture.put(710, "You notice the lights in the sky are leading somewhere, somewhere dark, even evil. You decide to follow them.");
        fracture.put(720, "Big secret");

        // LAIR
        lair.put(800, "Crossing a rickety old bridge, you arrive at the lair. The air is thick with smoke. You only can describe this place as the underworld. Nothing good happens here. Across the bridge you see an onmious building, like a castle, but evil. You decide to go inside.");
        lair.put(810, "You see a door that seems to enter some kind of throne room. Loud roars are coming from inside. Whatever is in there, knows your here, and plans to change that very soon. Holding your breath, you open the door and step inside.");

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