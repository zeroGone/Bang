package Ani;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Game.GameFrame;

public class AniPanel extends JPanel{
	public Thread ȭ��;
	public Thread ���ְ�;
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
		
		��Ż=new ImageIcon("./image/Ani/��Ż.jpg");
		����=new ImageIcon("./image/Ani/����.jpg");
		ȸ��=new ImageIcon[]{
				new ImageIcon("./image/Ani/ȸ��1.jpg"),
				new ImageIcon("./image/Ani/ȸ��2.jpg")};
		����=new ImageIcon[]{
				new ImageIcon("./image/Ani/beer1.jpg"),
				new ImageIcon("./image/Ani/beer2.jpg"),
				new ImageIcon("./image/Ani/beer3.jpg")};
		
		ȭ�� = new Thread() {
			@Override
			public void run() {
				while(true) {
					if(action.equals("start")) {
						if(max<ī�嵹��Ƚ��) break;
						ī�嵹����();
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else if(action.equals("bang")) {
						if(x<2000) {
							if(check[0]) {
								caster[1]+=30;
								check[0]=false;
							}else {
								caster[1]-=30;
								check[0]=true;
								
							}
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}else if(x==2000) {
							File file = null;
							if(evasion) {
								try {
									file = new File("./audio/ȸ��.wav");
									AudioInputStream stream = AudioSystem.getAudioInputStream(file);
									clip = AudioSystem.getClip();
									clip.open(stream);
									clip.loop(5);
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else {
								try {
									file = new File("./audio/��.wav");
									AudioInputStream stream = AudioSystem.getAudioInputStream(file);
									clip = AudioSystem.getClip();
									clip.open(stream);
									clip.start();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}else {
							if(evasion) {
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(x>2010) break;
							}else {
								if(check[0]) {
									goal[0]+=(int)(Math.random()*10);
									goal[1]+=(int)(Math.random()*10);
									check[0]=false;
								}else {
									goal[0]-=(int)(Math.random()*10);
									goal[1]-=(int)(Math.random()*10);
									check[0]=true;
								}
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(x>3000) break;
							}
						}
					}else if(action.equals("beer")) {
						File file = null;
						try {
							Thread.sleep(500);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(x==2) {
							file = new File("./audio/������.wav");
							try {
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==4) {
							file = new File("./audio/���ñ�.wav");
							try {
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==9) {
							file = new File("./audio/Ʈ��.wav");
							try {
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==12) break;
					}else if(action.equals("take")) {
						if(check[0]) {
							caster[1]+=10;
							check[0]=false;
						}else {
							caster[1]-=10;
							check[0]=true;
							
						}
						File file = null;
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(x==10) {
							try {
								file = new File("./audio/��Ż1.wav");
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==50) {
							try {
								file = new File("./audio/��Ż2.wav");
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==55) {
							caster[0]=goal[0];
							caster[1]=goal[1];
						}else if(x==80) {
							break;
						}
						System.out.println(x);
						x++;
					}else if(action.equals("bang")) {
						
					}else if(action.equals("bang")) {
						
					}
					repaint();
					
				}
				System.out.println("��");
				ȭ��.interrupt();
			}
		};
		
		���ְ� = new Thread() {
			@Override
			public void run() {
				clip.start();
				while(true) {
					try {
						if(max==ī�嵹��Ƚ��) break;
						Thread.sleep(540);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					clip.loop(1);
				}
			}
		};
		
		check = new boolean[2];
		action = "";
	}
	
	private int[] ī���;
	private Point[] ī����ǥ;
	private int ī�嵹��Ƚ��;
	private int max;
	private Clip clip;
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
		ī�嵹��Ƚ�� = 0;
		File file = new File("./audio/ī�嵹����.wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ȭ��.start();
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
	private boolean evasion;
	public void bangAnimation(int caster, int goal, boolean evasion) {//�� �ִϸ��̼�
		action="bang";
		this.caster=GameFrame.������ǥ[caster-1];
		this.goal=GameFrame.������ǥ[goal-1];
		this.evasion=evasion;
		File file = new File("./audio/�����.wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ȭ��.start();
	}
	
	public void beerAnimation(int caster) {//���� �ִϸ��̼�
		action="beer";
		this.caster=GameFrame.������ǥ[caster-1];
		File file = new File("./audio/ĵ����.wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ȭ��.start();
		���ְ�.start();
	}

	public void takeAnimation(int caster, int goal) {//��Ż �ִϸ��̼�
		action="take";
		this.caster=GameFrame.������ǥ[caster-1];
		this.goal=GameFrame.������ǥ[goal-1];
		ȭ��.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ī��޸�.paintIcon(this, g, (int)screen.getWidth()/2-ī��޸�.getIconWidth()/2,(int)screen.getHeight()/2-ī��޸�.getIconHeight()/2);

		switch(action) {
		case("start"): 
			for(int i=0; i<ī���.length; i++) ī��޸�.paintIcon(this, g, (int)ī����ǥ[i].getX(), (int)ī����ǥ[i].getY()); 
			break;
		case("bang"):
			if(x<2000) 	��.paintIcon(this, g, caster[0], caster[1]);
			else if(evasion) {
				if(x<2007){
					if(check[0]) {
						ȸ��[0].paintIcon(this, g, goal[0], goal[1]);
						check[0]=false;
					}else {
						ȸ��[1].paintIcon(this, g, goal[0], goal[1]);
						check[0]=true;
					}
				}
			}else if(x<3000){
				����.paintIcon(this, g, goal[0], goal[1]);
			}
			break;
		case("beer"): 
			if(x<12) ����[x%3].paintIcon(this, g, caster[0], caster[1]);
			break;
		case("take"):
			if(x<80) ��Ż.paintIcon(this, g, caster[0], caster[1]);
			break;
		}
	}
}
