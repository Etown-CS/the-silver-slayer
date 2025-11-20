import java.util.HashMap;

public class Story {

    // The idea for this class is to store all the story elements here
    // This way they won't get in the way of the code

    /*
     * STRUCTURE
     * 
     * Every major location has its own HashMap.
     * Each HashMap consists of an event ID and then the event itself.
     * Events ending in 0 are the event that occurs when first entering somewhere.
     * Events ending in 1 are what happens when you run look.
     * Similarly, events ending in 2 are the search events.
     * 
     * Cave & Mine are omitted here because they're implemented exclusively in C
     * Also, this allows for us to use 3 digit locations IDs.
     * 
     */

    private HashMap<Integer, String> start = new HashMap<>();
    private HashMap<Integer, String> village = new HashMap<>();
    private HashMap<Integer, String> lake = new HashMap<>();
    private HashMap<Integer, String> mountain = new HashMap<>();
    private HashMap<Integer, String> desert = new HashMap<>();
    private HashMap<Integer, String> swamp = new HashMap<>();
    private HashMap<Integer, String> fracture = new HashMap<>();
    private HashMap<Integer, String> lair = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String>> events = new HashMap<Integer, HashMap<Integer, String>>();

    public final String[] TITLE_STRINGS = {"Silver Slayer RPG", "Also try Terraria!", "Also try Minecraft!", "THE FOG IS COMING", 
                                            "There may be an egg", "here come dat boi", "JOHN WAS HERE", "The name is Gus... Amon Gus", "water bottle", 
                                            "As I write this, it's 1:30pm on October 3rd, 2025", "[J]ohn, [A]sher, and [M]artin: JAM", 
                                            "Why am I writing these?", "Silksong is out!", "I ate my toothbrush", "o _ o", "get rekt", 
                                            "Low on magenta!", "Strings", "jk jk... unless?", "Remember to cave"};
    public final String[] FLEE_STRINGS = {"You can't run forever.", "You got away... for now.", "You'll be back."};
    public final String[] BOSS_DEFEATED = {"The Guardian has fallen. The first of many."};
    public final String[] GAME_OVERS = {"How unfortunate", "That's gonna leave a mark", "Better luck some time!", "oof", "bruh.mp3", "Process killed"};

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
        village.put(201, "Rows of abandoned houses lie in silence, and debris lines the streets. A dry well stands in the corner of the square, and a graveyard rests just beyond.");
        village.put(202, "You scavenge amongst the debris. There's nothing of value to be found, and you give up after realizing you look like another desperate looter.");

        village.put(210, "A particular house catches your eye. It seems less worn then the rest of the buildings, so you decide to take a closer look. You go inside.");
        village.put(211, "The house appears to be completely furnished. Nothing is moved it makes you wonder if there are people still living here. You look at the kitchen table to see a fully cooked meal. The scary part is that you can still see steam coming off the food. On the table is a Silver Spoon. You want to pick it up.");
        village.put(212, "You pick up the Silver Spoon.");

        village.put(220, "The village graveyard is vast, with many gravestones in perfect rows. You decide to explore.");
        village.put(221, "Walking up to the gravestones, you begin to read the names on them. Some names you may recognize, but who is Smitty Werbenjagermanjenson?");
        village.put(222, "You find nothing of value.");

        village.put(230, "In the center of the village there is a well. Must have been the water source for everyone. You decide to take a look inside.");
        village.put(231, "A bucket is sitting at the bottom of this dry well. Thats what they must have used to retrieve the water.");
        village.put(232, "You scrounge around the well and peer down the pit. There's the bucket, but beyond that, there's nothing here.\nYou're not taking the bucket.");

        // LAKE
        lake.put(300, "You voyage to a great lake. The water is completely still, but yet you can see strange looking creatures moving under the surface");

        lake.put(310, "You have stumbled upon a dock. This would be a good place to fish, but for what?");
        lake.put(311, "You see a tackle box on the dock.");
        lake.put(312, "You find goggles in the tackle box. You also notice a weird smell coming from it.");

        lake.put(320, "You decide to take a swim! You dive into the water.");
        lake.put(321, "Luckily you're wearing goggles, so you begin to look around. At the bottom of the lake, you see the enterance to a cave, but you are to nervous to look any further. You then get out of the water.");

        lake.put(330, "Time to Phish!"); // phishing minigame

        // MOUNTAIN
        mountain.put(400, "You arrive the base of a mountain. A long, steep path looks like it makes it way to the peak, but you cant see the top from here.");

        // DESERT
        desert.put(500, "You travel to the desert. The heat is almost unbearable. In front of you are dunes that go on for what seems like forever.");

        desert.put(510, "You walk to one of the many large dunes. You decide to look around.");
        desert.put(511, "Once you're on top of the dune, you are able to see an oasis with a town nearby.");

        desert.put(520, "You decide to walk to the town and check it out.");
        desert.put(521, "In the town you see a bazaar and go inside.");
        desert.put(522, "You see a bar in the town and decide to go into it. Maybe someone in there will have some advice.");

        desert.put(530, "A small well is not far from you. Though looking inside, you see it is completely dry.");


        // SWAMP
        swamp.put(600, "You make your way to a swamp. The air is humid. Your feet sink into the soft mud as you walk. Erie sounds come from within the swamp, but you cant tell what is making the noise.");

        // FRACTURE
        fracture.put(700, "You have entered the fracture. The landscape doesn't seem real. The ground is cracked and broken. Looking into these cracks in the ground, you see strange shapes that can only be described as supernatural. The sky seems to be constantly changing color.");
        fracture.put(701, "");
        fracture.put(702, "");

        fracture.put(710, "You notice the lights in the sky are leading somewhere, somewhere dark, even evil. You decide to follow them.");
        fracture.put(711, "");
        fracture.put(712, "");

        fracture.put(720, "Big secret");
        fracture.put(721, "");
        fracture.put(722, "");

        // Edge (reveal next)
        // Mirrors

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

    public String getLookEvent(int loc, int sub) {
        /*
         * Get look event for current location
         * 
         * loc: Location ID
         * sub: Sublocation ID
         */

        return getLSEvent(loc, sub, true);

    }

    public String getSearchEvent(int l, int s) {
        /*
         * Get search event for current location
         * 
         * loc: Location ID
         * sub: Sublocation ID
         */

        return getLSEvent(l, s, false);

    }

    private String getLSEvent(int l, int s, boolean look) {
        /*
         * Mathematically derives the look or search event key for any location/sublocation combination,
         *      then returns the event.
         *      Does not get called directly.
         * 
         * l: location
         * s: sublocation
         * look: [true] for look, [false] for search
         */

        if (look) return getEvent(l, l * 100 + s * 10 + 1);
        else return getEvent(l, l * 100 + s * 10 + 2);

    }
    
}