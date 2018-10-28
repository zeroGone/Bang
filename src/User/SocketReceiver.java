package User;

import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JOptionPane;

import Game.GameFrame;
import Start.Main;

public class SocketReceiver implements Runnable{
	private Main main;
	private BufferedReader reader;
	private PrintWriter writer;
	private String nick;
	
	public SocketReceiver(Main main) throws IOException {
		this.main=main;
		Socket socket = new Socket("172.30.1.34",2018);
		writer = new PrintWriter(socket.getOutputStream(),true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		new Thread(this).start();
	}
	
	public void setNick(String nick) {
		this.nick=nick;
		writer.println("�г���:"+nick);
	}
	
	public void roomCreate() {
		String title = (String)JOptionPane.showInputDialog(main,null,"������ �Է�",JOptionPane.PLAIN_MESSAGE,null,null,null);
		if(title!=null) {
			if(title.length()==0) title="�̸����¹�";
			writer.println("�����:"+title);
		}
		new GameFrame();
	}
	
	public void roomEnter(String title) {
		writer.println("������:"+title+",nick");
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
					//�����κ��� ���� ������ �г��ӵ��� �� �޾Ƽ� �߰��� �� �ٽñ׸���.
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
					case "������":
						data = data[1].split(",");
						new Game.GameFrame();
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

}
