import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import javax.swing.JOptionPane;

public class FantasyClient extends JFrame implements ActionListener {

    public final static String clientUser = "Asheq";
    private int currentWeek = 14;
    private boolean isAdmin = true;
    private static Object [][] playerData;
    private static JButton refreshButton, scrapeButton, refresh;
    private static databaseAPI database;
    private static RosterPanel rosterTab;
    private static MenuPanel menuPanel;
    private static JPanel matchupTab, playerTab, standingsTab;

    private FantasyClient(String title) {

        super(title);
        this.getContentPane().setLayout(new BorderLayout());
        refreshButton = makeMenuButton("Refresh");
        scrapeButton = makeMenuButton("Scrape");
    }

    //protected void initUI() {
    public static void main(String args[]){

        database = new databaseAPI();

        FantasyClient frame = new FantasyClient("Fantasy Football Client, Fall 2015");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuPanel = new MenuPanel(clientUser);
        menuPanel.add(refreshButton);
        menuPanel.add(scrapeButton);

        rosterTab = new RosterPanel(database, clientUser);
        matchupTab = new JPanel();
        playerTab = new JPanel();
        standingsTab = new JPanel();
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
        tabPanel.addTab("Standings",standingsTab);

        tabPanel.setVisible(true);
        // rosterContainer.setVisible(true);
        // playerContainer.setVisible(true);

        frame.getContentPane().add(menuPanel, BorderLayout.NORTH);
        frame.getContentPane().add(tabPanel, BorderLayout.CENTER);
        frame.pack(); 
        frame.setBackground(Color.white);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton makeMenuButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        return button;
    }


    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        String action = ae.getActionCommand();
        if (action.equals("Refresh")) 
        {
        	refreshButton.setBackground((Color.BLUE));
            rosterTab.refresh();
            validate();
        }
        else if (action.equals("Scrape"))
        {
        	int week = Integer.parseInt(JOptionPane.showInputDialog("Enter Week:"));
        	setVisible(false);
        	scrapeAPI.scrapeTheWeek(week);
        	setVisible(true);
        }
    }

    public void mouseEntered(MouseEvent e) 
    {
        refreshButton.setBackground(Color.BLUE);
    }
    
    public void mouseExited(MouseEvent e) 
    {
        refreshButton.setBackground(Color.WHITE);    
    }   
    public void mouseClicked(MouseEvent e) 
    {

    }
    public void mousePressed(MouseEvent e) 
    {
        refreshButton.setBackground(Color.BLACK);
    }  
    public void mouseReleased(MouseEvent e)
    {}

}