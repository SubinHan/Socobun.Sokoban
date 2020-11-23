package objects;

public class UndoSnapshot {

	private int deltaX;
	private int deltaY;
	public Actor_Baggage bag;
	
	public UndoSnapshot(int dX, int dY, Actor_Baggage b) {
		
		this.deltaX = dX;
		this.deltaY = dY;
		bag = b;
		
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}
}
