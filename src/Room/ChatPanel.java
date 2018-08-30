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
		this.setLayout(new BorderLayout());//레이아웃 BorderLayout으로 지정
		this.setBounds(100, 600, 1400, 300);//크기 위치 지정
		this.setBackground(Color.WHITE);//배경색 지정
		this.setOpaque(false);
		
		output = new JTextArea();//텍스트에이리어 객체생성
		output.setEditable(false);//텍스트에이리어에 입력못하게함
		input = new JTextField();//텍스트필드 객체생성
		JScrollPane scroll = new JScrollPane(output);//텍스트에이리어 스크롤 되기위한 JScrollPane 클래스
		
		input.addActionListener(new ActionListener() {//엔터키 이벤트리스너
			@Override
			public void actionPerformed(ActionEvent arg0) {
				output.append(input.getText()+"\n");//내용을 출력해주고
				input.setText("");//입력을 초기화해줌
			}
		});
		
		this.add(scroll);
		this.add(input, "South");//입력필드를 남쪽에 추가
		
	}
}
