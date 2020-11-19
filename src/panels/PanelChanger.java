package panels;

import java.awt.BorderLayout;
import java.util.Stack;

import javax.swing.JPanel;

import utils.SokobanUtilities;

public class PanelChanger {
	
	private static Frame_Sokoban frame = Frame_Sokoban.getInstance();
	private static Stack<JPanel> prePanelStack = new Stack<>();
	
	public static void changePanel(JPanel prePanel, JPanel panel) {

    	if(frame.getContentPane() == null) return;

    	prePanelStack.push(prePanel);
    	
    	frame.getContentPane().removeAll();
    	reAddUserInfo();
    	frame.add(panel);
		frame.setSize(SokobanUtilities.MAX_RESOLUTION_WIDTH, SokobanUtilities.MAX_RESOLUTION_HEIGHT); // 사이즈
        frame.setLocationRelativeTo(null); // 중앙으로
        panel.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
		
	}

	public static void backToPreviousPanel() {
		
		if(prePanelStack.isEmpty()) return;
		if(frame.getContentPane() == null) return;
		
		JPanel previousPanel = prePanelStack.pop();

    	frame.getContentPane().removeAll();
    	reAddUserInfo();
    	frame.add(previousPanel);
		frame.setSize(SokobanUtilities.MAX_RESOLUTION_WIDTH, SokobanUtilities.MAX_RESOLUTION_HEIGHT); // 사이즈
        frame.setLocationRelativeTo(null); // 중앙으로
        previousPanel.requestFocusInWindow();
        frame.revalidate();
        frame.repaint();
		
	}
	
	public static void reAddUserInfo() {
        frame.add(new Panel_UserInfo(), BorderLayout.NORTH);
	}
	
}
