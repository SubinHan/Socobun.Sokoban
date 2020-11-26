package manageUser;
import java.util.HashMap;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserInfo{
	
	public String id, nickname, pw;
	public int wholeScore;
	public HashMap<String, Highscore> clearedStagesInfo = new HashMap<>();
	
	public UserInfo() {
    }
	
	public UserInfo(String nickname, String id, char[] pw) {
		this.nickname = nickname;
		this.id = id;
		this.pw = passwordToString(pw);
	}

	public static String passwordToString(char[] ch) {
		int len = ch.length;
		String result = "";
		for(int i=0 ; i<len ; ++i) {
			result += ch[i];
		}
		return result;
	}
	
	public String toString() {
		return id + ", " + nickname + ", " + pw;
	}
	
	private void refreshHighScore() {
		
	}
	
}

