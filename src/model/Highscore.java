package model;

import java.io.Serializable;

public class Highscore implements IModel, Serializable, Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3214460176990195381L;

	private int score, numOfMove, numOfUndo, elapsedTime;

	public Highscore() {}
	
	public Highscore(int score, int numOfMove, int numOfUndo, int elapsedTime) {
		this.score = score;
		this.numOfMove = numOfMove;
		this.numOfUndo = numOfUndo;
		this.elapsedTime = elapsedTime;
	}
	
//	public Highscore(UserInfo userInfo, String levelName) {
//			getHighscore(userInfo, levelName);
//	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumOfMove() {
		return numOfMove;
	}

	public void setNumOfMove(int numOfMove) {
		this.numOfMove = numOfMove;
	}

	public int getNumOfUndo() {
		return numOfUndo;
	}

	public void setNumOfUndo(int numOfUndo) {
		this.numOfUndo = numOfUndo;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

//	public void writeHighscoreToFirebase(UserInfo info, String levelName) {
//		info.clearedStagesInfo.put(levelName, this);
//		FirebaseClass.rootReference.child("users").child(info.id).setValueAsync(info);
//	}
//
//	private void getHighscore(UserInfo userInfo, String levelName){
//		
//		HashMap<String, Highscore> clearedStages = Frame_Sokoban.getInstance().getUserinfo().clearedStagesInfo; 
//		if(clearedStages.get(levelName) == null)
//			return;
//		
//		this.score = clearedStages.get(levelName).score;
//		this.numOfMove = clearedStages.get(levelName).numOfMove;
//		this.numOfUndo = clearedStages.get(levelName).numOfUndo;
//		this.elapsedTime = clearedStages.get(levelName).elapsedTime;
//	}

	@Override
	public int compareTo(Object arg0) {
		return this.score - ((Highscore)arg0).getScore();
	}
}
