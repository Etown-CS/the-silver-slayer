import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.concurrent.Flow;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Menu {

    // Display
    private JFrame mainframe = new JFrame();
    private JPanel panel = new JPanel();
    private JTextArea terminal;
    private JTextArea playerBar;
    private JTextArea enemyBar;
    private JScrollPane scrollPane;
    private JTextField inputField;

    // Character selection screen
    private JFrame playerScreen;
    private JButton[] characterButtons = new JButton[SelectedPlayer.values().length];

    // Text
    private final Font gameFont = new Font("Cascadia Mono", Font.PLAIN, 20);;
    private Timer timer;
    private int characterIndex;

    // Elements
    private Random r = new Random();
    private Story theStory;
    private Player[] players = new Player[SelectedPlayer.values().length];;
    public Player playerRef;
    public Enemy enemyRef;
    public boolean gameOver = false;

    // Sounds
    private Audio[] voices = {new Audio("blip.wav")};
    private Audio damageSFX = new Audio("damage.wav");
    private Audio bgMusic;

    // Date and Time
    private DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("HH:mm:ss 'on' E, MMM dd yyyy");

    // Options
    private int textDelay = 5;

    // Storage
    private final String[] TITLE_STRINGS = {"Silver Slayer RPG", "Also try Terraria!", "Also try Minecraft!", "THE FOG IS COMING", 
                                            "There may be an egg", "It's " + LocalDateTime.now().format(dateTime) + " right now", 
                                            "here come dat boi", "JOHN WAS HERE", "The name is Gus... Amon Gus", "water bottle 😭", 
                                            "Microwave be like 'mmmmmm BEEP BEEP BEEP BEEP'", "-inf < x < inf", 
                                            "As I write this, it's 1:30pm on Friday, October 3rd, 2025", "[J]ohn, [A]sher, and [M]artin... JAM", 
                                            "Why am I writing these?", "Silksong is out!", "I ate my toothbrush :(", "o _ o", "get rekt", 
                                            "Low on magenta!", "Strings 🙏", "WORK is a dish best served NO", "jk jk............ unless?",
                                            "Remember to cave"};
    private final String[] FLEE_STRINGS = {"You can't run forever.", "You got away... for now.", "You'll be back."};
    private final String[] GAME_OVERS = {"How unfortunate", "That's gonna leave a mark", "Better luck some time!", "oof", "bruh.mp3",
                                            "Process killed"};

    public Menu() {
        /* Constructor */

        mainframe.setTitle(TITLE_STRINGS[0]);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        characterButtons[0] = new JButton("Bitter Java");
        characterButtons[0].addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = players[0];
                playerScreen.setVisible(false);
                mainframe.setVisible(true);
                updatePlayerBar(playerRef.name, playerRef.health, playerRef.attack, playerRef.defense);

            }

        });

        characterButtons[1] = new JButton("Brustel Sprout");
        characterButtons[1].addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = players[1];
                playerScreen.setVisible(false);
                mainframe.setVisible(true);
                updatePlayerBar(playerRef.name, playerRef.health, playerRef.attack, playerRef.defense);

            }

        });

        characterButtons[2] = new JButton("C--");
        characterButtons[2].addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = players[2];
                playerScreen.setVisible(false);
                mainframe.setVisible(true);
                updatePlayerBar(playerRef.name, playerRef.health, playerRef.attack, playerRef.defense);

            }

        });

        characterButtons[3] = new JButton("Dapper Python");
        characterButtons[3].addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = players[3];
                playerScreen.setVisible(false);
                mainframe.setVisible(true);
                updatePlayerBar(playerRef.name, playerRef.health, playerRef.attack, playerRef.defense);

            }

        });

        characterButtons[4] = new JButton("P. H. Periwinkle");
        characterButtons[4].addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = players[4];
                playerScreen.setVisible(false);
                mainframe.setVisible(true);
                updatePlayerBar(playerRef.name, playerRef.health, playerRef.attack, playerRef.defense);

            }

        });

        characterButtons[5] = new JButton("ReacTor");
        characterButtons[5].addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = players[5];
                playerScreen.setVisible(false);
                mainframe.setVisible(true);
                updatePlayerBar(playerRef.name, playerRef.health, playerRef.attack, playerRef.defense);

            }

        });

        characterButtons[6] = new JButton("Saea Quowle");
        characterButtons[6].addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = players[6];
                playerScreen.setVisible(false);
                mainframe.setVisible(true);
                updatePlayerBar(playerRef.name, playerRef.health, playerRef.attack, playerRef.defense);

            }

        });
        
        timer = new Timer(0, null);
        init();

    }

    public void updatePlayerBar(String name, int H, int A, int D) {
        /*
         * Updates the player's sidebar
         * 
         * H: Health value
         * A: Attack value
         * D: Defense value
         */

        playerBar.setText("PLAYER\n\n" + playerRef.name + "\n\nHealth: " + H + " / " + playerRef.healthCap + "\nAttack: " + A + "\nDefense: " + D);

    }

    private Enemy[] getEnemies(String location) {

        switch (location.toLowerCase()) {

            case "village": return Locations.Village;
            case "lake": return Locations.Lake;
            case "mountain": return Locations.Mountain;
            case "desert": return Locations.Desert;
            case "swamp": return Locations.Swamp;
            case "fracture": return Locations.Fracture;
            case "lair": return Locations.Lair;
            default: return null;

        }

    }

    public Enemy spawnEnemy() {

        Enemy[] enemies = getEnemies(playerRef.location);
        if (enemies != null) return enemies[r.nextInt(enemies.length - 2)];
        else return null;

    }

    public Enemy spawnBoss() {

        Enemy[] enemies = getEnemies(playerRef.location);
        return enemies[enemies.length - 1]; // Boss is always the last enemy in the array
        
    }

    public void updateEnemyBar(String name, int H, int A, int D) {
        /*
         * Updates the enemy sidebar
         * 
         * enemy: A reference to the current enemy
         * H: Health value
         * A: Attack value
         * D: Defense value
         */

        enemyBar.setText("ENEMY\n" + name + "\n\nHealth: " + H + "\nAttack: " + A + "\nDefense: " + D);

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

            case "enter":

                if (bgMusic != null) bgMusic.command(0);
                bgMusic = new Audio("mushroom_music.wav");
                bgMusic.command(1);
                playerRef.location = "Village";

                // maybe spawn an enemy??? its a gamble for sure
                if (enemyRef == null) {

                    int chance = 25; // 25% chance to see an enemy
                    System.out.println("Attempting to spawn enemy with chance: " + chance);

                    if (r.nextInt(100) < chance) {

                        Enemy enemy = spawnEnemy();
                        if (enemy != null) {

                            enemyRef = enemy;
                            updateEnemyBar(enemyRef.name, enemyRef.health, enemyRef.attack, enemyRef.defense);

                        } else System.out.println("Did not spawn anything.");

                    }

                }

                writeText(theStory.getEvent(1, 100), 0);
                break;

            // GAMEPLAY COMMANDS

            case "characters":
            case "character":

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
                    updateEnemyBar(enemyRef.name, enemyRef.health, enemyRef.attack, enemyRef.defense);
                    
                    if (enemyRef.health == 0) {

                        damageSFX.command(2);
                        writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!\n" + enemyRef.name + " has been defeated.", 0);
                        enemyRef.reset();
                        enemyRef = null;
                        enemyBar.setText("ENEMY");
                        
                    } else {

                        damageSFX.command(2);
                        writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!                    \n\n" + enemyRef.name + " attacks you for " + playerRef.playerAttacked(enemyRef.attack) + " damage!", 0);
                        // there are 20 spaces in the line above

                    }
                    
                }

                break;

            case "flee":

                if (enemyRef == null) writeText("There's nothing to run from.", 0);
                else {

                    writeText("You fled from " + enemyRef.name + ".\n" + FLEE_STRINGS[r.nextInt(FLEE_STRINGS.length)], 0);
                    enemyRef = null;
                    enemyBar.setText("ENEMY");
                    // TODO: Make it a percent chance to escape and/or take damage

                }

                break;

            // GENERAL COMMANDS

            case "help":

                writeText("GENERAL\nclear: Clear screen\nexit / quit: Quit the game.\nsettings: Modify game settings\ntitle [int]: Display a random title or specifiy\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int): Drop an item\ninv / inventory / ls: Display inventory\nuse (int): Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy\nflee: Run away", 0);
                break;

            case "quit":
            case "exit":

                mainframe.dispatchEvent(new WindowEvent(mainframe, WindowEvent.WINDOW_CLOSING));
                break;

            case "settings":

                writeText("TODO: Options", 0);
                break;

            case "clear":

                terminal.setText(null);
                writeText("The Silver Slayer [Beta v1.0]", -1);
                break;

            case "title":

                if (bits.length < 2) {

                    mainframe.setTitle(TITLE_STRINGS[r.nextInt(TITLE_STRINGS.length)]);
                    writeText("Rerolled title!", 0);

                } else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot > TITLE_STRINGS.length - 1) writeText(slot + " is out of range.", 0);
                        else {

                            mainframe.setTitle(TITLE_STRINGS[slot]);
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
        
        if (voiceID >= 0) voices[voiceID].command(1);
        characterIndex = 0;

        timer = new Timer(textDelay, new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) terminal.append(String.valueOf(text.charAt(characterIndex++)));
                else {

                    if (voiceID >= 0) voices[voiceID].command();
                    if (!gameOver && playerRef != null) terminal.append("\n\n" + playerRef.location + "/" + playerRef.sublocation + " > ");
                    else if (!gameOver) terminal.append("\n\nStart/Gate>");
                    timer.stop();

                }

            }

        });

        timer.start();

    }

    public void terminate() {
        /*
         * Ends the game
         * Remove the inputField and displays a game over message
         */

        if (bgMusic != null) bgMusic.command(0);
        panel.remove(inputField);
        mainframe.setTitle("Game Over");
        JOptionPane.showMessageDialog(panel, "You have been terminated.", GAME_OVERS[r.nextInt(GAME_OVERS.length)], JOptionPane.ERROR_MESSAGE);
        
    }
    
    public static void main(String[] args) {
        /* Main */

        Menu main = new Menu();
        main.theStory = new Story(); // Making it create a "new story" has so much aura
        main.writeText("The Silver Slayer [Beta v1.0]\n\nWelcome\nYou are at the Gate.\nBegin by typing 'enter'", 0);

    }

    private void init() {
        /*
         * Sets up the game & UI
         */

        // Prep playable characters
        for (int c = 0; c < SelectedPlayer.values().length; c++) players[c] = new Player(this, SelectedPlayer.values()[c]);

        // UI Base
        mainframe.setLocationRelativeTo(null);
        mainframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        panel = new JPanel();

        // Terminal
        terminal = new JTextArea("", 1, 30);
        terminal.setLineWrap(true);
        terminal.setWrapStyleWord(true);
        terminal.setEditable(false);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setFont(gameFont);
        scrollPane = new JScrollPane(terminal);

        // Right (player's) sidebar
        playerBar = new JTextArea("", 1, 10);
        playerBar.setFont(gameFont);
        playerBar.setEditable(false); // make player stats not editable
        playerBar.setBackground(Color.BLACK);
        playerBar.setForeground(Color.GREEN);

        // Left (enemy's) sidebar
        enemyBar = new JTextArea("ENEMY", 1, 10);
        enemyBar.setFont(gameFont);
        enemyBar.setEditable(false);
        enemyBar.setBackground(Color.BLACK);
        enemyBar.setForeground(Color.GREEN);

        // Input
        inputField = new JTextField();
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.GREEN);
        inputField.setFont(gameFont);
        inputField.setBorder(BorderFactory.createEmptyBorder()); // removes border from input field
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.addActionListener((ActionEvent e) -> {

            if (!timer.isRunning()) {

                terminal.append(inputField.getText() + "\n\n");
                readInput(inputField.getText());
                inputField.setText(null);

            }

        });

        // Layout
        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(playerBar, BorderLayout.EAST);
        panel.add(enemyBar, BorderLayout.WEST);

        // Final
        mainframe.add(panel);
        mainframe.setTitle(TITLE_STRINGS[r.nextInt(TITLE_STRINGS.length)]);
        playerSelect();

    }

    private void playerSelect() {

        mainframe.setVisible(false);
        playerScreen = new JFrame();
        playerScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerScreen.setLayout(new FlowLayout());
        playerScreen.setSize(300, 300);
        playerScreen.setLocationRelativeTo(null);
        for (int c = 0; c < SelectedPlayer.values().length; c++) if (players[c].health > 0) playerScreen.add(characterButtons[c]);
        playerScreen.setVisible(true);

    }

}