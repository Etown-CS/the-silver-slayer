public class Enemy {

    private Menu menuRef;
    private Player playerRef;
    public String name;
    public int health, attack, defense;
    public boolean defeated;

    public Enemy(Menu refToMenu, Player refToPlayer, String enemyName) {
        /* Constructor */

        menuRef = refToMenu;
        playerRef = refToPlayer;
        name = enemyName;
        defeated = false;

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

        if (health <= 0) {

            health = 0;
            defeated = true;

        }

        if (attack < 0) attack = 0;
        if (defense < 0) defense = 0;

        menuRef.updateEnemyBar(name, health, attack, defense);

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
        
        dmg = dmg - defense;
        if (dmg < 1) dmg = 1;
        changeStats(-dmg, 0, 0);
        return dmg;

    }
    
}