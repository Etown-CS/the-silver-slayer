public class Player {

    Menu menuRef;
    Item[] inventory = {null, null, null, null, null, null, null, null, null, null};
    int health, attack, defense, invCap;

    public Player(Menu refToMenu) {
        /* Constructor */

        health = 3;
        attack = 1;
        defense = 0;
        invCap = 5;

        menuRef = refToMenu;

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

        boolean consume = inventory[slot].use();
        if (consume) inventory[slot] = null;
        return true;

    }
    
}