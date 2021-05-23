package other;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.umlv.zen5.KeyboardKey;
import word.Element;
import word.Word;

public class Board {
	private List<List<List<Word>>> board;
	private int length;
	private int height;
	private ArrayList<Rules> rules;
	
	public Board(List<List<List<Word>>> board, List<Rules> list) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(list);
		
		if(board.size() == 0 || board.get(0).size() == 0)
			throw new IllegalArgumentException();
		
		
		this.board = board;
		this.length = board.size();
		this.height = board.get(0).size();
		this.rules = (ArrayList<Rules>) list;
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
				if (board[i][j].getName().equals(e.getName())) {
					return true;
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
			case DOWN : newPosition[0] = Math.min(board.length-1, previousPosition[0]+1); newPosition[1] = previousPosition[1]; break;
			case LEFT : newPosition[1] = Math.max(0, previousPosition[1]-1); newPosition[0] = previousPosition[0]; break;
			case RIGHT : newPosition[1] = Math.min(board[0].length-1, previousPosition[1]+1); newPosition[0] = previousPosition[0]; break;
			default : throw new IllegalArgumentException("Mauvaise touche prise en compte !");
		}
		return newPosition;
	}
	
	private boolean win(Word w) {
		for (Rules r : rules) {
			// Si on a la r�gle gagnante : on v�rifie si le mot sur lequel on va arriver en fait partie
			if (r.isWin() && r.getE().getName().equals(w.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * D�place tous les �l�ments jou�s puis renvoi un bool�en pour indiquer si la partie est gagn�e.
	 */
	public boolean moveElements(KeyboardKey direction) {
		ArrayList<Element> elements = playedElements();
		for (int i=0;i<height;i++) {
			for (int j=0;j<length;j++) {
				if (elements.contains(board[i][j])) {
					// Move element from position [i][j]
					int[] previousPosition = new int[2];
					previousPosition[0] = i;
					previousPosition[1] = j;
					int[] newPosition = translateDirection(direction, previousPosition);
					// Si obstacle ou autre impossibilit� de se d�placer : mettre les conditions et v�rifications ici (avant le d�placement)
					if (win(board[newPosition[0]][newPosition[1]])) {
						return true; // Partie gagn�e
					}
					board[newPosition[0]][newPosition[1]] = board[i][j];
				}
			}
		}
		return false;
	}
}
