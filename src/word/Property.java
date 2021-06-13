package word;

import wordEnum.PropertyEnum;

/**
 * Class who represents blocks of Property
 * @author Etienne
 * @version 1.0
 */
public class Property extends BoardElem {

	/**
	 * Method to create a Property with a PropertyEnum
	 * @param name PropertyEnum
	 * @see PropertyEnum
	 */
	public Property(PropertyEnum name) {
		super(name.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Property)) {
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
