package word;

import wordEnum.NameEnum;
import wordEnum.PlayableEnum;

public class Name extends BoardElem {

	public Name(NameEnum name) {
		super(name.toString());
	}
	
	private String justName() {
		String s = getName();
		return s.split("_")[0].toLowerCase();
	}
	
	public PlayableElem equivalent() {
		return new PlayableElem(PlayableEnum.valueOf(justName()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Name)) {
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
