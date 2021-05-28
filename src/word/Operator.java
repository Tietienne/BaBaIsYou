package word;

import wordEnum.OperatorEnum;

public class Operator extends BoardElem {

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
