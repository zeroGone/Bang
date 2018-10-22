package Start;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import User.SocketReceiver;

public class Main extends JFrame implements MouseListener{
	private JLayeredPane container;//패널들을 쌓아서 넣을 수 있게 기본 패널이될 JLayeredPane
	private JPanel chat;//대기방 채팅패널
	private JPanel roomList;//대기방 리스트패널
	private JPanel userList;//대기방 유저리스트패널
	private JButton button;//시작화면의 버튼
	private JPanel background;//배경 패널
	private JTextArea output;//채팅패널 출력창
	private ImageIcon image;
	private SocketReceiver socket;
	
	public Main() {
		/* 메인프레임 
		 * layout이 default로 설정되있어서 패널들의 크기와 위치를 설정해주는게 바람직함
		 */
		
		//bgm 셋팅
		File bgm = new File("./audio/bgm.wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(bgm);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			System.out.println("오디어파일없음");
		}
		
		ImageIcon buttonImage = new ImageIcon("./image/button1.png");//ImageIcon객체로 버튼 이미지 받아옴
		button = new JButton(buttonImage);//버튼 객체 생성
		button.setContentAreaFilled(false);//버튼 내용 채우기 없음
		button.setBorderPainted(false);//버튼 배경선 없음
		button.setBounds(650, 600, buttonImage.getIconWidth(), buttonImage.getIconHeight());//버튼 이미지 크기만큼 650,600 위치에 셋팅
		
		button.addMouseListener(this);
		
		background = new JPanel() {//배경이 될 패널을 생성한다
			@Override
			public void paintComponent(Graphics g) {//패널을 기본적으로 그리는 메소드를 오버라이드
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("./image/illust.png");//배경이미지 불러와서
				image = new ImageIcon(image.getImage().getScaledInstance(1600, 1000, Image.SCALE_SMOOTH));//크기 다시설정해주고
				g.drawImage(image.getImage(), 0, 0, null);//받아온 ImageIcon의 getImage로 받아서 이미지를 0,0 위치에 그린다
			}
		};
		background.setBounds(0, 0, 1600, 1000);//패널 크기 위치 설정
		
		container= new JLayeredPane();//메인 컨테이너 생성
		container.add(button,1);//button 추가 뒤에 인덱스 파라미터가 클수록 위에 쌓임
		container.add(background);//배경 패널 추가 인덱스를 설정안해주면 default로  0, 제일 밑에 쌓임

		add(container);//프레임에 메인 컨테이너 추가
		
		setTitle("Bang");//타이틀설정
		setSize(1600,1000);//크기설정
		setLocationRelativeTo(null);//화면 중앙에 배치
		setResizable(false);//화면 사이즈 변경 불가
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//메인프레임을 닫아서 쓰레드가 실행됨을 방지하기위해 설정해줘야하는 메소드
		setVisible(true);//프레임이 보일수있게
		
	}
	
	public static void main(String[] args) {
		new Main();
//		new Game.GameFrame();
	}

