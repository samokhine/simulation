package simulation;

public class Coordinate3D extends Coordinate2D {
	private int z;
	
	public Coordinate3D(Coordinate3D coordinate) {
		this(coordinate.getX(), coordinate.getY(), coordinate.getZ());
	}

	public Coordinate3D(Coordinate2D coordinate, int z) {
		this(coordinate.getX(), coordinate.getY(), z);
	}

	
	public Coordinate3D(int x, int y, int z) {
		super(x, y);
		this.z = z;
	}
	
	@Override
	public Coordinate3D shift(Coordinate2D offset) {
		return new Coordinate3D(super.shift(offset), getZ());
	}
	
	public int getZ() {
		return z;
	}

	@Override
	public String toString() {
		return super.toString()+", "+getZ();
	}
}
