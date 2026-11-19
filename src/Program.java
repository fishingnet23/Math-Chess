import java.awt.Component;
import java.awt.FlowLayout;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.*;


public class Program
{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Netanel's Chess Game Window");
        
        frame.setLayout(new FlowLayout());

        // wrapper that lets me interact with the reference inside a lambda
        AtomicReference<GamePreset> currentPreset = new AtomicReference<>(new MathPreset());

        Game game = new Game(currentPreset.get());
        
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new BoxLayout(mainMenu,BoxLayout.Y_AXIS));
        JLabel title = new JLabel("<html><h1>Netanel's Barebones Chess</h1></html>");
        JButton restartBtn = new JButton("Restart");
        JButton chessPreset = new JButton("Normal Chess Preset");
        JButton mathPreset = new JButton("Math Chess Preset");

        JLabel info = new JLabel("""
            <html>
            <h2>Info:</h2>
            <p>Standard chess rules apply, <br>however, castling, en passant and promotion are excluded.</p>
            <p><br>When in check, all moves that leave your king in danger are blocked.</p>
            <p><br>The game doesn't warn you about check or checkmate.<br>If you can't move anything, you lose.</p>
            <p><br>The math and chess skins work the same, the pieces behave the same.</p>
            </html>
            """);
        

        restartBtn.addActionListener(e -> game.restart(currentPreset.get()));
        chessPreset.addActionListener(e -> {
            currentPreset.set(new ChessPreset());
            game.restart(currentPreset.get());
        });
        mathPreset.addActionListener(e -> {
            currentPreset.set(new MathPreset());
            game.restart(currentPreset.get());
        });

        mainMenu.add(title);
        mainMenu.add(restartBtn);
        mainMenu.add(chessPreset);
        mainMenu.add(mathPreset);
        mainMenu.add(info);
        
        for(Component child:mainMenu.getComponents())
            if (child instanceof JComponent) {
            ((JComponent) child).setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        frame.add(mainMenu);
        frame.add(game);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        });
    }
}