package com.zetcode.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import com.zetcode.Frame_Sokoban;
import com.zetcode.SokobanUtilities;
import com.zetcode.model.Actor;
import com.zetcode.model.Actor_Area;
import com.zetcode.model.Actor_Baggage;
import com.zetcode.model.Actor_Ground;
import com.zetcode.model.Actor_Player;
import com.zetcode.model.Actor_Wall;
import com.zetcode.model.IGameListener;
import com.zetcode.model.ISokobanKeyListener;
import com.zetcode.model.Level;

public class Panel_Game extends JPanel implements ISokobanKeyListener {

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
	private final int LEFT_COLLISION = 1;
	private final int RIGHT_COLLISION = 2;
	private final int TOP_COLLISION = 3;
	private final int BOTTOM_COLLISION = 4;
	private ArrayList<Actor_Wall> walls;
	private ArrayList<Actor_Baggage> baggs;
	private ArrayList<Actor_Area> areas;
	private ArrayList<Actor_Ground> grounds;
	private Actor_Player soko;
	private Level level;
	private char levelArray[][];
	ArrayList listeners;

	public Panel_Game(Frame_Sokoban f, IGameListener listener, Level givenLevel) {
		frame = f;
		addGameListener(listener);
		
		if (givenLevel == null)
			return;
		level = givenLevel;
		levelArray = level.getCharArray();

		InitUI();
	}

	private void addGameListener(IGameListener listener) {
		if (listeners == null) {
			listeners = new ArrayList();
		}
		listeners.add(listener);
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

	private void fireCompleted() {
		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			IGameListener listener = (IGameListener) iter.next();
			listener.completed();
		}
	}

	public void fireMoved() {
		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			IGameListener listener = (IGameListener) iter.next();
			listener.moved();
		}
	}

	public void fireRestarted() {
		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			IGameListener listener = (IGameListener) iter.next();
			listener.restarted();
		}
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

				char item = levelArray[i][j];

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

			for (int j = 0; j < nOfBags; j++) {

				Actor_Area area = areas.get(j);
				if (bag.x() == area.x() && bag.y() == area.y()) {

					finishedBags += 1;
				}
			}
		}

		if (finishedBags == nOfBags) {
			fireCompleted();
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

	private void restartLevel() {

		areas.clear();
		baggs.clear();
		walls.clear();

		initWorld();
	}

	@Override
	public void keyPressed(int key) {
		switch (key) {
		case SokobanUtilities.KEY_UP:
			if (checkWallCollision(soko, TOP_COLLISION)) {
				return;
			}
			if (checkBagCollision(TOP_COLLISION)) {
				return;
			}
			soko.move(0, -SIZE_OF_CELLS);
			fireMoved();
			break;
			
		case SokobanUtilities.KEY_DOWN:
			if (checkWallCollision(soko, BOTTOM_COLLISION)) {
				return;
			}
			if (checkBagCollision(BOTTOM_COLLISION)) {
				return;
			}
			soko.move(0, SIZE_OF_CELLS);
			fireMoved();
			break;
			
		case SokobanUtilities.KEY_LEFT:
			if (checkWallCollision(soko, LEFT_COLLISION))
				return;
			if (checkBagCollision(LEFT_COLLISION))
				return;
			soko.move(-SIZE_OF_CELLS, 0);
			fireMoved();
			break;
			
		case SokobanUtilities.KEY_RIGHT:
			if (checkWallCollision(soko, RIGHT_COLLISION)) {
				return;
			}
			if (checkBagCollision(RIGHT_COLLISION)) {
				return;
			}
			soko.move(SIZE_OF_CELLS, 0);
			fireMoved();
			break;
			
		case SokobanUtilities.KEY_R:
			restartLevel();
			fireRestarted();
			break;
			
		default:
			break;
		}

		repaint();
	}

}
