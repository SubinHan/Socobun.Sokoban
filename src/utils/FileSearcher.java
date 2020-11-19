package utils;

import java.io.File;

public abstract class FileSearcher {

	public static File[] getFiles(String path) {
		File directory = new File(path);
		File[] fileList = directory.listFiles();
		return fileList;
	}
	
	public static File getFile(String path, String name) {
		File file = new File(path + "/" + name);
		return file;
	}

}