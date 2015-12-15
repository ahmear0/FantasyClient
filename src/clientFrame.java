import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


class clientFrame extends JFrame
{
  JLabel banner;
  JButton team;
  JMenu menu;
  JTextField username;
  JPasswordField password;
  teamPanel myTeam;
  JPanel uiPanel;

  // constructor
  public clientFrame(String title)
  {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    uiPanel = new JPanel();
    username = new JTextField("user",10);
    uiPanel.add(username);
    password = new JPasswordField(10);
    password.setEchoChar('*');
    uiPanel.add(password);


    getContentPane().add(uiPanel);
    getContentPane().add(myTeam);

  }
}