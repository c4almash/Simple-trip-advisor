package csc301.exercise1;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;
import org.junit.Test;

import csc301.exercise1.util.Constants;
import csc301.exercise1.util.Utils;
public class TestTrainCompany {
	private static TrainCompany ToStringTrain, PositivePriceTrain,
	EmptyCountTrain, NormalCountTrain, FastTrain, GetNameTrain,
	TestUpdateDirectRouteTrain, TestDeleteDirectRouteTrain,
	TestAddGetDirectRoute;
	/*
	 * Test Train Company Names
	 */

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create TrainCompany instances from data files in the resources folder.
		EmptyCountTrain = Utils.createCompanyFromDataFile("EmptyCountTrain.txt");
		ToStringTrain = Utils.createCompanyFromDataFile("ToStringTrain.txt");
		GetNameTrain = Utils.createCompanyFromDataFile("GetNameTrain.txt");
		TestUpdateDirectRouteTrain = Utils.createCompanyFromDataFile("TestUpdateDirectRouteTrain.txt");
		TestDeleteDirectRouteTrain = Utils.createCompanyFromDataFile("TestDeleteDirectRouteTrain.txt");
		TestAddGetDirectRoute = Utils.createCompanyFromDataFile("TestAddGetDirectRouteTrain.txt");
	}
	
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

	@Test
	public void emptyDirectRouteCollectionShouldNotReturnNull() {
		TrainCompany tc = new TrainCompany("tc");
		assertNotNull(tc.getAllDirectRoutes());
		// should also test that it returns an empty collection
	}

    @Test
    public void testToString() {
		assertEquals("FastTrain, offering 2 routes between 3 stations",
				FastTrain.toString());
	}

    @Test
	public void testGetName(){
		assertEquals("GetNameTrain", GetNameTrain.getName());
	}	

	@Test
	public void shouldCreateDirectRoutesSuccessfully() {
		TrainCompany tc = new TrainCompany("tc_create");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		DirectRoute testRoute = tc.getDirectRoute(Constants.TORONTO, Constants.LONDON);
		assertNotNull(testRoute);
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

	@Test
	public void countDirectRoutesTest() {
		TrainCompany tc = new TrainCompany("tc_count");
		tc.createOrUpdateDirectRoute(Constants.TORONTO, Constants.LONDON, 50);
		tc.createOrUpdateDirectRoute(Constants.LONDON, Constants.TORONTO, 50);
		tc.createOrUpdateDirectRoute(Constants.OTTAWA, Constants.LONDON, 50);
		assertTrue(tc.getDirectRoutesCount() == 3);
	}

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

    @Test
	public void testGetDirectRoutesCountEmpty() throws Exception{
		assertEquals(0, EmptyCountTrain.getDirectRoutesCount());
	}	
}
