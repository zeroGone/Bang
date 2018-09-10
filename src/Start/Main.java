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
	private JLayeredPane container;//�гε��� �׾Ƽ� ���� �� �ְ� �⺻ �г��̵� JLayeredPane
	private JButton button;//����ȭ���� ��ư
	private JPanel background;//��� �г�
	
	public Main() {
		/* ���������� 
		 * layout�� default�� �������־ �гε��� ũ��� ��ġ�� �������ִ°� ��`������
		 */
		
		ImageIcon buttonImage = new ImageIcon("./image/button1.png");//ImageIcon��ü�� ��ư �̹��� �޾ƿ�
		button = new JButton(buttonImage);//��ư ��ü ����
		button.setContentAreaFilled(false);//��ư ���� ä��� ����
		button.setBorderPainted(false);//��ư ��漱 ����
		button.setBounds(650, 600, buttonImage.getIconWidth(), buttonImage.getIconHeight());//��ư �̹��� ũ�⸸ŭ 650,600 ��ġ�� ����
		
		button.addMouseListener(new MouseListener() {//���콺 �̺�Ʈ ����
			ImageIcon image;
			@Override
			public void mouseClicked(MouseEvent arg0) {//���콺 Ŭ��������
				container.remove(button);//���������̳ʿ� ��ư�� ����
				
				String nick = "";
				while(nick.length()==0) {
					nick = (String) JOptionPane.showInputDialog(null,null,"�г��� �Է�",JOptionPane.PLAIN_MESSAGE, null,null,null);//�г��� ���� ���̾�α� 
					if(nick==null) System.exit(0);//�г��� �Է� ����� ��� �ý��� ����
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
			public void mouseEntered(MouseEvent arg0) {//���콺�� ��ư������ ��������
			}

			@Override
			public void mouseExited(MouseEvent arg0) {//���콺�� ��ư������ ��������
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {//���콺�� ��ư�� ��������
				image = new ImageIcon("./image/button2.png");//�̹����� �ٸ��̹����κҷ��ͼ�
				button.setIcon(image);//�̹��� ����
				button.setBounds(650, 610, image.getIconWidth(), image.getIconHeight());//��ġ ũ�⼳��
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {//���콺�� ��ư�� ������
				image = new ImageIcon("./image/button1.png");//ó�� �̹����� �ٽúҷ�����
				button.setIcon(image);//�̹��� ����
				button.setBounds(650, 600, image.getIconWidth(), image.getIconHeight());//��ġ ũ�⼳��
			}
		});
		
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
	
}
