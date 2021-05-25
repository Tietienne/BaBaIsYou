package other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import word.BoardElem;

public class Lecture {
	public static Board fileToBoard(String level) throws IOException {
		File file = new File(level);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		
		ArrayList<ArrayList<ArrayList<BoardElem>>> board = new ArrayList<>();
		ArrayList<Rules> rules = new ArrayList<>();
		
		
		while((line = br.readLine()) != null) {
				
			for(int i = 0; i < line.length(); i++) {
				

//				if(line.charAt(i) == 'v')
//					System.out.print(" ");
//				else
//					System.out.print(line.charAt(i));
			}
		}
		br.close();
		
		return new Board(board, rules);
	}	
}

