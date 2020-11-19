package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import utils.SokobanUtilities;

public class ActorManager {
	
	private final int MAX_LEVEL_WIDTH = SokobanUtilities.MAX_LEVEL_WIDTH;
	private final int MAX_LEVEL_HEIGHT = SokobanUtilities.MAX_LEVEL_HEIGHT;
	private final int SIZE_OF_CELLS = SokobanUtilities.SIZE_OF_CELLS;
	private final char ACTOR_SPACE = SokobanUtilities.ACTOR_SPACE;
	private final char ACTOR_WALL = SokobanUtilities.ACTOR_WALL;
	private final char ACTOR_GROUND = SokobanUtilities.ACTOR_GROUND;
	private final char ACTOR_AREA = SokobanUtilities.ACTOR_AREA;
	private final char ACTOR_BAGGAGE = SokobanUtilities.ACTOR_BAGGAGE;
	private final char ACTOR_PLAYER = SokobanUtilities.ACTOR_PLAYER;

	private Stack<UndoSnapshot> undoStack;
	
	Actor_Player player = null;
	ArrayList<Actor_Baggage> baggages;

	public Actor_Player getPlayer() {
		return player;
	}

	public ArrayList<Actor_Baggage> getBaggages() {
		return baggages;
	}

	public ArrayList<Actor_Wall> getWalls() {
		return walls;
	}

	public ArrayList<Actor_Ground> getGrounds() {
		return grounds;
	}

	public ArrayList<Actor_Area> getAreas() {
		return areas;
	}

	ArrayList<Actor_Wall> walls;
	ArrayList<Actor_Ground> grounds;
	ArrayList<Actor_Area> areas;
	ArrayList<Actor> collisionObjects;

	
	public ActorManager(Level level) {
		
		undoStack = new Stack<>();
		initWorld(level);
	}

	private void initWorld(Level level) {

		walls = new ArrayList<>();
		baggages = new ArrayList<>();
		areas = new ArrayList<>();
		grounds = new ArrayList<>();
		collisionObjects = new ArrayList<>();

		Actor_Wall wall;
		Actor_Baggage b;
		Actor_Area a;
		Actor_Ground g;

		char[][] levelArray = level.getCharArray();

		for (int i = 0; i < MAX_LEVEL_WIDTH; i++) {
			for (int j = 0; j < MAX_LEVEL_HEIGHT; j++) {

				char item = levelArray[i][j];

				switch (item) {
				case ACTOR_WALL:
					addWall(i, j);
					break;

				case ACTOR_BAGGAGE:
					addBaggage(i, j);
					break;

				case ACTOR_AREA:
					addArea(i, j);
					break;

				case ACTOR_PLAYER:
					addPlayer(i, j);
					break;

				case ACTOR_GROUND:
					addGround(i, j);
					break;

				case ' ':
					break;

				default:
					break;
				}
			}
		}
	}

	private void addGround(int i, int j) {
		Actor_Ground g;
		g = new Actor_Ground(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
		grounds.add(g);
	}

	private void addPlayer(int i, int j) {
		Actor_Ground g;
		addGround(i, j);
		player = new Actor_Player(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
	}

	private void addArea(int i, int j) {
		Actor_Area a;
		Actor_Ground g;
		addGround(i, j);
		a = new Actor_Area(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
		areas.add(a);
	}

	private void addBaggage(int i, int j) {
		Actor_Baggage b;
		Actor_Ground g;
		addGround(i, j);
		b = new Actor_Baggage(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
		baggages.add(b);
		collisionObjects.add(b);
	}

	private void addWall(int i, int j) {
		Actor_Wall wall;
		Actor_Ground g;
		addGround(i, j);
		wall = new Actor_Wall(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
		walls.add(wall);
		collisionObjects.add(wall);
	}

	private Actor getActor(int x, int y) {
		for (int i = 0; i < collisionObjects.size(); i++) {
			Actor actor = collisionObjects.get(i);
			if (actor.x() == x && actor.y() == y)
				return actor;
		}
		return null;
	}

	public boolean canMove(int xDelta, int yDelta) {
		Actor actor = getActor(player.x() + xDelta, player.y() + yDelta);

		if (actor == null)
			return true;
		if (actor.getType() == "baggage") {
			if (getActor(player.x() + 2 * xDelta, player.y() + 2 * yDelta) == null)
				return true;
		}
		return false;

	}

	public void move(int xDelta, int yDelta) {
		int playerX = player.x();
		int playerY = player.y();

		if (!canMove(xDelta, yDelta))
			return;
		Actor actor = getActor(playerX + xDelta, playerY + yDelta);
		if (actor != null)
			if (actor.getType() == "baggage") {
				((Actor_Baggage) actor).move(xDelta, yDelta);
			}
		player.move(xDelta, yDelta);
		undoStack.add(new UndoSnapshot(xDelta, yDelta, (Actor_Baggage)actor));
		
	}
	
	public void undo() {

		if (undoStack.size() < 1)
			return;

		UndoSnapshot popped = undoStack.pop();
		int dx = popped.getDeltaX();
		int dy = popped.getDeltaY();
		player.undo(-dx, -dy);
		if (popped.bag != null)
			popped.bag.move(-dx, -dy);
		
	}

}
