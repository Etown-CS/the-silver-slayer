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
    public Player playerRef;
    public Enemy enemyRef;
    public boolean gameOver;

    // Sounds
    private Audio[] voices = {new Audio("blip.wav")};
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

            case "character":
                if (bits.length < 2 ) {
                    writeText("Available characters:\n0: Bitter Java\n1: Brustel Sprout\n2: Dapper Python\n3: P.H. Periwinkle\n4: ReacTor\n5: Saea Quowle\n\nType 'character [int]'", 0);
                } else {
                    try {
                        SelectedPlayer selectedCharacter = SelectedPlayer.values()[Integer.parseInt(bits[1])];
                        playerRef = new Player(this, selectedCharacter);
                        playerRef.changeStats(0, 0, 0);
                        writeText("Character set to " + playerRef.name + "!", 0);
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
                    if (enemyRef.defeated) {

                        writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!\n" + enemyRef.name + " has been defeated.", 0);
                        enemyRef = null;
                        enemyBar.setText("ENEMY");
                        // there are 20 spaces in the line below
                    } else writeText("Attacked " + enemyRef.name + " for " + atkdmg + " damage!                    \n\n" + enemyRef.name + " attacks you for " + playerRef.playerAttacked(enemyRef.attack) + " damage!", 0);
                    
                }

                break;

            case "flee":

                if (enemyRef == null) writeText("There's nothing to run from.", 0);
                else {

                    writeText("You fled from " + enemyRef.name + ".\n" + FLEE_STRINGS[getRandomInt(FLEE_STRINGS.length)], 0);
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

                    frame.setTitle(TITLE_STRINGS[getRandomInt(TITLE_STRINGS.length)]);
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

                bgMusic = new Audio("mushroom_music.wav");
                bgMusic.command(1);
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
        JOptionPane.showMessageDialog(panel, "You have been terminated.", GAME_OVERS[getRandomInt(GAME_OVERS.length)], JOptionPane.ERROR_MESSAGE);
        
    }

    public int getRandomInt() {return r.nextInt();}                 // Return a random integer
    public int getRandomInt(int bound) {return r.nextInt(bound);}   // Return a random integer between 0 and bound - 1
    
    public static void main(String[] args) {
        /* Main */

        Menu main = new Menu();

        main.writeText("The Silver Slayer [Beta v1.0]\n\nYou are at the Gate.\nBegin by typing 'enter'", 0);

        main.enemyRef = new Enemy(main, "The Silver Slayer");
        main.enemyRef.changeStats(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        main.playerRef.changeStats(0, 0, 0); // to get sidebar to update for the first time
        main.playerRef.addItem(new Item("Paper Hat", ItemType.Armor, "A carefully folded origami hat.", 1, false));
        main.playerRef.addItem(new Item("Golden Apple", ItemType.Health, "A normal apple, but encased in gold.", -3, true));
        main.playerRef.addItem(new Item("Pebble", ItemType.Junk, "It's a small, white pebble.", 0, false));
        main.playerRef.addItem(new Item("BBQ Bacon Burger", ItemType.Health, "BBQ. Bacon. Burger.", 5, true));
        main.playerRef.addItem(new Item("Comically Large Spoon", ItemType.Weapon, "A spoon of impressive size.", 5, false));

        main.theStory = new Story(); // Making it create a "new story" has so much aura

    }

    private void init() {
        /*
         * Sets up the game & UI
         */

        // Player
        playerRef = new Player(this, SelectedPlayer.Bitter_Java);

        // Character select buttons
        JButton javaButton = new JButton("Bitter Java");
        javaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef.name = "Bitter Java";
                wait = false;

            }
            
        });

        JButton cButton = new JButton("C--");
        cButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef.name = "C--";
                wait = false;

            }
            
        });

        JButton pythonButton = new JButton("Dapper Python");
        pythonButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                playerRef.name = "Dapper Python";
                wait = false;

            }
            
        });

        JButton phpButton = new JButton("P. H. Periwinkle");
        phpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef.name = "P. H. Periwinkle";
                wait = false;

            }
            
        });

        JButton sqlButton = new JButton("Saea Quowle");
        sqlButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                playerRef.name = "Saea Quowle";
                wait = false;

            }
            
        });

        panel.add(javaButton);
        panel.add(cButton);
        panel.add(pythonButton);
        panel.add(phpButton);
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
        panel.remove(cButton);
        panel.remove(pythonButton);
        panel.remove(phpButton);
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