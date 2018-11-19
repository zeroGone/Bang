package Game;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//ī��
public class Card extends JPanel{
	private String ����;
	private String name;
	private String sign;
	private int num;
	
	public Card(String ����, String name, String sign, int num) {
		this.����=����;
		this.name=name;
		this.sign=sign;
		this.num=num;
	}
	
	public void imageSet(int width, int height) {
		this.setSize(width,height);
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource(String.format("image/%s/%s.png", ����, name)));
		image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));

	}
	
	@Override
	public String toString() {
		return String.format("%s %d%s", name, num, sign);
	}
}
