package model;

import java.awt.Image;

public class Actor implements Cloneable{

    private final int SPACE = 64;

    protected String type;
    private int x;
    private int y;
    private Image image;

    public Actor(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        
        return x;
    }

    public int y() {
        
        return y;
    }

    public void setX(int x) {
        
        this.x = x;
    }

    public void setY(int y) {
        
        this.y = y;
    }

    public String getType() {
    	return type;
    }
    
    public Actor clone() {
    	Actor vo = null;
		try {
			vo = (Actor) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return vo;	
    }
}
