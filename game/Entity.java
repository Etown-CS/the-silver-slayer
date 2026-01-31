import java.util.HashMap;
import java.security.SecureRandom;

public class Entity {

    protected SecureRandom r = new SecureRandom();
    public HashMap<String, Integer> statuses = new HashMap<String, Integer>();
    public String name, spawnMsg;
    public int health, attack, defense, healthDefault, attackDefault, defenseDefault;

    protected void initStatuses() {
        /*
        * Populates statuses map
        */

        statuses.put("poison", 0);      // Damage over time
        statuses.put("fire", 0);        // High damage over time
        statuses.put("strength", 0);    // Increases attack
        statuses.put("weak", 0);        // Reduces attack
        statuses.put("dazed", 0);       // Garbles text
        statuses.put("blind", 0);       // Activates light mode
        statuses.put("known", 0);       // Enemies are stronger
        statuses.put("doom", 0);        // Unstoppable stat drain

    }

    public void changeStats(int H, int A, int D) {
        /*
         * Change entity's stats
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
         * Use getAttacked to deal damage to this entity
         * 
         * dmg: Incoming damage value
         */
        
        dmg -= defense;
        if (dmg < 1) dmg = 1;
        changeStats(-dmg, 0, 0);
        return dmg;

    }

    public boolean flee(int chance) {
        /*
        * Attempt to flee from battle
        * //TODO: Make this more substantial
        */

        return r.nextInt(100) < chance;

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