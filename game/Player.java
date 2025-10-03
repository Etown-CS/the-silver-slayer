public class Player {

    Item[] inventory;
    int health, attack, defense, invCap;

    public Player() {

        health = 3;
        attack = 1;
        defense = 0;

        inventory = new Item[10];
        invCap = 5;

    }
    
}