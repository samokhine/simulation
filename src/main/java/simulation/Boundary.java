package simulation;

public class Boundary {
	private int minX, maxX, minY, maxY;

	public Boundary(Coordinate2D coordinate) {
		minX = coordinate.getX();
		maxX = coordinate.getX();
		minY = coordinate.getY();
		maxY = coordinate.getY();
	}

	public Boundary(int minX, int maxX, int minY, int maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public Boundary extendTo(Coordinate2D coordinate) {
		if(minX > coordinate.getX()) minX = coordinate.getX();
		if(maxX < coordinate.getX()) maxX = coordinate.getX();
		if(minY > coordinate.getY()) minY = coordinate.getY();
		if(maxY < coordinate.getY()) maxY = coordinate.getY();
		
		return this;
	}
	
	public Boundary centerAt(Coordinate2D coordinate) {
		int halfWidth = Math.max(Math.abs(coordinate.getX() - getMinX()), Math.abs(coordinate.getX() - getMaxX()));
		int halfHeight = Math.max(Math.abs(coordinate.getY() - getMinY()), Math.abs(coordinate.getY() - getMaxY()));
		
		return new Boundary(coordinate.getX() - halfWidth, coordinate.getX() + halfWidth, coordinate.getY() - halfHeight, coordinate.getY() + halfHeight);
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}
	
	public int getWidth() {
		return maxX - minX;
	}

	public int getHeight() {
		return maxY - minY;
	}
	
	@Override
	public String toString() {
		return "minX="+minX+" maxX="+maxX+" minY="+minY+" maxY="+maxY;
	}
}
