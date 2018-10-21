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
		this.setBorder(BorderFactory.createTitledBorder("유저목록"));
		this.setBackground(Color.WHITE);
		this.setBounds(1150, 100, 350, 450);
		this.main=main;
		
		container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
//		container.add(new UserPanel("김영곤"));
//		container.add(new UserPanel("정형일"));
//		container.add(new UserPanel("전승익"));
//		container.add(new UserPanel("정기혁"));
//		container.add(new UserPanel("이호석"));
//		container.add(new UserPanel("오일권"));
//		container.add(new UserPanel("황현기"));
//		container.add(new UserPanel("허수진"));
//		container.add(new UserPanel("박소정"));
//		container.add(new UserPanel("등등"));
//		container.add(new UserPanel("또누가있냐"));
//		container.add(new UserPanel("하기싫군"));
//		container.add(new UserPanel("별것도아닌데"));
//		container.add(new UserPanel("개힘드네"));
		
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
