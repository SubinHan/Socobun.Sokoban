package objects;

import java.awt.Image;

public abstract class Actor implements Cloneable, IObject{

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

    public int getX() {
        
        return x;
    }

    public int getY() {
        
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
