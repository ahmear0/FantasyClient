import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Graphics;

public class MenuPanel extends JPanel implements FantasyPanel {

	private static final long serialVersionUID = 7907473022182198683L;

	private String clientUser;

    public MenuPanel(String _clientUser) {


        setBackground(new Color(255,255,255));
        setSize(new Dimension(400,70));
        setLayout(new FlowLayout());

        clientUser = _clientUser;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    } 

    public void refresh()
    {
        
    }
}