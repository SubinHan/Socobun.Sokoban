package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import login.FindUser;
import login.FirebaseClass;
import model.UserInfo;

public class Panel_SignUp extends JPanel {

	boolean nicknameAvailable = false;
	boolean idAvailable = false;
	boolean pwAvailable = false;
	boolean pwconfirmAvailable = false;
	
	//Center
	JPanel panelCenter = new JPanel();
		//Nickname
		JLabel label_Nickname = new JLabel("Nickname");
		JTextField field_Nickname = new JTextField(20);
		JLabel available_Nickname = new JLabel("�� �� �̻� �Է����ּ���.");
		//ID
		JLabel label_ID = new JLabel("ID");
		JTextField field_ID = new JTextField(20);
		JLabel available_ID = new JLabel("�� �� �̻� �Է����ּ���.");
		//PW
		JLabel label_PW = new JLabel("Password");
		JPasswordField field_PW = new JPasswordField(20);
		JLabel available_PW = new JLabel("�� �� �̻� �Է����ּ���.");
		//PW Confirm
		JLabel label_PWconfirm = new JLabel("Password Confirm");
		JPasswordField field_PWconfirm = new JPasswordField(20);
		JLabel available_PWconfirm = new JLabel("�� �� �̻� �Է����ּ���.");
		//Buttons
		JButton okBtn = new JButton("OK");
		JButton cancelBtn = new JButton("Cancel");


	public Panel_SignUp() {

		initUI();
	}
	
	//Layout
	private void initLayout() {

		setLayout(new BorderLayout());

		JPanel southMargin = new JPanel();
		southMargin.setPreferredSize(new Dimension(0, 300));
		JPanel westMargin = new JPanel();
		westMargin.setPreferredSize(new Dimension(300, 0));
		JPanel eastMargin = new JPanel();
		eastMargin.setPreferredSize(new Dimension(300, 0));

		this.add(southMargin, BorderLayout.SOUTH);
		this.add(westMargin, BorderLayout.WEST);
		this.add(eastMargin, BorderLayout.EAST);
		
	}
	
