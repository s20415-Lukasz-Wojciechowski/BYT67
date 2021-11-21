package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	Account testAccountB;


	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.11);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");

		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		testAccountB = new Account("Alice", SEK);
		testAccountB.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));


	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1",0,1,new Money(666,SEK),SweBank,testAccountB.getId());
		assertTrue("Append Timed Payment",testAccount.timedPaymentExists("1"));

		testAccount.removeTimedPayment("1");
		assertFalse("Remove Timed Payment",testAccount.timedPaymentExists("1"));
		//fail("Write test case here");

		testAccountB.addTimedPayment("2",0,1,new Money(777,DKK),SweBank,testAccount.getId());
		assertFalse("Append Timed Payment that does not exist",testAccountB.timedPaymentExists("1"));
		assertTrue("Append Timed Payment that exists",testAccountB.timedPaymentExists("2"));

		testAccountB.removeTimedPayment("2");
		assertFalse("Remove payment",testAccountB.timedPaymentExists("2"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccountB.addTimedPayment("1",0,1,new Money(1389,SEK),Nordea,testAccount.getId());
		testAccountB.addTimedPayment("2",0,2,new Money(2378,SEK),SweBank,testAccount.getId());

		//appended Payments
		assertTrue("Time Payment 1",testAccountB.timedPaymentExists("1"));
		assertTrue("Time Payment 2",testAccountB.timedPaymentExists("2"));
		assertFalse("Time Payment 3",testAccountB.timedPaymentExists("3"));
		//remove Payments
		testAccountB.removeTimedPayment("1");
		assertFalse("Time Payment 1-removed",testAccountB.timedPaymentExists("1"));
		assertTrue("Time Payment 2",testAccountB.timedPaymentExists("2"));
		testAccountB.removeTimedPayment("2");
		assertFalse("Time Payment 2",testAccountB.timedPaymentExists("2"));
	}

	@Test
	public void testAddWithdraw() {

		//fail("Write test case here");
		Money money = new Money(200,DKK);
		Money money2 = new Money(7384,SEK);
		Money money3 = new Money(100,DKK);


		testAccount.deposit(money2);
		assertEquals(Integer.valueOf(10007384),testAccount.getBalance().getAmount());

		testAccount.withdraw(money3);
		assertEquals(Integer.valueOf(10007373),testAccount.getBalance().getAmount());
		System.out.println(testAccount.getBalance());

		testAccount.deposit(money);
		assertEquals(Integer.valueOf(10007395),testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(Integer.valueOf(1000000),testAccountB.getBalance().getAmount());
		assertEquals(SEK,testAccountB.getBalance().getCurrency());

	}
}
