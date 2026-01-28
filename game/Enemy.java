public class Enemy extends Entity {

    public boolean isBoss;

    public Enemy(String enemyName, int h, int a, int d) {this(enemyName, h, a, d, false);}
    public Enemy(String enemyName, int h, int a, int d, boolean boss) {
        /* Constructor */

        name = enemyName;
        health = h;
        healthDefault = h;
        attack = a;
        attackDefault = a;
        defense = d;
        defenseDefault = d;
        isBoss = boss;

        switch (name) {

            case "Dat Boi":

                spawnMsg = "\n\nOh shoot here come dat boi";
                break;

            default:

                spawnMsg = "\n\n" + name + " has spawned!";

        }

    }
    
}