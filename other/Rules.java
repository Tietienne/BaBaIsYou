package other;

import java.util.Objects;

import word.Name;
import word.Operator;
import word.Property;
import wordEnum.OperatorEnum;
import wordEnum.PropertyEnum;

public class Rules {
	private Name n;
	private Operator op;
	private Property p;
	
	public Rules(Name n, Operator op, Property p) {
		Objects.requireNonNull(n, "Le nom ne peut pas �tre null");
		Objects.requireNonNull(op, "L'op�rateur ne peut pas �tre null");
		Objects.requireNonNull(p, "La propri�t� ne peut pas �tre null");
		this.n = n;
		this.op = op;
		this.p = p;
	}
	
	public boolean isYou() {
		return op.equals(new Operator(OperatorEnum.Is)) && p.equals(new Property(PropertyEnum.You));
	}
	
	public Name getN() {
		return n;
	}
	
	public boolean isWin() {
		return op.equals(new Operator(OperatorEnum.Is)) && p.equals(new Property(PropertyEnum.You));
	}
}
