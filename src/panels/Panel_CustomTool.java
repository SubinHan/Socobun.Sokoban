package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import model.Level;
import utils.IFileSearcher;
import utils.LevelFileSearcher;
import utils.SokobanUtilities;

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
				PanelChanger.backToPreviousPanel();
				break;

			default:
				break;
			}
		}
	}

	private class SaveActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (isConfirmed)
				saveMap();
			else {
				labelStatus.setForeground(Color.RED);
				labelStatus.setText("need to play and clear once.");
			}
		}
		
	}
	
	private class PlayActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isPlaying) {
				if (!panelCustomToolInner.isValidMap()) {
					labelStatus.setForeground(Color.RED);
					labelStatus.setText("is not valid map.");
					return;
				}
				panelGame.setLevel(new Level(panelCustomToolInner.getLevel()));
				panelCenter.removeAll();
				panelCenter.add(panelGame);
				labelStatus.setText(" ");
				buttonPlay.setText("Back to Custom Mode");
				Panel_CustomTool.this.requestFocusInWindow();
				isPlaying = true;
				repaint();
			} else {
				backToCustomMode();
			}
		}
		
	}
	
	private ActionListener createActionListener(char brushInfo) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brush = brushInfo;
			}

		};
	}

	Frame_Sokoban frame = null;
	private final String IMAGE_WALL = "src/resources/block_08.png";
	private final String IMAGE_GROUND = "src/resources/ground_06.png";
	private final String IMAGE_AREA = "src/resources/environment_ground.png";
	private final String IMAGE_BAGGAGE = "src/resources/crate_02.png";
	private final String IMAGE_PLAYER = "src/resources/player_ground.png";
	private final String IMAGE_SPACE = "src/resources/null.png";
	private boolean isConfirmed;
	private boolean isPlaying;

	private JLabel labelStatus;
	private JPanel panelNorth, panelWest, panelCenter, panelEast;
	private Panel_Game panelGame;
	private Panel_CustomToolInner panelCustomToolInner;
	private JButton buttonPlay;

	private char brush;

	/**
	 * ���ο� �� �����.
	 * 
	 * @param f
	 */

	public Panel_CustomTool() {
		frame = Frame_Sokoban.getInstance();
		panelGame = new Panel_Game(Panel_CustomTool.this, new Level());
		panelCustomToolInner = new Panel_CustomToolInner();
		this.addKeyListener(new KeyListener());
		brush = SokobanUtilities.ACTOR_GROUND;
		InitUI();
	}

	/**
	 * ���� ���.
	 * 
	 * @param f
	 * @param level ������ level.
	 */
//	public Panel_CustomTool(Frame_Sokoban f, char level[][]) {
//		// TODO load map.
//	}

	/**
	 * Custom Map�� Confirm�ϱ� ���� �ּ��� �� �� Play�� �����ؼ� Ŭ���� �ؾ� �ϴµ�, Play���� �ٽ� ���� ����
	 * ���Խ�Ű�� �޼ҵ�.
	 */
	public void backToCustomMode() {
		panelCenter.removeAll();
		panelCenter.add(panelCustomToolInner);
		buttonPlay.setText("Play");
		isPlaying = false;
		repaint();
	}

	/**
	 * Custom Map�� Clear�� ��, ������ �� �ִ� ���°� ��.
	 */
	public void confirm() {
		isConfirmed = true;
		labelStatus.setForeground(Color.GREEN);
		labelStatus.setText("Confirmed!");
	}

	/**
	 * �ش� Ŭ�������� Map�� BorderLayout�� CenterPanel ���ο� �ִ�
	 * MAX_LEVEL_WIDTH*MAX_LEVEL_HEIGHT ũ���� GridBagLayout���� ������.
	 */
	private void initPanelCenter() {
		panelCenter = new JPanel();

		panelCustomToolInner.addMouseListener(new MouseListener() {

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
				updateLevelImage(e.getX() / SokobanUtilities.SIZE_OF_CELLS, e.getY() / SokobanUtilities.SIZE_OF_CELLS);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		panelCustomToolInner.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				updateLevelImage(e.getX() / SokobanUtilities.SIZE_OF_CELLS, e.getY() / SokobanUtilities.SIZE_OF_CELLS);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Previewing
			}

		});

		panelCenter.add(panelCustomToolInner);
		this.add(panelCenter, BorderLayout.CENTER);

	}
	
	private void updateLevelImage(int x, int y) {
		panelCustomToolInner.updateLevelImage(x, y, brush);
		isConfirmed = false;
		revalidate();
		repaint();
	}

	private void initPanelEast() {

		panelEast = new JPanel(new FlowLayout());
		panelEast.setPreferredSize(new Dimension(100, 0));
		JLabel title = new JLabel("Brush");
		title.setFont(new Font("���� ���", Font.PLAIN, 10));
		panelEast.add(title);

		JButton buttonWall = new JButton(new ImageIcon(IMAGE_WALL));
		buttonWall.addActionListener(createActionListener(SokobanUtilities.ACTOR_WALL));
		JButton buttonGround = new JButton(new ImageIcon(IMAGE_GROUND));
		buttonGround.addActionListener(createActionListener(SokobanUtilities.ACTOR_GROUND));
		JButton buttonBaggage = new JButton(new ImageIcon(IMAGE_BAGGAGE));
		buttonBaggage.addActionListener(createActionListener(SokobanUtilities.ACTOR_BAGGAGE));
		JButton buttonArea = new JButton(new ImageIcon(IMAGE_AREA));
		buttonArea.addActionListener(createActionListener(SokobanUtilities.ACTOR_AREA));
		JButton buttonPlayer = new JButton(new ImageIcon(IMAGE_PLAYER));
		buttonPlayer.addActionListener(createActionListener(SokobanUtilities.ACTOR_PLAYER));
		JButton buttonSpace = new JButton(new ImageIcon(IMAGE_SPACE));
		buttonSpace.addActionListener(createActionListener(SokobanUtilities.ACTOR_SPACE));
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
		title.setFont(new Font("���� ���", Font.BOLD, 40));
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
	}

	private void initPanelSouth() {
		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton buttonSave;
		JButton buttonBack;
		buttonSave = new JButton("Save");
		buttonSave.setFont(new Font("���� ���", Font.PLAIN, 20));
		buttonSave.addActionListener(new SaveActionListener());
		buttonBack = new JButton("Back (esc)");
		buttonBack.setFont(new Font("���� ���", Font.PLAIN, 20));
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelChanger.backToPreviousPanel();;
			}
		});
		labelStatus = new JLabel(" ");
		labelStatus.setFont(new Font("���� ���", Font.ITALIC, 20));
		buttonPlay = new JButton("Play");
		buttonPlay.setFont(new Font("���� ���", Font.PLAIN, 20));
		buttonPlay.addActionListener(new PlayActionListener());

		panelSouth.add(labelStatus);
		panelSouth.add(buttonPlay);
		panelSouth.add(buttonSave);
		panelSouth.add(buttonBack);
		this.add(panelSouth, BorderLayout.SOUTH);
	}

	/**
	 * ���� file�� ����.
	 */
	protected void saveMap() {
		labelStatus.setForeground(Color.GREEN);
		labelStatus.setText("Saved!");
		
		IFileSearcher fileSearcher;
		fileSearcher = new LevelFileSearcher();

		SokobanUtilities.charArrayToFile(
				"src/CustomMaps/Custom Map " + (fileSearcher.getFiles("src/customMaps").length + 1),
				panelCustomToolInner.getLevel());
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

