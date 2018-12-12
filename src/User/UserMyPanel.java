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
	private boolean bangCheck;
	private MyCardDialog dialog;
	private ArrayList<MOCCard> myCards;

	public UserMyPanel(Dimension screen, String name) {
		super(screen, name);
		myCards = new ArrayList<MOCCard>();
	}
	
	public void removeMyCard(String card) {
		myCards.remove(Integer.parseInt(card));
	}
	
	public void removeMyCard(String[] data) {
		MOCCard MOCCard = new MOCCard(200,300,data[0],data[1],data[2],Integer.parseInt(data[3]));
		myCards.remove(MOCCard);
	}
	
	public String getMyAllCards() {
		ArrayList<MOCCard> mountCards = super.mountingPanel.getMount();
		StringBuilder builder = new StringBuilder();
		for(MOCCard card : myCards) {
			Map data = card.getCard();
			builder.append(data.get("종류")+"/");
			builder.append(data.get("name")+"/");
			builder.append(data.get("sign")+"/");
			builder.append(data.get("number")+",");
		}
		builder.append(";");
		for(MOCCard card : mountCards) {
			Map data = card.getCard();
			builder.append(data.get("종류")+"/");
			builder.append(data.get("name")+"/");
			builder.append(data.get("sign")+"/");
			builder.append(data.get("number")+",");
		}
		
		return mountCards.size()==0?builder.toString():builder.toString().substring(0, builder.length()-1);
	}
	
	public int getMyCardsSize() {
		return myCards.size();
	}

	public void attackedSet(String caster, String goal, String value) {
		if(value.equals("뱅")) new MyCardDialog("피해!").myMissShow(caster, goal);
	}
	
	//턴 셋
	public void myTurnSet(boolean check) {
		if(check) for(MOCCard myCard:myCards) {
			myTurnCheck = true;
			myCard.addMouseListener(new CardUseAdapter(myCard));
		}
		else for(MOCCard myCard:myCards) {
			myTurnCheck = false;
			myCard.removeMouseListener(myCard.getMouseListeners()[0]);
		}
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
				if(UserPanel.check) {
					dialog = new MyCardDialog("내 카드");
					dialog.myCardShow();
				}
			}
		});
	}
	
	@Override
	public void cardNumSet(int num) {
		super.cardNumSet(num);
		this.consumePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(UserPanel.check) {
					dialog = new MyCardDialog("내 카드");
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
					if(myTurnCheck) card.addMouseListener(new CardUseAdapter(card));
					add(card);
				}
				this.setSize(myCards.size()*200, 330);
			}
			setLocation((int)GameFrame.screen.getWidth()/2-this.getWidth()/2,(int)GameFrame.screen.getHeight()/2-this.getHeight()/2);
			setVisible(true);
		}
		
		public void myMissShow(String caster, String goal) {
			ArrayList<MOCCard> 빗나감 = new ArrayList<MOCCard>();
			for(MOCCard card:myCards) if(card.getName().equals("빗나감")) 빗나감.add(card);
			if(빗나감.size()==0) {
				UserPanel.check = true;
				JOptionPane.showMessageDialog(this, "빗나감이 없으므로 생명이 하나 줄어듭니다.", "앙대!", JOptionPane.WARNING_MESSAGE);
				SocketReceiver.writer.println(String.format(
						"게임:뱅:%d:%s:%s:%s", SocketReceiver.myRoomId, caster, goal, "false"));
			}
			else {
				for(int i=0; i<빗나감.size(); i++) {
					MOCCard card = 빗나감.get(i);
					card.setLocation(i*200, 0);
					card.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(e.getClickCount()%2==0) {
								myCards.remove(card);
								UserPanel.check = true;
								SocketReceiver.writer.println(String.format(
										"게임:뱅:%d:%s:%s:%s", SocketReceiver.myRoomId, caster, goal, "true"));
								dispose();
							}
						}
					});
					add(card);
				}
				JButton button = new JButton("나는 상남자니까 그냥 맞겠다");
				button.setBounds(0,300,빗나감.size()*200,30);
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						UserPanel.check = true;
						SocketReceiver.writer.println(String.format(
								"게임:뱅:%d:%s:%s:%s", SocketReceiver.myRoomId, caster, goal, "false"));
						dispose();
					}
				});
				this.add(button);
				this.setSize(빗나감.size()*200, 360);
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
			use = new JButton("사용");
			use.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!myCardUseCheck&&myTurnCheck) {
						myCardUseCheck = true;
						String value = card.getName();
						if(value.equals("뱅")||value.equals("강탈")||value.equals("캣벌로우")||
								value.equals("결투")||value.equals("감옥")) {
							userChoice = new JDialog();
							userChoice.setTitle("목표 선택");
							userChoice.setResizable(false);
							userChoice.setAlwaysOnTop(true);
							userChoice.setLayout(null);
							
							int index = 0;
							if(value.equals("뱅")) {
								//거리계산필요
								ArrayList<MOCCard> mounts = GameFrame.users[0].mountingPanel.getMount();
								int distance = 1;
								if(mounts.size()>0) {
									for(MOCCard mount:mounts) {
										if(mount.getName().equals("볼캐닉")) bangCheck = false;
										else if(mount.getName().equals("스코필드")) distance = 2;
										else if(mount.getName().equals("레밍턴")) distance = 3;
										else if(mount.getName().equals("카빈")) distance = 4;
										else if(mount.getName().equals("윈체스터")) distance = 5;
									}
								}
								if(bangCheck) {
									ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/x.png"));
									JLabel label = new JLabel(image);
									label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
									userChoice.add(label);
									userChoice.setSize(label.getWidth(), label.getHeight()+40);
								}else {
									for(int i=1; i<GameFrame.users.length; i++) {
										if(GameFrame.users[i].getLife()==0||GameFrame.users[i].getDistance()>distance) continue;
										JButton button = new JButton(GameFrame.users[i].getNick());
										button.setBounds(index*200, 0, 200, 100);
										button.addMouseListener(new CardChoiceAdapter(button, i));
										userChoice.add(button);
										index++;
									}
									userChoice.setSize(index*200, 130);
								}
							//강탈
							}else if(value.equals("강탈")) {
								for(int i=1; i<GameFrame.users.length; i++) {
									if(GameFrame.users[i].getLife()==0||GameFrame.users[i].getDistance()>=2) continue;
									JButton button = new JButton(GameFrame.users[i].getNick());
									button.setBounds(index*200, 0, 200, 100);
									button.addMouseListener(new CardChoiceAdapter(button, i));
									userChoice.add(button);
									index++;
								}
								if(index == 0) {
									ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/x.png"));
									JLabel label = new JLabel(image);
									label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
									add(label);
									userChoice.setSize(label.getWidth(), label.getHeight()+40);
								}else userChoice.setSize(index*200, 130);
							}
							//감옥
							else {
								for(int i=1; i<GameFrame.users.length; i++) {
									if(!GameFrame.users[i].보안관Check()) {
										JButton button = new JButton(GameFrame.users[i].getNick());
										button.setBounds((i-1)*200, 0, 200, 100);
										button.addMouseListener(new CardChoiceAdapter(button, i));
										userChoice.add(button);
									}
								}
								userChoice.setSize((GameFrame.users.length-2)*200, 130);
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
							myCardUseCheck=false;
							UserPanel.check=true;
							if(value.equals("맥주")&&life==lifeLabel.length) 
								JOptionPane.showMessageDialog(card, "풀피이므로 맥주를 쓸수 없습니다.", "놉", JOptionPane.WARNING_MESSAGE);
							else if(value.equals("빗나감"))
								JOptionPane.showMessageDialog(card, "빗나감은 뱅맞을때만 사용가능합니다.", "놉", JOptionPane.WARNING_MESSAGE);
							else {
								SocketReceiver.writer.println(String.format(
										"게임:카드:%d:사용:%s/%s/%s/%d", 
										SocketReceiver.myRoomId,
										cardInfo.get("종류").toString(), 
										value, 
										cardInfo.get("sign").toString(), 
										(int)cardInfo.get("number")));
								myCards.remove(myCards.indexOf(card));
								dialog.dispose();
								if(userChoice != null) userChoice.dispose();
								if(value.equals("조준경")) {
									for(int i=1; i<GameFrame.users.length; i++) 
										GameFrame.users[i].setDistance(GameFrame.users[i].getDistance()-1);
								}else if(value.equals("다이너마이트")) {
								}else if(value.equals("술통")){
								}
								
								ArrayList<MOCCard> mounts = mountingPanel.getMount();
								String[] guns = new String[] {"볼캐닉","스코필드","레밍턴","카빈","윈체스터"};
								
								boolean check = false;
								for(String temp:guns) if(value.equals(temp)) check = true;
								
								mountCheck:
								for(int i=0; i<mounts.size(); i++) {
									if(value.equals(mounts.get(i).getName())) {
										Map map = mounts.get(i).getCard();
										SocketReceiver.writer.println(String.format(
												"게임:무덤설정:%d:%s/%s/%s/%d", 
												SocketReceiver.myRoomId, 
												map.get("종류").toString(), 
												map.get("name").toString(),
												map.get("sign").toString(),
												(int)map.get("number")));
										SocketReceiver.writer.println(String.format(
												"게임:장착삭제:%d:%s/%s/%s/%d", 
												SocketReceiver.myRoomId, 
												map.get("종류").toString(), 
												map.get("name").toString(),
												map.get("sign").toString(),
												(int)map.get("number")));
										break;
									}
									else {
										for(String temp:guns) {
											if(mounts.get(i).getName().equals(temp)&&check) {
												Map map = mounts.get(i).getCard();
												SocketReceiver.writer.println(String.format(
														"게임:무덤설정:%d:%s/%s/%s/%d", 
														SocketReceiver.myRoomId, 
														map.get("종류").toString(), 
														map.get("name").toString(),
														map.get("sign").toString(),
														(int)map.get("number")));
												SocketReceiver.writer.println(String.format(
														"게임:장착삭제:%d:%s/%s/%s/%d", 
														SocketReceiver.myRoomId, 
														map.get("종류").toString(), 
														map.get("name").toString(),
														map.get("sign").toString(),
														(int)map.get("number")));
												break mountCheck;
											};
										}
									}
								}
							}
						}
					}
				}
			});
			throwing = new JButton("버리기");
			throwing.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(myTurnCheck) {
						SocketReceiver.writer.println(String.format(
								"게임:카드:%d:버림:%s/%s/%s/%d", 
								SocketReceiver.myRoomId,
								cardInfo.get("종류").toString(), 
								cardInfo.get("name").toString(), 
								cardInfo.get("sign").toString(), 
								(int)cardInfo.get("number")));
						myCards.remove(myCards.indexOf(card));
						if(userChoice != null) userChoice.dispose();
						dialog.dispose();
						UserPanel.check=true;
					}
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
						"게임:카드:%d:사용:%s/%s/%s/%s:%d", 
						SocketReceiver.myRoomId,
						cardInfo.get("종류").toString(), 
						cardInfo.get("name").toString(), 
						cardInfo.get("sign").toString(), 
						(int)cardInfo.get("number"),
						this.goal));
				myCards.remove(myCards.indexOf(card));
				if(cardInfo.get("name").equals("뱅")) bangCheck = true;
				if(userChoice!=null) userChoice.dispose();
				dialog.dispose();
				UserPanel.check=true;
				myCardUseCheck=false;
			}
		}
	}
}
