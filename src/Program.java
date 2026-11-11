import javax.swing.*;


public class Program
{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Netanel's Chess Game Window");
        Game game = new Game();

        frame.add(game);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        });
    }
}