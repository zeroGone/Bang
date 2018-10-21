package Room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class UserListPanel extends JPanel{
	private static JPanel container;
	private static Start.Main main;
	
	public UserListPanel(Start.Main main) {
		this.setBorder(BorderFactory.createTitledBorder("�������"));
		this.setBackground(Color.WHITE);
		this.setBounds(1150, 100, 350, 450);
		this.main=main;
		
		container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
//		container.add(new UserPanel("�迵��"));
//		container.add(new UserPanel("������"));
//		container.add(new UserPanel("������"));
//		container.add(new UserPanel("������"));
//		container.add(new UserPanel("��ȣ��"));
//		container.add(new UserPanel("���ϱ�"));
//		container.add(new UserPanel("Ȳ����"));
//		container.add(new UserPanel("�����"));
//		container.add(new UserPanel("�ڼ���"));
//		container.add(new UserPanel("���"));
//		container.add(new UserPanel("�Ǵ����ֳ�"));
//		container.add(new UserPanel("�ϱ�ȱ�"));
//		container.add(new UserPanel("���͵��ƴѵ�"));
//		container.add(new UserPanel("�������"));
		
		this.add(container);
	}
	
	public static void userAdd(String name) {
		container.add(new UserPanel(name));
		
	}
	
	private static class UserPanel extends JPanel{
		private ImageIcon userImage;
		private JTextArea userName;
		
		public UserPanel(String name) {
			this.setLayout(new BorderLayout());
			this.setBorder(new BevelBorder(BevelBorder.RAISED));
			
			userImage=new ImageIcon("./image/user.png");
			userImage=new ImageIcon(userImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			
			userName= new JTextArea(name);
			userName.setFont(new Font(null,0,25));
			userName.setEditable(false);
			
			add(new JLabel(userImage),"West");
			add(userName);
		}
	}
}
