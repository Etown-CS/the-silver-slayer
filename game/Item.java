public class Item {

    ItemType type = ItemType.Unassigned;
    String name;
    String useMessage;
    int magnitude;
    boolean consumable;

    public Item(String itemName, ItemType itemType, String usageText, int statValChange, boolean consumedOnUse) {
        /* Constructor */

        name = itemName;
        type = itemType;
        useMessage = usageText;
        magnitude = statValChange;
        consumable = consumedOnUse;

    }
    
}