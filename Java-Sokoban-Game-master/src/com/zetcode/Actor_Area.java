package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Actor_Area extends Actor {

    public Actor_Area(int x, int y) {
        super(x, y);
        
        initArea();
    }
    
    private void initArea() {

        ImageIcon iicon = new ImageIcon("src/resources/environment_02.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
