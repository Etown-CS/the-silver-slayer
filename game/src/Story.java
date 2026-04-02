import java.util.HashMap;

public class Story {

    // Messages that can be shown on the title bar of the game window
    public static final String[] TITLE_STRINGS = {"Silver Slayer RPG", "Also try Terraria!", "Also try Minecraft!", "THE FOG IS COMING", 
                                            "There may be an egg", "here come dat boi", "The names Gus... Amon Gus", "water bottle", 
                                            "As I write this, it's 1:30pm on October 3rd, 2025", "[J]ohn, [A]sher, and [M]artin: JAM", 
                                            "Why am I writing these?", "I ate my toothbrush", "o _ o", "get rekt", "lock in", "svlmcmdsfl",
                                            "Low on magenta!", "Strings", "jk jk... unless?", "Remember to cave"};
    
    // Messages that can be displayed when the player flees
    public static final String[] FLEE_STRINGS = {"You can't run forever.", "You got away... for now.", "You've escaped this time.", "You'll be back."};
    
    // Each boss has a unique message for when it's defeated
    public static final String[] BOSS_DEFEATED = {null, null, "The Guardian has fallen. The first of many.", "lake", "The snowball shivers, and then collapses into a pile of slush.", "desert", "swamp", "lair"};
    
    // Messages that can appear on the title bar of the game over popup
    public static final String[] GAME_OVERS = {"How unfortunate", "That's gonna leave a mark", "Better luck some time!", "oof", "bruh.mp3", "Process killed", "CONNECTION TERMINATED"};
    
    // The string that prints when the player enters 'help'
    public static final String HELP_TEXT = "look / ls: Look around yourself\n" +
                                            "search / grep: Search your surroundings\n" +
                                            "goto / cd [place]: Travel to a specified location (eg: goto gate)\n" +
                                            "unlock [code / item]: Unlock something\n" +
                                            "char / chars / whoami: Select a character\n\n" +
                                            "desc / describe [slot #]: Describe the item in the specific inventory slot\n" + 
                                            "use [slot #]: Use an item in the specified inventory slot\n" +
                                            "drop [slot #]: Drop a specific item\n" + 
                                            "atk / attack: Attack the current enemy\n" + 
                                            "flee: Attempt to flee from an ongoing fight\n" + 
                                            "pass: Do nothing; wait.\n\n" + 
                                            "sql: Open the SQL window.\n\n" +
                                            "help: Display this message\n" + 
                                            "save: Save your game\n" + 
                                            "exit / quit: Close the game (save first!)\n" + 
                                            "clear: Clear the terminal\n" + 
                                            "title: Change the title message";

    public static final String header = "The Silver Slayer [beta-1]";
    
    // Data for fishing minigame (fake)
    public static final String[] fNames = {"Asher", "Austin", "Beverly", "Brandon", "Brian", "Cecil", "David", "Dorian", "Eric", "Felix", "Greg", "Harold", "Ingram", "Jason", "Jasper", "John", "Josh", "Kylie", "Lydia", "Martin", "Nick", "Oscar", "Patrick", "Qui", "Randall", "Silas", "Steve", "Steven", "Theresa", "Truman", "Ulysses", "Vector", "Wanita", "Xander", "Yennifer", "Zachary"};
    public static final String[] lNames = {"Armstrong", "Brown", "Conrad", "Davis", "Evans", "Ferguson", "Garcia", "Henderson", "Iglehart", "Johnson", "Jones", "Krafton", "Li", "McGovern", "Norris", "Oinyoin", "Pork", "Quinn", "Rodriguez", "Smith", "Thompson", "Ulgrith", "Veriton", "Williams", "Wilson", "X", "Yearsley", "Zorin"};

