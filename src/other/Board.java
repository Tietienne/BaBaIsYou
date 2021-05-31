package other;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;
import graphics.Graphics;
import word.Name;
import word.Operator;
import word.Property;
import wordEnum.OperatorEnum;
import wordEnum.PropertyEnum;
import word.BoardElem;

public class Board {
	private final ArrayList<BoardElem> board[];
	private final int lineLength;
	private final HashMap<BoardElem, ArrayList<Property>> rules = new HashMap<>();
	
	public Board(ArrayList<BoardElem> board[], int lineLength) {
		Objects.requireNonNull(board, "Le plateau ne peut pas ï¿½tre null");
		
		if(lineLength == 0)
			throw new IllegalArgumentException();
		
		
		this.board = board;
		this.lineLength = lineLength;
		updateProperties();
	}
	
	public void printRules() {
		for (BoardElem be : rules.keySet()) {
			System.out.print(be+" : ");
			for (Property p : rules.get(be)) {
				System.out.print(p+ " ");
			}
			System.out.println();
		}
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

	public HashMap<BoardElem, ArrayList<Property>> getRules() {
		return rules;
	}
	
	public ArrayList<BoardElem> getElems(int i, int j) {
		return board[i+j*lineLength];
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
		for (int i=0; i<board.length; i++) {
			for (BoardElem be : board[i]) {
				if (be.equals(e)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isDisabled(BoardElem be) {
		return rules.get(be).size()==0;
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
			case UP : newPosition = previousPosition%lineLength==0 ? previousPosition : previousPosition-1; break;
			case DOWN : newPosition = previousPosition%lineLength==lineLength-1 ? previousPosition : previousPosition+1; break;
			case LEFT : newPosition = Math.max(0, previousPosition-lineLength); break;
			case RIGHT : newPosition = Math.min(board.length, previousPosition+lineLength); break;
			default : throw new IllegalArgumentException("Mauvaise touche prise en compte !");
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
	
	private void changeWordsPlace(List<BoardElem> toMoveElem, List<Integer> toMovePrevPos, List<Integer> toMoveNextPos) {
		for (int i=0; i<toMoveElem.size(); i++) {
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
	
	/*
	 * Dï¿½place tous les ï¿½lï¿½ments jouï¿½s puis renvoi un boolï¿½en pour indiquer si la partie est gagnï¿½e.
	 */
	public boolean moveElements(KeyboardKey direction) {
		ArrayList<BoardElem> elements = playedElements();
		ArrayList<BoardElem> toMoveElem = new ArrayList<>();
		ArrayList<Integer> toMovePrevPos = new ArrayList<>();
		ArrayList<Integer> toMoveNextPos = new ArrayList<>();
		for (int i=0;i<board.length;i++) {
			for (BoardElem w : board[i]) {
				if (elements.contains(w)) {
					// Move element from position [i][j]
					int newPosition = translateDirection(direction, i);
					// Si obstacle ou autre impossibilitï¿½ de se dï¿½placer : mettre les conditions et vï¿½rifications ici (avant le dï¿½placement)
					if (win(board[newPosition])) {
						return true; // Partie gagnï¿½e
					}
					// Dï¿½placement à faire : on garde en mémoire
					keepMemoryToMove(toMoveElem, toMovePrevPos, toMoveNextPos, w, i, newPosition);
				}
			}
		}
		changeWordsPlace(toMoveElem, toMovePrevPos, toMoveNextPos);
		return false;
	}
	
	private int[] getIndexAround(int index) {
		int[] tab = new int[4]; // [UP, RIGHT, DOWN, LEFT]
		tab[0] = index-lineLength<0 ? null : index-lineLength;
		tab[1] = index%lineLength==0 ? null : index-1;
		tab[2] = index+lineLength>board.length ? null : index+lineLength;
		tab[3] = index%lineLength==lineLength-1 ? null : index+1;
		return tab;
	}
	
	private void insertProperties(ArrayList<Property> list, Property... properties) {
		for (Property p : properties) {
			if (p!=null && !(list.contains(p))) {
				list.add(p);
			}
		}
	}
	
	private Property checkColumnUp(int index) {
		if (index-lineLength>=0) {
			for (BoardElem be : board[index-lineLength]) {
				if (be.equals(new Operator(OperatorEnum.Is))) {
					if (index-lineLength-lineLength>=0) {
						for (BoardElem be2 : board[index-lineLength-lineLength]) {
							if (be2 instanceof Property) {
								return (Property) be2;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private Property checkColumnDown(int index) {
		if (index+lineLength<board.length) {
			for (BoardElem be : board[index+lineLength]) {
				if (be.equals(new Operator(OperatorEnum.Is))) {
					if (index+lineLength+lineLength<board.length) {
						for (BoardElem be2 : board[index+lineLength+lineLength]) {
							if (be2 instanceof Property) {
								return (Property) be2;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private Property checkLineLeft(int index) {
		if (index-1>=0) {
			for (BoardElem be : board[index-1]) {
				if (be.equals(new Operator(OperatorEnum.Is))) {
					if (index-1-1>=0) {
						for (BoardElem be2 : board[index-1-1]) {
							if (be2 instanceof Property) {
								return (Property) be2;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private Property checkLineRight(int index) {
		if (index+1<board.length) {
			for (BoardElem be : board[index+1]) {
				if (be.equals(new Operator(OperatorEnum.Is))) {
					if (index+1+1<board.length) {
						for (BoardElem be2 : board[index+1+1]) {
							if (be2 instanceof Property) {
								return (Property) be2;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	
	private Property[] checkProperties(int index) {
		Property[] properties = new Property[4];
		Objects.checkIndex(index, board.length);
		properties[0] = checkColumnUp(index);
		properties[1] = checkLineRight(index);
		properties[2] = checkColumnDown(index);
		properties[3] = checkLineLeft(index);
		return properties;
	}
	
	private void updateProperties() {
		for (int i=0; i<board.length; i++) {
			for (BoardElem be : board[i]) {
				if (be instanceof Name) {
					Property[] properties = checkProperties(i);
					var l = rules.get(be);
					ArrayList<Property> list = l!=null ? l : new ArrayList<>();
					insertProperties(list, properties);
					rules.put(((Name) be).equivalent(), list);
				}
			}
		}
	}
	
	public void drawBoard(Graphics graph, ApplicationContext context, float width, float height) throws IOException {
		for (int i=0; i<board.length; i++) {
			for (BoardElem be : board[i]) {
				graph.drawImage(context, this, width, height, i/lineLength+1, i%lineLength+1, be);
			}
		}	
	}
}
