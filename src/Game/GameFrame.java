package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameFrame extends JFrame{
	private Dimension screen;
	private JLayeredPane container;
	private static final int MAXIMUM_NUM_OF_PEOPLE=7;

	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//메인프레임을 닫아서 쓰레드가 실행됨을 방지하기위해 설정해줘야하는 메소드
		setExtendedState(JFrame.MAXIMIZED_BOTH);//전체화면
		setUndecorated(true);//상태표시줄 지우기
		setResizable(false);//화면 사이즈 변경 불가
		screen = Toolkit.getDefaultToolkit().getScreenSize();//내 노트북 : 1920X1080

		container = new JLayeredPane();
//			@Override
//			public void paintComponent(Graphics g) {//패널을 기본적으로 그리는 메소드를 오버라이드
//				super.paintComponent(g);
//				g.setColor(new Color(0x964B00));
//				int radius = 10;
//				int step = 4;
//				int forLen = (radius / step);
//				for (int i = 0; i < forLen; i++)
//				    g.fillOval(radius - i * step, radius - i * step, i * step * 2, i * step * 2);
//				try {
//					BufferedImage image = ImageIO.read(new File("./image/배경.jpg"));
//					g.drawImage(image, 0, 0, null);//받아온 ImageIcon의 getImage로 받아서 이미지를 0,0 위치에 그린다
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				setOpaque(false);
//				ImageIcon image = new ImageIcon("./image/배경.jpg");//배경이미지 불러와서
//				image = new ImageIcon(image.getImage().getScaledInstance((int)screen.getWidth(), (int)screen.getHeight(), Image.SCALE_SMOOTH));//크기 다시설정해주고
//			}
//		};//전체패널
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setLayout(null);
		
		//채팅패널 추가
		Chat.ChatPanel chatPanel = new Chat.ChatPanel();
		chatPanel.setBounds(10, (int)screen.getHeight()/3*2-10,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		container.add(chatPanel);
		
		//로그패널 추가
		Chat.LogPanel logPanel = new Chat.LogPanel();
		logPanel.setBounds((int)screen.getWidth()/3*2-10, (int)screen.getHeight()/3*2-10 ,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		container.add(logPanel);
		
		//유저패널 자리 세팅
		for(int i=0; i<MAXIMUM_NUM_OF_PEOPLE; i++) {
			User.UserPanel panel = new User.UserPanel(screen);
			if(i==0) panel.setLocation(760, 750);
			else if(i<5) panel.setLocation((int)screen.getWidth()/4*(i-1)+40, 20);
			else if(i==5) panel.setLocation(40,360);
			else panel.setLocation((int)screen.getWidth()/4*3+40 ,360);
			container.add(panel);
		}
		
		Ani.AniPanel ani = new Ani.AniPanel(screen);
		container.add(ani,new Integer(1));
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());
		panel.setOpaque(false);
		JButton button1 = new JButton("시작");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ani.startAnimation(5,3,0,4,4,0,4);
			}
		});
		
		JButton button2 = new JButton("뱅");
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ani.bangAnimation(1, 2, true);
			}
		});
		
		JButton button3 = new JButton("맥주");
		button3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ani.beerAnimation(1);
			}
		});
		
		JButton button4 = new JButton("강탈");
		button4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ani.takeAnimation(1, 2);
			}
		});
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		container.add(panel, new Integer(2));
		add(container);
		setVisible(true);//프레임이 보일수있게
		
	}
}
	
