package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.UserInfo;

public class Panel_UserInfo extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public UserInfo userinfo;
	private Frame_Sokoban frame = null;
	
	public Panel_UserInfo() {
		
		frame = Frame_Sokoban.getInstance();
		initUI();
		
	}
	
	private void initUI() {
		
		this.setPreferredSize(new Dimension(0, 60));
		setLayout(new BorderLayout());
		
		JLabel nickname = new JLabel("ss");
		nickname.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(nickname, BorderLayout.CENTER);
		
	}

}