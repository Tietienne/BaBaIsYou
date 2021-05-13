package other;

import java.util.Objects;

import word.Element;
import word.Operator;
import word.Property;

public class Rules {
	private Element e;
	private Operator op;
	private Property p;
	
	public Rules(Element e, Operator op, Property p) {
		Objects.requireNonNull(e);
		Objects.requireNonNull(op);
		Objects.requireNonNull(p);
		this.e = e;
		this.op = op;
		this.p = p;
	}
	
	public boolean isYou() {
		return op.getName().equals("Is") && p.getName().equals("You");
	}
	
	public Element getE() {
		return e;
	}
}
