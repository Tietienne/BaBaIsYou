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

/**
 * Board is the class representing the game board.
 * <br>
 * It is made up of:
 * An array of ArrayList of BoardElem.
 * An integer representing the length of a row (the number of columns).
 * A HashMap with PlayableElem : ArrayList of Property. This represents all the rules present in the current level.
 * <br>
 * This class includes some methods to create, operate, modify this board and finish a level.
 * 
 * @see BoardElem
 * @see PlayableElem
 * @see Property
 * 
 * @author Etienne
 * @version 1.0
 */
public class Board {
	/**
	 * The board: an array of ArrayList from BoardElem.
	 * 
	 * @see BoardElem
	 */
	private final ArrayList<BoardElem> board[];
	
	/**
	 * The integer representing the length of a line on the board.
	 */
	private final int lineLength;
	
	/**
	 * The set of rules associated with the board: a HashMap with PlayableElem : ArrayList of Property.
	 * 
	 * @see PlayableElem
	 * @see Property
	 */
	private final HashMap<PlayableElem, ArrayList<Property>> rules = new HashMap<>();
	
	/**
	 * The set of permanent rules used for cheating: a HashMap with PlayableElem : ArrayList of Property.
	 */
	private final HashMap<PlayableElem, ArrayList<Property>> cheatRules = new HashMap<>();

	/**
	 * Method for creating the game board.
	 * 
	 * @param board The array representing the level.
	 * @param lineLength The length of a row in the board.
	 */
	public Board(ArrayList<BoardElem> board[], int lineLength) {
		Objects.requireNonNull(board, "Le plateau ne peut pas �tre null");

		if (lineLength == 0 || board.length == 0)
			throw new IllegalArgumentException("Les lignes ou les colonnes ne peuvent pas �tre null.");

		this.board = board;
		this.lineLength = lineLength;
		updateProperties();
	}

	/**
	 * Returns the number of columns on the board.
	 * 
	 * @return The number of columns on the board.
	 */
	public int getColumn() {
		return lineLength;
	}

	/**
	 * Returns the number of rows on the board.
	 * 
	 * @return The number of rows on the board.
	 */
	public int getLine() {
		return board.length / lineLength;
	}

	/**
	 * Indicates if the game is over (lose).
	 * 
	 * @return A boolean of the state of the game (true : over, false : not over).
	 */
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
	
	/**
	 * Use getToDestroy() to get Elements and then destroy them.
	 * 
	 * @see Board#getToDestroy()
	 */
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
	
	/**
	 * Return an ArraList with all the elements who need to be destroyed (if they have contradictory properties).
	 * For example : Hot and Melt.
	 * 
	 * @return An ArrayList of BoardElem of Elems who need to be destroy.
	 */
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
	
	/**
	 * Check if there is one entity of BoardElem e in the board.
	 * 
	 * @param e BoardElem
	 * @return Boolean : true if exists, false if not
	 */
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
	
	/**
	 * Indicates if you can push the BoardElem be.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if pushable, false if not
	 */
	private boolean isPushable(BoardElem be) {
		List<Property> r = rules.get(be);
		if (r == null) {
			return !(be instanceof PlayableElem);
		}
		return r.contains(new Property(PropertyEnum.Push)) || r.contains(new Property(PropertyEnum.You))
				|| !(be instanceof PlayableElem);
	}

	/**
	 * Indicates if the BoardElem be is disabled (if it don't own properties).
	 * 
	 * @param be BoardElem.
	 * @return Boolean : true if disabled, false if active.
	 */
	private boolean isDisabled(BoardElem be) {
		List<Property> r = rules.get(be);
		return (r == null || r.size()==0) && be instanceof PlayableElem;
	}
	
