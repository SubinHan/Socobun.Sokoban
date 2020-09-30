package com.zetcode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Actor_Ground extends Actor {

	public Actor_Ground(int x, int y) {
		super(x, y);

		initGround();
	}
	
	private void initGround() {
		ImageIcon iicon = new ImageIcon("src/resources/ground_06.png");
        Image image = iicon.getImage();
        setImage(image);
	}
	
}
