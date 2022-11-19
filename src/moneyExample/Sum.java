package moneyExample;

public class Sum implements Expression {
	public Expression augend;
	public Expression addend;
	
	public Sum(Expression augend, Expression addend) {
		this.augend = augend;
		this.addend = addend;
	}

	public Money reduce(Bank bank, String to) {
		int reducedAugend = augend.reduce(bank, to).amount;
		int reducedAddend = addend.reduce(bank, to).amount;
		int amount = reducedAugend + reducedAddend;
		return new Money(amount, to);
	}

	public Expression plus(Expression addend) {
		return new Sum(this, addend);
	}

	public Expression times(int multiplier) {
		return new Sum(augend.times(multiplier),addend.times(multiplier));
	}
	
}
