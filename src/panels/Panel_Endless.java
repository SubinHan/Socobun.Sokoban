package panels;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel_Endless extends JPanel{
	
	private static final long serialVersionUID = 4962034188446655988L;
	Frame_Sokoban frame = null;
	
	public Panel_Endless(Frame_Sokoban f) {
		
		frame = f;
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
