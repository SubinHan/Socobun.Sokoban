package utils;

import java.io.File;

public class LevelFileSearcher implements IFileSearcher{

	public File[] getFiles(String path) {
		File directory = new File(path);
		File[] fileList = directory.listFiles();
		return fileList;
	}
	
	public File getFile(String path, String name) {
		File file = new File(path + "/" + name);
		return file;
	}

}
