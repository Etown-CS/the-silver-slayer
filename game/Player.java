public class Player {

    private Menu menuRef;
    public Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
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
        menuRef.updatePlayerBar(health, attack, defense);

        if (health <= 0) {

            menuRef.gameOver = true;

        }

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

    public String listItems() {

        String inv = "";
        for (int c = 0; c < invCap; c++) if (inventory[c] != null) inv += "|" + inventory[c].name + "|";
        else inv += "|_____|";
        return inv;

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
        if (inventory[slot].consumable) inventory[slot] = null;
        return msg;

    }
    
}