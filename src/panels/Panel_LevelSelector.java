package panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import objects.Level;
import utils.IFileSearcher;
import utils.LevelFileSearcher;

public class Panel_LevelSelector extends JPanel {

	private Frame_Sokoban frame;
	private ArrayList listeners;
	private String directory;
	private String selectedLevel;
	private File[] files;
	private JScrollPane panelScroll;
	private JPanel panelGrid;
	
	public Panel_LevelSelector(ILevelSelectorListener listener, String directory) {
		super();
		this.directory = directory;
		this.frame = Frame_Sokoban.getInstance();
		addLevelSelectorListener(listener);
		
		setFileSelector();
	}

	private void addLevelSelectorListener(ILevelSelectorListener listener) {
		if(listeners == null)
			listeners = new ArrayList();
		listeners.add(listener);
	}
	
	private void fireLevelSelected(Level level) {
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
		
		revalidate();
		repaint();
	}
	
	private void setFileSelector() {
		
		IFileSearcher fileSearcher = new LevelFileSearcher();
		files = fileSearcher.getFiles(directory);
		panelGrid = new JPanel();
		panelGrid.setLayout(new GridLayout(files.length, 1));
		for(int i = 0; i < files.length; i++) {
			JButton button = new JButton(files[i].getName());
			File file = files[i];
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						fireLevelSelected(new Level(file));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			button.setPreferredSize(new Dimension(570, 100));
			panelGrid.add(button);
		}
		
		panelScroll = new JScrollPane(panelGrid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelScroll.getVerticalScrollBar().setUnitIncrement(15);
		panelScroll.setBorder(null);
		panelScroll.setPreferredSize(new Dimension(580, 350));
		this.add(panelScroll);
		
	}

}
