package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

public class GameFrame extends JFrame{
	private Dimension screen;
	private JLayeredPane container;
	private static final int MAXIMUM_NUM_OF_PEOPLE=7;

	public GameFrame() {
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
		
		JTextArea chatOutput = new JTextArea();//텍스트에이리어 객체생성
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
			chatOutput.append(input.getText()+"\n");//내용을 출력해주고
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
		container.add(chat);
		
		//로그패널 추가
		JPanel log = new JPanel();
		log.setBounds((int)screen.getWidth()/3*2-10, (int)screen.getHeight()/3*2-10 ,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		log.setLayout(new BorderLayout());
		log.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		JTextArea logOutput = new JTextArea();//텍스트에이리어 객체생성
		logOutput.setEditable(false);//텍스트에이리어에 입력못하게함
		JScrollPane logScroll = new JScrollPane(logOutput);//텍스트에이리어 스크롤 되기위한 JScrollPane 클래스
		
		JTextPane logTitle = new JTextPane();
		logTitle.setEditable(false);
		logTitle.setText("Log");
		logTitle.setParagraphAttributes(attribs, true);
		
		log.add(logTitle,"North");
		log.add(logScroll);
		container.add(log);
		
		
		
		Ani.AniPanel ani = new Ani.AniPanel(screen);
		container.add(ani,new Integer(1));
		
		add(container);
		setVisible(true);//프레임이 보일수있게
	}
	
	public void userSet(int member, int startIndex, String nicks) {
		System.out.println(member+" "+startIndex+" "+nicks);
		String[] nick = nicks.split(",");
		int index = startIndex;
		//유저패널 자리 세팅
		for(int i=0; i<member; i++) {
			User.UserPanel panel = new User.UserPanel(screen, nick[index]);
			if(i==0) panel.setLocation(760, 750);
			else if(i<5) panel.setLocation((int)screen.getWidth()/4*(i-1)+40, 20);
			else if(i==5) panel.setLocation(40,360);
			else panel.setLocation((int)screen.getWidth()/4*3+40 ,360);
			container.add(panel);
			index = (index+1)%member;
		}
		
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
	
