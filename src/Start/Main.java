package Start;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main extends JFrame{
	private JLayeredPane container;//패널들을 쌓아서 넣을 수 있게 기본 패널이될 JLayeredPane
	private JButton button;//시작화면의 버튼
	private JPanel background;//배경 패널
	
	public Main() {
		/* 메인프레임 
		 * layout이 default로 설정되있어서 패널들의 크기와 위치를 설정해주는게 바`람직함
		 */
		
		ImageIcon buttonImage = new ImageIcon("./image/button1.png");//ImageIcon객체로 버튼 이미지 받아옴
		button = new JButton(buttonImage);//버튼 객체 생성
		button.setContentAreaFilled(false);//버튼 내용 채우기 없음
		button.setBorderPainted(false);//버튼 배경선 없음
		button.setBounds(650, 600, buttonImage.getIconWidth(), buttonImage.getIconHeight());//버튼 이미지 크기만큼 650,600 위치에 셋팅
		
		button.addMouseListener(new MouseListener() {//마우스 이벤트 생성
			ImageIcon image;
			@Override
			public void mouseClicked(MouseEvent arg0) {//마우스 클릭했을때
				container.remove(button);//메인컨테이너에 버튼을 삭제
				
				String nick = "";
				while(nick.length()==0) {
					nick = (String) JOptionPane.showInputDialog(null,null,"닉네임 입력",JOptionPane.PLAIN_MESSAGE, null,null,null);//닉네임 설정 다이얼로그 
					if(nick==null) System.exit(0);//닉네임 입력 취소할 경우 시스템 종료
				}
				
				JScrollPane userList = new JScrollPane(new Room.UserListPanel());
				userList.setBounds(1150, 100, 350, 450);
				
				JScrollPane roomList = new JScrollPane(new Room.RoomListPanel());
				roomList.setBounds(100, 100, 1000, 450);
				
				JPanel chat = new Room.ChatPanel();
				
				container.add(chat,new Integer(1));
				container.add(roomList,new Integer(1));
				container.add(userList,new Integer(1));
					
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {//마우스가 버튼영역에 들어왔을때
			}

			@Override
			public void mouseExited(MouseEvent arg0) {//마우스가 버튼영역에 나갔을때
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {//마우스가 버튼을 눌렀을때
				image = new ImageIcon("./image/button2.png");//이미지를 다른이미지로불러와서
				button.setIcon(image);//이미지 설정
				button.setBounds(650, 610, image.getIconWidth(), image.getIconHeight());//위치 크기설정
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {//마우스가 버튼을 땠을때
				image = new ImageIcon("./image/button1.png");//처음 이미지로 다시불러오고
				button.setIcon(image);//이미지 설정
				button.setBounds(650, 600, image.getIconWidth(), image.getIconHeight());//위치 크기설정
			}
		});
		
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
	
}
