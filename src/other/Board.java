package other;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;
import graphics.Graphics;
import word.Name;
import word.BoardElem;

public class Board {
	private final ArrayList<BoardElem> board[];
	private final int lineLength;
	private final ArrayList<Rules> rules;
	
	public Board(ArrayList<BoardElem> board[], ArrayList<Rules> list, int lineLength) {
		Objects.requireNonNull(board, "Le plateau ne peut pas ï¿½tre null");
		Objects.requireNonNull(list, "La liste des rï¿½gles ne peut pas ï¿½tre nul");
		
		if(lineLength == 0)
			throw new IllegalArgumentException();
		
		
		this.board = board;
		this.lineLength = lineLength;
		this.rules = list;
	}
	
	public int getLength() {
		return lineLength;
	}

	public int getHeight() {
		return board.length/lineLength;
	}

	public ArrayList<BoardElem>[] getBoard() {
		return board;
	}

	public ArrayList<Rules> getRules() {
		return rules;
	}
	
	public ArrayList<BoardElem> getElems(int i, int j) {
		return board[i+j*lineLength];
	}

	public boolean isOver() {
		for (Rules r : rules) {
			if (r.isYou() && elementExists(r.getN())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean elementExists(Name e) {
		for (int i=0; i<board.length; i++) {
			for (BoardElem be : board[i]) {
				if (be.equals(e)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void addRule(Rules r) {
		rules.add(r);
	}
	
	public boolean isDisabled(Name e) {
		for (Rules r : rules) {
			if (r.getN().equals(e)) {
				return false;
			}
		}
		return true;
	}
	
	private ArrayList<Name> playedElements() {
		ArrayList<Name> played = new ArrayList<>();
		for (Rules r : rules) {
			if (r.isYou() && elementExists(r.getN())) {
				played.add(r.getN());
			}
		}
		return played;
	}
	
//	private int[] translateDirection(KeyboardKey direction, int[] previousPosition) {
//		int[] newPosition = new int[2];
//		switch (direction) {
//			case UP : newPosition[0] = Math.max(0, previousPosition[0]-1); newPosition[1] = previousPosition[1]; break;
//			case DOWN : newPosition[0] = Math.min(board.size()-1, previousPosition[0]+1); newPosition[1] = previousPosition[1]; break;
//			case LEFT : newPosition[1] = Math.max(0, previousPosition[1]-1); newPosition[0] = previousPosition[0]; break;
//			case RIGHT : newPosition[1] = Math.min(board.get(0).size()-1, previousPosition[1]+1); newPosition[0] = previousPosition[0]; break;
//			default : throw new IllegalArgumentException("Mauvaise touche prise en compte !");
//		}
//		return newPosition;
//	}
	
	private boolean win(ArrayList<BoardElem> words) {
		for (Rules r : rules) {
			// Si on a la rï¿½gle gagnante : on vï¿½rifie si le mot sur lequel on va arriver en fait partie
			for (BoardElem w : words) {	
				if (r.isWin() && r.getN().equals(w)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void changeWordPlace(BoardElem w, ArrayList<BoardElem> previous, ArrayList<BoardElem> next) {
		previous.remove(w);
		next.add(w);
	}
	
	/*
	 * Dï¿½place tous les ï¿½lï¿½ments jouï¿½s puis renvoi un boolï¿½en pour indiquer si la partie est gagnï¿½e.
	 */
//	public boolean moveElements(KeyboardKey direction) {
//		ArrayList<Name> elements = playedElements();
//		for (int i=0;i<height;i++) {
//			for (int j=0;j<length;j++) {
//				for (BoardElem w : board.get(i).get(j)) {
//					if (elements.contains(w)) {
//						// Move element from position [i][j]
//						int[] previousPosition = new int[2];
//						previousPosition[0] = i;
//						previousPosition[1] = j;
//						int[] newPosition = translateDirection(direction, previousPosition);
//						// Si obstacle ou autre impossibilitï¿½ de se dï¿½placer : mettre les conditions et vï¿½rifications ici (avant le dï¿½placement)
//						if (win(board.get(newPosition[0]).get(newPosition[1]))) {
//							return true; // Partie gagnï¿½e
//						}
//						// Dï¿½placement
//						changeWordPlace(w, board.get(i).get(j), board.get(newPosition[0]).get(newPosition[1]));
//					}
//				}
//			}
//		}
//		return false;
//	}
	
	private void initProperties() {
		
	}
	
	public void drawBoard(Graphics graph, ApplicationContext context, float width, float height) throws IOException {
		for (int i=0; i<board.length; i++) {
			for (BoardElem be : board[i]) {
				System.out.println(be.toString()+" Numéro : "+i+" x : "+i/lineLength+" y : "+i%lineLength);
				graph.drawImage(context, this, width, height, i/lineLength+1, i%lineLength+1, be);
			}
		}
		
	}
}
