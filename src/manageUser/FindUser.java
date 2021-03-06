package manageUser;

import java.util.Iterator;

import com.google.firebase.database.DataSnapshot;

public abstract class FindUser {

	public static UserInfo findUserByNickname(String inputNickname) {
		Iterable<DataSnapshot> infoIterable = null;
		// null pointer exception 에러가 종종 생겨서 다음 if문 추가
		if (FirebaseClass.database != null)
			if (FirebaseClass.dataSnapshot != null)
				infoIterable = FirebaseClass.dataSnapshot.child("users").getChildren();
		
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

	public static UserInfo findUserByID(String inputID) {
		DataSnapshot infoDS = null;
		// null pointer exception 에러가 종종 생겨서 다음 if문 추가
		if (FirebaseClass.database != null)
			if (FirebaseClass.dataSnapshot != null)
				infoDS = FirebaseClass.dataSnapshot.child("users").child(inputID);
		if (infoDS != null && infoDS.exists()) {
			return infoDS.getValue(UserInfo.class);
		}
		return null;
	}
	
}
