package User;

import java.net.Socket;

import Card.Card;
import Character.Character;

public class User{
	private Socket socket;
	
	private int life;
	private Character character;
	private Card[] mounting;
	private Card[] owened;
	public User() {
	}
	
	private String nick;
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
}
