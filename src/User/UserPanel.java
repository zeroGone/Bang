package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Game.Card;
import Game.CharacterCard;
import Game.GameFrame;
import Game.JobCard;
import Game.MOCCard;

public class UserPanel extends JPanel{
	public static final int MAX_LIFE_NUM = 5;
	private String nick;
	private String job;
	//캐릭터 카드
	private Card character;
	//장착 카드 패널
	public static MountingPanel mountingPanel;
	//소비 카드 패널
	protected JPanel consumePanel;
	private JPanel lifePanel;
	private int life;
	public int distance;
	private ImageIcon lifeImage;
	private JLabel[] lifeLabel;
	//다이얼로그 중복으로 띄우는 것을 방지하기 위한 변수
	public static boolean check = true;
	
	public UserPanel(Dimension screen, String name) {
		setSize(400,300);
		setLayout(null);
		setBackground(Color.WHITE);
		
		this.nick=name;
		TitledBorder nick = new TitledBorder(name);//테두리 titledBorder로 설정해서 닉네임을 타이틀로
		setBorder(nick);//테두리 위에서부터 20 양쪽 10 빼야됨 
		
		//생명을 표시할 패널
		lifePanel = new JPanel();
		lifePanel.setBackground(Color.WHITE);
		lifePanel.setBounds(10, 20, 380, 40);
		lifePanel.setOpaque(false);
		
		lifeLabel=new JLabel[MAX_LIFE_NUM];//생명숫자
		lifeImage = new ImageIcon(getClass().getClassLoader().getResource("image/life.png"));
		lifeImage= new ImageIcon(lifeImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		add(lifePanel,"North");//생명패널 추가
		
		//소비카드패널
		consumePanel = new JPanel();
		consumePanel.setLayout(null);
		consumePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		consumePanel.setBounds(0, 70, this.getWidth()/3, 200);
		consumePanel.setBackground(Color.white);

		add(consumePanel);
		
		//장착카드패널
		mountingPanel = new MountingPanel();
		mountingPanel.setBounds(this.getWidth()/3*2, 70, this.getWidth()/3, 200);
		mountingPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check) new CardDialog("장착카드").mountShow();
			}
		});
		
		add(mountingPanel);
		
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public int getLife() {
		return this.life;
	}
	
	public void dieSet() {
		this.removeAll();
		Card jobCard = new JobCard(this.getWidth()/2, this.getHeight(), job);
		jobCard.imageSet();
		jobCard.setLocation(this.getWidth()/2-this.getWidth()/4, 0);
		this.add(jobCard);
		this.revalidate();
	}
	
	//소비카드 세팅
	public void cardNumSet(int num) {
		consumePanel.removeAll();
		if(num>0) {
			JLabel label = new JLabel(Integer.toString(num)+"장");
			label.setFont(new Font(null, Font.BOLD, 20));
			label.setBounds(
					0, 0, 
					40, 40);
			consumePanel.add(label);
			ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/back.jpg"));
			image = new ImageIcon(image.getImage().getScaledInstance(consumePanel.getWidth(), consumePanel.getHeight(), Image.SCALE_SMOOTH));
			label = new JLabel(image);
			label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
			consumePanel.add(label);
		}else consumePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		consumePanel.repaint();
	}
	
	//캐릭터 세팅
	public void characterSet(String value) {
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
	
	//생명 셋팅
	public void lifeSet(int life) {
		this.life = life;
		for(int i=0; i<life; i++) {//생명숫자 셋팅
			lifeLabel[i]=new JLabel(lifeImage);
			lifePanel.add(lifeLabel[i]);
		}
		this.revalidate();
	}
	
	//생명 깍이거나 추가
	public void lifeAddOrRemove(int value) {
		this.life+=value;
		lifePanel.removeAll();
		for(int i=0; i<this.life+value; i++) {
			lifeLabel[i]=new JLabel(lifeImage);
			lifePanel.add(lifeLabel[i]);
		}
		this.revalidate();
	}
	
	public void 보안관Set() {
		this.job="보안관";
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/job/보안관배지.jpg"));
		image = new ImageIcon(image.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check) new CardDialog("보안관").jobShow();
			}
		});
		label.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		label.setBounds(0, 15, 60, 60);
		add(label);
		this.revalidate();
	}
	
	public void jobSet(String job) {
		this.job=job;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check) new CardDialog("직업").jobShow();
			}
		});
	}
	
	protected class CardDialog extends JDialog{
		public CardDialog(String name) {
			check = false;
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/ani/back.jpg")).getImage());
			setTitle(name);
			setLayout(null);
			setResizable(false);
			setAlwaysOnTop(true);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					check = true;
				}
			});
		}
		
		public void characterShow() {
			this.setSize(450,700);
			Card character = new CharacterCard(450, 700, this.getTitle());
			character.imageSet();
			character.setLocation(0, 0);
			this.add(character);
			this.setVisible(true);
		}
		
		public void jobShow() {
			this.setSize(450,700);
			Card jobCard = new JobCard(450, 700, job);
			jobCard.imageSet();
			jobCard.setLocation(0, 0);
			this.add(jobCard);
			this.setVisible(true);
		}
		
		public void mountShow() {
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
					MOCCard card = new MOCCard(300, 450, (String)data.get("종류"), (String)data.get("name"), (String)data.get("sign"), (int)data.get("number"));
					card.setBounds(i*300, 0, 300, 450);
					card.imageSet();
					add(card);
				}
				this.setSize(list.size()*300, 490);
			}
			setLocation((int)GameFrame.screen.getWidth()/2-this.getWidth()/2,(int)GameFrame.screen.getHeight()/2-this.getHeight()/2);
			setVisible(true);
		}
	}
}