package Game;

import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MOCCard extends Card {
	private int number;//숫자
	private String sign;//기호
	private String 종류;//종류
	
	public MOCCard(int width, int height, String 종류, String name, String sign, int number) {
		super(width, height, name);
		this.종류 = 종류;
		this.number = number;
		this.sign = sign;
	}
	
	public Map getCard() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", this.getName());
		map.put("종류", this.종류);
		map.put("number", this.number);
		map.put("sign", this.sign);
		return map;
	}

	@Override
	public void imageSet() {
		//기호이미지
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/sign/"+this.sign+".png"));
		image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth()/10, this.getHeight()/10, Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		label.setBounds(0, this.getHeight()/10*9, image.getIconWidth(), image.getIconHeight());
		add(label);
		//숫자
		label = new JLabel(Integer.toString(number));
		label.setFont(new Font(null, Font.BOLD, this.getHeight()/17));
		label.setBounds(this.getWidth()/10, this.getHeight()/10*9, this.getWidth()/10, (int)this.getHeight()/10);
		add(label);
		//카드이미지
		image = new ImageIcon(getClass().getClassLoader().getResource(String.format("image/%s/%s.png", 종류, this.getName())));
		image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
		label = new JLabel(image);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		add(label);
	}
	
	//equals 메소드 재정의
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MOCCard == false) return false;
		MOCCard other = (MOCCard)obj;
		return Objects.equals(this.getName(), other.getName())
				&&Objects.equals(this.종류, other.종류)
				&&Objects.equals(this.sign, other.sign)
				&&this.number==other.number;
	}

	//hashCode 메소드 재정의
	@Override
	public int hashCode() {
		return Objects.hash(this.getName(), this.종류, this.sign, this.number);
	}
}
