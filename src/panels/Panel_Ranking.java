package panels;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Panel_Ranking extends JPanel{
	
	private static final long serialVersionUID = 4962034188446655988L;
	Frame_Sokoban frame = null;
	
	public Panel_Ranking() {

		frame = Frame_Sokoban.getInstance();
		initUI();
		
	}
	
	private void initUI() { // ���� �������
		
		setLayout(null);
		JLabel title = new JLabel("Ranking");
		title.setBounds(450, 100, 100, 100);
		title.setFont(new Font("���� ����", Font.BOLD, 40));
		add(title);
        setFocusable(true);
		
	}
}