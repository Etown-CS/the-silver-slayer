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
    private boolean gameOver = false;

    // Sounds
    private Audio[] voices = {new Audio("blip.wav")};
    private Audio[] music = {new Audio("mushroom_music.wav"), new Audio("boss_battle_loop.wav")};
    private Audio damageSFX = new Audio("damage.wav");

    private byte counter; // This is here so it can be used in the ActionListerner creations below
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
                    inputField.requestFocusInWindow();
                    updatePlayerBar();

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
        terminalScreen.setText("The Silver Slayer [Beta v0.1]\n\nWelcome\nYou are at the Gate.\nBegin by typing 'enter'\n\n" + Locations.locations[1] + '/' + Locations.sublocations[1][0] + "> ");

    }

    public void updatePlayerBar() {
        /*
         * Updates the player's sidebar
         */

        playerBar.setText("PLAYER\n\n" + playerRef.name + "\n\nHealth: " + playerRef.health + " / " + playerRef.healthDefault + "\nAttack: " + playerRef.attack + "\nDefense: " + playerRef.defense);

    }

    public void updateEnemyBar() {
        /*
         * Updates the enemy sidebar
         */

        if (enemyRef != null) enemyBar.setText("ENEMY\n" + enemyRef.name + "\n\nHealth: " + enemyRef.health + "\nAttack: " + enemyRef.attack + "\nDefense: " + enemyRef.defense);

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

            case "look":

                writeText(theStory.getLookEvent(Player.location, Player.sublocation), 0);
                break;

            case "search":

                writeText(theStory.getSearchEvent(Player.location, Player.sublocation), 0);
                break;

            case "enter":

                //TODO: Expansion of enter

            case "goto":

                if (enemyRef != null) writeText("You're in combat!", 0);
                else if (bits.length < 2) writeText(Player.getAvailablePlaces(), 0);
                else if (playerRef.travel(bits[1].toLowerCase())) {

                    switch (Player.location) {

                        case 8:

                            if (Player.sublocation == 3) {

                                enemyRef = Locations.spawnEnemy(8, true);
                                music[1].command(1);

                            }

                            // no break here

                        default:

                            if (enemyRef == null && Player.location >= 2) enemyRef = Locations.spawnEnemy(Player.location, false);
                            writeText(theStory.getBaseEvent(Player.location, Player.sublocation), 0);

                    }

                } else writeText("You can't get there from here, if there even exists.", 0);
                break;

            // GAMEPLAY COMMANDS

            case "characters":
            case "character":
            case "chars":
            case "char":

                playerSelect();
                writeText("Swapping!", -1);
                break;

            case "ls":
            case "inv":
            case "inventory":

                writeText(playerRef.listItems(), 0);
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
                    
                    if (enemyRef.isBoss && enemyRef.health == 0) {

                        damageSFX.command(2);
                        writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!\n" + Story.BOSS_DEFEATED[Player.location], 0);
                        enemyRef = null;
                        enemyBar.setText("ENEMY");

                    } else if (enemyRef.health == 0) {

                        damageSFX.command(2);
                        writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!\n" + enemyRef.name + " has been defeated.", 0);
                        enemyRef.reset();
                        enemyRef = null;
                        enemyBar.setText("ENEMY");

                    } else {

                        damageSFX.command(2);
                        writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!                    \n\n" + enemyRef.name + " attacks you for " + playerRef.getAttacked(enemyRef.attack) + " damage!", 0);
                        // there are 20 spaces in the line above

                    }
                    
                }

                break;

            case "flee":

                if (enemyRef == null) writeText("There's nothing to run from (yet).", 0);
                else {

                    // 75% chance to flee unharmed; 25% chance to take damage while fleeing
                    if (playerRef.flee(50)) {

                        if (playerRef.flee(75)) writeText("You fled from " + enemyRef.name + '\n' + Story.FLEE_STRINGS[r.nextInt(Story.FLEE_STRINGS.length)], 0);
                        else writeText("You fled from " + enemyRef.name + ", but not unscathed.\nReceived " + playerRef.getAttacked(enemyRef.attack) + " damage!", 0);

                        enemyRef.reset();
                        enemyRef = null;
                        enemyBar.setText("ENEMY");

                    } else writeText("You attempted to flee, but failed!\n" + enemyRef.name + " attacked you for " + playerRef.getAttacked(enemyRef.attack) + " damage!\n", 0);

                }

                break;

            // GENERAL COMMANDS

            case "help":

                writeText("GENERAL\nclear: Clear screen\nexit / quit: Quit the game.\nsettings: Modify game settings\ntitle [int]: Display a random title or specifiy\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int): Drop an item\ninv / inventory / ls: Display inventory\nuse (int): Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy\nflee: Run away", 0);
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

                try {

                    save.saveQuit(players, playerRef);
                    mainframe.dispatchEvent(new WindowEvent(mainframe, WindowEvent.WINDOW_CLOSING));

                } catch (IOException ex) {

                    JOptionPane.showMessageDialog(null, "Failed saving game!\nTrying again.", "FATAL", JOptionPane.ERROR_MESSAGE);
                    writeText("Attempting save... if no error shows after this, then it should be good.", -1);

                }

                break;

            case "settings":
            case "setting":
            case "options":
            case "option":

                writeText("TODO: Options", 0);
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

    public void writeText(final String text, int voiceID) {
        /*
        * Uses the typewriter effect to print text to the screen
        * SHOULD TYPICALLY ONLY BE CALLED FROM readInput()
        *
        * text: The text to be written
        * voiceID: ID for the sound to be played (use 0 for default or negative for silent)
        */

        if (timer != null && timer.isRunning()) {

            System.out.println("WARN: Attempt to call writeText whilst timer is still active.");
            return;

        }

        timer = new Timer(5, new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) terminalScreen.append(String.valueOf(text.charAt(characterIndex++)));
                else {

                    if (voiceID >= 0) voices[voiceID].command();
                    if (!gameOver) terminalScreen.append("\n\n" + Locations.locations[Player.location] + '/' + Locations.sublocations[Player.location][Player.sublocation] + "> ");
                    update();
                    timer.stop();

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

        updatePlayerBar();
        updateEnemyBar();

        if (gameOver) return;
        if (playerRef.health == 0) {

            int dead = 0;
            for (int c = 0; c < Player.names.length; c++) if (players[c].health == 0) dead++;
            if (dead == Player.names.length) terminate();
            else playerSelect();

        }

    }

    public void terminate() {
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

            if (!timer.isRunning() && inputField.getText().length() > 0) {

                terminalScreen.append(inputField.getText() + "\n\n");
                readInput(inputField.getText().strip());
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
        playerSelect();

    }

}