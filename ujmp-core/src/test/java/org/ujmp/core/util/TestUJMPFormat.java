package org.ujmp.core.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class TestUJMPFormat {

	@Test
	public void testDatFormat() throws Exception {
		final UJMPFormat format = new UJMPFormat(true, 15, true);
		final Calendar cal = Calendar.getInstance();
		cal.set(2018, Calendar.DECEMBER, 31, 15, 59, 55);
		final String text = format.format(cal.getTime());
		
		assertEquals("2018-12-31     ", text);
	}

}
