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
    JFrame frame;
    private JTextArea terminal;
    private JScrollPane scrollPane;
    private Timer timer;
    private int characterIndex;

    Random r;

    Audio audio;

    Player playerRef;

    // Date and Time
    LocalDate dateObj = LocalDate.now();
    LocalTime timeObj = LocalTime.now();

    DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("HH:mm:ss 'on' E, MMM dd yyyy");


    // Options
    private int frameWidth = 1000, frameHeight = 1000;
    private int textDelay = 15;

    // Storage
    private final String[] TITLE_STRINGS = {"Silver Slayer RPG", "Also try Terraria!", "Also try Minecraft!", "THE FOG IS COMING", "There may be an egg", "It's " + LocalDateTime.now().format(dateTime) + " right now", "here come dat boi"};
    private final String INTRO_TEXT = "The Silver Slayer [Version 1.0]\n\nYou are at the Gate.\nBegin by typing 'enter'\n";

    public Menu() {
        /* Constructor */

        r = new Random();
        audio = new Audio();

        // Frame itself
        frame = new JFrame(TITLE_STRINGS[getRandomInt(TITLE_STRINGS.length)]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);

        playerRef = new Player(this);



        // Display
        terminal = new JTextArea();
        terminal.setEditable(false);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setFont(new Font("Cascadia Mono", Font.PLAIN, 20));

        scrollPane = new JScrollPane(terminal);

        // Input
        JTextField inputField = new JTextField();

        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.GREEN);
        inputField.setFont(new Font("Cascadia Mono", Font.PLAIN, 20));
        inputField.setBorder(BorderFactory.createEmptyBorder()); // removes border from input field

        inputField.addActionListener((ActionEvent e) -> {

            if (!timer.isRunning()) {

                readInput(inputField.getText());
                inputField.setText("");

            }

        });

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Automatically clicks on input field
        inputField.requestFocusInWindow();

    }

    private void readInput(String text) {
        /*
         * Called whenever the player enters text
         * 
         * text: The String that was submitted
         */

        text = text.strip();
        switch (text.toLowerCase()) {

            case "help":

                writeText("TODO: Help text\n\"exit\" - Quit the game.", 0);
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

            default:

                writeText(text, 0);
                break;

        }

    }

    public void writeText(final String text, int voiceID) {
        /*
        * Uses the typewrite effect to print text to the screen
        *
        * text: The text to be written
        * voiceID: ID for the sound to be played (use 0 for default or negative for silent)
        */

        
        characterIndex = 0;
        if (voiceID >= 0) audio.command(1, voiceID);

        this.timer = new Timer(textDelay, new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) terminal.append(String.valueOf(text.charAt(characterIndex++)));
                else {


                    terminal.append("\n/" + playerRef.location + "/" + playerRef.sublocation + " >");
                    if (voiceID >= 0) audio.command(0);
                    timer.stop();

                }

            }

        });

        timer.start();

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