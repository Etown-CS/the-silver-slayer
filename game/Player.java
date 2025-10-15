public class Player {

    private Menu menuRef;
    public Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
    public Item currentArmor = null, currentWeapon = null, currentWearable = null;
    public String location, sublocation;
    public int health, attack, defense, invCap;

    public Player(Menu refToMenu) {
        /* Constructor */

        health = 3;
        attack = 1;
        defense = 0;
        invCap = 5;

        location = "Start";
        sublocation = "Gate";
        menuRef = refToMenu;

        addItem(new Item("Paper Hat", ItemType.Armor, "A carefully folded origami hat.", 1, false));
        addItem(new Item("Golden Apple", ItemType.Health, "A normal apple, but encased in gold.", -3, true));
        addItem(new Item("Pebble", ItemType.Junk, "It's a small, white pebble.", 0, false));
        addItem(new Item("BBQ Bacon Burger", ItemType.Health, "BBQ. Bacon. Burger.", 5, true));
        addItem(new Item("Comically Large Spoon", ItemType.Weapon, "A spoon of impressive size.", 5, false));

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

        if (health <= 0) {

            health = 0;
            menuRef.gameOver = true;

        }

        if (attack < 0) attack = 0;
        if (defense < 0) defense = 0;

        menuRef.updatePlayerBar(health, attack, defense);

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
        
        String msg = inventory[slot].description;
        switch (inventory[slot].type) {

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

            default:

                break;

        }
        
        if (inventory[slot].consumable) inventory[slot] = null;
        return msg;

    }

    public int attackEnemy(Enemy target) {
        /*
         * Attack an enemy!
         * 
         * target: The enemy object that's under fire
         */

        return target.getAttacked(attack);

    }

    public void playerAttacked(int dmg) {
        /*
         * Use this to deal damage to the player
         * Minimum damage taken cannot be <1
         * 
         * dmg: Incoming damage amount
         */

        dmg = dmg - defense;
        if (dmg < 1) dmg = 1;
        changeStats(-dmg, 0, 0);

    }
    
}