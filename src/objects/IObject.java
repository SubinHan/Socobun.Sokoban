package objects;

import java.awt.Image;

public interface IObject {
	public int getX();
	public int getY();
	public Image getImage();
	public void setImage(Image img);
	public String getType();
}
