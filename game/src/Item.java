public class Item {

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
    
}