package Game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animation extends JPanel implements ActionListener {
	private Timer timer;
	private ImageIcon image;
	private int x;
	private int y;
	private int member;
	private int seat;
	private int[] card;
	private int cardValue;
	private String action;
	private int beerValue;
	
	public Animation() {
		this.setOpaque(false);
	}
	
	protected void gameStart(int member, int[] card) {
		timer = new Timer(1,this);
		this.member=member;
		this.card=card;
		
		x=650;
		y=450;
		seat=0;
		
		image = new ImageIcon("./image/Ä«µåµÞ¸é.jpg");
		add(new JLabel(image));
		setSize(image.getIconWidth(),image.getIconHeight());
		
		action="start";
		timer.start();
	}
	
	private void startAnimation() {
		if(cardValue<card[seat]) {
			if(seat%2==1) x+=8;
			if(seat%2==0&&seat<=4) x-=8;
			if(seat<=1) y-=8;
			if(seat>=4) y+=8; 
			
			if(x<450||y<300||x>850||y>690) {
				cardValue++;
				x=650;
				y=450;
			}
		}else {
			cardValue=0;
			seat++;
			if(seat==7) {
				timer.stop();
				this.removeAll();
			}
		}
	}
	
	protected void beer(int seat) {
		timer = new Timer(500,this);
		image = new ImageIcon("./image/beer/beer1.jpg");
		
		setSize(image.getIconWidth(),image.getIconHeight());
		
		if(seat==1) { x=150; y=40; }
		else if(seat==2) { x=1050; y=40; }
		else if(seat==3) { x=150; y=410; }
		else if(seat==4) { x=1050; y=410; }
		else if(seat==5) { x=150; y=780; }
		else if(seat==6) { x=1050; y=780; }
		else if(seat==7) { x=600; y=780; } 
		
		beerValue=1;
		action="beer";
		timer.start();
	}
	
	private void beerAnimation() {
		removeAll();
		if(beerValue==1||beerValue==4) {
			image = new ImageIcon("./image/beer/beer2.jpg");
			add(new JLabel(image));
			beerValue++;
		}else if(beerValue==2||beerValue==5) {
			image = new ImageIcon("./image/beer/beer3.jpg");
			add(new JLabel(image));
			beerValue++;
		}else if(beerValue==3||beerValue==6){
			image = new ImageIcon("./image/beer/beer1.jpg");
			add(new JLabel(image));
			beerValue++;
		}else timer.stop();
		this.revalidate();
	}
	
	private int casterX;
	private int casterY;
	private int goalX;
	private int goalY;
	private JLabel caster;
	private JLabel goal;
	
	protected void bang(int seat, int goal) {
		timer = new Timer(50,this);
		setLayout(null);
		setSize(1400,1080);
		
		image = new ImageIcon("./image/¹ð.jpg");
		caster = new JLabel(image);
		
		if(seat==1) { casterX=150; casterY=40; }
		else if(seat==2) { casterX=1050; casterY=40; }
		else if(seat==3) { casterX=150; casterY=410; }
		else if(seat==4) { casterX=1050; casterY=410; }
		else if(seat==5) { casterX=150; casterY=780; }
		else if(seat==6) { casterX=1050; casterY=780; }
		else if(seat==7) { casterX=600; casterY=780; } 
		
		caster.setBounds(casterX, casterY, image.getIconWidth(), image.getIconHeight());
		add(caster);
		
		image = new ImageIcon("./image/»§¾ß.jpg");
		this.goal = new JLabel(image);
		
		if(goal==1) { goalX=150; goalY=40; }
		else if(goal==2) { goalX=1050; goalY=40; }
		else if(goal==3) { goalX=150; goalY=410; }
		else if(goal==4) { goalX=1050; goalY=410; }
		else if(goal==5) { goalX=150; goalY=780; }
		else if(goal==6) { goalX=1050; goalY=780; }
		else if(goal==7) { goalX=600; goalY=780; } 
		
		this.goal.setBounds(goalX,goalY,image.getIconWidth(),image.getIconHeight());
		add(this.goal);
		
		action="bang";
		timer.start();
		
		bangCount=0;
	}
	
	private int bangCount;
	private boolean bangCheck;
	
	private void bangAnimation() {
		if(bangCount<25) {
			if(bangCheck) {
				casterY+=10;
				bangCheck=false;
			}
			else {
				casterY-=10;
				bangCheck=true;
			}
			bangCount++;
			caster.setLocation(casterX,casterY);
		}else if(bangCount<40){
			goalX+=((int)(Math.random()*50)-(int)(Math.random()*50));
			goalY+=((int)(Math.random()*50)-(int)(Math.random()*50));
			bangCount++;
			goal.setLocation(goalX, goalY);
		}else {
			this.removeAll();
			timer.stop();
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(action) {
		case "start": startAnimation();
		break;
		case "beer": beerAnimation();
		break;
		case "bang": bangAnimation();
		break;
		}
		this.repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setLocation(x,y);
	}
}
