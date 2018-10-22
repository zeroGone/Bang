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
	private JLayeredPane container;//�гε��� �׾Ƽ� ���� �� �ְ� �⺻ �г��̵� JLayeredPane
	private JPanel chat;//���� ä���г�
	private JPanel roomList;//���� ����Ʈ�г�
	private JPanel userList;//���� ��������Ʈ�г�
	private JButton button;//����ȭ���� ��ư
	private JPanel background;//��� �г�
	private JTextArea output;//ä���г� ���â
	private ImageIcon image;
	private SocketReceiver socket;
	
	public Main() {
		/* ���������� 
		 * layout�� default�� �������־ �гε��� ũ��� ��ġ�� �������ִ°� �ٶ�����
		 */
		
		//bgm ����
		File bgm = new File("./audio/bgm.wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(bgm);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			System.out.println("��������Ͼ���");
		}
		
		ImageIcon buttonImage = new ImageIcon("./image/button1.png");//ImageIcon��ü�� ��ư �̹��� �޾ƿ�
		button = new JButton(buttonImage);//��ư ��ü ����
		button.setContentAreaFilled(false);//��ư ���� ä��� ����
		button.setBorderPainted(false);//��ư ��漱 ����
		button.setBounds(650, 600, buttonImage.getIconWidth(), buttonImage.getIconHeight());//��ư �̹��� ũ�⸸ŭ 650,600 ��ġ�� ����
		
		button.addMouseListener(this);
		
		background = new JPanel() {//����� �� �г��� �����Ѵ�
			@Override
			public void paintComponent(Graphics g) {//�г��� �⺻������ �׸��� �޼ҵ带 �������̵�
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("./image/illust.png");//����̹��� �ҷ��ͼ�
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
	
	public static void main(String[] args) {
		new Main();
//		new Game.GameFrame();
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
			ImageIcon userImage=new ImageIcon("./image/user.png");
			userImage=new ImageIcon(userImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			//���� �̸��� ������ ���� �޾ƿ� name���� ����
			JTextArea userName= new JTextArea(nameData[i]);
			userName.setFont(new Font(null,0,25));
			userName.setEditable(false);
			
			user.add(new JLabel(userImage),"West");
			user.add(userName);
			
			userList.add(user);
		}
		this.revalidate();
	}
	//���濡�� �渮��Ʈ�� ����� �߰��ϴ� �޼ҵ�
	public void roomAdd(String[] roomData) {//�����κ��� �޾ƿ� �븮��Ʈ �����͸� �޾ƿ�
		for(int i=0; i<roomData.length; i++) {
			roomData= roomData[i].split("/");//��:����/�̸� �� /�� ������
			JPanel room = new JPanel();//���г�
			//���г� ����
			room.setPreferredSize(new Dimension(900,50));
			room.setLayout(new BorderLayout());
			room.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			//���ȣ
			JTextArea roomNum = new JTextArea(Integer.toString(i+1)+". ");
			roomNum.setFont(new Font(null,0,20));
			roomNum.setEditable(false);
			//���̸� ex)�ο���/7 ���̸� 
			final JTextArea roomName = new JTextArea(" ("+roomData[1]+"/7)  "+roomData[0]);
			roomName.setFont(new Font(null,0,20));
			roomName.setEditable(false);
			//������ ��ư
			JButton enter = new JButton("����");
			enter.addActionListener(e->socket.roomEnter(roomName.getText().split("  ")[1]));
			
			room.add(roomName,"Center");
			room.add(enter,"East");
			room.add(roomNum,"West");
			roomList.add(room);
		}
		JButton create = new JButton("�����");
		roomList.add(create);
		this.revalidate();
	}
	
	public void roomChatting(String ����) {
		output.append(����+"\n");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			String nick = (String)JOptionPane.showInputDialog(this,null,"�г��� �Է�",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if(nick!=null) {
				//���� ����Ʈ �г� ����
				JPanel roomListPanel = new JPanel();
				roomListPanel.setBackground(Color.white);
				roomListPanel.setBorder(BorderFactory.createTitledBorder("����"));
				roomListPanel.setBounds(100,100,1000,450);
				roomList = new JPanel();
				roomList.setLayout(new BoxLayout(roomList,BoxLayout.Y_AXIS));
				roomListPanel.add(roomList);

				//���� ��������Ʈ �г� ����
				JPanel userListPanel = new JPanel();
				userListPanel.setBorder(BorderFactory.createTitledBorder("�������"));
				userListPanel.setBackground(Color.WHITE);
				userListPanel.setBounds(1150, 100, 350, 450);
				userList = new JPanel();
				userListPanel.add(userList);
				userList.setLayout(new BoxLayout(userList,BoxLayout.Y_AXIS));

				//���� �г� ����
				if(nick.length()==0) nick = "Unknown";
				User.SocketReceiver user = new User.SocketReceiver(this);
				user.setNick(nick);
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
						user.chatting(input.getText().toString());
						input.setText("");
					}
				});
				
				chat.add(scroll);
				chat.add(input, "South");//�Է��ʵ带 ���ʿ� �߰�

				container.add(roomListPanel,new Integer(1));
				container.add(userListPanel,new Integer(1));
				container.add(chat,new Integer(1));
				
				this.revalidate();
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, " ������ �������� �ʾҽ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
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
		image = new ImageIcon("./image/button2.png");//�̹����� �ٸ��̹����κҷ��ͼ�
		button.setIcon(image);//�̹��� ����
		button.setBounds(650, 610, image.getIconWidth(), image.getIconHeight());//��ġ ũ�⼳��
	}

	@Override
	public void mouseReleased(MouseEvent e) {//���콺�� ��ư�� ������
		image = new ImageIcon("./image/button1.png");//ó�� �̹����� �ٽúҷ�����
		button.setIcon(image);//�̹��� ����
		button.setBounds(650, 600, image.getIconWidth(), image.getIconHeight());//��ġ ũ�⼳��
	}
	
}
