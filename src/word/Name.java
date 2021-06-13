package word;

import wordEnum.NameEnum;
import wordEnum.PlayableEnum;

/**
 * Class who represents Name blocks who are representing PlayableElem
 * @author Etienne
 * @version 1.0
 */
public class Name extends BoardElem {

	/**
	 * Method to create a Name with a NameEnum
	 * @param name NameEnum
	 * @see NameEnum
	 */
	public Name(NameEnum name) {
		super(name.toString());
	}
	
	/**
	 * Method to get the name without the "_blabla" behind
	 * @return String name in lower case
	 */
	private String justName() {
		String s = getName();
		return s.split("_")[0].toLowerCase();
	}
	
	/**
	 * Method to get the equivalent as PlayableElem
	 * @return PlayableElem
	 * @see PlayableElem
	 */
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