	//대기방에서 유저리스트에 유저를 추가하는 메소드
	public void userAdd(String[] nameData) {
		userList.removeAll();
		for(int i=0; i<nameData.length; i++) {
			JPanel user = new JPanel();//유저패널 하나만들어서 
			//패널 셋팅
			user.setLayout(new BorderLayout());
			user.setBorder(new BevelBorder(BevelBorder.RAISED));
			//유저 이미지
			ImageIcon userImage=new ImageIcon("./image/user.png");
			userImage=new ImageIcon(userImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			//유저 이름은 서버로 부터 받아온 name으로 설정
			JTextArea userName= new JTextArea(nameData[i]);
			userName.setFont(new Font(null,0,25));
			userName.setEditable(false);
			
			user.add(new JLabel(userImage),"West");
			user.add(userName);
			
			userList.add(user);
		}
		this.revalidate();
	}
	//대기방에서 방리스트에 방들을 추가하는 메소드
	public void roomAdd(String[] roomData) {//서버로부터 받아온 룸리스트 데이터를 받아옴
		for(int i=0; i<roomData.length; i++) {
			roomData= roomData[i].split("/");//방:제목/이름 을 /로 나눈다
			JPanel room = new JPanel();//방패널
			//방패널 셋팅
			room.setPreferredSize(new Dimension(900,50));
			room.setLayout(new BorderLayout());
			room.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			//방번호
			JTextArea roomNum = new JTextArea(Integer.toString(i+1)+". ");
			roomNum.setFont(new Font(null,0,20));
			roomNum.setEditable(false);
			//방이름 ex)인원수/7 방이름 
			final JTextArea roomName = new JTextArea(" ("+roomData[1]+"/7)  "+roomData[0]);
			roomName.setFont(new Font(null,0,20));
			roomName.setEditable(false);
			//방입장 버튼
			JButton enter = new JButton("입장");
			enter.addActionListener(e->socket.roomEnter(roomName.getText().split("  ")[1]));
			
			room.add(roomName,"Center");
			room.add(enter,"East");
			room.add(roomNum,"West");
			roomList.add(room);
		}
		JButton create = new JButton("방생성");
		roomList.add(create);
		this.revalidate();
	}
	
	public void roomChatting(String 내용) {
		output.append(내용+"\n");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			String nick = (String)JOptionPane.showInputDialog(this,null,"닉네임 입력",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if(nick!=null) {
				//대기방 리스트 패널 설정
				JPanel roomListPanel = new JPanel();
				roomListPanel.setBackground(Color.white);
				roomListPanel.setBorder(BorderFactory.createTitledBorder("방목록"));
				roomListPanel.setBounds(100,100,1000,450);
				roomList = new JPanel();
				roomList.setLayout(new BoxLayout(roomList,BoxLayout.Y_AXIS));
				roomListPanel.add(roomList);

				//대기방 유저리스트 패널 설정
				JPanel userListPanel = new JPanel();
				userListPanel.setBorder(BorderFactory.createTitledBorder("유저목록"));
				userListPanel.setBackground(Color.WHITE);
				userListPanel.setBounds(1150, 100, 350, 450);
				userList = new JPanel();
				userListPanel.add(userList);
				userList.setLayout(new BoxLayout(userList,BoxLayout.Y_AXIS));

				//유저 닉넴 설정
				if(nick.length()==0) nick = "Unknown";
				User.SocketReceiver user = new User.SocketReceiver(this);
				user.setNick(nick);
				container.remove(button);

				//대기방 채팅패널 설정
				chat = new JPanel();
				chat.setLayout(new BorderLayout());//레이아웃 BorderLayout으로 지정
				chat.setBounds(100, 600, 1400, 300);//크기 위치 지정
				chat.setBackground(Color.WHITE);//배경색 지정
				chat.setOpaque(false);
				output = new JTextArea();//텍스트에이리어 객체생성
				output.setEditable(false);//텍스트에이리어에 입력못하게함
				JTextField input = new JTextField();//텍스트필드 객체생성
				JScrollPane scroll = new JScrollPane(output);//텍스트에이리어 스크롤 되기위한 JScrollPane 클래스
				input.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						user.chatting(input.getText().toString());
						input.setText("");
					}
				});
				
				chat.add(scroll);
				chat.add(input, "South");//입력필드를 남쪽에 추가

				container.add(roomListPanel,new Integer(1));
				container.add(userListPanel,new Integer(1));
				container.add(chat,new Integer(1));
				
				this.revalidate();
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, " 서버가 열려있지 않았습니다.", "에러", JOptionPane.ERROR_MESSAGE);
		}
	}

	//버튼 영역 들어갈시
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	//버튼 영역 나갈시
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {//마우스가 버튼을 눌렀을때
		image = new ImageIcon("./image/button2.png");//이미지를 다른이미지로불러와서
		button.setIcon(image);//이미지 설정
		button.setBounds(650, 610, image.getIconWidth(), image.getIconHeight());//위치 크기설정
	}

	@Override
	public void mouseReleased(MouseEvent e) {//마우스가 버튼을 땠을때
		image = new ImageIcon("./image/button1.png");//처음 이미지로 다시불러오고
		button.setIcon(image);//이미지 설정
		button.setBounds(650, 600, image.getIconWidth(), image.getIconHeight());//위치 크기설정
	}
	
}
