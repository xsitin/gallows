import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

enum Result {Win, Lose, Guessed, NotGuessed}

public class Main {
    public static void main(String[] args) {
        String word = JOptionPane.showInputDialog("Input word");
        String lives = JOptionPane.showInputDialog("Input lives count");
        Game game = new Game(word, Integer.parseInt(lives));
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 700, 700);
        window.setVisible(true);

        JPanel panel = new JPanel();

        JLabel livesLabel = new JLabel();
        livesLabel.setText(String.valueOf(game.GetLives()));

        JLabel remainderLabel = new JLabel();
        remainderLabel.setText(game.GetRemainder());

        window.addKeyListener(new GallowKeyListener(game, livesLabel, remainderLabel));

        panel.add(livesLabel);
        panel.add(remainderLabel);
        window.add(panel);
    }
}


class GallowKeyListener implements KeyListener {
    Game game;
    JLabel lives;
    JLabel remainder;

    public GallowKeyListener(Game game, JLabel lives, JLabel remainder) {
        this.game = game;
        this.lives = lives;
        this.remainder = remainder;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Result res = game.TryGuess(e.getKeyChar());
        lives.setText(String.valueOf(game.GetLives()));
        remainder.setText(game.GetRemainder());
        if (res == Result.Win) {
            JOptionPane.showMessageDialog(null, "You win");
            System.exit(0);
        }
        if (res == Result.Lose) {
            JOptionPane.showMessageDialog(null, "You lose");
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

class Game {
    String Word;
    String Remainder;
    int Lives;

    public Game(String word, int lives) {
        Word = word;
        StringBuilder bulder = new StringBuilder();
        bulder.append(word.charAt(0));
        for (char c : word.substring(1, word.length() - 1).toCharArray())
            bulder.append('_');
        bulder.append(word.charAt(word.length() - 1));
        Remainder = bulder.toString();
        Lives = lives;
    }

    public Result TryGuess(char letter) {
        char[] remainder = Remainder.toCharArray();
        String wordGuessed = Remainder;
        for (int i = 0; i < Word.length(); i++)
            if (letter == Word.charAt(i)) {
                remainder[i] = letter;
                wordGuessed = String.valueOf(remainder);
            }
        if (wordGuessed.equals(Remainder)) {
            Lives--;
            return Lives > 0 ? Result.NotGuessed : Result.Lose;
        }
        Remainder = wordGuessed;
        return wordGuessed.equals(Word) ? Result.Win : Result.Guessed;
    }

    public String GetRemainder() {
        return Remainder;
    }

    public String GetWord() {
        return Word;
    }

    public int GetLives() {
        return Lives;
    }

}
