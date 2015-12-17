import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

public class FantasyClient extends JFrame implements ActionListener {

    public final static String clientUser = "Asheq";
    private static Object [][] playerData;
    private static JButton refreshButton;
    private static databaseAPI database;
    private static RosterPanel rosterTab;
    private static JPanel matchupTab, playerTab;

    public FantasyClient(String title) {

    super(title);
        // set flow layout for the frame
    this.getContentPane().setLayout(new FlowLayout());

    JButton button1 = new JButton("Refresh");

    //set action listeners for buttons
    button1.addActionListener(this);

    //add buttons to the frame
    add(button1);

    }

    //protected void initUI() {
    public static void main(String args[]){

        database = new databaseAPI();

        final JFrame frame = new FantasyClient("Fantasy Football Client, Fall 2015");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rosterTab = new RosterPanel(database, clientUser);
        matchupTab = new JPanel();
        playerTab = new JPanel();
        JTabbedPane tabPanel = new JTabbedPane();

        //playerTAB
        String [] playerColumns = new String[4];
        playerColumns[0]="Position";
        playerColumns[1]="Team";
        playerColumns[2]="First";
        playerColumns[3]="Last";

        playerData = database.getPlayers();
        JTable playerTable = new JTable(new DefaultTableModel(playerData,playerColumns));
        JScrollPane playerContainer = new JScrollPane(playerTable);
        playerTab.add(playerContainer, BorderLayout.CENTER);

        tabPanel.addTab("Roster",rosterTab);
        tabPanel.addTab("Matchup",matchupTab);
        tabPanel.addTab("Players",playerTab);

        tabPanel.setVisible(true);
        // rosterContainer.setVisible(true);
        // playerContainer.setVisible(true);

        frame.getContentPane().add(tabPanel);
        frame.pack(); 
        frame.setBackground(Color.white);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        String action = ae.getActionCommand();
        if (action.equals("Refresh")) {
            rosterTab.refresh();
            System.out.println("action has been performed");
            rosterTab.revalidate();
        }
    }
}