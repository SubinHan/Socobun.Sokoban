package com.zetcode;

// import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zetcode.Login.Panel_Login;
import com.zetcode.Login.Panel_SignUp;

public class Frame_Sokoban extends JFrame {

	private static final long serialVersionUID = 9173696879006592846L;
	
	private final int MAX_RESOLUTION_WIDTH = SokobanUtilities.MAX_RESOLUTION_WIDTH;
	private final int MAX_RESOLUTION_HEIGHT = SokobanUtilities.MAX_RESOLUTION_HEIGHT;
	
	private final int PANEL_MAIN = SokobanUtilities.PANEL_MAIN;
	private final int PANEL_NORMAL = SokobanUtilities.PANEL_NORMAL;
	private final int PANEL_ENDLESS = SokobanUtilities.PANEL_ENDLESS;
	private final int PANEL_CUSTOM = SokobanUtilities.PANEL_CUSTOM;
	private final int PANEL_RANKING = SokobanUtilities.PANEL_RANKING;
	private final int PANEL_CUSTOMTOOL = SokobanUtilities.PANEL_CUSTOMTOOL;
	private final int PANEL_LEVELLIST = SokobanUtilities.PANEL_LEVELLIST;
	private final int PANEL_LOGIN = SokobanUtilities.PANEL_LOGIN;
	private final int PANEL_SIGNUP = SokobanUtilities.PANEL_SIGNUP;
	
	Panel_MainScene mainScene = null; // 0 : 메인
	Panel_Board board_normal = null; // 1 : 일반모드
	Panel_Endless board_endless = null; // 2 : 무한모드
	Panel_Custom board_custom = null; // 3 : 커스텀
	Panel_Ranking rankingScene = null; // 4 : 랭킹
	Panel_CustomTool board_customTool = null; // 5 커스텀 툴
	Panel_LevelList board_levelList = null;
	Panel_Login loginScene = null;
	Panel_SignUp signupScene = null;
	
	
    public Frame_Sokoban() {

        initUI();
    }

    private void initUI() {
        
    	mainScene = new Panel_MainScene(this);
    	board_normal = new Panel_Board(this);
    	board_custom = new Panel_Custom(this);
    	board_customTool = new Panel_CustomTool(this);
    	board_levelList = new Panel_LevelList(this);
    	loginScene = new Panel_Login(this);
    	signupScene = new Panel_SignUp(this);
    	
        add(board_levelList);
        setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT);
        setTitle("Sokoban");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setResizable(false);
        setExtendedState(JFrame.NORMAL);
        
    }
    
    public void changePanel(int panelCode) {
    	
    	if(getContentPane() == null) return;

    	getContentPane().removeAll();
    	
    	switch (panelCode) {
    	
	    	case PANEL_MAIN :
	    		add(mainScene);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	            mainScene.requestFocusInWindow();
	    		break;
	    		
	    	case PANEL_NORMAL : 
	    		add(board_normal);
	            setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT);
	            setLocationRelativeTo(null);
	            board_normal.requestFocusInWindow();
	        	break;

	    	case PANEL_ENDLESS :
	    		add(board_endless);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	    		break;
	    		
	    	case PANEL_CUSTOM :
	    		add(board_custom);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	            board_custom.requestFocusInWindow();
	    		break;
	    		
	    	case PANEL_RANKING :
	    		add(rankingScene);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	            rankingScene.requestFocusInWindow();
	    		break;
	    		
	    	case PANEL_CUSTOMTOOL :
	    		add(board_customTool);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	            board_customTool.requestFocusInWindow();
	    		break;
	    		
	    	case PANEL_LEVELLIST :
	    		add(board_levelList);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	            board_customTool.requestFocusInWindow();
	    		break;

	    	case PANEL_LOGIN :
	    		add(loginScene);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	            board_customTool.requestFocusInWindow();
	    		break;
	    		
	    	case PANEL_SIGNUP :
	    		add(signupScene);
	    		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
	            setLocationRelativeTo(null); // 중앙으로
	            board_customTool.requestFocusInWindow();
	            
	    	default :
	    		break;
	    		
    	}
    	revalidate();
		repaint();
    }
    
    public void changePanel(JPanel panel) {
    	if(getContentPane() == null) return;

    	getContentPane().removeAll();
    	add(panel);
		setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT); // 사이즈
        setLocationRelativeTo(null); // 중앙으로
        panel.requestFocusInWindow();
        revalidate();
        repaint();
    }
}