    /*
    * The original idea behind this class was to store all the story elements here
    * This way the events won't get in the way of the code
    * 
    * Every major location has its own HashMap consisting of event IDs and then the events themselves
    * Events ending in 0 are the events that occur when entering a location for the first time
    * Events ending in 1 are what happens when you run look.
    * Similarly, events ending in 2 are the search events, 3 is for an unlock, and 4 is for failing to unlock
    * 5 is for the event that shows when arriving somewhere after the first time
    * There's one final, over-arching HashMap that holds all the others
    * 
    * Cave & Mine are omitted here because they're implemented exclusively in C
    * Also, this allows us to use 3 digit locations IDs.
    */
    private static HashMap<Integer, String> start = new HashMap<>();
    private static HashMap<Integer, String> village = new HashMap<>();
    private static HashMap<Integer, String> lake = new HashMap<>();
    private static HashMap<Integer, String> mountain = new HashMap<>();
    private static HashMap<Integer, String> desert = new HashMap<>();
    private static HashMap<Integer, String> swamp = new HashMap<>();
    private static HashMap<Integer, String> fracture = new HashMap<>();
    private static HashMap<Integer, String> lair = new HashMap<>();
    private static HashMap<Integer, HashMap<Integer, String>> events = new HashMap<Integer, HashMap<Integer, String>>();
    
    // A 2D boolean array that keeps track of which events have been shown
    private static boolean[][] eventsSeen;

