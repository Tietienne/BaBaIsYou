package word;

public abstract class Word {
	private final String name;
	
	public Word(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
