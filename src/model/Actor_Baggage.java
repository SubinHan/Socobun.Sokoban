package model;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Actor_Baggage extends Actor {

    public Actor_Baggage(int x, int y) {
        super(x, y);
        type = "baggage";
        initBaggage();
    }
    
    private void initBaggage() {
        
        ImageIcon iicon = new ImageIcon("src/resources/crate_02.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {
        
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}
