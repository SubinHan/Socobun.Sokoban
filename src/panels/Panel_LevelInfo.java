package panels;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import manageUser.Highscore;
import objects.ILevel;

public class Panel_LevelInfo extends JPanel {
	Frame_Sokoban frame;
	ILevel level;
	Highscore highscore;
	JLabel labelScore;
	JLabel labelMove;
	JLabel labelUndo;
	JLabel labelTime;

	public Panel_LevelInfo(Frame_Sokoban f, ILevel level) {
		frame = f;
		this.level = level;
		
		setLayout(new GridLayout(5, 1));
		
		labelScore = new JLabel();
		labelMove = new JLabel();
		labelUndo = new JLabel();
		labelTime = new JLabel();
		
		labelScore.setFont(new Font("���� ����", Font.PLAIN, 20));
		labelMove.setFont(new Font("���� ����", Font.PLAIN, 20));
		labelUndo.setFont(new Font("���� ����", Font.PLAIN, 20));
		labelTime.setFont(new Font("���� ����", Font.PLAIN, 20));
		
		if(frame.getUserinfo().clearedStagesInfo.get(level.getFile().getName()) != null) {
			highscore = frame.getUserinfo().clearedStagesInfo.get(level.getFile().getName());

			labelScore.setText("Highscore: " + highscore.getScore());
			labelMove.setText("Move: " + highscore.getNumOfMove());
			labelUndo.setText("Undo: " + highscore.getNumOfUndo());
			labelTime.setText("Elapsed Time: " + highscore.getElapsedTime());

		} else {
			labelScore.setText("�ְ������� �������� �ʽ��ϴ�.");
		}
		this.add(labelScore);
		this.add(labelMove);
		this.add(labelUndo);
		this.add(labelTime);
	}

}
