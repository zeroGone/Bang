package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Character.CharacterPanel;

public class UserPanel extends JPanel{
	private JLabel[] life;
	private JPanel lifePanel;
	private MountingPanel mountingPanel;
	public UserPanel(Dimension screen, String name) {
		setSize(400,300);
		setLayout(null);
		setBackground(Color.WHITE);
		
		TitledBorder nick = new TitledBorder(name);//테두리 titledBorder로 설정해서 닉네임을 타이틀로
		setBorder(nick);//테두리 위에서부터 20 양쪽 10 빼야됨 
		
		lifePanel = new JPanel();//생명을 표시할 패널
		lifePanel.setBackground(Color.WHITE);
		lifePanel.setBounds(10, 20, 380, 40);
		
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/life_void.png"));
		image=new ImageIcon(image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		life=new JLabel[5];//생명숫자
		for(int i=0; i<5; i++) {//생명숫자 셋팅
			life[i]=new JLabel(image);
			lifePanel.add(life[i]);
		}
		
		add(lifePanel,"North");//생명패널 추가
		
		//캐릭터패널
		CharacterPanel characterPanel = new CharacterPanel();
		characterPanel.setBounds(this.getWidth()/3, 70, this.getWidth()/3, 200);
		characterPanel.set보안관(false);//보안관이면 true로
		add(characterPanel);
		
		
		//장착카드패널
		mountingPanel = new MountingPanel();
		mountingPanel.setBounds(this.getWidth()/3*2, 70, this.getWidth()/3, 200);
		add(mountingPanel);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new CardDialog();
			}
		});
			
		JPanel consumePanel = new JPanel();
		consumePanel.setBounds(0, 70, this.getWidth()/3, 200);
		consumePanel.setBackground(Color.black);
		add(consumePanel);
	}
	
	//생명 회복시
	private void addLife() {
		
	}
	
	//생명 잃을시
	private void removeLife() {
		
	}
	
	private class CardDialog extends JDialog{
		public CardDialog() {
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/카드뒷면.jpg")).getImage());
			setSize(1000,500);
			setTitle("카드목록");
			setVisible(true);
			setLocationRelativeTo(null);//화면 중앙에 배치
			setResizable(false);
		}
	}
}