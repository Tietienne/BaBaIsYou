package word;

import wordEnum.NameEnum;

public class Name extends BoardElem {

	public Name(NameEnum name) {
		super(name.toString());
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
