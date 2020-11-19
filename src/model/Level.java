package model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utils.SokobanUtilities;


public class Level {
	private final int MAX_LEVEL_WIDTH = SokobanUtilities.MAX_LEVEL_WIDTH;
	private final int MAX_LEVEL_HEIGHT = SokobanUtilities.MAX_LEVEL_HEIGHT;
	
	private char[][] level;
	private String levelName;
	private File file;

	public Level(File file) throws IOException {
		this.file = file;
		this.levelName = file.getName();
		
		level = new char[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];
		FileReader fr = new FileReader(file);
		for (int i = 0; i < MAX_LEVEL_HEIGHT; i++) {
			for (int j = 0; j < MAX_LEVEL_WIDTH; j++) {
				char cr = (char) fr.read();
				level[j][i] = cr;
			}
		}
		fr.close();	
	}
	
	public Level(char[][] level) {
		this.level = level;
	}
	
	public Level() {
		level = new char[MAX_LEVEL_WIDTH][MAX_LEVEL_HEIGHT];
		for (int i = 0; i < MAX_LEVEL_HEIGHT; i++) {
			for (int j = 0; j < MAX_LEVEL_WIDTH; j++) {
				level[j][i] = SokobanUtilities.ACTOR_WALL;
			}
		}
	}
	
	public char[][] getCharArray() {
		return level;
	}
	
	public File getFile() {
		return file;
	}
}
