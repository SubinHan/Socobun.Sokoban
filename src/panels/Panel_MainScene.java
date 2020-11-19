package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Panel_MainScene extends JPanel{
	
	private static final long serialVersionUID = 4962034188446655988L;
	Frame_Sokoban frame = null;
	JButton buttonStartNormal;
	JButton buttonStartEndless;
	JButton buttonCustom;
	JButton buttonHighscore;
	JButton buttonLogin;
	
	public Panel_MainScene() {
		
		frame = Frame_Sokoban.getInstance();
		initUI();
		
	}
	
	private void initUI() { // ´ëÃæ ½á³ùÀ¸¿ä
		
		setLayout(new BorderLayout());
        
        JLabel title = new JLabel("SOKOBAN");
        title.setPreferredSize(new Dimension(0, 250));
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);

		JPanel panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(400, 0));
		this.add(panelWest, BorderLayout.WEST);
		JPanel panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(400, 0));
		this.add(panelEast, BorderLayout.EAST);

		JPanel panelCenter = new JPanel(new GridLayout(5, 1));
		buttonStartNormal = new JButton("Start Game");
		buttonStartNormal.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonStartNormal.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PanelChanger.changePanel(Panel_MainScene.this, new Panel_Normal());
			}
		});
		
		
		buttonStartEndless = new JButton("Start Endless");
		buttonStartEndless.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonStartEndless.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PanelChanger.changePanel(Panel_MainScene.this, null);
			}
		});
		buttonCustom = new JButton("Custom Mode");
		buttonCustom.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonCustom.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PanelChanger.changePanel(Panel_MainScene.this, new Panel_Custom());
			}
		});
		
		buttonHighscore = new JButton("Highscores");
		buttonHighscore.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		buttonHighscore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PanelChanger.changePanel(Panel_MainScene.this, new Panel_Ranking());
			}
		});
	
		panelCenter.add(buttonStartNormal);
		panelCenter.add(buttonStartEndless);
		panelCenter.add(buttonCustom);
		panelCenter.add(buttonHighscore);
		
		this.add(panelCenter, BorderLayout.CENTER);

		JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.add(panelSouth, BorderLayout.SOUTH);
        setFocusable(true);
	}
	
}
