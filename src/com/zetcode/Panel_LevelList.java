package com.zetcode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_LevelList extends JPanel implements ILevelSelectorListener {
	
	private Frame_Sokoban frame = null;
	private final String stagePath = "src/stages";

	public Panel_LevelList(Frame_Sokoban f) {

		frame = f;
		this.addKeyListener(new KeyListener());
		initUI();

	}

	private void initUI() {

		setLayout(new BorderLayout());
		
		addKeyListener(new KeyListener());

		JLabel title = new JLabel("Select Level");
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(400, 0));
		this.add(panelWest, BorderLayout.WEST);
		JPanel panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(400, 0));
		this.add(panelEast, BorderLayout.EAST);

		JPanel panelCenter = new Panel_LevelSelector(frame, this, stagePath);
		this.add(panelCenter);

		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton buttonBack = new JButton("Back to Main Menu (esc)");
		buttonBack.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.changePanel(0);
			}
		});
		panelSouth.add(buttonBack);
		this.add(panelSouth, BorderLayout.SOUTH);

	}

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {

			case KeyEvent.VK_ESCAPE:
				frame.changePanel(SokobanUtilities.PANEL_MAIN);

			default:
				break;
			}

			repaint();
		}
	}

	@Override
	public void levelSelected(char[][] level) {
		frame.changePanel(new Panel_Normal(frame, this, level));
	}
}
