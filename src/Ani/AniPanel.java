package Ani;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AniPanel extends JPanel implements Runnable{
	private Thread 화가;
	private Dimension screen;
	
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

		뱅 = new ImageIcon("./image/Ani/back.jpg");
		
		강탈=new ImageIcon("./image/Ani/back.jpg");
		폭발=new ImageIcon("./image/Ani/back.jpg");
		회피=new ImageIcon[]{
				new ImageIcon("./image/Ani/회피1.jpg"),
				new ImageIcon("./image/Ani/회피2.jpg")};
		맥주=new ImageIcon[]{
				new ImageIcon("./image/Ani/beer1.jpg"),
				new ImageIcon("./image/Ani/beer2.jpg"),
				new ImageIcon("./image/Ani/beer3.jpg")};
		action="뱅";
		화가 = new Thread(this);
	}
	
	private ImageIcon 카드뒷면;
	private int[] 카드들;
	private Point[] 카드좌표;
	private int 카드돌린횟수;
	private int max;
	public void startAnimation(int... cards) {//시작 애니메이션
		action="start";
		
		max = 0 ;
		for(int i=0; i<cards.length; i++) {
			if(max<cards[i]) max = i;
		}
		
		카드들 = cards;
		
		카드뒷면=new ImageIcon("./image/Ani/back.jpg");
		카드뒷면=new ImageIcon(카드뒷면.getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH));
		
		카드좌표 = new Point[cards.length];
		
		for(int i=0; i<카드좌표.length; i++) {
			카드좌표[i] = new Point((int)screen.getWidth()/2-카드뒷면.getIconWidth()/2,(int)screen.getHeight()/2-카드뒷면.getIconHeight()/2);
		}
		check = new boolean[2];
		카드돌린횟수 = 0;
		화가.start();
	}
	
	private boolean[] check;
	private void 카드돌리기() {
		for(int i=0; i<카드들.length; i++) {
			if(카드돌린횟수<카드들[i]) {
				if(i==0) {//아래
					카드좌표[i].y++;
				}else if(i==1) {//맨왼쪽
					카드좌표[i].x-=2;
				}else if(i==2) {//상단왼쪽기준 2번째
					if(check[0]) {
						카드좌표[i].x--;
						check[0]=false;
					}else check[0]=true;
					카드좌표[i].y--;
				}else if(i==3) {//맨오른쪽
					카드좌표[i].x+=2;
				}else if(i==4) {//상단왼쪽기준 3번째
					if(check[1]) {
						카드좌표[i].x++;
						check[1]=false;
					}else check[1]=true;
					카드좌표[i].y--;
				}else if(i==5) {//상단왼쪽기준 1번쨰
					카드좌표[i].x-=2;
					카드좌표[i].y--;
				}else if(i==6) {//상단왼쪽기준 4번쨰
					카드좌표[i].x+=2;
					카드좌표[i].y--;
				}
			}
		}

		if(카드좌표[0].getY()==790||카드좌표[2].getY()==110||카드좌표[4].getY()==110||카드좌표[5].getY()==110||카드좌표[6].getY()==110||카드좌표[1].getX()==234||카드좌표[3].getX()==1554) {
			for(int i=0; i<카드좌표.length; i++) {
				카드좌표[i] = new Point((int)screen.getWidth()/2-카드뒷면.getIconWidth()/2,(int)screen.getHeight()/2-카드뒷면.getIconHeight()/2);
			}
			카드돌린횟수++;
		}

	}
	
	public void bangAnimation(int catser, int goal, boolean evasion) {//뱅 애니메이션
		action="bang";
	}
	
	public void takeAnimation(int caster, int goal) {//강탈 애니메이션
		action="take";
	}
	
	public void beerAnimation(int caster) {
		action="beer";
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		카드뒷면.paintIcon(this, g, (int)screen.getWidth()/2-카드뒷면.getIconWidth()/2,(int)screen.getHeight()/2-카드뒷면.getIconHeight()/2);

		if(action.equals("start")) {
			for(int i=0; i<카드들.length; i++) {
				카드뒷면.paintIcon(this, g, (int)카드좌표[i].getX(), (int)카드좌표[i].getY());
			}
		}
	}

	@Override
	public void run() {
		while(true) {
			if(max<카드돌린횟수) break;
			카드돌리기();
			System.out.println(카드좌표[1].getX());
			this.repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("끝");
	}
}
