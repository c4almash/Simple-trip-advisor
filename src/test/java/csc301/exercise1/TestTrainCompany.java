package csc301.exercise1;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;

import csc301.exercise1.util.Constants;

public class TestTrainCompany {

	private static TrainCompany TC1, TC2, TC3, TC4, TC5,
		TC6, TC7;	
	
	/*
	 * Test Train Company Names
	 */

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
	
	@Test
	public void testGetName(){
		TC1 = new TrainCompany("TestCompany1");
		assertEquals("TestCompany1", TC1.getName());
	}	
	
	@Test
	public void testUpdateDirectRoute(){
		TC2 = new TrainCompany("TestCompany2");
		assertEquals(new DirectRoute(TC2, Constants.TORONTO, Constants.OTTAWA, 50), 
				TC2.createOrUpdateDirectRoute(Constants.TORONTO, Constants.OTTAWA, 50));
	}
	
	public void testDeleteDirectRoute(){
		
	}
		
	@Test
	public void testAddGetDirectRoute(){
		TC3 = new TrainCompany("TestCompany3");
		TC3.addDirectRoute(Constants.TORONTO, Constants.OTTAWA, 50);
		assertEquals(new DirectRoute(TC3, Constants.TORONTO, Constants.OTTAWA, 50), 
				TC3.getDirectRoute(Constants.TORONTO, Constants.OTTAWA));
	}
	
	@Test
	public void testToString(){
		TC6 = new TrainCompany("TestCompany6");
		TC6.addDirectRoute(Constants.TORONTO, Constants.OTTAWA, 50);
		assertEquals("TestCompany6, offering 1 routes between 2 stations",
				TC6.toString());
	}
	
	public void testGetDirectRoutesFrom(){
		
	}
	
	public void testGetRoutesTo(){
		
	}
	
	public void testGetAllDirectRoutes(){
		
	}
	
	@Test
	public void testGetDirectRoutesCountEmpty(){
		TC4 = new TrainCompany("TestCompany4");
		assertEquals(0, TC4.getDirectRoutesCount());
	}
	
	@Test
	public void testGetDirectRoutesCount(){
		TC5 = new TrainCompany("TestCompany5");
		TC5.addDirectRoute(Constants.TORONTO, Constants.OTTAWA, 50);
		TC5.addDirectRoute(Constants.OTTAWA, Constants.TORONTO, 40);
		TC5.addDirectRoute(Constants.OTTAWA, Constants.WATERLOO, 60);
		assertEquals(3, TC5.getDirectRoutesCount());
	}
	
	@Test
	public void emptyDirectRouteCollectionShouldNotReturnNull() {
		TrainCompany tc = new TrainCompany("tc");
		assertNotNull(tc.getAllDirectRoutes());
		// should also test that it returns an empty collection
	}
}
