import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class RefreshButtonListener extends MouseInputAdapter 
{

	FantasyPanel panel;
	JButton button;

	public RefreshButtonListener(FantasyPanel _panel, JButton _button)
	{
		super();
		panel = _panel;
		button = _button;
	}
	public void mouseEntered(MouseEvent me)
    {
        button.setBackground(new Color(200,200,200));
    }
    public void mouseExited(MouseEvent me)
    {
        button.setBackground(Color.WHITE);
    }
    public void mousePressed(MouseEvent me) 
    { 
        button.setBackground(new Color(180,180,180));
    } 
    public void mouseReleased(MouseEvent me)
    {
        button.setBackground(new Color(200,200,200));
    }
    public void mouseClicked(MouseEvent me)
    {
        button.setBackground(new Color(200,200,200));
        panel.refresh();
    }
}