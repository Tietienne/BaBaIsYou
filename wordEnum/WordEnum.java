package wordEnum;

public enum WordEnum {
	//Property
	You('Y', TypeEnum.Property, "You"),
	Win('!', TypeEnum.Property, "Win"),
	Stop('S', TypeEnum.Property, "Stop"),
	Push('P', TypeEnum.Property, "Push"),
	Melt('M', TypeEnum.Property, "Melt"),
	Hot('H', TypeEnum.Property, "Hot"),
	Defeat('D', TypeEnum.Property, "Defeat"),
	Sink('S', TypeEnum.Property, "Sink"),
	
	//Operator
	Is('I', TypeEnum.Operator, "Is"),
	
	//Element
	Baba('B', TypeEnum.Element, "Baba"),
	Flag('F', TypeEnum.Element, "Flag"),
	Wall('W', TypeEnum.Element, "Wall"),
	Water('~', TypeEnum.Element, "Water"),
	Skull('&', TypeEnum.Element, "Skull"),
	Lava('L', TypeEnum.Element, "Lava"),
	Rock('R', TypeEnum.Element, "Rock");
	
	private final char fileStr;
	private final TypeEnum type;
	private final String boardStr;
	
	WordEnum(char str, TypeEnum type, String name){
		this.fileStr = str;
		this.type = type;
		this.boardStr = name;
	}

	public String getBoardStr() {
		return boardStr;
	}

	public char getFileStr() {
		return fileStr;
	}

	public TypeEnum getType() {
		return type;
	}

}
