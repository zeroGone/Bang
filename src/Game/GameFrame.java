package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class GameFrame extends JFrame{
	public GameFrame() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		
		add(new GamePanel(),c);
		add(new ChatPanel(),c);
		
		
		/**/setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//������������ �ݾƼ� �����尡 ������� �����ϱ����� ����������ϴ� �޼ҵ�
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);//ȭ�� ������ ���� �Ұ�
		setVisible(true);//�������� ���ϼ��ְ�
	}
	
	private class ChatPanel extends JPanel{
		private JTextPane title;
		private JTextArea output;
		private JTextField input;
		
		public ChatPanel() {
			this.setLayout(new BorderLayout());
			this.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			
			output = new JTextArea();//�ؽ�Ʈ���̸��� ��ü����
			output.setEditable(false);//�ؽ�Ʈ���̸�� �Է¸��ϰ���
			input = new JTextField();//�ؽ�Ʈ�ʵ� ��ü����
			JScrollPane scroll = new JScrollPane(output);//�ؽ�Ʈ���̸��� ��ũ�� �Ǳ����� JScrollPane Ŭ����
			
			input.addActionListener(new ActionListener() {//����Ű �̺�Ʈ������
				@Override
				public void actionPerformed(ActionEvent arg0) {
					output.append(input.getText()+"\n");//������ ������ְ�
					input.setText("");//�Է��� �ʱ�ȭ����
				}
			});
			
			title = new JTextPane();
			title.setEditable(false);
			title.setText("ä��â");
			
			//�������
			SimpleAttributeSet attribs = new SimpleAttributeSet();
			StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
			title.setParagraphAttributes(attribs, true);
			
			this.add(title,"North");
			this.add(scroll);
			this.add(input, "South");//�Է��ʵ带 ���ʿ� �߰�
		}
	}
	
	private class GamePanel extends JPanel{
		public GamePanel() {
			GridLayout layout = new GridLayout(3,3);//33 GridLayout
			layout.setHgap(10);//����  ���� ����
			layout.setVgap(10);//����  ���� ����
			setLayout(layout);
			
			add(new UserPanel("�迵��","���Ȱ�","������Ű��"));
			add(new JPanel());
			add(new UserPanel("������","�����","���׸���"));
			add(new UserPanel("ȫ�ؼ�","������","����"));
			add(new JPanel());
			add(new UserPanel("������","������","��Ʈĳ�õ�"));
			add(new UserPanel("���ϱ�","�ΰ�","����Ѷ�"));
			add(new UserPanel("�����","�ΰ�","Į���Ƽ�ڳ�"));
			add(new UserPanel("������","������","���ζ�̳���"));
		}
	}
	
	private class UserPanel extends JPanel{
		private String job;
		private String character;
		
		public UserPanel(String userName, String jobName, String characterName) {
			this.job=jobName;
			this.character=characterName;
			
			setBorder(new BevelBorder(BevelBorder.RAISED));
			setLayout(new BorderLayout());
			JTextPane name = new JTextPane();
			name.setEditable(false);
			//�������
			SimpleAttributeSet attribs = new SimpleAttributeSet();
			StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
			name.setParagraphAttributes(attribs, true);
			name.setText(userName);
			
			setBackground(Color.gray);
			
			JPanel buttonPanel = new JPanel();
			JButton button = new JButton("ī�� ��ư");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new CardDialog(new JFrame(),false);
				}
			});
			buttonPanel.add(button);
			
			add(new CardPanel(),"Center");
			add(buttonPanel,"South");
			add(name,"North");
		}
		
		private class CardPanel extends JPanel{
			
			public CardPanel() {
				setBackground(Color.white);
				setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.weightx=1;
				c.weighty=1;
				c.fill=GridBagConstraints.VERTICAL;
				
				ImageIcon jobImage;
				if(!job.equals("���Ȱ�")) jobImage =  new ImageIcon("./image/����ī��޸�.jpg");
				else jobImage =  new ImageIcon("./image/���Ȱ�.png");
				jobImage= new ImageIcon(jobImage.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH));
				ImageIcon characterImage = new ImageIcon("./image/"+character+".jpg");
				characterImage = new ImageIcon(characterImage.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH));
				
				add(new JLabel(jobImage),c);
				add(new JLabel(characterImage),c);
			}
		}
	}
	
	private class CardDialog extends JDialog{
		public CardDialog(JFrame frame,Boolean my) {
			super(frame,"ī���");
			setLayout(new BorderLayout());
			if(my);
			else {
				JLabel label = new JLabel("4��",JLabel.CENTER);
				label.setFont(new Font(null,Font.BOLD,30));
				JPanel panel = new JPanel();
				panel.add(label);
				add(panel,"Center");
			}
			setSize(200,100);
			setLocation(680,450);
			setVisible(true);
		}
	}
}
