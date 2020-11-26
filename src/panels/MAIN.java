package panels;
import java.awt.EventQueue;

import manageUser.FirebaseClass;

public class MAIN {

	
    public static void main(String[] args) {

    	FirebaseClass.init();
    	
        EventQueue.invokeLater(() -> {
            Frame_Sokoban game = Frame_Sokoban.getInstance();
            
            game.add(new Panel_Login());
            game.setVisible(true);
        });
    }
}