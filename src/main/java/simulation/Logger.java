package simulation;

public class Logger {
	public void log(int step, String fieldBefore, String command, String fieldAfter) {
		System.out.println("Step "+step);
		System.out.println("");
		System.out.println(fieldBefore);
		System.out.println("");
		System.out.println(command);
		System.out.println("");
		System.out.println(fieldAfter);
		System.out.println("");
	}
	
	public void log(boolean fail, int score) {
		System.out.println((fail ? "fail" : "pass")+" ("+score+")");
	}
}
