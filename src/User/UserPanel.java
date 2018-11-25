package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Game.Card;
import Game.CharacterCard;
import Game.GameFrame;
import Game.MOCCard;

public class UserPanel extends JPanel{
	public static final int MAX_LIFE_NUM = 5;
	private String nick;
	private String job;
	//ĳ���� ī��
	private Card character;
	//���� ī�� �г�
	private MountingPanel mountingPanel;

	private int life;
	private ImageIcon lifeImage;
	private JLabel[] lifeLabel;
	private JPanel lifePanel;
	
	//���̾�α� �ߺ����� ���� ���� �����ϱ� ���� ����
	private static boolean check = true;
	
	private boolean getCheck() { return check; };
	
	public UserPanel(Dimension screen, String name) {
		setSize(400,300);
		setLayout(null);
		setBackground(Color.WHITE);
		
		this.nick=name;
		TitledBorder nick = new TitledBorder(name);//�׵θ� titledBorder�� �����ؼ� �г����� Ÿ��Ʋ��
		setBorder(nick);//�׵θ� ���������� 20 ���� 10 ���ߵ� 
		
		//������ ǥ���� �г�
		lifePanel = new JPanel();
		lifePanel.setBackground(Color.WHITE);
		lifePanel.setBounds(10, 20, 380, 40);
		lifePanel.setOpaque(false);
		
		lifeLabel=new JLabel[MAX_LIFE_NUM];//�������
		lifeImage = new ImageIcon(getClass().getClassLoader().getResource("image/life.png"));
		lifeImage= new ImageIcon(lifeImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		add(lifePanel,"North");//�����г� �߰�
		
		//�Һ�ī���г�
		JPanel consumePanel = new JPanel();
		consumePanel.setBounds(0, 70, this.getWidth()/3, 200);
		consumePanel.setBackground(Color.black);
		consumePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check&&character!=null) new CardDialog("�Һ�ī��");
			}
		});
		add(consumePanel);
		
		//����ī���г�
		mountingPanel = new MountingPanel();
		mountingPanel.setBounds(this.getWidth()/3*2, 70, this.getWidth()/3, 200);
		mountingPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check) new CardDialog("����ī��").mountShow();
			}
		});
		MOCCard moc = new MOCCard(this.getWidth()/3, 200, "mount", "���̳ʸ���Ʈ", "���̾�", 9);
		moc.imageSet();
		MOCCard moc2 =  new MOCCard(this.getWidth()/3, 200, "mount", "����", "�����̵�", 10);
		moc2.imageSet();
		mountingPanel.addMounting(moc);
		mountingPanel.addMounting(moc2);
		
		add(mountingPanel);
	}
	
	public String getNick() {
		return this.nick;
	}
	
	//ĳ���� ����
	public void CharacterSet(String value) {
		this.character = new CharacterCard(this.getWidth()/3, 200, value);
		this.character.setLocation(this.getWidth()/3*1, 70);
		this.character.imageSet();
		this.character.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check) new CardDialog(character.getName()).characterShow();
			}
		});
		this.add(character);
	}
	
	//���� ����
	public void lifeSet(int life) {
		this.life = life;
		for(int i=0; i<life; i++) {//������� ����
			lifeLabel[i]=new JLabel(lifeImage);
			lifePanel.add(lifeLabel[i]);
		}
		lifePanel.repaint();
	}
	
	//���� ���̰ų� �߰�
	public void lifeAddOrRemove(int value) {
		lifePanel.removeAll();
		for(int i=0; i<this.life+value; i++) {
			lifeLabel[i]=new JLabel(lifeImage);
			lifePanel.add(lifeLabel[i]);
		}
		lifePanel.repaint();
	}
	
	public void ���Ȱ�Set() {
		this.job="���Ȱ�";
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/job/���Ȱ�����.jpg"));
		image = new ImageIcon(image.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		label.setBounds(0, 15, 60, 60);
		add(label);
		this.revalidate();
	}
	
	private class CardDialog extends JDialog{
		public CardDialog(String name) {
			check = false;
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/ani/back.jpg")).getImage());
			setTitle(name);
			setResizable(false);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					check = true;
				}
			});
		}
		
		public void characterShow() {
			this.setLayout(new FlowLayout());
			this.setSize(500,800);
			Card character = new CharacterCard(500, 800, this.getTitle());
			character.imageSet();
			this.add(character);
			this.setVisible(true);
		}
		
		public void mountShow() {
			this.setLayout(null);
			ArrayList<MOCCard> list = mountingPanel.getMount();
			if(list.size()==0) {
				ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/x.png"));
				JLabel label = new JLabel(image);
				label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
				add(label);
				this.setSize(label.getWidth(), label.getHeight()+40);
			}else {
				for(int i=0; i<list.size(); i++) {
					Map data = list.get(i).getCard();
					MOCCard card = new MOCCard(300, 450, (String)data.get("����"), (String)data.get("name"), (String)data.get("sign"), (int)data.get("number"));
					card.setBounds(i*300, 0, 300, 450);
					card.imageSet();
					add(card);
				}
				this.setSize(list.size()*300, 490);
			}
			setLocation((int)GameFrame.screen.getWidth()/2-this.getWidth()/2,(int)GameFrame.screen.getHeight()/2-this.getHeight()/2);
			setVisible(true);
		}
		
		public void consumeShow() {
			this.setLayout(null);
		}
	}
}