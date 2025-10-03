public class Player {

    Menu menuRef;
    Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
    int health, attack, defense, invCap;

    String location, sublocation;

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

    public boolean useItem(int slot) {
        /*
         * Use a particular item. Checks if the target slot is valid and actually has an item
         * Returns true if the item was successfully used; false otherwise
         * 
         * slot: The inventory slot of the item to be used
         */

        if (slot < 0 || slot > invCap) {

            menuRef.writeText("Invalid slot number!", 0);
            return false;

        } else if (inventory[slot] == null) {

            menuRef.writeText("Slot " + slot + " is empty.", 0);
            return false;

        }

        menuRef.writeText(inventory[slot].useMessage, 0);
        if (inventory[slot].consumable) inventory[slot] = null;
        return true;

    }
    
}