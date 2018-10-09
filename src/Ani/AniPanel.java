package Ani;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Game.GameFrame;

public class AniPanel extends JPanel implements Runnable{
	private Thread ȭ��;
	private ���ְ� ���ְ�;
	private Dimension screen;
	private ImageIcon ī��޸�;
	private ImageIcon ��;
	private ImageIcon ��Ż;
	private ImageIcon ����;
	private ImageIcon[] ȸ��;
	private ImageIcon[] ����;
	
	private String action;
	private int x;
	private int y;
	
	
	//������  ȭ�� ũ�� �ް�, �̹����� ��������
	public AniPanel(Dimension screen) {
		setOpaque(false);
		this.screen = screen;
		this.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());

		ī��޸�=new ImageIcon("./image/Ani/back.jpg");
		ī��޸�=new ImageIcon(ī��޸�.getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH));
		
		�� = new ImageIcon("./image/Ani/��.jpg");
		
		��Ż=new ImageIcon("./image/Ani/back.jpg");
		����=new ImageIcon("./image/Ani/����.jpg");
		ȸ��=new ImageIcon[]{
				new ImageIcon("./image/Ani/ȸ��1.jpg"),
				new ImageIcon("./image/Ani/ȸ��2.jpg")};
		����=new ImageIcon[]{
				new ImageIcon("./image/Ani/beer1.jpg"),
				new ImageIcon("./image/Ani/beer2.jpg"),
				new ImageIcon("./image/Ani/beer3.jpg")};
		
		ȭ�� = new Thread(this);
		���ְ� = new ���ְ�();
	}
	
	private int[] ī���;
	private Point[] ī����ǥ;
	private int ī�嵹��Ƚ��;
	private int max;
	public void startAnimation(int... cards) {//���� �ִϸ��̼�
		action="start";
		
		max = 0 ;
		for(int i=0; i<cards.length; i++) {
			if(max<cards[i]) max = i;
		}
		
		ī��� = cards;
		
		ī����ǥ = new Point[cards.length];
		
		for(int i=0; i<ī����ǥ.length; i++) {
			ī����ǥ[i] = new Point((int)screen.getWidth()/2-ī��޸�.getIconWidth()/2,(int)screen.getHeight()/2-ī��޸�.getIconHeight()/2);
		}
		check = new boolean[2];
		ī�嵹��Ƚ�� = 0;
		ȭ��.start();
		���ְ�.����();
		���ְ�.start();
	}
	
	private boolean[] check;
	private void ī�嵹����() {
		for(int i=0; i<ī���.length; i++) {
			if(ī�嵹��Ƚ��<ī���[i]) {
				if(i==0) {//�Ʒ�
					ī����ǥ[i].y++;
				}else if(i==1) {//�ǿ���
					ī����ǥ[i].x-=2;
				}else if(i==2) {//��ܿ��ʱ��� 2��°
					if(check[0]) {
						ī����ǥ[i].x--;
						check[0]=false;
					}else check[0]=true;
					ī����ǥ[i].y--;
				}else if(i==3) {//�ǿ�����
					ī����ǥ[i].x+=2;
				}else if(i==4) {//��ܿ��ʱ��� 3��°
					if(check[1]) {
						ī����ǥ[i].x++;
						check[1]=false;
					}else check[1]=true;
					ī����ǥ[i].y--;
				}else if(i==5) {//��ܿ��ʱ��� 1����
					ī����ǥ[i].x-=2;
					ī����ǥ[i].y--;
				}else if(i==6) {//��ܿ��ʱ��� 4����
					ī����ǥ[i].x+=2;
					ī����ǥ[i].y--;
				}
			}
		}

		if(ī����ǥ[0].getY()==790||ī����ǥ[2].getY()==110||ī����ǥ[4].getY()==110||ī����ǥ[5].getY()==110||ī����ǥ[6].getY()==110||ī����ǥ[1].getX()==234||ī����ǥ[3].getX()==1554) {
			for(int i=0; i<ī����ǥ.length; i++) {
				ī����ǥ[i] = new Point((int)screen.getWidth()/2-ī��޸�.getIconWidth()/2,(int)screen.getHeight()/2-ī��޸�.getIconHeight()/2);
			}
			ī�嵹��Ƚ��++;
		}

	}
	
	
	private int[] caster;
	private int[] goal;
	public void bangAnimation(int caster, int goal, boolean evasion) {//�� �ִϸ��̼�
		action="bang";
		this.caster=GameFrame.������ǥ[caster-1];
		this.goal=GameFrame.������ǥ[goal-1];
	}
	
	public void beerAnimation(int caster) {
		action="beer";
	}
	
	public void takeAnimation(int caster, int goal) {//��Ż �ִϸ��̼�
		action="take";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ī��޸�.paintIcon(this, g, (int)screen.getWidth()/2-ī��޸�.getIconWidth()/2,(int)screen.getHeight()/2-ī��޸�.getIconHeight()/2);

		if(action.equals("start")) {
			for(int i=0; i<ī���.length; i++) ī��޸�.paintIcon(this, g, (int)ī����ǥ[i].getX(), (int)ī����ǥ[i].getY());
		}else if(action.equals("bang")) {
			��.paintIcon(this, g, caster[0], caster[1]);
			ȸ��[0].paintIcon(this, g, goal[0], goal[1]);
			����.paintIcon(this, g, goal[0], goal[1]);
		}else if(action.equals("beer")) {
			
		}else if(action.equals("bang")) {
			
		}else if(action.equals("bang")) {
			
		}else if(action.equals("bang")) {
			
		}
	}

	@Override
	public void run() {
		while(true) {
			if(action.equals("start")) {
				if(max<ī�嵹��Ƚ��) break;
				ī�嵹����();
			}else if(action.equals("bang")) {
				
			}else if(action.equals("beer")) {
				
			}else if(action.equals("bang")) {
				
			}else if(action.equals("bang")) {
				
			}else if(action.equals("bang")) {
				
			}
			this.repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("��");
	}
	
	public class ���ְ� extends Thread{
		private Clip clip;
		
		public void ����() {
			File file = null;
			if(action.equals("start")) {
				file = new File("./audio/ī�嵹����.wav");
			}else if(action.equals("bang")) {

			}else if(action.equals("beer")) {

			}else if(action.equals("bang")) {

			}else if(action.equals("bang")) {

			}else if(action.equals("bang")) {
			}
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(stream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			while(true) {
				clip.start();
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
