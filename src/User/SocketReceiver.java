package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Start.Main;

public class SocketReceiver implements Runnable{
	private Main main;
	private BufferedReader reader;
	private PrintWriter writer;
	private String nick;
	
	public SocketReceiver(Main main) throws IOException {
		this.main=main;
		Socket socket = new Socket("172.30.1.56",2018);
		writer = new PrintWriter(socket.getOutputStream(),true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		new Thread(this).start();
	}
	
	public void setNick(String nick) {
		this.nick=nick;
		writer.println("닉네임:"+nick);
	}
	
	public void roomEnter(String title) {
		writer.println("방입장:"+title+",nick");
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
					//서버로부터 받은 접속한 닉네임들을 다 받아서 추가한 후 다시그린다.
					case "닉네임":
						data = data[1].split(",");
						main.userAdd(data);
						break;
					case "방":
						data = data[1].split(",");
						main.roomAdd(data);
						break;
					case "방채팅":
						data = data[1].split(",");
						main.roomChatting(data[0]+":"+data[1]);
						break;
					case "방입장":
						data = data[1].split(",");
						new Game.GameFrame();
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
