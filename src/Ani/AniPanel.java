package Ani;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AniPanel extends JPanel{
	private Thread È­°¡;
	private ImageIcon ¹ð;
//	private BufferedImage ¹ð;
	private BufferedImage Ä«µåµÞ¸é;
	private BufferedImage °­Å»;
	private BufferedImage Æø¹ß;
	private BufferedImage[] È¸ÇÇ;
	private BufferedImage[] ¸ÆÁÖ;
	private String action;
	private int x;
	private int y;
	
	public AniPanel() {
		setOpaque(false);
		¹ð = new ImageIcon("./image/Ani/back.jpg");
		try {
//			¹ð=ImageIO.read(new File("./image/Ani/back.jpg"));
			Ä«µåµÞ¸é=ImageIO.read(new File("./image/Ani/back.jpg"));
			°­Å»=ImageIO.read(new File("./image/Ani/back.jpg"));
			Æø¹ß=ImageIO.read(new File("./image/Ani/back.jpg"));
			È¸ÇÇ=new BufferedImage[]{ImageIO.read(new File("./image/Ani/È¸ÇÇ1.jpg")),ImageIO.read(new File("./image/Ani/È¸ÇÇ2.jpg"))};
			¸ÆÁÖ=new BufferedImage[]{
					ImageIO.read(new File("./image/Ani/beer1.jpg")),
					ImageIO.read(new File("./image/Ani/beer2.jpg")),
					ImageIO.read(new File("./image/Ani/beer3.jpg"))};
		} catch (IOException e) {
			System.out.println("ÀÌ¹ÌÁöºÒ·¯¿À±â ½ÇÆÐ");
		}
		
		action="¹ð";
		
		È­°¡ = new Thread() {
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
		È­°¡.start();
	}
	
	public void paint(Graphics g) {
		¹ð.paintIcon(this, g, x, y);
	}
	
	public void update(Graphics g) {
		super.paint(g);
	}
}
