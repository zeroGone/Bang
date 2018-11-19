package User;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;

public class MountingPanel extends JLayeredPane{
	private ArrayList<String> mount;
	
	public ArrayList<String> getMount(){ return mount; }
	
	public MountingPanel() {
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		mount = new ArrayList<String>();
	}
	
	public void addMounting(String value) {
		mount.add(value);
		this.paintMounting();
	}
	
	public void removeMounting(String value) {
		mount.remove(mount.indexOf(value));
		this.paintMounting();
	}
	
	private void paintMounting() {
		this.removeAll();
		for(int i=0; i<mount.size(); i++) {
			ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("image/mount/"+mount.get(i)+".png"));
			image = new ImageIcon(image.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
			JLabel label = new JLabel(image);
			label.setBounds(20*i, 0, this.getWidth(), this.getHeight());
			add(label, new Integer(i));
		}
		this.repaint();
	}
}

