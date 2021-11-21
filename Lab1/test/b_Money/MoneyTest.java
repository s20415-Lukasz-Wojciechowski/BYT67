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
		assertEquals(  10000,SEK100.getAmount().intValue());
		assertEquals(  0,EUR0.getAmount().intValue());
		assertEquals(  0,SEK0.getAmount().intValue());

		//fail("Write test case here");
	}

	@Test
	public void testGetCurrency() {

		//fail("Write test case here");
		assertEquals( "SEK", SEK.getName());
		assertTrue(Math.abs(0.2d-DKK.getRate().doubleValue())<MISTAKE_POINT_LIMIT);
	}

	@Test
	public void testToString()
	{
		//fail("Write test case here");
		assertEquals("Currency:name=SEK, rate=0.15",SEK.toString());
		assertEquals("Money:amount=0, currency=Currency:name=SEK, rate=0.15",SEK0.toString());

	}

	@Test
	public void testGlobalValue() {
	//	fail("Write test case here");
		assertEquals(1500,SEK100.universalValue().intValue());
		assertEquals(0,SEK0.universalValue().intValue());
		assertEquals(3000,EUR20.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		//Object Money?
		//fail("Write test case here");
		assertTrue(SEK0 instanceof Money);
		assertTrue(SEK200 instanceof Money);
		 assertTrue(SEK100 instanceof Money);
		 assertTrue(EUR20 instanceof Money);

	}

	@Test
	public void testAdd() {
		assertEquals(SEK200.getAmount(), SEK100.add(SEK100).getAmount());
		assertEquals(SEK100.getAmount(), SEK100.add(SEK100).add(SEKn100).getAmount());
		assertEquals(EUR20.getAmount(), EUR20.add(EUR0).getAmount());
		//fail("Write test case here");
	}

	@Test
	public void testSub() {
		assertEquals(SEK100.getAmount(), SEK200.sub(SEK100).getAmount());
		assertEquals(SEK200.getAmount(), SEK100.sub(SEKn100).getAmount());
		assertEquals(EUR10.getAmount(),EUR10.sub(EUR0).getAmount());
		//fail("Write test case here");
	}

	@Test
	public void testIsZero() {
		assertTrue(EUR0.isZero());
		assertTrue(EUR20.sub(EUR20).isZero());
		assertFalse(EUR20.sub(EUR10).isZero());
		assertTrue( SEK100.add(SEKn100).isZero());
		//fail("Write test case here");
	}

	@Test
	public void testNegate() {
		assertTrue(SEK0.negate().isZero());
		assertEquals(-1000,EUR10.negate().getAmount().intValue());
		assertEquals(SEKn100.getAmount().intValue(),SEK100.negate().getAmount().intValue());
		//fail("Write test case here");

	}

	@Test
	public void testCompareTo() {
		assertEquals(1,EUR10.compareTo(EUR0));
		assertEquals(1,SEK100.compareTo(SEKn100));
		assertEquals(-1,SEK100.compareTo(SEK200));
		assertEquals(0,SEK100.compareTo(SEK100));
	//	fail("Write test case here");
	}
}
