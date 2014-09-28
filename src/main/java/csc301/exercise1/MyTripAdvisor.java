package csc301.exercise1;

import java.util.List;
import java.util.ArrayList;

/**
 * The MyTripAdvisor class represents the trip advisor that provides users the cheapest trip and price.
 */
public class MyTripAdvisor {

	/* List that keeps track of all train companies. */
	private List<TrainCompany> trainCompanyList = new ArrayList<TrainCompany>();
	/* Constructor */
	public MyTripAdvisor() {
	}
	
	/**
	 * Add new train company to <code>trainCompanyList</code>.
	 * 
	 * @param	TrainCompany				The new train company to be added.
	 * @throws	IllegalArgumentException	if the name of <code>trainCompany</code> is null
	 * 										or containing whitespace only;
	 * 										And if there exist a company in <code>trainCompanyList</code>
	 * 										with the same name.
	 */
	public void addTrainCompany(TrainCompany trainCompany){
		if (trainCompany == null) {
			throw new IllegalArgumentException("Adding a null company is not allowed");
		}
		trainCompanyList.add(trainCompany);
	}
	
	/**
	 * Return the price of a cheapest trip from <code>fromStation</code>
	 * to <code>toStation</code>.
	 * Return -1, if there is no trip between the two specified stations.
	 */
	public double getCheapestPrice(String fromStation, String toStation){
		return -1;
	}
	
	/**
	 * Return a cheapest trip from <code>fromStation</code> to <code>toStation</code>,
	 * as a list of DirectRoute objects.
	 * Return null, if there is no trip between the two specified stations.
	 */
	public List<DirectRoute> getCheapestTrip(String fromStation, String toStation){
		return null;
	}
	
}
