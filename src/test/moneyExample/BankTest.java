package test.moneyExample;

import static org.junit.jupiter.api.Assertions.*;

import moneyExample.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import moneyExample.Bank.RateNotFoundException;

class BankTest {

	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void testRate() {
		bank.addRate("USD", "GBP", 2);
		int rate = bank.rate("USD", "GBP");
		assertEquals(2, rate);
	}

	@Test
	public void whenSearchingMissingRate_shouldThrowRateNotFoundException() throws Exception {
		assertThrows(RateNotFoundException.class, () -> bank.rate("USD", "DZD"));
	}

}
