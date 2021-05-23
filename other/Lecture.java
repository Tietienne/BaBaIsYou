package other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Lecture {
	public static void fileToBoard(String level) throws IOException {
		File file = new File(level);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line = br.readLine()) != null) {
				
			for(int i = 0; i < line.length(); i++) {
				switch(line.charAt(i)) {
					case 'B' : System.out.print("Baba");
					case 'I' : System.out.print("Is");
					case 'Y' : System.out.print("You");
					case 'F' : System.out.print("Flag");
					case '!' : System.out.print("Win");
					case 'W' : System.out.print("Wall");
					case 'w' : System.out.print("wall");
					case 'r' : System.out.print("rock");
					case 'R' : System.out.print("Rock");
					case 'S' : System.out.print("Stop");
					case 'P' : System.out.print("Push");
					case 'v' : System.out.print(" ");
				}
//				if(line.charAt(i) == 'v')
//					System.out.print(" ");
//				else
//					System.out.print(line.charAt(i));
			}
		}
		br.close();
	}	
}

