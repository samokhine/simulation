package simulation;

import java.util.HashMap;
import java.util.Map;

/*
 * The class represents the mine clearing vessel that lives in space and has a position.
 * It can move 1km in given direction, fire torpedos in a pattern or drop down 1km in the space.
 * It fires a runtime exception if direction or pattern is not known.
 * It can move outside of the space (my assumption). 
 */
public class Vessel {
	private static final Map<String, Coordinate2D[]> FIRE_OFFSETS = new HashMap<>();

	private static final Map<String, Coordinate2D> MOVE_OFFSETS = new HashMap<>();

	static {
		FIRE_OFFSETS.put("alpha", new Coordinate2D[] { new Coordinate2D(-1, -1), new Coordinate2D(-1, 1), new Coordinate2D(1, -1), new Coordinate2D(1, 1) });
		FIRE_OFFSETS.put("beta", new Coordinate2D[] { new Coordinate2D(-1, 0), new Coordinate2D(0, -1), new Coordinate2D(0, 1), new Coordinate2D(1, 0) });
		FIRE_OFFSETS.put("gamma", new Coordinate2D[] { new Coordinate2D(-1, 0), new Coordinate2D(0, 0), new Coordinate2D(1, 0) });
		FIRE_OFFSETS.put("delta", new Coordinate2D[] { new Coordinate2D(0, -1), new Coordinate2D(0, 0), new Coordinate2D(0, 1) });
	
		MOVE_OFFSETS.put("north", new Coordinate2D(0, 1));
		MOVE_OFFSETS.put("east", new Coordinate2D(1, 0));
		MOVE_OFFSETS.put("south", new Coordinate2D(0, -1));
		MOVE_OFFSETS.put("west", new Coordinate2D(-1, 0));
	}
	
	public static CommandType getCommandType(String command) {
		if(MOVE_OFFSETS.containsKey(command)) {
			return CommandType.MOVE;
		} else if(FIRE_OFFSETS.containsKey(command)) {
			return CommandType.FIRE;
		} else {
			return CommandType.UNKNOWN;
		}
	}
	
	private Space space;
	private Coordinate3D position;
	
	public Vessel(Space space, Coordinate3D position) {
		setSpace(space);
		setPosition(position);
	}
	
	public Coordinate3D getPosition() {
		return position;
	}

	public void setPosition(Coordinate3D position) {
		this.position = position;
	}
	
	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public void drop() {
		setPosition(new Coordinate3D(getPosition().getX(), getPosition().getY(), getPosition().getZ()-1));
	}

	public void move(String direction) {
		Coordinate2D offset = MOVE_OFFSETS.get(direction);
		if(offset == null) throw new RuntimeException(direction + " is unknown direction to move");
		setPosition(getPosition().shift(offset));
	}

	public void fire(String pattern) {
		Coordinate2D[] offsets = FIRE_OFFSETS.get(pattern);
		if(offsets == null) throw new RuntimeException(pattern + " is unknown pattern to fire");

		for(Coordinate2D offset : offsets) {
			space.applyTorpedo(getPosition().shift(offset));
		}
	}
	
	@Override
	public String toString() {
		return getPosition().toString();
	}
}