	//Title(North)
	private void initTitle() {

		JLabel title = new JLabel("SIGN UP");
		title.setFont(new Font("���� ���", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension(0, 300));

		this.add(title, BorderLayout.NORTH);
		
	}
	
	
	//Center
	private void initCenter() {
		
		panelCenter.setLayout(new GridLayout(6, 3, 10, 10));

		//Nickname
		label_Nickname.setHorizontalAlignment(SwingConstants.RIGHT);
		available_Nickname.setForeground(new Color(255, 0, 0));

		//ID
		label_ID.setHorizontalAlignment(SwingConstants.RIGHT);
		available_ID.setForeground(new Color(255, 0, 0));

		//PW
		label_PW.setHorizontalAlignment(SwingConstants.RIGHT);
		field_PW.setEchoChar('��');
		available_PW.setForeground(new Color(255, 0, 0));

		//PW Confirm
		label_PWconfirm.setHorizontalAlignment(SwingConstants.RIGHT);
		field_PWconfirm.setEchoChar('��');
		available_PWconfirm.setForeground(new Color(255, 0, 0));


		//Buttons

		panelCenter.add(label_Nickname);
		panelCenter.add(field_Nickname);
		panelCenter.add(available_Nickname);

		panelCenter.add(label_ID);
		panelCenter.add(field_ID);
		panelCenter.add(available_ID);

		panelCenter.add(label_PW);
		panelCenter.add(field_PW);
		panelCenter.add(available_PW);

		panelCenter.add(label_PWconfirm);
		panelCenter.add(field_PWconfirm);
		panelCenter.add(available_PWconfirm);

		panelCenter.add(new JLabel(""));
		panelCenter.add(okBtn);
		panelCenter.add(new JLabel(""));
		
		panelCenter.add(new JLabel(""));
		panelCenter.add(cancelBtn);
		panelCenter.add(new JLabel(""));

		this.add(panelCenter, BorderLayout.CENTER);
	}
	
	private void addListeners() {

		InputListener inputListener = new InputListener();
		field_PWconfirm.addActionListener(inputListener);
		okBtn.addActionListener(inputListener);
		cancelBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PanelChanger.changePanel(Panel_SignUp.this, new Panel_Login());
			}
		});
		
		EnterListener enterListener = new EnterListener();
		field_PWconfirm.addActionListener(enterListener);
		field_ID.addActionListener(enterListener);
		field_Nickname.addActionListener(enterListener);
		
		ValidityChecker validityChecker = new ValidityChecker();
		field_Nickname.getDocument().addDocumentListener(validityChecker);
		field_ID.getDocument().addDocumentListener(validityChecker);
		field_PW.getDocument().addDocumentListener(validityChecker);
		field_PWconfirm.getDocument().addDocumentListener(validityChecker);
		
	}
	
	/////
	private void initUI() {

		initLayout();
		initTitle();
		initCenter();
		
		addListeners();

	}
	
	private class InputListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (nicknameAvailable && idAvailable && pwAvailable && pwconfirmAvailable) {
				UserInfo info = new UserInfo(field_Nickname.getText(), field_ID.getText(), field_PW.getPassword());
				FirebaseClass.rootReference.child("users").child(info.id).setValueAsync(info);
				PanelChanger.changePanel(Panel_SignUp.this, new Panel_Login());
			}
		}
	}

	private class EnterListener implements ActionListener { // ���ʹ����� Ŀ�� �������� �̵�
		public void actionPerformed(ActionEvent e) {
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.getFocusOwner().transferFocus();
		}
	}


	private class ValidityChecker implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) { whenChanged(arg0); }

		@Override
		public void removeUpdate(DocumentEvent arg0) { whenChanged(arg0); }

		@Override
		public void insertUpdate(DocumentEvent arg0) { whenChanged(arg0); }

		private void whenChanged(DocumentEvent arg) {
			
			checkNickname();
			checkID();
			checkPW();
			checkPWConfirm();
		}
		
		private void checkNickname() {
			
			available_Nickname.setForeground(new Color(255, 0, 0));
			
			if (field_Nickname.getText().length() < 2) {
				
				available_Nickname.setText("�� �� �̻� �Է����ּ���.");
				
			} else {
				
				UserInfo info = FindUser.findUserByNickname(field_Nickname.getText());
				if (info != null) { // �г��� ������
					available_Nickname.setForeground(new Color(255, 0, 0));
					available_Nickname.setText("�̹� �ִ� �г����Դϴ�.");
					nicknameAvailable = false;
				} else {
					available_Nickname.setForeground(new Color(0, 255, 0));
					available_Nickname.setText("��");
					nicknameAvailable = true;
				}
			}
			
		}

		private void checkID() {

			available_ID.setForeground(new Color(255, 0, 0));
			
			if (field_ID.getText().length() < 2) {
				
				available_ID.setText("�� �� �̻� �Է����ּ���.");
				
			} else {
				
				UserInfo info = FindUser.findUserByID(field_ID.getText());
				if (info != null) { // ���̵� ������
					available_ID.setText("�̹� �ִ� ���̵��Դϴ�.");
					idAvailable = false;
				} else {
					available_ID.setForeground(new Color(0, 255, 0));
					available_ID.setText("��");
					idAvailable = true;
				}
			}
		}

		private void checkPW() {
			
			available_PW.setForeground(new Color(255, 0, 0));
			
			if (field_PW.getPassword().length >= 4) {
				available_PW.setForeground(new Color(0, 255, 0));
				available_PW.setText("��");
				pwAvailable = true;
			} else {
				available_PW.setText("�� �� �̻� �Է����ּ���.");
				pwAvailable = false;
			}
			
		}

		private void checkPWConfirm() {
			
			available_PWconfirm.setForeground(new Color(255, 0, 0));
			
			if (field_PWconfirm.getPassword().length < 4) {
				available_PWconfirm.setText("�� �� �̻� �Է����ּ���.");
				pwconfirmAvailable = false;
			} else if (Arrays.equals(field_PWconfirm.getPassword(), field_PW.getPassword())) {
				available_PWconfirm.setForeground(new Color(0, 255, 0));
				available_PWconfirm.setText("��");
				pwconfirmAvailable = true;
			} else {
				available_PWconfirm.setText("���� �Է��� ��й�ȣ�� �ٸ��ϴ�.");
				pwconfirmAvailable = false;
			}
		}
		
	}
}