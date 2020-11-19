package com.zetcode.Login;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zetcode.model.UserInfo;

public class FirebaseClass {

	private static DataSnapshot dataSnapshot = null;

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

		DBListener dblistener = new DBListener();
		rootReference.addValueEventListener(dblistener);
	}

	private static class DBListener implements ValueEventListener {

		@Override
		public void onDataChange(DataSnapshot snapshot) {
			dataSnapshot = snapshot;
		}

		@Override
		public void onCancelled(DatabaseError error) {

		}

	}

	public static UserInfo findUserByNickname(String inputNickname) {
		Iterable<DataSnapshot> infoIterable = null;
		// null pointer exception ������ ���� ���ܼ� ���� if�� �߰�
		if (dataSnapshot != null)
			infoIterable = dataSnapshot.child("users").getChildren();
		Iterator<DataSnapshot> infoIterator = null;
		if (infoIterable != null) {
			infoIterator = infoIterable.iterator();
			while (infoIterator.hasNext()) {
				DataSnapshot ds = infoIterator.next();
				UserInfo info = ds.getValue(UserInfo.class);
				if (info.nickname.contentEquals(inputNickname)) {
					return ds.getValue(UserInfo.class);
				}
			}
		}
		return null;
	}

	// �α����Ҷ� ���̵�-�н����� �´��� Ȯ��
	public static UserInfo findUserByID(String inputID) {
		DataSnapshot infoDS = null;
		// null pointer exception ������ ���� ���ܼ� ���� if�� �߰�
		if (dataSnapshot != null)
			infoDS = dataSnapshot.child("users").child(inputID);
		if (infoDS.exists()) {
			System.out.println(((UserInfo) infoDS.getValue(UserInfo.class)).clearedStagesInfo);
			return infoDS.getValue(UserInfo.class);
		}
		return null;
	}

	public static void putUser(UserInfo info) {
		rootReference.child("users").child(info.id).setValueAsync(info);
	}

}