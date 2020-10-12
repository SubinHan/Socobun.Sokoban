package com.zetcode.levelSelect;

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
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zetcode.Frame_Sokoban;
import com.zetcode.SokobanUtilities;
import com.zetcode.game.Panel_GameOuter;
import com.zetcode.model.Level;

/**
 * Custom Mode 진입 시 첫 화면.
 * @author 한수빈
 *
 */
public class Panel_Custom extends JPanel implements ILevelSelectorListener {

	private static final long serialVersionUID = 4962034188446655988L;
	Frame_Sokoban frame = null;
	private static final String directory = "src/CustomMaps";

	public Panel_Custom(Frame_Sokoban f) {

		frame = f;
		this.addKeyListener(new KeyListener());
		initUI();

	}

	/**
	 * 새로운 맵 만들기, 이하로는 만들어진 맵들을 플레이 하는 버튼으로 구성 됨.
	 * 이전에 만들어진 Custom Map이 있다면 해당 맵들을 플레이할 수 있는 버튼을 만들어줌.
	 * 현재는 Sample이라는 파일만 인식해서 버튼을 생성함.
	 */
	private void initUI() {

		setLayout(new BorderLayout());

		JLabel title = new JLabel("Map Customizing");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(400, 0));
		this.add(panelWest, BorderLayout.WEST);
		JPanel panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(400, 0));
		this.add(panelEast, BorderLayout.EAST);
		
		JPanel panelCenter;
		panelCenter = new JPanel(new BorderLayout());
		JButton buttonNew = new JButton("New Custom Map..");
		buttonNew.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(SokobanUtilities.PANEL_CUSTOMTOOL);
			}
		});
		panelCenter.add(buttonNew, BorderLayout.NORTH);
		JPanel panelList;
		panelList = new Panel_LevelSelector(frame, this, directory);
		panelCenter.add(panelList, BorderLayout.CENTER);
	
		this.add(panelCenter, BorderLayout.CENTER);

		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton buttonBack = new JButton("Back to Main Menu (esc)");
		buttonBack.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.changePanel(SokobanUtilities.PANEL_MAIN);
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
	public void levelSelected(Level level) {
		frame.changePanel(new Panel_GameOuter(frame, this, level));
	}

}
