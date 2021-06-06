package other;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.umlv.zen5.KeyboardKey;
import graphics.Graphics;
import word.BoardElem;
import word.Name;
import word.Operator;
import word.PlayableElem;
import word.Property;
import wordEnum.OperatorEnum;
import wordEnum.PropertyEnum;

public class Board {
	private final ArrayList<BoardElem> board[];
	private final int lineLength;
	private final HashMap<BoardElem, ArrayList<Property>> rules = new HashMap<>();

	public Board(ArrayList<BoardElem> board[], int lineLength) {
		Objects.requireNonNull(board, "Le plateau ne peut pas �tre null");

		if (lineLength == 0 || board.length == 0)
			throw new IllegalArgumentException("Les lignes ou les colonnes ne peuvent pas �tre null.");

		this.board = board;
		this.lineLength = lineLength;
		updateProperties();
	}

	public void printRules() {
		for (BoardElem be : rules.keySet()) {
			System.out.print(be + " : ");
			for (Property p : rules.get(be)) {
				System.out.print(p + " ");
			}
			System.out.println();
		}
	}

	public int getColumn() {
		return lineLength;
	}

	public int getLine() {
		return board.length / lineLength;
	}

	public ArrayList<BoardElem>[] getBoard() {
		return board;
	}

	public HashMap<BoardElem, ArrayList<Property>> getRules() {
		return rules;
	}

	public ArrayList<BoardElem> getElems(int i, int j) {
		return board[i + j * lineLength];
	}

