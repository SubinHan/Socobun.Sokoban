package com.zetcode;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Panel_LevelSelector extends JPanel {

	private Frame_Sokoban frame;
	private ArrayList listeners;
	private String directory;
	private String selectedLevel;
	private File[] files;
	private JScrollPane panelScroll;
	private JPanel panelGrid;
	
	public Panel_LevelSelector(Frame_Sokoban frame, ILevelSelectorListener listener, String directory) {
		super();
		this.frame = frame;
		addLevelSelectorListener(listener);
		files = FileSearcher.getFiles(directory);
		panelGrid = new JPanel();
		panelGrid.setLayout(new GridLayout(files.length, 1));
		for(int i = 0; i < files.length; i++) {
			JButton button = new JButton(files[i].getName());
			File file = files[i];
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fireLevelSelected(SokobanUtilities.fileToCharArray(file));
				}
				
			});
			button.setPreferredSize(new Dimension(400, 100));
			panelGrid.add(button);
		}
//		for(int i = 0; i < 11; i++) {
//			JButton button = new JButton();
//			button.setPreferredSize(new Dimension(400, 100));
//			panelFlow.add(button);
//		}
		
		panelScroll = new JScrollPane(panelGrid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		this.setLayout(new FlowLayout());
		panelScroll.setPreferredSize(new Dimension(600, 600));
		this.add(panelScroll);
	}

	private void addLevelSelectorListener(ILevelSelectorListener listener) {
		if(listeners == null)
			listeners = new ArrayList();
		listeners.add(listener);
	}
	
	private void fireLevelSelected(char[][] level) {
		Iterator iter = listeners.iterator();
		while(iter.hasNext()) {
			ILevelSelectorListener listener = (ILevelSelectorListener)iter.next();
			listener.levelSelected(level);
		}
	}
	
	public void refresh() {
		panelGrid.removeAll();
		panelScroll.removeAll();
		this.removeAll();
		files = FileSearcher.getFiles(directory);
		panelGrid = new JPanel();
		panelGrid.setLayout(new GridLayout(files.length, 1));
		for(int i = 0; i < files.length; i++) {
			JButton button = new JButton(files[i].getName());
			File file = files[i];
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fireLevelSelected(SokobanUtilities.fileToCharArray(file));
				}
				
			});
			button.setPreferredSize(new Dimension(400, 100));
			panelGrid.add(button);
		}
//		for(int i = 0; i < 11; i++) {
//			JButton button = new JButton();
//			button.setPreferredSize(new Dimension(400, 100));
//			panelFlow.add(button);
//		}
		
		panelScroll = new JScrollPane(panelGrid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		this.setLayout(new FlowLayout());
		panelScroll.setPreferredSize(new Dimension(600, 600));
		this.add(panelScroll);
		revalidate();
		repaint();
	}

}
