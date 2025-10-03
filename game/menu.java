import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu {

    private final JTextArea terminal = new JTextArea();
    private Timer timer;

    private int characterIndex;
    private int textDelay;

    public menu() {

        textDelay = 50;

    }

    private void writeText(JTextArea terminal, String text) {
        
        characterIndex = 0;
        this.timer = new Timer(textDelay, new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                if (characterIndex < text.length()) {
                    
                    terminal.append(String.valueOf(text.charAt(characterIndex)));
                    characterIndex++;

                } else timer.stop();

            }

        });

        timer.start();

    }
    
    public static void main(String[] args) {

        menu main = new menu();

        JFrame frame = new JFrame("Silver Slayer RPG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

        // Display
        JTextArea terminal = new JTextArea();
        terminal.setEditable(false);
        terminal.setText("Welcome to the Silver Slayer text-based RPG\n\n" +
                         "You are at the Gate.\n\n" +
                         "Begin by typing 'enter'\n\n");
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setFont(new Font("Cascadia Mono", Font.PLAIN, 20));
        
        JScrollPane scrollPane = new JScrollPane(terminal);

        // Input
        JTextField inputField = new JTextField();
        inputField.addActionListener((ActionEvent e) -> {

            main.writeText(terminal, inputField.getText());
            inputField.setText("");

        });

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

}