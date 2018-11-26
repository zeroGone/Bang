package Game;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CharacterCard extends Card {
	
	public CharacterCard(int width, int height, String name) {
		super(width, height, name);
	}

	@Override
	public void imageSet() {
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource(String.format("image/character/%s.jpg", this.getName())));
		image = new ImageIcon(image.getImage().getScaledInstance((int)super.getSize().getWidth(), (int)super.getSize().getHeight(), Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		add(label);
	}
}
