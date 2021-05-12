package other;

import java.util.Objects;

import word.Name;
import word.Operator;
import word.Property;

public class Rules {
	private Name n;
	private Operator op;
	private Property p;
	
	public Rules(Name n, Operator op, Property p) {
		Objects.requireNonNull(n);
		Objects.requireNonNull(op);
		Objects.requireNonNull(p);
		this.n = n;
		this.op = op;
		this.p = p;
	}
}
