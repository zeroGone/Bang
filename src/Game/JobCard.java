package Game;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JobCard extends Card {

	public JobCard(int width, int height, String name) {
		super(width, height, name);
	}

	@Override
	public void imageSet() {
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource(String.format("image/job/%s.png", this.getName())));
		image = new ImageIcon(image.getImage().getScaledInstance((int)this.getSize().getWidth(), (int)this.getSize().getHeight(), Image.SCALE_SMOOTH));
		JLabel label = new JLabel(image);
		label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		add(label);
		this.setVisible(true);
	}
	
}
