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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//������������ �ݾƼ� �����尡 ������� �����ϱ����� ����������ϴ� �޼ҵ�
		setExtendedState(JFrame.MAXIMIZED_BOTH);//��üȭ��
		setUndecorated(true);//����ǥ���� �����
		setResizable(false);//ȭ�� ������ ���� �Ұ�
		screen = Toolkit.getDefaultToolkit().getScreenSize();//�� ��Ʈ�� : 1920X1080

		container = new JLayeredPane();
//			@Override
//			public void paintComponent(Graphics g) {//�г��� �⺻������ �׸��� �޼ҵ带 �������̵�
//				super.paintComponent(g);
//				g.setColor(new Color(0x964B00));
//				int radius = 10;
//				int step = 4;
//				int forLen = (radius / step);
//				for (int i = 0; i < forLen; i++)
//				    g.fillOval(radius - i * step, radius - i * step, i * step * 2, i * step * 2);
//				try {
//					BufferedImage image = ImageIO.read(new File("./image/���.jpg"));
//					g.drawImage(image, 0, 0, null);//�޾ƿ� ImageIcon�� getImage�� �޾Ƽ� �̹����� 0,0 ��ġ�� �׸���
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				setOpaque(false);
//				ImageIcon image = new ImageIcon("./image/���.jpg");//����̹��� �ҷ��ͼ�
//				image = new ImageIcon(image.getImage().getScaledInstance((int)screen.getWidth(), (int)screen.getHeight(), Image.SCALE_SMOOTH));//ũ�� �ٽü������ְ�
//			}
//		};//��ü�г�
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setLayout(null);
		
		//ä���г� �߰�
		Chat.ChatPanel chatPanel = new Chat.ChatPanel();
		chatPanel.setBounds(10, (int)screen.getHeight()/3*2-10,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		container.add(chatPanel);
		
		//�α��г� �߰�
		Chat.LogPanel logPanel = new Chat.LogPanel();
		logPanel.setBounds((int)screen.getWidth()/3*2-10, (int)screen.getHeight()/3*2-10 ,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		container.add(logPanel);
		
		//�����г� �ڸ� ����
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
		JButton button1 = new JButton("����");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ani.startAnimation(5,3,0,4,4,0,4);
			}
		});
		
		JButton button2 = new JButton("��");
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ani.bangAnimation(1, 2, true);
			}
		});
		
		JButton button3 = new JButton("����");
		button3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ani.beerAnimation(1);
			}
		});
		
		JButton button4 = new JButton("��Ż");
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
		setVisible(true);//�������� ���ϼ��ְ�
		
	}
}
	
