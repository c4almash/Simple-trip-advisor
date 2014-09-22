package csc301.exercise1;

import org.junit.Test;

import csc301.exercise1.util.Constants;


public class TestDirectRoute {


	// A very basic example of a passing test
	@Test
	public void testCreateInstanceWithoutException(){
		new DirectRoute(new TrainCompany("Via"), Constants.TORONTO, Constants.OTTAWA, 37.5);
		
	}
	
	public void testTrainCompanyNull(){
		new DirectRoute(new TrainCompany(null), Constants.TORONTO, Constants.OTTAWA, 37.5);
		
	}
	
	public void testTrainCompanyTrailingSpace(){
		
	}
	
	public void testFromStationNull(){
		
	}
	
	public void testFromStationSpace(){
		
	}	
	
	public void testToStationNull(){
		
	}
	
	public void testToStationSpace(){
		
	}	
	
	public void testPriceNull(){
		
	}
	
	public void testPriceEmpty(){
	
	}

	

}
