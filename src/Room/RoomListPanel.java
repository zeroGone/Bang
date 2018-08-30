package Room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class RoomListPanel extends JPanel {
	private JPanel container;
	
	public RoomListPanel() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createTitledBorder("방목록"));
		container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		
		container.add(new RoomPanel(1,"첫번째 방",0));
		container.add(new RoomPanel(2,"두번째 방",0));
		container.add(new RoomPanel(3,"세번째 방",0));
		container.add(new RoomPanel(4,"허리존나아프네",0));
		container.add(new RoomPanel(5,"글짜가늘면 동시에커지냐?",5));
		
		add(container);
	}
	
	private class RoomPanel extends JPanel{
		private JTextArea roomNum;
		private JTextArea roomName;
		private JTextArea userNum;
		
		public RoomPanel(int num,String name,int userNum) {
			setPreferredSize(new Dimension(900,50));
			setLayout(new BorderLayout());
			setBorder(new EtchedBorder(EtchedBorder.RAISED));
			
			this.roomNum = new JTextArea(Integer.toString(num)+". ");
			this.roomNum.setFont(new Font(null,0,20));
			this.userNum = new JTextArea(Integer.toString(userNum)+"명");
			this.userNum.setFont(new Font(null,0,20));
			
			this.roomNum.setEditable(false);
			this.userNum.setEditable(false);

			
			roomName = new JTextArea(name);
			roomName.setFont(new Font(null,0,20));
			roomName.setEditable(false);
			
			add(this.roomName,"Center");
			add(this.userNum,"East");
			add(this.roomNum,"West");
		}
	}
}
