package panels;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.SokobanUtilities;

public class Panel_CustomToolInner extends JPanel {

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
	
	JPanel panelInner;
	
	private char levelArray[][];
	private JLabel levelImages[][];
	private GridBagConstraints gbcs[][];
	
	public Panel_CustomToolInner() {
		levelArray = new char[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];
		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				levelArray[i][j] = ACTOR_WALL;
			}
		}
		levelImages = new JLabel[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];
		
		initUI();
	}
	
	public JPanel getInnerPanel() {
		return panelInner;
	}
	
	public char[][] getLevel() {
		return levelArray;
	}
	
	private void initUI() {
		panelInner = new JPanel();
		FlowLayout flow = new FlowLayout();
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(flow);
		panelInner.setLayout(gbl);

		this.setSize(SIZE_OF_CELLS * MAX_LEVEL_WIDTH, SIZE_OF_CELLS * MAX_LEVEL_HEIGHT);

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

		this.add(panelInner);
	}
	
	private void buildLevel() {
		panelInner.removeAll();
		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				panelInner.add(levelImages[i][j], gbcs[i][j]);
			}
		}
		revalidate();
		repaint();
	}
	
	/**
	 * 마우스 클릭을 통해 맵을 수정할 시 호출.
	 * 
	 * @param x     x좌표
	 * @param y     y좌표
	 * @param brush brush의 종류
	 */
	public void updateLevelImage(int x, int y, char brush) {
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
		repaint();
		buildLevel();
	}
	
	private void updateLevelImages() {
		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				levelImages[i][j] = new JLabel(parseImage(levelArray[i][j]));
			}
		}
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
	
	public void changed() {
		buildLevel();
	}
	
	/**
	 * @return 플레이어의 유무, 짐과 목적지의 개수가 동일한지를 파악하여 Custom Map이 유효하면 true.
	 */
	public boolean isValidMap() {

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
	
}
