package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Character.CharacterPanel;

public class UserPanel extends JPanel{
	private JLabel[] life;
	private JPanel lifePanel;
	
	public UserPanel(Dimension screen) {
		setSize(400,300);
		setLayout(null);
		setBackground(Color.WHITE);
		
		TitledBorder nick = new TitledBorder("�׽�Ʈ");//�׵θ� titledBorder�� �����ؼ� �г����� Ÿ��Ʋ��
		setBorder(nick);//�׵θ� ���������� 20 ���� 10 ���ߵ� 
		
		lifePanel = new JPanel();//������ ǥ���� �г�
		lifePanel.setBackground(Color.WHITE);
		lifePanel.setBounds(10, 20, 380, 40);
		
		ImageIcon image = new ImageIcon("./image/life_void.png");
		image=new ImageIcon(image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		life=new JLabel[5];
		for(int i=0; i<5; i++) {
			life[i]=new JLabel(image);
			lifePanel.add(life[i]);
		}
		
		add(lifePanel,"North");
		
		CharacterPanel characterPanel = new CharacterPanel();
		characterPanel.setBounds(this.getWidth()/3, 70, this.getWidth()/3, 200);
		add(characterPanel);
	}
	
	//���� ȸ����
	private void addLife() {
		
	}
	
	//���� ������
	private void removeLife() {
		
	}
	
}