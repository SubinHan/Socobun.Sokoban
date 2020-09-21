package com.zetcode;

import java.util.ArrayList;
import java.util.Vector;

public class Level {
	
	public char[][] levelString;
	private int w = 0;
	private int h = 0;
	private int l = 0;
	private int bggCnt = 0;
	
	// �÷��̾�, ��, ��ǥ������ ������ �� �ִ� ����(y,x��ǥ�� ���� 2��������)���� ��� ���� ��̸���Ʈ. �ϳ��� �����ϸ� �ȴ�.
	public static ArrayList< Vector<Integer> > possiblePosition = new ArrayList< Vector<Integer>>();
	
	//width, height�� 7 �̻��� ����.
	public Level(int width, int height, int baggage_cnt, int seed) {
		
		levelString = new char[height][width+1];
		w = width + 1; // '\n'�� �־��ֱ� ���� +1.
		h = height;
		l = w * h;
		bggCnt = baggage_cnt;
		
		// Baggage, Area(��������), Player�� �� ��ģ ����(=2*bggCnt+1)�� possiblePosition�� ũ��(width-4)*(height-4)���� ũ�� �ȵȴ�.
    	while((bggCnt*2)+ 1 > (width-4)*(height-4)) bggCnt-=1;
		
		initiateWithWalls();
		genPlayer(seed);
		genBaggages(seed);
		genGoals(seed);
		makePaths();
		
	}

	private void initiateWithWalls() { // ��� ��ǥ�� ������ ä���. i�� y��ǥ. �迭���� ��ǥ�� [y][x]�� ���� ǥ���ȴٰ� ��������.
		for(int i=0 ; i<h ; ++i) {
			for(int j=0 ; j<w ; ++j) {
				
				levelString[i][j] = '#';
				
				if(j == w-1) levelString[i][j] = '\n'; // �� �������� �ٹٲ� ���ڷ� 
				
				//�� ������ '��'�� �� ���� '��', �� ���ʰ� �� �Ʒ����� �ƴ� ��� ��ǥ�� possiblePosition�� �ְ�, ���߿� �õ忡 ���� �������� ������ ����.
				if(j < w-3 && j > 1 && i > 1 && i < h-2) {
					Vector<Integer> positionVector = new Vector<Integer>();
					levelString[i][j] = ' ';
					positionVector.add(i);
					positionVector.add(j);
					possiblePosition.add(positionVector); // Vector.get(0) = y��ǥ��� ��������.
					// possiblePosition�� ũ��� (w-3) * (h-2) = (width-2) * (height-2) < baggage_cnt * 2 + 1 �̴�.
				}
				
				//����� �ڵ�
				if(j < w-2 && j > 0 && i > 0 && i < h-1) {
					levelString[i][j] = ' ';
				}
			}
		}
		
	}

	
	// �÷��̾� ���� (@)
	private void genPlayer(int seed) {
		int index = seed % possiblePosition.size();
		System.out.println("Seed : " + seed);
		int y = possiblePosition.get(index).get(0);
		int x = possiblePosition.get(index).get(1);
		levelString[y][x] = '@';
		possiblePosition.remove(index);
	}
	
	// �� ��ǥ�� �������� ���� ($)
	private void genBaggages(int seed) {
		int index;
		for(int i=0 ; i<bggCnt ; ++i) {
			index = seed % possiblePosition.size();
			int y = possiblePosition.get(index).get(0);
			int x = possiblePosition.get(index).get(1);
			
			// �� �������� ��ǥ�� �����̰ų�, ���簢 �׸� ����� ���, seed�� 1�� �������ν� possiblePosition ���� �ٸ� ��ǥ�� ã�´�.
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
	
	// ��ǥ����(Area)�� �������� ���� (���� = �� ����) (.)
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
	
	// ���� ��ǥ�������� �ű�鼭 �� �� '#'�� ' '(����)���� �ٲ���.
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
