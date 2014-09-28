package csc301.exercise1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import csc301.exercise1.util.Constants;

public class TestDirectRoute {

	private static TrainCompany viaRail, cnr, cpr;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		viaRail = new TrainCompany("ViaRail");
		cnr = new TrainCompany("Canadian National Railway");
		cpr = new TrainCompany("Canadian Pacific Railway");
	}

	// A very basic example of a passing test
	@Test
	public void testCreateInstanceWithoutException() {
		new DirectRoute(new TrainCompany("Via"), Constants.TORONTO, Constants.OTTAWA, 37.5);
	}

	@Test
	public void createMultipleUniqueInstancesWithoutException() {
		new DirectRoute(cnr, Constants.TORONTO, Constants.OTTAWA, 37.5);
		new DirectRoute(cpr, Constants.TORONTO, Constants.OTTAWA, 37.5);
	}

	/*
	 * Test trainCompany attribute of DirectRoute
	 */
	@Test(expected = IllegalArgumentException.class)
	public void trainCompanyMustNotBeNull() {
		new DirectRoute(null, Constants.TORONTO, Constants.MONTREAL, 37.5);
	}

	/*
	 * Test price attribute of DirectRoute
	 */	
	@Test(expected = IllegalArgumentException.class)
	public void priceMustBeNonNegative() {
		new DirectRoute(viaRail, Constants.TORONTO, Constants.MONTREAL, -1);
	}

	@Test
	public void testDifferentRoutes() {
		DirectRoute dr1 = new DirectRoute(viaRail, Constants.TORONTO, Constants.WATERLOO, 1);
		DirectRoute dr2 = new DirectRoute(viaRail, Constants.TORONTO, Constants.LONDON, 1);
		assertFalse(dr1.equals(dr2));
	}

	@Test
	public void testEqualRoutes() {
		String from = new String("Toronto");
		String from2 = new String("Toronto");

		String to = new String("Waterloo");
		String to2 = new String("Waterloo");

		DirectRoute dr1 = new DirectRoute(viaRail, from, to, 1);
		DirectRoute dr2 = new DirectRoute(viaRail, from2, to2, 1);

		assertTrue(dr1.equals(dr2));
	}

	/*
	 * Test Station names
	 * Assume station names stored in this class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowStationWithNullNames() {
		new DirectRoute(cnr, null, null, 37.5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowStationWithEmptyOrBlankNames() {
		new DirectRoute(cnr, "", " ", 37.5);
	}

	@Test
	public void allowStationWithNonWhitespaceNames() {
		new DirectRoute(cnr, "A", "B ", 37.5);
		new DirectRoute(cnr, " C", " D ", 37.5);
	}
}
