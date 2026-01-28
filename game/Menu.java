import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

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
    private Random r = new Random();
    private Save save;
    private Story theStory = new Story(); // Making it create a "new story" has so much aura
    private Player[] players = new Player[Player.names.length];
    private Player playerRef;
    private Enemy enemyRef;
    private boolean enemyTurn = false, gameOver = false;

    // Sounds
    private Audio[] voices = {new Audio("blip")};
    private Audio[] bossTracks = {null, null, null, null, null, null, null, null, new Audio("boss_battle_loop")};
    private Audio damageSFX = new Audio("damage");

    private byte counter; // This is here so it can be used in the ActionListerner creations below

    // Items player can get
    private boolean silverSpoon = false;
    private boolean magicKey = false;
    private boolean silverSword = false;



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
        
        for (int c = 0; c < Player.names.length; c++) players[c] = new Player(Player.names[c]);
        setupUI();

    }

    // Adding items to inventory based on player loc & subloc (used while searching)
    private String getItems( int loc, int sub) {
        if (playerRef == null) return null;
        
        // Silver Spoon
        if (!silverSpoon && loc == 2 && sub == 1 && theStory.wasEventSeen(212)) {
            int invSlot = playerRef.addItem(new Item("Silver Spoon", ItemType.Weapon, "A shiny silver spoon.", 10, false));
            silverSpoon = true;
            // If inventory is full
            if (invSlot == -1) return "Inventory Full";
            else return "Added the Silver Spoon to slot " + invSlot + ".";
        }

        // Magic Key
        if (!magicKey && loc == 8 && sub == 3 && theStory.wasEventSeen(833)) {
            int invSlot = playerRef.addItem(new Item("Magic Key", ItemType.Unassigned, "A key that opens a mysterious chest", 0, false));
            magicKey = true;
            // If inventory is full
            if (invSlot == -1) return "Inventory Full";
            else return "Added the Magic Key to slot " + invSlot + ".";
        }

        // Silver Sword
        if (!silverSword && loc == 8 && sub == 3 && theStory.wasEventSeen(833)) {
            int invSlot = playerRef.addItem(new Item("Silver Sword", ItemType.Weapon, "A sharp silver sword", 0, false));
            silverSword = true;
            // If inventory is full
            if (invSlot == -1) return "Inventory Full";
            else return "Added the Silver Sword to slot " + invSlot + ".";
        }
        
        return null;
    }

    private void readInput(String text) {
        /*
         * Called whenever the player enters text
         * 
         * text: The String that was submitted
         */

        if (gameOver) return;
        String[] bits = text.toLowerCase().split(" ");

        if (playerRef == null) {

            if (bits[0].equals("whoami")) {

                playerSelect();
                return;

            } else if (bits[0].equals("exit") || bits[0].equals("quit")) {

                Log.logData("Shutting down. Goodbye!");
                Log.closeLog();
                mainframe.dispatchEvent(new WindowEvent(mainframe, WindowEvent.WINDOW_CLOSING));

            } else {

                terminalScreen.append("Begin by typing 'whoami'\n\n" + Locations.locations[1] + '/' + Locations.sublocations[1][0] + "> ");
                return;

            }

        }

        switch (bits[0]) {

            // STORY COMMANDS

            case "ls":
            case "look":
                if (enemyRef != null) writeText("You're in combat!", 0);
                else writeText(theStory.getLookEvent(Player.location, Player.sublocation), 0);
                break;

            case "search":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else {
                    String searchTxt = theStory.getSearchEvent(Player.location, Player.sublocation);
                    String itemTxt = getItems(Player.location, Player.sublocation);
                    if (itemTxt != null && !itemTxt.isEmpty()) searchTxt = searchTxt + "\n" + itemTxt;
                    writeText(searchTxt, 0);
                }
                break;

            // case "pickup":
            // case "get":

            //     Unnecessary; items are automatically added on search
                
            //     break;

            // case "enter":

                //TODO: Expansion of enter

            case "goto":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else if (bits.length < 2) writeText(Player.getAvailablePlaces(), 0);
                else if (playerRef.travel(bits[1].toLowerCase())) {

                    switch (Player.location) {

                        case 8:

                            if (Player.sublocation == 3) {

                                enemyRef = Locations.spawnEnemy(8, true);
                                bossTracks[8].command(1);

                            }

                            // no break here

                        default:

                            if (enemyRef == null && Player.location >= 2) enemyRef = Locations.spawnEnemy(Player.location, false);
                            writeText(theStory.getBaseEvent(Player.location, Player.sublocation), 0);

                    }

                } else writeText("You can't get there from here, if there even exists.", 0);
                break;

            case "unlock":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else if (bits.length < 2) writeText("Specify the code after 'unlock'!", 0);
                else {

                    if (Player.location == 1 && Player.sublocation == 1) {

                        if (bits[1].equals("a") || Player.gates[0]) {

                            writeText(theStory.getUnlockEvent(1, 1, true), 0);
                            if (!Player.gates[0]) {

                                Player.gates[0] = true;
                                theStory.start.put(110, "You step up to the gate. The iron doors stand open, and an old lock rests on the ground nearby.");
                                theStory.start.put(111, "The gate is old an weathered.");
                                theStory.start.put(113, "This lock is open already.");

                            }

                        } else writeText(theStory.getUnlockEvent(1, 1, false), 0);

                    }

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
                else writeText("GENERAL\nclear: Clear screen\nexit / quit: Quit the game.\ntitle [int]: Display a random title or specifiy\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int): Drop an item\nuse (int): Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy\nflee: Run away", 0);
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

            /*case "settings":
            case "setting":
            case "options":
            case "option":

                writeText("Options", 0);
                break;*/

                //TODO: Settings?

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
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) terminalScreen.append(String.valueOf(text.charAt(characterIndex++)));
                else {

                    if (voiceID >= 0) voices[voiceID].command();
                    if (!gameOver) terminalScreen.append("\n\n" + Locations.locations[Player.location] + '/' + Locations.sublocations[Player.location][Player.sublocation] + "> ");
                    timer.stop();
                    update();

                }

            }

        });

        if (voiceID >= 0) voices[voiceID].command(1);
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
            switch (enemyRef.name) {

                case "Flashbang":

                    if (r.nextInt(100) < 300) {

                        if (r.nextBoolean()) {

                            playerRef.statuses.put("blinded", 3);
                            msg.append("\nFlashbang blinds you!");

                        } else {

                            //TODO: 4:3 resolution
                            msg.append("\nFlashbang distorts your vision!");

                        }

                    }

            }

            enemyTurn = false;
            writeText(msg.toString(), 0);

        } else {

            enemyTurn = false;
            if (gameOver) return;
            if (playerRef.health == 0) {

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
        inputField.setBorder(BorderFactory.createEmptyBorder()); // removes border from input field
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

            } else Log.logData("Player entered empty input");

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
        terminalScreen.setText("The Silver Slayer [Beta v0.1]\n\nWelcome\nBegin by typing 'whoami'\n\n" + Locations.locations[1] + '/' + Locations.sublocations[1][0] + "> ");

        // Wait
        while (playerRef == null) {

            try {

                Thread.sleep(1000);

            } catch (InterruptedException ex) {
                
                ex.printStackTrace();

            }

        }

        // Randomize title
        mainframe.setTitle(Story.TITLE_STRINGS[r.nextInt(Story.TITLE_STRINGS.length)]);

        // Background service
        new Thread(() -> {

            while (true) {

                if (playerRef.statuses.get("blinded") > 0 && terminalScreen.getBackground() != Color.WHITE) {

                    terminalScreen.setBackground(Color.WHITE);
                    playerBar.setBackground(Color.WHITE);
                    enemyBar.setBackground(Color.WHITE);
                    inputField.setBackground(Color.WHITE);
                    charsPanel.setBackground(Color.WHITE);

                } else if (playerRef.statuses.get("blinded") <= 0 && terminalScreen.getBackground() != Color.BLACK) {

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