	/**
	 * Add all the BoardElem who owns the Property prop.
	 * 
	 * @param list ArrayList of BoardElem
	 * @param prop PropertyEnum
	 * @see PropertyEnum
	 */
	private void addElements(ArrayList<BoardElem> list, PropertyEnum prop) {
		for (BoardElem be : rules.keySet()) {
			for (Property p : rules.get(be)) {
				if (p.equals(new Property(prop)) && elementExists(be)) {
					list.add(be);
				}
			}
		}
	}
	
	/**
	 * Returns all the BoardElem who owns the Property : Hot
	 * 
	 * @return ArrayList of Hot BoardElem
	 */
	private ArrayList<BoardElem> hotElements() {
		ArrayList<BoardElem> hot = new ArrayList<>();
		addElements(hot, PropertyEnum.Hot);
		return hot;
	}
	
	/**
	 * Returns all the BoardElem who owns the Property : Melt
	 * 
	 * @return ArrayList of Melt BoardElem
	 */
	private ArrayList<BoardElem> meltElements() {
		ArrayList<BoardElem> melt = new ArrayList<>();
		addElements(melt, PropertyEnum.Melt);
		return melt;
	}
	
	/**
	 * Returns all the BoardElem who owns the Property : Sink
	 * 
	 * @return ArrayList of Sink BoardElem
	 */
	private ArrayList<BoardElem> sinkElements() {
		ArrayList<BoardElem> sinks = new ArrayList<>();
		addElements(sinks, PropertyEnum.Sink);
		return sinks;
	}

	/**
	 * Returns all the BoardElem who owns the Property : You
	 * 
	 * @return ArrayList of You BoardElem
	 */
	private ArrayList<BoardElem> playedElements() {
		ArrayList<BoardElem> played = new ArrayList<>();
		addElements(played, PropertyEnum.You);
		return played;
	}
	
	/**
	 * Returns all the BoardElem who owns the Property : Defeat
	 * 
	 * @return ArrayList of Defeat BoardElem
	 */
	private ArrayList<BoardElem> defeatElements() {
		ArrayList<BoardElem> loser = new ArrayList<>();
		addElements(loser, PropertyEnum.Defeat);
		return loser;
	}
	
	/**
	 * Returns all the BoardElem who owns the Property : Drunk
	 * 
	 * @return ArrayList of Drunk BoardElem
	 */
	private ArrayList<BoardElem> drunkElements() {
		ArrayList<BoardElem> drunk = new ArrayList<>();
		addElements(drunk, PropertyEnum.Drunk);
		return drunk;
	}
	
	/**
	 * Returns all the BoardElem who owns the Property : Win
	 * 
	 * @return ArrayList of Win BoardElem
	 */
	private ArrayList<BoardElem> winElements() {
		ArrayList<BoardElem> winner = new ArrayList<>();
		addElements(winner, PropertyEnum.Win);
		return winner;
	}
	
	/**
	 * Indicates if the BoardElem owns the You property.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if it's you, false if not
	 * @see Board#playedElements()
	 */
	private boolean isYou(BoardElem be) {
		return playedElements().contains(be);
	}
	
	/**
	 * Indicates if the BoardElem owns the Defeat property.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if it's a Defeat BoardElem, false if not
	 * @see Board#defeatElements()
	 */
	private boolean isDefeat(BoardElem be) {
		return defeatElements().contains(be);
	}
	
	/**
	 * Indicates if the BoardElem owns the Melt property.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if it's a Melt BoardElem, false if not
	 * @see Board#meltElements()
	 */
	private boolean isMelt(BoardElem be) {
		return meltElements().contains(be);
	}
	
	/**
	 * Indicates if the BoardElem owns the Hot property.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if it's a Hot BoardElem, false if not
	 * @see Board#hotElements()
	 */	
	private boolean isHot(BoardElem be) {
		return hotElements().contains(be);
	}
	
	/**
	 * Indicates if the BoardElem owns the Sink property.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if it's a Sink BoardElem, false if not
	 * @see Board#sinkElements()
	 */
	private boolean isSink(BoardElem be) {
		return sinkElements().contains(be);
	}
	
