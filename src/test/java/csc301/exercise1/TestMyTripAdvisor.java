package csc301.exercise1;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import csc301.exercise1.util.Constants;
import csc301.exercise1.util.Utils;

public class TestMyTripAdvisor {

	private static TrainCompany fastTrain;
	private static TrainCompany swiftRail;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create TrainCompany instances from data files in the resources folder.
		fastTrain = Utils.createCompanyFromDataFile("FastTrain.txt");
		swiftRail = Utils.createCompanyFromDataFile("SwiftRail.txt");
	}

	
	
	
	/*
	 * We added two examples of a tests, feel free to edit/remove them.
	 * 
	 * Notice how we set a timeout of 3000ms - If the trip advisor that we're testing
	 * is broken, and goes into an infinite loop, our tests will not get stuck, they
	 * will just fail after 3 seconds.
	 * 
	 * TODO: Delete this comment before you submit the assignment.
	 */
	
	@Test(timeout=3000)
	public void twoRouteTripWhereRoutesAreFromDifferentCompanies() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(fastTrain);
		advisor.addTrainCompany(swiftRail);
		
		List<DirectRoute> trip = advisor.getCheapestTrip(Constants.TORONTO, Constants.MONTREAL);
		
		// Make sure that we got the route we expect
		assertEquals(2, trip.size());
		assertEquals(new DirectRoute(swiftRail, Constants.TORONTO, Constants.OTTAWA, 30), trip.get(0));
		assertEquals(new DirectRoute(fastTrain, Constants.OTTAWA, Constants.MONTREAL, 25), trip.get(1));
	}
	
	
	@Test(timeout=3000)
	public void priceOfTwoRouteTripWhereRoutesAreFromDifferentCompanies() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(fastTrain);
		advisor.addTrainCompany(swiftRail);
		
		// Make sure that we got the total price we expect
		assertTrue(55 == advisor.getCheapestPrice(Constants.TORONTO, Constants.MONTREAL));
	}


	// Adding a null company is not allowed
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAddNullCompany() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(null);
	}
}
