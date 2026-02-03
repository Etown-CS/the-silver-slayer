import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.SecureRandom;

public class Menu {

    // Display
    private JFrame mainframe = new JFrame();
    private CardLayout cards = new CardLayout();
    private JPanel basePanel = new JPanel();
    private JPanel terminalPanel = new JPanel();
    private JTextArea terminalScreen = new JTextArea("", 1, 30);;
    private JTextArea playerBar = new JTextArea("", 1, 10);;
    private JTextArea enemyBar = new JTextArea("ENEMY", 1, 10);;
    private JScrollPane scrollPane;
    private JTextField inputField = new JTextField();

    // Character selection screen
    private JPanel charsPanel = new JPanel();
    private JButton[] characterButtons = new JButton[Player.names.length];

    // Text
    private Timer timer = new Timer(0, null);
    private int characterIndex;

    // Elements
    private SecureRandom r = new SecureRandom();
    private Save save;
    private Story theStory = new Story(); // Making it create a "new story" has so much aura
    private Player[] players = new Player[Player.names.length];
    private Player playerRef;
    private Enemy enemyRef;
    private boolean enemyTurn = false, gameOver = false;

    // Sounds
    private Audio[] voices = {new Audio("voice_blip")};
    private Audio[] bossTracks = {new Audio("boss_village"), new Audio("boss_lake"), new Audio("boss_mountain"), new Audio("boss_desert"), new Audio("boss_swamp"), new Audio("boss_fracture"), new Audio("boss_lair")};
    private Audio damageSFX = new Audio("sfx_attack");

    private byte counter; // This is here so it can be used in the ActionListerner creations below

    // Items player can get
    private boolean silverSpoon = false, paperHat = false, goggles = false, bitingRing = false, magicKey = false, silverSword = false;

