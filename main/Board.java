package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.umlv.zen5.KeyboardKey;
import other.Rules;
import word.Element;
import word.Word;

public class Board {
	private Word[][] board;
	private int length;
	private int height;
	private ArrayList<Rules> rules;
	
	public Board(Word[][] board, int length, int height, List<Rules> list) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(length);
		Objects.requireNonNull(height);
		Objects.requireNonNull(list);
		if (board.length!=height) {
			throw new IllegalArgumentException("Le nombre de lignes du plateau n'est pas ï¿½gal au nombre de lignes donnï¿½ !");
		}
		for (int i=0; i<board.length; i++) {
			if (board[i].length!=length) {
				throw new IllegalArgumentException("Le nombre de colonnes du plateau n'est pas ï¿½gal au nombre de colonnes donnï¿½ !");
			}
		}
		
		this.board = board;
		this.length = length;
		this.height = height;
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
			// Si on a la règle gagnante : on vérifie si le mot sur lequel on va arriver en fait partie
			if (r.isWin() && r.getE().getName().equals(w.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Déplace tous les éléments joués puis renvoi un booléen pour indiquer si la partie est gagnée.
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
					// Si obstacle ou autre impossibilité de se déplacer : mettre les conditions et vérifications ici (avant le déplacement)
					if (win(board[newPosition[0]][newPosition[1]])) {
						return true; // Partie gagnée
					}
					board[newPosition[0]][newPosition[1]] = board[i][j];
				}
			}
		}
		return false;
	}
}
