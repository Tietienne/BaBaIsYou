package other;

import java.util.ArrayList;
import java.util.Objects;

import fr.umlv.zen5.KeyboardKey;
import word.Element;
import word.Block;

public class Board {
	private final ArrayList<ArrayList<ArrayList<Block>>> board;
	private int length;
	private int height;
	private final ArrayList<Rules> rules;
	
	public Board(ArrayList<ArrayList<ArrayList<Block>>> board, ArrayList<Rules> list) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(list);
		
		if(board.size() == 0 || board.get(0).size() == 0)
			throw new IllegalArgumentException();
		
		
		this.board = board;
		this.length = board.size();
		this.height = board.get(0).size();
		this.rules = list;
	}
	
	public boolean isOver() {
		for (Rules r : rules) {
			if (r.isYou() && elementExists(r.getE())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean elementExists(Element e) {
		for (int i=0;i<height;i++) {
			for (int j=0;j<length;j++) {
				for (Block w : board.get(i).get(j)) {
					if (w instanceof Element && w.getName().equals(e.getName())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void addRule(Rules r) {
		rules.add(r);
	}
	
	public boolean isDisabled(Element e) {
		for (Rules r : rules) {
			if (r.getE().getName().equals(e.getName())) {
				return false;
			}
		}
		return true;
	}
	
	private ArrayList<Element> playedElements() {
		ArrayList<Element> played = new ArrayList<>();
		for (Rules r : rules) {
			if (r.isYou() && elementExists(r.getE())) {
				played.add(r.getE());
			}
		}
		return played;
	}
	
	private int[] translateDirection(KeyboardKey direction, int[] previousPosition) {
		int[] newPosition = new int[2];
		switch (direction) {
			case UP : newPosition[0] = Math.max(0, previousPosition[0]-1); newPosition[1] = previousPosition[1]; break;
			case DOWN : newPosition[0] = Math.min(board.size()-1, previousPosition[0]+1); newPosition[1] = previousPosition[1]; break;
			case LEFT : newPosition[1] = Math.max(0, previousPosition[1]-1); newPosition[0] = previousPosition[0]; break;
			case RIGHT : newPosition[1] = Math.min(board.get(0).size()-1, previousPosition[1]+1); newPosition[0] = previousPosition[0]; break;
			default : throw new IllegalArgumentException("Mauvaise touche prise en compte !");
		}
		return newPosition;
	}
	
	private boolean win(ArrayList<Block> words) {
		for (Rules r : rules) {
			// Si on a la rï¿½gle gagnante : on vï¿½rifie si le mot sur lequel on va arriver en fait partie
			for (Block w : words) {	
				if (r.isWin() && w instanceof Element && r.getE().getName().equals(w.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void changeWordPlace(Block w, ArrayList<Block> previous, ArrayList<Block> next) {
		previous.remove(w);
		next.add(w);
	}
	
	/*
	 * Dï¿½place tous les ï¿½lï¿½ments jouï¿½s puis renvoi un boolï¿½en pour indiquer si la partie est gagnï¿½e.
	 */
	public boolean moveElements(KeyboardKey direction) {
		ArrayList<Element> elements = playedElements();
		for (int i=0;i<height;i++) {
			for (int j=0;j<length;j++) {
				for (Block w : board.get(i).get(j)) {
					if (w instanceof Element && elements.contains(w)) {
						// Move element from position [i][j]
						int[] previousPosition = new int[2];
						previousPosition[0] = i;
						previousPosition[1] = j;
						int[] newPosition = translateDirection(direction, previousPosition);
						// Si obstacle ou autre impossibilitï¿½ de se dï¿½placer : mettre les conditions et vï¿½rifications ici (avant le dï¿½placement)
						if (win(board.get(newPosition[0]).get(newPosition[1]))) {
							return true; // Partie gagnï¿½e
						}
						// Déplacement
						changeWordPlace(w, board.get(i).get(j), board.get(newPosition[0]).get(newPosition[1]));
					}
				}
			}
		}
		return false;
	}
}
