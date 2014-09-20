package csc301.exercise1;

public class DirectRoute {
	
	private TrainCompany trainCompany;
	private String fromStation;
	private String toStation;
	private double price;
	
	public DirectRoute(TrainCompany trainCompany, String fromStation, String toStation, double price) {
		this.trainCompany = trainCompany;
		this.fromStation = fromStation;
		this.toStation = toStation;
		this.price = price;
	}


	public TrainCompany getTrainCompany() {
		return this.trainCompany;
	}
	
	public void setTrainCompany(TrainCompany trainCompany) {
		this.trainCompany = trainCompany;
	}
	
	
	public String getFromStation() {
		return this.fromStation;
	}
	
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}


	public String getToStation() {
		return this.toStation;
	}
	
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}


	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		//Why is the argument Object type and not DirectRoute type?
		//Doesn't this function just compare if two DirectRoute objects are equal?
		
		return getTrainCompany().getName() == ((DirectRoute) obj).getTrainCompany().getName() &&
				getFromStation() == ((DirectRoute) obj).getFromStation() &&
				getToStation() == ((DirectRoute) obj).getToStation() &&
				getPrice() == ((DirectRoute) obj).getPrice();
	}
	
	
	@Override
	public String toString() {
		return String.format("%s from %s to %s, %.2f$", getTrainCompany().getName(), 
				getFromStation(), getToStation(), getPrice());
	}

}
