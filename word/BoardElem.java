package word;

import java.util.Objects;

public abstract class BoardElem {
	private final String name;
	
	public BoardElem(String name) {
		Objects.requireNonNull(name, "Le nom de l'élément du jeu ne peut pas être null");
		this.name = name;
	}
	
	public String getName() {
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
