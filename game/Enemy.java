public class Enemy extends Entity {
    
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
    
}