	/**
	 * Indicates if the BoardElem owns the Drunk property.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if it's a Drunk BoardElem, false if not
	 * @see Board#sinkElements()
	 */
	private boolean isDrunk(BoardElem be) {
		return drunkElements().contains(be);
	}
	
	/**
	 * Indicates if the BoardElem owns the Win property.
	 * 
	 * @param be BoardElem
	 * @return Boolean : true if it's a Win BoardElem, false if not
	 * @see Board#winElements()
	 */
	private boolean isWin(BoardElem be) {
		return winElements().contains(be);
	}
	
	/**
	 * Translate the direction with a position and then return the new position
	 * 
	 * @param direction The direction as a KeyBoardKey.
	 * @param previousPosition The position before the movement occurs.
	 * @return The new Position in the board.
	 * @see KeyboardKey
	 */
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
	
	/**
	 * Method to return the opposite direction.
	 * 
	 * @param direction Direction as KeyboardKey
	 * @return The opposite direction as KeyboardKey
	 * @see KeyboardKey
	 */
	private KeyboardKey oppositeDirection(KeyboardKey direction) {
		if (direction.equals(KeyboardKey.LEFT)) {
			return KeyboardKey.RIGHT;
		}
		if (direction.equals(KeyboardKey.RIGHT)) {
			return KeyboardKey.LEFT;
		}
		if (direction.equals(KeyboardKey.UP)) {
			return KeyboardKey.DOWN;
		}
		if (direction.equals(KeyboardKey.DOWN)) {
			return KeyboardKey.UP;
		}
		throw new IllegalArgumentException("Mauvaise touche prise en compte !");
	}
	
	/**
	 * Indicates if a case in board contains two (or one) BoardElem who are in both lists given as parameters. 
	 * 
	 * @param list1 ArrayList of BoardElem
	 * @param list2 ArrayList of BoardElem
	 * @return Boolean : true if it exists a case in board with two (or one) BoardElem who are in both lists, false if not.
	 */
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
	
	/**
	 * Indicates if a You BoardElem is at the same place than a Win BoardElem. (So if the game is win).
	 * 
	 * @return Boolean : true if You BoardElem is at the same position than Win BoardElem, false if not
	 * @see Board#containsSomething(ArrayList, ArrayList)
	 */
	public boolean win() {
		ArrayList<BoardElem> player = playedElements();
		ArrayList<BoardElem> winner = winElements();
		return containsSomething(player, winner);
	}
	
	/**
	 * Check all possible conditions / relations between two BoardElem
	 * 
	 * @param direction Direction as KeyBoardKey
	 * @param w BoardElem to move
	 * @param i Actual Position of BoardElem w
	 * @param itPrev Iterator of BoardElem w
	 * @param be BoardElem be
	 * @param newPosition Position of BoardElem be
	 * @param it Iterator of BoardElem be
	 * @return String with 3 possibility : "true" if it can move but won't move w, "continue" if it can move and will move w aswell, false if it can't move
	 * @see Board#checkMoveRec(KeyboardKey, BoardElem, int, Iterator)
	 */
	private String checkConditions(KeyboardKey direction, BoardElem w, int i, Iterator<BoardElem> itPrev,
			BoardElem be, int newPosition, Iterator<BoardElem> it) {
		if (isWin(be) && isYou(w) && !isDrunk(w)) {
			return "continue";
		}
		
		if (isPushable(be)) {
			if (!checkMoveRec(direction, be, newPosition, it)) {
				return "false";
			}
			return "continue";
		}
		
		if (isSink(be) || isSink(w)) {
			it.remove();
			itPrev.remove();
			return "true";
		}
		
		if (isHot(be) && isMelt(w)) {
			itPrev.remove();
			return "true";
		}
		
		if (isHot(w) && isMelt(be)) {
			it.remove();
			return "continue";
		}
		
		if (isDefeat(be)) {
			if (isYou(w)) {
				itPrev.remove();
				return "true";
			}
			return "continue";
		}
		return "false";
	}

