import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;

public class MatchupPanel extends JPanel implements FantasyPanel {

    private String clientUser;
    private int week;
    private databaseAPI database;
    private Object[][] scheduleData;

    public MatchupPanel(String _clientUser, databaseAPI database) {

        clientUser = _clientUser;

        week = 12;

        scheduleData = database.getSchedule(clientUser);

        String [] scheduleColumns = new String[3];
        scheduleColumns[0] = "Week";
        scheduleColumns[1] = "Home Team";
        scheduleColumns[2] = "Away Team";

        JTable scheduleTable = new JTable(new DefaultTableModel(scheduleData,scheduleColumns));
        JScrollPane scheduleContainer = new JScrollPane(scheduleTable);
        add(scheduleContainer, BorderLayout.CENTER);


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    } 

    public void refresh()
    {

    }
}

