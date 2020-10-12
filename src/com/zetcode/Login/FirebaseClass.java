package com.zetcode.Login;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zetcode.model.UserInfo;

public class FirebaseClass {

	public static FirebaseDatabase database = null;
	public static DatabaseReference rootReference = null;

	private static FileInputStream serviceAccount = null;
	private static FirebaseOptions options = null;
	private static FirebaseApp app = null;
	
	public static void init() {

		// ���̾�̽� �����ͺ��̽� adminSDK
		try {
			serviceAccount = new FileInputStream("socobun-sokoban-firebase-adminsdk-85opg-25021480dd.json");
			options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://socobun-sokoban.firebaseio.com").build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		app = FirebaseApp.initializeApp(options);

		database = FirebaseDatabase.getInstance(app);
		rootReference = database.getReference();
	}

	static boolean exists = false;
	
	public static void checkSignupForm(String searchingCat, String fieldText, Panel_SignUp pane) {
		
		rootReference.child("users").orderByChild(searchingCat).equalTo(fieldText).addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(searchingCat.contentEquals("nickname"))
					pane.setNicknameAvailabilityLabel(snapshot.exists());
				else if(searchingCat.contentEquals("id"))
					pane.setIDAvailabilityLabel(snapshot.exists());
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println("cancelled");
			}

		});
	}

	// �α����Ҷ� ���̵� �ִ°��� Ȯ��
	public static void checkLoginForm(String searchingCat, String fieldText, Panel_Login pane) {
		
		rootReference.child("users").orderByChild(searchingCat).equalTo(fieldText).addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(searchingCat.contentEquals("id")) {
					pane.setIDAvailabilityLabel(snapshot.exists());
				}
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println("cancelled");
			}

		});
	}

	// �α����Ҷ� ���̵�-�н����� �´��� Ȯ��
	public static void checkIDPW(String inputID, String inputPW, Panel_Login pane) {
		
		rootReference.child("users").child(inputID).addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				UserInfo info = snapshot.getValue(UserInfo.class);
				pane.setPWAvailabilityLabel(info.pw.contentEquals(inputPW), info); // PW �´��� �ƴ��� ��ȯ
			}
			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println("cancelled");
			}

		});
	}
	
	public static void putUser(UserInfo info) {
		rootReference.child("users").child(info.id).setValueAsync(info);
	}

}