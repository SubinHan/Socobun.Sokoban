package com.zetcode;

import java.awt.Dimension;

// import java.awt.Color;
import javax.swing.JFrame;

public class Frame_Sokoban extends JFrame {

	private static final long serialVersionUID = 9173696879006592846L;
	
	Panel_MainScene mainScene = null; // 0 : ����
	Panel_Board board_normal = null; // 1 : �Ϲݸ��
	Panel_Endless board_endless = null; // 2 : ���Ѹ��
	Panel_Custom board_custom = null; // 3 : Ŀ����
	Panel_Ranking rankingScene = null; // 4 : ��ŷ
	Panel_CustomTool board_customTool = null; // 5 Ŀ���� ��
	
    public Frame_Sokoban() {

        initUI();
    }

    private void initUI() {
        
    	mainScene = new Panel_MainScene(this);
    	board_normal = new Panel_Board(this);
    	board_custom = new Panel_Custom(this);
    	board_customTool = new Panel_CustomTool(this);
        add(board_custom);
        setSize(1600, 900);
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
    	
	    	case 0 :
	    		add(mainScene);
	    		setSize(1000, 700); // ������
	            setLocationRelativeTo(null); // �߾�����
	            mainScene.requestFocusInWindow();
	    		break;
	    		
	    	case 1 : 
	    		add(board_normal);
	            setSize(new Dimension(board_normal.getBoardWidth() + 16, board_normal.getBoardHeight() + 39));
	            setLocationRelativeTo(null);
	            board_normal.requestFocusInWindow();
	        	break;

	    	case 2 :
	    		add(board_endless);
	    		setSize(1000, 700); // ������
	            setLocationRelativeTo(null); // �߾�����
	    		break;
	    		
	    	case 3 :
	    		add(board_custom);
	    		setSize(1600, 900); // ������
	            setLocationRelativeTo(null); // �߾�����
	            board_custom.requestFocusInWindow();
	    		break;
	    		
	    	case 4 :
	    		add(rankingScene);
	    		setSize(1000, 700); // ������
	            setLocationRelativeTo(null); // �߾�����
	            rankingScene.requestFocusInWindow();
	    		break;
	    		
	    	case 5 :
	    		add(board_customTool);
	    		setSize(1600, 900); // ������
	            setLocationRelativeTo(null); // �߾�����
	            board_customTool.requestFocusInWindow();
	    		break;
	        	
	    	default :
	    		break;
	    		
    	}
    	revalidate();
		repaint();
    }
}