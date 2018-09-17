package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Character.CharacterSetting;

public class GameFrame extends JFrame{
	private Dimension screen;
	private JLayeredPane container;
	private final int MAXIMUM_NUM_OF_PEOPLE=7;
	
	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//메인프레임을 닫아서 쓰레드가 실행됨을 방지하기위해 설정해줘야하는 메소드
		setExtendedState(JFrame.MAXIMIZED_BOTH);//전체화면
		setUndecorated(true);//상태표시줄 지우기
		setResizable(false);//화면 사이즈 변경 불가
		
		container = new JLayeredPane() {
			@Override
			public void paintComponent(Graphics g) {//패널을 기본적으로 그리는 메소드를 오버라이드
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("./image/배경.jpg");//배경이미지 불러와서
				image = new ImageIcon(image.getImage().getScaledInstance((int)screen.getWidth(), (int)screen.getHeight(), Image.SCALE_SMOOTH));//크기 다시설정해주고
				g.drawImage(image.getImage(), 0, 0, null);//받아온 ImageIcon의 getImage로 받아서 이미지를 0,0 위치에 그린다
			}
		};//전체패널
		container.setLayout(null);
		
		screen = Toolkit.getDefaultToolkit().getScreenSize();//내 노트북 : 1920X1080
		
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
		
		Animation ani = new Animation(screen,7);
		container.add(ani,new Integer(1));
		
		add(container);
		setVisible(true);//프레임이 보일수있게
		
		ani.gameStart(7, 4,4,4,4,4,4,4);
//		ani.bang(1, 7, true);
	}
	
}
	
