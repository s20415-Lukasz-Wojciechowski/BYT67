package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	private static final double  LIMIT= 0.000001;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		//fail("Write test case here");
		assertEquals("SEK",SEK.getName());
		assertEquals("DKK",DKK.getName());
		assertEquals("EUR",EUR.getName());
	}
	
	@Test
	public void testGetRate() {

		assertTrue(Math.abs(DKK.getRate()-0.20)<LIMIT);
		assertTrue(Math.abs(EUR.getRate()-1.5)<LIMIT);
		//fail("Write test case here");
	}
	
	@Test
	public void testSetRate() {
		assertTrue(Math.abs(SEK.getRate()-0.15)<LIMIT);
		SEK.setRate(0.35d);
		assertTrue(Math.abs(SEK.getRate()-0.35)<LIMIT);
		//fail("Write test case here");
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(1500,SEK.universalValue(10000).intValue());
		assertEquals(2000,DKK.universalValue(10000).intValue());
		assertEquals(15000,EUR.universalValue(10000).intValue());

		//fail("Write test case here");
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(225,SEK.valueInThisCurrency(1000,EUR).intValue());
		assertEquals(30,SEK.valueInThisCurrency(1000,DKK).intValue());
		assertEquals(300,EUR.valueInThisCurrency(1000,DKK).intValue());

		//fail("Write test case here");
	}

}
