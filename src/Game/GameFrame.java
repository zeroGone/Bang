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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//������������ �ݾƼ� �����尡 ������� �����ϱ����� ����������ϴ� �޼ҵ�
		setExtendedState(JFrame.MAXIMIZED_BOTH);//��üȭ��
		setUndecorated(true);//����ǥ���� �����
		setResizable(false);//ȭ�� ������ ���� �Ұ�
		
		container = new JLayeredPane() {
			@Override
			public void paintComponent(Graphics g) {//�г��� �⺻������ �׸��� �޼ҵ带 �������̵�
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("./image/���.jpg");//����̹��� �ҷ��ͼ�
				image = new ImageIcon(image.getImage().getScaledInstance((int)screen.getWidth(), (int)screen.getHeight(), Image.SCALE_SMOOTH));//ũ�� �ٽü������ְ�
				g.drawImage(image.getImage(), 0, 0, null);//�޾ƿ� ImageIcon�� getImage�� �޾Ƽ� �̹����� 0,0 ��ġ�� �׸���
			}
		};//��ü�г�
		container.setLayout(null);
		
		screen = Toolkit.getDefaultToolkit().getScreenSize();//�� ��Ʈ�� : 1920X1080
		
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
		
		add(container);
		setVisible(true);//�������� ���ϼ��ְ�
	}
	
	//�����г�
	private class GamePanel extends JLayeredPane{
		private Animation animation;
		public GamePanel() {
			setLayout(null);
			SeatPanel seat = new SeatPanel();
			seat.setBounds(0, 0, 1400, 1080);
			
			add(seat,new Integer(0));
			
			animation = new Animation();
			
			Animation ani = new Animation();
			add(ani, new Integer(1));
			
		}
		
		
		private class SeatPanel extends JPanel{
			public SeatPanel() {
				GridLayout layout = new GridLayout(3,3);//33 GridLayout
				layout.setHgap(10);//����  ���� ����
				layout.setVgap(10);//����  ���� ����
				setLayout(layout);
				
//				add(new UserPanel("�迵��","���Ȱ�",CharacterSetting.character()));
//				add(new JPanel());
//				add(new UserPanel("������","�����",CharacterSetting.character()));
//				add(new UserPanel("ȫ�ؼ�","������",CharacterSetting.character()));
//				add(new DeckPanel());
//				add(new UserPanel("������","������",CharacterSetting.character()));
//				add(new UserPanel("���ϱ�","�ΰ�",CharacterSetting.character()));
//				add(new UserPanel("�����","�ΰ�",CharacterSetting.character()));
//				add(new UserPanel("������","������",CharacterSetting.character()));
			}
			

			private class DeckPanel extends JPanel{
				private ImageIcon image;
				public DeckPanel() {
					setLayout(null);
					image = new ImageIcon("./image/deck.jpg");
					image = new ImageIcon(image.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH));
					JLabel imageLabel = new JLabel(image);
					imageLabel.setLocation(130,80);
					imageLabel.setSize(image.getIconWidth(),image.getIconHeight());
					add(imageLabel);
					
				}
			}
		}
		
	}
	
}
	
