package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import word.BoardElem;
import word.Name;
import word.Operator;
import word.PlayableElem;
import word.Property;
import wordEnum.NameEnum;
import wordEnum.OperatorEnum;
import wordEnum.PlayableEnum;
import wordEnum.PropertyEnum;
import wordEnum.WordEnum;

public class Lecture {
	@SuppressWarnings("unchecked")
	/**
	 * Converts the file level to a board usable for the game
	 * @param level
	 * @return
	 * @throws IOException
	 */
	
	/**
	 * Return the Width of the level depending on the value on the file
	 * @param line
	 * @return
	 */
	public static int getWidthLevel(String line) {
		StringBuilder sWidth = new StringBuilder();
		sWidth.append(line.charAt(0)).append(line.charAt(1));
		return Integer.parseInt(sWidth.toString());
	}
	
	/**
	 * Return the Height of the level depending on the value on the file
	 * @param line
	 * @return
	 */
	public static int getHeightLevel(String line) {
		StringBuilder sHeight = new StringBuilder();
		sHeight.append(line.charAt(3)).append(line.charAt(4));
		return Integer.parseInt(sHeight.toString());
	}
	
	/**
	 * Fill the board on the line "cpt" compared to the String line
	 * @param line String
	 * @param board ArrayList<BoardElem>
	 * @param cpt int 
	 * @param width Width of the window
	 * @return
	 */
	public static ArrayList<BoardElem>[] fillBoard(String line, ArrayList<BoardElem> board[], int cpt, int width) {
		WordEnum word = null;
		for (int i = 0; i < line.length(); i++) {
			for (WordEnum wordenum : WordEnum.values()) {
				if (wordenum.getFileStr() == line.charAt(i)) {
					word = wordenum;
				}
			}
			if (word != null) {
				switch (word.getType()) {
				case Name:
					board[(cpt - 1) * width + i].add(new Name(NameEnum.valueOf(word.getBoardStr())));
					break;
				case Operator:
					board[(cpt - 1) * width + i].add(new Operator(OperatorEnum.valueOf(word.getBoardStr())));
					break;
				case Property:
					board[(cpt - 1) * width + i].add(new Property(PropertyEnum.valueOf(word.getBoardStr())));
					break;
				case PlayableElem:
					board[(cpt - 1) * width + i]
							.add(new PlayableElem(PlayableEnum.valueOf(word.getBoardStr())));
					break;
				default:
					board[(cpt - 1) * width + i].add(null);
					break;
				}
			}
			word = null;
		}
		return board;
	}
	
	/**
	 * Convert the file to a Board usable for the game
	 * @param level String
	 * @return
	 * @throws IOException
	 */
	public static Board fileToBoard(String level) throws IOException {
		Path file = Paths.get(level);
		BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8);
		String line;
		int cpt = 0, height = 0, width = 0;
		ArrayList<BoardElem> board[] = null;
		System.out.println("start");

		while ((line = br.readLine()) != null) {
			if (cpt == 0) {
				width = getWidthLevel(line);
				height = getHeightLevel(line);

				board = new ArrayList[height * width];
				for (int i = 0; i < width * height; i++) {
					board[i] = new ArrayList<BoardElem>();
				}
			}
			else
				board = fillBoard(line, board, cpt, width);
			cpt++;
		}
		br.close();
		return new Board(board, width);
	}
}
