package com.zetcode.Login;

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

import com.zetcode.Frame_Sokoban;
import com.zetcode.SokobanUtilities;
import com.zetcode.model.UserInfo;

public class Panel_SignUp extends JPanel {

	private static final long serialVersionUID = -6842563827077598904L;

	boolean nicknameAvailable = false;
	boolean idAvailable = false;
	boolean pwAvailable = false;
	boolean pwconfirmAvailable = false;

	Panel_SignUp panel = this;

	JLabel label_Nickname = null;
	JTextField field_Nickname = null;
	JLabel available_Nickname = null;

	JLabel label_ID = null;
	JTextField field_ID = null;
	JLabel available_ID = null;

	JLabel label_PW = null;
	JPasswordField field_PW = null;
	JLabel available_PW = null;

	JLabel label_PWconfirm = null;
	JPasswordField field_PWconfirm = null;
	JLabel available_PWconfirm = null;

	Frame_Sokoban frame;

	public Panel_SignUp(Frame_Sokoban f) {

		frame = f;
		initUI();
	}

	private void initUI() {

		setLayout(new BorderLayout());

		JLabel title = new JLabel("SIGN UP");
		title.setFont(new Font("���� ���", Font.BOLD, 40));
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

		panelCenter.setLayout(new GridLayout(6, 3, 10, 10));

		label_Nickname = new JLabel("Nickname");
		field_Nickname = new JTextField(20);
		available_Nickname = new JLabel("");
		label_Nickname.setHorizontalAlignment(SwingConstants.RIGHT);
		available_Nickname.setForeground(new Color(255, 0, 0));

		label_ID = new JLabel("ID");
		field_ID = new JTextField(20);
		available_ID = new JLabel("");
		label_ID.setHorizontalAlignment(SwingConstants.RIGHT);
		available_ID.setForeground(new Color(255, 0, 0));

		label_PW = new JLabel("Password");
		field_PW = new JPasswordField(20);
		available_PW = new JLabel("");
		label_PW.setHorizontalAlignment(SwingConstants.RIGHT);
		field_PW.setEchoChar('��');
		available_PW.setForeground(new Color(255, 0, 0));

		label_PWconfirm = new JLabel("Password Confirm");
		field_PWconfirm = new JPasswordField(20);
		available_PWconfirm = new JLabel("");
		label_PWconfirm.setHorizontalAlignment(SwingConstants.RIGHT);
		field_PWconfirm.setEchoChar('��');
		available_PWconfirm.setForeground(new Color(255, 0, 0));

		InputListener inputListener = new InputListener();
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(inputListener);
		field_PWconfirm.addActionListener(inputListener);

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

		/**
		 * ȸ������ �� ��ȿ�� ����
		 * 1. ���̵� : DB�� ����� ��. ������ �ƴ� �� �� �̻�
		 * 2. �н����� : �� �� �̻�
		 * 4. �г��� : DB��
		 * ����� ��.
		 * 
		 * �� ��ȣ�� ���� ��ȯ. ���� 1:���̵� ����, 2:�н����� ����, 4:�г��ӹ��� 3:���̵���н����幮��, 6:�н�������г��ӹ��� 7:�ٹ���
		 * 0:���� ����.
		 */

		field_Nickname.getDocument().addDocumentListener(new DocumentListener() { // �г��� �Է��� ��

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			private void whenChanged() {

				if (field_Nickname.getText().length() < 2) {
					available_Nickname.setForeground(new Color(255, 0, 0));
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

		});

		field_ID.getDocument().addDocumentListener(new DocumentListener() { // ���̵� �Է��� ��

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			private void whenChanged() {

				if (field_ID.getText().length() < 2) {
					available_ID.setForeground(new Color(255, 0, 0));
					available_ID.setText("�� �� �̻� �Է����ּ���.");
				} else {
					UserInfo info = FindUser.findUserByID(field_ID.getText());
					if (info != null) { // ���̵� ������
						available_ID.setForeground(new Color(255, 0, 0));
						available_ID.setText("�̹� �ִ� ���̵��Դϴ�.");
						idAvailable = false;
					} else {
						available_ID.setForeground(new Color(0, 255, 0));
						available_ID.setText("��");
						idAvailable = true;
					}
				}
			}

		});

		field_PW.getDocument().addDocumentListener(new DocumentListener() { // ��� �Է��� ��

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			private void whenChanged() {
				int pw_minLength = 4;
				if (field_PW.getPassword().length >= pw_minLength) {
					available_PW.setForeground(new Color(0, 255, 0));
					available_PW.setText("��");
					pwAvailable = true;
				} else {
					available_PW.setForeground(new Color(255, 0, 0));
					available_PW.setText("�� �� �̻� �Է����ּ���.");
					pwAvailable = false;
				}

			}
		});

		field_PWconfirm.getDocument().addDocumentListener(new DocumentListener() { // ���Ȯ�� �Է��� ��

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				whenChanged();
			}

			private void whenChanged() {
				if (field_PWconfirm.getPassword().length < 4) {
					available_PWconfirm.setForeground(new Color(255, 0, 0));
					available_PWconfirm.setText("�� �� �̻� �Է����ּ���.");
					pwconfirmAvailable = false;
				} else if (Arrays.equals(field_PWconfirm.getPassword(), field_PW.getPassword())) {
					available_PWconfirm.setForeground(new Color(0, 255, 0));
					available_PWconfirm.setText("��");
					pwconfirmAvailable = true;
				} else {
					available_PWconfirm.setForeground(new Color(255, 0, 0));
					available_PWconfirm.setText("���� �Է��� ��й�ȣ�� �ٸ��ϴ�.");
					pwconfirmAvailable = false;
				}
			}
		});

		/*
		 * ����Ű�� ���� �ʵ�� Ŀ�� �̵�
		 */
		EnterListener enterListener = new EnterListener();

		field_PWconfirm.addActionListener(enterListener);
		field_Nickname.addActionListener(enterListener);
		field_ID.addActionListener(enterListener);

	}

	private class InputListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (nicknameAvailable && idAvailable && pwAvailable && pwconfirmAvailable) {
				FirebaseClass
						.putUser(new UserInfo(field_Nickname.getText(), field_ID.getText(), field_PW.getPassword()));
				field_Nickname.setText("");
				field_ID.setText("");
				field_PW.setText("");
				field_PWconfirm.setText("");
				nicknameAvailable = false;
				idAvailable = false;
				pwAvailable = false;
				pwconfirmAvailable = false;
				available_Nickname.setText("");
				available_ID.setText("");
				available_PW.setText("");
				available_PWconfirm.setText("");
				frame.changePanel(SokobanUtilities.PANEL_LOGIN);
			}
		}
	}

	private class EnterListener implements ActionListener { // ���ʹ����� Ŀ�� �������� �̵�
		public void actionPerformed(ActionEvent e) {
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.getFocusOwner().transferFocus();
		}
	}

}