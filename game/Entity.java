public class Entity {

    public String name;
    public int health, attack, defense;
    public int healthDefault, attackDefault, defenseDefault;

    public void changeStats(int H, int A, int D) {
        /*
         * Change player stats
         * 
         * H: Mod health
         * A: Mod attack
         * D: Mod defense
         */

        health += H;
        attack += A;
        defense += D;

        if (health > healthDefault) health = healthDefault;
        else if (health < 0) health = 0;
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
        /*
        * Return stats to their initial values
        */

        health = healthDefault;
        attack = attackDefault;
        defense = defenseDefault;

    }
    
}