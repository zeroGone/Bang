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
	private Thread ȭ��;
	private ImageIcon ��;
	private ImageIcon ī��޸�;
	private ImageIcon ��Ż;
	private ImageIcon ����;
	private ImageIcon[] ȸ��;
	private ImageIcon[] ����;
	
	private String action;
	private int x;
	private int y;
	
	public AniPanel() {
		setOpaque(false);
		�� = new ImageIcon("./image/Ani/back.jpg");
		ī��޸�=new ImageIcon("./image/Ani/back.jpg");
		ī��޸�=new ImageIcon(ī��޸�.getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH));
		��Ż=new ImageIcon("./image/Ani/back.jpg");
		����=new ImageIcon("./image/Ani/back.jpg");
		ȸ��=new ImageIcon[]{
				new ImageIcon("./image/Ani/ȸ��1.jpg"),
				new ImageIcon("./image/Ani/ȸ��2.jpg")};
		����=new ImageIcon[]{
				new ImageIcon("./image/Ani/beer1.jpg"),
				new ImageIcon("./image/Ani/beer2.jpg"),
				new ImageIcon("./image/Ani/beer3.jpg")};
		action="��";
		ȭ�� = new Thread(this);
	}
	
	public void startAnimation(int... cards) {//���� �ִϸ��̼�
		action="start";
		x=910;
		y=540;
	}
	
	public void bangAnimation(int catser, int goal, boolean evasion) {//�� �ִϸ��̼�
		action="bang";
	}
	
	public void takeAnimation(int caster, int goal) {//��Ż �ִϸ��̼�
		action="take";
	}
	
	public void beerAnimation(int caster) {
		action="beer";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(action.equals("start")) {
			ī��޸�.paintIcon(this, g, x, y);
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
