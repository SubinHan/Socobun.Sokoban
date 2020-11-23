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

	private static final long serialVersionUID = -6842563827077598904L;

	boolean idAvailable = false;
	boolean pwAvailable = false;

	Panel_Login panel = this;

	JLabel label_ID = null;
	JTextField field_ID = null;
	JLabel available_ID = null;

	JLabel label_PW = null;
	JPasswordField field_PW = null;
	JLabel available_PW = null;

	JButton okBtn = null;
	JButton signUpBtn = null;

	Frame_Sokoban frame;

	public Panel_Login() {
		
		frame = Frame_Sokoban.getInstance();
		System.out.println(frame);
		initUI();
	}

	private void initUI() {

		setLayout(new BorderLayout());

		JLabel title = new JLabel("LOG IN");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension(0, 300));

		JPanel panelCenter = new JPanel();

		JPanel southMargin = new JPanel();
		southMargin.setPreferredSize(new Dimension(0, 300));
		JPanel westMargin = new JPanel();
		westMargin.setPreferredSize(new Dimension(300, 0));
		JPanel eastMargin = new JPanel();
		eastMargin.setPreferredSize(new Dimension(300, 0));

		this.add(title, BorderLayout.NORTH);
		this.add(southMargin, BorderLayout.SOUTH);
		this.add(westMargin, BorderLayout.WEST);
		this.add(eastMargin, BorderLayout.EAST);
		this.add(panelCenter, BorderLayout.CENTER);

		panelCenter.setLayout(new GridLayout(5, 3, 10, 10));

		label_ID = new JLabel("ID");
		field_ID = new JTextField(20);
		available_ID = new JLabel("");
		label_ID.setHorizontalAlignment(SwingConstants.RIGHT);
		available_ID.setForeground(new Color(255, 0, 0));

		label_PW = new JLabel("Password");
		field_PW = new JPasswordField(20);
		available_PW = new JLabel("");
		label_PW.setHorizontalAlignment(SwingConstants.RIGHT);
		field_PW.setEchoChar('◆');
		available_PW.setForeground(new Color(255, 0, 0));

		okBtn = new JButton("LOGIN");
		signUpBtn = new JButton("SIGN UP");
		IDPW_InputListener inputListener = new IDPW_InputListener();
		okBtn.addActionListener(inputListener);
		field_PW.addActionListener(inputListener);
		signUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelChanger.changePanel(Panel_Login.this, new Panel_SignUp());
			}
		});

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

		/*
		 * 엔터키로 다음 필드로 커서 이동
		 */
		EnterListener enterListener = new EnterListener();
		field_ID.addActionListener(enterListener);

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
			UserInfo info = FindUser.findUserByID(field_ID.getText());
			if(info != null) { // 아이디 있으면
				available_ID.setText("");
				available_PW.setText("");
				if(info.pw.contentEquals(UserInfo.passwordToString(field_PW.getPassword()))) { // 비번 맞으면
					available_PW.setText("");
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