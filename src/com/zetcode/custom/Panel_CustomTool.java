package com.zetcode.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zetcode.Frame_Sokoban;
import com.zetcode.SokobanUtilities;
import com.zetcode.game.IGameListener;
import com.zetcode.game.Panel_Game;
import com.zetcode.levelSelect.FileSearcher;
import com.zetcode.levelSelect.Panel_Custom;
import com.zetcode.model.Level;

public class Panel_CustomTool extends JPanel implements IGameListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -35246509229204399L;

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {
			case KeyEvent.VK_ESCAPE:
				frame.changePanel(new Panel_Custom(frame));
				break;

			default:
				break;
			}
			revalidate();
			repaint();
		}
	}

	Frame_Sokoban frame = null;
	private final int MAX_LEVEL_WIDTH = SokobanUtilities.MAX_LEVEL_WIDTH;
	private final int MAX_LEVEL_HEIGHT = SokobanUtilities.MAX_LEVEL_HEIGHT;
	private final int SIZE_OF_CELLS = SokobanUtilities.SIZE_OF_CELLS;
	private final char ACTOR_SPACE = SokobanUtilities.ACTOR_SPACE;
	private final char ACTOR_WALL = SokobanUtilities.ACTOR_WALL;
	private final char ACTOR_GROUND = SokobanUtilities.ACTOR_GROUND;
	private final char ACTOR_AREA = SokobanUtilities.ACTOR_AREA;
	private final char ACTOR_BAGGAGE = SokobanUtilities.ACTOR_BAGGAGE;
	private final char ACTOR_PLAYER = SokobanUtilities.ACTOR_PLAYER;
	private final String IMAGE_WALL = "src/resources/block_08.png";
	private final String IMAGE_GROUND = "src/resources/ground_06.png";
	private final String IMAGE_AREA = "src/resources/environment_ground.png";
	private final String IMAGE_BAGGAGE = "src/resources/crate_02.png";
	private final String IMAGE_PLAYER = "src/resources/player_ground.png";
	private final String IMAGE_SPACE = "src/resources/null.png";
	private boolean isChanged;
	private boolean isConfirmed;
	private boolean isPlaying;
	private char levelArray[][];
	private Level level;

	private JLabel levelImages[][], labelStatus;
	private JPanel panelNorth, panelWest, panelCenter, panelCenterInner, panelEast, panelSouth;
	private JButton buttonPlay, buttonSave, buttonBack;
	private GridBagConstraints gbcs[][];

	private char brush;
	private ArrayList sokobanKeyListeners;

	/**
	 * 새로운 맵 만들기.
	 * 
	 * @param f
	 */
	public Panel_CustomTool(Frame_Sokoban f) {
		frame = f;

		this.addKeyListener(new KeyListener());

		levelArray = new char[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];
		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				levelArray[i][j] = ACTOR_WALL;
			}
		}
		levelImages = new JLabel[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];

		InitUI();
	}

	/**
	 * 수정 모드.
	 * 
	 * @param f
	 * @param level 수정할 level.
	 */
	public Panel_CustomTool(Frame_Sokoban f, char level[][]) {
		// TODO load map.
	}

	/**
	 * Custom Map을 Confirm하기 위해 최소한 한 번 Play로 진입해서 클리어 해야 하는데, Play에서 다시 수정 모드로
	 * 돌입시키는 메소드.
	 */
	public void backToCustomMode() {
		panelCenter.removeAll();
		panelCenter.add(panelCenterInner);
		buttonPlay.setText("Play");
		isPlaying = false;
		revalidate();
		repaint();
	}

	/**
	 * 각 격자의 ImageLabel들을 update함.
	 */
	private void buildLevel() {
		panelCenterInner.removeAll();
		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				panelCenterInner.add(levelImages[i][j], gbcs[i][j]);
			}
		}
	}

	/**
	 * Custom Map을 Clear할 시, 저장할 수 있는 상태가 됨.
	 */
	public void confirm() {
		isConfirmed = true;
		labelStatus.setForeground(Color.GREEN);
		labelStatus.setText("Confirmed!");
	}

	public JPanel getPanelCenter() {
		return panelCenter;
	}

	public JPanel getPanelCenterInner() {
		return panelCenterInner;
	}

	/**
	 * 해당 클래스에서 Map은 BorderLayout의 CenterPanel 내부에 있는
	 * MAX_LEVEL_WIDTH*MAX_LEVEL_HEIGHT 크기의 GridBagLayout으로 구성함.
	 */
	private void initPanelCenter() {
		panelCenter = new JPanel();
		panelCenterInner = new JPanel();
		FlowLayout flow = new FlowLayout();
		GridBagLayout gbl = new GridBagLayout();
		panelCenter.setLayout(flow);
		panelCenterInner.setLayout(gbl);

		panelCenter.setSize(SIZE_OF_CELLS * MAX_LEVEL_WIDTH, SIZE_OF_CELLS * MAX_LEVEL_HEIGHT);

		gbcs = new GridBagConstraints[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];

		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				gbcs[i][j] = new GridBagConstraints();
				gbcs[i][j].fill = GridBagConstraints.BOTH;
				gbcs[i][j].gridx = i;
				gbcs[i][j].gridy = j;
				gbcs[i][j].gridwidth = gbcs[i][j].gridheight = 1;
			}
		}

		updateLevelImages();
		buildLevel();

		panelCenterInner.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse Pressed");
				int x, y;
				x = e.getX() / SIZE_OF_CELLS;
				y = e.getY() / SIZE_OF_CELLS;

				updateLevelImage(x, y, brush);
				buildLevel();
				isChanged = true;
				isConfirmed = false;
				revalidate();
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		panelCenterInner.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println("Mouse Dragged");
				int x, y;
				x = e.getX() / SIZE_OF_CELLS;
				y = e.getY() / SIZE_OF_CELLS;

				updateLevelImage(x, y, brush);
				buildLevel();
				isChanged = true;
				isConfirmed = false;
				revalidate();
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Previewing
				System.out.println(e.getX() + ", " + e.getY());
			}

		});

		panelCenter.add(panelCenterInner);
		this.add(panelCenter, BorderLayout.CENTER);

	}

	/**
	 * 붓을 선택할 수 있는 Brush Button의 생성.
	 */
	private void initPanelEast() {

		panelEast = new JPanel(new FlowLayout());
		panelEast.setPreferredSize(new Dimension(100, 0));
		JLabel title = new JLabel("Brush");
		title.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		panelEast.add(title);

		JButton buttonWall = new JButton(new ImageIcon(IMAGE_WALL));
		buttonWall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brush = ACTOR_WALL;
			}
		});
		JButton buttonGround = new JButton(new ImageIcon(IMAGE_GROUND));
		buttonGround.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brush = ACTOR_GROUND;
			}
		});
		JButton buttonBaggage = new JButton(new ImageIcon(IMAGE_BAGGAGE));
		buttonBaggage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brush = ACTOR_BAGGAGE;
			}
		});
		JButton buttonArea = new JButton(new ImageIcon(IMAGE_AREA));
		buttonArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brush = ACTOR_AREA;
			}
		});
		JButton buttonPlayer = new JButton(new ImageIcon(IMAGE_PLAYER));
		buttonPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brush = ACTOR_PLAYER;
			}
		});
		JButton buttonSpace = new JButton();
		buttonSpace.setPreferredSize(new Dimension(50, 50));
		buttonSpace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brush = ACTOR_SPACE;
				System.out.println("BRUSH_ACTOR_SPACE");
			}
		});
		panelEast.add(buttonWall);
		panelEast.add(buttonGround);
		panelEast.add(buttonBaggage);
		panelEast.add(buttonArea);
		panelEast.add(buttonPlayer);
		panelEast.add(buttonSpace);

		this.add(panelEast, BorderLayout.EAST);
	}

	private void InitUI() {

		setFocusable(true);
		setLayout(new BorderLayout());
		isPlaying = false;

		panelNorth = new JPanel();
		JLabel title = new JLabel("Map Customizing");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(title);
		panelNorth.setPreferredSize(new Dimension(0, 60));
		this.add(panelNorth, BorderLayout.NORTH);

		panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(100, 0));
		this.add(panelWest, BorderLayout.WEST);

		initPanelEast();
		initPanelCenter();
		initPanelSouth();

		brush = ACTOR_GROUND;

	}

	private void initPanelSouth() {
		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonSave = new JButton("Save");
		buttonSave.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isConfirmed) {
					labelStatus.setForeground(Color.GREEN);
					labelStatus.setText("Saved!");
					saveMap();
				} else {
					labelStatus.setForeground(Color.RED);
					labelStatus.setText("need to play and clear once.");
				}
			}
		});
		buttonBack = new JButton("Back (esc)");
		buttonBack.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(new Panel_Custom(frame));
			}
		});
		labelStatus = new JLabel(" ");
		labelStatus.setFont(new Font("맑은 고딕", Font.ITALIC, 20));
		buttonPlay = new JButton("Play");
		buttonPlay.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!isPlaying) {
					if (!isValidMap()) {
						labelStatus.setForeground(Color.RED);
						labelStatus.setText("is not valid map.");
						return;
					}

					Panel_Game panelGame = new Panel_Game(frame, Panel_CustomTool.this, new Level(levelArray));
					panelCenter.removeAll();
					panelCenter.add(panelGame);
					labelStatus.setText(" ");
					buttonPlay.setText("Back to Custom Mode");
					Panel_CustomTool.this.requestFocusInWindow();
					isPlaying = true;
				} else {
					backToCustomMode();
				}
				revalidate();
				repaint();
			}

		});

		panelSouth.add(labelStatus);
		panelSouth.add(buttonPlay);
		panelSouth.add(buttonSave);
		panelSouth.add(buttonBack);
		this.add(panelSouth, BorderLayout.SOUTH);
	}

	/**
	 * 맵을 file로 저장.
	 */
	protected void saveMap() {
		SokobanUtilities.charArrayToFile(
				"src/CustomMaps/Custom Map " + (FileSearcher.getFiles("src/customMaps").length + 1), levelArray);

		isChanged = false;
	}

	/**
	 * @return 플레이어의 유무, 짐과 목적지의 개수가 동일한지를 파악하여 Custom Map이 유효하면 true.
	 */
	private boolean isValidMap() {

		boolean hasPlayer = false;
		boolean hasBaggage = false;
		boolean hasArea = false;
		boolean isValid = false;
		int numOfBaggage = 0;
		int numOfArea = 0;

		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				switch (levelArray[i][j]) {
				case ACTOR_AREA:
					hasArea = true;
					numOfArea++;
					break;
				case ACTOR_BAGGAGE:
					hasBaggage = true;
					numOfBaggage++;
					break;
				case ACTOR_PLAYER:
					hasPlayer = true;
					break;
				default:
					break;
				}
			}
		}
		if (numOfBaggage == numOfArea)
			isValid = true;

		return (hasArea && hasBaggage && hasPlayer && isValid);
	}

	private ImageIcon parseImage(char image) {
		switch (image) {
		case ACTOR_WALL:
			return new ImageIcon(IMAGE_WALL);
		case ACTOR_GROUND:
			return new ImageIcon(IMAGE_GROUND);
		case ACTOR_BAGGAGE:
			return new ImageIcon(IMAGE_BAGGAGE);
		case ACTOR_AREA:
			return new ImageIcon(IMAGE_AREA);
		case ACTOR_PLAYER:
			return new ImageIcon(IMAGE_PLAYER);
		case ACTOR_SPACE:
			return new ImageIcon(IMAGE_SPACE);
		default:
			return null;
		}
	}

	/**
	 * 마우스 클릭을 통해 맵을 수정할 시 호출.
	 * 
	 * @param x     x좌표
	 * @param y     y좌표
	 * @param brush brush의 종류
	 */
	protected void updateLevelImage(int x, int y, char brush) {
		if (x >= MAX_LEVEL_WIDTH || x < 0 || y >= MAX_LEVEL_HEIGHT || y < 0) {
			return;
		}
		if (brush == ACTOR_PLAYER) {
			System.out.println("Player");
			for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
				for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
					if (levelArray[i][j] == ACTOR_PLAYER) {
						levelArray[i][j] = ACTOR_GROUND;
						levelImages[i][j] = new JLabel(parseImage(ACTOR_GROUND));
						levelImages[x][y] = new JLabel(parseImage(brush));
					}
				}
			}
		}
		levelArray[x][y] = brush;
		System.out.println(brush);
		levelImages[x][y] = new JLabel(parseImage(brush));

	}

	/**
	 * 초기 init에 사용됨.
	 */
	private void updateLevelImages() {
		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				levelImages[i][j] = new JLabel(parseImage(levelArray[i][j]));
			}
		}
	}

	@Override
	public void moved() {

	}

	@Override
	public void undid() {

	}

	@Override
	public void restarted() {

	}

	@Override
	public void completed() {
		isConfirmed = true;
		labelStatus.setForeground(Color.GREEN);
		labelStatus.setText("Confirmed!");
		backToCustomMode();
	}

}
