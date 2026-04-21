import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.security.SecureRandom;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Base64;

public class Menu {

    // Display
    private JFrame mainframe = new JFrame();
    private CardLayout cards = new CardLayout();
    private JPanel basePanel = new JPanel(), terminalPanel = new JPanel(), charsPanel = new JPanel();
    private JTextArea terminalScreen = new JTextArea("", 1, 30), playerBar = new JTextArea("", 1, 10), enemyBar = new JTextArea("ENEMY", 1, 10);;
    private JScrollPane scrollPane;
    private JTextField inputField = new JTextField();
    private JButton[] characterButtons = new JButton[Player.names.length];
    private JButton cancelButton = new JButton("Cancel");

    // Text
    private Timer timer = new Timer(0, null);
    private int characterIndex;

    // SQL
    private JFrame sqlFrame = new JFrame();
    private JPanel sqlPanel = new JPanel(new BorderLayout());
    private JTextArea sqlArea = new JTextArea();
    private JTextField sqlField = new JTextField();

    // Elements
    private SecureRandom r = new SecureRandom();
    private Player[] players = new Player[Player.names.length];
    private Player playerRef;
    private Enemy enemyRef;
    private final int pSwapCostMultipler = 5;
    private boolean enemyTurn = false, gameOver = false;

    // Sounds
    private Audio[] voices = {new Audio("voice_blip"), new Audio("voice_beep")};
    private Audio[] bossTracks = {new Audio("boss_village"), new Audio("boss_lake"), new Audio("boss_mountain"), new Audio("boss_desert"), new Audio("boss_swamp"), new Audio("boss_fracture"), new Audio("boss_lair")};
    private Audio damageSFX = new Audio("sfx_attack"), beatBossSFX = new Audio("sfx_beatboss");

    // This is here so it can be used in the ActionListerner creations below
    private byte counter;

