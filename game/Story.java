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
    * Similarly, events ending in 2 are the search events, 3 is for an unlock, and 4 is for failing to unlock
    * 
    * Cave & Mine are omitted here because they're implemented exclusively in C
    * Also, this allows us to use 3 digit locations IDs.
    * 
    */

    public static final String[] TITLE_STRINGS = {"Silver Slayer RPG", "Also try Terraria!", "Also try Minecraft!", "THE FOG IS COMING", 
                                            "There may be an egg", "here come dat boi", "JOHN WAS HERE", "The name is Gus... Amon Gus", "water bottle", 
                                            "As I write this, it's 1:30pm on October 3rd, 2025", "[J]ohn, [A]sher, and [M]artin: JAM", 
                                            "Why am I writing these?", "Silksong is out!", "I ate my toothbrush", "o _ o", "get rekt", 
                                            "Low on magenta!", "Strings", "jk jk... unless?", "Remember to cave"};
    public static final String[] FLEE_STRINGS = {"You can't run forever.", "You got away... for now.", "You'll be back."};
    public static final String[] BOSS_DEFEATED = {null, null, "The Guardian has fallen. The first of many."};
    public static final String[] GAME_OVERS = {"How unfortunate", "That's gonna leave a mark", "Better luck some time!", "oof", "bruh.mp3", "Process killed"};

    private HashMap<Integer, String> start = new HashMap<>();
    private HashMap<Integer, String> village = new HashMap<>();
    private HashMap<Integer, String> lake = new HashMap<>();
    private HashMap<Integer, String> mountain = new HashMap<>();
    private HashMap<Integer, String> desert = new HashMap<>();
    private HashMap<Integer, String> swamp = new HashMap<>();
    private HashMap<Integer, String> fracture = new HashMap<>();
    private HashMap<Integer, String> lair = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String>> events = new HashMap<Integer, HashMap<Integer, String>>();
    
    private boolean[][] eventsSeen;

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

        // Field
        start.put(100, "Where it all began.");
        start.put(101, "You stand in an empty field of verdant grass. The path you came from trails away behind, and ahead, a dark, iron gate stands waiting. The endless expanse of green grass extends to the horizon, while the gate waits patiently.");
        start.put(102, "A wooden signpost stands nearby. The text has long since worn away. At its base sits a small, metal box crate. A silver-lined keyhole resides front and center.");
        start.put(103, "OPENED THE BOX");
        start.put(104, "You attempt to pry the box open, but it remains sealed. You're not getting into this without either special tools or the original key.");

        // Gate
        start.put(110, "You step up to the gate. Despite the lack of travelers, it's locked. A rusted, yet entirely solid padlock still hangs on a chain wrapped between the two doors.\n\n(Tip: Use 'unlock [code]' to try the lock!)");
        start.put(111, "The gate is old and weathered. The lock features eight numerical combination wheels, and there's an interesting inscription etched into the bottom:\n\nODM5MjczNTQ=");
        start.put(112, "There's nothing to be found in the nearby vicinity. Nothing seems to have been disturbed prior to your arrival.");
        start.put(113, "The lock clicks open, the chain falls to the ground, and you're able to push the gate open with little resistance. The doors swing wide with a long, metallic screech. The path forward is open to you.");
        start.put(114, "The lock is unresponsive.");

        // VILLAGE

        // Center
        village.put(200, "You arrive at the village. The buildings are worn from years of neglect and misuse. You don't see any signs of life around. At first glance, it seems like you are alone.");
        village.put(201, "Rows of abandoned houses lie in silence, and debris lines the streets. A dry well stands in the corner of the square, and a graveyard rests just beyond.");
        village.put(202, "You scavenge amongst the debris. There's nothing of value to be found, and you give up after realizing you look like another desperate looter.");
        village.put(203, "unlock");
        village.put(204, "failed unlock");

        // House
        village.put(210, "A particular house catches your eye. It seems less worn then the rest of the buildings, so you decide to take a closer look. You go inside.");
        village.put(211, "The house appears to be completely furnished. Nothing is moved it makes you wonder if there are people still living here. You look at the kitchen table to see a fully cooked meal. The scary part is that you can still see steam coming off the food. On the table is a Silver Spoon. You want to pick it up.");
        village.put(212, "You pick up the Silver Spoon.");
        village.put(213, "unlock");
        village.put(214, "failed unlock");

        // Graveyard
        village.put(220, "The village graveyard is vast, with many gravestones in perfect rows. You decide to explore.");
        village.put(221, "Walking up to the gravestones, you begin to read the names on them. Some names you may recognize, but who is Smitty Werbenjagermanjenson?");
        village.put(222, "You find nothing of value.");
        village.put(223, "unlock");
        village.put(224, "failed unlock");

        // Well
        village.put(230, "In the center of the village there is a well. Must have been the water source for everyone. You decide to take a look inside.");
        village.put(231, "A bucket is sitting at the bottom of this dry well. Thats what they must have used to retrieve the water.");
        village.put(232, "You scrounge around the well and peer down the pit. There's the bucket, but beyond that, there's nothing here.\nYou're not taking the bucket.");
        village.put(233, "unlock");
        village.put(234, "failed unlock");

        // LAKE

        // Shore
        lake.put(300, "You voyage to a great lake. The water is completely still, but yet you can see strange looking creatures moving under the surface");
        lake.put(301, "look");
        lake.put(302, "search");
        lake.put(303, "unlock");
        lake.put(304, "failed unlock");

        // Dock
        lake.put(310, "You have stumbled upon a dock. This could be a good place to phish, but for what?");
        lake.put(311, "You see a tackle box on the dock.");
        lake.put(312, "You find goggles in the tackle box. You also notice a weird smell coming from it.");
        lake.put(303, "unlock");
        lake.put(304, "failed unlock");

        // Water
        lake.put(320, "You decide to take a swim! You dive into the water.");
        lake.put(321, "Luckily you're wearing goggles, so you begin to look around. At the bottom of the lake, you see the enterance to a cave, but you are to nervous to look any further. You then get out of the water.");
        lake.put(322, "search");
        lake.put(323, "unlock");
        lake.put(324, "failed unlock");

        // Entrance
        lake.put(330, "Cave entrance??");
        lake.put(331, "look");
        lake.put(332, "search");
        lake.put(333, "unlock");
        lake.put(334, "failed unlock");

        // MOUNTAIN

        // Base
        mountain.put(400, "You arrive at the base of a mountain. A long, steep path looks like it makes it way to the peak, but you cant see the top from here due to the winter storm.");
        mountain.put(401, "look");
        mountain.put(402, "search");
        mountain.put(403, "unlock");
        mountain.put(404, "event not found");

        // Path
        mountain.put(410, "As you travel up the mountain, you see a globe of light surrounding what seems to be a person meditating. You should check it out.");
        mountain.put(411, "look");
        mountain.put(412, "search");
        mountain.put(413, "unlock");
        mountain.put(414, "unlock failed");

        // Oracle
        mountain.put(420, "As you approach the figure, you see a man in robes. You feel a dark magic coming off of him. He looks towards you as you get closer");
        mountain.put(421, "You ask the man, 'Who are you? What are you doing here?'");
        mountain.put(422, "The man says, 'I am the Oracle, keeper of knowledge. I am studying dark magic. If you wish, I can enchant your weapon to help you defeat what comes.'"); // maybe increase weapon damage, or maybe increase attack power?
        mountain.put(423, "unlock");
        mountain.put(424, "unlock failed");

        // Peak
        mountain.put(430, "You have made it to the peak of the mountain.");
        mountain.put(431, "You look at the vast landscape from the top of this mountain. In the distance, you can see a desert to left, and a swamp to the right.");
        mountain.put(432, "search");
        mountain.put(433, "unlock");
        mountain.put(434, "unlock failed");

        // DESERT

        // Plain
        desert.put(500, "You travel to the desert. The heat is almost unbearable. In front of you are dunes that go on for what seems like forever.");
        desert.put(501, "look");
        desert.put(502, "search");
        desert.put(503, "unlock");
        desert.put(504, "unlock failed");

        // Dunes
        desert.put(510, "You walk to one of the many large dunes. You decide to look around.");
        desert.put(511, "Once you're on top of the dune, you are able to see an oasis with a town nearby.");
        desert.put(512, "search");
        desert.put(513, "unlock");
        desert.put(514, "unlock failed");

        // Town
        desert.put(520, "You decide to walk to the town and check it out.");
        desert.put(521, "In the town you see a bazaar and go inside.");
        desert.put(522, "You see a bar in the town and decide to go into it. Maybe someone in there will have some advice.");
        desert.put(523, "unlock");
        desert.put(524, "unlock failed");

        // Well
        desert.put(530, "A small well is not far from you. Though looking inside, you see it is completely dry.");
        desert.put(531, "look");
        desert.put(532, "search");
        desert.put(533, "unlock");
        desert.put(534, "unlock failed");

        // SWAMP

        // Mudpits
        swamp.put(600, "You make your way to a swamp. The air is humid. Your feet sink into the soft mud as you walk. Erie sounds come from within the swamp, but you cant tell what is making the noise.");
        swamp.put(601, "look");
        swamp.put(602, "search");
        swamp.put(603, "unlock");
        swamp.put(604, "unlock failed");

        // Wetland
        swamp.put(610, "wetland");
        swamp.put(611, "The ground here is more liquid than solid. Ripples echo across the fetid pools, and you can see the shadows of those that lurk beneath.");
        swamp.put(612, "Digging into the mud is a fruitless endeavor, and sticking your hands randomly into the water probably isn't a good idea. If there's anything to find here, it's too well hidden.");
        swamp.put(613, "unlock");
        swamp.put(614, "unlock failed");

        // Woodland
        swamp.put(620, "woodland");
        swamp.put(621, "The horizon is obscured by dense willows and hanging vines. Pools of sludge are scattered throughout the forest.");
        swamp.put(622, "There are a wide variety of plants to be found, some of which yield fruit."); //TODO: fruit
        swamp.put(623, "unlock");
        swamp.put(624, "unlock failed");

        // FRACTURE

        // Wasteland
        fracture.put(700, "You have entered the fracture. The landscape doesn't seem real. The ground is cracked and broken. Looking into these cracks in the ground, you see strange shapes that can only be described as supernatural. The sky is constantly changing color.");
        fracture.put(701, "The colors cry. The sounds simmer. Spending time here is painful. What could have caused this?");
        fracture.put(702, "You find foreign plants, hints of ancient ruins, and your own memories. Phones ring from behind you. The scent of honeysuckle blends with lacquered wood, and you keep picking up the same rock. It burns. Perhaps you should stop.");
        fracture.put(703, "unlock");
        fracture.put(704, "unlock failed");

        // Edge
        fracture.put(710, "You notice the lights in the sky are leading somewhere, somewhere dark, even evil. You decide to follow them.");
        fracture.put(711, "While following the mysterious path, you found your way to the Edge. It seems as though this is the end of the world. An invisible wall prevents you from moving any further. Looking past the wall, there is an infinite series of floating islands that you will never be able to reach.");
        fracture.put(712, "search");
        fracture.put(713, "unlock");
        fracture.put(714, "unlock failed");

        // Doom
        fracture.put(720, "Big secret");
        fracture.put(721, "You are questioning why you are afraid considering you've already defeated the Silver Slayer. A wave of terror washes over you as you hear [redacted]");
        fracture.put(722, "search");
        fracture.put(723, "unlock");
        fracture.put(724, "unlock failed");

        // Mirrors
        fracture.put(730, "A building in the distance catches your eye. It is completely black, a void color. You decide to check it out.");
        fracture.put(731, "Inside the building all you see is yourself... forever. This building is full of a complex series of mirrors. Good luck finding your way out.");
        fracture.put(732, "search");
        fracture.put(733, "unlock");
        fracture.put(734, "unlock failed");

        // LAIR

        // Gate
        lair.put(800, "Crossing a rickety old bridge, you arrive at the lair. The air is thick with smoke. You only can describe this place as the underworld. Nothing good happens here. Across the bridge you see an onmious building, like a castle, but evil. Around the castle are fields of dead trees. Its looks like there was once a lot of life here.");
        lair.put(801, "look");
        lair.put(802, "search");
        lair.put(803, "unlock");
        lair.put(804, "unlock failed");

        // Village
        lair.put(810, "Exploring the outskirts around the lair, you see a small shack that catches you eye.");
        lair.put(811, "You enter the shack. Inside you see a chest that looks like it hasn't been opened in years. You want to look inside.");
        lair.put(812, "You open the chest and are shocked by whats inside.");
        lair.put(813, "unlock");
        lair.put(814, "unlock failed");

        // Castle
        lair.put(820, "castle");
        lair.put(821, "You see a door that seems to enter some kind of throne room. Loud roars are coming from inside. Whatever is in there, knows your here, and plans to change that very soon. Holding your breath, you open the door and step inside.");
        lair.put(822, "search");
        lair.put(823, "unlock");
        lair.put(824, "unlock failed");

        // Throne
        lair.put(830, "\"Welcome. You took your time coming here, so let us not waste any more.\"");
        lair.put(831, "look");
        lair.put(832, "search");
        lair.put(833, "unlock");
        lair.put(834, "unlock failed");
        lair.put(835, "Congratulations on defeating the Silver Slayer! You now have access to the Silver Sword, but there is still more adventure to be had.");

        eventsSeen = new boolean[8][100];
        for (int c = 0; c < eventsSeen.length; c++) for (int c2 = 0; c2 < eventsSeen[c].length; c2++) eventsSeen[c][c2] = false;

    }

    private String getExactEvent(int locationID, int eventID) {
        /*
        * Get a specific story event
        * 
        * locationID: The ID of the location to pull the event from
        * eventID: A three digit integer referencing the specific event
        */

        eventsSeen[(locationID - 1) / 100][eventID % 100] = true;
        return events.get(locationID).get(eventID);

    }

    private int genEventKey(int l, int s, int offset) {
        /*
        * Mathematically determines an exact event ID based on location and an offset value
        * 
        * l: location ID
        * s: sublocation ID
        * offset: Offset from zero
        */

        return l * 100 + s * 10 + offset;

    }

    public String getBaseEvent(int loc, int sub) {
        /*
        * Get the base event (XX0) for provided location
        * 
        * loc: Location ID
        * sub: Sublocation ID
        */

        return getExactEvent(loc, genEventKey(loc, sub, 0));

    }

    public String getLookEvent(int loc, int sub) {
        /*
        * Get look event (XX1) for provided location
        * 
        * loc: Location ID
        * sub: Sublocation ID
        */

        return getExactEvent(loc, genEventKey(loc, sub, 1));

    }

    public String getSearchEvent(int loc, int sub) {
        /*
        * Get search event (XX2) for provided location
        * 
        * loc: Location ID
        * sub: Sublocation ID
        */

        return getExactEvent(loc, genEventKey(loc, sub, 2));

    }

    public String getUnlockEvent(int loc, int sub, boolean unlocked) {
        /*
        * Get [failed] unlock event (XX3 or XX4) for provided location
        * 
        * loc: Location ID
        * sub: Sublocation ID
        * unlocked: Whether or not the attempted unlock was successful
        */

        if (unlocked) return getExactEvent(loc, genEventKey(loc, sub, 3));
        else return getExactEvent(loc, genEventKey(loc, sub, 4));

    }

    public boolean wasEventSeen(int eventID) {
        /*
        * Reports whether a specific event has been seen
        *
        * eventID: The ID of the event
        */

        return eventsSeen[(eventID - 1) / 100][eventID % 100];

    }

    public void updateEvent(int loc, int eventID, String content) {
        /*
        * Used to dynamically change an event.
        * 
        * loc: Location ID
        * eventID: The exact ID of the event to be changed
        * content: The new event content
        */

        events.get(loc).put(eventID, content);

    }
    
}