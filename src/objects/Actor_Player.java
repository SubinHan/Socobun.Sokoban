package objects;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Actor_Player extends Actor implements IPlayer {
	
	ImageIcon upImage, downImage, leftImage, rightImage;

    public Actor_Player(int x, int y) {
        super(x, y);
        type = "player";
        initPlayer();
    }

    /*
     *	플레이어 이미지는  Normal 1개, Walking 2개 해서 3개의 이미지가 상하좌우로 총 12개 있음.
     *	05~07: down, 08~10: up, 17~19: right, 20~22: left.
     */
    private void initPlayer() {

    	upImage = new ImageIcon("src/resources/player_08.png");
    	downImage = new ImageIcon("src/resources/player_05.png");
    	leftImage = new ImageIcon("src/resources/player_20.png");
    	rightImage = new ImageIcon("src/resources/player_17.png");

        Image image = downImage.getImage();
        setImage(image.getScaledInstance(64, 64, Image.SCALE_DEFAULT));
    }

    public void move(int x, int y) {

    	if(y < 0)
    		setImage(upImage.getImage());
    	else if(y > 0)
    		setImage(downImage.getImage());
    	else if(x < 0)
    		setImage(leftImage.getImage());
    	else if(x > 0)
    		setImage(rightImage.getImage());
    	
        int dx = getX() + x;
        int dy = getY() + y;
        
        setX(dx);
        setY(dy);
    }

    public void undo(int x, int y) {

    	if(y < 0)
    		setImage(downImage.getImage());
    	else if(y > 0)
    		setImage(upImage.getImage());
    	else if(x < 0)
    		setImage(rightImage.getImage());
    	else if(x > 0)
    		setImage(leftImage.getImage());
    	
        int dx = getX() + x;
        int dy = getY() + y;
        
        setX(dx);
        setY(dy);
    }
    
}
