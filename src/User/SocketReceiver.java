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
	
	public SocketReceiver(Main main) throws IOException {
		this.main=main;
		Socket socket = new Socket("14.37.11.158",2018);
		writer = new PrintWriter(socket.getOutputStream(),true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		new Thread(this).start();
	}
	
	public void setNick(String nick) {
		writer.println("�г���:"+nick);
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
						main.userAdd(data[1]);
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
