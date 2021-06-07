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
	Sink('N', TypeEnum.Property, "Sink"),
	
	//Operator
	Is('I', TypeEnum.Operator, "Is"),
	
	//Element
	Baba('B', TypeEnum.Name, "Baba"),
	Flag('F', TypeEnum.Name, "Flag"),
	Wall('W', TypeEnum.Name, "Wall"),
	Water('A', TypeEnum.Name, "Water"),
	Skull('&', TypeEnum.Name, "Skull"),
	Lava('L', TypeEnum.Name, "Lava"),
	Rock('R', TypeEnum.Name, "Rock"),
	
	//Bloc
	baba('b', TypeEnum.PlayableElem, "baba"),
	flag('d', TypeEnum.PlayableElem, "flag"),
	rock('r', TypeEnum.PlayableElem, "rock"),
	wall('w', TypeEnum.PlayableElem, "wall"),
	water('~', TypeEnum.PlayableElem, "water"),
	skull('s', TypeEnum.PlayableElem, "skull"),
	lava('l', TypeEnum.PlayableElem, "lava"),
	floor('f', TypeEnum.PlayableElem, "floor"),
	grass('g', TypeEnum.PlayableElem, "grass");
	
	
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
