package simulation;

/*
 * The class represents a mine that can live in a Space.
 * It knows it's vertical position (z) and status (active/not active)
 */
public class Mine {
	private boolean active = true;
	private int z;

	public Mine(int z) {
		this.z = z;
	}

	public boolean isActive() {
		return active;
	}

	public void deactivate() {
		active = false;
	}
	
	public int getZ() {
		return z;
	}
	
	@Override
	public String toString() {
		return ""+getZ()+" "+isActive();
	}
}
