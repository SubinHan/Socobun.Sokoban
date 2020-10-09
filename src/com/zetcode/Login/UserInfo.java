package com.zetcode.Login;

public class UserInfo {
	
	String id, pw, nickname;
	int score;
	
	public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(UserInfo.class)
    }
	
	public UserInfo(String nickname, String id, String pw) {
		this.nickname = nickname;
		this.id = id;
		this.pw = pw;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public String getID() {
		return this.id;
	}

	public String getPW() {
		return this.pw;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int newscore) {
		this.score = newscore;
	}
	
}