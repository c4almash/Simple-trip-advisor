package csc301.exercise1;

public class DirectRoute {
	
	private TrainCompany trainCompany;
	private String fromStation;
	private String toStation;
	private double price;
	
	public DirectRoute(TrainCompany trainCompany, String fromStation, String toStation, double price) {
		if (fromStation.equals(toStation)) {
			throw new IllegalArgumentException("From station and to Station cannot be the same");
		}
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
			throw new IllegalArgumentException("Name must not be null");
		}
		fromStation = fromStation.trim();
		if (fromStation.isEmpty()) {
			throw new IllegalArgumentException("Names must contain at least one non-whitespace character");
		}
		this.fromStation = fromStation;
	}


	public String getToStation() {
		return this.toStation;
	}
	
	public void setToStation(String toStation) {
		if (toStation == null) {
			throw new IllegalArgumentException("Name must not be null");
		}
		toStation = toStation.trim();
		if (toStation.isEmpty()) {
			throw new IllegalArgumentException("Names must contain at least one non-whitespace character");
		}
		this.toStation = toStation;
	}


	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price must be non-negative");
		}
		this.price = price;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		DirectRoute comp = (DirectRoute) obj;
		return getTrainCompany().equals(comp.getTrainCompany()) &&
			getFromStation().equals(comp.getFromStation()) &&
			getToStation().equals(comp.getToStation()) &&
			getPrice() == comp.getPrice();
	}
	
	
	@Override
	public String toString() {
		return String.format("%s from %s to %s, %.2f$", getTrainCompany().getName(), 
				getFromStation(), getToStation(), getPrice());
	}

}
