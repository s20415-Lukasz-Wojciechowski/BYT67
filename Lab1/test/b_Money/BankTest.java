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
		//checking if each bank has the expected name.
		assertEquals("Nordea",Nordea.getName());
		assertEquals("DanskeBank",DanskeBank.getName());
		assertNotEquals("BANK",Nordea.getName());

	}

	@Test
	public void testGetCurrency() {
		//checking currency of each bank
		assertEquals(SEK ,Nordea.getCurrency());
		assertNotEquals(DKK ,Nordea.getCurrency());
		assertEquals(SEK,SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		//opening account
		SweBank.openAccount("LOL");
		//Each account has a balance = 0
		//no deposit and no withdraw yet
		assertEquals(0,SweBank.getBalance("Bob").intValue());
		assertEquals(0,SweBank.getBalance("LOL").intValue());
		//fail("Write test case here");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		//Check if account Bob has balance =0
		assertEquals(0,SweBank.getBalance("Bob").intValue());
		//deposit 10 Sek to account/Bank that operates on such a currency
		SweBank.deposit("Bob",new Money(10,SEK));
		//checking validation- deposit has the same currency as bank
		assertEquals(10,SweBank.getBalance("Bob").intValue());

		//Deposit to Bob account from another Bank - not the same Bob
		Nordea.deposit("Bob",new Money(15,SEK));
		//The same ID but banks are different
		assertEquals(10,SweBank.getBalance("Bob").intValue());
		assertEquals(15,Nordea.getBalance("Bob").intValue());

	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {

		//to withdraw it's needed to deposit something firstly
		//Swebank gives bob 100 SEk (this is a banks main currency)
		SweBank.deposit("Bob",new Money(100,SEK));
		assertEquals(100,SweBank.getBalance("Bob").intValue());

		//20=100-80
		SweBank.withdraw("Bob",new Money(80,SEK));
		assertEquals(20,SweBank.getBalance("Bob").intValue());

		//10=20-10
		SweBank.withdraw("Bob",new Money(10,SEK));
		assertEquals(10,SweBank.getBalance("Bob").intValue());

	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		//0
		assertEquals((Integer)0 , SweBank.getBalance("Bob"));
		// Swebank gives Bob 100 SEk (main currency)
		SweBank.deposit("Bob",new Money(100,SEK));
		assertEquals(100,SweBank.getBalance("Bob").intValue());
		//100-80=20
		SweBank.withdraw("Bob",new Money(80,SEK));
		assertEquals(20,SweBank.getBalance("Bob").intValue());
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		//SweBank gives bob 100sek
		SweBank.deposit("Bob",new Money(100,SEK));
		//Checking validation-bob from another bank has a balance=0
		assertEquals(100,SweBank.getBalance("Bob").intValue());

		//Bob from SweBank gives Bob from Nordea 10SEK
		//SWEBank Bob =90Sek , Nordea Bob =10
		//both banks have the same main currency
		SweBank.transfer("Bob",Nordea,"Bob",new Money(10,SEK));
		assertEquals(90,SweBank.getBalance("Bob").intValue());
		assertEquals(10,Nordea.getBalance("Bob").intValue());

	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//adding time payment
		Nordea.addTimedPayment("Bob","1",123,32,new Money(123,SEK),SweBank,"Bob");

	}
}
