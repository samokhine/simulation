package simulation;

import java.io.File;

public class App {
	public static void main(String[] args) {
		if(args.length<2) {
			System.out.println("The program expects 2 arguments: 1) path to field file 2) path to script file");
			return;
		}
		
        File fieldFile = new File(args[0]);
		Space space = new Space(fieldFile);

        File instructionsFile = new File(args[1]);
        Strategy strategy = new Strategy(instructionsFile);
        
        strategy.run(space);
	}
}
