public class Item {

    ItemType type = ItemType.Unassigned;
    String name;
    String description;
    int magnitude;
    boolean consumable;

    public Item(String itemName, ItemType itemType, String desc, int statValChange, boolean consumedOnUse) {
        /* Constructor */

        name = itemName;
        type = itemType;
        description = desc;
        magnitude = statValChange;
        consumable = consumedOnUse;

    }
    
}