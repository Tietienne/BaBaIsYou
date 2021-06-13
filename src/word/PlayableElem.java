package word;

import wordEnum.PlayableEnum;

/**
 * Class who represents PlayableElem
 * @author Etienne
 * @version 1.0
 */
public class PlayableElem extends BoardElem {

	/**
	 * Method to create a PlayableElem with a PlayableEnum
	 * @param name PlayableEnum
	 * @see PlayableEnum
	 */
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
