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
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Custom Mode ���� �� ù ȭ��.
 * @author �Ѽ���
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
	 * ���ο� �� �����, ���Ϸδ� ������� �ʵ��� �÷��� �ϴ� ��ư���� ���� ��.
	 * ������ ������� Custom Map�� �ִٸ� �ش� �ʵ��� �÷����� �� �ִ� ��ư�� �������.
	 * ����� Sample�̶�� ���ϸ� �ν��ؼ� ��ư�� ������.
	 */
	private void initUI() {

		setLayout(new BorderLayout());

		JLabel title = new JLabel("Map Customizing");
		title.setFont(new Font("���� ���", Font.BOLD, 40));
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
		buttonNew.setFont(new Font("���� ���", Font.PLAIN, 20));
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.changePanel(SokobanUtilities.PANEL_CUSTOMTOOL);
			}
		});
		panelCenter.add(buttonNew, BorderLayout.NORTH);
		JPanel panelList;
		panelList = new Panel_LevelSelector(frame, this, directory);
		panelCenter.add(panelList, BorderLayout.CENTER);
		
//		JPanel panelCenter = new JPanel(new GridLayout(10, 1));
//		JButton buttonNew = new JButton("New Custom Map..");
//		buttonNew.setFont(new Font("���� ���", Font.PLAIN, 20));
//		buttonNew.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				frame.changePanel(5);
//			}
//		});
//		panelCenter.add(buttonNew);
//		File file = new File("Sample");
//		if(file.exists()) {
//			JButton buttonCustomMap = new JButton(file.getName());
//			buttonCustomMap.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					String level = "";
//					
//					level = SokobanUtilities.fileToString(file);
//					if(frame.getContentPane() == null) return;
//
//					// �ӽ÷� ����.
//			    	frame.getContentPane().removeAll();
//					Panel_Board board = new Panel_Board(frame, level);
//					frame.add(board);
//		    		frame.setSize(1600, 900);
//		            frame.setLocationRelativeTo(null);
//		            board.requestFocusInWindow();
//		            revalidate();
//		            repaint();
//				}
//				
//			});
//			panelCenter.add(buttonCustomMap);
//		}
//		
		this.add(panelCenter, BorderLayout.CENTER);

		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton buttonBack = new JButton("Back to Main Menu (esc)");
		buttonBack.setFont(new Font("���� ���", Font.PLAIN, 20));
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
