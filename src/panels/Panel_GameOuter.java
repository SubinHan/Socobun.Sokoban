package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Highscore;
import model.Level;
import utils.AudioPlayer;
import utils.SokobanUtilities;

public class Panel_GameOuter extends JPanel implements IGameListener {

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {

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
	private JPanel panelNorth, panelWest, panelCenter, panelEast;
	private JButton buttonBack;
	private int score, numOfMove, numOfUndo, elapsedTime;
	private Timer timer;
	private Clip backgroundClip;

	private JLabel labelScore;
	private JLabel labelMove;
	private JLabel labelUndo;
	private JLabel labelTime;

	public Panel_GameOuter(Level givenLevel) {
		
		frame = Frame_Sokoban.getInstance();
		level = givenLevel;
		
		backgroundClip = AudioPlayer.playSoundLoop(SokobanUtilities.AUDIO_BGM);

		this.addKeyListener(new KeyListener());

		InitUI();
	}

	private void resetScore() {
		if (timer == null) {
			timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					elapsedTime++;
					updateScores();
				}
			});
			timer.start();
		} else
			timer.restart();
		score = 1000;
		numOfMove = 0;
		numOfUndo = 0;
		elapsedTime = 0;
		updateScores();
	}

	private void initPanelCenter() {
		panelCenter = new Panel_Game(this, level);

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
		timer.stop();
		backgroundClip.stop();
		PanelChanger.backToPreviousPanel();
	}

	private void updateScores() {
		score = 1000 - numOfMove - (numOfUndo*10) - elapsedTime;
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
		updateScores();
		revalidate();
		repaint();
	}

	@Override
	public void completed() {
		labelStatus.setText("Completed!");
		timer.stop();
		Highscore newScore = new Highscore(score, numOfMove + 1, numOfUndo, elapsedTime);
		if (Frame_Sokoban.userinfo.clearedStagesInfo.get(level.getFile().getName()) != null) {
			Highscore oldScore = Frame_Sokoban.userinfo.clearedStagesInfo.get(level.getFile().getName());
			if (oldScore.compareTo(newScore) > 0) {
				;
			} else
				newScore.writeHighscoreToFirebase(level.getFile().getName());
		} else
			newScore.writeHighscoreToFirebase(level.getFile().getName());
	}

}
