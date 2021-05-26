package other;

import java.util.ArrayList;
import java.util.Objects;

import fr.umlv.zen5.KeyboardKey;
import word.Name;
import word.BoardElem;

public class Board {
	private final ArrayList<ArrayList<ArrayList<BoardElem>>> board;
	private int length;
	private int height;
	private final ArrayList<Rules> rules;
	
	public Board(ArrayList<ArrayList<ArrayList<BoardElem>>> board, ArrayList<Rules> list, int lenght, int height) {
		Objects.requireNonNull(board, "Le plateau ne peut pas �tre null");
		Objects.requireNonNull(list, "La liste des r�gles ne peut pas �tre nul");
		
		if(lenght == 0 || height == 0)
			throw new IllegalArgumentException();
		
		
		this.board = board;
		this.length = lenght;
		this.height = height;
		this.rules = list;
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
		for (int i=0;i<height;i++) {
			for (int j=0;j<length;j++) {
				for (BoardElem be : board.get(i).get(j)) {
					if (be.equals(e)) {
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
	
	private boolean win(ArrayList<BoardElem> words) {
		for (Rules r : rules) {
			// Si on a la r�gle gagnante : on v�rifie si le mot sur lequel on va arriver en fait partie
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
	 * D�place tous les �l�ments jou�s puis renvoi un bool�en pour indiquer si la partie est gagn�e.
	 */
	public boolean moveElements(KeyboardKey direction) {
		ArrayList<Name> elements = playedElements();
		for (int i=0;i<height;i++) {
			for (int j=0;j<length;j++) {
				for (BoardElem w : board.get(i).get(j)) {
					if (elements.contains(w)) {
						// Move element from position [i][j]
						int[] previousPosition = new int[2];
						previousPosition[0] = i;
						previousPosition[1] = j;
						int[] newPosition = translateDirection(direction, previousPosition);
						// Si obstacle ou autre impossibilit� de se d�placer : mettre les conditions et v�rifications ici (avant le d�placement)
						if (win(board.get(newPosition[0]).get(newPosition[1]))) {
							return true; // Partie gagn�e
						}
						// D�placement
						changeWordPlace(w, board.get(i).get(j), board.get(newPosition[0]).get(newPosition[1]));
					}
				}
			}
		}
		return false;
	}
}
