package csc301.exercise1;

import static org.junit.Assert.assertFalse;

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
	 * Test DirectRoute
	 */
	@Test(expected = IllegalArgumentException.class)
	public void trainCompanyMustNotBeNull() {
		new DirectRoute(null, Constants.TORONTO, Constants.MONTREAL, 37.5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void priceMustBeNonNegative() {
		new DirectRoute(viaRail, Constants.TORONTO, Constants.MONTREAL, -1);
	}
	
	@Test
	public void testEqualMethod() {
		// how do we assert equal if we couldn't even create a duplicate?
		DirectRoute dr1 = new DirectRoute(viaRail, Constants.TORONTO, Constants.WATERLOO, 1);
		DirectRoute dr2 = new DirectRoute(viaRail, Constants.TORONTO, Constants.LONDON, 1);
		assertFalse(dr1.equals(dr2));
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

	// Not sure if creating duplicate route should throw error or gracefully fall back and
	// return the existing route..
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowStationWithSameNames() {
		new DirectRoute(cnr, Constants.TORONTO, Constants.MONTREAL, 37.5);
		new DirectRoute(cnr, Constants.TORONTO, Constants.MONTREAL, 37.5);
	}


	/*
	 * Test that "a company cannot offer different prices for the same route".
	 * Should we just update the price for that route instead?
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateExistingRouteWithDifferentPrices() {
		new DirectRoute(cpr, Constants.TORONTO, Constants.MONTREAL, 37.5);
		new DirectRoute(cpr, Constants.TORONTO, Constants.MONTREAL, 36);
	}

}
