package csc301.exercise1;

import java.util.List;
import java.util.ArrayList;


public class MyTripAdvisor {

	private List<TrainCompany> trainCompanyList = new ArrayList<TrainCompany>();
	
	public MyTripAdvisor() {
	}
	
	
	public void addTrainCompany(TrainCompany trainCompany){
		//Typo in function name, originally called addTrainCopmany
		//Same typo in TestMyTripAdvisor file
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
