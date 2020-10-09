package com.zetcode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Panel_Normal extends JPanel implements IGameListener {

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {
			
			case KeyEvent.VK_LEFT:
				fireKeyPressed(SokobanUtilities.KEY_LEFT);
				break;
				
			case KeyEvent.VK_RIGHT:
				fireKeyPressed(SokobanUtilities.KEY_RIGHT);
				break;
				
			case KeyEvent.VK_UP:
				fireKeyPressed(SokobanUtilities.KEY_UP);
				break;
				
			case KeyEvent.VK_DOWN:
				fireKeyPressed(SokobanUtilities.KEY_DOWN);
				break;
				
			case KeyEvent.VK_R:
				fireKeyPressed(SokobanUtilities.KEY_R);
				break;

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
	private char level[][];

	private JLabel levelImages[][], labelStatus;
	private JPanel panelNorth, panelWest, panelCenter, panelCenterInner, panelEast, panelSouth;
	private JPanel parent;
	private JButton buttonPlay, buttonSave, buttonBack;
	private GridBagConstraints gbcs[][];
	private ArrayList sokobanKeyListeners;
	private int score, numOfMove, numOfUndo;
	private long startedTime, elapsedTime;

	private char brush;

	/**
	 * »õ·Î¿î ¸Ê ¸¸µé±â.
	 * 
	 * @param f
	 */
	public Panel_Normal(Frame_Sokoban f, JPanel parent, char[][] givenLevel) {
		frame = f;
		this.parent = parent;
		level = givenLevel;
		
		this.addKeyListener(new KeyListener());
		

		InitUI();
		if(panelCenter instanceof ISokobanKeyListener)
			addSokobanKeyListener((ISokobanKeyListener) panelCenter);
	}

	public void fireKeyPressed(int key) {
		Iterator iter = sokobanKeyListeners.iterator();
		while(iter.hasNext()) {
			ISokobanKeyListener listener = (ISokobanKeyListener)iter.next();
			listener.keyPressed(key);
		}
		
	}

	private void addSokobanKeyListener(ISokobanKeyListener listener) {
		if(sokobanKeyListeners == null)
			sokobanKeyListeners = new ArrayList();
		sokobanKeyListeners.add(listener);
	}

	/**
	 * ÇØ´ç Å¬·¡½º¿¡¼­ MapÀº BorderLayoutÀÇ CenterPanel ³»ºÎ¿¡ ÀÖ´Â
	 * MAX_LEVEL_WIDTH*MAX_LEVEL_HEIGHT Å©±âÀÇ GridBagLayoutÀ¸·Î ±¸¼ºÇÔ.
	 */
	private void initPanelCenter() {
		panelCenter = new Panel_Game(frame, this, level);

		this.add(panelCenter, BorderLayout.CENTER);
		
	}

	private void initPanelEast() {

		panelEast = new JPanel(new FlowLayout());
		panelEast.setPreferredSize(new Dimension(100, 0));
		JLabel title = new JLabel("Brush");
		title.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 10));
		panelEast.add(title);

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
		panelWest.setPreferredSize(new Dimension(100, 0));
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
		frame.changePanel(parent);
	}

	@Override
	public void moved() {
		// TODO Auto-generated method stub
		numOfMove++;
	}

	@Override
	public void restarted() {
		// TODO Auto-generated method stub
		score = 1000;
		numOfMove = 0;
		numOfUndo = 0;
		elapsedTime = 0;
		startedTime = System.currentTimeMillis();
	}

	@Override
	public void completed() {
		labelStatus.setText("Completed!");		
	}

}
