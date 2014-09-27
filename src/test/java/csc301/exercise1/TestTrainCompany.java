package csc301.exercise1;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.BeforeClass;
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
	public void testTrainCompanyNullName() {
		new TrainCompany(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTrainCompanyEmptyName() {
		new TrainCompany("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTrainCompanyBlankName() {
		new TrainCompany(" ");
	}

	@Test
	public void testTrainCompanySpaceNames() {
		new TrainCompany("A");
		new TrainCompany("B ");
		new TrainCompany(" C");
		new TrainCompany(" D ");
		new TrainCompany(" A  B  C ");
	}
	
	// There should never be two TrainCompany instances with the same name.
	@Test(expected = IllegalArgumentException.class)
	public void testTrainCompanySameName() {
		new TrainCompany("E");
		new TrainCompany("E");
	}
	
	public void testToString(){
		assertEquals("FastTrain, offering 2 routes between 3 stations",
				FastTrain.toString());
	}

	@Test
	public void testGetName(){
		assertEquals("GetNameTrain", GetNameTrain.getName());
	}	
	
	@Test
	public void testUpdateDirectRoute(){
		assertEquals(new DirectRoute(TestUpdateDirectRouteTrain, Constants.MONTREAL, Constants.OTTAWA, 50), 
				TestUpdateDirectRouteTrain.createOrUpdateDirectRoute(Constants.MONTREAL, Constants.OTTAWA, 50));
	}
	
	public void testDeleteDirectRoute(){
		
	}
		
	//@Test
	//public void testAddGetDirectRoute(){
	//	TC3 = new TrainCompany("TestCompany3");
	//	TC3.addDirectRoute(Constants.TORONTO, Constants.OTTAWA, 50);
	//	assertEquals(new DirectRoute(TC3, Constants.TORONTO, Constants.OTTAWA, 50), 
	//			TC3.getDirectRoute(Constants.TORONTO, Constants.OTTAWA));
	//}
	
	
	public void testGetDirectRoutesFrom(){
		
	}
	
	public void testGetRoutesTo(){
		
	}
	
	public void testGetAllDirectRoutes(){
		
	}
	
	@Test
	public void testGetDirectRoutesCountEmpty() throws Exception{
		assertEquals(0, EmptyCountTrain.getDirectRoutesCount());
	}	
	
	//@Test
	//public void testGetDirectRoutesCount(){
	//		assertEquals(3, TC5.getDirectRoutesCount());
	//}
	
	@Test
	public void emptyDirectRouteCollectionShouldNotReturnNull() {
		TrainCompany tc = new TrainCompany("tc");
		assertNotNull(tc.getAllDirectRoutes());
		// should also test that it returns an empty collection
	}
}