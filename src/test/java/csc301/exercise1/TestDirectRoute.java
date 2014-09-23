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
	public void testMultipleUniqueInstancesWithoutException() {
		new DirectRoute(cnr, Constants.TORONTO, Constants.OTTAWA, 37.5);
		new DirectRoute(cpr, Constants.TORONTO, Constants.OTTAWA, 37.5);
	}

	/*
	 * Test trainCompany attribute of DirectRoute
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTrainCompanyNull() {
		new DirectRoute(null, Constants.TORONTO, Constants.MONTREAL, 37.5);
	}
	
	/*
	 * Test fromStation and toStation attribute of DirectRoute
	 * Assume station names stored in this class
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFromToNull() {
		new DirectRoute(cnr, null, null, 37.5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromToEmptyBlank() {
		new DirectRoute(cnr, "", " ", 37.5);
	}

	@Test
	public void testFromToTrailingSpace() {
		new DirectRoute(cnr, "A", "B ", 37.5);
		new DirectRoute(cnr, " C", " D ", 37.5);
	}

	/* Not sure if we actually need to test these cases but it would
	 * make our code function with mismatched inputs
	
	@Test(expected = IllegalArgumentException.class) 
	public void testFromToMismatch(){
		new DirectRoute(viaRail, 0, 1.23, 37.5);
	}
	*/
	
	/*
	 * Test price attribute of DirectRoute
	 */	
	@Test(expected = IllegalArgumentException.class)
	public void testPriceNegative() {
		new DirectRoute(viaRail, Constants.TORONTO, Constants.MONTREAL, -1);
	}		
	/* Not sure if we actually need to test these cases but it would
	 * make our code function with mismatched inputs
	
	@Test(expected = IllegalArgumentException.class) 
	public void testPriceNull(){
		new DirectRoute(viaRail, Constants.TORONTO, Constants.MONTREAL, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPriceMismatch(){
		new DirectRoute(viaRail, Constants.TORONTO, Constants.MONTREAL, "String");
	}
	*/
	
	/*
	 * Test that "a company cannot offer different prices for the same route".
	 * Should we just update the price for that route instead?
	 * "A company offers a single price per route.
	 * In other words, a company cannot offer different prices for the same route."
	 * - so I am assuming we don't update the price attribute?
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testExistingRouteWithDifferentPrices() {
		new DirectRoute(cpr, Constants.TORONTO, Constants.MONTREAL, 37.5);
		new DirectRoute(cpr, Constants.TORONTO, Constants.MONTREAL, 36);
	}
	
	@Test
	public void testEqualMethod() {
		// how do we assert equal if we couldn't even create a duplicate?
		DirectRoute dr1 = new DirectRoute(viaRail, Constants.TORONTO, Constants.WATERLOO, 1);
		DirectRoute dr2 = new DirectRoute(viaRail, Constants.TORONTO, Constants.LONDON, 1);
		assertFalse(dr1.equals(dr2));
	}

	// Not sure if creating duplicate route should throw error or gracefully fall back and
	// return the existing route..
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowStationWithSameNames() {
		new DirectRoute(cnr, Constants.TORONTO, Constants.MONTREAL, 37.5);
		new DirectRoute(cnr, Constants.TORONTO, Constants.MONTREAL, 37.5);
	}


		
}
