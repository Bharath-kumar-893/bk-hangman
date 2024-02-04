import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class hangman_ extends JFrame {
    private Map<String, String> Hints;//map for hints
    private String selected;//selected word
    private StringBuilder gw;//guessing word
    private int max;//maximum attempts
    private int remainingAttempts;//attempts left
    private StringBuilder gl;//guessing letter
    
    //Ui components
    private JLabel wordLabel;
    private JLabel guessedLettersLabel;
    private JLabel hintLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JTextArea rulesTextArea;

    public hangman_(Map<String, String> wordHints) {
        this.Hints = wordHints;
        selectRandomWord();
        gw = new StringBuilder(repeat("_", selected.length()));
        max = 6;
        remainingAttempts = max;
        gl = new StringBuilder();

        frontend();
    }

    private void frontend() {
        // Title
        JLabel titleLabel = new JLabel("HANG MAN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Game components
        wordLabel = new JLabel("Word: " + gw.toString());
        guessedLettersLabel = new JLabel("Guessed Letters: " + gl.toString());
        hintLabel = new JLabel("Hint: " + Hints.get(selected));

        guessTextField = new JTextField(10);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(e -> handleGuess());

        rulesTextArea = new JTextArea("Rules of Hangman:\n" +
                "1. Try to guess the word by entering one letter at a time.\n" +
                "2. You have a maximum of " + max + " attempts.\n" +
                "3. If you guess a correct letter, it will be revealed in the word.\n" +
                "4. If you guess an incorrect letter, you lose an attempt.\n" +
                "5. The game ends when you correctly guess the word or run out of attempts.");

        rulesTextArea.setEditable(false);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setWrapStyleWord(true);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Game components
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(wordLabel, gbc);
        gbc.gridy = 2;
        add(guessedLettersLabel, gbc);
        gbc.gridy = 3;
        add(hintLabel, gbc);
        gbc.gridy = 4;
        add(new JLabel("Enter a letter: "), gbc);
        gbc.gridy = 5;
        add(guessTextField, gbc);
        gbc.gridy = 6;
        add(guessButton, gbc);

        // Rules
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(rulesTextArea), gbc);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hangman Game");
        setVisible(true);
    }

    private void handleGuess() {
        char guess = guessTextField.getText().toLowerCase().charAt(0);

        if (gl.toString().indexOf(guess) != -1) {
            JOptionPane.showMessageDialog(this, "You already guessed that letter. Try again.");
        } else {
            gl.append(guess);

            if (!checkGuess(guess)) {
                --remainingAttempts;
            }

            if (gw.indexOf("_") == -1 || remainingAttempts == 0) {
                displayEndGameMessage();
            }

            updateLabels();
        }

        guessTextField.setText("");
        guessTextField.requestFocus();
    }

    private void displayEndGameMessage() {
        String message;
        if (remainingAttempts == 0) {
            message = "Sorry, you ran out of attempts. The correct word was: " + selected;
        } else {
            message = "Congratulations! You guessed the word: " + selected;
        }

        JOptionPane.showMessageDialog(this, message);
        System.exit(0);
    }

    private void updateLabels() {
        wordLabel.setText("Word: " + gw.toString());
        guessedLettersLabel.setText("Guessed Letters: " + gl.toString());
    }

    private boolean checkGuess(char guess) {
        boolean found = false;

        for (int i = 0; i < selected.length(); i++) {
            if (selected.charAt(i) == guess) {
                gw.setCharAt(i, guess);
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Incorrect guess. Remaining attempts: " + remainingAttempts);
        }

        return found;
    }

    private void selectRandomWord() {
        int randomIndex = (int) (Math.random() * Hints.size());
        selected = Hints.keySet().toArray(new String[0])[randomIndex].toLowerCase();
    }

    private String repeat(String str, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(str);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        final Map<String, String> Hints = new HashMap<>();
        Hints.put("programming", "This is the process of creating a set of instructions that tell a computer how to perform a task.");
        Hints.put("java", "A high-level, class-based, object-oriented programming language.");
        Hints.put("hangman", "A word-guessing game where players attempt to build a missing word by guessing one letter at a time.");
        Hints.put("developer", "A person who writes computer programs or develops software.");
        Hints.put("computer", "An electronic device for storing and processing data.");
        Hints.put("elephant", "A large herbivorous mammal with a long trunk and tusks.");
        Hints.put("tiger", "A large cat with a distinctive orange coat with black stripes.");
        Hints.put("lion", "A large wild cat with a magnificent mane.");
        Hints.put("shahrukhkhan", "A renowned Indian actor, often referred to as the 'King of Bollywood'.");
        Hints.put("aamirkhan", "An Indian actor, director, and filmmaker.");
        Hints.put("gandhiji", "Leader of the Indian independence movement against British rule.");
        Hints.put("newton", "A physicist who formulated the laws of motion and universal gravitation.");
        Hints.put("inception", "A mind-bending movie directed by Christopher Nolan.");
        Hints.put("cricket", "A popular sport played with a bat and ball between two teams.");

        SwingUtilities.invokeLater(() -> {
            hangman_ hangmanGame = new hangman_(Hints);
        });
    }
}
