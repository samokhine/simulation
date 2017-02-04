package simulation;

import java.io.File;
import java.util.List;

/*
 * The class executes the give script by applying instructions to a vessel in a given space.
 * It gets constructed from a script file.
 * Unknown instructions in the script file are ignored.
 * It counts a score, determines pass/fail and does logging.
 */
public class Strategy {
	private static final String SEPARATOR = " ";
	
	private int currentStep;
	private List<String> steps;
	
	private boolean fail;
	private int score;
	
	private Logger logger = new Logger();
	
	public Strategy(File file) {
		steps = cleanUp(Utils.readFile(file));
		reset();
	}
	
	public void run(Space space) {
		// create vessel
		Vessel vessel = new Vessel(space, space.getCenter());
		
		// set initial score
		setScore(10 * space.getNumberOfMines());

		// loop through script
		int step = 0;
		while(hasNextStep()) { 
			String fieldBefore = space.getFieldMap(vessel.getPosition());
			
			String instruction = getNextStep();
			processStep(instruction, vessel, space);
			
			String fieldAfter = space.getFieldMap(vessel.getPosition());
			logger.log(++step, fieldBefore, instruction, fieldAfter);
			
			if(space.isHasActiveMineAtZ(vessel.getPosition().getZ())) { // if pass a mine
				setFail(true); // fail
				break; // abort
			} else if(space.getNumberOfActiveMines()<=0) { // or all mines cleared
				if(hasNextStep()) { // but step(s) left
					setScore(1); // pass but with just 1 point
				}
				break; // abort
			}
		}
		
		if(space.getNumberOfActiveMines()>0) { // if done with the script but at least one active mine left
			setFail(true); // fail
		}
		
		logger.log(fail, score);
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	private void processStep(String step, Vessel vessel, Space space) {
		for(String command : step.split(SEPARATOR)) {
			switch(Vessel.getCommandType(command)) {
				case FIRE:
					vessel.fire(command);
					adjustScore(-5, 5 * space.getNumberOfMines());
					break;
					
				case MOVE:
					vessel.move(command);
					adjustScore(-2, 3 * space.getNumberOfMines());
					break;
					
				default:
					// do nothing
			}
		}
		// drop down 1 km
		vessel.drop();
	}

	private void setFail(boolean fail) {
		this.fail = fail;
		score = 0;
	}

	private void setScore(int score) {
		this.score = score;
	}

	private void adjustScore(int adjustment, int allowedFloor) {
		this.score = Math.max(score + adjustment, allowedFloor);
	}

	private void reset() {
		currentStep = 0;
		fail = false;
		score = 0;
	}
	
	private String getNextStep() {
		if(hasNextStep()) {
			return steps.get(currentStep++);
		} else {
			return null;
		}
	}
	
	private boolean hasNextStep() { 
		return currentStep <= steps.size()-1;
	}
	
	private List<String> cleanUp(List<String> steps) {
		// make sure that there is only one separator separates commands (if any)
		String doubleSeparator = SEPARATOR + SEPARATOR;
		for(int i=0; i<steps.size(); i++) {
			String step = steps.get(i);
			while(step.indexOf(doubleSeparator)>0) {
				step = step.replaceAll(doubleSeparator, SEPARATOR);
			}
			steps.set(i, step);
		}
		return steps;
	}
}
