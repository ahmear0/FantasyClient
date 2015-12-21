import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;

public class MenuPanel extends JPanel {

    private String clientUser;

    public MenuPanel(String _clientUser) {


        setBackground(new Color(220,240,240));
        setSize(new Dimension(400,70));
        setLayout(new FlowLayout());

        clientUser = _clientUser;
    }

    private static JButton makeMenuButton(String text) {
      JButton button = new JButton(text);
      button.setForeground(Color.BLACK);
      button.setBackground(Color.WHITE);
      Border line = new LineBorder(Color.BLACK);
      Border margin = new EmptyBorder(5, 15, 5, 15);
      Border compound = new CompoundBorder(line, margin);
      button.setBorder(compound);
      button.setRolloverEnabled(true);
      return button;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);     
    } 
}