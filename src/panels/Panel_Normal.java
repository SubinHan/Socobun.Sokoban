package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Level;

public class Panel_Normal extends JPanel implements ILevelSelectorListener {
	
	private static final long serialVersionUID = -2066833025416219742L;
	
	private Frame_Sokoban frame = null;
	private final String stagePath = "src/stages";
	private Level selectedLevel;
	
	private JPanel panelEast;

	public Panel_Normal() {

		frame = Frame_Sokoban.getInstance();
		this.addKeyListener(new KeyListener());
		initUI();

	}

	private void initUI() {

		setLayout(new BorderLayout());
		
		addKeyListener(new KeyListener());

		JLabel title = new JLabel("Select Level");
		title.setPreferredSize(new Dimension(0,250));
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(400, 0));
		this.add(panelWest, BorderLayout.WEST);
		
		panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(400, 0));
		
		this.add(panelEast, BorderLayout.EAST);

		JPanel panelCenter = new Panel_LevelSelector(this, stagePath);
		this.add(panelCenter);

		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton buttonBack = new JButton("Back to Main Menu (esc)");
		JButton buttonPlay = new JButton("Play");
		buttonBack.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonPlay.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PanelChanger.changePanel(Panel_Normal.this, new Panel_MainScene());
			}
		});
		buttonPlay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(selectedLevel == null) {
					;
				}
				else
					PanelChanger.changePanel(Panel_Normal.this, new Panel_GameOuter(selectedLevel));
			}
		});
		panelSouth.add(buttonPlay);
		panelSouth.add(buttonBack);
		this.add(panelSouth, BorderLayout.SOUTH);

	}

	private class KeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {

			case KeyEvent.VK_ESCAPE:
				PanelChanger.changePanel(Panel_Normal.this, new Panel_MainScene());

			default:
				break;
			}

			repaint();
		}
	}

	@Override
	public void levelSelected(Level level) {
		selectedLevel = level;
		panelEast.removeAll();
		panelEast.add(new Panel_LevelInfo(frame, selectedLevel));
		revalidate();
		repaint();
	}
}
