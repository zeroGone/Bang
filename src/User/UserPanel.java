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
		
		TitledBorder nick = new TitledBorder(name);//�׵θ� titledBorder�� �����ؼ� �г����� Ÿ��Ʋ��
		setBorder(nick);//�׵θ� ���������� 20 ���� 10 ���ߵ� 
		
		lifePanel = new JPanel();//������ ǥ���� �г�
		lifePanel.setBackground(Color.WHITE);
		lifePanel.setBounds(10, 20, 380, 40);
		
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/life_void.png"));
		image=new ImageIcon(image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		life=new JLabel[5];//�������
		for(int i=0; i<5; i++) {//������� ����
			life[i]=new JLabel(image);
			lifePanel.add(life[i]);
		}
		
		add(lifePanel,"North");//�����г� �߰�
		
		//ĳ�����г�
		CharacterPanel characterPanel = new CharacterPanel();
		characterPanel.setBounds(this.getWidth()/3, 70, this.getWidth()/3, 200);
		characterPanel.set���Ȱ�(false);//���Ȱ��̸� true��
		add(characterPanel);
		
		
		//����ī���г�
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
	
	//���� ȸ����
	private void addLife() {
		
	}
	
	//���� ������
	private void removeLife() {
		
	}
	
	private class CardDialog extends JDialog{
		public CardDialog() {
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/ī��޸�.jpg")).getImage());
			setSize(1000,500);
			setTitle("ī����");
			setVisible(true);
			setLocationRelativeTo(null);//ȭ�� �߾ӿ� ��ġ
			setResizable(false);
		}
	}
}