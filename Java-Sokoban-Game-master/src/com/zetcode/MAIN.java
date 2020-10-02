package com.zetcode;
import java.awt.EventQueue;

public class MAIN {

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            Frame_Sokoban game = new Frame_Sokoban();
            game.setVisible(true);
        });
    }
}