package Room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JPanel{
	private JTextArea output;
	private JTextField input;
	
	public ChatPanel() {
		this.setLayout(new BorderLayout());//���̾ƿ� BorderLayout���� ����
		this.setBounds(100, 600, 1400, 300);//ũ�� ��ġ ����
		this.setBackground(Color.WHITE);//���� ����
		this.setOpaque(false);
		
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
		
		this.add(scroll);
		this.add(input, "South");//�Է��ʵ带 ���ʿ� �߰�
		
	}
}