	/**
	 * Recursive method used to move recursively BoardElem.
	 * If the method returns true, the BoardElem will move.
	 * It won't if it returns false.
	 * 
	 * @param direction Direction as KeyBoardKey
	 * @param w BoardElem to move
	 * @param i Actual Position of BoardElem w
	 * @param itPrev Iterator of BoardElem w
	 * @return Boolean : true if it can move, false if not.
	 * @see KeyBoardKey
	 * @see Iterator
	 */
	private boolean checkMoveRec(KeyboardKey direction, BoardElem w, int i, Iterator<BoardElem> itPrev) {
		int newPosition = translateDirection(direction, i);
		try {
			if(i == newPosition) return false;
			for (Iterator<BoardElem> it = board[newPosition].iterator(); it.hasNext();) {
				BoardElem be = it.next();
				if (!isDisabled(be)) {
					switch (checkConditions(direction, w, i, itPrev, be, newPosition, it)) {
						case "true" : return true;
						case "continue" : continue;
						case "false" : return false;
					}
				}

			}
			itPrev.remove();
			board[newPosition].add(w);
		} catch(StackOverflowError e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Method to move BoardElem if it's include in the list of BoardElem in a direction as an Iterator.
	 * 
	 * @param elements ArrayList of BoardElem to move
	 * @param direction Direction as a KeyBoardKey
	 * @param i Position in the board
	 * @see Board#moveElementsUpLeft(List, KeyboardKey)
	 */
	private void moveIterator(List<BoardElem> elements, KeyboardKey direction, int i) {
		for (Iterator<BoardElem> it = board[i].iterator(); it.hasNext();) {
			BoardElem be = it.next();
			if (elements.contains(be)) {
				checkMoveRec(direction, be, i, it);
			}
		}
	}
	
	/**
	 * Method to move elements up or left.
	 * 
	 * @param elements ArrayList of BoardElem to move
	 * @param direction Direction as a KeyBoardKey
	 * @see Board#moveElements(KeyboardKey)
	 */
	private void moveElementsUpLeft(List<BoardElem> elements, KeyboardKey direction) {
		for (int i = 0; i < board.length; i++) {
			moveIterator(elements, direction, i);
		}
	}
	
	/**
	 * Method to move elements down or right.
	 * 
	 * @param elements ArrayList of BoardElem to move
	 * @param direction Direction as a KeyBoardKey
	 * @see Board#moveElements(KeyboardKey)
	 */
	private void moveElementsDownRight(List<BoardElem> elements, KeyboardKey direction) {
		for (int i = board.length-1; i >= 0; i--) {
			moveIterator(elements, direction, i);
		}
	}
	
	/**
	 * Method who choose the right method to move all elements depending of the direction.
	 * 
	 * @param direction Direction as KeyboardKey
	 * @param elements ArrayList of all elements to move
	 * @see Board#moveElements(KeyboardKey)
	 */
	private void chooseDirectionAndMove(KeyboardKey direction, ArrayList<BoardElem> elements) {
		if (direction.equals(KeyboardKey.DOWN) || direction.equals(KeyboardKey.RIGHT)) {
			moveElementsDownRight(elements, direction);
		} else {
			moveElementsUpLeft(elements, direction);
		}
	}

	/**
	 * Method to move all the You BoardElem in a direction and then use updateProperties()
	 * 
	 * @param direction Direction as a KeyBoardKey
	 * @see Board#updateProperties()
	 */
	public void moveElements(KeyboardKey direction) {
		ArrayList<BoardElem> elements = playedElements();
		ArrayList<BoardElem> drunk = new ArrayList<>();
		for (Iterator<BoardElem> it = elements.iterator(); it.hasNext();) {
			BoardElem be = it.next();
			if (isDrunk(be)) {
				drunk.add(be);
				it.remove();
			}
		}
		chooseDirectionAndMove(direction, elements);
		if (drunk.size()!=0) {
			chooseDirectionAndMove(oppositeDirection(direction), drunk);
		}
		updateProperties();
	}

	/**
	 * Method to insert properties Property in a list.
	 * 
	 * @param list ArrayList of Property
	 * @param properties One or more Property to add in the list
	 */
	private void insertProperties(ArrayList<Property> list, Property... properties) {
		for (Property p : properties) {
			if (p != null && !(list.contains(p))) {
				list.add(p);
			}
		}
	}
	
	/**
	 * Method to transform every PlayableElem of the board in an other one.
	 * 
	 * @param origin BoardElem as Name who will be transformed
	 * @param toTransform BoardElem as Name who will be used to transform the other one
	 * @see Name#equivalent()
	 */
	private void transformElements(Name origin, Name toTransform) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j<board[i].size(); j++) {
				if (board[i].get(j).equals(origin.equivalent())) {
					board[i].set(j, toTransform.equivalent());
				}
			}
		}
	}

	/**
	 * Check if a rule is present on the board in column.
	 * Also use transformElements() if it's not a Property but a Name who is found.
	 * 
	 * @param index Position in board
	 * @param n Name of the rule who is checked
	 * @return Property if it exists, null if not
	 * @see Board#transformElements(Name, Name)
	 */
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
	
	/**
	 * Check if a rule is present on the board in line.
	 * Also use transformElements() if it's not a Property but a Name who is found.
	 * 
	 * @param index Position in board
	 * @param n Name of the rule who is checked
	 * @return Property if it exists, null if not
	 * @see Board#transformElements(Name, Name)
	 */
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

	/**
	 * Check the rules in line and in column.
	 * 
	 * @param index Position in board to check
	 * @param n Name of the rules who are checked
	 * @return an array of Property (or null) of size 2
	 * @see Board#checkColumnDown(int, Name)
	 * @see Board#checkLineRight(int, Name)
	 */
	private Property[] checkProperties(int index, Name n) {
		Property[] properties = new Property[2];
		Objects.checkIndex(index, board.length);
		properties[0] = checkLineRight(index, n);
		properties[1] = checkColumnDown(index, n);
		return properties;
	}
	
	/**
	 * Method to add a Property in the cheatRules HashMap.
	 * 
	 * @param name Name of BoardElem
	 * @param p Property
	 */
	public void addCheatProperty(Name name, Property p) {
		var list = cheatRules.getOrDefault(name.equivalent(), new ArrayList<Property>());
		list.add(p);
		cheatRules.put(name.equivalent(), list);
	}

	/**
	 * Update all the rules in the board.
	 * Clear the rules HashMap and then fill it if the program finds any rules.
	 * 
	 * @see Board#checkProperties(int, Name)
	 */
	private void updateProperties() {
		rules.clear();
		cheatRules.forEach((key, value) -> rules.merge(key, value, (oldValue, newValue) -> { return oldValue;}));
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
	
	

	/**
	 * Method to draw the board. Uses the Graphics class.
	 *  
	 * @param graph Instance of Graphics class
	 * @param context Graphics2D context
	 * @param width	Width of the window
	 * @param height Height of the window
	 * @throws IOException If problems occurs with reading of pictures
	 * @see Graphics
	 * @see Graphics2D
	 */
	public void drawBoard(Graphics graph, Graphics2D context, float width, float height) throws IOException {
		for (int i = 0; i < board.length; i++) {
			for (BoardElem be : board[i]) {
				graph.drawImage(context, this, width, height, i % getColumn(), i / getColumn(), be);
			}
		}
	}
	
	/**
	 * Method to initialize images of all BoardElem. Uses the Graphics class.
	 * 
	 * @param graph Instance of Graphics class
	 * @throws IOException If problems occurs with reading of pictures
	 * @see Graphics
	 */
	public void initializeImages(Graphics graph) throws IOException {
		for (int i = 0; i < board.length; i++) {
			for (BoardElem be : board[i]) {
				graph.initializeImage(be);
			}
		}
	}
}
