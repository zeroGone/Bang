package Chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

//ä���г�
public class ChatPanel extends JPanel{
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
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length()>=20) ke.consume();
			}
		});
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