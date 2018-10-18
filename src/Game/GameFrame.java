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
		
//		button1.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				ani.startAnimation(5,3,0,4,4,0,4);
//			}
//		});
//		
		//람다식 
		button1.addActionListener(e -> ani.startAnimation(5,3,0,4,4,0,4));
		
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
		
		JButton button5 = new JButton("기관총");
		button5.addActionListener(e->ani.machineGunAnimation());
		
		JButton button6 = new JButton("역마차");
		button6.addActionListener(e->ani.stageCoachAnimation());
		
		JButton button7 = new JButton("웰스파고은행");
		button7.addActionListener(e->ani.bankAnimation());
		
		JButton button8 = new JButton("인디언");
		button8.addActionListener(e->ani.indianAnimation());

		JButton button9 = new JButton("다이너마이트");
		button9.addActionListener(e->ani.dynamiteAnimation(0,false));
		
		JButton button10 = new JButton("감옥");
		button10.addActionListener(e->ani.prisonAnimation(0));
		
		JButton button11 = new JButton("결투");
		button11.addActionListener(e->ani.fightAnimation());
		
		JButton button12 = new JButton("히트");
		button12.addActionListener(e->ani.hitAnimation(0));
		
		JButton button13 = new JButton("죽음");
		button13.addActionListener(e->ani.dieAnimation(0));
		
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);
		panel.add(button7);
		panel.add(button8);
		panel.add(button9);
		panel.add(button10);
		panel.add(button11);
		panel.add(button12);
		panel.add(button13);

		container.add(panel, new Integer(2));
		add(container);
		setVisible(true);//프레임이 보일수있게
		
	}
}
	
