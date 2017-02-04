package simulation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class represents the space (cuboid).
 * It gets constructed from a field file that describes how mines are positioned in the space.
 * The construction fails if the field file has invalid characters or has lines of different length.
 * Torpedo can be fired into the space to deactivate the mines.
 */
public class Space {
	private Map<Integer, Map<Integer,Mine>> xyIndex;
	private Map<Integer, List<Mine>> zIndex;
	private Coordinate3D center;
	private Boundary activeMinesBoundary;
	
	private int numberOfMines, numberOfActiveMines;
	
	public Space(File file) {
		initiateFromFile(file);
	}
	
	public void applyTorpedo(Coordinate3D position) {
		Mine mine = getMineAtXY(position.getX(), position.getY());
		if(mine == null || mine.getZ() > position.getZ()) return;
		
		mine.deactivate();
		numberOfActiveMines--;
		recalculateActiveMinesBoundary();
	}
	
	private Mine getMineAtXY(int x, int y) {
		Map<Integer, Mine> yIndex = xyIndex.get(x);
		return yIndex == null ? null : yIndex.get(y);
	}
	
	public int getNumberOfMines() {
		return numberOfMines;
	}

	public int getNumberOfActiveMines() {
		return numberOfActiveMines;
	}

	public Coordinate3D getCenter() {
		return center;
	}
	
	public boolean isCleared() {
		return numberOfActiveMines<=0;
	}

	public String getFieldMap(Coordinate3D position) {
		StringBuilder fieldMap = new StringBuilder();
		
		Boundary fieldMapBoundary; 
		if(activeMinesBoundary == null) {
			fieldMapBoundary = new Boundary(position);
		} else {
			fieldMapBoundary = activeMinesBoundary.centerAt(position);
		}
		
		for(int y=fieldMapBoundary.getMaxY(); y>=fieldMapBoundary.getMinY(); y--) {
			for(int x=fieldMapBoundary.getMinX(); x<=fieldMapBoundary.getMaxX(); x++) {
				Mine mine = getMineAtXY(x, y);
				
				char ch;
				if(mine != null && mine.isActive()) {
					ch = mine.getZ() == position.getZ() ?
							'*' : // passed mine
							Utils.distanceToChar(position.getZ() - mine.getZ()); // down below mine
				} else {
					ch = '.';
				}
				fieldMap.append(ch);
			}
			if(y>fieldMapBoundary.getMinY()) {
				fieldMap.append('\n');
			}
		}
		
		return fieldMap.toString();
	}
	
	public boolean isHasActiveMineAtZ(int z) {
		List<Mine> minesAtZ = zIndex.get(z);
		if(minesAtZ == null) return false;
		
		for(Mine mine : minesAtZ) {
			if(mine.isActive()) return true;
		}
		
		return false;
	}
	
	private void recalculateActiveMinesBoundary() {
		activeMinesBoundary = null;
		if(isCleared()) return;
		
		for(int x : xyIndex.keySet()) {
			for(int y : xyIndex.get(x).keySet()) {
				Mine mine = xyIndex.get(x).get(y);
				if(!mine.isActive()) continue;
				
				Coordinate2D position = new Coordinate2D(x, y);
				if(activeMinesBoundary == null) {
					activeMinesBoundary = new Boundary(position);
				} else {
					activeMinesBoundary.extendTo(position);
				}
			}
		}
	}
	
	private Space initiateFromFile(File file) {
	    List<String> lines = Utils.readFile(file);

	    // validate
	    for(int i=1; i<lines.size()-1; i++) {
	    	if(lines.get(i-1).length() != lines.get(i).length()) {
		    	throw new RuntimeException("Abnormal length of line in field file");
	    	}
	    }
	    
	    int xDim = lines.size()>0 ? lines.get(0).length() : 0;
	    int yDim = -1*lines.size();
	    center = new Coordinate3D(xDim/2, yDim/2, 0);
	    
	    activeMinesBoundary = null;
	    
	    numberOfActiveMines = 0;

	    xyIndex = new HashMap<>();
	    zIndex = new HashMap<>();
	    
    	for(int x=0; x<xDim; x++) {
		    for(int y=0; y>yDim; y--) {
		    	String line = lines.get(-1*y);
	    		char ch =  line.charAt(x);
	    		if(ch == '.') continue;
	    		
	    		int z = -1 * Utils.charToDistance(ch);

	    		Mine mine = new Mine(z);

	    		List<Mine> minesAtZ = zIndex.get(z);
	    		if(minesAtZ == null) {
	    			minesAtZ = new ArrayList<>();
	    			zIndex.put(z, minesAtZ);
	    		}
	    		minesAtZ.add(mine);
	    		
	    		Coordinate2D coordinate = new Coordinate2D(x, y);
	    		if(activeMinesBoundary == null) {
	    			activeMinesBoundary = new Boundary(coordinate);
	    		} else {
	    			activeMinesBoundary.extendTo(coordinate);
	    		}
	    		
	    		Map<Integer, Mine> yIndex = xyIndex.get(x);
	    		if(yIndex == null) {
	    			yIndex = new HashMap<>();
	    			xyIndex.put(x, yIndex);
	    		}
	    		yIndex.put(y, mine);
	    		
	    		numberOfActiveMines++;
	    	}
	    }
    	
    	numberOfMines = numberOfActiveMines;
    	
    	return this;
	}
}
