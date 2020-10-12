package com.zetcode.model;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserInfo {
	
	public String id, nickname, pw;
	public int score;
	
	
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
	
	public void addScore(int sc) {
		this.score += sc;
	}
	
	public String toString() {
		return id + ", " + nickname + ", " + pw + ", " + score;
	}
}