package Game;

import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MOCCard extends Card{
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
		label.setBounds(2, this.getHeight()/10*9, image.getIconWidth(), image.getIconHeight()-2);
		add(label);
		//����
		label = new JLabel(Integer.toString(number));
		label.setFont(new Font(null, Font.BOLD, this.getHeight()/20));
		label.setBounds(this.getWidth()/10+2, this.getHeight()/10*9, this.getWidth()/10, (int)this.getHeight()/10-2);
		add(label);
		//ī���̹���
		if(����.equals("mount")) 	image = new ImageIcon(getClass().getClassLoader().getResource(String.format("image/%s/%s.png", ����, this.getName())));
		else image = new ImageIcon(getClass().getClassLoader().getResource(String.format("image/%s/%s.jpg", ����, this.getName())));
		image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth()-4, this.getHeight()-4, Image.SCALE_SMOOTH));
		label = new JLabel(image);
		label.setBounds(2, 2, image.getIconWidth(), image.getIconHeight());
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
