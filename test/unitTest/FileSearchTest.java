package unitTest;

import java.io.File;

import junit.framework.TestCase;
import utils.IFileSearcher;
import utils.LevelFileSearcher;

public class FileSearchTest extends TestCase {
	
	LevelFileSearcher searcher;
	final String path = "src/test/testFileSearch";
	public void setUp() {
		IFileSearcher searcher = new LevelFileSearcher();
	}
	
	public void testFileSearch() {
		
		
		File[] files;
		files = searcher.getFiles(path);
		for(int i = 0; i < files.length; i++) {
			assertTrue(files[i].getName().equals(String.valueOf(i)));
		}
	}
}
