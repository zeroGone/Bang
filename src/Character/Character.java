package Character;

public class Character {
	private String name;
	private int life;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	

	public void ability() {};
	
	class ��Ű��ũ extends Character{
		public ��Ű��ũ() {
			this.setName("��Ű��ũ");
			this.setLife(life);
		}
	}
}
