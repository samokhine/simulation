package simulation;

import java.io.File;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class StrategyTest {
	private static final String DIR = "src/test/resources/";
	
	@Test
	public void test()  {
        File fieldFile = new File(DIR, "field.txt");
		Space space = new Space(fieldFile);

        File instructionsFile = new File(DIR, "script.txt");
        Strategy strategy = new Strategy(instructionsFile);
        
        Logger logger = mock(Logger.class);
        
        strategy.setLogger(logger);
        strategy.run(space);
        
        // testing first step
        verify(logger).log(eq(1), 
        		eq("A....\n.....\n.....\n....c\n....."), 
        		eq("south"), 
        		eq("z....\n.....\n.....\n....b\n.....\n.....\n....."));
        
        // testing last step
        verify(logger).log(eq(8), 
        		eq("t\n.\n."), 
        		eq("delta"), 
        		eq("."));
        
        // testing result call
        verify(logger).log(eq(false), eq(10));
	}
}
