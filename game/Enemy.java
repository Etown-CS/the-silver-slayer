public class Enemy {

    public String name;
    public int health, attack, defense;
    private int healthDefault, attackDefault, defenseDefault;
    
    public Enemy(String enemyName, int h, int a, int d) {
        /* Constructor */

        name = enemyName;
        health = h;
        healthDefault = h;
        attack = a;
        attackDefault = a;
        defense = d;
        defenseDefault = d;

    }

    public void changeStats(int H, int A, int D) {
        /*
         * Change enemy stats
         * 
         * H: Mod health
         * A: Mod attack
         * D: Mod defense
         */

        health += H;
        attack += A;
        defense += D;

        if (health < 0) health = 0;
        if (attack < 0) attack = 0;
        if (defense < 0) defense = 0;

    }

    public int getAttacked(int dmg) {
        /*
         * Use getAttacked to deal damage to this enemy
         * 
         * dmg: Incoming damage value
         */
        
        dmg = dmg - defense;
        if (dmg < 1) dmg = 1;
        changeStats(-dmg, 0, 0);
        return dmg;

    }

    public void reset() {

        health = healthDefault;
        attack = attackDefault;
        defense = defenseDefault;

    }
    
}