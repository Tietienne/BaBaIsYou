package word;

import wordEnum.PlayableEnum;

public class PlayableElem extends BoardElem {

	public PlayableElem(PlayableEnum name) {
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
