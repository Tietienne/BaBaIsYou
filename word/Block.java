package word;

import java.util.Objects;

public class Block {
	private Word w;
	
	public Block(Word w) {
		Objects.requireNonNull(w);
		this.w = w;
	}
}
