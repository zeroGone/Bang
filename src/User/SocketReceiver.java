package User;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import Game.GameFrame;
import Game.MOCCard;
import Start.Main;

public class SocketReceiver implements Runnable{
	private Main main;
	private BufferedReader reader;
	public static PrintWriter writer;
	private String nick;
	public static int myRoomId;
	private GameFrame gameFrame;
	public SocketReceiver(Main main, Socket socket) {
		this.main=main;
		try {
			writer = new PrintWriter(socket.getOutputStream(),true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Thread(this).start();
			myRoomId=-1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setNick(String nick) {
		this.nick=nick;
		writer.println("�г���:"+nick);
	}
	
	public void roomCreate() {
		if(myRoomId==-1) {
			String title = (String)JOptionPane.showInputDialog(main,null,"������ �Է�",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if(title!=null) {
				if(title.length()==0) title="�̸����¹�";
				writer.println("��:����:"+title+","+nick);
			}
		}
		else JOptionPane.showMessageDialog(null, "�ϳ��� �游 ���� ���", "Warning!", JOptionPane.WARNING_MESSAGE);
	}

	public void roomEnter(int id) {
		if(myRoomId==-1) writer.println("��:����:"+id+","+nick);
		else JOptionPane.showMessageDialog(null, "�ϳ��� �游 ���� ���", "Warning!", JOptionPane.WARNING_MESSAGE);
	}

	public void chatting(String ����) {
		writer.println("��ä��:"+nick+","+����);
	}

	@Override
	public void run() {
		while(true) {
			try {
				String temp = reader.readLine();
				if(temp==null) continue;
				else {
					System.out.println(temp);
					String[] data = temp.split(":");
					switch(data[0]) {
					case "�г���":
						data = data[1].split(",");
						main.userAdd(data);
						break;
					case "��":
						data[1]=data[1].substring(1, data[1].length()-1);
						if(data[1].equals("")) data = new String[0];
						else data = data[1].split(",");
						main.roomAdd(data);
						break;
					case "��ä��":
						data = data[1].split(",");
						main.roomChatting(data[0]+":"+data[1]);
						break;
					case "�����":
						int id = Integer.parseInt(data[1]);
						myRoomId = id;
						gameFrame = new Game.GameFrame();
						gameFrame.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent e) {
								writer.println("����:����:"+myRoomId);
								myRoomId = -1;
							}
						});
						break;
					case "Ǯ��":
						JOptionPane.showMessageDialog(null, "Ǯ���ΰ���~", "Warning!", JOptionPane.WARNING_MESSAGE);
						break;
					case "����������":
						JOptionPane.showMessageDialog(null, "�����������ΰ���~!", "Warning!", JOptionPane.WARNING_MESSAGE);
						break;
					case "���̷�":
						myRoomId=Integer.parseInt(data[1]);
						break;
					case "����":
						switch(data[1]) {
						case "ä��":
							GameFrame.chatOutput.append(data[2]+":"+data[3]+"\n");
							break;
						case "�����غ�":
							gameFrame.gameReady();
							break;
						case "����":
							data = data[2].split("/");
							gameFrame.userSet(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]);
							break;
						case "�ɸ�����":
							data = data[2].split("/");
							gameFrame.userCharacterSet(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2].split(","));
							break;
						case "���Ȱ�����":
							gameFrame.���Ȱ�Set(Integer.parseInt(data[2]));
							break;
						case "��������":
							gameFrame.myJobSet(data[2]);
							break;
						case "������":
							String[] lifeValue = data[4].split(",");
							int[] life = new int[lifeValue.length];
							for(int i=0; i<lifeValue.length; i++) life[i]=Integer.parseInt(lifeValue[i]);
							gameFrame.userLifeSet(Integer.parseInt(data[2]), Integer.parseInt(data[3]), life);
							break;
						case "ī�弳��":
							String[] card = data[4].split(",");
							int[] cards = new int[card.length];
							for(int i=0; i<card.length; i++) cards[i]=Integer.parseInt(card[i]);
							gameFrame.userCardNumSet(Integer.parseInt(data[2]), Integer.parseInt(data[3]), cards);
							int[] startAniCards = startAniCardsSet(Integer.parseInt(data[2]), Integer.parseInt(data[3]), cards);
							gameFrame.ani.startAnimation(startAniCards);
							break;
						case "��������":
							card = data[2].split("/");
							gameFrame.tombSet(card);
							break;
						case "��������":
							if(data[2].equals("�߰�")) {
								card = data[4].split("/");
								MOCCard mocCard = new MOCCard(130, 200, card[0], card[1], card[2], Integer.parseInt(card[3]));
								mocCard.imageSet();
								gameFrame.users[Integer.parseInt(data[3])].mountingPanel.addMounting(mocCard);
							}else gameFrame.users[Integer.parseInt(data[3])].mountingPanel.removeMounting(data[4]);
							break;
						case "��ī��":
							card = data[2].split(",");
							((User.UserMyPanel)gameFrame.users[0]).myCardsSet(card);
							break;
						case "��ο�":
							int distance = Integer.parseInt(data[2]);
							if(gameFrame.users.length<=5) gameFrame.ani.cardDrawAnimation(distance+1, Integer.parseInt(data[3]));
							else gameFrame.ani.cardDrawAnimation(distance, Integer.parseInt(data[3]));
							gameFrame.users[distance].cardNumSet(gameFrame.users[distance].getCardNum()+Integer.parseInt(data[3]));
							break;
						case "����":
							gameFrame.myTurnSet(true);
							break;
						case "ī�峿":
							distance = Integer.parseInt(data[2]);
							if(distance!=0&&gameFrame.users.length<=5) gameFrame.ani.cattleRow(distance+1);
							else gameFrame.ani.cattleRow(distance);
							gameFrame.users[distance].cardNumSet(gameFrame.users[distance].getCardNum()-1);
							break;
						case "��":
							((UserMyPanel)gameFrame.users[0]).attackedSet(data[3],data[4],"��");
							break;
						case "Ĺ���ο�":
							SocketReceiver.writer.println(String.format(
									"����:ī������:%d:%s:%s:%s", myRoomId, data[3], data[4], ((User.UserMyPanel)gameFrame.users[0]).getMyAllCards()));
							break;
						case "ī������":
							gameFrame.goalCardsShow(Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5]);
							break;
						case "ī�����":
							((UserMyPanel)gameFrame.users[0]).removeMyCard(data[2]);
							break;
						case "ī�尳������":
							distance = Integer.parseInt(data[2]);
							int num = Integer.parseInt(data[3]);
//							if(num<0) {
//								for(int i=0; i<-num; i++) {
//									if(distance!=0&&gameFrame.users.length<=5) gameFrame.ani.cattleRow(distance+1);
//									else gameFrame.ani.cattleRow(distance);				
//								}
//							}
							gameFrame.users[distance].cardNumSet(gameFrame.users[distance].getCardNum()+num);
							break;
						case "�ִ�":
							if(data[2].equals("�����İ�����")) gameFrame.ani.bankAnimation();
							else if(data[2].equals("������")) gameFrame.ani.stageCoachAnimation();
							else if(data[2].equals("��")) {
								int caster = Integer.parseInt(data[3]);
								int goal =  Integer.parseInt(data[4]);
								if(gameFrame.users.length<=5) {
									if(caster!=0) caster += 1;
									if(goal!=0) goal += 1;
								}
								gameFrame.ani.bangAnimation(caster, goal, Boolean.valueOf(data[5]));
							}
							break;
						case "�߻�������":
							distance = Integer.parseInt(data[2]);
							gameFrame.users[distance].setDistance(gameFrame.users[distance].getDistance()+1);
							break;
						case "���������":
							distance = Integer.parseInt(data[2]);
							gameFrame.users[distance].lifeAddOrRemove(Integer.parseInt(data[3]));
							break;
						}
						break;
					case "�α�":
						GameFrame.logOutput.append("����:"+data[1]+"\n");
						break;
					case "����":
						if(data[1].equals("close")) {
							System.out.println("��������");
							System.exit(0);
						}
					}
				}
			} catch (IOException e) {
				System.out.println("���� ������");
				System.exit(0);
			}
		}
	}
	
	private int[] startAniCardsSet(int member, int startIndex, int[] cards) {
		int[] aniCards = new int[7];
		int index = startIndex;
		aniCards[0]=cards[index];
		index = (index+1)%member;
		for(int i=1; i<cards.length; i++) {
			if(member<6) aniCards[i+1]=cards[index];
			else aniCards[i]=cards[index];
			index = (index+1)%member;
		}
		return aniCards;
	}
}
