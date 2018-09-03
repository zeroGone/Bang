package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class GameFrame extends JFrame{
	public GameFrame() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx=3;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		
		add(new GamePanel(),c);
		c.weightx=1;
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
			
		}
	}
}
