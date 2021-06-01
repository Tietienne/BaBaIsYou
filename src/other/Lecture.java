package other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	public static Board fileToBoard(String level) throws IOException {
		File file = new File(level);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		WordEnum word = null;
		int cpt = 0, height = 0, width = 0;
		ArrayList<BoardElem> board[] = null;
		System.out.println("start");

		while ((line = br.readLine()) != null) {
			if (cpt == 0) {
				StringBuilder sWidth = new StringBuilder();
				sWidth.append(line.charAt(0)).append(line.charAt(1));
				width = Integer.parseInt(sWidth.toString());

				StringBuilder sHeight = new StringBuilder();
				sHeight.append(line.charAt(3)).append(line.charAt(4));
				height = Integer.parseInt(sHeight.toString());

				board = new ArrayList[height * width];
				for (int i = 0; i < width * height; i++) {
					board[i] = new ArrayList<BoardElem>();
				}
			}

			else {

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
							board[(cpt - 1) * width + i].add(new PlayableElem(PlayableEnum.valueOf(null)));
							break;
						}
					}
					word = null;

				}
			}
			cpt++;
		}
		br.close();

		return new Board(board, height, width);
	}
}
