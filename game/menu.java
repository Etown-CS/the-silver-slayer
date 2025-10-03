import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

public class menu {

    // Core
    JFrame frame;
    private JTextArea terminal;
    private JScrollPane scrollPane;
    private Timer timer;
    private int characterIndex;

    Random r;

    Audio audio;

    // Options
    private int frameWidth = 1000, frameHeight = 1000;
    private int textDelay = 10;

    // Storage
    private final String[] titleStrings = {"Silver Slayer RPG", "Also try Terraria!", "Also try Minecraft!", "THE FOG IS COMING", "There may be an egg"};
    private final String introText = "Welcome to The Silver Slayer text-based RPG!\n\nYou are at the Gate.\n\nBegin by typing 'enter'\n\n";

    public menu() {
        // Constructor

        r = new Random();
        audio = new Audio();

        // Frame itself
        frame = new JFrame(titleStrings[r.nextInt(titleStrings.length)]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);

        // Display
        terminal = new JTextArea();
        terminal.setEditable(false);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setFont(new Font("Cascadia Mono", Font.PLAIN, 20));

        scrollPane = new JScrollPane(terminal);

        // Input
        JTextField inputField = new JTextField();
        inputField.addActionListener((ActionEvent e) -> {

            if (!timer.isRunning()) {

                writeText(inputField.getText().toLowerCase(), 0);
                inputField.setText("");

            }

        });

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    private void writeText(final String text, int voiceID) {
        /*
        * Uses the typewrite effect to print text to the screen
        * text: The text to be written
        * voiceID: ID for the sound to be played (use 0 for default or negative for silent)
        */

        // Special Commands
        switch (text) {

            case "help":

                writeText("TODO: Help text\n\"exit\" - Quit the game.", voiceID);
                return;

            case "exit":

                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                return;

        }
        
        characterIndex = 0;
        if (voiceID >= 0) audio.command(1, voiceID);

        this.timer = new Timer(textDelay, new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) {
                    
                    terminal.append(String.valueOf(text.charAt(characterIndex)));
                    characterIndex++;

                } else {

                    terminal.append("\n>");
                    if (voiceID >= 0) audio.command(0);
                    timer.stop();

                }

            }

        });

        timer.start();

    }
    
    public static void main(String[] args) {
        // Main

        menu main = new menu();
        main.writeText(main.introText, 0);

    }

}