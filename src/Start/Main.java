package Start;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame{
	
	public Main() {
		setLayout(null);
		
		ImageIcon illust = new ImageIcon("./image/button1.png");
		JButton button = new JButton(illust);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setSize(illust.getIconWidth(),illust.getIconHeight());
		button.setLocation(650, 600);
		
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				System.out.println(1);
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				ImageIcon illust = new ImageIcon("./image/button2.png");
				button.setIcon(illust);
				button.setSize(illust.getIconWidth(),illust.getIconHeight());
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				ImageIcon illust = new ImageIcon("./image/button1.png");
				button.setIcon(illust);
				button.setSize(illust.getIconWidth(),illust.getIconHeight());
			}
			
		});
		
		add(button);
		
		illust = new ImageIcon("./image/illust.png");
		Image temp = illust.getImage();
		JLabel label = new JLabel(new ImageIcon(temp.getScaledInstance(1600, 1000, Image.SCALE_SMOOTH)));
		label.setSize(1600,1000);
		add(label);
		
		setTitle("Bang");
		setSize(1600,1000);
		setLocationRelativeTo(null);//화면 중앙에 배치
		setResizable(false);//화면 사이즈 변경 불가
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
