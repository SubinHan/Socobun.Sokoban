package com.zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Panel_Board의 내용을 때려박았음. Panel_Board를 재사용 하고 싶은데, 아직 코드 구성 상 힘들 것 같아서 일단 코드
 * 그대로 때려박고 고쳐서 사용 하였음. Panel_CustomPlay는 Panel_CustomTool.PanelCenter을 채우고 있는
 * Panel임.
 */
public class Panel_CustomPlay extends JPanel {

	private static final long serialVersionUID = 1679415530039064054L;

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
//
//            if (isCompleted) {
//                return;
//            }

			int key = e.getKeyCode();

			switch (key) {

			case KeyEvent.VK_LEFT:

				if (checkWallCollision(soko, LEFT_COLLISION))
					return;

				if (checkBagCollision(LEFT_COLLISION))
					return;

				soko.move(-SIZE_OF_CELLS, 0);

				break;

			case KeyEvent.VK_RIGHT:

				if (checkWallCollision(soko, RIGHT_COLLISION)) {
					return;
				}

				if (checkBagCollision(RIGHT_COLLISION)) {
					return;
				}

				soko.move(SIZE_OF_CELLS, 0);

				break;

			case KeyEvent.VK_UP:

				if (checkWallCollision(soko, TOP_COLLISION)) {
					return;
				}

				if (checkBagCollision(TOP_COLLISION)) {
					return;
				}

				soko.move(0, -SIZE_OF_CELLS);

				break;

			case KeyEvent.VK_DOWN:

				if (checkWallCollision(soko, BOTTOM_COLLISION)) {
					return;
				}

				if (checkBagCollision(BOTTOM_COLLISION)) {
					return;
				}

				soko.move(0, SIZE_OF_CELLS);

				break;

			default:
				break;
			}

