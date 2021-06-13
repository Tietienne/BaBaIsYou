package word;

import java.util.Objects;

/**
 * Abstract class of all elements who can be present in a Board
 * @author Etienne and Guillaume
 * @version 1.0
 */
public abstract class BoardElem {
	/**
	 * The name of the BoardElem
	 */
	private final String name;
	
	/**
	 * Method to create a BoardElem with a String
	 * @param name String
	 */
	public BoardElem(String name) {
		Objects.requireNonNull(name, "Le nom de l'�l�ment du jeu ne peut pas �tre null");
		this.name = name;
	}
	
	/**
	 * Give the name of the BoardElem
	 * @return name String
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardElem)) {
			return false;
		}
		BoardElem b = (BoardElem) obj;
		return Objects.equals(b.name, name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
