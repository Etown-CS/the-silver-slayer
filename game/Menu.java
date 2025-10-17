import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Menu {

    // Core
    private JFrame frame;
    private JPanel panel;
    private JTextArea terminal;
    private JTextArea playerBar;
    private JTextArea enemyBar;
    private JScrollPane scrollPane;
    private JTextField inputField;

    // Text
    private Font gameFont;
    private Timer timer;
    private int characterIndex;
    private boolean wait;

    // Elements
    private Random r;
    private Story theStory;
    private Player[] characters;
    public Player playerRef;
    public Enemy enemyRef;
    public Locations locs;
    public boolean gameOver;

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
                                            "Low on magenta!", "Strings 🙏", "WORK is a dish best served NO", "jk jk............ unless?"};
    private final String[] FLEE_STRINGS = {"You can't run forever.", "You got away... for now."};
    private final String[] GAME_OVERS = {"How unfortunate", "That's gonna leave a mark", "Better luck some time!", "oof", "bruh.mp3",
                                            "Process killed"};

    public Menu() {
        /* Constructor */

        r = new Random();
        gameFont = new Font("Cascadia Mono", Font.PLAIN, 20);
        gameOver = false;
        wait = true;

        // Frame itself
        frame = new JFrame(TITLE_STRINGS[0]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display
        panel = new JPanel();
        init(this);

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

        enemyBar.setText("ENEMY\n\n" + name + "\n\nHealth: " + H + "\nAttack: " + A + "\nDefense: " + D);

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

            case "characters":
            case "character":

                if (bits.length < 2) {

                    String chars = "Characters:\n\n";
                    int alive = 0;

                    for (int c = 0; c < SelectedPlayer.values().length; c++) {

                        chars += c + ": ";

                        if (characters[c].health != 0) {

                            chars += characters[c].name + "\n";
                            alive++;

                        } else chars += "...\n";

                    }

                    writeText(chars + "\n" + alive + " characters available.", 0);
                    
                } else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot >= SelectedPlayer.values().length) writeText(slot + " is not valid.", 0);
                        else if (characters[slot].health == 0) writeText(characters[slot].name + " is not available.", 0);
                        else {playerRef = characters[slot];

                            playerRef.changeStats(0, 0, 0);
                            writeText("Character set to " + playerRef.name + "!", 0);
                            
                        }

                    } catch (NumberFormatException ex) {

                        writeText(bits[1] + " is not a valid character.", 0);

                    }

                }

                break;

            case "help":

                writeText("GENERAL\nclear: Clear screen\nexit / quit: Quit the game.\nsettings: Modify game settings\ntitle [int]: Display a random title or specifiy\n\nINVENTORY\ndesc / describe: Show an inventory item's description\ndrop (int): Drop an item\ninv / inventory / ls: Display inventory\nuse (int): Use an inventory item\n\nCOMBAT\natk / attack: Attack the current enemy\nflee: Run away", 0);
                break;

            case "quit":
            case "exit":

                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                break;

            case "settings":

                writeText("TODO: Options", 0);
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

            case "clear":

                terminal.setText(null);
                writeText("The Silver Slayer [Beta v1.0]", -1);
                break;

            case "title":

                if (bits.length < 2) {

                    frame.setTitle(TITLE_STRINGS[r.nextInt(TITLE_STRINGS.length)]);
                    writeText("Rerolled title!", 0);

                } else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot > TITLE_STRINGS.length - 1) writeText(slot + " is out of range.", 0);
                        else {

                            frame.setTitle(TITLE_STRINGS[slot]);
                            writeText("Updated title!", 0);

                        }

                    } catch (NumberFormatException ex) {

                        writeText('"' + bits[1] + "\" is not a valid title ID.", 0);

                    }

                }

                break;

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
                    else terminate();
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
        frame.setTitle("Game Over");
        JOptionPane.showMessageDialog(panel, "You have been terminated.", GAME_OVERS[r.nextInt(GAME_OVERS.length)], JOptionPane.ERROR_MESSAGE);
        
    }
    
    public static void main(String[] args) {
        /* Main */

        Menu main = new Menu();

        main.writeText("The Silver Slayer [Beta v1.0]\n\nWelcome " + main.playerRef.name + ".\nYou are at the Gate.\nBegin by typing 'enter'", 0);
        main.playerRef.changeStats(0, 0, 0); // to get sidebar to update for the first time
        main.theStory = new Story(); // Making it create a "new story" has so much aura

    }

    private void init(Menu self) {
        /*
         * Sets up the game & UI
         */

        // Prep playable characters
        characters = new Player[SelectedPlayer.values().length];
        for (int c = 0; c < SelectedPlayer.values().length; c++) characters[c] = new Player(this, SelectedPlayer.values()[c]);

        // Character select buttons
        JButton javaButton = new JButton("Bitter Java");
        javaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = characters[0];
                wait = false;

            }
            
        });

        JButton rustButton = new JButton("Brustel Sprout");
        rustButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = characters[1];
                wait = false;

            }
            
        });
        
        JButton cButton = new JButton("C--");
        cButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = characters[2];
                wait = false;

            }
            
        });
        
        JButton pythonButton = new JButton("Dapper Python");
        pythonButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                playerRef = characters[3];
                wait = false;

            }
            
        });

        JButton phpButton = new JButton("P. H. Periwinkle");
        phpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = characters[1];playerRef = characters[4];
                wait = false;

            }
            
        });

        JButton reactButton = new JButton("ReacTor");
        reactButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = characters[5];
                wait = false;

            }
            
        });

        JButton sqlButton = new JButton("Saea Quowle");
        sqlButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef = characters[6];
                wait = false;

            }
            
        });

        panel.add(javaButton);
        panel.add(rustButton);
        panel.add(cButton);
        panel.add(pythonButton);
        panel.add(phpButton);
        panel.add(reactButton);
        panel.add(sqlButton);

        frame.setSize(300, 300);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while (wait) {System.out.print("");} // TODO: Find line that does less but still works

        // Reset frame
        frame.setVisible(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Clear buttons
        panel.remove(javaButton);
        panel.remove(rustButton);
        panel.remove(cButton);
        panel.remove(pythonButton);
        panel.remove(phpButton);
        panel.remove(reactButton);
        panel.remove(sqlButton);
        panel.setLayout(new BorderLayout());

        // Terminal
        terminal = new JTextArea("", 1, 30);
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
        panel.add(inputField, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(playerBar, BorderLayout.EAST);
        panel.add(enemyBar, BorderLayout.WEST);

        // Final
        frame.setTitle(TITLE_STRINGS[r.nextInt(TITLE_STRINGS.length)]);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Automatically clicks on input field
        inputField.requestFocusInWindow();

    }

}