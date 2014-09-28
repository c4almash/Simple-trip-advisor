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
	private static TrainCompany moonTrain;
	private static TrainCompany rubyOnRails;
	private static TrainCompany viaRail;
	private static TrainCompany goTrain;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create TrainCompany instances from data files in the resources folder.
		fastTrain = Utils.createCompanyFromDataFile("FastTrain.txt");
		swiftRail = Utils.createCompanyFromDataFile("SwiftRail.txt");
		moonTrain = Utils.createCompanyFromDataFile("MoonTrain.txt");
		rubyOnRails = Utils.createCompanyFromDataFile("RubyOnRails.txt");
		viaRail = Utils.createCompanyFromDataFile("ViaRail.txt");
		goTrain = Utils.createCompanyFromDataFile("GOTrain.txt");
	}

	
	

	
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

	@Test(timeout=3000)
	public void nonExistentTrip() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(moonTrain);
		
		assertTrue(null == advisor.getCheapestTrip(Constants.TORONTO, Constants.MONTREAL));
	}
	
	@Test(timeout=3000)
	public void nonExistentPrice() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(viaRail);
		
		assertTrue(-1 == advisor.getCheapestPrice(Constants.TORONTO, Constants.LONDON));
	}

	@Test(timeout=3000)
	public void equalPriceForTwoRoutes() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(viaRail);
		assertTrue(50 == advisor.getCheapestPrice(Constants.MONTREAL, Constants.VANCOUVER));
	}
	
	@Test(timeout=3000)
	public void multipleWaysToGetToDestinationPrice() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(viaRail);
		assertTrue(25 == advisor.getCheapestPrice(Constants.TORONTO, Constants.WATERLOO));
	}
	
	@Test(timeout=3000)
	public void multipleWaysToGetToDestinationTrip() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(viaRail);
		advisor.addTrainCompany(rubyOnRails);
		
		List<DirectRoute> trip = advisor.getCheapestTrip(Constants.TORONTO, Constants.WATERLOO);
		
		assertEquals(new DirectRoute(rubyOnRails, Constants.TORONTO, Constants.VANCOUVER, 10), trip.get(0));
		assertEquals(new DirectRoute(rubyOnRails, Constants.VANCOUVER, Constants.WATERLOO, 5), trip.get(1));
	}
	
	@Test(timeout=3000)
	public void sameFromStationAndToStationPrice() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(goTrain);
		assertTrue(0 == advisor.getCheapestPrice(Constants.TORONTO, Constants.TORONTO));
	}
	
	@Test(timeout=3000)
	public void sameFromStationAndToStationTrip() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(goTrain);
		assertTrue(0 == advisor.getCheapestTrip(Constants.TORONTO, Constants.TORONTO).size());
	}

	// Adding a null company is not allowed
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAddNullCompany() {
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(null);
	}
}
