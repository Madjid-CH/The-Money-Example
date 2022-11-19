package moneyExample;

import java.util.HashMap;
import java.util.Objects;

public class Bank {
	private HashMap<Pair, Integer> rates = new HashMap<Pair, Integer>();

	public Money reduce(Expression source, String to) {
		return source.reduce(this, to);
	}
	
	public void addRate(String from, String to, int rate) {
		rates.put(new Pair(from, to), rate);
	}
	
	public int rate(String from, String to) throws RateNotFoundException {
		try {
			return from.equals(to) ? 1 :
				rates.get(new Pair(from, to));
		} catch (Exception e) {
			throw new RateNotFoundException();
		}
	}

	private static class Pair {
		private String from;
		private String to;

		Pair(String from, String to) {
			this.from = from;
			this.to = to;
		}
		
		public int hashCode() {
			return Objects.hash(from, to);
		}

		public boolean equals(Object obj) {
			var other = (Pair) obj;
			return from.equals(other.from) && to.equals(other.to);
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class RateNotFoundException extends RuntimeException {

	}

}
