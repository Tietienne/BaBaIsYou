package word;

import wordEnum.PropertyEnum;

public class Property extends BoardElem {

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
