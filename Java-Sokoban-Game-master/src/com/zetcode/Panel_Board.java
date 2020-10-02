package com.zetcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Panel_Board extends JPanel {

	private static final long serialVersionUID = 6877713473902207903L;
	
	private final int OFFSET = 64;
    private final int SPACE = 64;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private ArrayList<Actor_Wall> walls;
    private ArrayList<Actor_Baggage> baggs;
    private ArrayList<Actor_Area> areas;
    private ArrayList<Actor_Ground> grounds;
    
    private Actor_Player soko;
    private int w = 0;
    private int h = 0;
    
    private Frame_Sokoban frame = null;
    
    private boolean isCompleted = false;
	private boolean maximized = false;

    private String level
            = "    #####\n"
            + "    #^^^#\n"
            + "    #$^^#\n"
            + "  ###^^$##\n"
            + "  #^^$^$^#\n"
            + "###^#^##^#   ######\n"
            + "#^^^#^##^#####^^..#\n"
            + "#^$^^$^^^^^^^^^^..#\n"
            + "#####^###^#@##^^..#\n"
            + "    #^^^^^#########\n"
            + "    #######\n";

    public Panel_Board(Frame_Sokoban f) {
        this.frame = f;
        initBoard();
    }

    private void initBoard() {
    	TAdapter listener = new TAdapter();
        frame.setSize(getBoardWidth() + 24, getBoardHeight() + 39);
        addKeyListener(listener);
        
        setFocusable(true);
        initWorld();
        
    }
    
    public int getBoardWidth() {
    	return this.w;
    }
    
    public int getBoardHeight() {
    	return this.h;
    }
    
    private void initWorld() {
        
        walls = new ArrayList<>();
        baggs = new ArrayList<>();
        areas = new ArrayList<>();
        grounds = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Actor_Wall wall;
        Actor_Baggage b;
        Actor_Area a;
        Actor_Ground g;

        for (int i = 0; i < level.length(); i++) {

            char item = level.charAt(i);

            switch (item) {

                case '\n':
                    y += SPACE;

                    if (this.w < x) {
                        this.w = x + SPACE;
                    }

                    x = OFFSET;
                    break;

                case '#':
                	g = new Actor_Ground(x, y);
                	grounds.add(g);
                    wall = new Actor_Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '$':
                	g = new Actor_Ground(x, y);
                	grounds.add(g);
                    b = new Actor_Baggage(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case '.':
                	g = new Actor_Ground(x, y);
                	grounds.add(g);
                    a = new Actor_Area(x, y);
                    areas.add(a);
                    x += SPACE;
                    break;

                case '@':
                	g = new Actor_Ground(x, y);
                	grounds.add(g);
                    soko = new Actor_Player(x, y);
                    x += SPACE;
                    break;
                    
                case '^':
                	g = new Actor_Ground(x, y);
                	grounds.add(g);
                	x += SPACE;
                	break;

                case ' ':
                    x += SPACE;
                    break;	

                default:
                    break;
            }

            h = y + SPACE;
        }
    }

    private void buildWorld(Graphics g) {

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

            if (isCompleted) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        buildWorld(g);
    }

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
                    
                    if (checkWallCollision(soko, LEFT_COLLISION)) return;
                    
                    if (checkBagCollision(LEFT_COLLISION)) return;
                    
                    if (isCompleted) return;
                    
                    soko.move(-SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    
                    if (checkWallCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }

                    if (isCompleted) return;
                    
                    soko.move(SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_UP:
                    
                    if (checkWallCollision(soko, TOP_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }

                    if (isCompleted) return;
                    
                    soko.move(0, -SPACE);
                    
                    break;
                    
                case KeyEvent.VK_DOWN:
                    
                    if (checkWallCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }

                    if (isCompleted) return;
                    
                    soko.move(0, SPACE);
                    
                    break;
                    
                case KeyEvent.VK_R:
                    
                    restartLevel();
                    
                    break;
                    
                case KeyEvent.VK_F:
                    if(maximized) {
                    	frame.setExtendedState(JFrame.NORMAL);
                    	maximized = false;
                    }
                    else {
                    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    	maximized = true;
                    }
                    break;
                    
                case KeyEvent.VK_T :
                	frame.changePanel(0);
                	
                default:
                    break;
            }

            repaint();
        }
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
                        
                        bag.move(-SPACE, 0);
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
                        
                        bag.move(SPACE, 0);
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
                        
                        bag.move(0, -SPACE);
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
                            
                            if (checkWallCollision(bag,BOTTOM_COLLISION)) {
                                
                                return true;
                            }
                        }
                        
                        bag.move(0, SPACE);
                        isCompleted();
                    }
                }
                
                break;
                
            default:
                break;
        }

        return false;
    }

    public void isCompleted() {

        int nOfBags = baggs.size();
        int finishedBags = 0;

        for (int i = 0; i < nOfBags; i++) {
            
            Actor_Baggage bag = baggs.get(i);
            
            for (int j = 0; j < nOfBags; j++) {
                
                Actor_Area area =  areas.get(j);
                
                if (bag.x() == area.x() && bag.y() == area.y()) {
                    
                    finishedBags += 1;
                }
            }
        }

        if (finishedBags == nOfBags) {
            
            isCompleted = true;
            repaint();
        }
    }

    private void restartLevel() {

        areas.clear();
        baggs.clear();
        walls.clear();

        initWorld();

        if (isCompleted) {
            isCompleted = false;
        }
    }
}