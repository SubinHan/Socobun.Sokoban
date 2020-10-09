package com.zetcode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/*
 * 
 */
public class SokobanUtilities {
	public final static int MAX_LEVEL_WIDTH = 20;
	public final static int MAX_LEVEL_HEIGHT = 11;
	public final static int SIZE_OF_CELLS = 64;
	public final static char ACTOR_SPACE = ' ';
	public final static char ACTOR_WALL = '#';
	public final static char ACTOR_GROUND = '^';
	public final static char ACTOR_AREA = '.';
	public final static char ACTOR_BAGGAGE = '$';
	public  final static char ACTOR_PLAYER = '@';
	
	/**
	 * 
	 * @param levelFile		
	 * @return 				String으로 변환된 level	
	 */
	public static String fileToString(File levelFile) {
		String level = "";
		try {
			FileReader fr = new FileReader(levelFile);
			for(int i = 0; i < MAX_LEVEL_HEIGHT; i++) {
	    		for(int j = 0; j < MAX_LEVEL_WIDTH; j++) {
	    			char cr = (char)fr.read();
	    			level = level.concat(Character.toString(cr));
	    		}
	    		level = level.concat("\n");
	    	}
			fr.close();
		} catch(IOException exception){
			;
		}
		return level;
	}
	
	/**
	 * 
	 * @param filePath	저장할 파일 경로
	 * @param level		문자형 배열로 이루어진 level, level의 크기는 MAX_LEVEL_WIDTH * MAX_LEVEL_HEIGHT
	 */
	public static void charArrayToFile(String filePath, char[][] level) {
		FileWriter fw = null;
		File file = new File(filePath);
		
		try {
			fw = new FileWriter(file, false);
			for(int i = 0; i < MAX_LEVEL_HEIGHT; i++) {
				for(int j = 0; j < MAX_LEVEL_WIDTH; j++) {
					fw.write(level[j][i]);
				}
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
