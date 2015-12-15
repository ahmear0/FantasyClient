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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class FantasyClient {

    JMenuBar fantasyMenu;
    String clientUser;

    //protected void initUI() {
    public static void main(String args[]){


        databaseAPI database = new databaseAPI();

        final JFrame frame = new JFrame("Fantasy Football Client, Fall 2015");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rosterTab = new JPanel();
        JPanel matchupTab = new JPanel();
        JPanel playersTab = new JPanel();
        
        JTabbedPane tabPanel = new JTabbedPane();

        String [] rosterColumns = new String[5];
        Object [][] ronRosterData = database.getCurrentRoster("Ron");
        Object [][] asheqRosterData = database.getCurrentRoster("Asheq");

        rosterColumns[0]="Player ID";
        rosterColumns[1]="Start/Bench";
        rosterColumns[2]="Position";
        rosterColumns[3]="First";
        rosterColumns[4]="Last";

        JTable RonRoster = new JTable(new DefaultTableModel(ronRosterData,rosterColumns));
        JTable AsheqRoster = new JTable(new DefaultTableModel(asheqRosterData,rosterColumns));

        JScrollPane tableContainer = new JScrollPane(AsheqRoster);

        rosterTab.add(tableContainer, BorderLayout.CENTER);
        tabPanel.addTab("Roster",rosterTab);
        tabPanel.addTab("Matchup",matchupTab);
        tabPanel.addTab("playersTab",playersTab);
        tabPanel.setVisible(true);

        AsheqRoster.revalidate();

        tableContainer.setVisible(true);

        frame.getContentPane().add(tabPanel);

        frame.pack(); 
        frame.setBackground(Color.white);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}