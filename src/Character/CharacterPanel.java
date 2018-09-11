package Character;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;

public class CharacterPanel extends JLayeredPane{
	private JLabel ����;
	public CharacterPanel() {
		setLayout(null);
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		setBackground(Color.white);
		
		ImageIcon image = new ImageIcon("./image/job/���Ȱ�����.jpg");
		image = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		���� = new JLabel(image);
		����.setBounds(70, 10, 50, 50);
		
		image = new ImageIcon("./image/job/back.jpg");
		image = new ImageIcon(image.getImage().getScaledInstance(110, 180, Image.SCALE_SMOOTH));
		JLabel content = new JLabel(image);
		content.setBounds(10, 10, image.getIconWidth(), image.getIconHeight());
		add(content,1);
		
		add(����,0);
	}
	
	public void set���Ȱ�(boolean check) {
		����.setVisible(check);
		����.revalidate();
	}
}