    public static void initStory() {
        /*
        * Populates all story HashMaps
        */

        // START

        // Field
        start.put(100, "The place where it began.");
        start.put(101, "You stand in an empty field of verdant grass. The path you came from trails away behind, and ahead, a dark, iron gate stands waiting. The endless expanse of green grass extends to the horizon, while the gate waits patiently.");
        start.put(102, "A wooden signpost stands nearby. The text has long since worn away. At its base sits a small, metal crate. A silver-lined keyhole resides front and center.");
        start.put(103, "OPENED THE BOX");
        start.put(104, "You attempt to pry the box open, but it remains sealed. You're not getting into this without either special tools or the original key.");
        start.put(105, "It's quiet here. No one else has come this way.");

        // Gate
        start.put(110, "You step up to the gate. Despite the lack of travelers, it's locked. A rusted, yet entirely solid padlock hangs on a chain wrapped between the two doors.");
        start.put(111, "The gate is old and weathered. The lock features eight numerical combination wheels, and there's an interesting inscription etched into the bottom:\n\nODM5MjczNTQ=");
        start.put(112, "There's nothing to be found in the nearby vicinity. Nothing seems to have been disturbed prior to your arrival.");
        start.put(113, "The lock clicks open, the chain falls to the ground, and you're able to push the gate open with little resistance. The doors swing wide with a long, metallic screech. The path forward is open to you.");
        start.put(114, "The lock is unresponsive.");
        start.put(115, "The occassional breeze causes a gentle creaking in the iron.");

        // VILLAGE

        // Center
        village.put(200, "You arrive at the village. The buildings are worn from years of neglect and misuse. You don't see any signs of life around. At first glance, it seems like you are alone.");
        village.put(201, "Rows of abandoned houses lie in silence, and debris lines the streets. A dry well stands in the corner of the square, and a graveyard rests just beyond. Opposite the way you entered from lies a road leading up into the mountains. Another gate blocks this way, with another eight digit padlock attached.");
        village.put(202, "You scavenge amongst the debris. There's nothing of value to be found, and you give up after realizing you look like another desperate looter.");
        village.put(203, "You hear the click of the lock coming open. This gate is tougher to open, but you manage to shove the doors aside, leaving divets in the dirt. The road to the mountains is clear.");
        village.put(204, "The lock hangs with indifference.");
        village.put(205, "You return to the village. The buildings are worn from years of neglect and misuse. You don't see any signs of life around. At first glance, it seems like you are alone.");

        // House
        village.put(210, "A particular house catches your eye. It seems less worn then the rest of the buildings, so you decide to take a closer look. You go inside.");
        village.put(211, "The house appears to be completely furnished. Nothing is moved it makes you wonder if there are people still living here. You look at the kitchen table to see a fully cooked meal. The scary part is that you can still see steam coming off the food. On the table is a Silver Spoon. You want to pick it up.");
        village.put(212, "You pick up the Silver Spoon.");
        village.put(213, "unlock");
        village.put(214, "failed unlock");
        village.put(215, "The house remains as you had left it previously, with no others signs of disturbance. The dust has already begun to settle again.");

        // Graveyard
        village.put(220, "The village graveyard is vast, with many gravestones in perfect rows. You decide to explore.");
        village.put(221, "Walking up to the gravestones, you begin to read the names on them. Some names you may recognize, but who is Smitty Werbenjagermanjenson?");
        village.put(222, "Digging through a graveyard seems like a bad idea for several reasons. However, you do find a paper hat sitting on an unmarked stone. Someone placed this here.");
        village.put(223, "unlock");
        village.put(224, "failed unlock");
        village.put(225, "The village graveyard is vast, with many gravestones in perfect rows.");

        // Well
        village.put(230, "In the center of the village there is a well. Must have been the water source for everyone. You decide to take a look inside.");
        village.put(231, "A bucket is sitting at the bottom of this dry well. Thats what they must have used to retrieve the water.");
        village.put(232, "You scrounge around the well and peer down the pit. There's the bucket, but beyond that, there's nothing here.\nYou're not taking the bucket.");
        village.put(233, "unlock");
        village.put(234, "failed unlock");
        village.put(235, "Wellcome back.");

        // LAKE

        // Shore
        lake.put(300, "You voyage to a great lake. The water is completely still, but you can see strange looking creatures moving underneath the surface.");
        lake.put(301, "The lake is massive, with smooth water and no islands. A nearby wooden dock has fallen into disrepair, presumably having been abandoned just like the village.");
        lake.put(302, "Scrounging along the edge of the lake yields a variety of different rocks. One in particular is quite interesting.");
        lake.put(303, "unlock");
        lake.put(304, "failed unlock");
        lake.put(305, "The water is completely still. The lake's inhabitants continue their business, either unaware or uncaring of your presence.");

        // Dock
        lake.put(310, "You have stumbled upon a dock. This could be a good place to phish, but for what?");
        lake.put(311, "You see a tackle box on the dock.");
        lake.put(312, "You find goggles and bait in the tackle box. Oddly the bait is a bunch of emails. You also notice a weird smell coming from it.");
        lake.put(313, "unlock");
        lake.put(314, "failed unlock");

        // Water
        lake.put(320, "You decide to take a swim! You dive into the water.");
        lake.put(321, "The lake is deep, and the water is murky. Your vision is too distorted to make anything out.");
        lake.put(322, "You lake is large and surprisingly clean. There's nothing to be found within its waters. (No, you can't grab a fish.)");
        lake.put(323, "unlock");
        lake.put(324, "failed unlock");
        lake.put(325, "You once again leap into the water. It hasn't warmed up since last time.");

        // Entrance
        lake.put(330, "A dark tunnel leads downwards into the depths. It's difficult to C very far.");
        lake.put(331, "The cave entrance is surrounded by dense plant life, to the point where you'd never have found it without knowing something was here.");
        lake.put(332, "There's an excessive amount of plant debris. No one has passed through here in quite some time.");
        lake.put(333, "unlock");
        lake.put(334, "failed unlock");
        lake.put(335, "A dark tunnel leads downwards into the depths.");

        // Phishing - chance to catch someones personal info

        lake.put(340, "Using the emails you got from the tackle box, you are now able to phish.");
        // lake.put(341, "You cast your rod into the water hoping to catch something.");
        // lake.put(342, "You have caught someones personal information!");
        // lake.put(343, "Your line broke.");


        // MOUNTAIN

        // Base
        mountain.put(400, "You arrive at the base of a mountain. A long, steep path looks like it makes it way to the peak, but you cant see the top from here due to the winter storm.");
        mountain.put(401, "look");
        mountain.put(402, "search");
        mountain.put(403, "unlock");
        mountain.put(404, "event not found");
        mountain.put(405, "new base");

        // Path
        mountain.put(410, "As you travel up the mountain, you see a globe of light surrounding what seems to be a person meditating. You should check it out.");
        mountain.put(411, "The weathered post marks the location where the path splits into three. It seems one path leads down the back of the mountain towards the desert, another further up the mountain, and the last to... somewhere? The text is scratched away, seemingly intentionally. The path it indicates withers away after only a few steps. The road to the desert is blocked by a wooden palisade, held shut by an enourmous metal deadbolt. A series of rusted chains converge on the bolt, holding it in place. Perhaps something with sufficient weight could smash the ball of chains apart...");
        mountain.put(412, "Tufts of coarse grass and hardy, knotted trees dot the surroundings. There's nothing to be found in the immediate vicinity. You could, however, try searching again to find where the missing path used to lead.");
        mountain.put(413, "The wooden club easily smashes the rusted chain. You painstakingly heft the deadbolt out of the clasp and slide it open. The desert path is now accessible.");
        mountain.put(414, "That didn't do it.");
        mountain.put(415, "new base");

        // Oracle
        mountain.put(420, "As you approach the figure, you see a man in robes. You feel a dark magic coming off of him. He looks towards you as you get closer. You ask the man, 'Who are you? What are you doing here?' The man says, 'I am the Oracle, keeper of knowledge. I am studying dark magic. If you wish, I can enchant your weapon to help you defeat what comes.'"); // TODO: maybe increase weapon damage, or maybe increase attack power?
        mountain.put(421, "look");
        mountain.put(422, "The few living plants give the Oracle a wide berth. Scattered branches litter the area. One of them is much larger than the others...");
        mountain.put(423, "unlock");
        mountain.put(424, "unlock failed");
        mountain.put(425, "The Oracle looks towards you as you get closer. He seems unsurprised to see you.");

        // Peak
        mountain.put(430, "You have made it to the peak of the mountain.");
        mountain.put(431, "You look at the vast landscape from the top of this mountain. In the distance, you can see a desert to left, and a swamp to the right.");
        mountain.put(432, "There are mostly just drifts of snow; however, there is a curious glint half buried in a nearby mound.");
        mountain.put(433, "unlock");
        mountain.put(434, "unlock failed");
        mountain.put(435, "new base");

        // DESERT

        // Plain
        desert.put(500, "You travel to the desert. The heat is almost unbearable. In front of you are dunes that go on for what seems like forever.");
        desert.put(501, "look");
        desert.put(502, "search");
        desert.put(503, "unlock");
        desert.put(504, "unlock failed");
        desert.put(505, "new base");

        // Dunes
        desert.put(510, "You walk to one of the many large dunes. You decide to look around.");
        desert.put(511, "Once you're on top of the dune, you are able to see an oasis with a town nearby.");
        desert.put(512, "search");
        desert.put(513, "unlock");
        desert.put(514, "unlock failed");
        desert.put(515, "new base");

        // Town
        desert.put(520, "You decide to walk to the town and check it out.");
        desert.put(521, "In the town you see a bazaar and go inside.");
        desert.put(522, "You see a bar in the town and decide to go into it. Maybe someone in there will have some advice.");
        desert.put(523, "unlock");
        desert.put(524, "unlock failed");
        desert.put(525, "new base");

        // Well
        desert.put(530, "A small well is not far from you. Though looking inside, you see it is completely dry.");
        desert.put(531, "The well is the only landmark in this area. What a random structure.");
        desert.put(532, "This one doesn't even have any water, let alone a bucket.");
        desert.put(533, "unlock");
        desert.put(534, "unlock failed");
        desert.put(535, "new base");

        // SWAMP

        // Mudpits
        swamp.put(600, "You make your way to a swamp. The air is humid. Your feet sink into the soft mud as you walk. Erie sounds come from within the swamp, but you cant tell what is making the noise.");
        swamp.put(601, "look");
        swamp.put(602, "search");
        swamp.put(603, "unlock");
        swamp.put(604, "unlock failed");
        swamp.put(605, "new base");

        // Wetland
        swamp.put(610, "The ground here is more liquid than solid. Ripples echo across the fetid pools, and you can see the shadows of those that lurk beneath.");
        swamp.put(611, "Shallow mounds and pools of water stretch across the landscape. The sound of insects is intense.");
        swamp.put(612, "Digging into the mud is a fruitless endeavor, and sticking your hands randomly into the brackish water probably isn't a good idea. If there's anything to find here, it's too well hidden.");
        swamp.put(613, "unlock");
        swamp.put(614, "unlock failed");
        swamp.put(615, "new base");

        // Woodland
        swamp.put(620, "A dark, dense forest. An ominous mist lingers on the ground, making it difficult to see what you're stepping in to.");
        swamp.put(621, "The horizon is obscured by dense willows and hanging vines. Pools of sludge are scattered throughout the forest.");
        swamp.put(622, "There are a wide variety of plants to be found, some of which yield fruit. These may or may not be safe to eat.");
        swamp.put(623, "unlock");
        swamp.put(624, "unlock failed");
        swamp.put(625, "new base");

        // FRACTURE

        // Wasteland
        fracture.put(700, "You have entered the fracture. The landscape doesn't seem real. The ground is cracked and broken. Looking into these cracks in the ground, you see strange shapes that can only be described as supernatural. The sky is constantly changing color.");
        fracture.put(701, "The colors cry. The sounds simmer. Spending time here is painful. What could have caused this?");
        fracture.put(702, "You find foreign plants, hints of ancient ruins, and your own memories. Phones ring from behind you. The scent of honeysuckle blends with lacquered wood, and you keep picking up the same rock. It burns. Perhaps you should stop.");
        fracture.put(703, "unlock");
        fracture.put(704, "unlock failed");
        fracture.put(705, "new base");

        // Edge
        fracture.put(710, "You notice the lights in the sky are leading somewhere, somewhere dark, even evil. You decide to follow them.");
        fracture.put(711, "While following the mysterious path, you found your way to the Edge. It seems as though this is the end of the world. An invisible wall prevents you from moving any further. Looking past the wall, there is an infinite series of floating islands that you will never be able to reach.");
        fracture.put(712, "search");
        fracture.put(713, "unlock");
        fracture.put(714, "unlock failed");
        fracture.put(715, "new base");

        // Doom
        fracture.put(720, "Big secret");
        fracture.put(721, "You are questioning why you are afraid considering you've already defeated the Silver Slayer. A wave of terror washes over you as you hear [redacted]");
        fracture.put(722, "search");
        fracture.put(723, "unlock");
        fracture.put(724, "unlock failed");
        fracture.put(725, "new base");

        // Mirrors
        fracture.put(730, "A building in the distance catches your eye. It is completely black, a void color. You decide to check it out.");
        fracture.put(731, "Inside the building all you see is yourself... forever. This building is full of a complex series of mirrors. Good luck finding your way out.");
        fracture.put(732, "search");
        fracture.put(733, "unlock");
        fracture.put(734, "unlock failed");
        fracture.put(735, "new base");

        // LAIR

        // Gate
        lair.put(800, "Crossing a rickety old bridge, you arrive at the lair. The air is thick with smoke. You only can describe this place as the underworld. Nothing good happens here. Across the bridge you see an onmious building, like a castle, but evil. Around the castle are fields of dead trees. Its looks like there was once a lot of life here.");
        lair.put(801, "look");
        lair.put(802, "search");
        lair.put(803, "unlock");
        lair.put(804, "unlock failed");
        lair.put(805, "new base");

        // Shack
        lair.put(810, "Exploring the outskirts around the lair, you see a small shack that catches you eye.");
        lair.put(811, "You enter the shack. Inside you see a chest that looks like it hasn't been opened in years. You want to look inside.");
        lair.put(812, "You open the chest and are shocked by whats inside.");
        lair.put(813, "unlock");
        lair.put(814, "unlock failed");
        lair.put(815, "new base");

        // Castle
        lair.put(820, "castle");
        lair.put(821, "You see a door that seems to enter some kind of throne room. Loud roars are coming from inside. Whatever is in there, knows your here, and plans to change that very soon. Holding your breath, you open the door and step inside.");
        lair.put(822, "search");
        lair.put(823, "unlock");
        lair.put(824, "unlock failed");
        lair.put(825, "new base");

        // Throne
        lair.put(830, "\"Welcome. You took your time coming here, so let us not waste any more.\"");
        lair.put(831, "look");
        lair.put(832, "search");
        lair.put(833, "unlock");
        lair.put(834, "unlock failed");
        lair.put(835, "new base");
        lair.put(839, "Congratulations on defeating the Silver Slayer! You now have access to the Silver Sword, but there is still more adventure to be had.");

        // Populating top-level map
        events.put(1, start);
        events.put(2, village);
        events.put(3, lake);
        events.put(4, mountain);
        events.put(5, desert);
        events.put(6, swamp);
        events.put(7, fracture);
        events.put(8, lair);

        // Fill arrays w/ 'false'
        eventsSeen = new boolean[8][100];
        for (int c = 0; c < eventsSeen.length; c++) for (int c2 = 0; c2 < eventsSeen[c].length; c2++) eventsSeen[c][c2] = false;

    }

