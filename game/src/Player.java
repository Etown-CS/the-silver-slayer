public class Player extends Entity {

    public static final String[] names = {"Bitter Java", "Brustel Sprout", "C--", "Dapper Python", "P. H. Periwinkle", "ReacTor", "Saea Quowle"};
    public Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
    public Item currentArmor = null, currentWeapon = null, currentWearable = null;
    public static int location = 1, sublocation = 0, bits = 0, pSwaps = 0, invCap = 5, mountainPathSearches = 0, fractureMirrorMoves = 0;
    public static boolean inCombat = false, inBossfight = false;

    public Player(String title) {

        name = title;
        switch (name) {

            case "Bitter Java":

                healthDefault = 8;
                attack = 3;
                addItem(Database.genItem(19));
                break;

            case "Brustel Sprout":

                healthDefault = 15;
                attack = 1;
                break;

            case "C--":

                healthDefault = 7;
                attack = 4;
                break;

            case "Dapper Python":

                healthDefault = 5;
                attack = 5;
                break;

            case "P. H. Periwinkle":

                healthDefault = 12;
                attack = 2;
                break;

            case "ReacTor":

                healthDefault = 7;
                attack = 3;
                addItem(Database.genItem(20));
                break;

            case "Saea Quowle":

                // Default stats. Use as reference
                healthDefault = 10;
                attack = 3;
                break;

        }

        health = healthDefault;
        initStatuses();

    }

    public int addItem(Item item) {
        /*
        * Add an item to the player's inventory
        * Returns the number of the slot the item was placed in, or -1 if inventory is full
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
        * slot: The inventory slot to clear
        */

        if (inventory[slot] == null) return null;
        String tmp = inventory[slot].name;

        if (inventory[slot] == currentArmor) {

            currentArmor = null;

        } else if (inventory[slot] == currentWeapon) {

            currentWeapon = null;

        } else if (inventory[slot] == currentWearable) {

            currentWearable = null;

        }

        inventory[slot] = null;
        return tmp;
        
    }

    public boolean hasItem(String itemName) {
        /*
        * Performs a linear search on player inventory
        * itemName: The name of the item being searched for
        */

        for (Item i : inventory) if (i != null && i.name.equals(itemName)) return true;
        return false;

    }

    public String listItems() {
        /*
         * Print out entirety of player's inventory
         */

        StringBuilder inv = new StringBuilder(32);

        for (byte c = 0; c < invCap; c++) {

            if (inventory[c] != null) {

                inv.append(c + ": " + inventory[c].name);
                if (inventory[c].equipped) inv.append(" *");
                inv.append("\n");

            } else inv.append(c + ": \n");

        }

        inv.append("\nBits: " + bits);
        return inv.toString();

    }

    public String useItem(int slot) {
        /*
         * Use a particular item.
         * Returns the item's usage message
         * slot: The inventory slot of the item to be used
         */
        
        StringBuilder msg = new StringBuilder(64);
        switch (inventory[slot].type) {
            
            case Junk:

                msg.append("You're not too sure what to do with this...");
                break;

            case Health:

                changeStats(inventory[slot].magnitude, 0, 0);
                msg.append("You ate the " + inventory[slot].name + ".\n");

                if (inventory[slot].magnitude >= 0) msg.append("+" + inventory[slot].magnitude + " health!");
                else msg.append(inventory[slot].magnitude + " health!");
                break;

            case Armor:

                currentArmor = inventory[slot];
                currentArmor.equipped = true;
                msg.append("Equipped armor: " + inventory[slot].name);
                break;

            case Weapon:

                currentWeapon = inventory[slot];
                currentWeapon.equipped = true;
                msg.append("Equipped weapon: " + inventory[slot].name);
                break;

            case Wearable:

                currentWearable = inventory[slot];
                currentWearable.equipped = true;
                msg.append("Equipped wearable: " + inventory[slot].name);

                // TODO: Logic for each wearable

                break;

            default:

                msg.append("This item cannot be used nor equipped.");

        }
        
        if (inventory[slot].consumable) inventory[slot] = null;
        return msg.toString();

    }

    @Override
    public int getAttacked(int dmg) {
        /*
         * Use getAttacked to deal damage to this entity
         * dmg: Incoming damage value
         */
        
        int n = (currentArmor != null) ? dmg - defense - currentArmor.magnitude : dmg - defense;
        if (n < 1) n = 1;
        changeStats(-n, 0, 0);
        return n;

    }

    public boolean travel(String dest) {
        /*
        * Travel to wherever, if you can from where you're at
        * Monstrous method
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

                        if (sublocation != 1 || !Story.wasEventSeen(113)) break;
                        location = 2;
                        sublocation = 0;
                        Audio.activeTrack.stop();
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
                        Audio.activeTrack.stop();
                        return true;

                    case "lake":

                        if (sublocation != 0) break;
                        location = 3;
                        sublocation = 0;
                        Audio.activeTrack.stop();
                        return true;

                    case "mountain":

                        if (sublocation != 0 || !Story.wasEventSeen(203)) break;
                        location = 4;
                        sublocation = 0;
                        Audio.activeTrack.stop();
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
                        Audio.activeTrack.stop();
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
                        Audio.activeTrack.stop();
                        return true;

                    case "desert":

                        if (sublocation != 1 || !Story.wasEventSeen(413)) break;
                        location = 5;
                        sublocation = 0;
                        Audio.activeTrack.stop();
                        return true;

                    case "fracture":

                        if (sublocation != 1 || Locations.Mountain[Locations.Mountain.length - 1].health > 0) break;
                        location = 7;
                        sublocation = 0;
                        Audio.activeTrack.stop();
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

                        if (sublocation != 0 || !Story.wasEventSeen(413)) break;
                        location = 4;
                        sublocation = 1; // Yes this is 1
                        Audio.activeTrack.stop();
                        return true;

                    case "swamp":

                        if (sublocation != 1) break;
                        location = 6;
                        sublocation = 0;
                        Audio.activeTrack.stop();
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
                        Audio.activeTrack.stop();
                        return true;

                    case "lair":

                        if (sublocation == 0) break;
                        location = 8;
                        sublocation = 0;
                        Audio.activeTrack.stop();
                        return true;

                }

                break;

            case 7:

                switch (dest) {

                    case "wasteland":

                        if (fractureMirrorMoves > 0 && fractureMirrorMoves < 7) break;
                        sublocation = 0;
                        return true;

                    case "edge":

                        if (sublocation != 0 || (fractureMirrorMoves > 0 && fractureMirrorMoves < 7)) break;
                        sublocation = 1;
                        return true;

                    case "mirrors":

                        if (sublocation != 0) break;
                        sublocation = 2;
                        return true;

                    case "i<3_tss_123":

                        if (sublocation != 0 || (fractureMirrorMoves > 0 && fractureMirrorMoves < 7)) break;
                        sublocation = 3;
                        return true;

                    case "mountain":

                        if (sublocation > 1 || (fractureMirrorMoves > 0 && fractureMirrorMoves < 7)) break;
                        location = 4;
                        sublocation = 3;
                        Audio.activeTrack.stop();
                        return true;

                    case "lair":

                        if (sublocation > 1 || (fractureMirrorMoves > 0 && fractureMirrorMoves < 7)) break;
                        location = 8;
                        sublocation = 0;
                        Audio.activeTrack.stop();
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
                        Audio.activeTrack.stop();
                        return true;

                }

                break;

        }

        return false;

    }
    
}