package csc301.exercise1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import csc301.exercise1.util.Constants;
import csc301.exercise1.util.Utils;

public class TestTrainCompany {

	private static TrainCompany ToStringTrain, PositivePriceTrain,
	EmptyCountTrain, NormalCountTrain, FastTrain, GetNameTrain,
	TestUpdateDirectRouteTrain, TestDeleteDirectRouteTrain,
	TestAddGetDirectRoute;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create TrainCompany instances from data files in the resources folder.
		EmptyCountTrain = Utils.createCompanyFromDataFile("EmptyCountTrain.txt");
		ToStringTrain = Utils.createCompanyFromDataFile("ToStringTrain.txt");
		GetNameTrain = Utils.createCompanyFromDataFile("GetNameTrain.txt");
		FastTrain = Utils.createCompanyFromDataFile("FastTrain.txt");
		TestUpdateDirectRouteTrain = Utils.createCompanyFromDataFile("TestUpdateDirectRouteTrain.txt");
		TestDeleteDirectRouteTrain = Utils.createCompanyFromDataFile("TestDeleteDirectRouteTrain.txt");
		TestAddGetDirectRoute = Utils.createCompanyFromDataFile("TestAddGetDirectRouteTrain.txt");
	}


	/*
	 * Test TrainCompany construct
	 */

	// An example of how to verify that an exception is thrown
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithNullName() {
		new TrainCompany(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithEmptyName() {
		new TrainCompany("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithBlankName() {
		new TrainCompany(" ");
	}

	@Test
	public void shouldCreateCompanyWithNonWhitespaceNames() {
		new TrainCompany("A");
		new TrainCompany("B ");
		new TrainCompany(" C");
		new TrainCompany(" D ");
		new TrainCompany(" A  B  C ");
	}

	// There should never be two TrainCompany instances with the same name.
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithSameName() {
		new TrainCompany("E");
		new TrainCompany("E");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateCompanyWithSameNameEvenWithTrailingWhitespace() {
		new TrainCompany("F ");
		new TrainCompany("	F\n");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldIgnoreWhitespaceInCompanyName() {
		new TrainCompany(" E");
		new TrainCompany("E ");
	}


	/*
	 * Test TrainCompany toString
	 */

    @Test
    public void testToString() {
		assertEquals("FastTrain, offering 2 routes between 3 stations",
				FastTrain.toString());
	}


    /*
     * Test getName, setName
     */

    @Test
	public void testGetName(){
		assertEquals("GetNameTrain", GetNameTrain.getName());
	}

    @Test
	public void testSetName(){
    	TrainCompany tsn = new TrainCompany("TestSetName");
		assertEquals("TestSetName", tsn.getName());

		tsn.setName("NameChanged");
		assertNotEquals("TestSetName", tsn.getName());
		assertEquals("NameChanged", tsn.getName());

		tsn.setName("TestSetName");
		assertNotEquals("NameChanged", tsn.getName());
		assertEquals("TestSetName", tsn.getName());
	}


	/*
	 * Tests for createOrUpdateDirectRoute, getDirectRoute,
	 *           deleteDirectRoute
	 */

	@Test
	public void shouldCreateDirectRoutesSuccessfully() {
		TrainCompany tc = new TrainCompany("tc_create");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		DirectRoute testRoute = tc.getDirectRoute(Constants.TORONTO, Constants.LONDON);
		assertNotNull(testRoute);
		assertEquals(testRoute, new DirectRoute(tc, Constants.TORONTO, Constants.LONDON, 50));
	}

	@Test
	public void shouldGetDirectRoutesSuccessfully() {
		TrainCompany tc = new TrainCompany("tc_read");

		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.OTTAWA, 10);
		DirectRoute r1 = tc.getDirectRoute(Constants.TORONTO, Constants.OTTAWA);
		tc.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.TORONTO, 20);
		DirectRoute r2 = tc.getDirectRoute(Constants.OTTAWA, Constants.TORONTO);

		assertNotNull(r1);
		assertNotNull(r2);
		
		DirectRoute r3 = tc.getDirectRoute(Constants.TORONTO, Constants.LONDON);
		assertNull(r3);
	}

	@Test
	public void shouldUpdateDirectRoutesSuccessfully() {
		TrainCompany tc = new TrainCompany("tc_update");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 100);
		DirectRoute testRoute = tc.getDirectRoute(Constants.TORONTO, Constants.LONDON);
		assertTrue(testRoute.getPrice() == ((double) 100));
	}

	@Test
	public void shouldDeleteDirectRoutesSuccessfully() {
		TrainCompany tc = new TrainCompany("tc_delete");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		tc.deleteDirectRoute(Constants.TORONTO, Constants.LONDON);
		DirectRoute testRoute = tc.getDirectRoute(Constants.TORONTO, Constants.LONDON);
		assertNull(testRoute);
	}


	/*
	 * Test getDirectRoutesFrom, getRoutesTo
	 */

	@Test
	public void getDirectRoutesFromTest() {
		TrainCompany tc = new TrainCompany("tc_from");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.WATERLOO, 50);
		tc.createOrUpdateDirectRoute(Constants.LONDON, Constants.TORONTO, 50);
		tc.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.LONDON, 50);
		assertTrue(tc.getDirectRoutesFrom(Constants.TORONTO).size() == 2);
	}

	@Test
	public void getDirectRoutesToTest() {
		TrainCompany tc = new TrainCompany("tc_to");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.WATERLOO, 50);
		tc.createOrUpdateDirectRoute(Constants.LONDON, Constants.TORONTO, 50);
		tc.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.LONDON, 50);
		assertTrue(tc.getRoutesTo(Constants.LONDON).size() == 2);
	}


	/*
	 * Test getAllDirectRoutes, getDirectRoutesCount, getStationsCount
	 */

	@Test
	public void shouldNotReturnNullForEmptyDirectRouteCollection() {
		TrainCompany tc = new TrainCompany("tc");
		assertNotNull(tc.getAllDirectRoutes());
		// TODO: should also test that it returns an empty collection
	}

	@Test
	public void countDirectRoutesTest() {
		TrainCompany tc = new TrainCompany("tc_count");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		tc.createOrUpdateDirectRoute(Constants.LONDON, Constants.TORONTO, 50);
		tc.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.LONDON, 50);
		assertTrue(tc.getDirectRoutesCount() == 3);
	}

    @Test
	public void testGetDirectRoutesCountEmpty() throws Exception{
		assertEquals(0, EmptyCountTrain.getDirectRoutesCount());
	}

    public void testGetStationCount() {
    	// TODO: implement this
    }


    /*
     * Test edge cases
     */

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowMovingToTheSamePlace() {
    	TrainCompany ttc = new TrainCompany("TTC");
    	ttc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.TORONTO, 3);
    }
}
