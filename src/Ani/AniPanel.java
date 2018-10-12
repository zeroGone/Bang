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
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Game.GameFrame;

public class AniPanel extends JPanel{
	public Thread 화가;
	private Dimension screen;
	public final Point[] 유저자리좌표 = {
			new Point(760,750),
			new Point(40,360),
			new Point(40,20),
			new Point(520,20),
			new Point(1000,20),
			new Point(1480,20),
			new Point(2440,360)};
	
	private ImageIcon 덱;
	private Clip clip;
	private ImageIcon 카드뒷면;
	private ImageIcon 뱅;
	private ImageIcon 강탈;
	private ImageIcon 폭발;
	private ImageIcon[] 회피;
	private ImageIcon[] 맥주;
	private String action;
	private int x;
	private int y;
	
	//생성자  화면 크기 받고, 이미지를 셋팅해줌
	public AniPanel(Dimension screen) {
		setOpaque(false);
		this.screen = screen;
		this.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());

		덱 = new ImageIcon("./image/deck.jpg");
		덱 = new ImageIcon(덱.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		
		카드뒷면=new ImageIcon("./image/Ani/back.jpg");
		카드뒷면=new ImageIcon(카드뒷면.getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH));
		
		뱅 = new ImageIcon("./image/Ani/뱅.jpg");
		
		강탈=new ImageIcon("./image/Ani/강탈.jpg");
		폭발=new ImageIcon("./image/Ani/폭발.jpg");
		회피=new ImageIcon[]{
				new ImageIcon("./image/Ani/회피1.jpg"),
				new ImageIcon("./image/Ani/회피2.jpg")};
		맥주=new ImageIcon[]{
				new ImageIcon("./image/Ani/beer1.jpg"),
				new ImageIcon("./image/Ani/beer2.jpg"),
				new ImageIcon("./image/Ani/beer3.jpg")};
		