			repaint();
		}
	}

	Frame_Sokoban frame = null;

	Panel_CustomTool parent;

	private final int MAX_LEVEL_WIDTH = SokobanUtilities.MAX_LEVEL_WIDTH;
	private final int MAX_LEVEL_HEIGHT = SokobanUtilities.MAX_LEVEL_HEIGHT;
	private final int SIZE_OF_CELLS = SokobanUtilities.SIZE_OF_CELLS;
	private final char ACTOR_SPACE = SokobanUtilities.ACTOR_SPACE;
	private final char ACTOR_WALL = SokobanUtilities.ACTOR_WALL;
	private final char ACTOR_GROUND = SokobanUtilities.ACTOR_GROUND;
	private final char ACTOR_AREA = SokobanUtilities.ACTOR_AREA;
	private final char ACTOR_BAGGAGE = SokobanUtilities.ACTOR_BAGGAGE;
	private final char ACTOR_PLAYER = SokobanUtilities.ACTOR_PLAYER;
	private final int LEFT_COLLISION = 1;
	private final int RIGHT_COLLISION = 2;
	private final int TOP_COLLISION = 3;
	private final int BOTTOM_COLLISION = 4;
	private ArrayList<Actor_Wall> walls;
	private ArrayList<Actor_Baggage> baggs;
	private ArrayList<Actor_Area> areas;

	private ArrayList<Actor_Ground> grounds;
	private Actor_Player soko;
	private char level[][];

	/**
	 * 
	 * @param f				
	 * @param parent		이 클래스는 Panel_CustomTool.panelCenter 내부에 있는 Panel임. 따라서 Panel_CustomTool을 인자로 받음.
	 * @param givenLevel	Panel_CustomTool에서 넘겨주는 level을 이용해 맵을 구성하고 플레이함.
	 */
	public Panel_CustomPlay(Frame_Sokoban f, Panel_CustomTool parent, char[][] givenLevel) {
		frame = f;
		this.parent = parent;

		this.addKeyListener(new TAdapter());

		if (givenLevel == null)
			return;
		if (givenLevel.length < MAX_LEVEL_WIDTH)
			return;
		if (givenLevel[0].length < MAX_LEVEL_HEIGHT)
			return;

		level = new char[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];
		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {
				level[i][j] = givenLevel[i][j];
			}
		}

		InitUI();
	}

	private void buildLevel(Graphics g) {

		g.setColor(new Color(85, 85, 85));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		ArrayList<Actor> world = new ArrayList<>();

		world.addAll(grounds);
		world.addAll(walls);
		world.addAll(areas);
		world.addAll(baggs);
		world.add(soko);

		for (int i = 0; i < world.size(); i++) {

			Actor item = world.get(i);

			g.drawImage(item.getImage(), item.x(), item.y(), this);
		}

	}

	private boolean checkBagCollision(int type) {

		switch (type) {

		case LEFT_COLLISION:

			for (int i = 0; i < baggs.size(); i++) {

				Actor_Baggage bag = baggs.get(i);

				if (soko.isLeftCollision(bag)) {

					for (int j = 0; j < baggs.size(); j++) {

						Actor_Baggage item = baggs.get(j);

						if (!bag.equals(item)) {

							if (bag.isLeftCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, LEFT_COLLISION)) {
							return true;
						}
					}
					bag.move(-SIZE_OF_CELLS, 0);
					isCompleted();
				}
			}

			return false;

		case RIGHT_COLLISION:

			for (int i = 0; i < baggs.size(); i++) {

				Actor_Baggage bag = baggs.get(i);

				if (soko.isRightCollision(bag)) {

					for (int j = 0; j < baggs.size(); j++) {

						Actor_Baggage item = baggs.get(j);

						if (!bag.equals(item)) {

							if (bag.isRightCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, RIGHT_COLLISION)) {
							return true;
						}
					}

					bag.move(SIZE_OF_CELLS, 0);
					isCompleted();
				}
			}
			return false;

		case TOP_COLLISION:

			for (int i = 0; i < baggs.size(); i++) {

				Actor_Baggage bag = baggs.get(i);

				if (soko.isTopCollision(bag)) {

					for (int j = 0; j < baggs.size(); j++) {

						Actor_Baggage item = baggs.get(j);

						if (!bag.equals(item)) {

							if (bag.isTopCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, TOP_COLLISION)) {
							return true;
						}
					}

					bag.move(0, -SIZE_OF_CELLS);
					isCompleted();
				}
			}

			return false;

		case BOTTOM_COLLISION:

			for (int i = 0; i < baggs.size(); i++) {

				Actor_Baggage bag = baggs.get(i);

				if (soko.isBottomCollision(bag)) {

					for (int j = 0; j < baggs.size(); j++) {

						Actor_Baggage item = baggs.get(j);

						if (!bag.equals(item)) {

							if (bag.isBottomCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, BOTTOM_COLLISION)) {

							return true;
						}
					}

					bag.move(0, SIZE_OF_CELLS);
					isCompleted();
				}
			}

			break;

		default:
			break;
		}

		return false;
	}

	private boolean checkWallCollision(Actor actor, int type) {

		switch (type) {

		case LEFT_COLLISION:

			for (int i = 0; i < walls.size(); i++) {

				Actor_Wall wall = walls.get(i);

				if (actor.isLeftCollision(wall)) {

					return true;
				}
			}

			return false;

		case RIGHT_COLLISION:

			for (int i = 0; i < walls.size(); i++) {

				Actor_Wall wall = walls.get(i);

				if (actor.isRightCollision(wall)) {
					return true;
				}
			}

			return false;

		case TOP_COLLISION:

			for (int i = 0; i < walls.size(); i++) {

				Actor_Wall wall = walls.get(i);

				if (actor.isTopCollision(wall)) {

					return true;
				}
			}

			return false;

		case BOTTOM_COLLISION:

			for (int i = 0; i < walls.size(); i++) {

				Actor_Wall wall = walls.get(i);

				if (actor.isBottomCollision(wall)) {

					return true;
				}
			}

			return false;

		default:
			break;
		}

		return false;
	}

	
	private void InitUI() {
		this.setPreferredSize(new Dimension(SIZE_OF_CELLS * MAX_LEVEL_WIDTH, SIZE_OF_CELLS * MAX_LEVEL_HEIGHT));
		initWorld();
		revalidate();
		repaint();
	}

	private void initWorld() {

		walls = new ArrayList<>();
		baggs = new ArrayList<>();
		areas = new ArrayList<>();
		grounds = new ArrayList<>();

		Actor_Wall wall;
		Actor_Baggage b;
		Actor_Area a;
		Actor_Ground g;

		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {

				char item = level[i][j];

				switch (item) {
				case ACTOR_WALL:
					g = new Actor_Ground(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					grounds.add(g);
					wall = new Actor_Wall(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					walls.add(wall);
					break;

				case ACTOR_BAGGAGE:
					g = new Actor_Ground(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					grounds.add(g);
					b = new Actor_Baggage(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					baggs.add(b);
					break;

				case ACTOR_AREA:
					g = new Actor_Ground(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					grounds.add(g);
					a = new Actor_Area(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					areas.add(a);
					break;

				case ACTOR_PLAYER:
					g = new Actor_Ground(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					grounds.add(g);
					soko = new Actor_Player(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					break;

				case ACTOR_GROUND:
					g = new Actor_Ground(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
					grounds.add(g);
					break;

				case ' ':
					break;

				default:
					break;
				}
			}
		}
	}

	public void isCompleted() {

		int nOfBags = baggs.size();
		int finishedBags = 0;

		for (int i = 0; i < nOfBags; i++) {

			Actor_Baggage bag = baggs.get(i);
			System.out.println(bag.x() + ", " + bag.y());

			for (int j = 0; j < nOfBags; j++) {

				Actor_Area area = areas.get(j);
				System.out.println(area.x() + ", " + area.y());
				if (bag.x() == area.x() && bag.y() == area.y()) {

					finishedBags += 1;
				}
			}
		}

		if (finishedBags == nOfBags) {
			parent.backToCustomMode();
			parent.confirm();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		buildLevel(g);
	}

	@Override
	public void repaint() {
		super.repaint();
	}

}
