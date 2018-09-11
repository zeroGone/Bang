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
		
		TitledBorder nick = new TitledBorder("테스트");//테두리 titledBorder로 설정해서 닉네임을 타이틀로
		setBorder(nick);//테두리 위에서부터 20 양쪽 10 빼야됨 
		
		lifePanel = new JPanel();//생명을 표시할 패널
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
	
	//생명 회복시
	private void addLife() {
		
	}
	
	//생명 잃을시
	private void removeLife() {
		
	}
	
}