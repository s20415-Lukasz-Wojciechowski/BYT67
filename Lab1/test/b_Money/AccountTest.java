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
		//the test remove method it's needed to append some values firstly
		testAccount.addTimedPayment("1",0,1,new Money(666,SEK),SweBank,testAccountB.getId());
		//adding a new Payment was performed correctly.
		assertTrue("Append Timed Payment",testAccount.timedPaymentExists("1"));

		//remove the upper payment
		testAccount.removeTimedPayment("1");
		//The payment with id=1 doesn't exist- was deleted
		assertFalse("Remove Timed Payment",testAccount.timedPaymentExists("1"));

		//appending new Payment to another account
		testAccountB.addTimedPayment("2",0,1,new Money(777,DKK),SweBank,testAccount.getId());
		//this account has only one payment with id=2. Because of that such payment (id=1) doesnt exist for it.
		assertFalse("Append Timed Payment that does not exist",testAccountB.timedPaymentExists("1"));
		assertTrue("Append Timed Payment that exists",testAccountB.timedPaymentExists("2"));

		//Now account B doesnt have any payments it means checking a existance of any Payment will return false
		testAccountB.removeTimedPayment("2");
		assertFalse("Remove payment",testAccountB.timedPaymentExists("2"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//appended Payments
		testAccountB.addTimedPayment("1",0,1,new Money(1389,SEK),Nordea,testAccount.getId());
		testAccountB.addTimedPayment("2",0,2,new Money(2378,SEK),SweBank,testAccount.getId());

		//checking payment validation
		//The account has payments with id=1 and id=2 but doesnt have id=3 that's why testAccountB.timedPaymentExists("3") returns false
		assertTrue("Time Payment 1",testAccountB.timedPaymentExists("1"));
		assertTrue("Time Payment 2",testAccountB.timedPaymentExists("2"));
		assertFalse("Time Payment 3",testAccountB.timedPaymentExists("3"));


		//remove Payments
		//removing a payment with id=1
		testAccountB.removeTimedPayment("1");
		//it should return false because we've just deleted payment id=1 - doesnt exist any longer
		assertFalse("Time Payment 1-removed",testAccountB.timedPaymentExists("1"));
		//The payment with id=2 still exists.
		assertTrue("Time Payment 2",testAccountB.timedPaymentExists("2"));
		//removing payment id=2
		testAccountB.removeTimedPayment("2");
		//now the payment id=2 also doesnt exist
		assertFalse("Time Payment 2",testAccountB.timedPaymentExists("2"));
	}

	@Test
	public void testAddWithdraw() {

		//fail("Write test case here");
		//create 3 Money objects with different amount and currency
		Money money = new Money(200,DKK);
		Money money2 = new Money(7384,SEK);
		Money money3 = new Money(100,DKK);

		//Deposit money with amount=7384 and currency=Sek to account that has the same currency
		testAccount.deposit(money2);
		//check if deposit with the same currency was made correctly
		assertEquals(Integer.valueOf(10007384),testAccount.getBalance().getAmount());

		//withdraw money object with amount=100 and currency=DKK from previous account
		testAccount.withdraw(money3);
		//the currency of money3 and test account are different (DKK,SEK)
		//that's why system revalues DKK to universal value then to SEK.
		//Finally the valid amount is substarcted from the account
		assertEquals(Integer.valueOf(10007373),testAccount.getBalance().getAmount());

		//deposit money that has different currency than the account.
		//it also revalues it (look above) and add it to the account
		testAccount.deposit(money);
		assertEquals(Integer.valueOf(10007395),testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		//checking amount of money from accountB
		assertEquals(Integer.valueOf(1000000),testAccountB.getBalance().getAmount());
		//checking currency of money from accountB
		assertEquals(SEK,testAccountB.getBalance().getCurrency());

	}
}
