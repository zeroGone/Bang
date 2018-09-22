package Ani;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AniPanel extends JPanel implements Runnable{
	private Thread È­°¡;
	private ImageIcon ¹ð;
	private ImageIcon Ä«µåµÞ¸é;
	private ImageIcon °­Å»;
	private ImageIcon Æø¹ß;
	private ImageIcon[] È¸ÇÇ;
	private ImageIcon[] ¸ÆÁÖ;
	
	private String action;
	private int x;
	private int y;
	
	public AniPanel() {
		setOpaque(false);
		¹ð = new ImageIcon("./image/Ani/back.jpg");
		Ä«µåµÞ¸é=new ImageIcon("./image/Ani/back.jpg");
		Ä«µåµÞ¸é=new ImageIcon(Ä«µåµÞ¸é.getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH));
		°­Å»=new ImageIcon("./image/Ani/back.jpg");
		Æø¹ß=new ImageIcon("./image/Ani/back.jpg");
		È¸ÇÇ=new ImageIcon[]{
				new ImageIcon("./image/Ani/È¸ÇÇ1.jpg"),
				new ImageIcon("./image/Ani/È¸ÇÇ2.jpg")};
		¸ÆÁÖ=new ImageIcon[]{
				new ImageIcon("./image/Ani/beer1.jpg"),
				new ImageIcon("./image/Ani/beer2.jpg"),
				new ImageIcon("./image/Ani/beer3.jpg")};
		action="¹ð";
		È­°¡ = new Thread(this);
	}
	
	public void startAnimation(int... cards) {//½ÃÀÛ ¾Ö´Ï¸ÞÀÌ¼Ç
		action="start";
		x=910;
		y=540;
	}
	
	public void bangAnimation(int catser, int goal, boolean evasion) {//¹ð ¾Ö´Ï¸ÞÀÌ¼Ç
		action="bang";
	}
	
	public void takeAnimation(int caster, int goal) {//°­Å» ¾Ö´Ï¸ÞÀÌ¼Ç
		action="take";
	}
	
	public void beerAnimation(int caster) {
		action="beer";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(action.equals("start")) {
			Ä«µåµÞ¸é.paintIcon(this, g, x, y);
		}
	}

	@Override
	public void run() {
		while(true) {
			x++;
			y++;
			repaint();
			System.out.println(x+","+y);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
