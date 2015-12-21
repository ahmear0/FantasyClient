import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import javax.swing.JOptionPane;
import java.lang.NumberFormatException;

public class FantasyClient extends JFrame {
	
	private static final long serialVersionUID = 8008367570604652741L;
	private static String clientUser;
    private static boolean isAdmin = true;
    private static String adminString = "";
    private static Object [][] playerData, standingsData;
    private static JButton refreshButton, scrapeButton, clientUserButton, changeUserButton;
    private static databaseAPI database;
    private static RosterPanel rosterTab;
    private static MenuPanel menuPanel;
    private static JPanel matchupTab, playerTab, standingsTab;
    private JFrame currentFrame;

    private FantasyClient(String title) {

        super(title);
        clientUser = JOptionPane.showInputDialog("Enter Client UserName:");
        currentFrame = this;
        this.getContentPane().setLayout(new BorderLayout());
    
        refreshButton = makeMenuButton("Refresh");
        refreshButton.addMouseListener(new MouseAdapter() { 
            public void mouseEntered(MouseEvent me)
            {
                refreshButton.setBackground(new Color(200,200,200));
            }
            public void mouseExited(MouseEvent me)
            {
                refreshButton.setBackground(Color.WHITE);
            }
            public void mousePressed(MouseEvent me) 
            { 
                refreshButton.setBackground(new Color(180,180,180));
            } 
            public void mouseReleased(MouseEvent me)
            {
                refreshButton.setBackground(new Color(200,200,200));
            }
            public void mouseClicked(MouseEvent me)
            {
                refreshButton.setBackground(new Color(200,200,200));
                rosterTab.refresh();
                validate();
            }
        }); 

        if (isAdmin)
        {
            adminString = " [ADMIN]";

            scrapeButton = makeMenuButton("Scrape");
            scrapeButton.addMouseListener(new MouseAdapter() { 
                public void mouseEntered(MouseEvent me)
                {
                    scrapeButton.setBackground(new Color(200,200,200));
                }
                public void mouseExited(MouseEvent me)
                {
                    scrapeButton.setBackground(Color.WHITE);
                }
                public void mousePressed(MouseEvent me) 
                { 
                    scrapeButton.setBackground(new Color(180,180,180));
                } 
                public void mouseReleased(MouseEvent me)
                {
                    scrapeButton.setBackground(new Color(200,200,200));
                }
                public void mouseClicked(MouseEvent me) 
                { 
                    scrapeButton.setBackground(new Color(200,200,200));
                    int week = 0;
                    try
                    {
                        week = Integer.parseInt(JOptionPane.showInputDialog("Enter Week:"));
                        if (week>=1 && week<=17)
                        {
                            setVisible(false);
                            scrapeAPI.scrapeTheWeek(week);
                            setVisible(true);
                        }
                        else
                        {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException exception)
                    {
                        JOptionPane.showMessageDialog(currentFrame, "Invalid Week.  Please use a valid integer, 1-17.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    }
                } 
            }); 
        
            changeUserButton = makeMenuButton("Change User");
            changeUserButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me)
                {
                    changeUserButton.setBackground(new Color(200,200,200));
                }
                public void mouseExited(MouseEvent me)
                {
                    changeUserButton.setBackground(Color.WHITE);
                }
                public void mousePressed(MouseEvent me) 
                { 
                    changeUserButton.setBackground(new Color(180,180,180));
                } 
                public void mouseReleased(MouseEvent me)
                {
                    changeUserButton.setBackground(new Color(200,200,200));
                }
                public void mouseClicked(MouseEvent me) 
                {
                    changeUserButton.setBackground(new Color(200,200,200));
                    clientUser = JOptionPane.showInputDialog("Enter Client UserName:");
                    validate();
                }
            });
        }

        clientUserButton = makeMenuButton("User: " + clientUser + adminString);
    }

    public static void main(String args[]) {

        database = new databaseAPI();

        FantasyClient frame = new FantasyClient("Fantasy Football Client, Fall 2015");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuPanel = new MenuPanel(clientUser);
        

        FlowLayout menuLayout = new FlowLayout();
        menuLayout.setAlignment(FlowLayout.LEFT);
        menuPanel.setLayout(menuLayout);

        menuPanel.add(clientUserButton);
        menuPanel.add(refreshButton);
        if (isAdmin)
        {
            menuPanel.add(changeUserButton);
            menuPanel.add(scrapeButton);
        }

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


        //standingsTab
        String [] standingsColumns = new String[3];
        standingsColumns[0] = "Fantasy Team";
        standingsColumns[1] = "Win";
        standingsColumns[2] = "Loss";

        standingsData = database.getStandings();
        JTable standingsTable = new JTable(new DefaultTableModel(standingsData,standingsColumns));
        JScrollPane standingsContainer = new JScrollPane(standingsTable);
        standingsTab.add(standingsContainer, BorderLayout.CENTER);

        tabPanel.addTab("Roster",rosterTab);
        tabPanel.addTab("Matchup",matchupTab);
        tabPanel.addTab("Players",playerTab);
        tabPanel.addTab("Standings",standingsTab);
        tabPanel.setVisible(true);

        frame.getContentPane().add(menuPanel, BorderLayout.NORTH);
        frame.getContentPane().add(tabPanel, BorderLayout.CENTER);
        frame.pack(); 
        frame.setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton makeMenuButton(String text) {
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