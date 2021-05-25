package word;

import wordEnum.NameEnum;

public class PlayableElem extends BoardElem {

	public PlayableElem(NameEnum name) {
		super(name.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PlayableElem)) {
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
