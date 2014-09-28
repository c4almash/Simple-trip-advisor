package csc301.exercise1;

/**
 * The DirectRoute class represents a direct route from the one station to the other. 
 *
 * <P> It contains the following information of the direct route:
 * - <code>trainCompany</code>:		the train company that offers the route.
 * - <code>fromStation</code>:		the departure station of the route.
 * - <code>toStation</code>:		the terminal station of the route.
 * - <code>price</code>:			the price of the route.
 * 
 * @author c4almash
 * @author g3abby
 * @author g3aishih
 * @author oneohtrix
 * @author SunnyLi
 * @author timothylai
 */
public class DirectRoute {
	
	/* The train company that offers the route. */
	private TrainCompany trainCompany;
	/* The departure station (origin) of the route. Must not be null or containing whitespace only. */
	private String fromStation;
	/* The terminal station (destination) of the route. Must not be null or containing whitespace only. */
	private String toStation;
	/* The price of the direct route. Must be non-negative. */
	private double price;
	
	/**
	 * Construct a new DirectRoute Object.
	 * 
	 * @param 	trainCompany				The company that offers the route.
	 * @param 	fromStation					The departure station (origin) of the route.
	 * @param	toStation					The terminal station (destination) of the route. 
	 * @param	price						The price of the route.
	 * @throws 	IllegalArgumentException	if <code>trainCompany</code> is null;
	 * 										And if <code>fromStation</code> or <code>toStation</code> is null, 
	 * 										or containing whitespace only; 
	 * 										And if <code>price</code> is null or negative.
	 * 										And if <code>fromStation</code> and <code>toStation</code> are
	 * 										the same.
	 */
	public DirectRoute(TrainCompany trainCompany, String fromStation, String toStation, double price) {
		setTrainCompany(trainCompany);
		setFromStation(fromStation);
		setToStation(toStation);
		setPrice(price);
		if (fromStation.equals(toStation)) {
			throw new IllegalArgumentException("From station and to Station cannot be the same");
		}
	}

	/** 
	 * @return the train company that offers the direct route. 
	 */
	public TrainCompany getTrainCompany() {
		return this.trainCompany;
	}
	
	/**
	 * Set the train company that offers the direct route. 
	 * 
	 * @param	trainCompany				The trainComany that offers the DirectRoute.
	 * @throws	IllegalArgumentException	Exception will be thrown if <code>trainCompany</code> is null.
	 */
	public void setTrainCompany(TrainCompany trainCompany) {
		if (trainCompany == null) {
			throw new IllegalArgumentException("Train company must not be null");
		}
		this.trainCompany = trainCompany;
	}
	
	/**
	 * @return the departure station of the direct route.
	 */
	public String getFromStation() {
		return this.fromStation;
	}
	
	/**
	 * Set the departure station of the direct route.
	 * 
	 * @param	fromStation					The departure station of the DirectRoute.
	 * @throws	IllegalArgumentException	if <code>fromStation</code> is null, 
	 * 										or containing whitespace only.
	 */
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

	/**
	 * @return the terminal station of the direct route.
	 */
	public String getToStation() {
		return this.toStation;
	}
	
	/**
	 * Set the terminal station of the direct route.
	 * 
	 * @param	toStation					The terminal station of the direct route.
	 * @throws	IllegalArgumentException	if <code>toStation</code> is null,
	 * 										or containing whitespace only.
	 */
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

	/**
	 * @return the price of the direct route.
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Set the price of the direct route.
	 *
	 * @param	price						The price of the direct route.
	 * @throws	IllegalArgumentException	if <code>price</code> is negative.
	 */
	public void setPrice(double price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price must be non-negative");
		}
		this.price = price;
	}
	
	/**
	 * @return true/false based on the equality of two direct routes.
	 *
	 * @param	obj						Another direct route to be compared.
	 * @return	<tt>true</tt>			if and only if all four attributes of 
	 *									both routes are exactly the same.
	 * @throws	ClassCastException		if <code>obj</code> cannot be casted t a DirectRoute.
	 */
	@Override
	public boolean equals(Object obj) {
        DirectRoute comp = (DirectRoute) obj;
		return getTrainCompany().equals(comp.getTrainCompany()) &&
			getFromStation().equals(comp.getFromStation()) &&
			getToStation().equals(comp.getToStation()) &&
			getPrice() == comp.getPrice();
	}
	
	/**
	 * @return the details of the direct route as one single String, in the format of 
	 * "[<code>trainCompany</code>] from [<code>fromStation</code>] to [<code>toStation</code>], [<code>price</code>]"
	 */
	@Override
	public String toString() {
		return String.format("%s from %s to %s, %.2f$", getTrainCompany().getName(), 
				getFromStation(), getToStation(), getPrice());
	}
}

