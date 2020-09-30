package com.zetcode;

// import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Sokoban extends JFrame {

	private static final long serialVersionUID = 9173696879006592846L;
	
	private final int OFFSET = 64;

    public Sokoban() {

        initUI();
    }

    private void initUI() {
        
        Panel_Board board = new Panel_Board();
        add(board);

        setTitle("Sokoban");
        
        setSize(board.getBoardWidth() + OFFSET,
                board.getBoardHeight() + 2 * OFFSET);
        
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