    public Menu() {
        /* Constructor */

        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (counter = 0; counter < Player.names.length; counter++) {

            characterButtons[counter] = new JButton(Player.names[counter]);
            characterButtons[counter].addActionListener(new ActionListener() {

                byte pNum = counter;

                @Override
                public void actionPerformed(ActionEvent e) {

                    playerRef = players[pNum];
                    cards.next(basePanel);
                    charsPanel.removeAll();
                    Log.logData("Player selects character: " + playerRef.name);
                    writeText("Selected " + playerRef.name, 0);
                    inputField.requestFocusInWindow();

                }
                
            });

        }

        try {

            save = new Save();

        } catch (IOException ex) {

            System.out.println("FATAL: Failed to access save file!");
            JOptionPane.showMessageDialog(null, ex, "MISSING SAVE", JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }

        if (!save.loaded) {

            JOptionPane.showMessageDialog(null, "Failed to load save file!", "BAD SAVE", JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }
        
        for (int c = 0; c < Player.names.length; c++) players[c] = new Player(Player.names[c], theStory);
        setupUI();

    }

    private String getItems() {
        /*
        * Get an item when searching if there's one to be found
        * Items marked with a * are unique
        */

        if (playerRef == null) return "";
        
        // Silver Spoon *
        if (!silverSpoon && Player.location == 2 && Player.sublocation == 1) {
            int invSlot = playerRef.addItem(new Item("Silver Spoon", ItemType.Weapon, "A shiny, silver spoon.", 1, false));
            silverSpoon = true;
            // If inventory is full
            if (invSlot == -1) return "\nInventory Full";
            else return "\nAdded the Silver Spoon to slot " + invSlot + ".";
        }

        // Paper Hat *
        if (!paperHat && Player.location == 2 && Player.sublocation == 2) {

            int invSlot = playerRef.addItem(new Item("Paper Hat", ItemType.Armor, "An origami paper hat. Adds a point to ~style~.", 1, false));
            paperHat = true;
            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded Paper Hat to slot " + invSlot + '!';

        }

        // Rock
        if (Player.location == 3 && Player.sublocation == 0) {

            int invSlot = playerRef.addItem(new Item("Rock", ItemType.Junk, "A cool rock. Does nothing.", 0, false));
            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded Rock to slot " + invSlot + '!';

        }

        // Goggles *
        if (!goggles && Player.location == 3 && Player.sublocation == 1) {

            int invSlot = playerRef.addItem(new Item("Goggles", ItemType.Quest, "A pair of purple, plastic swimming goggles. Luckily these don't leak.", 0, false));
            goggles = true;
            if (invSlot < 0) return "\nInventory full.";
            else {

                goggles = true;
                theStory.updateEvent(3, 321, "Luckily you're wearing goggles, so you begin to look around. At the bottom of the lake, you see the enterance to a cave, but you are too nervous to look any further.");
                return "\nAdded Goggles to slot " + invSlot + '!';

            }

        }

        // Mountain path searches
        if (Player.location == 4 && Player.sublocation == 1 && Player.mountainPathSearches < 6) {

            if (Player.mountainPathSearches == 0) {

                theStory.updateEvent(4, 412, "Attempting to follow the lost path's trail is difficult. You somehow keep ending up back at the signpost, over and over again.");
                Player.mountainPathSearches++;
                System.out.println(Player.mountainPathSearches);

            } else if (Player.mountainPathSearches == 1) {

                theStory.updateEvent(4, 412, "It almost feels like you're being pushed away from your destination. The plants and landmarks never seem to repeat, yet you always return to the signpost.");
                Player.mountainPathSearches++;
                System.out.println(Player.mountainPathSearches);

            } else if (Player.mountainPathSearches == 2) {

                theStory.updateEvent(4, 412, "The signpost's missing text has reappeared. It reads: \"enough\"");
                Player.mountainPathSearches++;
                System.out.println(Player.mountainPathSearches);

            } else if (Player.mountainPathSearches == 3) {

                theStory.updateEvent(4, 412, "Why do you feel so... ill? Give up the search. Give it up. There's nothing to be found.");
                Player.mountainPathSearches++;
                System.out.println(Player.mountainPathSearches);

            } else if (Player.mountainPathSearches == 4) {

                theStory.updateEvent(4, 412, "You're not wanted here. This place isn't for you. Let it rest. You don't want their attention.");
                Player.mountainPathSearches++;
                System.out.println(Player.mountainPathSearches);

            } else if (Player.mountainPathSearches == 5) {

                theStory.updateEvent(4, 412, "The signpost's missing text has reappeared. It reads: \"Fracture\"");
                Player.mountainPathSearches++;
                System.out.println(Player.mountainPathSearches);

            }

        }

        // Non-Biting Ring *
        if (!bitingRing && Player.location == 0 && Player.sublocation == 0) { // TODO: Put this somewhere

            int invSlot = playerRef.addItem(new Item("Non-Biting Ring", ItemType.Armor, "An iron ring with a bloodred gemstone that definitely does not bite.", 1, false));
            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded Non-Biting Ring to slot " + invSlot + '!';

        }

        // Swamp Fruits
        if (Player.location == 6 && Player.sublocation == 2) {

            int invSlot = 0;

            switch (r.nextInt(4)) {

                case 0:

                    invSlot = playerRef.addItem(new Item("Mottled Swamp Fruit", ItemType.Health, "A mottled fruit from the swamp's woodland.", 3, true));
                    break;

                case 1:

                    invSlot = playerRef.addItem(new Item("Oblong Swamp Fruit", ItemType.Health, "An oblong fruit from the swamp's woodland.", 2, true));
                    break;

                case 2:

                    invSlot = playerRef.addItem(new Item("Bulbous Swamp Fruit", ItemType.Health, "A bulbous fruit from the swamp's woodland.", 4, true));
                    break;

                case 3:

                    invSlot = playerRef.addItem(new Item("Speckled Swamp Fruit", ItemType.Health, "A speckled fruit from the swamp's woodland.", -3, true));
                    break;


            }

            if (invSlot < 0) return "\nInventory full.";
            else return "\nAdded a fruit to slot " + invSlot + '!';

        }

        // Magic Key *
        if (!magicKey && Player.location == 8 && Player.sublocation == 3 && theStory.wasEventSeen(Player.location, 833)) {
            int invSlot = playerRef.addItem(new Item("Magic Key", ItemType.Key, "A key that opens a mysterious chest", 0, false));
            magicKey = true;
            // If inventory is full
            if (invSlot == -1) return "\nInventory Full";
            else return "\nAdded the Magic Key to slot " + invSlot + ".";
        }

        // Silver Sword *
        if (!silverSword && Player.location == 8 && Player.sublocation == 3 && theStory.wasEventSeen(Player.location, 833)) {
            int invSlot = playerRef.addItem(new Item("Silver Sword", ItemType.Weapon, "A sharp silver sword", 0, false));
            silverSword = true;
            // If inventory is full
            if (invSlot == -1) return "\nInventory Full";
            else return "\nAdded the Silver Sword to slot " + invSlot + ".";
        }
        
        return "";
    }

    private void readInput(String text) {
        /*
         * Called whenever the player enters text
         * 
         * text: The String that was submitted
         */

        if (gameOver) return;
        String[] bits = text.toLowerCase().split(" ");

        switch (bits[0]) {

            // STORY COMMANDS

            case "ls":
            case "look":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else writeText(theStory.getLookEvent(Player.location, Player.sublocation), 0);
                break;

            case "search":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else writeText(theStory.getSearchEvent(Player.location, Player.sublocation) + getItems(), 0);
                break;

            case "goto":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else if (bits.length < 2) writeText("Go where?", 0);
                else if (playerRef.travel(bits[1].toLowerCase())) {

                    switch (Player.location) {

                        case 8:

                            if (Player.sublocation == 3 && Locations.Lair[Locations.Lair.length - 1].health > 0) {

                                enemyRef = Locations.spawnEnemy(8, true);
                                bossTracks[6].command(1);

                            }

                            // no break here

                        default:

                            if (enemyRef == null && Player.location >= 2) enemyRef = Locations.spawnEnemy(Player.location, false);
                            if (enemyRef != null) writeText(theStory.getBaseEvent(Player.location, Player.sublocation) + enemyRef.spawnMsg, 0);
                            else writeText(theStory.getBaseEvent(Player.location, Player.sublocation), 0);

                    }

                } else writeText("You can't get there from here, if there even exists.", 0);
                break;

            case "unlock":

                /*
                *
                * To anyone reading the source code: You better not be cheating >:(
                *
                */

                if (enemyRef != null) writeText("You're in combat!", 0);
                else if (bits.length < 2) writeText("Specify the code or item after 'unlock'!", 0);
                else {

                    if (Player.location == 1 && Player.sublocation == 0) {

                        if (bits[1].equals("a") && playerRef.hasItem("a")) {

                            writeText(theStory.getUnlockEvent(1, 0, true), 0);
                            
                        } else writeText(theStory.getUnlockEvent(1, 0, false), 0);

                    } else if (Player.location == 1 && Player.sublocation == 1) {

                        if (bits[1].equals("83927354") || theStory.wasEventSeen(Player.location, 113)) {

                            writeText(theStory.getUnlockEvent(1, 1, true), 0);

                            theStory.updateEvent(1, 110, "You step up to the gate. The iron doors stand open, and an old lock rests on the ground nearby.");
                            theStory.updateEvent(1, 111, "The gate is old and weathered.");
                            theStory.updateEvent(1, 113, "This lock is open already.");

                        } else writeText(theStory.getUnlockEvent(1, 1, false), 0);

                    } else writeText("There's nothing to unlock here.", 0);

                }

                break;
 
            // GAMEPLAY COMMANDS

            case "whoami":
            case "characters":
            case "character":
            case "chars":
            case "char":

                enemyTurn = true;
                playerSelect();
                break;

            case "desc":
            case "describe":

                if (bits.length < 2) writeText("Specify an inventory slot.", 0);
                else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot >= playerRef.invCap) writeText(slot + " is not a valid inventory slot.", 0);
                        else if (playerRef.inventory[slot] == null) writeText("Slot " + slot + " is empty.", 0);
                        else writeText(playerRef.inventory[slot].description, 0);

                    } catch (NumberFormatException ex) {

                        writeText('"' + bits[1] + "\" is not a valid inventory slot.", 0);

                    }

                }

                break;

            case "use":

                if (bits.length < 2) writeText("Specify an inventory slot.", 0);
                else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot >= playerRef.invCap) writeText(slot + " is not a valid inventory slot.", 0);
                        else if (playerRef.inventory[slot] == null) writeText("Slot " + slot + " is empty.", 0);
                        else writeText(playerRef.useItem(slot), 0);

                    } catch (NumberFormatException ex) {

                        writeText('"' + bits[1] + "\" is not a valid inventory slot.", 0);

                    }

                }

                break;

            case "drop":

                if (bits.length < 2) writeText("Specify an inventory slot.", 0);
                else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot >= playerRef.invCap) writeText(slot + " is not a valid inventory slot.", 0);
                        else if (playerRef.inventory[slot] == null) writeText("Slot " + slot + " is already empty.", 0);
                        else writeText("Dropped '" + playerRef.removeItem(slot) + "' from inventory.", 0);

                    } catch (NumberFormatException ex) {

                        writeText('"' + bits[1] + "\" is not a valid inventory slot.", 0);

                    }

                }

                break;

            case "atk":
            case "attack":

                if (enemyRef == null) writeText("There's nothing here...", 0);
                else {
                    
                    int atkdmg = enemyRef.getAttacked(playerRef.attack);
                    damageSFX.command(2);

                    if (enemyRef.health == 0) {

                        if (enemyRef.isBoss) writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!\n" + Story.BOSS_DEFEATED[Player.location], 0);
                        else {

                            writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!\n" + enemyRef.name + " has been defeated.", 0);
                            enemyRef.reset();

                        }

                        enemyRef = null;

                    } else {

                        writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!", 0);
                        enemyTurn = true;

                    }
                    
                }

                break;

            case "flee":

                if (enemyRef == null) writeText("There's nothing to run from (yet).", 0);
                else if (enemyRef == Locations.enemyIndex[8][Locations.enemyIndex[8].length - 1]) writeText("You either win, or you don't ever leave.", 0);
                else {

                    if (playerRef.flee(65)) {

                        if (playerRef.flee(80)) writeText("You fled from " + enemyRef.name + '\n' + Story.FLEE_STRINGS[r.nextInt(Story.FLEE_STRINGS.length)], 0);
                        else writeText("You fled from " + enemyRef.name + ", but not unscathed.\nReceived " + playerRef.getAttacked(enemyRef.attack) + " damage!", 0);

                        enemyRef.reset();
                        enemyRef = null;

                    } else {

                        writeText("You attempted to flee, but failed!", 0);
                        enemyTurn = true;

                    }

                }

                break;

            case "pass":

                writeText("You bide your time...", 0);
                enemyTurn = true;
                break;

            case "spawn":

                if (bits.length < 2) writeText("spawn what", -1);
                else if (enemyRef == null) {

                    for (Enemy e : Locations.enemyIndex[Player.location]) if (e.name.toLowerCase().equals(bits[1])) enemyRef = e;
                    if (enemyRef == null) writeText("Failed to spawn that.", -1);
                    else writeText("Spawned that.", -1);

                } else writeText("There's already an enemy", -1);

                break;

            // GENERAL COMMANDS

            case "help":

                if (enemyRef != null && enemyRef.name.equals("The Silver Slayer")) writeText("There's no help for you now.", 0);
                else writeText(Story.HELP_TEXT, 0);
                break;

            case "save":

                if (enemyRef == null) {

                    try {

                        save.saveGame(players, playerRef);

                    } catch (IOException ex) {

                        System.out.println("FATAL: Failed to access save file!");
                        JOptionPane.showMessageDialog(null, ex, "BROKEN SAVE", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);

                    }

                    writeText("Game saved.", 0);

                } else writeText("Cannot save during combat!", 0);

                break;

            case "quit":
            case "exit":

                Log.logData("Shutting down. Goodbye!");
                Log.closeLog();
                mainframe.dispatchEvent(new WindowEvent(mainframe, WindowEvent.WINDOW_CLOSING));
                break;

            case "clear":

                terminalScreen.setText(null);
                writeText("The Silver Slayer [Beta v1.0]", -1);
                break;

            case "title":

                if (bits.length < 2) {

                    mainframe.setTitle(Story.TITLE_STRINGS[r.nextInt(Story.TITLE_STRINGS.length)]);
                    writeText("Rerolled title!", 0);

                } else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot > Story.TITLE_STRINGS.length - 1) writeText(slot + " is out of range.", 0);
                        else {

                            mainframe.setTitle(Story.TITLE_STRINGS[slot]);
                            writeText("Updated title!", 0);

                        }

                    } catch (NumberFormatException ex) {

                        writeText('"' + bits[1] + "\" is not a valid title ID.", 0);

                    }

                }

                break;

            default:

                writeText("Unknown command: \"" + text + "\"\nUse 'help' to see valid commands.", 0);
                break;

        }

    }

    private void writeText(final String text, int voiceID) {
        /*
        * Uses the typewriter effect to print text to the screen
        * SHOULD TYPICALLY ONLY BE CALLED FROM readInput()
        *
        * text: The text to be written
        * voiceID: ID for the sound to be played (use 0 for default or negative for silent)
        */

        while (timer.isRunning()) {

                System.out.println("Waiting...");

                try {

                    Thread.sleep(100);

                } catch (InterruptedException ex) {
                    
                    ex.printStackTrace();

                }

            }

        if (timer != null && timer.isRunning()) {

            System.out.println("WARN: Attempt to call writeText whilst timer is still active.\n" + text);
            return;

        }

        timer = new Timer(5, new AbstractAction() {

            char nextChar;
            int times = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) {
                    
                    nextChar = text.charAt(characterIndex++);
                    if (Character.isAlphabetic(nextChar) && playerRef.statuses.get("dazed") > 0 && r.nextInt(100) < 2) terminalScreen.append(String.valueOf((char) (r.nextInt(26) + 'a')));
                    else terminalScreen.append(String.valueOf(nextChar));

                    if (voiceID >= 0 && times++ % 15 == 0) voices[voiceID].command(2);

                } else {

                    if (!gameOver) terminalScreen.append("\n\n" + Locations.locations[Player.location] + '/' + Locations.sublocations[Player.location][Player.sublocation] + "> ");
                    timer.stop();
                    update();

                }

            }

        });

        Log.logData("Game says: '" + text + "'");
        characterIndex = 0;
        timer.start();

    }

    private void update() {
        /*
        * Runs every time the terminal stops writing text
        */

        playerBar.setText(playerRef.name + "\n\nHealth: " + playerRef.health + " / " + playerRef.healthDefault + "\nAttack: " + playerRef.attack + "\nDefense: " + playerRef.defense + "\n\nInventory\n" + playerRef.listItems());
        if (enemyRef != null) {

            Player.inCombat = true;
            if (enemyRef.isBoss) {

                Player.inBossfight = true;
                Audio.activeBG.command();

            }
            
            enemyBar.setText(enemyRef.name + "\n\nHealth: " + enemyRef.health + "\nAttack: " + enemyRef.attack + "\nDefense: " + enemyRef.defense);

        } else {

            Player.inCombat = false;
            Player.inBossfight = false;
            enemyBar.setText(null);

        }

        if (enemyRef != null && enemyTurn) {

            StringBuilder msg = new StringBuilder(64);
            msg.append(enemyRef.name + " attacks you for " + playerRef.getAttacked(enemyRef.attack) + " damage!");
            
            // Enemy ability
            if (playerRef.health > 0) {
            
                switch (enemyRef.name) {

                    case "Banshee":

                        if (r.nextInt(100) < 50) {

                            if (r.nextBoolean()) {

                                for (int c = 0; c < r.nextInt(1,5); c++) {

                                    switch (r.nextInt(5)) {

                                        case 0:

                                            playerRef.addItem(new Item("Trash", ItemType.Junk, "What even is this?", 0, false));
                                            break;

                                        case 1:

                                            playerRef.addItem(new Item("Waste", ItemType.Junk, "This is just junk.", 0, false));
                                            break;

                                        case 2:

                                            playerRef.addItem(new Item("Rubbish", ItemType.Junk, "A collection of trash.", 0, false));
                                            break;

                                        case 3:

                                            playerRef.addItem(new Item("Expired Something", ItemType.Health, "An expired piece of some unknown food.", -1, true));
                                            break;

                                        case 4:

                                            playerRef.addItem(new Item("Mystery Meat", ItemType.Health, "This could be anything.", c, true));
                                            break;

                                    }

                                }

                                msg.append("\nBanshee spams you with garbage!\nYour inventory gets heavier.");

                            } else {

                                playerRef.statuses.put("dazed", playerRef.statuses.get("dazed") + 3);
                                msg.append("\nBanshee emits an earsplitting shriek!\nYou're dazed!");

                            }

                        }

                        break;

                    case "Bot Swarm":

                        enemyRef.changeStats(0, r.nextInt(1, 3), r.nextInt(1, 2));
                        msg.append("\nThe swarm becomes more powerful!");
                        break;

                    case "Cleanser":

                        enemyRef.changeStats(0, 100, 0);
                        msg.append("\nCleanser prepares to anihilate you!");
                        break;

                    case "Cyber Scorpion":

                        if (r.nextBoolean()) {

                            playerRef.statuses.put("poison", playerRef.statuses.get("poison") + 1);
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
                                playerRef.statuses.put("poison", playerRef.statuses.get("poison") + 3);
                                break;

                            case 2:

                                msg.append("You've gome weak at the knees.");
                                playerRef.statuses.put("weak", playerRef.statuses.get("weak") + 1);
                                break;

                            case 3:

                                msg.append("A sudden blinding blast of inspiration!");
                                playerRef.statuses.put("blinded", playerRef.statuses.get("blind") + 1);
                                enemyRef.reset();
                                enemyRef = null;
                                break;

                        }

                        break;

                    case "Flashbang":

                        if (r.nextInt(100) < 300) {

                            if (r.nextBoolean()) {

                                playerRef.statuses.put("blinded", playerRef.statuses.get("blind") + 1);
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
                            enemyRef.reset();
                            enemyRef = null;

                        } else {

                            switch (r.nextInt(6)) {

                                case 1:

                                    playerRef.statuses.put("dazed", playerRef.statuses.get("dazed") + 3);
                                    msg.append("\nThe memory is sudden and bursting with emotion!\nYou've been dazed!");
                                    break;

                                case 3:

                                    playerRef.statuses.put("poison", playerRef.statuses.get("poison") + 5);
                                    msg.append("\nThe memory is soured and toxic.\nYou've been poisoned!");
                                    break;
                                
                                case 5:

                                    playerRef.statuses.put("fire", playerRef.statuses.get("fire") + 3);
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

                        if (r.nextBoolean() && enemyRef.flee(50)) {

                            enemyRef.reset();
                            enemyRef = null;
                            msg.append("\nScavenger wastes no time.\nScavenger has fled!");

                        }

                        break;

                    case "Scrambler":

                        // TODO: Scrambler ability
                        break;

                    case "Worm":

                        if (r.nextBoolean()) {

                            enemyRef.changeStats(enemyRef.healthDefault, enemyRef.attackDefault, 0);
                            msg.append("\nThe virus is replicating!");

                        }

                        break;

                }

            }

            enemyTurn = false;
            writeText(msg.toString(), 0);

        } else {

            enemyTurn = false;
            if (gameOver) return;
            else if (playerRef.health == 0) {

                byte numDown = 0;
                for (byte c = 0; c < Player.names.length; c++) if (players[c].health == 0) numDown++;
                if (numDown == Player.names.length) terminate();
                else {

                    playerSelect();
                    playerBar.setText(playerRef.name + "\n\nHealth: " + playerRef.health + " / " + playerRef.healthDefault + "\nAttack: " + playerRef.attack + "\nDefense: " + playerRef.defense + "\n\nInventory\n" + playerRef.listItems());

                }

            }

        }

    }

    private void terminate() {
        /*
         * Removes the inputField and displays a game over message
         */

        gameOver = true;
        terminalPanel.remove(inputField);
        mainframe.setTitle("Game Over");
        JOptionPane.showMessageDialog(terminalPanel, "You have been terminated.", Story.GAME_OVERS[r.nextInt(Story.GAME_OVERS.length)], JOptionPane.ERROR_MESSAGE);
        
    }

    private void playerSelect() {
        /*
        * Shows a menu with buttons for available characters
        */
        
        for (int c = 0; c < Player.names.length; c++) if (players[c].health > 0) charsPanel.add(characterButtons[c]);
        cards.next(basePanel);
        
    }

    private void setupUI() {
        /*
        * Sets up the game UI
        */

        // UI Base
        final Font gameFont = new Font("Cascadia Mono", Font.PLAIN, 20);
        mainframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainframe.setLayout(new BorderLayout());
        mainframe.add(basePanel, BorderLayout.CENTER);
        basePanel.setLayout(cards);
        basePanel.add(terminalPanel);
        basePanel.add(charsPanel);

        // Terminal
        terminalScreen.setLineWrap(true);
        terminalScreen.setWrapStyleWord(true);
        terminalScreen.setEditable(false);
        terminalScreen.setBackground(Color.BLACK);
        terminalScreen.setForeground(Color.GREEN);
        terminalScreen.setFont(gameFont);

        // Character selection
        charsPanel.setLayout(new FlowLayout());
        charsPanel.setBackground(Color.BLACK);

        // Scroll
        scrollPane = new JScrollPane(terminalScreen);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());

            }

        });

        // Player (left) sidebar
        playerBar.setFont(gameFont);
        playerBar.setEditable(false); // make player stats not editable
        playerBar.setBackground(Color.BLACK);
        playerBar.setForeground(Color.GREEN);
        playerBar.setBorder(new LineBorder(Color.GREEN, 1));

        // Enemy (right) sidebar
        enemyBar.setFont(gameFont);
        enemyBar.setEditable(false);
        enemyBar.setBackground(Color.BLACK);
        enemyBar.setForeground(Color.GREEN);
        enemyBar.setBorder(new LineBorder(Color.GREEN, 1));

        // Input
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.GREEN);
        inputField.setFont(gameFont);
        inputField.setBorder(new LineBorder(Color.GREEN));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.addActionListener((ActionEvent e) -> {

            String entered = "";
            boolean empty = false;

            try {

                entered = inputField.getText().strip();
                if (entered.length() == 0) empty = true;

            } catch (NullPointerException ex) {

                empty = true;

            }

            if (!empty) {

                Log.logData("Player enters: " + entered);
                terminalScreen.append(entered + "\n\n");
                readInput(entered);
                inputField.setText(null);

            } else Log.logData("Player attempted to enter nothing");

        });

        // Layout
        terminalPanel.setLayout(new BorderLayout());
        terminalPanel.add(inputField, BorderLayout.SOUTH);
        terminalPanel.add(scrollPane, BorderLayout.CENTER);
        mainframe.add(playerBar, BorderLayout.WEST);
        mainframe.add(enemyBar, BorderLayout.EAST);

        // Final
        mainframe.setTitle(Story.TITLE_STRINGS[0]);
        mainframe.setVisible(true);
        inputField.requestFocus();
        terminalScreen.setText("The Silver Slayer [Beta v0.1]\n\n" + Locations.locations[1] + '/' + Locations.sublocations[1][0] + "> ");

        playerSelect();

        // Wait
        while (playerRef == null) {

            try {

                Thread.sleep(100);

            } catch (InterruptedException ex) {
                
                ex.printStackTrace();

            }

        }

        // Randomize title
        mainframe.setTitle(Story.TITLE_STRINGS[r.nextInt(Story.TITLE_STRINGS.length)]);

        // Background service
        new Thread(() -> {

            while (true) {

                if (playerRef.statuses.get("blind") > 0 && terminalScreen.getBackground() != Color.WHITE) {

                    terminalScreen.setBackground(Color.WHITE);
                    playerBar.setBackground(Color.WHITE);
                    enemyBar.setBackground(Color.WHITE);
                    inputField.setBackground(Color.WHITE);
                    charsPanel.setBackground(Color.WHITE);

                } else if (playerRef.statuses.get("blind") <= 0 && terminalScreen.getBackground() != Color.BLACK) {

                    terminalScreen.setBackground(Color.BLACK);
                    playerBar.setBackground(Color.BLACK);
                    enemyBar.setBackground(Color.BLACK);
                    inputField.setBackground(Color.BLACK);
                    charsPanel.setBackground(Color.BLACK);
                    
                }

                try {

                    Thread.sleep(100);

                } catch (InterruptedException ex) {

                    Log.logData("WARN: Background UI service wait was interrupted");

                }

            }

        }).start();

    }

}