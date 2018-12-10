package Game;

import java.awt.Font;
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
import javax.swing.JLabel;
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
	private final Point center;
	private Point[] cardLocation;
	private Point[] distance;//카드 거리계산
	private String action;//어떤 애니메이션인지 구분
	private Clip clip;//음악
	private int caster;//시전자 인덱스
	private int goal;//목표자 인덱스
	private int cardDrawNum;
	private int cardDrawCount;//드로우 몇장가는지
	private int[] cards;
	private int startAniCardMaxIndex;//카드를 제일 많이 가지고 있는 개수
	private boolean check;
	private boolean evasion;
	private static boolean state = false;

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
		this.cardLocation  = new Point[7];
		for(int i=0; i<7; i++) cardLocation[i] = new Point(center.x, center.y);
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
	
	private MOCCard openCard;
	public void cardOpenAnimation(String... info) throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);

		this.action="open";
		this.setClip("start");
		this.drawer = new Thread(this);
		this.drawer.start();
		this.cardDrawCount=0;

		openCard = new MOCCard(400,600, info[0], info[1], info[2], Integer.parseInt(info[3]));
		openCard.imageSet();
		openCard.setLocation(this.getWidth()/2-openCard.getWidth()/2, this.getHeight()/2-openCard.getHeight()/2);
	}

	public void cardDrawAnimation(int caster, int cardNum) throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
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
		AniPanel.state = true;
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
	
	//뱅 애니메이션 : 시전자, 목표, 회피여부
	public void bangAnimation(int caster, int goal, Boolean evasion) throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
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
	
	public void beerAnimation(int caster) throws InterruptedException {//맥주 애니메이션
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		action="beer";
		this.caster=caster;
		this.cardLocation[caster] = new Point(this.USER_SEAT_LOCATION[caster].x, this.USER_SEAT_LOCATION[caster].y);
		setClip("can");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}

	public void takeAnimation(int caster, int goal) throws InterruptedException {//강탈 애니메이션
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		this.action="take";
		this.caster=caster;
		this.goal=goal;
		this.cardDrawCount=0;
		this.cardLocation[caster] = new Point(this.USER_SEAT_LOCATION[caster].x, this.USER_SEAT_LOCATION[caster].y);
		this.cardLocation[goal] = new Point(this.USER_SEAT_LOCATION[goal].x, this.USER_SEAT_LOCATION[goal].y);
		this.setClip("take1");
		this.clip.start();
		this.drawer = new Thread(this);
		this.drawer.start();
	}

	public void machineGunAnimation() throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		this.action = "machineGun";
		this.cardDrawCount=0;
		this.setClip("machineGun");
		this.clip.start();
		this.drawer = new Thread(this);
		this.drawer.start();
	}
	
	public void stageCoachAnimation() throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		this.action = "stageCoach";
		this.cardLocation[0] = new Point(this.center.x-images.get("역마차").getIconWidth()/2, -300);
		setClip("stageCoach");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void bankAnimation() throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		action = "bank";
		this.cardDrawCount=0;
		setClip("bank");
		clip.loop(2);
		drawer = new Thread(this);
		drawer.start();
	}

	public void indianAnimation() throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		action = "indian";
		this.cardLocation[0] = new Point(-1500, this.center.y-images.get("인디언").getIconHeight()/2);
		setClip("indian");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void dynamiteAnimation(int caster, boolean check) throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		action="dynamite";
		this.caster=caster;
		this.check=check;
		setClip("fuse");
		clip.start();
		this.cardDrawCount=0;
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void prisonAnimation(int goal) throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		action="prison";
		this.goal=goal;
		setClip("prison");
		clip.start();
		this.cardDrawCount=0;
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void fightAnimation() throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		action="fight";
		setClip("fight");
		clip.start();
		this.cardLocation[0] = new Point(this.center.x-images.get("결투").getIconWidth()/2, -800);
		this.cardDrawCount=0;
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void dieAnimation(int caster) throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		action="die";
		this.caster=caster;
		this.cardDrawCount=0;
		setClip("die");
		clip.start();
		drawer = new Thread(this);
		drawer.start();
	}
	
	public void cattleRow(int goal) throws InterruptedException {
		while(AniPanel.state) Thread.sleep(1);
		AniPanel.state = true;
		this.action="cattleRow";
		this.goal=goal;
		this.cardLocation[goal] = new Point(USER_SEAT_LOCATION[goal].x, USER_SEAT_LOCATION[goal].y);
		this.distance[goal]= new Point((USER_SEAT_LOCATION[goal].x-center.x)/100, (USER_SEAT_LOCATION[goal].y-center.y)/100);
		this.cardDrawCount=0;
		this.setClip("start");
		this.clip.start();
		this.drawer = new Thread(this);
		this.drawer.start();
	}

	@Override
	public void run() {
		try {
			thread:
			while(true) {
				switch(action) {
				case "start":
					if(cards[startAniCardMaxIndex]==cardDrawCount) break thread; 
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
					if(this.cardDrawCount==this.cardDrawNum)  break thread; 
					if(cardLocation[caster].distance(USER_SEAT_LOCATION[caster])<50) { 
						cardLocation[caster].x=this.center.x; 
						cardLocation[caster].y=this.center.y; 
						this.cardDrawCount++; 
					}
					cardLocation[caster].x+=distance[caster].x;
					cardLocation[caster].y+=distance[caster].y;					
					break;
				case "open":
					if(this.cardDrawCount>1500) { this.remove(openCard); break thread; }
					if(this.cardDrawCount==500) { this.add(openCard); this.clip.start(); }
					this.cardDrawCount++;
					break;
				case "bang":
					if(cardDrawCount==30) { 
						this.EOHAnimation();
						break thread; 
					};
					if(cardLocation[caster].distance(USER_SEAT_LOCATION[caster])>150) { check = !check; this.cardDrawCount++; }
					if(check) cardLocation[caster].y+=5;
					else cardLocation[caster].y-=5;
					break;
				case "evasion":
					if(cardDrawCount==800)  break thread;
					if(cardDrawCount%100==0) check=!check;
					this.cardDrawCount++; 
					break;
				case "hit":
					if(cardDrawCount==200)  break thread; 
					this.cardDrawCount++; 
					break;
				case "beer":
					if(cardDrawCount==1200) break thread;
					if(cardDrawCount==350) { this.setClip("drinking"); this.clip.start();}
					if(cardDrawCount==1000) { this.setClip("burp"); this.clip.start();}
					this.cardDrawCount++; 
					break;
				case "take":
					if(cardDrawCount==26) break thread;
					if(cardDrawCount<12) {
						if(cardLocation[caster].distance(USER_SEAT_LOCATION[caster])>30) { check = !check; this.cardDrawCount++; }
						if(check) cardLocation[caster].y++;
						else cardLocation[caster].y--;
					}else if(cardDrawCount==12){
						this.setClip("take2");
						this.clip.start();
						this.cardDrawCount++;
					}
					else {
						if(cardLocation[goal].distance(USER_SEAT_LOCATION[goal])>30) { check = !check; this.cardDrawCount++; }
						if(check) cardLocation[goal].y++;
						else cardLocation[goal].y--;
					}
					break;
				case "machineGun":
					if(cardDrawCount==2000) break thread;
					this.cardDrawCount++;
					break;
				case "stageCoach":
					if(cardLocation[0].y==center.y) {this.cardLocation[0] = new Point(center.x, center.y); break thread;};
					cardLocation[0].y++;
					break;
				case "bank":
					if(cardDrawCount==3500) break thread;
					this.cardDrawCount+=5;
					break;
				case "indian":
					if(cardLocation[0].x==2799) {this.cardLocation[0] = new Point(center.x, center.y); break thread;};
					cardLocation[0].x++;
					break;
				case "dynamite":
					if((!check&&cardDrawCount==500)||cardDrawCount==3000) break thread;
					else if(cardDrawCount==500) {
						this.setClip("nuclear");
						this.clip.start();
					}
					this.cardDrawCount++;
					break;
				case "prison":
					if(cardDrawCount==500) break thread;
					this.cardDrawCount++;
					break;
				case "fight":
					if(cardLocation[0].y==center.y) {this.cardLocation[0] = new Point(center.x, center.y); break thread;};
					cardLocation[0].y++;
					break;
				case "die":
					if(cardDrawCount==300) break thread;
					this.cardDrawCount++;
					break;
				case "cattleRow":
					if(cardLocation[goal].distance(center)<50) { this.cardDrawCount++;  break thread; };
					cardLocation[goal].x-=distance[goal].x;
					cardLocation[goal].y-=distance[goal].y;					
					break;
				}
				Thread.sleep(2);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		action = "";
		AniPanel.state=false;
		System.out.println("애니쓰레드종료");
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
		case "open":
			if(this.cardDrawCount<500) images.get("카드뒷면").paintIcon(this, g, cardLocation[caster].x, cardLocation[caster].y);
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
		case "beer":
			if(cardDrawCount<1200) {
				if(cardDrawCount/100%3==0) images.get("beer1").paintIcon(this, g, cardLocation[caster].x, cardLocation[caster].y);
				else if(cardDrawCount/100%3==1) images.get("beer2").paintIcon(this, g, cardLocation[caster].x, cardLocation[caster].y);
				else images.get("beer3").paintIcon(this, g, cardLocation[caster].x, cardLocation[caster].y);
			}
			break;
		case "take":
			if(cardDrawCount<26){
				if(cardDrawCount<12) images.get("강탈").paintIcon(this, g, cardLocation[caster].x, cardLocation[caster].y);
				else images.get("강탈").paintIcon(this, g, cardLocation[goal].x, cardLocation[goal].y);
			}
			break;
		case "machineGun":
			if(cardDrawCount<2000) for(int i=0; i<10; i++) images.get("폭발").paintIcon(this, g, (int)(Math.random()*1920), (int)(Math.random()*1020));
			break;
		case "stageCoach":
			if(cardLocation[0].y!=center.y) images.get("역마차").paintIcon(this, g, cardLocation[0].x, cardLocation[0].y);
			break;
		case "bank":
			if(cardDrawCount<3500) {
				if(cardDrawCount/100%5==0) images.get("웰스파고은행1").paintIcon(this, g, this.center.x-50, this.center.y);
				else if(cardDrawCount/100%5==1) images.get("웰스파고은행2").paintIcon(this, g, this.center.x-50, this.center.y);
				else if(cardDrawCount/100%5==2) images.get("웰스파고은행3").paintIcon(this, g, this.center.x-50, this.center.y);
				else if(cardDrawCount/100%5==3) images.get("웰스파고은행4").paintIcon(this, g, this.center.x-50, this.center.y);
				else images.get("웰스파고은행5").paintIcon(this, g, this.center.x-50, this.center.y);
			}
			break;
		case "indian":
			if(cardLocation[0].x!=center.x) images.get("인디언").paintIcon(this, g, cardLocation[0].x, cardLocation[0].y);
			break;
		case "dynamite":
			if(cardDrawCount<500) images.get("다이너마이트").paintIcon(this, g, this.USER_SEAT_LOCATION[caster].x, this.USER_SEAT_LOCATION[caster].y);
			else if(check&&cardDrawCount<3000) images.get("핵폭발").paintIcon(this, g, this.USER_SEAT_LOCATION[caster].x, this.USER_SEAT_LOCATION[caster].y);
			break;
		case "prison":
			if(this.cardDrawCount<500) images.get("감옥").paintIcon(this, g, this.USER_SEAT_LOCATION[goal].x, this.USER_SEAT_LOCATION[goal].y);
			break;
		case "fight":
			if(cardLocation[0].y!=center.y) images.get("결투").paintIcon(this, g, cardLocation[0].x, cardLocation[0].y);
			break;
		case "die":
			if(cardDrawCount<300) images.get("해골").paintIcon(this, g, this.USER_SEAT_LOCATION[caster].x, this.USER_SEAT_LOCATION[caster].y);
			break;
		case "cattleRow":
			if(cardDrawCount==0) images.get("카드뒷면").paintIcon(this, g, cardLocation[goal].x, cardLocation[goal].y);
			break;
		}
		repaint();
	}

}