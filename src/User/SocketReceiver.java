package User;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	private PrintWriter writer;
	private String nick;
	private int myRoomId;
	private GameFrame gameFrame;
	
	public SocketReceiver(Main main) throws IOException {
		this.main=main;
		Socket socket = new Socket("172.20.10.3",2018);
		writer = new PrintWriter(socket.getOutputStream(),true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		new Thread(this).start();
		myRoomId=-1;
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
				writer.println("�����:"+title+","+nick);
			}
		}
		else JOptionPane.showMessageDialog(null, "�ϳ��� �游 ���� ���", "Warning!", JOptionPane.WARNING_MESSAGE);
	}

	public void roomEnter(int id) {
		if(myRoomId==-1) writer.println("������:"+id+","+nick);
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
						gameFrame.addWindowListener(new WindowListener() {

							@Override
							public void windowActivated(WindowEvent arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowClosed(WindowEvent arg0) {
							}

							@Override
							public void windowClosing(WindowEvent e) {
								writer.println("�泪��:"+myRoomId);
								myRoomId = -1;
							}

							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowDeiconified(WindowEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowIconified(WindowEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub

							}

						});
						break;
					case "���̷�":
						myRoomId=Integer.parseInt(data[1]);
						break;
					case "����":
						data = data[2].split("/");
						gameFrame.userSet(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2].substring(1,data[2].length()-1));
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

}
