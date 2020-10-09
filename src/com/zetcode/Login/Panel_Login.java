package com.zetcode.Login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.zetcode.Frame_Sokoban;
import com.zetcode.SokobanUtilities;

public class Panel_Login extends JPanel {
	
	private static final long serialVersionUID = -6842563827077598904L;

	Frame_Sokoban frame;
	
	public Panel_Login(Frame_Sokoban f) {

		frame = f;
		initUI();
	}

	private void initUI() {
		
		setLayout(new BorderLayout());

		JLabel title = new JLabel("ASDASD");
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(400, 0));
		this.add(panelCenter, BorderLayout.CENTER);
		
		panelCenter.setLayout(new FlowLayout());
		
		JTextField field_ID = new JTextField(20);
		JTextField field_PW = new JTextField(20);
		JButton signUpBtn = new JButton("Sign Up");
		
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(SokobanUtilities.PANEL_SIGNUP);
			}
		});
		
		panelCenter.add(new JLabel("        ID : "));
		panelCenter.add(field_ID);
		panelCenter.add(new JLabel("  Password : "));
		panelCenter.add(field_PW);
		panelCenter.add(signUpBtn);
		
		
		field_ID.addActionListener(new ActionListener() { // ID ÀÔ·Â¶õ
			public void actionPerformed(ActionEvent e) {
				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
				
		        manager.getFocusOwner().transferFocus();
			}
		});
		
		field_PW.addActionListener(new ActionListener() { // PW ÀÔ·Â¶õ
			public void actionPerformed(ActionEvent e) {
				
				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
				
				DatabaseReference usersRef = FirebaseClass.rootReference.child("users");
				UserInfo info = new UserInfo(field_ID.getText(), field_PW.getText(), "ss");
				
				usersRef.child(field_ID.getText()).setValueAsync(info);
				
		        manager.getFocusOwner().transferFocusBackward();
				 
			}
		});
		
	}
	
}