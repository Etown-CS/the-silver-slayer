public class Enemy extends Entity {
    
    public final boolean isBoss;

    public Enemy(String enemyName, int h, int a, int d) {this(enemyName, h, a, d, false);}
    public Enemy(String enemyName, int h, int a, int d, boolean boss) {

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

                spawnMsg = "\n\nOh shoot here come dat boi!";
                break;

            case "The Silver Slayer":

                spawnMsg = "\n\n\"Let us begin.\"";

            default:

                spawnMsg = "\n\n" + name + " has spawned!";

        }

        initStatuses();

    }

    public String enemyAttack(Player target) {
        /*
        * Launch an attack! Enemies with abilities may use them
        * target: Whichever player this attack is intended to affect
        */

        StringBuilder msg = new StringBuilder(64);
        int atkDmg = (statuses.get("weak") > 0) ? (int) (attack * 0.7) : attack;
        if (target.statuses.get("known") > 0) atkDmg *= 1.3;
        msg.append(name + " attacks for " + target.getAttacked(atkDmg) + " damage!");

        if (target.health > 0) {

            switch (name) {

                case "Banshee":

                    if (r.nextInt(100) < 50) {

                        if (r.nextBoolean()) {

                            int s = -1;
                            for (int c = 0; c < r.nextInt(1,5); c++) {

                                switch (r.nextInt(5)) {

                                    case 0:

                                        s = target.addItem(Database.genItem(14)); // Trash
                                        break;

                                    case 1:

                                        s = target.addItem(Database.genItem(15)); // Junk
                                        break;

                                    case 2:

                                        s = target.addItem(Database.genItem(16)); // Rubbish
                                        break;

                                    case 3:

                                        s = target.addItem(Database.genItem(17)); // Expired... Something
                                        break;

                                    case 4:

                                        s = target.addItem(Database.genItem(18)); // Mystery Meat
                                        break;

                                }

                            }

                            if (s >= 0) msg.append("\nBanshee spams you with garbage!\nYour inventory gets heavier.");
                            else msg.append("\nBanshee throws garbage all over you! Gross!");

                        } else {

                            target.statuses.put("dazed", target.statuses.get("dazed") + 3);
                            msg.append("\nBanshee emits an earsplitting shriek!\nYou're dazed!");

                        }

                    }

                    break;

                case "Bot Swarm":

                    changeStats(0, r.nextInt(1, 3), r.nextInt(1, 2));
                    msg.append("\nThe swarm becomes more powerful!");
                    break;

                case "Cleanser":

                    changeStats(0, 100, 0);
                    msg.append("\nCleanser is going to anihilate you!\nCleanser has gained attack!");
                    break;

                case "Cyber Scorpion":

                    if (r.nextBoolean()) {

                        target.statuses.put("poison", target.statuses.get("poison") + 1);
                        msg.append("\nCyber Scorpion poisoned you!");

                    }

                    break;

                case "Faceless":

                    switch (r.nextInt(3)) {

                        case 0:

                            if (r.nextBoolean()) {

                                msg.append("\nFaceless' absent gaze bores into you. You lost 1 attack!");
                                target.changeStats(0, -1, 0);

                            } else {

                                msg.append("\nFaceless' absent gaze bores into you. You lost 1 defense!");
                                target.changeStats(0, 0, -1);

                            }

                            break;

                        case 1:

                            target.statuses.put("dazed", target.statuses.get("dazed") + 5);
                            msg.append("\nThe void is calling...\nYou've been dazed!");
                            break;

                        case 2:

                            msg.append("\nFaceless' dark energy is corrupting the world!");
                            // TODO: Corrupt UI

                    }

                    break;

                case "Figment":

                    switch (r.nextInt(4)) {

                        case 0:

                            msg.append("\nIs it possible for imagination to shimmer?");
                            break;

                        case 1:

                            msg.append("\nIt's not your memory, but it's sickening nonetheless.\nYou've been poisoned!");
                            target.statuses.put("poison", target.statuses.get("poison") + 3);
                            break;

                        case 2:

                            msg.append("\nYou've gome weak at the knees.");
                            target.statuses.put("weak", target.statuses.get("weak") + 1);
                            break;

                        case 3:

                            msg.append("\nA sudden flash of inspiration!");
                            target.statuses.put("blinded", target.statuses.get("blind") + 1);
                            break;

                    }

                    break;

                case "Flashbang":

                    if (r.nextInt(100) < 300) {

                        if (r.nextBoolean()) {

                            target.statuses.put("blinded", target.statuses.get("blind") + 1);
                            msg.append("\nFlashbang blinds you!");

                        } else {

                            menuRef.squishUI();
                            msg.append("\nFlashbang distorts your vision!");

                        }

                    }

                    break;

                case "Memory":

                    if (r.nextBoolean()) {

                        msg.append("\nIt fades as quickly as it came...");
                        menuRef.despawnEnemy();

                    } else {

                        switch (r.nextInt(6)) {

                            case 1:

                                target.statuses.put("dazed", target.statuses.get("dazed") + 3);
                                msg.append("\nThe memory is sudden and bursting with emotion!\nYou've been dazed!");
                                break;

                            case 3:

                                target.statuses.put("poison", target.statuses.get("poison") + 5);
                                msg.append("\nThe memory is soured and toxic.\nYou've been poisoned!");
                                break;
                            
                            case 5:

                                target.statuses.put("fire", target.statuses.get("fire") + 3);
                                msg.append("\nThe memory burns!\nYou're on fire!");
                                break;

                        }

                    }

                    break;

                case "Mimic":

                    // TODO: Mimic ability
                    break;

                case "PISMPE":

                    msg.append("\nYour information is being recorded. Enemies will be stronger for awhile!");
                    target.statuses.put("known", 4); // Intentionally sets this to a flat value
                    break;

                case "RAT":

                    int slot = r.nextInt(target.invCap);
                    Item i = target.inventory[slot];
                    if (i != null) {
                        
                        switch (i.type) {

                            case Health:

                                msg.append("\nRAT ate your " + target.removeItem(slot) + '!');
                                changeStats(i.magnitude, 0, 0);
                                break;

                            default:

                                msg.append("\nRAT found nothing of interest.");

                        }

                    } else msg.append("\nRAT found nothing of interest.");
                    break;

                case "Scavenger":

                    if (flee(50)) {

                        msg.append("\nScavenger wastes no time.\nScavenger has fled!");
                        menuRef.despawnEnemy();

                    }

                    break;

                case "Scrambler":

                    if (r.nextBoolean()) {

                        msg.append("\nScrambler is mutating!");

                        int tmp;
                        switch (r.nextInt(3)) {

                            case 0:

                                tmp = health;
                                health = attack;
                                attack = tmp;
                            
                            case 1:

                                tmp = health;
                                health = defense;
                                defense = tmp;

                            case 2:

                                tmp = attack;
                                attack = defense;
                                defense = tmp;

                        }

                        changeStats(0, 0, 0);

                    } else {

                        // TODO: Scramble the player, preferably temporarily

                    }

                    break;

                case "Worm":

                    if (r.nextBoolean()) {

                        changeStats(healthDefault, attackDefault, defenseDefault);
                        msg.append("\nThe virus is replicating!");

                    }

                    break;

            }

        }

        return msg.toString();

    }
    
}