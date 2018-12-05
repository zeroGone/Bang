package Start;

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
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
	private JLayeredPane container;//�гε��� �׾Ƽ� ���� �� �ְ� �⺻ �г��̵� JLayeredPane
	private JPanel chat;//���� ä���г�
	private JPanel roomList;//���� ����Ʈ�г�
	private JPanel userList;//���� ��������Ʈ�г�
	private JButton button;//����ȭ���� ��ư
	private JPanel background;//��� �г�
	private JTextArea output;//ä���г� ���â
	private ImageIcon image;
	private SocketReceiver mySocket;
	public Main() {
		/* ���������� 
		 * layout�� default�� �������־ �гε��� ũ��� ��ġ�� �������ִ°� �ٶ�����
		 */
		//bgm ����
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("audio/bgm.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			System.out.println("��������Ͼ���");
		}
		
		ImageIcon buttonImage = new ImageIcon(getClass().getClassLoader().getResource("image/button1.png"));//ImageIcon��ü�� ��ư �̹��� �޾ƿ�
		button = new JButton(buttonImage);//��ư ��ü ����
		button.setContentAreaFilled(false);//��ư ���� ä��� ����
		button.setBorderPainted(false);//��ư ��漱 ����
		button.setBounds(650, 600, buttonImage.getIconWidth(), buttonImage.getIconHeight());//��ư �̹��� ũ�⸸ŭ 650,600 ��ġ�� ����
		
		button.addMouseListener(this);
		
		background = new JPanel() {//����� �� �г��� �����Ѵ�
			@Override
			public void paintComponent(Graphics g) {//�г��� �⺻������ �׸��� �޼ҵ带 �������̵�
				super.paintComponent(g);
				ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/illust.PNG"));//����̹��� �ҷ��ͼ�
				image = new ImageIcon(image.getImage().getScaledInstance(1600, 1000, Image.SCALE_SMOOTH));//ũ�� �ٽü������ְ�
				g.drawImage(image.getImage(), 0, 0, null);//�޾ƿ� ImageIcon�� getImage�� �޾Ƽ� �̹����� 0,0 ��ġ�� �׸���
			}
		};
		background.setBounds(0, 0, 1600, 1000);//�г� ũ�� ��ġ ����
		
		container= new JLayeredPane();//���� �����̳� ����
		container.add(button,1);//button �߰� �ڿ� �ε��� �Ķ���Ͱ� Ŭ���� ���� ����
		container.add(background);//��� �г� �߰� �ε����� ���������ָ� default��  0, ���� �ؿ� ����

		add(container);//�����ӿ� ���� �����̳� �߰�
		
		setTitle("Bang");//Ÿ��Ʋ����
		setSize(1600,1000);//ũ�⼳��
		setLocationRelativeTo(null);//ȭ�� �߾ӿ� ��ġ
		setResizable(false);//ȭ�� ������ ���� �Ұ�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//������������ �ݾƼ� �����尡 ������� �����ϱ����� ����������ϴ� �޼ҵ�
		setVisible(true);//�������� ���ϼ��ְ�
	}
	
	//����
	public static void main(String[] args) {
		new Main();
//		Game.GameFrame gameFrame = new Game.GameFrame();
//		gameFrame.userSet(7, 0, "�迵��,������,ȫ�ؼ�,���ϱ�,������,������,�ڼ���");
//		gameFrame.userLifeSet(7, 0, 4,4,4,4,3,4,5);
//		gameFrame.userCharacterSet(7, 0, "�����׷�", "��Ʈĳ�õ�", "��Ű��ũ", "����Ѷ�", "����", "���Ļ�", "������ų��");
//		gameFrame.userCardNumSet(7, 0, 4,4,4,4,3,4,5);
//		gameFrame.���Ȱ�Set(3);
//		((User.UserMyPanel)gameFrame.users[0]).myCardsSet("consume/������/���̾�/9","consume/��Ż/�����̵�/1","mount/����/��Ʈ/3","consume/�ε��/Ŭ�ι�/12");
//		gameFrame.myJobSet("������");
//		gameFrame.userDieSet(2, "�����");
//		gameFrame.ani.cardOpenAnimation("consume","������","���̾�","9");
//		gameFrame.users[1].aliveCheck=false;
//		gameFrame.users[6].aliveCheck=false;
//		gameFrame.users[4].aliveCheck=false;
//		gameFrame.users[2].aliveCheck=false;
//		gameFrame.users[5].aliveCheck=false;

//		gameFrame.myTurnSet(true);
//		gameFrame.gameEnd("���Ȱ�");
//		gameFrame.myTurnSet(false);
//		((User.UserMyPanel)gameFrame.users[0]).attackedSet("��");
//		gameFrame.ani.cattleRow(6);
//		gameFrame.ani.dieAnimation(2);
//		gameFrame.ani.fightAnimation();
//		gameFrame.ani.prisonAnimation(0);
//		gameFrame.ani.dynamiteAnimation(0, true);
//		gameFrame.ani.indianAnimation();
//		gameFrame.ani.bankAnimation();
//		gameFrame.ani.machineGunAnimation();
//		gameFrame.ani.stageCoachAnimation();
//		gameFrame.ani.takeAnimation(0, 2);
//		gameFrame.ani.beerAnimation(2);
//		gameFrame.ani.bangAnimation(2, 1, false);
//		gameFrame.ani.cardDrawAnimation(1, 2);
//		gameFrame.ani.cardDrawAnimation(2, 2);
//		gameFrame.ani.cardDrawAnimation(3, 2);
//		gameFrame.ani.cardDrawAnimation(4, 2);
//		gameFrame.ani.cardDrawAnimation(5, 2);
//		gameFrame.ani.cardDrawAnimation(6, 2);
//		gameFrame.ani.cardDrawAnimation(0, 2);
//		gameFrame.ani.startAnimation(5,4,4,4,3,2,1);
//		gameFrame.gameReady();
	}

	//���濡�� ��������Ʈ�� ������ �߰��ϴ� �޼ҵ�
	public void userAdd(String[] nameData) {
		userList.removeAll();
		for(int i=0; i<nameData.length; i++) {
			JPanel user = new JPanel();//�����г� �ϳ����� 
			//�г� ����
			user.setLayout(new BorderLayout());
			user.setBorder(new BevelBorder(BevelBorder.RAISED));
			//���� �̹���
			ImageIcon userImage=new ImageIcon(getClass().getClassLoader().getResource("image/user.png"));
			userImage=new ImageIcon(userImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			//���� �̸��� ������ ���� �޾ƿ� name���� ����
			JTextArea userName= new JTextArea(nameData[i]);
			userName.setFont(new Font(null,0,25));
			userName.setEditable(false);
			
			user.add(new JLabel(userImage),"West");
			user.add(userName);
			
			userList.add(user);
		}
		userList.add(Box.createVerticalStrut(400));

		this.revalidate();
	}
	//���濡�� �渮��Ʈ�� ����� �߰��ϴ� �޼ҵ�
	public void roomAdd(String[] roomData) {//�����κ��� �޾ƿ� �븮��Ʈ �����͸� �޾ƿ�
		roomList.removeAll();
		for(int i=0; i<roomData.length; i++) {
			String[] room = roomData[i].split("/");//��:����/����� �� /�� ������
			JPanel roomPanel = new JPanel();//���г�
			//���г� ����
			roomPanel.setPreferredSize(new Dimension(900,50));
			roomPanel.setLayout(new BorderLayout());
			roomPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			//���ȣ
			JTextArea roomNum = new JTextArea(room[0]+". ");
			roomNum.setFont(new Font(null,0,20));
			roomNum.setEditable(false);
			//���̸� ex)�ο���/7 ���̸� 
			final JTextArea roomName = new JTextArea(" ("+room[3]+"/7)  "+room[1]+" - "+ room[2]);
			roomName.setFont(new Font(null,0,20));
			roomName.setEditable(false);
			//������ ��ư
			JButton enter = new JButton("����");
			enter.addActionListener(e->mySocket.roomEnter(Integer.parseInt(room[0])));
			
			roomPanel.add(roomName,"Center");
			roomPanel.add(enter,"East");
			roomPanel.add(roomNum,"West");
			roomList.add(roomPanel);
		}
		JButton create = new JButton("��  ��  ��");
		create.setSize(500,100);
		create.addActionListener(e->mySocket.roomCreate());
		roomList.add(create);
		roomList.add(Box.createVerticalStrut(400));
		this.revalidate();
	}

	public void roomChatting(String ����) {
		output.append(����+"\n");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String ip = (String)JOptionPane.showInputDialog(this,null,"���� ������ �Է�",JOptionPane.PLAIN_MESSAGE,null,null,null);
		if(ip!=null) {
			try {
				Socket socket = new Socket(ip, 2018);
				String nick = (String)JOptionPane.showInputDialog(this,null,"�г��� �Է�",JOptionPane.PLAIN_MESSAGE,null,null,null);
				if(nick!=null) {
					//���� ����Ʈ �г� ����
					roomList = new JPanel();
					roomList.setBackground(Color.white);
					roomList.setLayout(new BoxLayout(roomList,BoxLayout.Y_AXIS));
					JScrollPane roomListPanel = new JScrollPane(roomList);
					roomListPanel.setBackground(Color.white);
					roomListPanel.setBorder(BorderFactory.createTitledBorder("����"));
					roomListPanel.setBounds(100,100,1000,450);

					//���� ��������Ʈ �г� ����
					userList = new JPanel();
					userList.setBackground(Color.white);
					JScrollPane userListPanel = new JScrollPane(userList);
					userListPanel.setBorder(BorderFactory.createTitledBorder("�������"));
					userListPanel.setBackground(Color.WHITE);
					userListPanel.setBounds(1150, 100, 350, 450);
					userList.setLayout(new BoxLayout(userList,BoxLayout.Y_AXIS));

					//���� �г� ����
					if(nick==null||nick.length()==0) nick = "Unknown";
					mySocket = new User.SocketReceiver(this, socket);
					mySocket.setNick(nick);
					container.remove(button);

					//���� ä���г� ����
					chat = new JPanel();
					chat.setLayout(new BorderLayout());//���̾ƿ� BorderLayout���� ����
					chat.setBounds(100, 600, 1400, 300);//ũ�� ��ġ ����
					chat.setBackground(Color.WHITE);//���� ����
					chat.setOpaque(false);
					output = new JTextArea();//�ؽ�Ʈ���̸��� ��ü����
					output.setEditable(false);//�ؽ�Ʈ���̸�� �Է¸��ϰ���
					JTextField input = new JTextField();//�ؽ�Ʈ�ʵ� ��ü����
					JScrollPane scroll = new JScrollPane(output);//�ؽ�Ʈ���̸��� ��ũ�� �Ǳ����� JScrollPane Ŭ����
					input.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							mySocket.chatting(input.getText().toString());
							input.setText("");
						}
					});

					chat.add(scroll);
					chat.add(input, "South");//�Է��ʵ带 ���ʿ� �߰�

					container.add(roomListPanel,new Integer(1));
					container.add(userListPanel,new Integer(1));
					container.add(chat,new Integer(1));

					this.revalidate();
				}else socket.close();
			} catch (Exception e1) { JOptionPane.showMessageDialog(this, " ������ �������� �ʾҽ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE); }
		}
	}

	//��ư ���� ����
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	//��ư ���� ������
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {//���콺�� ��ư�� ��������
		image = new ImageIcon(getClass().getClassLoader().getResource("image/button2.png"));//�̹����� �ٸ��̹����κҷ��ͼ�
		button.setIcon(image);//�̹��� ����
		button.setBounds(650, 610, image.getIconWidth(), image.getIconHeight());//��ġ ũ�⼳��
	}

	@Override
	public void mouseReleased(MouseEvent e) {//���콺�� ��ư�� ������
		image = new ImageIcon(getClass().getClassLoader().getResource("image/button1.png"));//ó�� �̹����� �ٽúҷ�����
		button.setIcon(image);//�̹��� ����
		button.setBounds(650, 600, image.getIconWidth(), image.getIconHeight());//��ġ ũ�⼳��
	}
	
}
