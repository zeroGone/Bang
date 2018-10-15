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

public class AniPanel extends JPanel implements Runnable{
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
	private ImageIcon 카드뒷면;
	private ImageIcon 뱅;
	private ImageIcon 강탈;
	private ImageIcon 폭발;
	private ImageIcon[] 회피;
	private ImageIcon[] 맥주;
	private ImageIcon 역마차;
	private ImageIcon[] 웰스파고은행;
	
	private String action;
	private Clip clip;
	private int timer;
	private Point point;
	
	//생성자  화면 크기 받고, 이미지를 셋팅해줌
	public AniPanel(Dimension screen) {
		setOpaque(false);
		this.screen = screen;
		this.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());

		action = "";
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
		
		역마차 = new ImageIcon("./image/Ani/역마차.jpg");
		역마차 = new ImageIcon(역마차.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));
		
		웰스파고은행 = new ImageIcon[] {
				new ImageIcon("./image/Ani/웰스파고은행1.png"),
				new ImageIcon("./image/Ani/웰스파고은행2.png"),
				new ImageIcon("./image/Ani/웰스파고은행3.png"),
				new ImageIcon("./image/Ani/웰스파고은행4.png"),
				new ImageIcon("./image/Ani/웰스파고은행5.png")};
		
	}
	
	private void clipSet(String fileName) {
		File file = new File("./audio/"+fileName+".wav");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
		} catch (Exception e) {
			System.out.println("오디어파일없음");
		}
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
	public void startAnimation(int... cards){
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
		clipSet("카드돌리기");
		clip.loop(max-1);
		//그리기 시작
		화가 = new Thread(this);
		화가.start();
	}
	
	private boolean check;
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
	
	private int caster;
	private int goal;
	private boolean evasion;
	
	//뱅 애니메이션 : 시전자, 목표, 회피여부
	public void bangAnimation(int caster, int goal, boolean evasion) {
		action="bang";
		this.caster=caster;
		this.goal=goal;
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
		timer=0;
		화가 = new Thread(this);

		화가.start();
	}
	
	public void beerAnimation(int caster) {//맥주 애니메이션
		action="beer";
		this.caster=caster;
		clipSet("캔따기");
		clip.start();
		timer = 0;
		화가 = new Thread(this);

		화가.start();
	}

	public void takeAnimation(int caster, int goal) {//강탈 애니메이션
		action="take";
		this.caster=caster;
		this.goal=goal;
		timer = 0;
		화가 = new Thread(this);
		화가.start();
	}

	public void machineGunAnimation() {
		action = "machineGun";
		timer = 0;
		clipSet("기관총");
		clip.start();
		화가 = new Thread(this);
		화가.start();
	}
	
	public void stageCoachAnimation() {
		action = "stageCoach";
		timer = 0;
		clipSet("역마차");
		clip.start();
		화가 = new Thread(this);
		화가.start();
	}
	
	public void bankAnimation() {
		action = "bank";
		timer = 0;
		point = new Point((int)screen.getWidth()/2-웰스파고은행[2].getIconWidth()/2, (int)screen.getHeight()/2-웰스파고은행[2].getIconHeight()/2);
		clipSet("웰스파고은행");
		clip.loop(3);
		화가 = new Thread(this);
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
				if(카드돌린횟수<카드개수[i]) 카드뒷면.paintIcon(this, g, 카드현재좌표[i].x, 카드현재좌표[i].y); 
			}
			break;
		case("bang"):
			if(timer<2000) 	뱅.paintIcon(this, g, 유저자리좌표[caster].x, 유저자리좌표[caster].y);
			else if(evasion) {
				if(timer<2007){
					if(check) {
						회피[0].paintIcon(this, g, 유저자리좌표[goal].x, 유저자리좌표[goal].y);
						check=false;
					}else {
						회피[1].paintIcon(this, g, 유저자리좌표[goal].x, 유저자리좌표[goal].y);
						check=true;
					}
				}
			}else if(timer<3000){
				폭발.paintIcon(this, g, 유저자리좌표[goal].x, 유저자리좌표[goal].y);
			}
			break;
		case("beer"): 
			if(timer<12) 맥주[timer%3].paintIcon(this, g, 유저자리좌표[caster].x, 유저자리좌표[caster].y);
			break;
		case("take"):
			if(timer<50) 강탈.paintIcon(this, g, 유저자리좌표[caster].x, 유저자리좌표[caster].y);
			else if(timer<80) 강탈.paintIcon(this, g, 유저자리좌표[goal].x, 유저자리좌표[goal].y);
			break;
		case("machineGun"):
			if(timer<48) for(int i=0; i<10; i++) 폭발.paintIcon(this, g, (int)(Math.random()*1920), (int)(Math.random()*1020));
			break;
		case("stageCoach"):
			if(timer<1100) 역마차.paintIcon(this, g, 유저자리좌표[1].x, 유저자리좌표[1].y);
			break;
		case("bank"):
			if(timer<95) 웰스파고은행[timer%5].paintIcon(this, g, point.x, point.y);
			break;
		}
	}

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
				if(timer<2000) {
					if(check) {
						유저자리좌표[caster].y+=30;
						check=false;
					}else {
						유저자리좌표[caster].y-=30;
						check=true;
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else if(timer==2000) {
					if(evasion) {
						clipSet("회피");
						clip.loop(5);
					}else {
						clipSet("뱅");
						clip.start();
					}
				}else {
					if(evasion) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(timer>2010) break;
					}else {
						if(check) {
							유저자리좌표[goal].x+=(int)(Math.random()*10);
							유저자리좌표[goal].y+=(int)(Math.random()*10);
							check=false;
						}else {
							유저자리좌표[goal].x-=(int)(Math.random()*10);
							유저자리좌표[goal].y-=(int)(Math.random()*10);
							check=true;
						}
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(timer>3000) break;
					}
				}
			}else if(action.equals("beer")) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(timer==2) {
					clipSet("쓰으읍");
					clip.start();
				}else if(timer==4) {
					clipSet("마시기");
					clip.start();
				}else if(timer==9) {
					clipSet("트름");
					clip.start();
				}
				else if(timer==12) break;
			}else if(action.equals("take")) {
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(check) {
					if(timer<50) 유저자리좌표[caster].y+=10;
					else 유저자리좌표[goal].y+=10;
					check=false;
				}else {
					if(timer<50) 유저자리좌표[caster].y-=10;
					else 유저자리좌표[goal].y-=10;
					check=true;
				}
				if(timer==10) {
					clipSet("강탈1");
					clip.start();
				}else if(timer==50) {
					clipSet("강탈2");
					clip.start();
				}else if(timer==80) break;
			}else if(action.equals("machineGun")) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(timer==48) break;
			}else if(action.equals("stageCoach")) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				유저자리좌표[1].x++;
				if(timer==1100) break;
			}else if(action.equals("bank")) {
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(timer==95) break;
			}
			System.out.println(timer);
			repaint();
			timer++;
		}
	}
}