	public boolean isOver() {
		for (BoardElem be : rules.keySet()) {
			for (Property p : rules.get(be)) {
				if (p.equals(new Property(PropertyEnum.You)) && elementExists(be)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean elementExists(BoardElem e) {
		for (int i = 0; i < board.length; i++) {
			for (BoardElem be : board[i]) {
				if (be.equals(e)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isPushable(BoardElem be) {
		List<Property> r = rules.get(be);
		if (r == null) {
			return !(be instanceof PlayableElem);
		}
		return r.contains(new Property(PropertyEnum.Push)) || r.contains(new Property(PropertyEnum.You))
				|| !(be instanceof PlayableElem);
	}

	public boolean isDisabled(BoardElem be) {
		List<Property> r = rules.get(be);
		return (r == null || r.equals(new ArrayList<Property>())) && be instanceof PlayableElem;
	}

	private ArrayList<BoardElem> playedElements() {
		ArrayList<BoardElem> played = new ArrayList<>();
		for (BoardElem be : rules.keySet()) {
			for (Property p : rules.get(be)) {
				if (p.equals(new Property(PropertyEnum.You)) && elementExists(be)) {
					played.add(be);
				}
			}
		}
		return played;
	}

	private int translateDirection(KeyboardKey direction, int previousPosition) {
		int newPosition;
		switch (direction) {
		case UP:
			newPosition = previousPosition / lineLength == 0 ? previousPosition : previousPosition - lineLength;
			break;
		case DOWN:
			newPosition = previousPosition / lineLength == getLine() - 1? previousPosition : previousPosition + lineLength;
			break;
		case LEFT:
			newPosition = previousPosition % lineLength == 0 ? previousPosition : previousPosition - 1;
			break;
		case RIGHT:
			newPosition = previousPosition % lineLength == lineLength - 1 ? previousPosition : previousPosition + 1;
			break;
		default:
			throw new IllegalArgumentException("Mauvaise touche prise en compte !");
		}
		return newPosition;
	}

	private boolean win(ArrayList<BoardElem> words) {
		for (BoardElem be : words) {
			if (rules.getOrDefault(be, new ArrayList<Property>()).contains(new Property(PropertyEnum.Win))) {
				return true;
			}
		}
		return false;
	}

	private void changeWordsPlace(List<BoardElem> toMoveElem, List<Integer> toMovePrevPos,
			List<Integer> toMoveNextPos) {
		for (int i = 0; i < toMoveElem.size(); i++) {
			BoardElem toMove = toMoveElem.get(i);
			board[toMovePrevPos.get(i)].remove(toMove);
			board[toMoveNextPos.get(i)].add(toMove);
		}
	}

	private void keepMemoryToMove(List<BoardElem> toMoveElem, List<Integer> toMovePrevPos, List<Integer> toMoveNextPos,
			BoardElem be, int previousPosition, int nextPosition) {
		toMoveElem.add(be);
		toMovePrevPos.add(previousPosition);
		toMoveNextPos.add(nextPosition);
	}

	private boolean checkMoveRec(KeyboardKey direction, BoardElem w, int i, ArrayList<BoardElem> toMoveElem,
			ArrayList<Integer> toMovePrevPos, ArrayList<Integer> toMoveNextPos) {
		int newPosition = translateDirection(direction, i);
		try {
			if(i == newPosition) return false;
			for (BoardElem be : board[newPosition]) {
				if (!isDisabled(be)) {
					if (!isPushable(be)) {
						return false;
					} else { // El�ment actif ET poussable
						// Si obstacle ou autre impossibilit� de se d�placer : mettre les conditions et
						// v�rifications ici (avant le d�placement)
						if (checkMoveRec(direction, be, newPosition, toMoveElem, toMovePrevPos, toMoveNextPos)) {
							// D�placement � faire : on garde en m�moire
							keepMemoryToMove(toMoveElem, toMovePrevPos, toMoveNextPos, w, i, newPosition);
							return true;
						} else {
							return false;
						}
					}
				}
			}
			// Pas d'�l�ments en direction du d�placement
			keepMemoryToMove(toMoveElem, toMovePrevPos, toMoveNextPos, w, i, newPosition);
		}
		catch(StackOverflowError e){
				return false;
			}
		return true;
	}
	
	
	private boolean moveElementsUpLeft(List<BoardElem> elements, KeyboardKey direction, ArrayList<BoardElem> toMoveElem,
			ArrayList<Integer> toMovePrevPos, ArrayList<Integer> toMoveNextPos) {
		for (int i = 0; i < board.length; i++) {
			for (BoardElem w : board[i]) {
				if (elements.contains(w)) {
					// Move element from position [i][j]
					int newPosition = translateDirection(direction, i);
					if (win(board[newPosition])) { // Refaire une m�thode win propre !!!
						return true; // Partie gagn�e
					}
					checkMoveRec(direction, w, i, toMoveElem, toMovePrevPos, toMoveNextPos);
				}
			}
		}
		return false;
	}
	
	private boolean moveElementsDownRight(List<BoardElem> elements, KeyboardKey direction, ArrayList<BoardElem> toMoveElem,
			ArrayList<Integer> toMovePrevPos, ArrayList<Integer> toMoveNextPos) {
		for (int i = board.length-1; i >= 0; i--) {
			for (BoardElem w : board[i]) {
				if (elements.contains(w)) {
					// Move element from position [i][j]
					int newPosition = translateDirection(direction, i);
					if (win(board[newPosition])) { // Refaire une m�thode win propre !!!
						return true; // Partie gagn�e
					}
					checkMoveRec(direction, w, i, toMoveElem, toMovePrevPos, toMoveNextPos);
				}
			}
		}
		return false;
	}

	/*
	 * D�place tous les �l�ments jou�s puis renvoi un bool�en pour indiquer si la
	 * partie est gagn�e.
	 */
	public boolean moveElements(KeyboardKey direction) {
		ArrayList<BoardElem> elements = playedElements();
		ArrayList<BoardElem> toMoveElem = new ArrayList<>();
		ArrayList<Integer> toMovePrevPos = new ArrayList<>();
		ArrayList<Integer> toMoveNextPos = new ArrayList<>();
		if (direction.equals(KeyboardKey.DOWN) || direction.equals(KeyboardKey.RIGHT)) {
			if (moveElementsDownRight(elements, direction, toMoveElem, toMovePrevPos, toMoveNextPos)) {
				return true;
			}
		} else {
			if (moveElementsUpLeft(elements, direction, toMoveElem, toMovePrevPos, toMoveNextPos)) {
				return true;
			}
		}
		changeWordsPlace(toMoveElem, toMovePrevPos, toMoveNextPos);
		updateProperties();
		return false;
	}

	private void insertProperties(ArrayList<Property> list, Property... properties) {
		for (Property p : properties) {
			if (p != null && !(list.contains(p))) {
				list.add(p);
			}
		}
	}
	
	private void transformElements(Name origin, Name toTransform) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j<board[i].size(); j++) {
				if (board[i].get(j).equals(origin.equivalent())) {
					System.out.println("test");
					board[i].set(j, toTransform.equivalent());
				}
			}
		}
	}

	private Property checkColumnDown(int index, Name n) {
		if (index + lineLength < board.length) {
			for (BoardElem be : board[index + lineLength]) {
				if (be.equals(new Operator(OperatorEnum.Is))) {
					if (index + lineLength + lineLength < board.length) {
						for (BoardElem be2 : board[index + lineLength + lineLength]) {
							if (be2 instanceof Property) {
								return (Property) be2;
							}
							if (be2 instanceof Name) {
								transformElements(n, (Name) be2);
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Property checkLineRight(int index, Name n) {
		if (index + 1 < board.length) {
			for (BoardElem be : board[index + 1]) {
				if (be.equals(new Operator(OperatorEnum.Is))) {
					if (index + 1 + 1 < board.length) {
						for (BoardElem be2 : board[index + 1 + 1]) {
							if (be2 instanceof Property) {
								return (Property) be2;
							}
							if (be2 instanceof Name) {
								transformElements(n, (Name) be2);
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Property[] checkProperties(int index, Name n) {
		Property[] properties = new Property[2];
		Objects.checkIndex(index, board.length);
		properties[0] = checkLineRight(index, n);
		properties[1] = checkColumnDown(index, n);
		return properties;
	}

	private void updateProperties() {
		for (int i = 0; i < board.length; i++) {
			for (BoardElem be : board[i]) {
				if (be instanceof Name) {
					Property[] properties = checkProperties(i, (Name) be);
					var l = rules.get(be);
					ArrayList<Property> list = l != null ? l : new ArrayList<>();
					insertProperties(list, properties);
					rules.put(((Name) be).equivalent(), list);
				}
			}
		}
	}

	public void drawBoard(Graphics graph, Graphics2D context, float width, float height) throws IOException {
		for (int i = 0; i < board.length; i++) {
			for (BoardElem be : board[i]) {
				graph.drawImage(context, this, width, height, i % getColumn(), i / getColumn(), be);
			}
		}
	}
}
