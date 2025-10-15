public class Item {

    ItemType type = ItemType.Unassigned;
    String name;
    String description;
    int magnitude;
    boolean consumable;

    public Item(String itemName, ItemType itemType, String desc, int statValChange, boolean consumedOnUse) {
        /* Constructor */

        if (itemType != ItemType.Health && statValChange < 0) System.out.println("WARN: Item created with negative magnitude.");

        name = itemName;
        type = itemType;
        description = desc;
        magnitude = statValChange;
        consumable = consumedOnUse;

    }
    
}