public class Enemy extends Entity {

    public boolean isBoss;

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

            default:

                spawnMsg = "\n\n" + name + " has spawned!";

        }

    }

    public String enemyAttack(Player target) {
        /*
        * Launch an attack! Enemies with abilities may use them
        * target: Whichever player this attack is intended to affect
        */

        StringBuilder msg = new StringBuilder(64);
        msg.append(name + " attacks for " + target.getAttacked(attack) + " damage!");

        if (target.health > 0) {

            switch (name) {

                case "Banshee":

                    if (r.nextInt(100) < 50) {

                        if (r.nextBoolean()) {

                            for (int c = 0; c < r.nextInt(1,5); c++) {

                                switch (r.nextInt(5)) {

                                    case 0:

                                        target.addItem(new Item("Trash", ItemType.Junk, "What even is this?", 0, false));
                                        break;

                                    case 1:

                                        target.addItem(new Item("Waste", ItemType.Junk, "This is just junk.", 0, false));
                                        break;

                                    case 2:

                                        target.addItem(new Item("Rubbish", ItemType.Junk, "A collection of trash.", 0, false));
                                        break;

                                    case 3:

                                        target.addItem(new Item("Expired Something", ItemType.Health, "An expired piece of some unknown food.", -1, true));
                                        break;

                                    case 4:

                                        target.addItem(new Item("Mystery Meat", ItemType.Health, "This could be anything.", c, true));
                                        break;

                                }

                            }

                            msg.append("\nBanshee spams you with garbage!\nYour inventory gets heavier.");

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
                    msg.append("\nCleanser prepares to anihilate you!");
                    break;

                case "Cyber Scorpion":

                    if (r.nextBoolean()) {

                        target.statuses.put("poison", target.statuses.get("poison") + 1);
                        msg.append("\nCyber Scorpion poisoned you!");

                    }

                    break;

                case "Faceless":

                    // TODO: Faceless ability
                    break;

                case "Figment":

                    switch (r.nextInt(4)) {

                        case 0:

                            msg.append("Is it possible for imagination to shimmer? Something is happening.");
                            break;

                        case 1:

                            msg.append("\nIt's not your memory, but it's sickening nonetheless.\nYou've been poisoned!");
                            target.statuses.put("poison", target.statuses.get("poison") + 3);
                            break;

                        case 2:

                            msg.append("You've gome weak at the knees.");
                            target.statuses.put("weak", target.statuses.get("weak") + 1);
                            break;

                        case 3:

                            msg.append("A sudden flash of inspiration!");
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

                            //TODO: 4:3 resolution
                            msg.append("\nFlashbang distorts your vision!");

                        }

                    }

                    break;

                case "Memory":

                    if (r.nextBoolean()) {

                        msg.append("\nIt fades as quickly as it came...");
                        // TODO: Make it despawn

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

                    // TODO: PISMPE ability
                    break;

                case "RAT":

                    // TODO: RAT ability
                    break;

                case "Scavenger":

                    if (r.nextBoolean() && flee(50)) {

                        msg.append("\nScavenger wastes no time.\nScavenger has fled!");
                        // TODO: Make it flee

                    }

                    break;

                case "Scrambler":

                    // TODO: Scrambler ability
                    break;

                case "Worm":

                    if (r.nextBoolean()) {

                        changeStats(healthDefault, attackDefault, 0);
                        msg.append("\nThe virus is replicating!");

                    }

                    break;

            }

        }

        return msg.toString();

    }
    
}