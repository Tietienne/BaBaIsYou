package other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import word.Block;

public class Lecture {
	public static void fileToBoard(String level) throws IOException {
		File file = new File(level);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		WordEnum word = null;
		int cpt = 0;
		
		ArrayList<ArrayList<ArrayList<Block>>> board = new ArrayList<>();
		ArrayList<Rules> rules = new ArrayList<>();
		
		
		while((line = br.readLine()) != null) {
				
			for(int i = 0; i < line.length(); i++) {
				
				for (WordEnum wordenum : WordEnum.values()) {
					if(wordenum.getFileStr() == line.charAt(i))
						word = wordenum;
				};
				switch(word.getType()) {
				case Element : board.get(cpt).get(i).add(new Element(ElementEnum.valueOf(word.getBoardStr())));
				case Operator : board.get(cpt).get(i).add(new Operator(OperatorEnum.valueOf(word.getBoardStr())));
				case Property : board.get(cpt).get(i).add(new Property(PropertyEnum.valueOf(word.getBoardStr())));
				default: // add Block
					break;
				}
				
//				if(line.charAt(i) == 'v')
//					System.out.print(" ");
//				else
//					System.out.print(line.charAt(i));
			}
			System.out.println("");
			cpt += 1;
		}
		br.close();
		
		//return new Board(board, rules);
	}	
}

