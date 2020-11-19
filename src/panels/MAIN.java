package panels;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import login.FirebaseClass;

public class MAIN {

	
    public static void main(String[] args) {

    	FirebaseClass.init();
    	
        EventQueue.invokeLater(() -> {
            Frame_Sokoban game = Frame_Sokoban.getInstance();
            game.setLayout(new BorderLayout());
            
            game.add(new Panel_Login());
            game.add(new Panel_UserInfo(), BorderLayout.NORTH);
            game.setVisible(true);
        });
    }
}