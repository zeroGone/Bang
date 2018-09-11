package Character;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;

public class CharacterPanel extends JLayeredPane{
	private JLabel 배지;
	public CharacterPanel() {
		setLayout(null);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		setBackground(Color.white);
		
		ImageIcon image = new ImageIcon("./image/job/보안관배지.jpg");
		image = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		배지 = new JLabel(image);
		배지.setBounds(70, 10, 50, 50);
		
		image = new ImageIcon("./image/job/back.jpg");
		image = new ImageIcon(image.getImage().getScaledInstance(110, 180, Image.SCALE_SMOOTH));
		JLabel content = new JLabel(image);
		content.setBounds(10, 10, image.getIconWidth(), image.getIconHeight());
		add(content,1);
		
		add(배지,0);
	}
	
	public void set보안관(boolean check) {
		배지.setVisible(check);
		배지.revalidate();
	}
}
