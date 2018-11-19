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
							break;
						case "보안관설정":
							break;
						case "직업설정":
							break;
						case "생명설정":
							break;
						case "카드설정":
							break;
						}
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
}
