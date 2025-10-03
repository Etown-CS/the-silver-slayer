public class Item {

    ItemType type;
    String name;

    public Item(String itemName) {
        /* Constructor */

        name = itemName;

    }

    public boolean use() {
        /*
         * Use this item
         * Returns true if the item should be consumed; false otherwise
         */

        switch (type) {

            case Heal:

                System.out.println("<3");
                return true;

        }

        return false;

    }
    
}