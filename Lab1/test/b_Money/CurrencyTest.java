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
		//testing names of each Currency
		assertEquals("SEK",SEK.getName());
		assertEquals("DKK",DKK.getName());
		assertEquals("EUR",EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		//testing if rates are valid
		//Comparing two float numbers requires point of precision(margin of error)
		// |0.20-0.20|<0.000001
		assertTrue(Math.abs(DKK.getRate()-0.20)<LIMIT);
		// |1.5-1.5|<0.000001
		assertTrue(Math.abs(EUR.getRate()-1.5)<LIMIT);

	}
	
	@Test
	public void testSetRate() {
		//
		assertTrue(Math.abs(SEK.getRate()-0.15)<LIMIT);
		//changing rate of SEK from 0.15 to 0.35
		SEK.setRate(0.35d);
		//Only SEK rate was changed, others currencies have still unchanged values.
		assertTrue(Math.abs(SEK.getRate()-0.35)<LIMIT);

	}
	
	@Test
	public void testGlobalValue() {
		//Checking universal value based on amount of money and Currency rate
		//Each currency has it's own rate
		assertEquals(1500,SEK.universalValue(10000).intValue());
		assertEquals(2000,DKK.universalValue(10000).intValue());
		assertEquals(15000,EUR.universalValue(10000).intValue());

	}
	
	@Test
	public void testValueInThisCurrency() {
		// currency1 -> universal value -> currency2
		assertEquals(225,SEK.valueInThisCurrency(1000,EUR).intValue());
		assertEquals(30,SEK.valueInThisCurrency(1000,DKK).intValue());
		assertEquals(300,EUR.valueInThisCurrency(1000,DKK).intValue());


	}

}
