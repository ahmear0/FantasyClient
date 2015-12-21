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
    private JTable roster;
    private JScrollPane rosterContainer;

    public RosterPanel(databaseAPI _database, String _clientUser) {

        database = _database;
        clientUser = _clientUser;

        tabulate();
    }

    private void tabulate()
    {
        String [] rosterColumns = new String[5];
        rosterData = database.getCurrentRoster(clientUser);

        rosterColumns[0]="Player ID";
        rosterColumns[1]="Start/Bench";
        rosterColumns[2]="Position";
        rosterColumns[3]="First";
        rosterColumns[4]="Last";

        roster = new JTable(new DefaultTableModel(rosterData,rosterColumns));
        roster.setRowHeight(25);

        rosterContainer = new JScrollPane(roster);
        add(rosterContainer, BorderLayout.CENTER);
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    public void paintComponent(Graphics g) {
    	refresh();
        super.paintComponent(g);
        System.out.println("Entered RosterPanel Paint Component Method");
        } 

    public void refresh()
    {  
        System.out.println("RosterPanel entered the Refresh Method");
        rosterData = database.getCurrentRoster(clientUser);
        int numRostered = rosterData.length;
        for (int i = 0; i<numRostered; i++)
        {
            System.out.print(rosterData[i][0] + " ");
        }
        System.out.println();
        tabulate();
        //this.repaint();
    }
}