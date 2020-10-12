package com.zetcode.levelSelect;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zetcode.Frame_Sokoban;
import com.zetcode.model.Highscore;
import com.zetcode.model.Level;

public class Panel_LevelInfo extends JPanel {
	Frame_Sokoban frame;
	Level level;
	Highscore highscore;
	JLabel labelScore;
	JLabel labelMove;
	JLabel labelUndo;
	JLabel labelTime;

	public Panel_LevelInfo(Frame_Sokoban f, Level level) {
		frame = f;
		this.level = level;
		
		setLayout(new GridLayout(5, 1));
		
		labelScore = new JLabel();
		labelMove = new JLabel();
		labelUndo = new JLabel();
		labelTime = new JLabel();
		
		labelScore.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 20));
		labelMove.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 20));
		labelUndo.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 20));
		labelTime.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 20));
		
		if(FileSearcher.getFile("src/highscores", level.getFile().getName()).exists()) {
			highscore = new Highscore(level.getFile().getName());

			labelScore.setText("Highscore: " + highscore.getScore());
			labelMove.setText("Move: " + highscore.getNumOfMove());
			labelUndo.setText("Undo: " + highscore.getNumOfUndo());
			labelTime.setText("Elapsed Time: " + highscore.getElapsedTime());

		} else {
			labelScore.setText("√÷∞Ì¡°ºˆ∞° ¡∏¿Á«œ¡ˆ æ Ω¿¥œ¥Ÿ.");
		}
		this.add(labelScore);
		this.add(labelMove);
		this.add(labelUndo);
		this.add(labelTime);
	}

}
