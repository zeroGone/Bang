package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AniPanel extends JPanel implements Runnable{
	private Thread drawer;
	private Map<String, ImageIcon> images;//이미지들 저장
	private final Point[] USER_SEAT_LOCATION = {
			new Point(894,750),
			new Point(240,440),
			new Point(240,170),
			new Point(680,170),
			new Point(1000,170),
			new Point(1480,170),
			new Point(1560,440)};
	private Point center;
	private Point[] cardLocation;
	private Point[] distance;//카드 거리계산
	private String action;//어떤 애니메이션인지 구분
	private Clip clip;//음악
	private int caster;//시전자 인덱스
	private int cardDrawNum;
	private int cardDrawCount;//드로우 몇장가는지
	private int[] cards;
	private int startAniCardMaxIndex;//카드를 제일 많이 가지고 있는 개수


	//생성자  
	//화면 크기에 맞게 설정, 이미지들 넣어줌
	public AniPanel() {
		this.action="";
		this.setOpaque(false);
		this.setBounds(0, 0, (int)GameFrame.screen.getWidth(), (int)GameFrame.screen.getHeight());
		images = new HashMap<String, ImageIcon>();
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/deck.jpg"));
		images.put("덱", new ImageIcon(image.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/back.jpg"));
		images.put("카드뒷면", new ImageIcon(image.getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH)));
		images.put("뱅", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/뱅.jpg")));
		images.put("강탈", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/강탈.jpg")));
		images.put("폭발", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/폭발.jpg")));
		images.put("회피1", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/회피1.jpg")));
		images.put("회피2", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/회피2.jpg")));
		images.put("beer1", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/beer1.jpg")));
		images.put("beer2", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/beer2.jpg")));
		images.put("beer3", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/beer3.jpg")));
		image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/역마차.jpg"));
		images.put("역마차", new ImageIcon(image.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH)));
		images.put("웰스파고은행1", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/웰스파고은행1.png")));
		images.put("웰스파고은행2", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/웰스파고은행2.png")));
		images.put("웰스파고은행3", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/웰스파고은행3.png")));
		images.put("웰스파고은행4", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/웰스파고은행4.png")));
		images.put("웰스파고은행5", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/웰스파고은행5.png")));
		image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/인디언.jpg"));
		images.put("인디언", new ImageIcon(image.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH)));
		images.put("다이너마이트", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/다이너마이트.jpg")));
		image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/핵폭발.jpg"));
		images.put("핵폭발", new ImageIcon(image.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH)));
		image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/감옥.jpg"));
		images.put("감옥", new ImageIcon(image.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH)));
		image = new ImageIcon(getClass().getClassLoader().getResource("image/Ani/결투.png"));
		images.put("결투", new ImageIcon(image.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH)));
		images.put("해골", new ImageIcon(getClass().getClassLoader().getResource("image/Ani/해골.jpg")));
		center = new Point((int)GameFrame.screen.getWidth()/2-images.get("카드뒷면").getIconWidth()/2,
								(int)GameFrame.screen.getHeight()/2-images.get("카드뒷면").getIconHeight()/2);
		cardLocation  = new Point[]{
				new Point(center.x, center.y),
				new Point(center.x, center.y),
				new Point(center.x, center.y),
				new Point(center.x, center.y),
				new Point(center.x, center.y),
				new Point(center.x, center.y),
				new Point(center.x, center.y)
		};
		distance = new Point[7];
	}
	
	//오디오 파일 셋팅 메소드
	private void setClip(String fileName) {
		File file = new File(getClass().getClassLoader().getResource(String.format("audio/%s.wav", fileName)).getPath());
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
		} catch (Exception e) {
			System.out.println("오디어파일없음");
		}
	}

	public void cardDrawAnimation(int caster, int cardNum) {
		this.caster = caster;
		this.action="draw";
		this.distance[caster]= new Point((USER_SEAT_LOCATION[caster].x-cardLocation[caster].x)/100, (USER_SEAT_LOCATION[caster].y-cardLocation[caster].y)/100);
		this.cardDrawNum=cardNum;
		this.cardDrawCount=0;
		this.setClip("start");
		this.clip.loop(cardNum-1);
		this.drawer = new Thread(this);
		this.drawer.start();
	}
	
	public void startAnimation(int... cards){
		this.action="start";
		this.cards = cards;
		this.cardDrawCount = 0;
		for(int i=0; i<cards.length; i++) {
			if(cards[startAniCardMaxIndex]<cards[i]) startAniCardMaxIndex = i;
			distance[i] = new Point((USER_SEAT_LOCATION[i].x-cardLocation[i].x)/100, (USER_SEAT_LOCATION[i].y-cardLocation[i].y)/100);
		}
		this.setClip("start");
		this.clip.loop(cards[startAniCardMaxIndex]-1);
		this.drawer = new Thread(this);
		this.drawer.start();
	}
	
	private boolean check;
	
	private int goal;
	private boolean evasion;
	
	//뱅 애니메이션 : 시전자, 목표, 회피여부
	public void bangAnimation(int caster, int goal, boolean evasion) {
		this.action="bang";
		this.cardDrawCount = 0;
		this.caster=caster;
		this.cardLocation[caster] = new Point(this.USER_SEAT_LOCATION[caster].x, this.USER_SEAT_LOCATION[caster].y);
		this.cardLocation[goal] = new Point(this.USER_SEAT_LOCATION[goal].x, this.USER_SEAT_LOCATION[goal].y);
		this.goal=goal;
		this.evasion=evasion;
		this.setClip("bangCaster");
		this.clip.start();
		this.drawer = new Thread(this);
		this.drawer.start();
	}
	
	public void EOHAnimation() {
		if(this.evasion) {
			this.action="evasion";
			this.setClip("evasion");
			this.clip.loop(3);
		}else {
			this.action="hit";
			this.setClip("bang");
			this.clip.start();

		}
		this.cardDrawCount = 0;
		this.drawer = new Thread(this);
		this.drawer.start();
	}
	
	public void beerAnimation(int caster) {//맥주 애니메이션
		action="beer";
		this.caster=caster;
		setClip("캔따기");
		clip.start();
		timer = 0;
		drawer = new Thread(this);
		drawer.start();
	}

	public void takeAnimation(int caster, int goal) {//강탈 애니메이션
		action="take";
		this.caster=caster;
		this.goal=goal;
		timer = 0;
		drawer = new Thread(this);
		drawer.start();
	}

	public void machineGunAnimation() {
		action = "machineGun";
		timer = 0;
		setClip("기관총");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void stageCoachAnimation() {
		action = "stageCoach";
		timer = 0;
		point = new Point(USER_SEAT_LOCATION[1].x, USER_SEAT_LOCATION[1].y);
		setClip("역마차");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void bankAnimation() {
		action = "bank";
		timer = 0;
		point = new Point((int)GameFrame.screen.getWidth()/2-웰스파고은행[2].getIconWidth()/2, (int)GameFrame.screen.getHeight()/2-웰스파고은행[2].getIconHeight()/2);
		setClip("웰스파고은행");
		clip.loop(3);
		drawer = new Thread(this);
		drawer.start();
	}

	public void indianAnimation() {
		action = "indian";
		timer = 0;
		point = new Point(USER_SEAT_LOCATION[1].x, USER_SEAT_LOCATION[1].y);
		setClip("인디언");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void dynamiteAnimation(int caster, boolean check) {
		action="dynamite";
		this.caster=caster;
		this.check=check;
		point = new Point(0,0);
		setClip("퓨즈");
		clip.start();
		timer = 0;
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void prisonAnimation(int goal) {
		action="prison";
		this.goal=goal;
		setClip("감옥");
		clip.start();
		timer = 0;
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void fightAnimation() {
		action="fight";
		setClip("결투");
		clip.start();
		point= new Point((int)GameFrame.screen.getWidth()/2-결투.getIconWidth()/2,100);
		timer = 0;
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void dieAnimation(int caster) {
		action="die";
		this.caster=caster;
		timer = 0;
		setClip("죽음");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}
	
	private int timer;//시간을 재기 위한 변수

	@Override
	public void run() {
		try {
			thread:
			while(true) {
				switch(action) {
				case "start":
					if(cards[startAniCardMaxIndex]==cardDrawCount) {
						for(int i=0; i<cards.length; i++) cardLocation[i] = new Point(center.x, center.y);
						break thread; 
					}
					if(cardLocation[startAniCardMaxIndex].distance(USER_SEAT_LOCATION[startAniCardMaxIndex])<50) { 
						for(int i=0; i<cards.length; i++) {
							cardLocation[i].x=this.center.x; 
							cardLocation[i].y=this.center.y; 
						}
						this.cardDrawCount++; 
					}
					for(int i=0; i<cards.length; i++) {
						cardLocation[i].x+=distance[i].x;
						cardLocation[i].y+=distance[i].y;		
					}
					break;
				case "draw":
					if(this.cardDrawCount==this.cardDrawNum) { cardLocation[caster] = new Point(center.x, center.y); break thread; }
					if(cardLocation[caster].distance(USER_SEAT_LOCATION[caster])<50) { 
						cardLocation[caster].x=this.center.x; 
						cardLocation[caster].y=this.center.y; 
						this.cardDrawCount++; 
					}
					cardLocation[caster].x+=distance[caster].x;
					cardLocation[caster].y+=distance[caster].y;					
					break;
				case "bang":
					if(cardDrawCount==30) { 
						cardLocation[caster] = new Point(center.x, center.y);
						this.EOHAnimation();
						break thread; 
					};
					if(cardLocation[caster].distance(USER_SEAT_LOCATION[caster])>150) { check = !check; this.cardDrawCount++; }
					if(check) cardLocation[caster].y+=5;
					else cardLocation[caster].y-=5;
					break;
				case "evasion":
					if(cardDrawCount==800) { cardLocation[goal] = new Point(center.x, center.y); break thread; } 
					if(cardDrawCount%100==0) check=!check;
					this.cardDrawCount++; 
					break;
				case "hit":
					if(cardDrawCount==200) { cardLocation[goal] = new Point(center.x, center.y); break thread; } 
					this.cardDrawCount++; 
					break;
				}
				Thread.sleep(2);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//덱을 언제나 가운데에 그림
		images.get("덱").paintIcon(this, g, this.center.x, this.center.y);
		switch(action) {
		case "start":
			for(int i=0; i<cards.length; i++) 
				if(cards[i]>this.cardDrawCount&&this.cardDrawCount<this.cards[startAniCardMaxIndex])
					images.get("카드뒷면").paintIcon(this, g, cardLocation[i].x, cardLocation[i].y);
			break;
		case "draw":
			if(this.cardDrawCount<this.cardDrawNum) images.get("카드뒷면").paintIcon(this, g, cardLocation[caster].x, cardLocation[caster].y);
			break;
		case "bang":
			if(cardDrawCount<30) images.get("뱅").paintIcon(this, g, cardLocation[caster].x, cardLocation[caster].y);
			break;
		case "evasion":
			if(cardDrawCount<800) {
				if(check) images.get("회피1").paintIcon(this, g, cardLocation[goal].x, cardLocation[goal].y);
				else images.get("회피2").paintIcon(this, g, cardLocation[goal].x, cardLocation[goal].y);
			}
			break;
		case "hit":
			if(cardDrawCount<200) images.get("폭발").paintIcon(this, g, cardLocation[goal].x, cardLocation[goal].y);
			break;
//		case("bang"):
//			if(timer<2000) 	뱅.paintIcon(this, g, 유저자리좌표[caster].x+뱅.getIconWidth()/3, 유저자리좌표[caster].y+뱅.getIconHeight()/3);
//			else if(evasion) {
//				if(timer<2007){
//					if(check) {
//						회피[0].paintIcon(this, g, 유저자리좌표[goal].x, 유저자리좌표[goal].y+20);
//						check=false;
//					}else {
//						회피[1].paintIcon(this, g, 유저자리좌표[goal].x+회피[0].getIconWidth()/2, 유저자리좌표[goal].y+20);
//						check=true;
//					}
//				}
//			}else if(timer<3000){
//				폭발.paintIcon(this, g, 유저자리좌표[goal].x+폭발.getIconWidth()/3, 유저자리좌표[goal].y+폭발.getIconWidth()/3);
//			}
//			break;
//		case("beer"): 
//			if(timer<12) 맥주[timer%3].paintIcon(this, g, 유저자리좌표[caster].x+맥주[0].getIconWidth()/2, 유저자리좌표[caster].y+20);
//			break;
//		case("take"):
//			if(timer<50) 강탈.paintIcon(this, g, 유저자리좌표[caster].x+강탈.getIconWidth()/2, 유저자리좌표[caster].y+강탈.getIconHeight()/3);
//			else if(timer<80) 강탈.paintIcon(this, g, 유저자리좌표[goal].x+강탈.getIconWidth()/2, 유저자리좌표[goal].y+강탈.getIconHeight()/3);
//			break;
//		case("machineGun"):
//			if(timer<48) for(int i=0; i<10; i++) 폭발.paintIcon(this, g, (int)(Math.random()*1920), (int)(Math.random()*1020));
//			break;
//		case("stageCoach"):
//			if(timer<1100) 역마차.paintIcon(this, g, point.x, point.y);
//			break;
//		case("bank"):
//			if(timer<95) 웰스파고은행[timer%5].paintIcon(this, g, point.x, point.y);
//			break;
//		case("indian"):
//			if(timer<1150) 인디언.paintIcon(this, g, point.x, point.y);
//			break;
//		case("dynamite"):
//			if(timer<3) 다이너마이트.paintIcon(this, g, 유저자리좌표[caster].x+150, 유저자리좌표[caster].y);
//			else if(check&&timer<25) 핵폭발.paintIcon(this, g, 유저자리좌표[caster].x, 유저자리좌표[caster].y);
//			break;
//		case("prison"):
//			if(timer<5) 감옥.paintIcon(this, g, 유저자리좌표[goal].x, 유저자리좌표[goal].y);
//			break;
//		case("fight"):
//			if(timer<250) 결투.paintIcon(this, g, point.x, point.y);
//			break;
//		case("hit"):
//			if(timer<1000) 폭발.paintIcon(this, g, 유저자리좌표[caster].x+폭발.getIconWidth()/3, 유저자리좌표[caster].y+폭발.getIconWidth()/3);
//			break;
//		case("die"):
//			if(timer<4) 해골.paintIcon(this, g, 유저자리좌표[caster].x+해골.getIconWidth()/3, 유저자리좌표[caster].y+20);
//			break;
		}
		repaint();
	}
}
//}else if(action.equals("bang")) {
//	if(timer<2000) {
//		if(check) {
//			유저자리좌표[caster].y+=30;
//			check=false;
//		}else {
//			유저자리좌표[caster].y-=30;
//			check=true;
//		}
//		Thread.sleep(1);
//	}else if(timer==2000) {
//		if(evasion) {
//			clipSet("회피");
//			clip.loop(5);
//		}else {
//			clipSet("뱅");
//			clip.start();
//		}
//	}else {
//		if(evasion) {
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if(timer>2010) break;
//		}else {
//			if(check) {
//				유저자리좌표[goal].x+=(int)(Math.random()*10);
//				유저자리좌표[goal].y+=(int)(Math.random()*10);
//				check=false;
//			}else {
//				유저자리좌표[goal].x-=(int)(Math.random()*10);
//				유저자리좌표[goal].y-=(int)(Math.random()*10);
//				check=true;
//			}
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(timer>3000) break;
//		}
//	}
//}else if(action.equals("beer")) {
//	try {
//		Thread.sleep(500);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	if(timer==2) {
//		clipSet("쓰으읍");
//		clip.start();
//	}else if(timer==4) {
//		clipSet("마시기");
//		clip.start();
//	}else if(timer==9) {
//		clipSet("트름");
//		clip.start();
//	}
//	else if(timer==12) break;
//}else if(action.equals("take")) {
//	try {
//		Thread.sleep(50);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	if(check) {
//		if(timer<50) 유저자리좌표[caster].y+=10;
//		else 유저자리좌표[goal].y+=10;
//		check=false;
//	}else {
//		if(timer<50) 유저자리좌표[caster].y-=10;
//		else 유저자리좌표[goal].y-=10;
//		check=true;
//	}
//	if(timer==10) {
//		clipSet("강탈1");
//		clip.start();
//	}else if(timer==50) {
//		clipSet("강탈2");
//		clip.start();
//	}else if(timer==80) break;
//}else if(action.equals("machineGun")) {
//	try {
//		Thread.sleep(100);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	if(timer==48) break;
//}else if(action.equals("stageCoach")) {
//	try {
//		Thread.sleep(1);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	point.x++;
//	if(timer==1100) break;
//}else if(action.equals("bank")) {
//	try {
//		Thread.sleep(30);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	if(timer==95) break;
//}else if(action.equals("indian")) {
//	try {
//		Thread.sleep(9);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	point.x++;
//	if(timer==1150) break;
//}else if(action.equals("dynamite")) {
//	try {
//		Thread.sleep(500);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	if(timer==3) {
//		if(check) {
//			clipSet("다이너마이트");
//			clip.start();
//		}else break;
//	}else if(timer==25) break;
//}else if(action.equals("prison")) {
//	try {
//		Thread.sleep(500);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	if(timer==5) break;
//}else if(action.equals("fight")) {
//	try {
//		Thread.sleep(10);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	point.y++;
//	if(timer==250) break;
//}else if(action.equals("hit")) {
//	if(check) {
//		유저자리좌표[goal].x+=(int)(Math.random()*10);
//		유저자리좌표[goal].y+=(int)(Math.random()*10);
//		check=false;
//	}else {
//		유저자리좌표[goal].x-=(int)(Math.random()*10);
//		유저자리좌표[goal].y-=(int)(Math.random()*10);
//		check=true;
//	}
//	try {
//		Thread.sleep(1);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	if(timer==1000) break;
//}else if(action.equals("die")) {
//	try {
//		Thread.sleep(500);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	if(timer==4) break;
//}
//timer++;
//}
//} catch (InterruptedException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//}
//}