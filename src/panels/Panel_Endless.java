package panels;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_Endless extends JPanel{
	
	Frame_Sokoban frame = null;
	
	public Panel_Endless() {
		
		frame = Frame_Sokoban.getInstance();
		initUI();
		
	}
	
	private void initUI() { // ´ëÃæ ½á³ùÀ¸¿ä
		
		setLayout(null);
		JLabel title = new JLabel("EndlessMode");
		title.setBounds(450, 100, 100, 100);
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		add(title);
        setFocusable(true);
		
	}
	
}
