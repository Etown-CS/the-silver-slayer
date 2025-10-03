public class Item {

    ItemType type = ItemType.Heal;
    String name;
    String useMessage;
    int magnitude;
    boolean consumable;

    public Item(String itemName, String usageText, int statValChange, boolean consumedOnUse) {
        /* Constructor */

        name = itemName;
        useMessage = usageText;
        magnitude = statValChange;
        consumable = consumedOnUse;

    }
    
}