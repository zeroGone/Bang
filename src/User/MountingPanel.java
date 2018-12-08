package User;

import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;

import Game.MOCCard;

public class MountingPanel extends JLayeredPane{
	private ArrayList<MOCCard> mount;
	public ArrayList<MOCCard> getMount(){ return mount; }

	public MountingPanel() {
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		mount = new ArrayList<MOCCard>();
	}
	
	public void addMounting(MOCCard value) {
		mount.add(value);
		this.paintMounting();
	}
	
	public void removeMounting(String value) {
		System.out.println("¿©±â:"+value);
		for(int i=0; i<mount.size(); i++) 
			if(mount.get(i).getName().equals(value)) {
				mount.remove(i);
				break;
			}
		this.paintMounting();
	}
	
	private void paintMounting() {
		this.removeAll();
		for(int i=0; i<mount.size(); i++) {
			MOCCard card = mount.get(i);
			card.setLocation(20*i, 0);
			add(card, new Integer(i));
		}
		this.repaint();
	}
}

