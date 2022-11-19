package test.moneyExample;


import static org.junit.jupiter.api.Assertions.*;

import moneyExample.Bank;
import moneyExample.Expression;
import moneyExample.Money;
import moneyExample.Sum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Testcase to Money Example - TDD")
public class MoneyTest {

	@Test
	public void testMultiplication() {
		var five = Money.dollar(5);
		assertEquals(Money.dollar(10),five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	}


	@Test
	public void testEquality() throws Exception {
		assertTrue(Money.dollar(5).equals(Money.dollar(5)));
		assertFalse(Money.dollar(5).equals(Money.dollar(6)));

		assertFalse(Money.franc(5).equals(Money.dollar(5)));
	}

	@Test
	public void testCurrency() {
		assertEquals("USD", Money.dollar(1).currency());
		assertEquals("CHF", Money.franc(1).currency());
	}

	@Test
	public void testPlusReturnsSum() {
		var five = Money.dollar(5);
		Expression result = five.plus(five);
		Sum sum = (Sum) result;
		assertEquals(five, sum.augend);
		assertEquals(five, sum.addend);
	}
	
	@Nested
	class MoneyConversionConctext {
		private Bank bank;
		private Expression sum;
		private Money result;

		@BeforeEach
		public void setUp() {
			bank = new Bank();
			bank.addRate("CHF", "USD", 2);
		}

		@Test
		public void testSimpleAddition() {
			var five = Money.dollar(5);
			sum = five.plus(five);
			result = bank.reduce(sum, "USD");
			assertEquals(Money.dollar(10), result);
		}

		@Test
		public void testReduceSum() {
			sum = new Sum(Money.dollar(3), Money.dollar(4));
			result = bank.reduce(sum, "USD");
			assertEquals(Money.dollar(7), result);
		}

		@Test
		public void testReduceMoney() {
			result = bank.reduce(Money.dollar(1), "USD");
			assertEquals(Money.dollar(1), result);
		}

		@Test
		public void testReduceMoneyDifferentCurrency() {
			result = bank.reduce(Money.franc(2), "USD");
			assertEquals(Money.dollar(1), result);
		}

		@Nested
		class MixedMoneyAdditionContext {
			private Expression fiveBucks;
			private Expression tenFrancs;
			
			@BeforeEach
			public void setUp() {
				fiveBucks = Money.dollar(5);
				tenFrancs = Money.franc(10);
				sum = new Sum(fiveBucks, tenFrancs);
			}
			
			@Test
			public void testMixedAddition() {
				result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
				assertEquals(Money.dollar(10), result);
			}

			@Test
			public void testSumPlusMoney() {
				sum = sum.plus(fiveBucks);
				result = bank.reduce(sum, "USD");
				assertEquals(Money.dollar(15), result);
			}

			@Test
			public void testSumTimes() {
				sum = sum.times(2);
				result = bank.reduce(sum, "USD");
				assertEquals(Money.dollar(20), result);
			}
		}
	}

}
