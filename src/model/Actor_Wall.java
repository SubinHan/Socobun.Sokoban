package model;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Actor_Wall extends Actor {

    private Image image;

    public Actor_Wall(int x, int y) {
        super(x, y);
        type = "wall";
        initWall();
    }
    
    private void initWall() {
        
        ImageIcon iicon = new ImageIcon("src/resources/block_08.png");
        image = iicon.getImage();
        setImage(image);
    }
}
