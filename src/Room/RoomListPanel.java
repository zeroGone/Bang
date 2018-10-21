package Room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class RoomListPanel extends JPanel {
	private JPanel container;
	
	public RoomListPanel() {
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createTitledBorder("����"));
		this.setBounds(100,100,1000,450);
		
		container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
//		container.add(new RoomPanel(1,"ù��° ��",0));
//		container.add(new RoomPanel(2,"�ι�° ��",0));
//		container.add(new RoomPanel(3,"����° ��",0));
//		container.add(new RoomPanel(4,"�㸮����������",0));
//		container.add(new RoomPanel(5,"��¥���ø� ���ÿ�Ŀ����?",5));
		
		this.add(container);
	}
	
	private class RoomPanel extends JPanel{
		private JTextArea roomNum;
		private JTextArea roomName;
		private JButton enter;
		
		public RoomPanel(int num,String name,int userNum) {
			setPreferredSize(new Dimension(900,50));
			setLayout(new BorderLayout());
			setBorder(new EtchedBorder(EtchedBorder.RAISED));
			
			this.roomNum = new JTextArea(Integer.toString(num)+". ");
			this.roomNum.setFont(new Font(null,0,20));
			
			this.roomNum.setEditable(false);
			
			roomName = new JTextArea(" ("+userNum+"/7)  "+name);
			roomName.setFont(new Font(null,0,20));
			roomName.setEditable(false);
			
			this.enter = new JButton("����");
			this.enter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new Game.GameFrame();
				}
			});
			
			add(this.roomName,"Center");
			add(this.enter,"East");
			add(this.roomNum,"West");
		}

	}
}
