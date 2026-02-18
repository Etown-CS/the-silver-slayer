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

            int invSlot = playerRef.addItem(Database.genItem(1));
            if (invSlot == -1) return "\nInventory Full";
            else {
                
                silverSpoon = true;
                return "\nAdded Silver Spoon to slot " + invSlot + ".";

            }

        }

        // Paper Hat *
        if (!paperHat && Player.location == 2 && Player.sublocation == 2) {

            int invSlot = playerRef.addItem(Database.genItem(2));
            if (invSlot < 0) return "\nInventory full.";
            else {
                
                paperHat = true;
                return "\nAdded Paper Hat to slot " + invSlot + '!';

            }

        }

        // Rock
        if (Player.location == 3 && Player.sublocation == 0) {

            int invSlot = playerRef.addItem(Database.genItem(3));
            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded Rock to slot " + invSlot + '!';

        }

        // Goggles *
        if (!goggles && Player.location == 3 && Player.sublocation == 1) {

            int invSlot = playerRef.addItem(Database.genItem(4));
            if (invSlot < 0) return "\nInventory full.";
            else {

                goggles = true;
                Story.updateEvent(321, "Luckily you're wearing goggles, so you begin to look around. At the bottom of the lake, you see the enterance to a cave, but you are too nervous to look any further.");
                return "\nAdded Goggles to slot " + invSlot + '!';

            }

        }

        // Wooden Club *
        if (!woodenClub && Player.location == 4 && Player.sublocation == 2) {

            int invSlot = playerRef.addItem(Database.genItem(5));
            if (invSlot < 0) return "\nInventory full.";
            else {

                woodenClub = true;
                Story.updateEvent(422, "The few living plants give the Oracle a wide berth. Scattered branches litter the area.");
                return "\nAdded Wooden Club to slot " + invSlot + '!';

            }

        }

        // Non-Biting Ring *
        if (!bitingRing && Player.location == 4 && Player.sublocation == 3) {

            int invSlot = playerRef.addItem(Database.genItem(6));
            if (invSlot < 0) return "\nInventory full.";
            else {

                bitingRing = true;
                Story.updateEvent(432, "There are just drifts of snow.");
                return "\nAdded Non-Biting Ring to slot " + invSlot + '!';

            }

        }

        // Cactus Fruit
        if (Player.location == 5 && (Player.sublocation == 0 || Player.sublocation == 1) && r.nextInt(100) < 40) {

            int invSlot = playerRef.addItem(Database.genItem(7));
            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded a fruit to slot " + invSlot + '!';

        }

        // Swamp Fruits
        if (Player.location == 6 && Player.sublocation == 2) {

            int invSlot = 0;

            switch (r.nextInt(9)) {

                case 0:
                case 1:

                    invSlot = playerRef.addItem(Database.genItem(8));
                    break;

                case 2:
                case 3:

                    invSlot = playerRef.addItem(Database.genItem(9));
                    break;

                case 4:
                case 5:

                    invSlot = playerRef.addItem(Database.genItem(10));
                    break;

                case 6:
                case 7:

                    invSlot = playerRef.addItem(Database.genItem(11));
                    break;

                default:

                    invSlot = playerRef.addItem(Database.genItem(12));
                    break;

            }

            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded a fruit to slot " + invSlot + '!';

        }

        // Silver Sword *
        if (!silverSword && Player.location == 8 && Player.sublocation == 3 && Story.wasEventSeen(833)) {

            int invSlot = playerRef.addItem(Database.genItem(13));
            if (invSlot == -1) return "\nInventory Full";
            else {
                
                silverSword = true;
                return "\nAdded the Silver Sword to slot " + invSlot + ".";

            }

        }
        
        return null;

    }
    
}