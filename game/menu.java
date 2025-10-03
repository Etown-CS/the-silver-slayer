import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class menu {

    // Core
    JFrame frame;
    private JTextArea terminal;
    private JScrollPane scrollPane;
    private Timer timer;
    private int characterIndex;

    // Options
    private int frameWidth = 1000, frameHeight = 1000;
    private int textDelay = 10;

    public menu() {

        // Frame itself
        frame = new JFrame("Silver Slayer RPG");
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

                writeText(inputField.getText().toLowerCase());
                inputField.setText("");

            }

        });

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    private void writeText(final String text) {

        switch (text) {

            case "help":

                writeText("TODO: Help text\n\"exit\" - Quit the game.");
                return;

            case "exit":

                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

        }
        
        characterIndex = 0;
        this.timer = new Timer(textDelay, new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) {
                    
                    terminal.append(String.valueOf(text.charAt(characterIndex)));
                    characterIndex++;

                } else {

                    terminal.append("\n>");
                    timer.stop();

                }

            }

        });

        timer.start();

    }
    
    public static void main(String[] args) {

        menu main = new menu();
        main.writeText("Welcome to The Silver Slayer text-based RPG\n\n" +
                         "You are at the Gate.\n\n" +
                         "Begin by typing 'enter'\n\n");

    }

}