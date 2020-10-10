package unitTest;

import java.io.File; 
import com.zetcode.FileSearcher;

import junit.framework.TestCase;

public class FileSearchTest extends TestCase {
	
	FileSearcher searcher;
	final String path = "src/test/testFileSearch";
	public void setUp() {
		searcher = new FileSearcher();
	}
	
	public void testFileSearch() {
		File[] files;
		files = FileSearcher.getFiles(path);
		for(int i = 0; i < files.length; i++) {
			assertTrue(files[i].getName().equals(String.valueOf(i)));
		}
	}
}
