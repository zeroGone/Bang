package Game;

import java.util.Objects;

import javax.swing.JPanel;

//��� ī����� �θ� �Ǵ� ī���г�
//������ ���� ��ǥ
public abstract class Card extends JPanel {
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public Card(int width, int height, String name) {
		this.setSize(width, height);
		this.setLayout(null);
		this.name = name;
	}
	
	//ī�� �̹��� ������ ���� �䱸��
	public abstract void imageSet();
	
	//equals �޼ҵ� ������
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Card == false) return false;
		Card other = (Card)obj;
		return Objects.equals(this.name, other.name);
	}
	
	//hashCode �޼ҵ� ������
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
