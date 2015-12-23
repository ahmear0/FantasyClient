import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import java.util.ArrayList;

public class PlayerPanel extends JPanel {

    private ArrayList<Player> playerList;

    public PlayerPanel(String _clientUser, ArrayList<Player> _playerList) {

        setLayout(new FlowLayout());

        playerList = _playerList;

        for (Player current : playerList)
        {
            add(makePlayerButton(current.getFullName() + " " + current.getPosition()));
        }


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    } 

    private JButton makePlayerButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        return button;
    }
}