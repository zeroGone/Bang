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

//채팅패널
public class ChatPanel extends JPanel{
	private JTextPane title;
	private JTextArea output;
	private JTextField input;

	public ChatPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EtchedBorder(EtchedBorder.RAISED));

		output = new JTextArea();//텍스트에이리어 객체생성
		output.setEditable(false);//텍스트에이리어에 입력못하게함
		input = new JTextField();//텍스트필드 객체생성
		JScrollPane scroll = new JScrollPane(output);//텍스트에이리어 스크롤 되기위한 JScrollPane 클래스
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length()>=20) ke.consume();
			}
		});
		input.addActionListener(new ActionListener() {//엔터키 이벤트리스너
			@Override
			public void actionPerformed(ActionEvent arg0) {
				output.append(input.getText()+"\n");//내용을 출력해주고
				input.setText("");//입력을 초기화해줌
			}
		});

		title = new JTextPane();
		title.setEditable(false);
		title.setText("채팅창");

		//가운데정렬
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		title.setParagraphAttributes(attribs, true);

		this.add(title,"North");
		this.add(scroll);
		this.add(input, "South");//입력필드를 남쪽에 추가
	}
}