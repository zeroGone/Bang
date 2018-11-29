package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import User.SocketReceiver;
import User.UserPanel;

public class GameFrame extends JFrame{
	public static Dimension screen;
	private JLayeredPane container;
	
	public AniPanel ani;
	
	private JPanel userPanel;
	public UserPanel[] users;//각 유저들의 패널
	
	private JPanel controller;//유저의 선택을 다룰 패널
	
	public static JTextArea chatOutput;//게임 채팅패널
	public static JTextArea logOutput;//게임 로그패널

	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);//전체화면
		setUndecorated(true);//상태표시줄 지우기
		setResizable(false);//화면 사이즈 변경 불가
		screen = Toolkit.getDefaultToolkit().getScreenSize();//내 노트북 : 1920X1080
		container = new JLayeredPane();
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setLayout(null);
		
		//채팅패널 추가
		JPanel chat = new JPanel();
		chat.setBounds(10, (int)screen.getHeight()/3*2-10,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		chat.setLayout(new BorderLayout());
		chat.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		chatOutput = new JTextArea();//텍스트에이리어 객체생성
		chatOutput.setEditable(false);//텍스트에이리어에 입력못하게함
		JScrollPane chatScroll = new JScrollPane(chatOutput);//텍스트에이리어 스크롤 되기위한 JScrollPane 클래스
		
		JTextField input = new JTextField();//텍스트필드 객체생성
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length()>=20) ke.consume();
			}
		});
		
		input.addActionListener(e->{
			SocketReceiver.writer.println(String.format("게임:채팅:%d:%s", SocketReceiver.myRoomId, input.getText()));
			input.setText("");//입력을 초기화해줌
		});

		JTextPane chatTitle = new JTextPane();
		chatTitle.setEditable(false);
		chatTitle.setText("채팅창");

		//가운데정렬
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		chatTitle.setParagraphAttributes(attribs, true);

		chat.add(chatTitle,"North");
		chat.add(chatScroll);
		chat.add(input, "South");//입력필드를 남쪽에 추가
		container.add(chat, new Integer(5));
		
		//로그패널 추가
		JPanel log = new JPanel();
		log.setBounds((int)screen.getWidth()/3*2-10, (int)screen.getHeight()/3*2-10 ,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		log.setLayout(new BorderLayout());
		log.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		logOutput = new JTextArea();//텍스트에이리어 객체생성
		logOutput.setEditable(false);//텍스트에이리어에 입력못하게함
		JScrollPane logScroll = new JScrollPane(logOutput);//텍스트에이리어 스크롤 되기위한 JScrollPane 클래스
		
		JTextPane logTitle = new JTextPane();
		logTitle.setEditable(false);
		logTitle.setText("Log");
		logTitle.setParagraphAttributes(attribs, true);
		
		log.add(logTitle,"North");
		log.add(logScroll);
		container.add(log, new Integer(5));
		
		controller = new JPanel();
		controller.setBackground(Color.white);
		controller.setBounds(520, 360, 880, 300);
		controller.setOpaque(false);
		controller.setLayout(null);
		container.add(controller, new Integer(5));
		
		userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setOpaque(false);
		userPanel.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());
		container.add(userPanel, new Integer(1));
		
		ani = new AniPanel();
		container.add(ani,new Integer(2));
		
		add(container);
		
		setVisible(true);//프레임이 보일수있게
	}
	
	//유저 패널 셋팅
	public void userSet(int member, int startIndex, String nicks) {
		userPanel.removeAll();
		users = new UserPanel[member];
		userPanelSet(member, 0, startIndex, nicks.split(","));
		userPanel.repaint();
		this.revalidate();
	}
	
	//유저 패널 셋팅 재귀로 구현
	private void userPanelSet(int member, int count, int index, String[] nick) {
		if(count>=member) return;
		User.UserPanel panel = new User.UserPanel(screen, nick[index]);
		if(count==0) panel.setLocation(760, 750);
		else if(member<=5) panel.setLocation((int)screen.getWidth()/4*(count-1)+40, 20);
		else if(count==1) panel.setLocation(40, 360);
		else if(count==6) panel.setLocation((int)screen.getWidth()/4*3+40 ,360);
		else panel.setLocation((int)screen.getWidth()/4*(count-2)+40, 20);
		users[count] = panel;
		userPanel.add(panel);
		userPanelSet(member, ++count, (index+1)%member, nick);
	}
	
	//유저 패널 생명 셋팅
	public void userLifeSet(int member, int startIndex, int... life) {
		int index = startIndex;
		for(int i=0; i<life.length; i++) {
			users[i].lifeSet(life[index]);
			index = (index+1)%member;
		}
	}
	
	public void userCharacterSet(int member, int startIndex, String... character) {
		int index = startIndex;
		for(int i=0; i<character.length; i++) {
			users[i].characterSet(character[index]);
			index = (index+1)%member;
		}
	}
	
	public void userCardNumSet(int member, int startIndex, int... cards) {
		int index = startIndex;
		for(int i=0; i<cards.length; i++) {
			users[i].consumeSet(cards[index]);
			index = (index+1)%member;
		}
		users[0].myConsumeSet();
	}
	
	public void 보안관Set(int 거리) {
		users[거리].보안관Set();
	}
	
	public void myJobSet(String job) {
		users[0].jobSet(job);
	}
	
	public void userCardShow(String[] cards) {
		users[0].myConsumeShow(cards);
	}
	
	//방장 게임시작버튼
	public void gameReady() {
		ImageIcon buttonImage = new ImageIcon(getClass().getClassLoader().getResource("image/button1.png"));//ImageIcon객체로 버튼 이미지 받아옴
		JButton button = new JButton(buttonImage); 
		button.setContentAreaFilled(false);//버튼 내용 채우기 없음
		button.setBorderPainted(false);//버튼 배경선 없음
		button.setBounds(controller.getWidth()/2-buttonImage.getIconWidth()/2, controller.getHeight()/2-buttonImage.getIconHeight()/2, buttonImage.getIconWidth(), buttonImage.getIconHeight());//버튼 이미지 크기만큼 650,600 위치에 셋팅
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//게임시작
				//애니메이션 패널추가하고
				//서버로 자기방 시작한다고 보냄
				controller.remove(button);
//				SocketReceiver.writer.println("게임:시작:"+SocketReceiver.myRoomId);
//				userCharacterSet(4,0,"바트캐시디","슬랩더킬러","로즈둘란","럭키듀크");
//				보안관Set(1);
//				myJobSet("무법자");
//				userCardNumSet(4,0,4,4,4,5);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon buttonImage = new ImageIcon(getClass().getClassLoader().getResource("image/button2.png"));//이미지를 다른이미지로불러와서
				button.setIcon(buttonImage);//이미지 설정
				button.setBounds(controller.getWidth()/2-buttonImage.getIconWidth()/2, controller.getHeight()/2-buttonImage.getIconHeight()/2, buttonImage.getIconWidth(), buttonImage.getIconHeight());//버튼 이미지 크기만큼 650,600 위치에 셋팅
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon buttonImage = new ImageIcon(getClass().getClassLoader().getResource("image/button1.png"));//이미지를 다른이미지로불러와서
				button.setIcon(buttonImage);//이미지 설정
				button.setBounds(controller.getWidth()/2-buttonImage.getIconWidth()/2, controller.getHeight()/2-buttonImage.getIconHeight()/2, buttonImage.getIconWidth(), buttonImage.getIconHeight());//버튼 이미지 크기만큼 650,600 위치에 셋팅
			}
		});
				
		controller.add(button);
		controller.repaint();
		this.revalidate();
	}
}

/*
 * 
 * JPanel panel = new JPanel();
		panel.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());
		panel.setOpaque(false);
JButton button1 = new JButton("시작");
button1.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ani.startAnimation(5,3,0,4,4,0,4);
	}
});

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
button9.addActionListener(e->ani.dynamiteAnimation(0,true));

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

*/
	
