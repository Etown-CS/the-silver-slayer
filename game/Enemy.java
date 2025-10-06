public class Enemy {

    Player playerRef;
    String name;
    int health, attack, defense;

    public Enemy(int h, int a, int d) {
        /* Constructor */

        health = h;
        attack = a;
        defense = d;

    }

    public void doAttack() {

        playerRef.playerAttacked(attack);

    }

    public int getAttacked(int dmg) {
        
        int netDamage = dmg - defense;
        if (netDamage < 1) netDamage = 1;
        health -= netDamage;
        if (health < 0) health = 0;
        return health;

    }
    
}