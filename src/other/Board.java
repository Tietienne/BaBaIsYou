package other;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	private final HashMap<PlayableElem, ArrayList<Property>> rules = new HashMap<>();

	
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

	public HashMap<PlayableElem, ArrayList<Property>> getRules() {
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
	
	private ArrayList<BoardElem> getToDestroy() {
		ArrayList<BoardElem> toDestroy = new ArrayList<>();
		for (BoardElem be : rules.keySet()) {
			ArrayList<Property> properties = rules.get(be);
			if (properties.contains(new Property(PropertyEnum.Hot)) && properties.contains(new Property(PropertyEnum.Melt))) {
				if (!toDestroy.contains(be)) {
					toDestroy.add(be);
				}
			}
			if (properties.contains(new Property(PropertyEnum.Defeat)) && properties.contains(new Property(PropertyEnum.You))) {
				if (!toDestroy.contains(be)) {
					toDestroy.add(be);
				}
			}
		}
		return toDestroy;
	}
	
	private void checkToDestroy() {
		ArrayList<BoardElem> toDestroy = getToDestroy();
		if (toDestroy.size()!=0) {
			for (int i = 0; i < board.length; i++) {
				for (Iterator<BoardElem> it = board[i].iterator(); it.hasNext();) {
					BoardElem be = it.next();
					if (toDestroy.contains(be)) {
						it.remove();
					}
				}
			}
		}
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
	
	private boolean isYou(BoardElem be) {
		return playedElements().contains(be);
	}
	
	private boolean isDefeat(BoardElem be) {
		return defeatElements().contains(be);
	}
	
	private boolean isMelt(BoardElem be) {
		return meltElements().contains(be);
	}
	
	private boolean isHot(BoardElem be) {
		return hotElements().contains(be);
	}
	
	private boolean isSink(BoardElem be) {
		return sinkElements().contains(be);
	}

	private boolean isPushable(BoardElem be) {
		List<Property> r = rules.get(be);
		if (r == null) {
			return !(be instanceof PlayableElem);
		}
		return r.contains(new Property(PropertyEnum.Push)) || r.contains(new Property(PropertyEnum.You))
				|| !(be instanceof PlayableElem);
	}

	private boolean isDisabled(BoardElem be) {
		List<Property> r = rules.get(be);
		return (r == null || r.size()==0) && be instanceof PlayableElem;
	}
	
	private void addElements(ArrayList<BoardElem> list, PropertyEnum prop) {
		for (BoardElem be : rules.keySet()) {
			for (Property p : rules.get(be)) {
				if (p.equals(new Property(prop)) && elementExists(be)) {
					list.add(be);
				}
			}
		}
	}
	
	private ArrayList<BoardElem> hotElements() {
		ArrayList<BoardElem> hot = new ArrayList<>();
		addElements(hot, PropertyEnum.Hot);
		return hot;
	}
	
	private ArrayList<BoardElem> meltElements() {
		ArrayList<BoardElem> melt = new ArrayList<>();
		addElements(melt, PropertyEnum.Melt);
		return melt;
	}
	
	private ArrayList<BoardElem> sinkElements() {
		ArrayList<BoardElem> sinks = new ArrayList<>();
		addElements(sinks, PropertyEnum.Sink);
		return sinks;
	}

	private ArrayList<BoardElem> playedElements() {
		ArrayList<BoardElem> played = new ArrayList<>();
		addElements(played, PropertyEnum.You);
		return played;
	}
	
	private ArrayList<BoardElem> defeatElements() {
		ArrayList<BoardElem> loser = new ArrayList<>();
		addElements(loser, PropertyEnum.Defeat);
		return loser;
	}
	
	private ArrayList<BoardElem> winElements() {
		ArrayList<BoardElem> winner = new ArrayList<>();
		addElements(winner, PropertyEnum.Win);
		return winner;
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
	
	private boolean containsSomething(ArrayList<BoardElem> list1, ArrayList<BoardElem> list2) {
		boolean b1 = false;
		boolean b2 = false;
		for (int i = 0; i < board.length; i++) {
			b1 = false;
			b2 = false;
			for (BoardElem be : board[i]) {
				if (list1.contains(be)) {
					b1 = true;
				}
				if (list2.contains(be)) {
					b2 = true;
				}
			}
			if (b1 && b2) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isWin() {
		ArrayList<BoardElem> player = playedElements();
		ArrayList<BoardElem> winner = winElements();
		return containsSomething(player, winner);
	}

	private boolean nextToWin(ArrayList<BoardElem> words) {
		for (BoardElem be : words) {
			if (rules.getOrDefault(be, new ArrayList<Property>()).contains(new Property(PropertyEnum.Win))) {
				return true;
			}
		}
		return false;
	}

	private boolean checkMoveRec(KeyboardKey direction, BoardElem w, int i, Iterator<BoardElem> itPrev) {
		int newPosition = translateDirection(direction, i);
		try {
			if(i == newPosition) return false;
			for (Iterator<BoardElem> it = board[newPosition].iterator(); it.hasNext();) {
				BoardElem be = it.next();
				if (!isDisabled(be)) {
					if (isPushable(be)) {
						// El�ment actif ET poussable
						if (!checkMoveRec(direction, be, newPosition, it)) {
							// D�placement impossible : on renvoit false
							return false;
						}
						continue;
					}
					
					if (isSink(be) || isSink(w)) {
						it.remove();
						itPrev.remove();
						return true;
					}
					
					if (isHot(be) && isMelt(w)) {
						itPrev.remove();
						return true;
					}
					
					if (isHot(w) && isMelt(be)) {
						it.remove();
						continue;
					}
					
					if (isDefeat(be)) {
						if (isYou(w)) {
							itPrev.remove();
							return true;
						}
						continue;
					}
					return false;
				}

			}
			itPrev.remove();
			board[newPosition].add(w);
		} catch(StackOverflowError e) {
			return false;
		}
		return true;
	}
	
	private void moveIterator(List<BoardElem> elements, KeyboardKey direction, int i) {
		for (Iterator<BoardElem> it = board[i].iterator(); it.hasNext();) {
			BoardElem be = it.next();
			if (elements.contains(be)) {
				// Check if next to win element
				int newPosition = translateDirection(direction, i);
				if (nextToWin(board[newPosition])) {
					it.remove();
					board[newPosition].add(be);
				} else {
					checkMoveRec(direction, be, i, it);
				}
			}
		}
	}
	
	
	private void moveElementsUpLeft(List<BoardElem> elements, KeyboardKey direction) {
		for (int i = 0; i < board.length; i++) {
			moveIterator(elements, direction, i);
		}
	}
	
	private void moveElementsDownRight(List<BoardElem> elements, KeyboardKey direction) {
		for (int i = board.length-1; i >= 0; i--) {
			moveIterator(elements, direction, i);
		}
	}

	/*
	 * D�place tous les �l�ments jou�s puis renvoi un bool�en pour indiquer si la
	 * partie est gagn�e.
	 */
	public void moveElements(KeyboardKey direction) {
		ArrayList<BoardElem> elements = playedElements();
		if (direction.equals(KeyboardKey.DOWN) || direction.equals(KeyboardKey.RIGHT)) {
			moveElementsDownRight(elements, direction);
		} else {
			moveElementsUpLeft(elements, direction);
		}
		updateProperties();
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
		rules.clear();
		for (int i = 0; i < board.length; i++) {
			for (BoardElem be : board[i]) {
				if (be instanceof Name) {
					Property[] properties = checkProperties(i, (Name) be);
					var l = rules.get(((Name) be).equivalent());
					ArrayList<Property> list = l != null ? l : new ArrayList<>();
					insertProperties(list, properties);
					rules.put(((Name) be).equivalent(), list);
				}
			}
		}
		checkToDestroy();
	}

	public void drawBoard(Graphics graph, Graphics2D context, float width, float height) throws IOException {
		for (int i = 0; i < board.length; i++) {
			for (BoardElem be : board[i]) {
				graph.drawImage(context, this, width, height, i % getColumn(), i / getColumn(), be);
			}
		}
	}
}
