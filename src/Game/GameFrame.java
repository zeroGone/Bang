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
		
		
		/**/setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//메인프레임을 닫아서 쓰레드가 실행됨을 방지하기위해 설정해줘야하는 메소드
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);//화면 사이즈 변경 불가
		setVisible(true);//프레임이 보일수있게
	}
	
	private class ChatPanel extends JPanel{
		private JTextPane title;
		private JTextArea output;
		private JTextField input;
		
		public ChatPanel() {
			this.setLayout(new BorderLayout());
			
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
	
	private class GamePanel extends JPanel{
		public GamePanel() {
			
		}
	}
}
