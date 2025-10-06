import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    private Font gameFont;
    private Timer timer = new Timer(0, null);
    private int characterIndex;

    private Random r;
    private Story theStory;
    public Player playerRef;
    public boolean gameOver;

    // Sounds
    private Audio terminalSound = new Audio("game/sound/blip.wav");

    // Date and Time
    private DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("HH:mm:ss 'on' E, MMM dd yyyy");

    // Options
    private int textDelay = 10;

    // Storage
    private final String[] TITLE_STRINGS = {"Silver Slayer RPG", "Also try Terraria!", "Also try Minecraft!", "THE FOG IS COMING", 
                                            "There may be an egg", "It's " + LocalDateTime.now().format(dateTime) + " right now", 
                                            "here come dat boi", "JOHN WAS HERE", "The name is Gus... Amon Gus", "water bottle 😭", 
                                            "Microwave be like 'mmmmmmmmmmmmmmmmmmmmmmmmmmmmm BEEP BEEP BEEP BEEP'", "-inf < x < inf", 
                                            "As I write this, it's 1:30pm on Friday, October 3rd, 2025", "[J]ohn, [A]sher, and [M]artin... JAM", 
                                            "Why am I writing these?", "Silksong is out!", "I ate my toothbrush :(", "o _ o", "get rekt", 
                                            "Low on magenta!", "Strings 🙏", "WORK is a dish best served NO", "jk jk............ unless?"};
    private final String INTRO_TEXT = "The Silver Slayer [Beta v1.0]\n\nYou are at the Gate.\nBegin by typing 'enter'";

    public Menu() {
        /* Constructor */

        r = new Random();
        gameFont = new Font("Cascadia Mono", Font.PLAIN, 20);
        gameOver = false;

        // Frame itself
        frame = new JFrame(TITLE_STRINGS[getRandomInt(TITLE_STRINGS.length)]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Display
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Terminal
        terminal = new JTextArea("", 1, 30);
        terminal.setEditable(false);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setFont(gameFont);

        scrollPane = new JScrollPane(terminal);

        // Right (player's) sidebar
        playerBar = new JTextArea("PLAYER\n\nHealth: ?\nAttack: ?\nDefense: ?", 1, 10);
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

        // Story
        theStory = new Story(); // Making it create a "new story" has so much aura

        // Player
        playerRef = new Player(this);
        playerRef.changeStats(0, 0, 0);
        playerRef.addItem(new Item("Cookie", "You ate the cookie.\n+3 health!", 3, true));
        playerRef.addItem(new Item("Golden Apple", "Eating gold is not good for you.\n-3 health!", -3, true));

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Automatically clicks on input field
        inputField.requestFocusInWindow();

    }

    public void updatePlayerBar(int H, int A, int D) {

        playerBar.setText("PLAYER\n\nHealth: " + H + "\nAttack: " + A + "\nDefense: " + D);

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

            case "help":

                writeText("clear: Clear screen\n\nexit: Quit the game.\nquit: Quit the game\n\ninv: Show inventory\nInventory: Show inventory.\n\nsettings: Modify game settings\n\ntitle [int]: Display a random title or specifiy\n\nuse [int]: Use an inventory item", 0);
                break;

            case "quit":
            case "exit":

                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                break;

            case "settings":

                writeText("TODO: Options", 0);
                break;

            case "inv":
            case "inventory":

                String items = playerRef.listItems();
                if (items.length() > 0) writeText(items, 0);
                else writeText("Your inventory is empty!", 0);
                break;

            case "use":

                if (bits.length < 2) writeText("Specify an inventory slot.", 0);
                else {

                    try {

                        int slot = Integer.parseInt(bits[1]);
                        if (slot < 0 || slot > playerRef.invCap) writeText(slot + " is not a valid slot.", 0);
                        else if (playerRef.inventory[slot] == null) writeText("Slot " + slot + " is empty.", 0);
                        else writeText(playerRef.useItem(slot), 0);

                    } catch (NumberFormatException ex) {

                        writeText('"' + bits[1] + "\" is not a valid inventory slot.", 0);

                    }

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

                writeText(theStory.getEvent(0, 0), 0);
                break;

            default:

                writeText("Unknown command: \"" + text + "\"\nUse 'help' to see valid commands.", 0);
                break;

        }

    }

    public void writeText(final String text, int voiceID) {
        /*
        * Uses the typewriter effect to print text to the screen
        *
        * text: The text to be written
        * voiceID: ID for the sound to be played (use 0 for default or negative for silent)
        */

        if (timer.isRunning()) {

            System.out.println("WARN: Attempt to call writeText whilst timer is still active.");
            return;

        }
        
        if (voiceID >= 0) terminalSound.command(1);
        characterIndex = 0;

        this.timer = new Timer(textDelay, new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) terminal.append(String.valueOf(text.charAt(characterIndex++)));
                else {

                    if (voiceID >= 0) terminalSound.command(0);
                    if (!gameOver) terminal.append("\n\n" + playerRef.location + "/" + playerRef.sublocation + " > ");
                    else terminate();
                    timer.stop();

                }

            }

        });

        timer.start();

    }

    public void terminate() {

        panel.remove(inputField);
        frame.setTitle("Game Over");
        JOptionPane.showMessageDialog(panel, "You have been terminated.", "Game Over", JOptionPane.ERROR_MESSAGE);

    }

    public int getRandomInt() {return getRandomInt();}              // Return a random integer
    public int getRandomInt(int bound) {return r.nextInt(bound);}   // Return a random integer between 0 and bound - 1
    public double getRandomDouble() {return r.nextDouble();}        // Return a random double between 0 and 1
    
    public static void main(String[] args) {
        /* Main */

        Menu main = new Menu();
        main.writeText(main.INTRO_TEXT, 0);

    }

}