public class Item {

    ItemType type;
    String name;
    String useMessage;
    boolean consumable;

    public Item(String itemName, String usageText, boolean consumedOnUse) {
        /* Constructor */

        name = itemName;
        useMessage = usageText;
        consumable = consumedOnUse;

    }
    
}