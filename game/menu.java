import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu {
    private final String textToType;
    private final Timer timer;
    private int characterIndex = 0;
    private final JTextArea terminal;




    public menu(JTextArea terminal, String text) {
        this.textToType = text;


        this.timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (characterIndex < textToType.length()) {
                    terminal.append(String.valueOf(textToType.charAt(characterIndex)));
                    characterIndex++;
                } else {
                    timer.stop();
                }
            }
        });
        this.terminal = new JTextArea();
    };
    public void start() {
        timer.start();
    }
    
    public static void main(String[] args) {
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
            String command = inputField.getText();
            terminal.append("> " + command + "\n\n");
            inputField.setText("");
        });

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
