package com.zetcode;
import java.awt.EventQueue;

import org.apache.log4j.PropertyConfigurator;

import com.zetcode.Login.FirebaseClass;

public class MAIN {

	
    public static void main(String[] args) {

    	FirebaseClass.init();
    	
        EventQueue.invokeLater(() -> {
            Frame_Sokoban game = new Frame_Sokoban();
            game.setVisible(true);
        });
    }
}