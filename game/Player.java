public class Player {

    private Menu menuRef;
    public Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
    public Item currentWeapon = null;
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

        addItem(new Item("Cookie", ItemType.Health, "You ate the cookie.\n+3 health!", 3, true));
        addItem(new Item("Golden Apple", ItemType.Health, "Eating gold is not good for you.\n-3 health!", -3, true));
        addItem(new Item("Pebble", ItemType.Junk, "It's a small, white pebble.", 0, false));
        addItem(new Item("BBQ Bacon Burger", ItemType.Health, "You ate the BBQ Bacon Burger.\n+5 health!", 5, true));
        addItem(new Item("Comically Large Spoon", ItemType.Weapon, "Equipped the Comically Large Spoon.", 5, false));

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
        if (inventory[slot] == currentWeapon) currentWeapon = null;
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
                if (inventory[c] == currentWeapon) inv += " *";
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
        
        String msg = inventory[slot].useMessage;
        if (inventory[slot].type == ItemType.Health) changeStats(inventory[slot].magnitude, 0, 0);
        else if (inventory[slot].type == ItemType.Weapon) currentWeapon = inventory[slot];
        if (inventory[slot].consumable) inventory[slot] = null;
        return msg;

    }

    public void attackEnemy(Enemy target) {
        /*
         * Attack an enemy!
         * 
         * target: The enemy object that's under fire
         */

        if (currentWeapon == null) target.getAttacked(attack);
        else target.getAttacked(attack + currentWeapon.magnitude);

    }

    public int playerAttacked(int dmg) {
        /*
         * Use this to deal damage to the player
         * Minimum damage taken cannot be <1
         * 
         * dmg: Incoming damage amount
         */

        int netDamage = dmg - defense;
        if (netDamage < 1) netDamage = 1;
        health -= netDamage;
        if (health < 0) health = 0;
        return health;

    }
    
}