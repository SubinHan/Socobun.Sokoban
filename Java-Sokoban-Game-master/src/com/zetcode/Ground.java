package com.zetcode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Ground extends Actor {

	public Ground(int x, int y) {
		super(x, y);

		initGround();
	}
	
	private void initGround() {
		ImageIcon iicon = new ImageIcon("src/resources/ground_06.png");
        Image image = iicon.getImage();
        setImage(image);
	}
	
}
