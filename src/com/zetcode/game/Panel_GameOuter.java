package com.zetcode.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zetcode.Frame_Sokoban;
import com.zetcode.SokobanUtilities;
import com.zetcode.levelSelect.FileSearcher;
import com.zetcode.model.Highscore;
import com.zetcode.model.IGameListener;
import com.zetcode.model.ISokobanKeyListener;
import com.zetcode.model.Level;

public class Panel_GameOuter extends JPanel implements IGameListener {

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {

			case KeyEvent.VK_LEFT:
				fireKeyPressed(SokobanUtilities.KEY_LEFT);
				break;

			case KeyEvent.VK_RIGHT:
				fireKeyPressed(SokobanUtilities.KEY_RIGHT);
				break;

			case KeyEvent.VK_UP:
				fireKeyPressed(SokobanUtilities.KEY_UP);
				break;

			case KeyEvent.VK_DOWN:
				fireKeyPressed(SokobanUtilities.KEY_DOWN);
				break;

			case KeyEvent.VK_R:
				fireKeyPressed(SokobanUtilities.KEY_R);
				break;

			case KeyEvent.VK_ESCAPE:
				backToParent();
				break;

			default:
				break;
			}
			revalidate();
			repaint();
		}
	}

	Frame_Sokoban frame = null;
	private Level level;

	private JLabel labelStatus;
	private JPanel panelNorth, panelWest, panelCenter, panelCenterInner, panelEast, panelSouth;
	private JPanel parent;
	private JButton buttonBack;
	private ArrayList sokobanKeyListeners;
	private int score, numOfMove, numOfUndo, elapsedTime;
	private long startedTime;

	private JLabel labelScore;
	private JLabel labelMove;
	private JLabel labelUndo;
	private JLabel labelTime;

	public Panel_GameOuter(Frame_Sokoban f, JPanel parent, Level givenLevel) {
		frame = f;
		this.parent = parent;
		level = givenLevel;

		this.addKeyListener(new KeyListener());

		InitUI();

		if (panelCenter instanceof ISokobanKeyListener)
			addSokobanKeyListener((ISokobanKeyListener) panelCenter);
	}

	private void resetScore() {
		score = 1000;
		numOfMove = 0;
		numOfUndo = 0;
		elapsedTime = 0;
		startedTime = System.currentTimeMillis();
	}

	public void fireKeyPressed(int key) {
		Iterator iter = sokobanKeyListeners.iterator();
		while (iter.hasNext()) {
			ISokobanKeyListener listener = (ISokobanKeyListener) iter.next();
			listener.keyPressed(key);
		}

	}

	private void addSokobanKeyListener(ISokobanKeyListener listener) {
		if (sokobanKeyListeners == null)
			sokobanKeyListeners = new ArrayList();
		sokobanKeyListeners.add(listener);
	}

	private void initPanelCenter() {
		panelCenter = new Panel_Game(frame, this, level);

		this.add(panelCenter, BorderLayout.CENTER);

	}

	private void initPanelEast() {

		panelEast = new JPanel(new FlowLayout());
		panelEast.setPreferredSize(new Dimension(100, 0));
		labelScore = new JLabel();
		labelMove = new JLabel();
		labelUndo = new JLabel();
		labelTime = new JLabel();
		resetScore();
		updateScores();
		labelScore.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labelMove.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labelUndo.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		labelTime.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		panelEast.add(labelScore);
		panelEast.add(labelMove);
		panelEast.add(labelUndo);
		panelEast.add(labelTime);

		this.add(panelEast, BorderLayout.EAST);
	}

	private void InitUI() {

		setFocusable(true);
		setLayout(new BorderLayout());

		panelNorth = new JPanel();
		JLabel title = new JLabel("SOKOBAN");
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(title);
		panelNorth.setPreferredSize(new Dimension(0, 60));
		this.add(panelNorth, BorderLayout.NORTH);

		panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(50, 0));
		this.add(panelWest, BorderLayout.WEST);

		initPanelEast();
		initPanelCenter();
		initPanelSouth();

	}

	private void initPanelSouth() {
		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		buttonBack = new JButton("Quit (esc)");
		buttonBack.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToParent();
			}
		});
		labelStatus = new JLabel(" ");
		labelStatus.setFont(new Font("¸¼Àº °íµñ", Font.ITALIC, 20));

		panelSouth.add(labelStatus);
		panelSouth.add(buttonBack);
		this.add(panelSouth, BorderLayout.SOUTH);
	}

	private void backToParent() {
		frame.changePanel(parent);
	}

	private void updateScores() {
		score = 1000 - numOfMove - numOfUndo - elapsedTime;
		labelScore.setText("Score: " + score);
		labelMove.setText("Move: " + numOfMove);
		labelUndo.setText("Undo: " + numOfUndo);
		labelTime.setText("Elapsed: " + elapsedTime);
	}

	@Override
	public void moved() {
		numOfMove++;
		updateScores();
	}

	@Override
	public void undid() {
		numOfUndo++;
		updateScores();
	}

	@Override
	public void restarted() {
		resetScore();
	}

	@Override
	public void completed() {
		labelStatus.setText("Completed!");
		Highscore newScore = new Highscore(score, numOfMove + 1, numOfUndo, elapsedTime);
		Highscore oldScore;
		if(FileSearcher.getFile("src/highscores", level.getFile().getName()).exists()) {
			oldScore = new Highscore(level.getFile().getName());
			if (oldScore.compareTo(newScore) > 0) {
				;
			} else
				try {
					newScore.writeHighscoreToFile(level.getFile().getName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else
			try {
				newScore.writeHighscoreToFile(level.getFile().getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
