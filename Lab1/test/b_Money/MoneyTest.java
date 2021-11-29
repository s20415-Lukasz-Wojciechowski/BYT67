package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	private static final double  MISTAKE_POINT_LIMIT= 0.0000001;
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		//checking if amounts of money suits their expected values
		assertEquals(  10000,SEK100.getAmount().intValue());
		assertEquals(  0,EUR0.getAmount().intValue());
		assertEquals(  0,SEK0.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {

		//checking if Currency object SEK has a name SEK
		assertEquals( "SEK", SEK.getName());
		//checking if currency rate is valid
		//comparing two float values may sometimes lead to error, that's why it's better to set a margin rate of error.
		assertTrue(Math.abs(0.2d-DKK.getRate().doubleValue())<MISTAKE_POINT_LIMIT);
	}

	@Test
	public void testToString()
	{
		//check if SEK correctly returns to currency toString method
		assertEquals("Currency:name=SEK, rate=0.15",SEK.toString());
		assertEquals("Money:amount=0, currency=Currency:name=SEK, rate=0.15",SEK0.toString());

	}

	@Test
	public void testGlobalValue() {
		//Checking universal value of SEK0, SEk100,EURO20 based on currency and method that returns
		// its universal value based on their rates
		assertEquals(1500,SEK100.universalValue().intValue());
		assertEquals(0,SEK0.universalValue().intValue());
		assertEquals(3000,EUR20.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		//Check if variables have an instance of Money
		assertTrue(SEK0 instanceof Money);
		assertTrue(SEK200 instanceof Money);
		 assertTrue(SEK100 instanceof Money);
		 assertTrue(EUR20 instanceof Money);

	}

	@Test
	public void testAdd() {
		//checking add method -each line value has the same currency for simplicity
		//200 = 100+100
		assertEquals(SEK200.getAmount(), SEK100.add(SEK100).getAmount());
		//100 = 100+100+(-100)
		assertEquals(SEK100.getAmount(), SEK100.add(SEK100).add(SEKn100).getAmount());
		//20 = 20+0
		assertEquals(EUR20.getAmount(), EUR20.add(EUR0).getAmount());

	}

	@Test
	public void testSub() {
		//checking Sub method
		//each line value has the same currency for simplicity
		// 100 = 200-100
		assertEquals(SEK100.getAmount(), SEK200.sub(SEK100).getAmount());
		//200 = 100 - (-100)
		assertEquals(SEK200.getAmount(), SEK100.sub(SEKn100).getAmount());
		//10 = 10 -0
		assertEquals(EUR10.getAmount(),EUR10.sub(EUR0).getAmount());
	}

	@Test
	public void testIsZero() {
		//Checking if those values are equal 0 - returns true
		//Each line value has the same currency for simplicity
		// 0 = 0 -true
		assertTrue(EUR0.isZero());
		// 0 = 20-20
		assertTrue(EUR20.sub(EUR20).isZero());
		// 0 != 20-10 -false
		assertFalse(EUR20.sub(EUR10).isZero());
		// 0 =  100 + (-100) - true
		assertTrue( SEK100.add(SEKn100).isZero());

	}

	@Test
	public void testNegate() {
		//checking an opposite value
		// 0 * -1 = -0 = 0
		//zero has the same negate value
		assertTrue(SEK0.negate().isZero());
		//-1000 = -1*(1000)
		assertEquals(-1000,EUR10.negate().getAmount().intValue());
		// -10000 = -1*10000
		assertEquals(SEKn100.getAmount().intValue(),SEK100.negate().getAmount().intValue());


	}

	@Test
	public void testCompareTo() {
		/*returns 1 if the first value is bigger
		* returns -1 if  the first value is smaller
		* returns 0 if it's a draw
		* */

		//10 euro is bigger than 0 euro - 1
		assertEquals(1,EUR10.compareTo(EUR0));
		//100Sek is bigger than -100Sek - 1
		assertEquals(1,SEK100.compareTo(SEKn100));
		//100Sek is smaller than 200sek - -1
		assertEquals(-1,SEK100.compareTo(SEK200));
		//100sek is equal to 100Sek - 0
		assertEquals(0,SEK100.compareTo(SEK100));
	//	fail("Write test case here");
	}
}
