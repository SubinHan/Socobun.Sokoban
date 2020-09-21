package com.zetcode;

import java.util.ArrayList;
import java.util.Vector;

public class Level {
	
	public char[][] levelString;
	private int w = 0;
	private int h = 0;
	private int l = 0;
	private int bggCnt = 0;
	
	// 플레이어, 짐, 목표지점이 생성될 수 있는 지점(y,x좌표를 담은 2차원벡터)들을 모두 담을 어레이리스트. 하나만 존재하면 된다.
	public static ArrayList< Vector<Integer> > possiblePosition = new ArrayList< Vector<Integer>>();
	
	//width, height가 7 이상을 권장.
	public Level(int width, int height, int baggage_cnt, int seed) {
		
		levelString = new char[height][width+1];
		w = width + 1; // '\n'을 넣어주기 위해 +1.
		h = height;
		l = w * h;
		bggCnt = baggage_cnt;
		
		// Baggage, Area(도착지점), Player를 다 합친 개수(=2*bggCnt+1)가 possiblePosition의 크기(width-4)*(height-4)보다 크면 안된다.
    	while((bggCnt*2)+ 1 > (width-4)*(height-4)) bggCnt-=1;
		
		initiateWithWalls();
		genPlayer(seed);
		genBaggages(seed);
		genGoals(seed);
		makePaths();
		
	}

	private void initiateWithWalls() { // 모든 좌표를 벽으로 채운다. i가 y좌표. 배열에서 좌표는 [y][x]와 같이 표현된다고 생각하자.
		for(int i=0 ; i<h ; ++i) {
			for(int j=0 ; j<w ; ++j) {
				
				levelString[i][j] = '#';
				
				if(j == w-1) levelString[i][j] = '\n'; // 맨 오른쪽을 줄바꿈 문자로 
				
				//맨 오른쪽 '벽'과 맨 왼쪽 '벽', 맨 위쪽과 맨 아래쪽이 아닐 경우 좌표를 possiblePosition에 넣고, 나중에 시드에 맞춰 랜덤으로 꺼내서 쓴다.
				if(j < w-3 && j > 1 && i > 1 && i < h-2) {
					Vector<Integer> positionVector = new Vector<Integer>();
					levelString[i][j] = ' ';
					positionVector.add(i);
					positionVector.add(j);
					possiblePosition.add(positionVector); // Vector.get(0) = y좌표라고 생각하자.
					// possiblePosition의 크기는 (w-3) * (h-2) = (width-2) * (height-2) < baggage_cnt * 2 + 1 이다.
				}
				
				//시험용 코드
				if(j < w-2 && j > 0 && i > 0 && i < h-1) {
					levelString[i][j] = ' ';
				}
			}
		}
		
	}

	
	// 플레이어 생성 (@)
	private void genPlayer(int seed) {
		int index = seed % possiblePosition.size();
		System.out.println("Seed : " + seed);
		int y = possiblePosition.get(index).get(0);
		int x = possiblePosition.get(index).get(1);
		levelString[y][x] = '@';
		possiblePosition.remove(index);
	}
	
	// 짐 좌표를 랜덤으로 생성 ($)
	private void genBaggages(int seed) {
		int index;
		for(int i=0 ; i<bggCnt ; ++i) {
			index = seed % possiblePosition.size();
			int y = possiblePosition.get(index).get(0);
			int x = possiblePosition.get(index).get(1);
			
			// 짐 놓으려는 좌표가 구석이거나, 정사각 네모를 만드는 경우, seed에 1씩 더함으로써 possiblePosition 내의 다른 좌표를 찾는다.
			if(fourBaggageNear(x, y) || !baggageIsMovable(x, y)) {
				i--;
				seed++;
			}
			else {
				levelString[y][x] = '$';
				possiblePosition.remove(index);
			}
		}
	}
	
	// 목표지점(Area)을 랜덤으로 생성 (개수 = 짐 개수) (.)
	private void genGoals(int seed) {
		int index;
		for(int i=0 ; i<bggCnt ; ++i) {
			index = seed % possiblePosition.size();
			int y = possiblePosition.get(index).get(0);
			int x = possiblePosition.get(index).get(1);
			levelString[y][x] = '.';
			possiblePosition.remove(index);
		}
	}
	
	// 짐을 목표지점으로 옮기면서 이 때 '#'을 ' '(공백)으로 바꾸자.
	private void makePaths() {
		
	}

	private boolean baggageIsMovable(int bx, int by) {
		if(levelString[by+1][bx] == '#' && levelString[by][bx-1] == '#') return false;
		else if(levelString[by+1][bx] == '#' && levelString[by][bx+1] == '#') return false;
		else if(levelString[by-1][bx] == '#' && levelString[by][bx-1] == '#') return false;
		else if(levelString[by-1][bx] == '#' && levelString[by][bx+1] == '#') return false;
		else if(levelString[by+1][bx] == '$' && levelString[by][bx-1] == '$') return true;
		else if(levelString[by+1][bx] == '$' && levelString[by][bx+1] == '$') return true;
		else if(levelString[by-1][bx] == '$' && levelString[by][bx-1] == '$') return true;
		else if(levelString[by-1][bx] == '$' && levelString[by][bx+1] == '$') return true;
		else return true;
	}
	
	private boolean fourBaggageNear(int bx, int by) {
		if(levelString[by+1][bx] == '$' && levelString[by][bx-1] == '$' && levelString[by+1][bx-1] == '$') return true;
		else if(levelString[by+1][bx] == '$' && levelString[by][bx+1] == '$' && levelString[by+1][bx+1] == '$') return true;
		else if(levelString[by-1][bx] == '$' && levelString[by][bx-1] == '$' && levelString[by-1][bx-1] == '$') return true;
		else if(levelString[by-1][bx] == '$' && levelString[by][bx+1] == '$' && levelString[by-1][bx+1] == '$') return true;
		else return false;
	}

	public int length() {
		return l;
	}
	
	public char charAt(int index) {
		int row = index / w;
		int col = index % w;
		return levelString[row][col];
	}
}