		화가 = new Thread() {
			@Override
			public void run() {
				while(true) {
					if(action.equals("start")) {
						if(max<카드돌린횟수) break;
						카드돌리기();
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
									file = new File("./audio/회피.wav");
									AudioInputStream stream = AudioSystem.getAudioInputStream(file);
									clip = AudioSystem.getClip();
									clip.open(stream);
									clip.loop(5);
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else {
								try {
									file = new File("./audio/뱅.wav");
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
							file = new File("./audio/쓰으읍.wav");
							try {
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==4) {
							file = new File("./audio/마시기.wav");
							try {
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==9) {
							file = new File("./audio/트름.wav");
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
								file = new File("./audio/강탈1.wav");
								AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								clip = AudioSystem.getClip();
								clip.open(stream);
								clip.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(x==50) {
							try {
								file = new File("./audio/강탈2.wav");
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
					}else if(action.equals("bang")) {
						
					}else if(action.equals("bang")) {
						
					}
					repaint();
					x++;
				}
				System.out.println("끝");
				화가.interrupt();
			}
		};
		
		check = new boolean[2];
		action = "";
	}
	
	private int[] 카드개수;
	private Point[] 카드현재좌표;
	private int max;//카드를 제일 많이 가지고 있는 개수
	private int 카드돌린횟수;
	
	//시작 애니메이션, 파라미터인 배열의 값으로 인원수 구분
	//최소인원 4명: 3,5,6 번째 원소 0
	//5명: 3,6 번째 원소 0
	//6명: 6번째 원소 0
	//7명: 배열의 모든 원소들이 3이상
	public void startAnimation(int... cards) throws Exception {
		action="start";
		
		max = 0;
		카드돌린횟수 = 0;
		카드개수 = cards;//카드 개수를 저장하고
		카드현재좌표 = new Point[cards.length];//카드현재좌표들을 포인트 배열로만든다
		
		//카드를 그리기위한 좌표초기화
		//카드 개수가 제일 많은 수를 저장
		for(int i=0; i<cards.length; i++) {
			if(max<cards[i]) max = cards[i];
			카드현재좌표[i] = new Point((int)screen.getWidth()/2-카드뒷면.getIconWidth()/2,(int)screen.getHeight()/2-카드뒷면.getIconHeight()/2);
		}
		
		//소리 세팅하고 loop로 돌린다
		File file = new File("./audio/카드돌리기.wav");
		AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(stream);
		clip.loop(max-1);
		
		//그리기 시작
		화가.start();
	}
	
	private boolean[] check;
	private void 카드돌리기() {
		for(int i=0; i<카드개수.length; i++) {
			if(i==0) 카드현재좌표[i].y+=2;//맨 아래
			else if(i==1) 카드현재좌표[i].x-=4;//맨 왼쪽
			else if(i==2) {//상단 1번째
				카드현재좌표[i].x-=4;
				카드현재좌표[i].y-=2;
			}else if(i==3) {//상단 2번째
				카드현재좌표[i].x--;
				카드현재좌표[i].y-=2;
			}else if(i==4) {//상단 3번째
				카드현재좌표[i].x++;
				카드현재좌표[i].y-=2;
			}else if(i==5) {//상단 4번째
				카드현재좌표[i].x+=4;
				카드현재좌표[i].y-=2;
			}else if(i==6) 카드현재좌표[i].x+=4;//맨 오른쪽
		}
		
		if(카드현재좌표[0].getY()==790) {
			for(int i=0; i<카드현재좌표.length; i++) 
				카드현재좌표[i].setLocation((int)screen.getWidth()/2-카드뒷면.getIconWidth()/2,(int)screen.getHeight()/2-카드뒷면.getIconHeight()/2);
			카드돌린횟수++;
		}
	}
	
	private int[] caster;
	private int[] goal;
	private boolean evasion;
	public void bangAnimation(int caster, int goal, boolean evasion) {//뱅 애니메이션
		action="bang";
		this.caster=GameFrame.유저좌표[caster-1];
		this.goal=GameFrame.유저좌표[goal-1];
		this.evasion=evasion;
		File file = new File("./audio/뱅웃음.wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		화가.start();
	}
	
	public void beerAnimation(int caster) {//맥주 애니메이션
		action="beer";
		this.caster=GameFrame.유저좌표[caster-1];
		File file = new File("./audio/캔따기.wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		화가.start();
//		연주가.start();
	}

	public void takeAnimation(int caster, int goal) {//강탈 애니메이션
		action="take";
		this.caster=GameFrame.유저좌표[caster-1];
		this.goal=GameFrame.유저좌표[goal-1];
		화가.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//덱을 언제나 가운데에 그림
		덱.paintIcon(this, g, (int)screen.getWidth()/2-덱.getIconWidth()/2, (int)screen.getHeight()/2-덱.getIconHeight()/2);
		
		switch(action) {
		case("start"):
			for(int i=0; i<카드개수.length; i++) {
				//카드돌린회수보다 카드개수가 크면 암것도 안함
				if(카드돌린횟수<카드개수[i]) 카드뒷면.paintIcon(this, g, (int)카드현재좌표[i].getX(), (int)카드현재좌표[i].getY()); 
			}
			break;
		case("bang"):
			if(x<2000) 	뱅.paintIcon(this, g, caster[0], caster[1]);
			else if(evasion) {
				if(x<2007){
					if(check[0]) {
						회피[0].paintIcon(this, g, goal[0], goal[1]);
						check[0]=false;
					}else {
						회피[1].paintIcon(this, g, goal[0], goal[1]);
						check[0]=true;
					}
				}
			}else if(x<3000){
				폭발.paintIcon(this, g, goal[0], goal[1]);
			}
			break;
		case("beer"): 
			if(x<12) 맥주[x%3].paintIcon(this, g, caster[0], caster[1]);
			break;
		case("take"):
			if(x<80) 강탈.paintIcon(this, g, caster[0], caster[1]);
			break;
		}
	}
}
