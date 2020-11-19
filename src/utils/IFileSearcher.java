package utils;

import java.io.File;

public interface IFileSearcher {
	public File[] getFiles(String path);
	public File getFile(String path, String name);
}
