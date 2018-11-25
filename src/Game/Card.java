package Game;

import java.util.Objects;

import javax.swing.JPanel;

//모든 카드들의 부모가 되는 카드패널
//다형성 구현 목표
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
	
	//카드 이미지 세팅할 것을 요구함
	public abstract void imageSet();
	
	//equals 메소드 재정의
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Card == false) return false;
		Card other = (Card)obj;
		return Objects.equals(this.name, other.name);
	}
	
	//hashCode 메소드 재정의
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
