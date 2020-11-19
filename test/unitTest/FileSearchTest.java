package unitTest;

import java.io.File;

import junit.framework.TestCase;
import utils.FileSearcher;

public class FileSearchTest extends TestCase {
	
	FileSearcher searcher;
	final String path = "src/test/testFileSearch";
	public void setUp() {
	//	searcher = new FileSearcher();
	}
	
	public void testFileSearch() {
		File[] files;
		files = FileSearcher.getFiles(path);
		for(int i = 0; i < files.length; i++) {
			assertTrue(files[i].getName().equals(String.valueOf(i)));
		}
	}
}
