package Chat;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

//로그패널
public class LogPanel extends JPanel{
	private JTextPane title;
	private JTextArea output;

	public LogPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EtchedBorder(EtchedBorder.RAISED));

		output = new JTextArea();//텍스트에이리어 객체생성
		output.setEditable(false);//텍스트에이리어에 입력못하게함
		JScrollPane scroll = new JScrollPane(output);//텍스트에이리어 스크롤 되기위한 JScrollPane 클래스

		title = new JTextPane();
		title.setEditable(false);
		title.setText("Log");

		//가운데정렬
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		title.setParagraphAttributes(attribs, true);

		this.add(title,"North");
		this.add(scroll);
	}
}