    public Menu() {

        if (!Database.online) { // If database connection fails, cancel startup

            JOptionPane.showMessageDialog(mainframe, "The database connection was not successful.", "Database Offline", JOptionPane.ERROR_MESSAGE);
            TheSilverSlayer.shutdownNow();
            return;

        }

        Entity.setMenu(this);  // Pass menu reference to Entity

        for (byte c = 0; c < Player.names.length; c++) players[c] = new Player(Player.names[c]);
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
                    Player.bits -= Player.pSwaps * pSwapCostMultipler;
                    writeText("Selected " + playerRef.name + ". Spent " + Player.pSwaps++ * pSwapCostMultipler + " bits.", 0);
                    inputField.requestFocusInWindow();

                }
                
            });

        }

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                cards.next(basePanel);
                charsPanel.removeAll();

                Log.logData("Player cancelled character swap");
                writeText("Cancelled swap.", 0);
                inputField.requestFocusInWindow();

            }
            
        });

        setupUI();

    }

    private String doSearch() {
        /*
        * Called whenever the player runs the search command
        */

        String foundItem = Item.getItem(playerRef);
        if (foundItem != null) return foundItem;
        else if (Player.location == 4 && Player.sublocation == 1) {
            // Mountain path searches

            if (Player.mountainPathSearches == 0) {

                Story.updateEvent(412, "Attempting to follow the lost path's trail is difficult. You somehow keep ending up back at the signpost, over and over again.");
                Player.mountainPathSearches++;

            } else if (Player.mountainPathSearches == 1) {

                Story.updateEvent(412, "It almost feels like you're being pushed away from your destination. The plants and landmarks never seem to repeat, yet you always return to the signpost.");
                Player.mountainPathSearches++;

            } else if (Player.mountainPathSearches == 2) {

                Story.updateEvent(412, "The signpost's missing text has reappeared. It reads: \"enough\"");
                Player.mountainPathSearches++;

            } else if (Player.mountainPathSearches == 3) {

                Story.updateEvent(412, "Why do you feel so... ill? Give up the search. Give it up. There's nothing to be found.");
                Player.mountainPathSearches++;

            } else if (Player.mountainPathSearches == 4) {

                Story.updateEvent(412, "You're not wanted here. This place isn't for you. Let it rest. You don't want their attention.");
                Player.mountainPathSearches++;

            } else if (Player.mountainPathSearches == 5) {

                Story.updateEvent(412, "Something comes crashing down from high above, barreling towards you at high speed. You barely manage to leap away. The object slams into the ground, and then turns towards you..." + Locations.Mountain[Locations.Mountain.length - 1].spawnMsg);
                Player.mountainPathSearches++;
                
            } else if (Player.mountainPathSearches == 6) {
                
                if (Locations.Mountain[Locations.Mountain.length - 1].health > 0) enemyRef = Locations.spawnEnemy(4, true);
                Story.updateEvent(412, "The signpost's missing text has reappeared. It reads: \"Fracture\"");
                Player.mountainPathSearches++;

            }

            return "";

        } else return "";

    }

    private void readInput(String text) {
        /*
         * Called whenever the player enters text
         * text: The String that was submitted
         */

        if (gameOver) return;
        String[] bits = text.toLowerCase().split(" ");
        switch (bits[0]) {

            // STORY COMMANDS

            case "ls":
            case "look":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else writeText(Story.getLookEvent(Player.location, Player.sublocation), 0);
                break;

            case "grep":
            case "search":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else writeText(Story.getSearchEvent(Player.location, Player.sublocation) + doSearch(), 0);
                break;

            case "cd":
            case "goto":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else if (bits.length < 2) writeText("Go where?", 0);
                else if (Player.fractureMirrorMoves > 0 && Player.fractureMirrorMoves < 7) {

                    writeText("You are lost in the mirrors!", 0);
                    Player.fractureMirrorMoves++;

                } else if (Player.location == 2 && Locations.Village[Locations.Village.length - 1].health > 0 && (bits[1].equals("lake") || bits[1].equals("mountain"))) {

                    writeText("As you approach the border to the village, you hear a sudden burst of heavy footsteps rushing up behind you. It seems your antics have attracted something's attention.", 0);
                    enemyRef = Locations.spawnEnemy(2, true);
                    Audio.activeTrack.stop();
                    bossTracks[0].play(true);
                
                } else if (Player.location == 5 && Locations.Desert[Locations.Desert.length - 1].health > 0 && bits[1].equals("swamp")) {

                    writeText("A massive barrier stands between you and the swamp. The heat emanating from it dwarfs the usual desert temperatures. There's no going around this; only through.", 0);
                    enemyRef = Locations.spawnEnemy(5, true);
                    Audio.activeTrack.stop();
                    bossTracks[3].play(true);

                } else if (playerRef.travel(bits[1])) {

                    if (Player.fractureMirrorMoves == 0 && Player.location == 7 && Player.sublocation == 2) Player.fractureMirrorMoves = 1;
                    else if (Player.location == 8 && Player.sublocation == 3 && Locations.Lair[Locations.Lair.length - 1].health > 0) {

                        enemyRef = Locations.spawnEnemy(8, true);
                        Audio.activeTrack.stop();
                        bossTracks[6].play(true);

                    }

                    if (enemyRef == null && Player.location >= 2) enemyRef = Locations.spawnEnemy(Player.location, false);
                    if (enemyRef != null) writeText(Story.getBaseEvent(Player.location, Player.sublocation) + enemyRef.spawnMsg, 0);
                    else writeText(Story.getBaseEvent(Player.location, Player.sublocation), 0);

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

                        if (bits[1].equals("silver") && playerRef.hasItem("Silver Sword")) {

                            writeText(Story.getUnlockEvent(1, 0, true), 0);
                            showImage("orange.png");
                            
                        } else writeText(Story.getUnlockEvent(1, 0, false), 0);

                    } else if (Player.location == 1 && Player.sublocation == 1) {

                        if (bits[1].equals(new String(Base64.getDecoder().decode("ODM5MjczNTQ="))) || Story.wasEventSeen(113)) {

                            writeText(Story.getUnlockEvent(1, 1, true), 0);
                            Story.updateEvent(115, "The gate remains open, and the padlock still rests nearby. The occassional breeze causes a gentle creaking in the iron.");
                            Story.updateEvent(111, "The gate is old and weathered.");
                            Story.updateEvent(113, "This lock is open already.");

                        } else writeText(Story.getUnlockEvent(1, 1, false), 0);

                    } else if (Player.location == 2 && Player.sublocation == 0) {

                        if (!Database.online) writeText("The database connection is offline.", 0);
                        else {

                            String code = Database.getMountainCode();
                            if (code == null || code.length() == 0) {

                                writeText("The lock is broken?!", -1);
                                JOptionPane.showMessageDialog(mainframe, "There was an error accessing the database. Save your game, exit, and fix any database problems.", "Database Error", JOptionPane.ERROR_MESSAGE);

                            } else if (bits[1].equals(code) || Story.wasEventSeen(203)) {

                                writeText(Story.getUnlockEvent(2, 0, true), 0);

                            } else writeText(Story.getUnlockEvent(2, 0, false), 0);

                        }

                    } else if (Player.location == 4 && Player.sublocation == 1) {

                        if (bits[1].equals("wooden") && playerRef.hasItem("Wooden Club")) {

                            writeText(Story.getUnlockEvent(4, 1, true), 0);
                            Story.updateEvent(413, "The chains lie in a ruined pile.");
                            // TODO: Find a better way to store new events, then add updates for the ones related to this action

                        } else writeText(Story.getUnlockEvent(4, 1, false), 0);

                    } else writeText("There's nothing to unlock here.", 0);

                }

                break;
 
            // GAMEPLAY COMMANDS

            case "sql":

                if (Database.online) {

                    sqlFrame.setVisible(true);
                    writeText("Opening.", -1);

                } else writeText("The database connection is offline.", 0);
                break;

            case "phish":
            case "fish":

                if (!Story.wasEventSeen(312)) writeText("You do not know where to fish.", 0);
                else if (Player.location == 3 && Player.sublocation == 0) {

                    int emailSlot = -1;
                    for (int i = 0; i < Player.inventory.length; i++) {

                        if (Player.inventory[i] != null && Player.inventory[i].name.equals("Email")) {

                            emailSlot = i;
                            break;

                        }

                    }

                    if (emailSlot == -1) writeText("You need bait in order to fish, or should I say... phish?", 0);
                    else {

                        playerRef.removeItem(emailSlot);

                        // randomly generate phishing result *FAKE NAME & FAKE SSN*
                        String fullName = Story.fNames[r.nextInt(Story.fNames.length)] + " " + Story.lNames[r.nextInt(Story.lNames.length)];
                        String randomSSN = String.format("%03d-%02d-%04d", r.nextInt(1000), r.nextInt(100), r.nextInt(10000));

                        writeText("You just phished " + fullName + " with SSN " + randomSSN + "!\n" + "Now you have their identity!", 0);
                
                    }

                } else writeText("There's not a good spot here.", 0);
                break;
            
            case "map":
            	
            	showImage("TSSMain.png");
            	writeText("Displaying map.", -1);
            	break;

            case "whoami":
            case "chars":
            case "char":

                playerSelect(true);
                break;

            case "desc":
            case "describe":

                if (bits.length < 2) writeText("Specify an inventory slot.", 0);
                else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot >= Player.invCap) writeText(slot + " is not a valid inventory slot.", 0);
                        else if (Player.inventory[slot] == null) writeText("Slot " + slot + " is empty.", 0);
                        else writeText(Player.inventory[slot].description, 0);

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
                        if (slot < 0 || slot >= Player.invCap) writeText(slot + " is not a valid inventory slot.", 0);
                        else if (Player.inventory[slot] == null) writeText("Slot " + slot + " is empty.", 0);
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
                        if (slot < 0 || slot >= Player.invCap) writeText(slot + " is not a valid inventory slot.", 0);
                        else if (Player.inventory[slot].equipped) writeText("Unequip the item first.", 0);
                        else if (Player.inventory[slot] == null) writeText("Slot " + slot + " is already empty.", 0);
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
                    
                    int atkDmg = (playerRef.currentWeapon != null) ? playerRef.attack + playerRef.currentWeapon.magnitude : playerRef.attack;
                    if (playerRef.statuses.get("weak") > 0) atkDmg *= 0.7;
                    
                    int dmgDealt = enemyRef.getAttacked(atkDmg);
                    damageSFX.play(false);

                    if (enemyRef.health == 0) {

                        if (enemyRef.isBoss) writeText("Attacked " + enemyRef.name + " for " + dmgDealt + " damage!\n" + Story.BOSS_DEFEATED[Player.location], 0);
                        else {

                            writeText("Attacked " + enemyRef.name + " for " + dmgDealt + " damage!\n" + enemyRef.name + " has been defeated.", 0);
                            enemyRef.reset();

                        }

                        despawnEnemy(true);

                    } else {

                        writeText("Attacked " + enemyRef.name + " for " + dmgDealt + " damage!", 0);
                        enemyTurn = true;

                    }
                    
                }

                break;

            case "flee":

                if (enemyRef == null) writeText("There's nothing to run from (yet).", 0);
                else if (enemyRef.isBoss) {

                    if (Player.location == 2) writeText("The Guardian is faster than you and intent on fighting. You need to defeat it.", 0);
                    else if (Player.location == 4) writeText("The one time you want to escape, you can't. Should've listened to the warnings.", 0);
                    else if (Player.location == 5) writeText("The flames have warped to block your retreat!", 0);
                    else if (Player.location == 8) writeText("You either win, or you don't ever leave.", 0);

                } else {

                    if (playerRef.flee(65)) {

                        if (playerRef.flee(80)) writeText("You fled from " + enemyRef.name + '\n' + Story.FLEE_STRINGS[r.nextInt(Story.FLEE_STRINGS.length)], 0);
                        else writeText("You fled from " + enemyRef.name + ", but not unscathed.\nReceived " + playerRef.getAttacked(enemyRef.attack) + " damage!", 0);
                        despawnEnemy(false);

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

            // GENERAL COMMANDS

            case "help":

                if (enemyRef != null && enemyRef.name.equals("The Silver Slayer")) writeText("There's no help for you now.", 0);
                else writeText(Story.HELP_TEXT, 0);
                break;

            case "save":

                if (enemyRef == null) {

                    if (Save.saveGame(players, playerRef, true)) writeText("Game saved.", 0);
                    else writeText("Failed to save game! Check log file.", 0);
                    

                } else writeText("Cannot save during combat!", 0);

                break;

            case "quit":
            case "exit":

                Log.logData("Shutting down. Goodbye!");
                if (Database.online) Database.closeConnection();
                Log.closeLog();
                mainframe.dispatchEvent(new WindowEvent(mainframe, WindowEvent.WINDOW_CLOSING));
                break;

            case "clear":

                terminalScreen.setText(null);
                writeText(Story.header, -1);
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

            // DEBUG COMMANDS

            case "warp":

                if (TheSilverSlayer.debug) {

                    if (++Player.location > 8) Player.location = 1;
                    Player.sublocation = 0;
                    Audio.activeTrack.stop();
                    writeText("Away you go!", -1);
                    break;
                    
                }

            default:

                writeText("Unknown command: \"" + text + "\"\nUse 'help' to see valid commands.", 0);
                break;

        }

    }

    private void writeText(final String text, int voiceID) {
        /*
        * Uses the typewriter effect to print text to the screen
        * text: The text to be written
        * voiceID: ID for the sound to be played (use 0 for default or negative for silent)
        */

        if (timer.isRunning()) {

            Log.logData("FATAL: Attempt to start writeText timer while its still active.");
            TheSilverSlayer.shutdownNow();

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

                    if (voiceID >= 0 && times++ % 15 == 0) voices[voiceID].play(false);

                } else {

                    if (!gameOver) terminalScreen.append("\n\n" + Locations.locations[Player.location] + '/' + Locations.sublocations[Player.location][Player.sublocation] + "> ");
                    timer.stop();
                    update();

                }

            }

        });

        Log.logData("Game responds via writeText().");
        characterIndex = 0;
        timer.start();

    }

    private void update() {
        /*
        * Runs every time the terminal stops writing text
        */

        String stats = playerRef.statusEffects();
        
        // Update player sidebar
        int totalAtk = (playerRef.currentWeapon != null) ? playerRef.attack + playerRef.currentWeapon.magnitude : playerRef.attack;
        int totalDef = (playerRef.currentArmor != null) ? playerRef.defense + playerRef.currentArmor.magnitude : playerRef.defense;
        playerBar.setText(playerRef.name + "\n\nHealth: " + playerRef.health + " / " + playerRef.healthDefault + "\nAttack: " + totalAtk + "\nDefense: " + totalDef + "\n\nInventory\n" + playerRef.listItems() + stats);
        
        if (gameOver) return;
        else if (playerRef.health == 0) {

            byte numDown = 0;
            for (byte c = 0; c < Player.names.length; c++) if (players[c].health == 0) numDown++;
            if (numDown == Player.names.length || Player.bits < Player.pSwaps * pSwapCostMultipler) {

                gameOver = true;
                mainframe.setTitle("Game Over");
                JOptionPane.showMessageDialog(terminalPanel, "You have been terminated.", Story.GAME_OVERS[r.nextInt(Story.GAME_OVERS.length)], JOptionPane.ERROR_MESSAGE);

            } else playerSelect(false);

        } else if (enemyRef != null) {

            Player.inCombat = true;
            Player.inBossfight = enemyRef.isBoss;

            String enStats = enemyRef.statusEffects();
            enemyBar.setText(enemyRef.name + "\n\nHealth: " + enemyRef.health + "\nAttack: " + enemyRef.attack + "\nDefense: " + enemyRef.defense + enStats);

            if (enemyTurn) {

                enemyTurn = false;
                writeText(enemyRef.enemyAttack(playerRef), 0);

            }

        } else {

            Player.inCombat = false;
            Player.inBossfight = false;
            enemyBar.setText(null);

        }

    }

    public void despawnEnemy(boolean awardBits) {
        /*
        * Despawns the currrent enemy, and optionally awards bits
        * awardBits: Whether to dispense bits to the player
        */

        if (awardBits) {

            int b = r.nextInt(1, enemyRef.healthDefault * 5);
            Player.bits += b;
            Log.logData("Despawning " + enemyRef.name + ". Awarded " + b + " bits");

            if (Player.bits > 9999) {

                Player.bits = 9999;
                Log.logData("Clamped bits to maximum allowed");

            }

        } else Log.logData("Despawning " + enemyRef.name);

        if (!enemyRef.isBoss) enemyRef.reset();
        else {

            Player.invCap++;
            bossTracks[Player.location - 2].stop();
            beatBossSFX.play(false);

        }

        enemyRef = null;
        enemyTurn = false;

    }

    private void playerSelect(boolean allowCancel) {
        /*
        * Shows a menu with buttons for available characters
        * Costs an increasing amount of bits to swap
        * allowCancel: Whether the cancel button is shown
        */
        
        int cost = Player.pSwaps * pSwapCostMultipler;
        if (Player.bits < cost) writeText("You need " + cost + " bits to swap characters.", 0);
        else {

            enemyTurn = true;
            for (int c = 0; c < Player.names.length; c++) if (players[c].health > 0) charsPanel.add(characterButtons[c]);
            if (allowCancel) charsPanel.add(cancelButton);
            cards.next(basePanel);

        }
        
    }
    
    private void showImage(String file) {
        /*
        * Displays an image in a new JFrame
        * file: The name of the image file to be displayed
        */
    	
    	JFrame imageFrame = new JFrame();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("images/" + file));
        Image scaledImage = imageIcon.getImage().getScaledInstance(1280,720, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        imageFrame.setSize(1280,720);        
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imageFrame.add(imageLabel);
        imageFrame.setLocationRelativeTo(null);
        imageFrame.setVisible(true);

        imageLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                // click on map to open minimaps
                if (file.equals("TSSMain.png")) {

                    //Village Location
                    if (x > 230 && x < 430 && y < 200 && y > 90) showImage("Town.png");

                    //Lake Location
                    if (x > 400 && x < 590 && y < 360 && y > 300) showImage("Lake.png");
                    
                    // Mountain Location
                    if (x > 620 && x < 820 && y < 120 && y > 20) showImage("Mountain.png");
                    
                    // Desert Location
                    if (x > 730 && x < 900 && y < 340 && y > 290) showImage("Desert.png");
                    
                    // Swamp Location
                    if (x > 850 && x < 1000 && y < 560 && y > 430) showImage("Swamp.png");
                    
                    // Evil Lair Location
                    if (x > 1000 && x < 1170 && y < 410 && y > 300) showImage("Lair.png");
                    
                    // Fracture Location
                    if (x > 1130 && x < 1240 && y < 140 && y > 80) showImage("Fracture.png");
                }
            }
        });

        // closes image when user hits escape key
        KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                imageFrame.dispose();
            }
        };
        imageFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKey, "ESCAPE");
        imageFrame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
    	
    }

    private void setupUI() {
        /*
        * Sets up the game UI
        */

        // UI Base
        final Font gameFont = new Font("Cascadia Mono", Font.PLAIN, 20);

        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainframe.setLayout(new BorderLayout());
        mainframe.add(basePanel, BorderLayout.CENTER);

        basePanel.setLayout(cards);
        basePanel.add(terminalPanel);
        basePanel.add(charsPanel);

        // Character selection screen
        charsPanel.setLayout(new FlowLayout());
        charsPanel.setBackground(Color.BLACK);

        // Terminal
        terminalScreen.setLineWrap(true);
        terminalScreen.setWrapStyleWord(true);
        terminalScreen.setEditable(false);
        terminalScreen.setBackground(Color.BLACK);
        terminalScreen.setForeground(Color.GREEN);
        terminalScreen.setFont(gameFont);

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
        playerBar.setEditable(false);
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
        inputField.setFont(gameFont);
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.GREEN);
        inputField.setBorder(new LineBorder(Color.GREEN));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.addActionListener((ActionEvent e) -> {

            String entered = inputField.getText().strip();
            if (!(entered == null || entered.length() == 0) && !timer.isRunning()) {

                Log.logData("Player entered: " + entered);
                terminalScreen.append(entered + "\n\n");
                readInput(entered);
                inputField.setText(null);

            }

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
        terminalScreen.setText(Story.header + "\n\n" + Locations.locations[1] + '/' + Locations.sublocations[1][0] + "> ");

        playerSelect(false);

        // Wait
        while (playerRef == null) {

            try {

                Thread.sleep(100);

            } catch (InterruptedException ex) {
                
                Log.logData("WARN: Wait while playerRef is null interrupted.");

            }

        }

        // Randomize title
        mainframe.setTitle(Story.TITLE_STRINGS[r.nextInt(Story.TITLE_STRINGS.length)]);

        // SQL Window Setup
        sqlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sqlFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        sqlFrame.add(sqlPanel);
        sqlPanel.add(sqlArea, BorderLayout.CENTER);
        sqlPanel.add(sqlField, BorderLayout.SOUTH);
        sqlArea.setEditable(false);
        sqlField.addActionListener((ActionEvent e) -> {

            String entered = sqlField.getText().strip();
            if (!(entered == null || entered.length() == 0)) {

                sqlArea.setText(Database.makeUnsafeQuery(entered));
                sqlField.setText(null);

            }

        });

        // Background service
        new Thread(() -> {

            while (true) {

                if (terminalScreen.getBackground() != Color.WHITE && playerRef.statuses.get("blind") > 0) {

                    terminalScreen.setBackground(Color.WHITE);
                    playerBar.setBackground(Color.WHITE);
                    enemyBar.setBackground(Color.WHITE);
                    inputField.setBackground(Color.WHITE);
                    charsPanel.setBackground(Color.WHITE);

                } else if (terminalScreen.getBackground() != Color.BLACK && playerRef.statuses.get("blind") <= 0) {

                    terminalScreen.setBackground(Color.BLACK);
                    playerBar.setBackground(Color.BLACK);
                    enemyBar.setBackground(Color.BLACK);
                    inputField.setBackground(Color.BLACK);
                    charsPanel.setBackground(Color.BLACK);
                    
                }

                try {

                    Thread.sleep(100);

                } catch (InterruptedException ex) {

                    Log.logData("WARN: Background UI service wait was interrupted.");

                }

            }

        }).start();

    }

    public void squishUI() {
        /*
        * Used by the Flashbang enemy to distort the UI
        */

        mainframe.setSize(new Dimension(800, 1200));

    }

    public void corruptUI() {
        /*
        * Used by the Faceless enemy to corrupt the UI
        * No I didn't copy/paste the previous function, this was manually typed
        * Why? Idk
        */

        // TODO: Make better and undo eventually
        playerBar.setVisible(false);
        enemyBar.setVisible(false);

    }

}