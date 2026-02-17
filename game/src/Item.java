import java.security.SecureRandom;

public class Item {

    private static SecureRandom r = new SecureRandom();
    public static boolean silverSpoon = false, paperHat = false, goggles = false, woodenClub = false, bitingRing = false, silverSword = false;

    public ItemType type;
    public String name, description;
    public int magnitude;
    public boolean consumable;

    public Item(String itemName, ItemType itemType, String desc, int mag, boolean consumedOnUse) {

        name = itemName;
        type = itemType;
        description = desc;
        magnitude = mag;
        consumable = consumedOnUse;

    }

    public static String getItem(Player playerRef) {
        /*
        * Get an item when searching, if there's one to be found
        * Items marked with a * are unique
        */

        // Silver Spoon *
        if (!silverSpoon && Player.location == 2 && Player.sublocation == 1) {

            int invSlot = playerRef.addItem(new Item("Silver Spoon", ItemType.Weapon, "A shiny, silver spoon.", 1, false));
            if (invSlot == -1) return "\nInventory Full";
            else {
                
                silverSpoon = true;
                return "\nAdded Silver Spoon to slot " + invSlot + ".";

            }

        }

        // Paper Hat *
        if (!paperHat && Player.location == 2 && Player.sublocation == 2) {

            int invSlot = playerRef.addItem(new Item("Paper Hat", ItemType.Armor, "An origami paper hat. Adds a point to ~style~.", 1, false));
            if (invSlot < 0) return "\nInventory full.";
            else {
                
                paperHat = true;
                return "\nAdded Paper Hat to slot " + invSlot + '!';

            }

        }

        // Rock
        if (Player.location == 3 && Player.sublocation == 0) {

            int invSlot = playerRef.addItem(new Item("Rock", ItemType.Junk, "A cool rock. Does nothing.", 0, false));
            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded Rock to slot " + invSlot + '!';

        }

        // Goggles *
        if (!goggles && Player.location == 3 && Player.sublocation == 1) {

            int invSlot = playerRef.addItem(new Item("Goggles", ItemType.Key, "A pair of purple, plastic swimming goggles. Thankfully, they don't leak.", 0, false));
            if (invSlot < 0) return "\nInventory full.";
            else {

                goggles = true;
                Story.updateEvent(321, "Luckily you're wearing goggles, so you begin to look around. At the bottom of the lake, you see the enterance to a cave, but you are too nervous to look any further.");
                return "\nAdded Goggles to slot " + invSlot + '!';

            }

        }

        // Wooden Club *
        if (!woodenClub && Player.location == 4 && Player.sublocation == 2) {

            int invSlot = playerRef.addItem(new Item("Wooden Club", ItemType.Weapon, "A hefty branch found on the slopes of the Mountain. Bonk!", 4, false));
            if (invSlot < 0) return "\nInventory full.";
            else {

                woodenClub = true;
                Story.updateEvent(422, "The few living plants give the Oracle a wide berth. Scattered branches litter the area.");
                return "\nAdded Wooden Club to slot " + invSlot + '!';

            }

        }

        // Non-Biting Ring *
        if (!bitingRing && Player.location == 0 && Player.sublocation == 0) { // TODO: Put this somewhere

            int invSlot = playerRef.addItem(new Item("Non-Biting Ring", ItemType.Wearable, "A golden ring inset with a blood-red gemstone. Does not bite.", 1, false));
            if (invSlot < 0) return "\nInventory full.";
            else {

                bitingRing = true;
                return "\nAdded Non-Biting Ring to slot " + invSlot + '!';

            }

        }

        // Cactus Fruit
        if (Player.location == 5 && (Player.sublocation == 0 || Player.sublocation == 1) && r.nextBoolean()) {

            int invSlot = playerRef.addItem(new Item("Cactus Fruit", ItemType.Health, "A colorful cactus fruit. Pull out the spines first!", 3, true));
            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded a fruit to slot " + invSlot + '!';

        }

        // Swamp Fruits
        if (Player.location == 6 && Player.sublocation == 2) {

            int invSlot = 0;

            switch (r.nextInt(9)) {

                case 0:
                case 1:

                    invSlot = playerRef.addItem(new Item("Mottled Swamp Fruit", ItemType.Health, "A mottled fruit from the swamp's woodland.", 3, true));
                    break;

                case 2:
                case 3:

                    invSlot = playerRef.addItem(new Item("Oblong Swamp Fruit", ItemType.Health, "An oblong fruit from the swamp's woodland.", -2, true));
                    break;

                case 4:
                case 5:

                    invSlot = playerRef.addItem(new Item("Bulbous Swamp Fruit", ItemType.Health, "A bulbous fruit from the swamp's woodland.", 4, true));
                    break;

                case 6:
                case 7:

                    invSlot = playerRef.addItem(new Item("Speckled Swamp Fruit", ItemType.Health, "A speckled fruit from the swamp's woodland.", -3, true));
                    break;

                default:

                    invSlot = playerRef.addItem(new Item("Star Swamp Fruit", ItemType.Health, "A star-shaped fruit from the swamp's woodland.", 7, true));
                    break;

            }

            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded a fruit to slot " + invSlot + '!';

        }

        // Silver Sword *
        if (!silverSword && Player.location == 8 && Player.sublocation == 3 && Story.wasEventSeen(833)) {

            int invSlot = playerRef.addItem(new Item("Silver Sword", ItemType.Weapon, "A sharp, silver sword taken from a mighty foe. The blade is strangely notched, and the pattern appears to be intentionally engraved.", 99, false));
            if (invSlot == -1) return "\nInventory Full";
            else {
                
                silverSword = true;
                return "\nAdded the Silver Sword to slot " + invSlot + ".";

            }

        }
        
        return null;

    }
    
}