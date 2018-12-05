package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import Game.GameFrame;
import Game.MOCCard;

public class UserMyPanel extends UserPanel {

	public static boolean myTurnCheck = false; 
	private boolean myCardUseCheck;
	private MyCardDialog dialog;
	
	private ArrayList<MOCCard> myCards;

	public UserMyPanel(Dimension screen, String name) {
		super(screen, name);
		myCards = new ArrayList<MOCCard>();
	}
	
	public int getMyCardsSize() {
		return myCards.size();
	}

	public void attackedSet(String value) {
		if(value.equals("��")) new MyCardDialog("����!").myMissShow();
	}
	
	//�� ��
	public void myTurnSet(boolean check) {
		if(check) for(MOCCard myCard:myCards) myCard.addMouseListener(new CardUseAdapter(myCard));
		else for(MOCCard myCard:myCards) myCard.removeMouseListener(myCard.getMouseListeners()[0]);
	}

	public void myCardsSet(String... cards) {
		for(String card:cards) {
			String[] data = card.split("/");
			MOCCard MOCCard = new MOCCard(200,300,data[0],data[1],data[2],Integer.parseInt(data[3]));
			MOCCard.imageSet();
			this.myCards.add(MOCCard);
		}
		
		super.consumePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(UserPanel.check) {
					dialog = new MyCardDialog("�� ī��");
					dialog.myCardShow();
				}
			}
		});
	}
	
	private class MyCardDialog extends CardDialog{

		public MyCardDialog(String name) {
			super(name);
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
					card.addMouseListener(new CardUseAdapter(card));
					add(card);
				}
				this.setSize(myCards.size()*200, 330);
			}
			setLocation((int)GameFrame.screen.getWidth()/2-this.getWidth()/2,(int)GameFrame.screen.getHeight()/2-this.getHeight()/2);
			setVisible(true);
		}
		
		public void myMissShow() {
			ArrayList<MOCCard> ������ = new ArrayList<MOCCard>();
			for(MOCCard card:myCards) if(card.getName().equals("������")) ������.add(card);
			if(������.size()==0) JOptionPane.showMessageDialog(this, "�������� �����Ƿ� ������ �ϳ� �پ��ϴ�.", "�Ӵ�!", JOptionPane.WARNING_MESSAGE);
			else {
				for(int i=0; i<������.size(); i++) {
					MOCCard card = ������.get(i);
					card.setLocation(i*200, 0);
					card.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(e.getClickCount()%2==0) {
								//������ī�徲��
								dispose();
							}
						}
					});
					add(card);
				}
				JButton button = new JButton("���� ���ڴϱ� �׳� �°ڴ�");
				button.setBounds(0,300,������.size()*200,30);
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				this.add(button);
				this.setSize(������.size()*200, 360);
				setLocation((int)GameFrame.screen.getWidth()/2-this.getWidth()/2,(int)GameFrame.screen.getHeight()/2-this.getHeight()/2);
				setVisible(true);
			}
		}
	}

	private class CardUseAdapter extends MouseAdapter{
		public MOCCard card;
		public JButton use;
		public JButton throwing;
		public JDialog userChoice;
		public CardUseAdapter(MOCCard card) {
			this.card = card;
			Map<String, Object> cardInfo = card.getCard();
			use = new JButton("���");
			use.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!myCardUseCheck) {
						myCardUseCheck = true;
						String value = card.getName();
						if(value.equals("��")||value.equals("��Ż")||value.equals("Ĺ���ο�")||
								value.equals("����")||value.equals("����")) {
							userChoice = new JDialog();
							userChoice.setTitle("��ǥ ����");
							userChoice.setResizable(false);
							userChoice.setAlwaysOnTop(true);
							userChoice.setLayout(null);
							if(value.equals("��")||value.equals("��Ż")) {
								//�Ÿ�����ʿ�
								ArrayList<MOCCard> mounts = GameFrame.users[0].mountingPanel.getMount();
								int distance = 1;
								if(mounts.size()>0) {

								}
								int index = 0;
								for(int i=1; i<GameFrame.users.length; i++) {
									if(GameFrame.users[i].getLife()==0||GameFrame.users[i].getDistance()>distance) continue;
									JButton button = new JButton(GameFrame.users[i].getNick());
									button.setBounds(index*200, 0, 200, 100);
									button.addMouseListener(new CardChoiceAdapter(button, i));
									userChoice.add(button);
									index++;
								}
								userChoice.setSize(index*200, 130);
							}else {
								for(int i=0; i<GameFrame.users.length-1; i++) {
									JButton button = new JButton(GameFrame.users[i+1].getNick());
									button.setBounds(i*200, 0, 200, 100);
									button.addMouseListener(new CardChoiceAdapter(button, i));
									userChoice.add(button);
								}
								userChoice.setSize((GameFrame.users.length-1)*200, 130);
							}
							userChoice.setLocation((int)GameFrame.screen.getWidth()/2-userChoice.getWidth()/2, 
									(int)GameFrame.screen.getHeight()/2+150);
							userChoice.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent e) {
									myCardUseCheck=false;
								}
							});
							userChoice.setVisible(true);
						}else {
							SocketReceiver.writer.println(String.format(
									"����:ī��:%d:���:%s/%s/%s/%d", 
									SocketReceiver.myRoomId,
									cardInfo.get("����").toString(), 
									value, 
									cardInfo.get("sign").toString(), 
									(int)cardInfo.get("number")));
							myCards.remove(myCards.indexOf(card));
							dialog.dispose();
							myCardUseCheck=false;
							UserPanel.check=true;
							if(userChoice != null) userChoice.dispose();
						}
					}
				}
			});

			throwing = new JButton("������");
			throwing.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					SocketReceiver.writer.println(String.format(
							"����:ī��:%d:����:%s/%s/%s/%d", 
							SocketReceiver.myRoomId,
							cardInfo.get("����").toString(), 
							cardInfo.get("name").toString(), 
							cardInfo.get("sign").toString(), 
							(int)cardInfo.get("number")));
					myCards.remove(myCards.indexOf(card));
					if(userChoice != null) userChoice.dispose();
					dialog.dispose();
					UserPanel.check=true;
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

		private class CardChoiceAdapter extends MouseAdapter{
			JButton button;
			int goal;
			Map<String, Object> cardInfo;
			public CardChoiceAdapter(JButton button, int goal) {
				this.button = button;
				this.goal=goal;
				this.cardInfo = card.getCard();
			}	
			
			@Override
			public void mouseClicked(MouseEvent e) {
				SocketReceiver.writer.println(String.format(
						"����:ī��:%d:���:%s/%s/%s/%s:%d", 
						SocketReceiver.myRoomId,
						cardInfo.get("����").toString(), 
						cardInfo.get("name").toString(), 
						cardInfo.get("sign").toString(), 
						(int)cardInfo.get("number"),
						this.goal));
				myCards.remove(myCards.indexOf(card));
				if(userChoice!=null) userChoice.dispose();
				dialog.dispose();
				UserPanel.check=true;
				myCardUseCheck=false;
			}
		}
	}
}
