package com.zetcode.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Highscore implements Serializable, Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3214460176990195381L;

	private int score, numOfMove, numOfUndo, elapsedTime;

	public Highscore(int score, int numOfMove, int numOfUndo, int elapsedTime) {
		this.score = score;
		this.numOfMove = numOfMove;
		this.numOfUndo = numOfUndo;
		this.elapsedTime = elapsedTime;
	}
	
	public Highscore(String levelName) {
		try {
			getHighscoreFromFile(levelName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public void writeHighscoreToFile(String levelName) throws IOException {
		FileOutputStream f = new FileOutputStream(new File("src/highscores/" + levelName));
		ObjectOutputStream o = new ObjectOutputStream(f);

		o.writeObject(this);
		o.close();
		f.close();
	}

	private void getHighscoreFromFile(String levelName) throws IOException, ClassNotFoundException {
		FileInputStream fi = new FileInputStream(new File("src/highscores/" + levelName));
		ObjectInputStream oi = new ObjectInputStream(fi);
		
		Highscore highscore = (Highscore) oi.readObject();
		
		this.score = highscore.score;
		this.numOfMove = highscore.numOfMove;
		this.numOfUndo = highscore.numOfUndo;
		this.elapsedTime = highscore.elapsedTime;
	}

	@Override
	public int compareTo(Object arg0) {
		return this.score - ((Highscore)arg0).getScore();
	}
}
