package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Game.GameFrame;

public class UserPanel extends JPanel{
	public static final int MAX_LIFE_NUM = 5;
	private String nick;
	private String character;
	private String job;
	private JPanel characterPanel;
	private int life;
	private ImageIcon lifeImage;
	private JLabel[] lifeLabel;
	private JPanel lifePanel;
	private MountingPanel mountingPanel;
	
	//다이얼로그 중복으로 띄우는 것을 방지하기 위한 변수
	private static boolean check = true;
	
	private boolean getCheck() { return check; };
	
	public UserPanel(Dimension screen, String name) {
		setSize(400,300);
		setLayout(null);
		setBackground(Color.WHITE);
		
		this.nick=name;
		TitledBorder nick = new TitledBorder(name);//테두리 titledBorder로 설정해서 닉네임을 타이틀로
		setBorder(nick);//테두리 위에서부터 20 양쪽 10 빼야됨 
		
		//생명을 표시할 패널
		lifePanel = new JPanel();
		lifePanel.setBackground(Color.WHITE);
		lifePanel.setBounds(10, 20, 380, 40);
		lifePanel.setOpaque(false);
		
		lifeLabel=new JLabel[MAX_LIFE_NUM];//생명숫자
		lifeImage = new ImageIcon(getClass().getClassLoader().getResource("image/life.png"));
		lifeImage= new ImageIcon(lifeImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		add(lifePanel,"North");//생명패널 추가
		
		//소비카드패널
		JPanel consumePanel = new JPanel();
		consumePanel.setBounds(0, 70, this.getWidth()/3, 200);
		consumePanel.setBackground(Color.black);
		consumePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check&&character!=null) new CardDialog("소비카드");
			}
		});
		add(consumePanel);

		characterPanel = new JPanel();
		characterPanel.setBounds(this.getWidth()/3*1, 70, this.getWidth()/3, 200);
		characterPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check&&character!=null) new CardDialog(character).characterShow();
			}
		});
		add(characterPanel);
		
		//장착카드패널
		mountingPanel = new MountingPanel();
		mountingPanel.setBounds(this.getWidth()/3*2, 70, this.getWidth()/3, 200);
		mountingPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(check) new CardDialog("장착카드").mountShow();
			}
		});
		add(mountingPanel);
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public void CharacterSet(String value) {
		this.character=value;
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/character/"+value+".jpg"));
		image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth()/3, 200, Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		characterPanel.add(label);
		characterPanel.repaint();
	}
	
	//생명 셋팅
	public void lifeSet(int life) {
		this.life = life;
		for(int i=0; i<life; i++) {//생명숫자 셋팅
			lifeLabel[i]=new JLabel(lifeImage);
			lifePanel.add(lifeLabel[i]);
		}
		lifePanel.repaint();
	}
	
	//생명 깍이거나 추가
	public void lifeAddOrRemove(int value) {
		lifePanel.removeAll();
		for(int i=0; i<this.life+value; i++) {
			lifeLabel[i]=new JLabel(lifeImage);
			lifePanel.add(lifeLabel[i]);
		}
		lifePanel.repaint();
	}
	
	public void 보안관Set() {
		this.job="보안관";
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/job/보안관배지.jpg"));
		image = new ImageIcon(image.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		label.setBounds(0, 15, 60, 60);
		add(label);
		this.revalidate();
	}
	
	private class CardDialog extends JDialog{
		public CardDialog(String name) {
			check = false;
			setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/ani/back.jpg")).getImage());
			setTitle(name);
			setResizable(false);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					check = true;
				}
			});
		}
		
		public void characterShow() {
			this.setLayout(new FlowLayout());
			ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/character/"+this.getTitle()+".jpg"));
			image = new ImageIcon(image.getImage().getScaledInstance(500, 800, Image.SCALE_SMOOTH));
			add(new JLabel(image));
			setSize(500,800);
			setVisible(true);
		}
		
		public void mountShow() {
			this.setLayout(null);
			ArrayList<String> list = mountingPanel.getMount();
			ImageIcon image = null;
			if(list.size()==0) {
				image = new ImageIcon(getClass().getClassLoader().getResource("image/x.png"));
				setSize(image.getIconWidth(), image.getIconHeight());
				add(new JLabel(image));
			}else {
				for(int i=0; i<list.size(); i++) {
					image = new ImageIcon(getClass().getClassLoader().getResource("image/mount/"+list.get(i)+".png"));
					image = new ImageIcon(image.getImage().getScaledInstance(300, 450, Image.SCALE_SMOOTH));
					JLabel label = new JLabel(image);
					label.setBounds(i*300, 0, image.getIconWidth(),image.getIconHeight());
					add(label);
					this.setSize((i+1)*300, image.getIconHeight()+50);
				}
			}
			setLocation((int)GameFrame.screen.getWidth()/2-this.getWidth()/2,(int)GameFrame.screen.getHeight()/2-this.getHeight()/2);
			setVisible(true);
		}
		
		public void consumeShow() {
			this.setLayout(null);
		}
	}
}