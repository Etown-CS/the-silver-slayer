import java.util.Random;

public class Locations {

    private static Random r = new Random();

    public static final String[] locations = {null, "Start", "Village", "Lake", "Mountain", "Desert", "Swamp", "Fracture", "Lair"};
    public static final String[][] sublocations = {
        null,
        {"Field", "Gate"},
        {"Center", "House", "Graveyard", "Well"},
        {"Shore", "Dock", "Water", "Entry"},
        {"Base", "Path", "Oracle", "Peak"},
        {"Plain", "Dune", "Town", "Well"},
        {"Mudpits", "Wetland", "Woodland"},
        {"Wasteland", "Edge", "Mirrors", "???"},
        {"Gate", "Village", "Castle", "Throne"}
    };

    public static final Enemy[] Village = 
        {
            new Enemy("Bug", 3, 1, 0),
            new Enemy("Gremlin", 4, 1, 1),
            new Enemy("Rat", 1, 3, 0),
            new Enemy("Red Flower", 3, 3, 0),
            new Enemy("Scavenger", 8, 0, 0),
            new Enemy("First Guardian", 10, 3, 2, true)
        };
        
    public static final Enemy[] Lake = 
        {
            new Enemy("Bug", 3, 1, 0),
            new Enemy("Eel", 5, 3, 1),
            new Enemy("Gobbler", 5, 3, 2),
            new Enemy("Gremlin", 4, 1, 1),
            new Enemy("Rat", 1, 3, 0),
            new Enemy("Red Flower", 3, 3, 0),
            new Enemy("Waterlogged", 7, 3, 4),
            new Enemy("PISMPE", 2, 0, 0),
            new Enemy("Thief", 10, 3, 3),
            new Enemy("The Big One", 30, 3, 1, true)
        };
        
    public static final Enemy[] Mountain = 
        {
            new Enemy("Air Cooler", 4, 1, 4),
            new Enemy("Bug", 3, 1, 0),
            new Enemy("Gobbler", 5, 3, 2),
            new Enemy("Gremlin", 4, 1, 1),
            new Enemy("Liquid Cooler", 5, 2, 3),
            new Enemy("Phantom", 3, 5, 0),
            new Enemy("Yellow Flower", 3, 3, 0),
            new Enemy("Dat Boi", 6, 7, 0),
            new Enemy("Scavenger", 8, 0, 0),
            new Enemy("PISMPE", 2, 0, 0),
            new Enemy("Banshee", 9, 9, 1),
            new Enemy("Faceless", 6, 1, 5),
            new Enemy("Thief", 10, 3, 3),
            new Enemy("Abominable Snowball", 40, 4, 4, true)
        };
        
    public static final Enemy[] Desert = 
        {
            new Enemy("Bug", 3, 1, 0),
            new Enemy("Gobbler", 5, 3, 2),
            new Enemy("Tumbler", 8, 2, 2),
            new Enemy("Waterlogged", 7, 3, 4),
            new Enemy("Yellow Flower", 3, 3, 0),
            new Enemy("Cyber Scorpion", 5, 9, 2),
            new Enemy("Scavenger", 8, 0, 0),
            new Enemy("Scrambler", 10, 1, 0),
            new Enemy("PISMPE", 2, 0, 0),
            new Enemy("Flashbang", 3, 7, 1),
            new Enemy("Thief", 10, 3, 3),
            new Enemy("Firewall", 60, 10, 5, true)
        };
        
    public static final Enemy[] Swamp = 
        {
            new Enemy("Blue Flower", 3, 3, 0),
            new Enemy("Bug", 3, 1, 0),
            new Enemy("Eel", 5, 3, 1),
            new Enemy("Gobbler", 5, 3, 2),
            new Enemy("Rat", 1, 3, 0),
            new Enemy("Tumbler", 8, 2, 2),
            new Enemy("Waterlogged", 7, 3, 4),
            new Enemy("Scrambler", 10, 1, 0),
            new Enemy("PISMPE", 2, 0, 0),
            new Enemy("Bot Swarm", 30, 1, 20),
            new Enemy("Flashbang", 3, 7, 1),
            new Enemy("Thief", 10, 3, 3),
            new Enemy("Worm", 4, 1, 5),
            new Enemy("Mudgulper", 30, 10, 10, true)
        };
        
    public static final Enemy[] Fracture = 
        {
            new Enemy("Polychromatic Flower", 9, 9, 0),
            new Enemy("Figment", 1, 0, 0),
            new Enemy("Glitch", 1, 1, 1),
            new Enemy("Memory", 15, 1, 0),
            new Enemy("Packet", 5, 5, 2),
            new Enemy("DISCOMBOBU-INATOR", 100, 100, 100, true)
        };
        
    public static final Enemy[] Lair = 
        {
            new Enemy("Dweller", 10, 4, 3),
            new Enemy("Scrambler", 10, 1, 0),
            new Enemy("PISMPE", 2, 0, 0),
            new Enemy("Banshee", 9, 9, 1),
            new Enemy("Bot Swarm", 30, 1, 20),
            new Enemy("Cleanser", 1, 999, 0),
            new Enemy("Faceless", 6, 1, 5),
            new Enemy("Mimic", 50, 0, 0),
            new Enemy("RAT", 6, 10, 0),
            new Enemy("Wirefiend", 5, 5, 3),
            new Enemy("Worm", 4, 1, 5),
            new Enemy("The Silver Slayer", 999, 999, 999, true)
        };

    public static final Enemy[][] enemyIndex = {null, null, Village, Lake, Mountain, Desert, Swamp, Fracture, Lair};

    public static Enemy spawnEnemy(int location, boolean boss) {
        /*
        * Attempt to spawn an enemy.
        *
        * location: Current player location ID
        * boss: Whether to spawn a boss (will always succeed)
        */
    
        if (boss) return enemyIndex[location][enemyIndex[location].length - 1];
        else if (r.nextInt(100) < (location - 1) * 10) return enemyIndex[location][r.nextInt(enemyIndex[location].length - 1)];
        else return null;

    }

}