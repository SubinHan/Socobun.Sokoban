package objects;

import java.util.ArrayList;
import java.util.Stack;

import model.Level;
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
	
	IPlayer player = null;
	ArrayList<IObject> baggages;

	public IPlayer getPlayer() {
		return player;
	}

	public ArrayList<IObject> getBaggages() {
		return baggages;
	}

	public ArrayList<IObject> getWalls() {
		return walls;
	}

	public ArrayList<IObject> getGrounds() {
		return grounds;
	}

	public ArrayList<IObject> getAreas() {
		return areas;
	}

	ArrayList<IObject> walls;
	ArrayList<IObject> grounds;
	ArrayList<IObject> areas;
	ArrayList<ICollidable> collidableObjects;

	
	public ActorManager(Level level) {
		
		undoStack = new Stack<>();
		initWorld(level);
	}

	private void initWorld(Level level) {

		walls = new ArrayList<>();
		baggages = new ArrayList<>();
		areas = new ArrayList<>();
		grounds = new ArrayList<>();
		collidableObjects = new ArrayList<>();

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
		collidableObjects.add(b);
	}

	private void addWall(int i, int j) {
		Actor_Wall wall;
		Actor_Ground g;
		addGround(i, j);
		wall = new Actor_Wall(i * SIZE_OF_CELLS, j * SIZE_OF_CELLS);
		walls.add(wall);
		collidableObjects.add(wall);
	}

	private ICollidable getCollidableActor(int x, int y) {
		for (int i = 0; i < collidableObjects.size(); i++) {
			ICollidable collidable = collidableObjects.get(i);
			if (collidable.getX() == x && collidable.getY() == y)
				return collidable;
		}
		return null;
	}

	public boolean canMove(int xDelta, int yDelta) {
		ICollidable collidable = getCollidableActor(player.getX() + xDelta, player.getY() + yDelta);

		if (collidable == null)
			return true;
		if (collidable instanceof IMovable) {
			if (getCollidableActor(player.getX() + 2 * xDelta, player.getY() + 2 * yDelta) == null)
				return true;
		}
		return false;

	}

	public void movePlayer(int xDelta, int yDelta) {
		int playerX = player.getX();
		int playerY = player.getY();

		if (!canMove(xDelta, yDelta))
			return;
		ICollidable collidable = getCollidableActor(playerX + xDelta, playerY + yDelta);
		if (collidable != null)
			if (collidable.getType() == "baggage") {
				((Actor_Baggage) collidable).move(xDelta, yDelta);
			}
		player.move(xDelta, yDelta);
		undoStack.add(new UndoSnapshot(xDelta, yDelta, (Actor_Baggage)collidable));
		
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
