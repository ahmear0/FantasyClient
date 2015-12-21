import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.table.DefaultTableModel;

class RosterPanel extends JPanel {

    private static Object [][] rosterData;
    private static databaseAPI database;
    private String clientUser;
    private String[] rosterColumns;
    private JTable roster;
    private JScrollPane rosterContainer;

    public RosterPanel(databaseAPI _database, String _clientUser) {

        database = _database;
        clientUser = _clientUser;

        rosterColumns = new String[5];
        rosterColumns[0]="Player ID";
        rosterColumns[1]="Start/Bench";
        rosterColumns[2]="Position";
        rosterColumns[3]="First";
        rosterColumns[4]="Last";

        rosterData = database.getCurrentRoster(clientUser);

        roster = new JTable(new DefaultTableModel(rosterData,rosterColumns));
        roster.setRowHeight(25);

        rosterContainer = new JScrollPane(roster);
        add(rosterContainer, BorderLayout.CENTER);
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    public void refresh()
    {  
        rosterData = database.getCurrentRoster(clientUser);

        roster = new JTable(new DefaultTableModel(rosterData,rosterColumns));
        roster.setRowHeight(25);

        rosterContainer = new JScrollPane(roster);
        this.removeAll();
        add(rosterContainer, BorderLayout.CENTER);
        this.updateUI();
    }
}