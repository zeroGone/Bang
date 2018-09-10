package Start;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame implements MouseListener{
	private JLayeredPane container;//�гε��� �׾Ƽ� ���� �� �ְ� �⺻ �г��̵� JLayeredPane
	private JButton button;//����ȭ���� ��ư
	private JPanel background;//��� �г�
	private ImageIcon image;
	public Main() {
		/* ���������� 
		 * layout�� default�� �������־ �гε��� ũ��� ��ġ�� �������ִ°� �ٶ�����
		 */
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		String input = (String)JOptionPane.showInputDialog(this,null,"�г��� �Է�",JOptionPane.PLAIN_MESSAGE,null,null,null);
		
		if(input==null);//NullPointException �����
		else if(input.length()==0) JOptionPane.showMessageDialog(this, "�г����� �Է��ϼ���", "���", JOptionPane.WARNING_MESSAGE);
		else System.out.println(input);
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
