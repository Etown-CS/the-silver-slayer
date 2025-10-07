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
        /*
         * Execute an attack against the player
         */

        playerRef.playerAttacked(attack);

    }

    public int getAttacked(int dmg) {
        /*
         * Use getAttacked to deal damage to this enemy
         * 
         * dmg: Incoming damage value
         */
        
        int netDamage = dmg - defense;
        if (netDamage < 1) netDamage = 1;
        health -= netDamage;
        if (health < 0) health = 0;
        return health;

    }
    
}