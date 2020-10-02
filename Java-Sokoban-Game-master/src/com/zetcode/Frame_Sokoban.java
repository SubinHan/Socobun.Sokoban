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
	
    public Frame_Sokoban() {

        initUI();
    }

    private void initUI() {
        
    	mainScene = new Panel_MainScene(this);
    	board_normal = new Panel_Board(this);
        add(mainScene);
        setSize(1000,700);
        setTitle("Sokoban");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(false);
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
	    		break;
	    		
	    	case 1 : 
	    		add(board_normal);
	            setSize(new Dimension(board_normal.getBoardWidth() + 16, board_normal.getBoardHeight() + 39));
	            setLocationRelativeTo(null);
	        	break;

	    	case 2 :
	    		add(board_endless);
	    		setSize(1000, 700); // ������
	            setLocationRelativeTo(null); // �߾�����
	    		break;
	    		
	    	case 3 :
	    		add(board_custom);
	    		setSize(1000, 700); // ������
	            setLocationRelativeTo(null); // �߾�����
	    		break;
	    		
	    	case 4 :
	    		add(rankingScene);
	    		setSize(1000, 700); // ������
	            setLocationRelativeTo(null); // �߾�����
	    		break;
	        	
	    	default :
	    		break;
	    		
    	}
    	revalidate();
		repaint();
    	
		
    }
}