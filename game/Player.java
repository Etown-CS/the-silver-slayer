public class Player extends Entity {

    public static final String[] names = {"Bitter Java", "Brustel Sprout", "C--", "Dapper Python", "P. H. Periwinkle", "ReacTor", "Saea Quowle"};
    public Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
    public static boolean[] gates = {false};
    public Item currentArmor = null, currentWeapon = null, currentWearable = null;
    public int invCap = 5;
    public static int location = 1, sublocation = 0;
    public static boolean inCombat = false, inBossfight = false;

    public Player(String title) {
        /* Constructor */

        name = title;
        switch (name) {

            case "Bitter Java":

                healthDefault = 4;
                attack = 1;
                break;

            case "Brustel Sprout":

                healthDefault = 5;
                attack = 1;
                break;

            case "C--":

                healthDefault = 3;
                attack = 2;
                break;

            case "Dapper Python":

                healthDefault = 300;
                attack = 30;
                break;

            case "P. H. Periwinkle":

                healthDefault = 4;
                attack = 1;
                break;

            case "ReacTor":

                healthDefault = 6;
                attack = 1;
                break;

            case "Saea Quowle":

                healthDefault = 1;
                attack = 4;
                break;

        }

        health = healthDefault;
        initStatuses();

    }

    public int addItem(Item item) {
        /*
        * Add an item to the player's inventory
        * Returns the number of the slot the item was placed in, or -1 if inventory is full
        * 
        * item: The item to be added
        */

        for (int c = 0; c < invCap; c++) {

            if (inventory[c] == null) {

                inventory[c] = item;
                return c;

            }

        }

        return -1;

    }

    public String removeItem(int slot) {
        /*
        * Remove an item from the player's inventory
        * 
        * slot: The inventory slot to clear
        */

        if (inventory[slot] == null) return null;
        String tmp = inventory[slot].name;

        if (inventory[slot] == currentArmor) {

            changeStats(0, 0, -currentArmor.magnitude);
            currentArmor = null;

        } else if (inventory[slot] == currentWeapon) {

            changeStats(0, -currentWeapon.magnitude, 0);
            currentWeapon = null;

        } else if (inventory[slot] == currentWearable) {

            currentWearable = null;

        }

        inventory[slot] = null;
        return tmp;
        
    }

    public String listItems() {
        /*
         * Print out entirety of player's inventory
         */

        StringBuilder inv = new StringBuilder(32);

        for (byte c = 0; c < invCap; c++) {

            if (inventory[c] != null) {

                inv.append("Slot " + c + ": " + inventory[c].name);
                if (inventory[c] == currentArmor || inventory[c] == currentWeapon || inventory[c] == currentWearable) inv.append(" *");
                inv.append("\n");

            } else inv.append("Slot " + c + ": \n");

        }

        return inv.toString();

    }

    public String useItem(int slot) {
        /*
         * Use a particular item.
         * Returns the item's usage message
         * 
         * slot: The inventory slot of the item to be used
         */
        
        String msg = "";
        switch (inventory[slot].type) {
            
            case Unassigned:
            case Junk:

                msg = "You're not too sure what to do with this...";
                break;

            case Health:

                changeStats(inventory[slot].magnitude, 0, 0);
                msg = "You ate the " + inventory[slot].name + ".\n";

                // Extra flavor text
                switch (inventory[slot].name) {

                    case "Golden Apple":

                        msg += "Eating gold is not good for you.\n";
                        break;

                }

                if (inventory[slot].magnitude >= 0) msg += "+" + inventory[slot].magnitude + " health!";
                else msg += inventory[slot].magnitude + " health!";
                break;

            case Armor:

                if (currentArmor != null) defense -= currentArmor.magnitude;
                currentArmor = inventory[slot];
                changeStats(0, 0, inventory[slot].magnitude);
                msg = "Equipped armor: " + inventory[slot].name;
                break;

            case Weapon:

                if (currentWeapon != null) attack -= currentWeapon.magnitude;
                currentWeapon = inventory[slot];
                changeStats(0, inventory[slot].magnitude, 0);
                msg = "Equipped weapon: " + inventory[slot].name;
                break;

            case Wearable:

                // Will likely need manual implemtations for each item
                break;

        }
        
        if (inventory[slot].consumable) inventory[slot] = null;
        return msg;

    }

    public boolean travel(String dest) {
        /*
        * Travel to wherever, if you can from where you're at
        * Monstrous method
        * 
        * dest: Destination name
        */
        
        switch (location) {

            case 1:

                switch (dest) {

                    case "field":

                        sublocation = 0;
                        return true;

                    case "gate":

                        sublocation = 1;
                        return true;

                    case "village":

                        if (sublocation != 1 || !gates[0]) break;
                        location = 2;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;

            case 2:

                switch (dest) {

                    case "center":

                        sublocation = 0;
                        return true;

                    case "house":

                        if (sublocation == 2) break;
                        sublocation = 1;
                        return true;

                    case "graveyard":

                        if (sublocation != 0) break;
                        sublocation = 2;
                        return true;

                    case "well":

                        if (sublocation == 2) break;
                        sublocation = 3;
                        return true;

                    case "start":

                        if (sublocation != 0) break;
                        location = 1;
                        sublocation = 1;
                        Audio.activeBG.command();
                        return true;

                    case "lake":

                        if (sublocation != 0) break;
                        location = 3;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                    case "mountain":

                        if (sublocation != 0) break;
                        location = 4;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;

            case 3:

                switch (dest) {

                    case "shore":

                        sublocation = 0;
                        return true;

                    case "dock":

                        if (sublocation != 0) break;
                        sublocation = 1;
                        return true;

                    case "water":

                        if (sublocation != 1) break;
                        sublocation = 2;
                        return true;

                    case "entry":

                        if (sublocation == 2) break;
                        sublocation = 3;
                        return true;

                    case "village":

                        if (sublocation != 0) break;
                        location = 2;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;
            
            case 4:

                switch (dest) {

                    case "base":

                        if (sublocation > 1) break;
                        sublocation = 0;
                        return true;

                    case "path":

                        if (sublocation == 3) break;
                        sublocation = 1;
                        return true;

                    case "oracle":

                        if (sublocation < 1) break;
                        sublocation = 2;
                        return true;

                    case "peak":

                        if (sublocation < 2) break;
                        sublocation = 3;
                        return true;

                    case "village":

                        if (sublocation != 0) break;
                        location = 2;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                    case "desert":

                        if (sublocation != 1) break;
                        location = 5;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                    case "fracture":

                        if (sublocation != 1) break;
                        location = 7;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;

            case 5:

                switch (dest) {

                    case "plain":
                    case "plains":

                        sublocation = 0;
                        return true;

                    case "dune":
                    case "dunes":

                        if (sublocation != 0) break;
                        sublocation = 1;
                        return true;

                    case "town":

                        if (sublocation > 2) break;
                        sublocation = 2;
                        return true;

                    case "well":

                        if (sublocation == 2) break;
                        sublocation = 3;
                        return true;

                    case "mountain":

                        if (sublocation != 0) break;
                        location = 4;
                        sublocation = 1; // Yes this is 1
                        Audio.activeBG.command();
                        return true;

                    case "swamp":

                        if (sublocation != 1) break;
                        location = 6;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;

            case 6:

                switch (dest) {

                    case "mudpit":
                    case "mudpits":

                        sublocation = 0;
                        return true;

                    case "wetland":
                    case "wetlands":

                        sublocation = 1;
                        return true;

                    case "woodland":
                    case "woodlands":

                        sublocation = 2;
                        return true;

                    case "desert":

                        if (sublocation != 0) break;
                        location = 5;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                    case "lair":

                        if (sublocation == 0) break;
                        location = 8;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;

            case 7:

                switch (dest) {

                    case "wasteland":

                        sublocation = 0;
                        return true;

                    case "edge":

                        if (sublocation != 0) break;
                        sublocation = 1;
                        return true;

                    case "mirrors":

                        if (sublocation != 0) break;
                        sublocation = 2;
                        return true;

                    case "i<3_tss_123":

                        if (sublocation != 0) break;
                        sublocation = 3;
                        return true;

                    case "mountain":

                        if (sublocation > 1) break;
                        location = 4;
                        sublocation = 3;
                        Audio.activeBG.command();
                        return true;

                    case "lair":

                        if (sublocation > 1) break;
                        location = 8;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;

            case 8:

                switch (dest) {

                    case "gate":

                        if (sublocation > 1) break;
                        sublocation = 0;
                        return true;

                    case "village":

                        if (sublocation == 3) break;
                        sublocation = 1;
                        return true;

                    case "castle":

                        if (sublocation != 1) break;
                        sublocation = 2;
                        return true;

                    case "throne":

                        if (sublocation < 2) break;
                        sublocation = 3;
                        return true;

                    case "swamp":

                        if (sublocation != 0) break;
                        location = 6;
                        sublocation = 0;
                        Audio.activeBG.command();
                        return true;

                }

                break;

        }

        return false;

    }

    public static String getAvailablePlaces() {

        StringBuilder places = new StringBuilder(64);

        switch (location) {

            case 1:

                if (sublocation == 0) places.append(Locations.sublocations[1][1]);
                else places.append(Locations.sublocations[1][0] + " | " + Locations.locations[2].toUpperCase());
                break;

            case 2:

                if (sublocation == 0) places.append(Locations.sublocations[2][1] + " | " + Locations.sublocations[2][2] + " | " + Locations.sublocations[2][3] + " | " + Locations.locations[1].toUpperCase() + " | " + Locations.locations[3].toUpperCase() + " | " + Locations.locations[4].toUpperCase());
                else if (sublocation == 1) places.append(Locations.sublocations[2][0] + " | " + Locations.sublocations[2][3]);
                else if (sublocation == 2) places.append(Locations.sublocations[2][0]);
                else places.append(Locations.sublocations[2][0] + " | " + Locations.sublocations[2][1]);
                break;

            case 3:

                if (sublocation == 0) places.append(Locations.sublocations[3][1] + " | " + Locations.sublocations[3][3] + " | " + Locations.locations[2].toUpperCase());

            case 4:

            case 5:

            case 6:

            case 7:

            case 8:

            default:

                places.append("Well that's a problem!");
                break;

        }

        return places.toString();

    }
    
}