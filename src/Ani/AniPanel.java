package Ani;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AniPanel extends JPanel{
	private Thread ȭ��;
	private ImageIcon ��;
//	private BufferedImage ��;
	private BufferedImage ī��޸�;
	private BufferedImage ��Ż;
	private BufferedImage ����;
	private BufferedImage[] ȸ��;
	private BufferedImage[] ����;
	private String action;
	private int x;
	private int y;
	
	public AniPanel() {
		setOpaque(false);
		�� = new ImageIcon("./image/Ani/back.jpg");
		try {
//			��=ImageIO.read(new File("./image/Ani/back.jpg"));
			ī��޸�=ImageIO.read(new File("./image/Ani/back.jpg"));
			��Ż=ImageIO.read(new File("./image/Ani/back.jpg"));
			����=ImageIO.read(new File("./image/Ani/back.jpg"));
			ȸ��=new BufferedImage[]{ImageIO.read(new File("./image/Ani/ȸ��1.jpg")),ImageIO.read(new File("./image/Ani/ȸ��2.jpg"))};
			����=new BufferedImage[]{
					ImageIO.read(new File("./image/Ani/beer1.jpg")),
					ImageIO.read(new File("./image/Ani/beer2.jpg")),
					ImageIO.read(new File("./image/Ani/beer3.jpg"))};
		} catch (IOException e) {
			System.out.println("�̹����ҷ����� ����");
		}
		
		action="��";
		
		ȭ�� = new Thread() {
			public void run() {
				while(true) {
					x+=1;
					y+=1;
					repaint();
					System.out.println(x+","+y);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(x>500||y>500) {
						this.interrupt();
						break;
					}
				}
			}
		};
		ȭ��.start();
	}
	
	public void paint(Graphics g) {
		��.paintIcon(this, g, x, y);
	}
	
	public void update(Graphics g) {
		super.paint(g);
	}
}