    private static String getExactEvent(int locationID, int eventID) {
        /*
        * Get a specific story event
        * locationID: The ID of the location to pull the event from
        * eventID: A three digit integer referencing the specific event
        */
        
        eventsSeen[locationID - 1][eventID % 100] = true;
        return events.get(locationID).get(eventID);

    }

    private static int genEventKey(int l, int s, int offset) {
        /*
        * Mathematically determines an exact event ID based on location and an offset value
        * l: location ID
        * s: sublocation ID
        * offset: Offset from zero
        */

        return l * 100 + s * 10 + offset;

    }

    public static String getBaseEvent(int loc, int sub) {
        /*
        * Get the base event (XX0) for provided location
        * loc: Location ID
        * sub: Sublocation ID
        */

        if (wasEventSeen(genEventKey(loc, sub, 0))) return getExactEvent(loc, genEventKey(loc, sub, 5));
        else return getExactEvent(loc, genEventKey(loc, sub, 0));

    }

    public static String getLookEvent(int loc, int sub) {
        /*
        * Get look event (XX1) for provided location
        * loc: Location ID
        * sub: Sublocation ID
        */

        return getExactEvent(loc, genEventKey(loc, sub, 1));

    }

    public static String getSearchEvent(int loc, int sub) {
        /*
        * Get search event (XX2) for provided location
        * loc: Location ID
        * sub: Sublocation ID
        */

        return getExactEvent(loc, genEventKey(loc, sub, 2));

    }

    public static String getUnlockEvent(int loc, int sub, boolean unlocked) {
        /*
        * Get [failed] unlock event (XX3 or XX4) for provided location
        * loc: Location ID
        * sub: Sublocation ID
        * unlocked: Whether or not the attempted unlock was successful
        */

        if (unlocked) return getExactEvent(loc, genEventKey(loc, sub, 3));
        else return getExactEvent(loc, genEventKey(loc, sub, 4));

    }

    public static boolean wasEventSeen(int eventID) {
        /*
        * Reports whether a specific event has been seen
        * eventID: The ID of the event
        */
        
        return eventsSeen[eventID / 100 - 1][eventID % 100];

    }

    public static void updateEvent(int eventID, String content) {
        /*
        * Used to dynamically change an event.
        * 
        * loc: Location ID
        * eventID: The exact ID of the event to be changed
        * content: The new event content
        */

        events.get(eventID / 100).put(eventID, content);

    }
    
}