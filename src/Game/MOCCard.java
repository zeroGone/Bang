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
	private int number;//����
	private String sign;//��ȣ
	private String ����;//����
	
	public MOCCard(int width, int height, String ����, String name, String sign, int number) {
		super(width, height, name);
		this.���� = ����;
		this.number = number;
		this.sign = sign;
	}
	
	public Map getCard() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", this.getName());
		map.put("����", this.����);
		map.put("number", this.number);
		map.put("sign", this.sign);
		return map;
	}

	@Override
	public void imageSet() {
		//��ȣ�̹���
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/sign/"+this.sign+".png"));
		image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth()/10, this.getHeight()/10, Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		label.setBounds(0, this.getHeight()/10*9, image.getIconWidth(), image.getIconHeight());
		add(label);
		//����
		label = new JLabel(Integer.toString(number));
		label.setFont(new Font(null, Font.BOLD, this.getHeight()/17));
		label.setBounds(this.getWidth()/10, this.getHeight()/10*9, this.getWidth()/10, (int)this.getHeight()/10);
		add(label);
		//ī���̹���
		image = new ImageIcon(getClass().getClassLoader().getResource(String.format("image/%s/%s.png", ����, this.getName())));
		image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
		label = new JLabel(image);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		add(label);
	}
	
	//equals �޼ҵ� ������
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MOCCard == false) return false;
		MOCCard other = (MOCCard)obj;
		return Objects.equals(this.getName(), other.getName())
				&&Objects.equals(this.����, other.����)
				&&Objects.equals(this.sign, other.sign)
				&&this.number==other.number;
	}

	//hashCode �޼ҵ� ������
	@Override
	public int hashCode() {
		return Objects.hash(this.getName(), this.����, this.sign, this.number);
	}
}
