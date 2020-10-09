package com.zetcode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Panel_Login extends JPanel {
	
	private static final long serialVersionUID = -6842563827077598904L;

	JFrame frame;
	
	DatabaseReference refDB;
	final FirebaseDatabase database;
	
	public Panel_Login(Frame_Sokoban f) throws IOException {
		
		
		FileInputStream serviceAccount = new FileInputStream("socobun-sokoban-firebase-adminsdk-85opg-25021480dd.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://socobun-sokoban.firebaseio.com").build();

		FirebaseApp app = FirebaseApp.initializeApp(options);

		database = FirebaseDatabase.getInstance(app);
		refDB = database.getReference("server");
		
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
		
		panelCenter.add(new JLabel("ID : "));
		panelCenter.add(field_ID);
		panelCenter.add(new JLabel("PW : "));
		panelCenter.add(field_PW);
		
		field_ID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		        manager.getFocusOwner().transferFocus();
			}
		});
		
		field_PW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
				
				DatabaseReference usersRef = refDB.child("users"); 
				String inputID = field_ID.getText();

				Map<String, String> userInfoMap = new HashMap<>(); 
				
					userInfoMap.put(field_ID.getText(), field_ID.getText());
					usersRef.setValueAsync(userInfoMap);
					field_ID.setText("¼º°ø");
					field_PW.setText("¼º°ø");
					
		        manager.getFocusOwner().transferFocusBackward();
				 
			}
		});
		
	}
}