package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Sokoban extends JFrame {

	private static final long serialVersionUID = 9173696879006592846L;
	
	private final int OFFSET = 20;

    public Sokoban() {

        initUI();
    }

    private void initUI() {
    	
        Board board = new Board(8, 8, 1515, (int)(Math.random()*521653));
        add(board);

        setTitle("Sokoban");
        
        setSize(board.getBoardWidth() + OFFSET - 4,
                board.getBoardHeight() + 4 * OFFSET - 11);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            
            Sokoban game = new Sokoban();
            game.setVisible(true);
        });
    }
}
