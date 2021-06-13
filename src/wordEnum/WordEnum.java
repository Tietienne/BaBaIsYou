package wordEnum;

/**
 * Enumeration of all existing Elements and Blocks associated with a letter/symbol, the TypeEnum, the name as String
 * @author Etienne
 * @version 1.0
 */
public enum WordEnum {
	/**
	 * Property You
	 */
	You('Y', TypeEnum.Property, "You"),
	/**
	 * Property Win
	 */
	Win('!', TypeEnum.Property, "Win"),
	/**
	 * Property Stop
	 */
	Stop('S', TypeEnum.Property, "Stop"),
	/**
	 * Property Push
	 */
	Push('P', TypeEnum.Property, "Push"),
	/**
	 * Property Melt
	 */
	Melt('M', TypeEnum.Property, "Melt"),
	/**
	 * Property Hot
	 */
	Hot('H', TypeEnum.Property, "Hot"),
	/**
	 * Property Defeat
	 */
	Defeat('D', TypeEnum.Property, "Defeat"),
	/**
	 * Property Sink
	 */
	Sink('N', TypeEnum.Property, "Sink"),
	/**
	 * Property Drunk
	 */
	Drunk('#', TypeEnum.Property, "Drunk"),
	
	/**
	 * Operator Is
	 */
	Is('I', TypeEnum.Operator, "Is"),
	
	/**
	 * PlayableElem Baba
	 */
	Baba('B', TypeEnum.Name, "Baba"),
	/**
	 * PlayableElem Flag
	 */
	Flag('F', TypeEnum.Name, "Flag"),
	/**
	 * PlayableElem Wall
	 */
	Wall('W', TypeEnum.Name, "Wall"),
	/**
	 * PlayableElem Water
	 */
	Water('A', TypeEnum.Name, "Water"),
	/**
	 * PlayableElem Skull
	 */
	Skull('&', TypeEnum.Name, "Skull"),
	/**
	 * PlayableElem Lava
	 */
	Lava('L', TypeEnum.Name, "Lava"),
	/**
	 * PlayableElem Rock
	 */
	Rock('R', TypeEnum.Name, "Rock"),
	/**
	 * PlayableElem Brain
	 */
	Brain('%', TypeEnum.Name, "Brain"),
	
	/**
	 * Name Block Baba
	 */
	baba('b', TypeEnum.PlayableElem, "baba"),
	/**
	 * Name Block Flag
	 */
	flag('d', TypeEnum.PlayableElem, "flag"),
	/**
	 * Name Block Rock
	 */
	rock('r', TypeEnum.PlayableElem, "rock"),
	/**
	 * Name Block Wall
	 */
	wall('w', TypeEnum.PlayableElem, "wall"),
	/**
	 * Name Block Water
	 */
	water('~', TypeEnum.PlayableElem, "water"),
	/**
	 * Name Block Skull
	 */
	skull('s', TypeEnum.PlayableElem, "skull"),
	/**
	 * Name Block Lava
	 */
	lava('l', TypeEnum.PlayableElem, "lava"),
	/**
	 * Name Block Floor
	 */
	floor('f', TypeEnum.PlayableElem, "floor"),
	/**
	 * Name Block Grass
	 */
	grass('g', TypeEnum.PlayableElem, "grass"),
	/**
	 * Name Block Brain
	 */
	brain('*', TypeEnum.PlayableElem, "brain");
	
	
	/**
	 * The char representing the Element/Block in a .txt file
	 */
	private final char fileStr;
	/**
	 * The TypeEnum of the Element/Block
	 * @see TypeEnum
	 */
	private final TypeEnum type;
	/**
	 * The name as a String represented in the Board
	 */
	private final String boardStr;
	
	WordEnum(char str, TypeEnum type, String name){
		this.fileStr = str;
		this.type = type;
		this.boardStr = name;
	}

	/**
	 * Return the name of the block
	 * @return String name
	 */
	public String getBoardStr() {
		return boardStr;
	}

	/**
	 * Return the character used in the file to represent it
	 * @return Char in the file
	 */
	public char getFileStr() {
		return fileStr;
	}

	/**
	 * Return the block's type
	 * @return TypeEnum
	 * @see TypeEnum
	 */
	public TypeEnum getType() {
		return type;
	}
	
	/**
	 * Method to return the WordEnum equivalent from a String
	 * 
	 * @param s String
	 * @return WordEnum if the equivalent exists or null if not
	 */
	public static WordEnum equivalent(String s) {
		for (WordEnum wordenum : WordEnum.values()) {
			if (wordenum.getBoardStr().equals(s)) {
				return wordenum;
			}
		}
		return null;
	}

}
