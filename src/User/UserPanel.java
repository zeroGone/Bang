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
	private MountingPanel mountingPanel;
	//소비 카드 패널
	private JPanel consumePanel;
	private JPanel lifePanel;
	private int life;
	private ImageIcon lifeImage;
	private JLabel[] lifeLabel;
	private ArrayList<MOCCard> myCards;
	//다이얼로그 중복으로 띄우는 것을 방지하기 위한 변수
	private boolean check = true;
	public static boolean myTurnCheck = false; 
	
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
		
		myCards = new ArrayList<MOCCard>();
	}
	
	public String getNick() {
		return this.nick;
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
	
	public void myCardsSet(String... cards) {
		for(String card:cards) {
			String[] data = card.split("/");
			MOCCard MOCCard = new MOCCard(200,300,data[0],data[1],data[2],Integer.parseInt(data[3]));
			MOCCard.imageSet();
			this.myCards.add(MOCCard);
		}
		
		this.consumePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check) new CardDialog("내 카드").myCardShow();
			}
		});
	}
	
	//턴 셋
	public void myTurnSet(boolean check) {
		if(check) for(MOCCard myCard:myCards) myCard.addMouseListener(new CardUseAdapter(myCard));
		else for(MOCCard myCard:myCards) myCard.removeMouseListener(myCard.getMouseListeners()[0]);
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
				if(check) new CardDialog("내 직업").jobShow();
			}
		});
	}
	
	private class CardDialog extends JDialog{
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
		
		public void myCardShow() {
			if(myCards.size()==0) {
				ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/x.png"));
				JLabel label = new JLabel(image);
				label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
				add(label);
				this.setSize(label.getWidth(), label.getHeight()+40);
			}else {
				for(int i=0; i<myCards.size(); i++) {
					MOCCard card = myCards.get(i);
					card.setLocation(i*200, 0);
					add(card);
				}
				this.setSize(myCards.size()*200, 330);
			}
			setLocation((int)GameFrame.screen.getWidth()/2-this.getWidth()/2,(int)GameFrame.screen.getHeight()/2-this.getHeight()/2);
			setVisible(true);
		}
	}

	private class CardUseAdapter extends MouseAdapter{
		public MOCCard card;
		public JButton use;
		public JButton throwing;
		public CardUseAdapter(MOCCard card) {
			this.card = card;
			use = new JButton("사용");
			use.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String value = card.getName();
					if(value.equals("뱅")||value.equals("강탈")||value.equals("캣벌로우")||
							value.equals("결투")||value.equals("감옥")) {
						JDialog userChoice = new JDialog();
						userChoice.setTitle("목표 선택");
						userChoice.setResizable(false);
						userChoice.setAlwaysOnTop(true);
						userChoice.setLayout(null);
						for(int i=0; i<GameFrame.users.length-1; i++) {
							JButton button = new JButton(GameFrame.users[i+1].getNick());
							button.setBounds(i*200, 0, 200, 100);
							userChoice.add(button);
						}
						
						userChoice.setSize((GameFrame.users.length-1)*200, 130);
						userChoice.setLocation((int)GameFrame.screen.getWidth()/2-userChoice.getWidth()/2, 
								(int)GameFrame.screen.getHeight()/2-userChoice.getHeight()/2);
						userChoice.setVisible(true);
					}else {
						System.out.println(value+"사용");
					}
				}
			});
			
			throwing = new JButton("버리기");
			throwing.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println(card.getName()+"버림");
				}
			});
			
			use.setBounds(card.getWidth()/2-50, card.getHeight()/2-50, 100, 50);
			throwing.setBounds(card.getWidth()/2-50, card.getHeight()/2+25, 100, 50);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()%2==0) {
				card.setBorder(new LineBorder(Color.BLACK, 2));
				card.add(use,2);
				card.add(throwing,2);
			}else {
				card.setBorder(null);
				card.remove(use);
				card.remove(throwing);
			}
			card.revalidate();
		}
	}
}