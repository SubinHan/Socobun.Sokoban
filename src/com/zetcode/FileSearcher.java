package com.zetcode;

import java.io.File;

public class FileSearcher {

	public static File[] getFiles(String path) {
		File directory = new File(path);
		File[] fileList = directory.listFiles();
		return fileList;
	}

}
