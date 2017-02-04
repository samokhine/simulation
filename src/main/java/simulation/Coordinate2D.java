package simulation;

public class Coordinate2D {
	private int x, y;
	
	public Coordinate2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate2D shift(Coordinate2D offset) {
		return new Coordinate2D(getX()+offset.getX(), getY()+offset.getY());
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return getX()+", "+getY();
	}
}
