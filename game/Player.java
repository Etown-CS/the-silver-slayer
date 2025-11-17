public class Player {

    private Menu menuRef;
    public SelectedPlayer character;
    public Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
    public Item currentArmor = null, currentWeapon = null, currentWearable = null;
    public String name;
    public int health, healthCap, attack, defense = 0, invCap = 5;
    public static int location, sublocation;

    public Player(Menu refToMenu, SelectedPlayer c) {
        /* Constructor */

        // set player to selected character
        character = c;
        setInitialCharacterStats(c);

        location = 1;
        sublocation = 1;
        menuRef = refToMenu;

    }

    private void setInitialCharacterStats(SelectedPlayer character) {
        /*
         * Set initial stats
         * 
         * character: Which playable character
         */

        switch (character) {

            case Bitter_Java:

                name = "Bitter Java";
                health = 3;
                healthCap = 3;
                attack = 1;
                addItem(new Item("Dregs", ItemType.Health, "The remnants of making a hot drink.", 1, true));
                break;

            case Brustel_Sprout:

                name = "Brustel Sprout";
                health = 3;
                healthCap = 5;
                attack = 1;
                addItem(new Item("Sprout", ItemType.Health, "An extra sprout!", 2, true));
                break;

            case C__:

                name = "C--";
                health = 3;
                healthCap = 3;
                attack = 2;
                addItem(new Item("Copper Shortsword", ItemType.Weapon, "A small, short sword made of a weak metal.", 1, false));
                break;

            case Dapper_Python:

                name = "Dapper Python";
                health = 2;
                healthCap = 2;
                attack = 3;
                addItem(new Item("Fang", ItemType.Weapon, "It fell out.", 1, false));
                break;

            case P_H_Periwinkle:

                name = "P. H. Periwinkle";
                health = 4;
                healthCap = 4;
                attack = 1;
                addItem(new Item("Old Book", ItemType.Armor, "It has no pages left.", 1, false));
                break;

            case ReacTor:

                name = "ReacTor";
                health = 3;
                healthCap = 3;
                attack = 1;
                addItem(new Item("Depleted Rod", ItemType.Armor, "An empty fuel rod. It's large enough to put an arm through.", 1, false));
                break;

            case Saea_Quowle:

                name = "Saea Quowle";
                health = 1;
                healthCap = 1;
                attack = 5;
                addItem(new Item("Needle", ItemType.Weapon, "A weapon often used by foreign kingdoms.", 1, false));
                break;

        }

    }

    public void changeStats(int H, int A, int D) {
        /*
         * Change player stats
         * 
         * H: Mod health
         * A: Mod attack
         * D: Mod defense
         */

        health += H;
        attack += A;
        defense += D;

        if (health > healthCap) health = healthCap;
        else if (health < 0) health = 0;
        if (attack < 0) attack = 0;
        if (defense < 0) defense = 0;

        menuRef.updatePlayerBar();

    }

    public int addItem(Item item) {
        /*
         * Add an item to the player's inventory
         * Returns the number of the slot the item was placed in, or -1 if the inventory is full
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
         * Remove an item from the players inventory
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

        }

        inventory[slot] = null;
        return tmp;
        
    }

    public String listItems() {
        /*
         * Print out entirety of player's inventory
         */

        String inv = "";
        int count = 0;

        for (int c = 0; c < invCap; c++) {

            if (inventory[c] != null) {

                inv += "Slot " + c + ": " + inventory[c].name;
                if (inventory[c] == currentArmor || inventory[c] == currentWeapon || inventory[c] == currentWearable) inv += " *";
                inv += "\n";
                count++;

            } else inv += "Slot " + c + ": \n";

        }

        return inv + "\n(" + count + " items total)";

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

    public int playerAttacked(int dmg) {
        /*
         * Use this to deal damage to the player
         * Minimum damage taken cannot be <1
         * 
         * dmg: Incoming damage amount
         */

        // TODO: Dodge chance? Maybe an item does this

        dmg = dmg - defense;
        if (dmg < 1) dmg = 1;
        changeStats(-dmg, 0, 0);
        return dmg;

    }
    
}