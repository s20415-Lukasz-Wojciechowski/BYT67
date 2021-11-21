package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		//fail("Write test case here");
		assertEquals("Nordea",Nordea.getName());
		assertEquals("DanskeBank",DanskeBank.getName());
		assertNotEquals("BANK",Nordea.getName());

	}

	@Test
	public void testGetCurrency() {
		//fail("Write test case here");
		assertEquals(SEK ,Nordea.getCurrency());
		assertNotEquals(DKK ,Nordea.getCurrency());
		assertEquals(SEK,SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("LOL");
		assertEquals(0,SweBank.getBalance("Bob").intValue());
		assertEquals(0,SweBank.getBalance("LOL").intValue());
		//fail("Write test case here");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertEquals(0,SweBank.getBalance("Bob").intValue());
		SweBank.deposit("Bob",new Money(10,SEK));
		assertEquals(10,SweBank.getBalance("Bob").intValue());

		Nordea.deposit("Bob",new Money(15,SEK));
		assertEquals(10,SweBank.getBalance("Bob").intValue());
		assertEquals(15,Nordea.getBalance("Bob").intValue());
		//fail("Write test case here");
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {

		SweBank.deposit("Bob",new Money(100,SEK));
		assertEquals(100,SweBank.getBalance("Bob").intValue());

		SweBank.withdraw("Bob",new Money(80,SEK));
		assertEquals(20,SweBank.getBalance("Bob").intValue());

		SweBank.withdraw("Bob",new Money(10,SEK));
		assertEquals(10,SweBank.getBalance("Bob").intValue());
		//	fail("Write test case here");
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		//fail("Write test case here");
		assertEquals((Integer)0 , SweBank.getBalance("Bob"));

		SweBank.deposit("Bob",new Money(100,SEK));
		assertEquals(100,SweBank.getBalance("Bob").intValue());

		SweBank.withdraw("Bob",new Money(80,SEK));
		assertEquals(20,SweBank.getBalance("Bob").intValue());
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		//fail("Write test case here");
		SweBank.deposit("Bob",new Money(100,SEK));
		assertEquals(100,SweBank.getBalance("Bob").intValue());

		SweBank.transfer("Bob",Nordea,"Bob",new Money(10,SEK));
		assertEquals(90,SweBank.getBalance("Bob").intValue());
		assertEquals(10,Nordea.getBalance("Bob").intValue());

	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//fail("Write test case here");
		Nordea.addTimedPayment("Bob","1",123,32,new Money(123,SEK),SweBank,"Bob");

	}
}
