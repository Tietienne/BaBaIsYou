package word;

import wordEnum.OperatorEnum;

/**
 * Class who represents blocks of Operator
 * @author Etienne
 * @version 1.0
 */
public class Operator extends BoardElem {

	/**
	 * Method to create an Operator with an OperatorEnum
	 * @param name OperatorEnum
	 * @see OperatorEnum
	 */
	public Operator(OperatorEnum name) {
		super(name.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Operator)) {
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
