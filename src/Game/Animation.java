package Game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animation extends JPanel implements ActionListener {
	private Timer timer;//타이머
	private ImageIcon image;
	private int x;//x좌표
	private int y;//y좌표
	private String action;//애니메이션 행동 구분을 위한 멤버변수
	private int cardValue;
	private int beerValue;
	private Clip audioClip;
	private File audio;
	
	//0917 수정
	private Dimension screen;
	private int member;
	public Animation(Dimension screen,int num) {
		this.setLayout(null);
		this.screen=screen;
		this.setOpaque(false);
		this.setBounds(0,0, (int)screen.getWidth(), (int)screen.getHeight());
		this.member=num;
	}
	
//	private JLabel[] cards;
	private Graphics 화가;
	private int[] cardsNum;
	protected void gameStart(int... card) {
		timer = new Timer(1,this);
		
		cardsNum=card;//시작할때 멤버마다 가지고 있는 카드 개수;
		
		image = new ImageIcon("./image/Ani/back.jpg");//이미지 불러오기
		
//		cards = new JLabel[cardsNum.length];
		
		int 간격=0;
//		for(int i=0; i<cards.length; i++) {
//			cards[i]=new JLabel(image);
//			cards[i].setBounds(this.screen.width/2-2*image.getIconWidth()+간격, this.screen.height/2-image.getIconHeight()/2, image.getIconWidth(), image.getIconHeight());
//			add(cards[i]);
//			간격+=20;
//		}
		
		action="start";
		timer.start();
	}
	
	static int count = 0;
	private void startAnimation() {
		System.out.println(++count);
//		for(JLabel card:cards) {
//			card.setLocation(card.getX()-10, card.getY()-10);
//		}
	}
	
//	private void audioStart(String name) {
//		audio = new File("./audio/"+name+".wav");
//		try {
//			AudioInputStream stream = AudioSystem.getAudioInputStream(audio);
//			audioClip = AudioSystem.getClip();
//			audioClip.open(stream);
//		} catch (Exception e) {
//			System.out.println("실패");
//		}
//		audioClip.start();
//	}
//	
//	protected void beer(int seat) {
//		timer = new Timer(500,this);
//		image = new ImageIcon("./image/beer/beer1.jpg");
//		
//		setSize(image.getIconWidth(),image.getIconHeight());
//		
//		if(seat==1) { x=150; y=40; }
//		else if(seat==2) { x=1050; y=40; }
//		else if(seat==3) { x=150; y=410; }
//		else if(seat==4) { x=1050; y=410; }
//		else if(seat==5) { x=150; y=780; }
//		else if(seat==6) { x=1050; y=780; }
//		else if(seat==7) { x=600; y=780; } 
//		
//		this.audioStart("캔따기");
//		
//		beerValue=1;
//		action="beer";
//		timer.start();
//	}
//	private void beerAnimation() {
//		removeAll();
//		if(beerValue==1||beerValue==4||beerValue==7) {
//			image = new ImageIcon("./image/beer/beer2.jpg");
//			if(beerValue==4) this.audioStart("마시기");
//			else if(beerValue==7) this.audioStart("트름");
//		}
//		else if(beerValue==2||beerValue==5) {
//			if(beerValue==2) this.audioStart("쓰으읍");
//			image = new ImageIcon("./image/beer/beer3.jpg");
//		}
//		else if(beerValue==3||beerValue==6) image = new ImageIcon("./image/beer/beer1.jpg");
//		else if(beerValue==5) image = new ImageIcon("./image/beer/beer3.jpg");
//		
//		if(beerValue==8) {
//			audioClip.stop();
//			timer.stop();
//		}else add(new JLabel(image));
//		
//		beerValue++;
//		this.revalidate();
//	}
//	
//	private int casterX;
//	private int casterY;
//	private int goalX;
//	private int goalY;
//	private JLabel caster;
//	private JLabel goal;
//	private boolean evasion;
//	
//	protected void bang(int seat, int goal, boolean evasion) {
//		timer = new Timer(50,this);
//		setLayout(null);
//		setSize(1400,1080);
//		
//		this.evasion=evasion;
//		
//		image = new ImageIcon("./image/뱅.jpg");
//		caster = new JLabel(image);
//		
//		if(seat==1) { casterX=150; casterY=40; }
//		else if(seat==2) { casterX=1050; casterY=40; }
//		else if(seat==3) { casterX=150; casterY=410; }
//		else if(seat==4) { casterX=1050; casterY=410; }
//		else if(seat==5) { casterX=150; casterY=780; }
//		else if(seat==6) { casterX=1050; casterY=780; }
//		else if(seat==7) { casterX=600; casterY=780; } 
//		
//		caster.setBounds(casterX, casterY, image.getIconWidth(), image.getIconHeight());
//		add(caster);
//		
//		if(evasion) image = new ImageIcon("./image/회피1.jpg");
//		else image = new ImageIcon("./image/빵야.jpg");
//		
//		this.goal = new JLabel(image);
//		
//		if(goal==1) { goalX=150; goalY=40; }
//		else if(goal==2) { goalX=1050; goalY=40; }
//		else if(goal==3) { goalX=150; goalY=410; }
//		else if(goal==4) { goalX=1050; goalY=410; }
//		else if(goal==5) { goalX=150; goalY=780; }
//		else if(goal==6) { goalX=1050; goalY=780; }
//		else if(goal==7) { goalX=600; goalY=780; } 
//		
//		this.goal.setBounds(goalX,goalY,image.getIconWidth(),image.getIconHeight());
//		this.goal.setVisible(false);
//		add(this.goal);
//		
//		this.audioStart("뱅웃음");
//		action="bang";
//		timer.start();
//		
//		bangCount=0;
//	}
//	
//	private int bangCount;
//	private boolean bangCheck;
//	
//	private void bangAnimation() {
//		if(bangCount<60) {
//			if(bangCheck) {
//				casterY+=10;
//				bangCheck=false;
//			}
//			else {
//				casterY-=10;
//				bangCheck=true;
//			}
//			bangCount++;
//			caster.setLocation(casterX,casterY);
//		}else if(bangCount<70){
//			this.goal.setVisible(true);
//			if(evasion) {
//				remove(this.goal);
//				this.audioStart("회피");
//				if(bangCount%2==1) image = new ImageIcon("./image/회피1.jpg");
//				else  image = new ImageIcon("./image/회피2.jpg");
//				goal=new JLabel(image);
//				goal.setBounds(goalX,goalY,image.getIconWidth(),image.getIconHeight());
//				add(this.goal);
//			}else {
//				this.audioStart("뱅");
//				goalX+=((int)(Math.random()*50)-(int)(Math.random()*50));
//				goalY+=((int)(Math.random()*50)-(int)(Math.random()*50));
//				goal.setLocation(goalX, goalY);
//			}
//			bangCount++;
//		}else {
//			this.removeAll();
//			timer.stop();
//		}
//	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(action) {
		case "start": startAnimation();
		break;
//		case "beer": beerAnimation();
//		break;
//		case "bang": bangAnimation();
//		break;
		}
//		this.repaint();
	}
	
	
//	@Override
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		setLocation(x,y);
//	}
}
