package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import login.FindUser;
import model.UserInfo;

public class Panel_Login extends JPanel {

	boolean idAvailable = false;
	boolean pwAvailable = false;
	
	Frame_Sokoban frame;
	
	//Center
	JPanel panelCenter; // Center 내용 담을 큰 틀
		//ID
		JLabel label_ID = new JLabel("ID");
		JTextField field_ID = new JTextField(20);
		JLabel available_ID = new JLabel("");
		//PW
		JLabel label_PW = new JLabel("Password");
		JPasswordField field_PW = new JPasswordField(20);
		JLabel available_PW = new JLabel("");
		//Button
		JButton okBtn = new JButton("LOGIN");
		JButton signUpBtn = new JButton("SIGN UP");

	public Panel_Login() {
		
		frame = Frame_Sokoban.getInstance();
		initUI();
		
	}

	//Layout//
	private void initLayout() {
		
		setLayout(new BorderLayout());

		//Margin
		JPanel southMargin = new JPanel();
		southMargin.setPreferredSize(new Dimension(0, 300));
		JPanel westMargin = new JPanel();
		westMargin.setPreferredSize(new Dimension(300, 0));
		JPanel eastMargin = new JPanel();
		eastMargin.setPreferredSize(new Dimension(300, 0));

		this.add(southMargin, BorderLayout.SOUTH);
		this.add(westMargin, BorderLayout.WEST);
		this.add(eastMargin, BorderLayout.EAST);
		
		//Title
		JLabel title = new JLabel("LOG IN");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension(0, 300));

		this.add(title, BorderLayout.NORTH);
		
	}
	
	//Center
	private void initCenter() {

		panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(5, 3, 10, 10));

		IDPW_InputListener inputListener = new IDPW_InputListener();
		EnterListener enterListener = new EnterListener();
		
		//ID
		label_ID.setHorizontalAlignment(SwingConstants.RIGHT);
		field_ID.addActionListener(enterListener);
		available_ID.setForeground(new Color(255, 0, 0));

		//PW
		label_PW.setHorizontalAlignment(SwingConstants.RIGHT);
		field_PW.setEchoChar('◆');
		field_PW.addActionListener(inputListener);
		available_PW.setForeground(new Color(255, 0, 0));
		
		//Button_Login
		okBtn.addActionListener(inputListener);
		
		//Button_SignUP
		signUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelChanger.changePanel(Panel_Login.this, new Panel_SignUp());
			}
		});
		
		addPanelCenter();
	}
	
	private void addPanelCenter() {

		panelCenter.add(label_ID);
		panelCenter.add(field_ID);
		panelCenter.add(available_ID);

		panelCenter.add(label_PW);
		panelCenter.add(field_PW);
		panelCenter.add(available_PW);

		panelCenter.add(new JLabel(""));
		panelCenter.add(okBtn);
		panelCenter.add(new JLabel(""));

		panelCenter.add(new JLabel(""));
		panelCenter.add(signUpBtn);
		panelCenter.add(new JLabel(""));

		this.add(panelCenter, BorderLayout.CENTER);
		
	}
	
	/////
	private void initUI() {

		initLayout();
		initCenter();
		
	}

	private class EnterListener implements ActionListener { // 엔터누르면 커서 다음으로 이동
		public void actionPerformed(ActionEvent e) {
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.getFocusOwner().transferFocus();
		}
	}

	private class IDPW_InputListener implements ActionListener { // PW Field와 OK Button에다가 넣어주자.

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(field_PW.getPassword().toString());
			if(field_PW.getPassword() == null) return;
			UserInfo info = FindUser.findUserByID(field_ID.getText());
			if(info != null) { // 아이디 있으면
				if(info.pw.contentEquals(UserInfo.passwordToString(field_PW.getPassword()))) { // 비번 맞으면
					frame.setUserinfo(info);
					PanelChanger.changePanel(Panel_Login.this, new Panel_MainScene());
				}
				else {
					field_PW.requestFocus();
					field_PW.selectAll();
					available_PW.setText("비밀번호가 틀립니다.");
				}
			} else {
				field_ID.requestFocus();
				field_ID.selectAll();
				available_ID.setText("없는 아이디입니다.");
			}
		}
	}
	
}