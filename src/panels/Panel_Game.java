package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import objects.IObject;
import objects.IPlayer;
import objects.Level;
import utils.AudioPlayer;
import utils.SokobanUtilities;

public class Panel_Game extends JPanel {

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {
			case KeyEvent.VK_UP:
				actorManager.movePlayer(0, -SIZE_OF_CELLS);
				AudioPlayer.playSound(SokobanUtilities.AUDIO_FOOTSTEP);
				isCompleted();
				fireMoved();
				break;

			case KeyEvent.VK_DOWN:
				actorManager.movePlayer(0, SIZE_OF_CELLS);
				AudioPlayer.playSound(SokobanUtilities.AUDIO_FOOTSTEP);
				isCompleted();
				fireMoved();
				break;

			case KeyEvent.VK_LEFT:
				actorManager.movePlayer(-SIZE_OF_CELLS, 0);
				AudioPlayer.playSound(SokobanUtilities.AUDIO_FOOTSTEP);
				isCompleted();
				fireMoved();
				break;

			case KeyEvent.VK_RIGHT:
				actorManager.movePlayer(SIZE_OF_CELLS, 0);
				AudioPlayer.playSound(SokobanUtilities.AUDIO_FOOTSTEP);
				isCompleted();
				fireMoved();
				break;

			case KeyEvent.VK_R:
				restartLevel();
				fireRestarted();
				break;

			case KeyEvent.VK_Z:
				undo();
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

	private ArrayList<IObject> walls;
	private ArrayList<IObject> baggs;
	private ArrayList<IObject> areas;
	private ArrayList<IObject> grounds;
	private IPlayer player;
	private Level level;
	private char levelArray[][];
	ArrayList listeners;

	ActorManager actorManager;
	
	public Panel_Game(IGameListener listener, Level givenLevel) {
		
		frame = Frame_Sokoban.getInstance();
		addGameListener(listener);

		if (listener instanceof JPanel) {
			((JPanel) listener).addKeyListener(new KeyListener());
		}

		if (givenLevel == null)
			return;
		level = givenLevel;
		levelArray = level.getCharArray();
		
		actorManager = new ActorManager(givenLevel);

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

		ArrayList<IObject> world = new ArrayList<>();
		
		grounds = actorManager.getGrounds();
		baggs = actorManager.getBaggages();
		areas = actorManager.getAreas();
		walls = actorManager.getWalls();
		player = actorManager.getPlayer();
		

		world.addAll(grounds);
		world.addAll(walls);
		world.addAll(areas);
		world.addAll(baggs);
		world.add(player);

		for (int i = 0; i < world.size(); i++) {

			IObject item = world.get(i);

			g.drawImage(item.getImage(), item.getX(), item.getY(), this);
		}

	}

	private void undo() {
		
		actorManager.undo();

		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			IGameListener listener = (IGameListener) iter.next();
			listener.undid();
		}
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
		revalidate();
		repaint();
	}


	public void isCompleted() {

		int nOfBags = baggs.size();
		int finishedBags = 0;

		for (int i = 0; i < nOfBags; i++) {

			IObject bag = baggs.get(i);

			for (int j = 0; j < nOfBags; j++) {

				IObject area = areas.get(j);
				if (bag.getX() == area.getX() && bag.getY() == area.getY()) {

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

		actorManager = new ActorManager(level);
		revalidate();
		repaint();
	}

	public void setLevel(Level givenLevel) {
		level = givenLevel;
		levelArray = givenLevel.getCharArray();
		actorManager = new ActorManager(givenLevel);
		repaint();
	}

}
