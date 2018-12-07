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
		writer.println("닉네임:"+nick);
	}
	
	public void roomCreate() {
		if(myRoomId==-1) {
			String title = (String)JOptionPane.showInputDialog(main,null,"방제목 입력",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if(title!=null) {
				if(title.length()==0) title="이름없는방";
				writer.println("방:생성:"+title+","+nick);
			}
		}
		else JOptionPane.showMessageDialog(null, "하나의 방만 접속 허용", "Warning!", JOptionPane.WARNING_MESSAGE);
	}

	public void roomEnter(int id) {
		if(myRoomId==-1) writer.println("방:입장:"+id+","+nick);
		else JOptionPane.showMessageDialog(null, "하나의 방만 접속 허용", "Warning!", JOptionPane.WARNING_MESSAGE);
	}

	public void chatting(String 내용) {
		writer.println("방채팅:"+nick+","+내용);
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
					case "닉네임":
						data = data[1].split(",");
						main.userAdd(data);
						break;
					case "방":
						data[1]=data[1].substring(1, data[1].length()-1);
						if(data[1].equals("")) data = new String[0];
						else data = data[1].split(",");
						main.roomAdd(data);
						break;
					case "방채팅":
						data = data[1].split(",");
						main.roomChatting(data[0]+":"+data[1]);
						break;
					case "방생성":
						int id = Integer.parseInt(data[1]);
						myRoomId = id;
						gameFrame = new Game.GameFrame();
						gameFrame.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent e) {
								writer.println("게임:나감:"+myRoomId);
								myRoomId = -1;
							}
						});
						break;
					case "풀방":
						JOptionPane.showMessageDialog(null, "풀방인거임~", "Warning!", JOptionPane.WARNING_MESSAGE);
						break;
					case "게임진행중":
						JOptionPane.showMessageDialog(null, "게임진행중인거임~!", "Warning!", JOptionPane.WARNING_MESSAGE);
						break;
					case "마이룸":
						myRoomId=Integer.parseInt(data[1]);
						break;
					case "게임":
						switch(data[1]) {
						case "채팅":
							GameFrame.chatOutput.append(data[2]+":"+data[3]+"\n");
							break;
						case "방장준비":
							gameFrame.gameReady();
							break;
						case "유저":
							data = data[2].split("/");
							gameFrame.userSet(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]);
							break;
						case "케릭설정":
							data = data[2].split("/");
							gameFrame.userCharacterSet(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2].split(","));
							break;
						case "보안관설정":
							gameFrame.보안관Set(Integer.parseInt(data[2]));
							break;
						case "직업설정":
							gameFrame.myJobSet(data[2]);
							break;
						case "생명설정":
							String[] lifeValue = data[4].split(",");
							int[] life = new int[lifeValue.length];
							for(int i=0; i<lifeValue.length; i++) life[i]=Integer.parseInt(lifeValue[i]);
							gameFrame.userLifeSet(Integer.parseInt(data[2]), Integer.parseInt(data[3]), life);
							break;
						case "카드설정":
							String[] card = data[4].split(",");
							int[] cards = new int[card.length];
							for(int i=0; i<card.length; i++) cards[i]=Integer.parseInt(card[i]);
							gameFrame.userCardNumSet(Integer.parseInt(data[2]), Integer.parseInt(data[3]), cards);
							int[] startAniCards = startAniCardsSet(Integer.parseInt(data[2]), Integer.parseInt(data[3]), cards);
							gameFrame.ani.startAnimation(startAniCards);
							break;
						case "무덤설정":
							card = data[2].split("/");
							gameFrame.tombSet(card);
							break;
						case "장착설정":
							card = data[3].split("/");
							MOCCard mocCard = new MOCCard(130, 200, card[0], card[1], card[2], Integer.parseInt(card[3]));
							mocCard.imageSet();
							gameFrame.users[Integer.parseInt(data[2])].mountingPanel.addMounting(mocCard);
							break;
						case "내카드":
							card = data[2].split(",");
							((User.UserMyPanel)gameFrame.users[0]).myCardsSet(card);
							break;
						case "드로우":
							int distance = Integer.parseInt(data[2]);
							if(gameFrame.users.length<=5) gameFrame.ani.cardDrawAnimation(distance+1, Integer.parseInt(data[3]));
							else gameFrame.ani.cardDrawAnimation(distance, Integer.parseInt(data[3]));
							gameFrame.users[distance].cardNumSet(gameFrame.users[distance].getCardNum()+Integer.parseInt(data[3]));
							break;
						case "내턴":
							gameFrame.myTurnSet(true);
							break;
						case "카드냄":
							distance = Integer.parseInt(data[2]);
							if(distance!=0&&gameFrame.users.length<=5) gameFrame.ani.cattleRow(distance+1);
							else gameFrame.ani.cattleRow(distance);
							gameFrame.users[distance].cardNumSet(gameFrame.users[distance].getCardNum()-1);
							break;
						case "캣벌로우":
							SocketReceiver.writer.println(String.format(
									"게임:카드정보:%d:%s:%s:%s", myRoomId, data[3], data[4], ((User.UserMyPanel)gameFrame.users[0]).getMyAllCards()));
							break;
						case "카드정보":
							gameFrame.goalCardsShow(Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5]);
							break;
						case "카드삭제":
							((UserMyPanel)gameFrame.users[0]).removeMyCard(data[2]);
							break;
						case "카드개수설정":
							distance = Integer.parseInt(data[2]);
							int num = Integer.parseInt(data[3]);
							if(num<0) {
								for(int i=0; i<-num; i++) {
									if(distance!=0&&gameFrame.users.length<=5) gameFrame.ani.cattleRow(distance+1);
									else gameFrame.ani.cattleRow(distance);				
								}
							}
							gameFrame.users[distance].cardNumSet(gameFrame.users[distance].getCardNum()+num);
							break;
						}
						break;
					case "로그":
						GameFrame.logOutput.append("서버:"+data[1]+"\n");
						break;
					case "서버":
						if(data[1].equals("close")) {
							System.out.println("서버닫힘");
							System.exit(0);
						}
					}
				}
			} catch (IOException e) {
				System.out.println("서버 강종함");
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
