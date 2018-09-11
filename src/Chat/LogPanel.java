package Chat;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

//�α��г�
public class LogPanel extends JPanel{
	private JTextPane title;
	private JTextArea output;

	public LogPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EtchedBorder(EtchedBorder.RAISED));

		output = new JTextArea();//�ؽ�Ʈ���̸��� ��ü����
		output.setEditable(false);//�ؽ�Ʈ���̸�� �Է¸��ϰ���
		JScrollPane scroll = new JScrollPane(output);//�ؽ�Ʈ���̸��� ��ũ�� �Ǳ����� JScrollPane Ŭ����

		title = new JTextPane();
		title.setEditable(false);
		title.setText("Log");

		//�������
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		title.setParagraphAttributes(attribs, true);

		this.add(title,"North");
		this.add(scroll);
	}
}