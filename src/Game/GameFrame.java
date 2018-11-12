package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

public class GameFrame extends JFrame{
	private Dimension screen;
	private JLayeredPane container;
	
	//������ ������ �ٷ� �г�
	private JPanel controller;

	public GameFrame() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);//��üȭ��
		setUndecorated(true);//����ǥ���� �����
		setResizable(false);//ȭ�� ������ ���� �Ұ�
		screen = Toolkit.getDefaultToolkit().getScreenSize();//�� ��Ʈ�� : 1920X1080
		container = new JLayeredPane();
		container.setOpaque(true);
		container.setBackground(Color.white);
		container.setLayout(null);
		
		//ä���г� �߰�
		JPanel chat = new JPanel();
		chat.setBounds(10, (int)screen.getHeight()/3*2-10,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		chat.setLayout(new BorderLayout());
		chat.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		JTextArea chatOutput = new JTextArea();//�ؽ�Ʈ���̸��� ��ü����
		chatOutput.setEditable(false);//�ؽ�Ʈ���̸�� �Է¸��ϰ���
		JScrollPane chatScroll = new JScrollPane(chatOutput);//�ؽ�Ʈ���̸��� ��ũ�� �Ǳ����� JScrollPane Ŭ����
		
		JTextField input = new JTextField();//�ؽ�Ʈ�ʵ� ��ü����
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length()>=20) ke.consume();
			}
		});
		
		input.addActionListener(e->{
			chatOutput.append(input.getText()+"\n");//������ ������ְ�
			input.setText("");//�Է��� �ʱ�ȭ����
		});

		JTextPane chatTitle = new JTextPane();
		chatTitle.setEditable(false);
		chatTitle.setText("ä��â");

		//�������
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		chatTitle.setParagraphAttributes(attribs, true);

		chat.add(chatTitle,"North");
		chat.add(chatScroll);
		chat.add(input, "South");//�Է��ʵ带 ���ʿ� �߰�
		container.add(chat);
		
		//�α��г� �߰�
		JPanel log = new JPanel();
		log.setBounds((int)screen.getWidth()/3*2-10, (int)screen.getHeight()/3*2-10 ,(int)screen.getWidth()/3, (int)screen.getHeight()/3);//640X360
		log.setLayout(new BorderLayout());
		log.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		JTextArea logOutput = new JTextArea();//�ؽ�Ʈ���̸��� ��ü����
		logOutput.setEditable(false);//�ؽ�Ʈ���̸�� �Է¸��ϰ���
		JScrollPane logScroll = new JScrollPane(logOutput);//�ؽ�Ʈ���̸��� ��ũ�� �Ǳ����� JScrollPane Ŭ����
		
		JTextPane logTitle = new JTextPane();
		logTitle.setEditable(false);
		logTitle.setText("Log");
		logTitle.setParagraphAttributes(attribs, true);
		
		log.add(logTitle,"North");
		log.add(logScroll);
		container.add(log);
		
		Ani.AniPanel ani = new Ani.AniPanel(screen);
		container.add(ani,new Integer(1));
		
		controller = new JPanel();
		controller.setBackground(Color.white);
		controller.setOpaque(false);
		controller.setBounds(520, 360, 880, 300);
		controller.setLayout(null);
		container.add(controller, new Integer(2));
		add(container);
		setVisible(true);//�������� ���ϼ��ְ�
	}
	
	public void userSet(int member, int startIndex, String nicks) {
		System.out.println(member+" "+startIndex+" "+nicks);
		String[] nick = nicks.split(",");
		int index = startIndex;
		//�����г� �ڸ� ����
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
	
	public void gameReady() {
		ImageIcon buttonImage = new ImageIcon(getClass().getClassLoader().getResource("image/button1.png"));//ImageIcon��ü�� ��ư �̹��� �޾ƿ�
		JButton button = new JButton(buttonImage); 
		button.setContentAreaFilled(false);//��ư ���� ä��� ����
		button.setBorderPainted(false);//��ư ��漱 ����
		button.setBounds(controller.getWidth()/2-buttonImage.getIconWidth()/2, controller.getHeight()/2-buttonImage.getIconHeight()/2, buttonImage.getIconWidth(), buttonImage.getIconHeight());//��ư �̹��� ũ�⸸ŭ 650,600 ��ġ�� ����
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				ImageIcon buttonImage = new ImageIcon(getClass().getClassLoader().getResource("image/button2.png"));//�̹����� �ٸ��̹����κҷ��ͼ�
				button.setIcon(buttonImage);//�̹��� ����
				button.setBounds(controller.getWidth()/2-buttonImage.getIconWidth()/2, controller.getHeight()/2-buttonImage.getIconHeight()/2, buttonImage.getIconWidth(), buttonImage.getIconHeight());//��ư �̹��� ũ�⸸ŭ 650,600 ��ġ�� ����
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				ImageIcon buttonImage = new ImageIcon(getClass().getClassLoader().getResource("image/button1.png"));//�̹����� �ٸ��̹����κҷ��ͼ�
				button.setIcon(buttonImage);//�̹��� ����
				button.setBounds(controller.getWidth()/2-buttonImage.getIconWidth()/2, controller.getHeight()/2-buttonImage.getIconHeight()/2, buttonImage.getIconWidth(), buttonImage.getIconHeight());//��ư �̹��� ũ�⸸ŭ 650,600 ��ġ�� ����
			}
		});
		
		controller.add(button);
		this.revalidate();
	}
}

/*
 * 
 * JPanel panel = new JPanel();
		panel.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());
		panel.setOpaque(false);
JButton button1 = new JButton("����");
button1.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ani.startAnimation(5,3,0,4,4,0,4);
	}
});

//���ٽ� 
button1.addActionListener(e -> ani.startAnimation(5,3,0,4,4,0,4));

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

JButton button5 = new JButton("�����");
button5.addActionListener(e->ani.machineGunAnimation());

JButton button6 = new JButton("������");
button6.addActionListener(e->ani.stageCoachAnimation());

JButton button7 = new JButton("�����İ�����");
button7.addActionListener(e->ani.bankAnimation());

JButton button8 = new JButton("�ε��");
button8.addActionListener(e->ani.indianAnimation());

JButton button9 = new JButton("���̳ʸ���Ʈ");
button9.addActionListener(e->ani.dynamiteAnimation(0,true));

JButton button10 = new JButton("����");
button10.addActionListener(e->ani.prisonAnimation(0));

JButton button11 = new JButton("����");
button11.addActionListener(e->ani.fightAnimation());

JButton button12 = new JButton("��Ʈ");
button12.addActionListener(e->ani.hitAnimation(0));

JButton button13 = new JButton("����");
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
	
