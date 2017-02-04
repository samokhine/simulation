package simulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	static public List<String> readFile(File file) {
		List<String> lines = new ArrayList<>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		    String line;
		    while((line = br.readLine()) != null) {
		    	line = line.trim();

		    	lines.add(line);
		    }
		} catch (IOException e) {
			e.printStackTrace();
	    	throw new RuntimeException("Cannot parse "+file);
		} finally {
		    if(br != null) {
		    	try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
			    	throw new RuntimeException("Cannot close "+file);
				}
		    }
		}
		
		return lines;
	}
	
	static public char distanceToChar(int distance) {
		if(distance<=0 || distance>52) {
			throw new RuntimeException(distance+" is an invalid distance to convert to character");
		} else {
			if(distance>0 && distance<=26) {
				return (char) (96 + distance); // [a...z]
			} else {
				return (char) (64 + distance - 26); // [A...Z]
			}
		}
	}
	
	static public int charToDistance(char ch) {
		if(ch < 65 || ch > 90 && ch < 97 || ch > 122) { // // not in [A...Z] or [a...z] ranges
			throw new RuntimeException(ch+" is an invalid character to convert to distance");
		}
	
		if(ch >= 65 && ch <= 90) { // [A...Z]
			return ch - 65 + 27;
		} else { // [a...z]
			return ch - 96;
		}
	}
}
