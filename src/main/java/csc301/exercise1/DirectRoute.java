package csc301.exercise1;

public class DirectRoute {
	
	private TrainCompany trainCompany;
	private String fromStation;
	private String toStation;
	private double price;
	
	public DirectRoute(TrainCompany trainCompany, String fromStation, String toStation, double price) {
		setTrainCompany(trainCompany);
		setFromStation(fromStation);
		setToStation(toStation);
		setPrice(price);
	}


	public TrainCompany getTrainCompany() {
		return this.trainCompany;
	}
	
	public void setTrainCompany(TrainCompany trainCompany) {
		if (trainCompany == null) {
			throw new IllegalArgumentException("Train company must not be null");
		}
		this.trainCompany = trainCompany;
	}
	
	
	public String getFromStation() {
		return this.fromStation;
	}
	
	public void setFromStation(String fromStation) {
		if (fromStation == null) {
			throw new IllegalArgumentException("name must not be null");
		}
		fromStation = fromStation.trim();
		if (fromStation.isEmpty()) {
			throw new IllegalArgumentException("names must contain at least one non-whitespace character");
		}
		this.fromStation = fromStation;
	}


	public String getToStation() {
		return this.toStation;
	}
	
	public void setToStation(String toStation) {
		if (toStation == null) {
			throw new IllegalArgumentException("name must not be null");
		}
		toStation = toStation.trim();
		if (toStation.isEmpty()) {
			throw new IllegalArgumentException("names must contain at least one non-whitespace character");
		}
		this.toStation = toStation;
	}


	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		if (price < 0) {
			throw new IllegalArgumentException("price must be non-negative");
		}
		this.price = price;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		//Why is the argument Object type and not DirectRoute type?
		//Doesn't this function just compare if two DirectRoute objects are equal?
		//Forces casting.
		
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
