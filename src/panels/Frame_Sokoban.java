package panels;

// import java.awt.Color;
import javax.swing.JFrame;

import manageUser.UserInfo;
import utils.SokobanUtilities;

public class Frame_Sokoban extends JFrame {

	private static final long serialVersionUID = 9173696879006592846L;
	
	private final int MAX_RESOLUTION_WIDTH = SokobanUtilities.MAX_RESOLUTION_WIDTH;
	private final int MAX_RESOLUTION_HEIGHT = SokobanUtilities.MAX_RESOLUTION_HEIGHT;
	
	private UserInfo userinfo = null;
	
	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	private static Frame_Sokoban instance = null;
	
    private Frame_Sokoban() {

        initUI();
    }
    
    public static Frame_Sokoban getInstance() {
    	if(instance == null)
    		instance = new Frame_Sokoban();
    	return instance;
    }
    
    private void initUI() {
 
        setSize(MAX_RESOLUTION_WIDTH, MAX_RESOLUTION_HEIGHT);
        setTitle("Sokoban");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setResizable(false);
        setExtendedState(JFrame.NORMAL);
        
    }
    
}