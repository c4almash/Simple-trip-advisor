package csc301.exercise1;

import java.util.ArrayList;
import java.util.Collection;

class DirectRouteNotFound extends Exception
{
      public DirectRouteNotFound() {}

      public DirectRouteNotFound(String message)
      {
         super(message);
      }
}

/**
 * The TrainCompany class represents a train company that offers direct train routes.
 * 
 * @author c4almash
 * @author g3abby
 * @author g3aishih
 * @author oneohtrix
 * @author SunnyLi
 * @author timothylai
 */
public class TrainCompany {
	public static Collection<String> trainCompanyNameList = new ArrayList<String>();
	/* Collection containing all the direct routes run by this train company. */
	private Collection<DirectRoute> directRouteCollection = new ArrayList<DirectRoute>();
	/* Name of the company. */
	private String name;

	/**
	 * Construct a new TrainCompany Object.
	 *
	 * @param 	name						The name of the company.
	 * @throws	IllegalArgumentException	if <code>name</code> is null or containing whitespace only;
	 * 										And if there exists a train company in <code>trainCompanyNameList</code>
	 * 										with the same name.
	 */
	public TrainCompany(String name) {
		setName(name);
	}
		
	/**
	 * @return details of the train station as one single string, in the format of
	 * "[<code>name</code>], offering [number of routes in <code>directRouteCollection</code>] routes between [number of total stations] stations".
	 */
	@Override
	public String toString() {
		return String.format("%s, offering %d routes between %d stations", 
				getName(), getDirectRoutesCount(), getStationsCount());
	}
	
	/**
	 * @return the name of the train company.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the train company.
	 *
	 * @param	name						The name of the station.
	 * @throws	IllegalArgumentException	if <code>name</code> is null or containing whitespace only;
	 * 										And if there exists a train company in <code>trainCompanyNameList</code>
	 * 										with the same name.
	 */
	public void setName(String name) {
		checkStrings(name);
		name = name.trim();
		
		trainCompanyNameList.remove(this.getName());
		
		if (trainCompanyNameList.contains(name)) {
			throw new IllegalArgumentException("Two instances of TrainCompany cannot have the same name");
		}
		this.name = name;
		trainCompanyNameList.add(this.name);
	}
	
	/**
	 * @return null if there is no route from <code>fromStation</code> to
	 * 			<code>toStation</code> with this TrainCompany.
	 */
	public DirectRoute getDirectRoute(String fromStation, String toStation){
		fromStation = fromStation.trim();
		toStation = toStation.trim();
		try {
			return getRoute(fromStation, toStation);
		} catch (DirectRouteNotFound e) {
			return null;
		}
	}

	/**
	 * @return	The DirectRoute object that was created/updated.
	 * @param	fromStation		The departure station of the route.
	 * @param	toStation		The terminal station of the route.
	 * @param	price			The price of the route.
	 * @throws 	IllegalArgumentException	if <code>fromStation</code> or <code>toStation</code> is null, 
	 * 										or containing whitespace only; 
	 * 										And if <code>price</code> is null or negative.
	 * 										And if <code>fromStation</code> and <code>toStation</code> are
	 * 										the same.
	 */
	public DirectRoute createOrUpdateDirectRoute(String fromStation, String toStation, double price){
		checkStrings(fromStation, toStation);
		checkPrice(price);
		
		fromStation = fromStation.trim();
		toStation = toStation.trim();
		
		DirectRoute newRoute = new DirectRoute(this, fromStation, toStation, price);
		try {
			this.updateRoutePrice(newRoute, price);
		} catch (DirectRouteNotFound e) {
			this.addRoute(newRoute);
		}
		return newRoute;
	}
	
	
	/**
	 * Delete the specified route, if it exists.
	 * 
	 * @param	fromStation					The departure station of the route.
	 * @param	toStation					The terminal station of the route.
	 * @throws	IllegalArgumentException	if <code>fromStation</code> or <code>toStation</code> is null,
	 * 										or containing whitespace only.
	 */
	public void deleteDirectRoute(String fromStation, String toStation){
		checkStrings(fromStation, toStation);
		fromStation = fromStation.trim();
		toStation = toStation.trim();

		try {
			this.deleteRoute(this.getRoute(fromStation, toStation));
		} catch (DirectRouteNotFound e) {
		}
	}
	
	/**
	 * @return	a collection of all routes departing from <code>fromStation</code> offered by the company,
	 * 			if there's any.
	 * @throws	IllegalArgumentException		if the <code>fromStation</code> is null or containing
	 * 											whitespace only.
	 */
	public Collection<DirectRoute> getDirectRoutesFrom(String fromStation){
		checkStrings(fromStation);
		fromStation = fromStation.trim();

		DirectRoute[] directRoutes = (DirectRoute[]) directRouteCollection.toArray(new DirectRoute[directRouteCollection.size()]);
		Collection<DirectRoute> routesFrom = new ArrayList<DirectRoute>();

		DirectRoute directRoute;
		for (int i = 0; i < directRoutes.length; i++) {
			directRoute = directRoutes[i];
			if (directRoute.getFromStation().equals(fromStation)) {
				routesFrom.add(directRoute);
			}
		}
		return routesFrom;
	}
	
	/**
	 * @return	a collection of all routes terminating at <code>toStation</code> offered by the company,
	 * 			if there's any.
	 * @throws 	IllegalArgumentException		if the <code>toStation</code> is null or containing
	 * 											whitespace only.
	 */
	public Collection<DirectRoute> getRoutesTo(String toStation){
		checkStrings(toStation);
		toStation = toStation.trim();

		DirectRoute[] directRoutes = (DirectRoute[]) directRouteCollection.toArray(new DirectRoute[directRouteCollection.size()]);
		Collection<DirectRoute> routesTo = new ArrayList<DirectRoute>();

		DirectRoute directRoute;
		for (int i = 0; i < directRoutes.length; i++) {
			directRoute = directRoutes[i];
			if (directRoute.getToStation().equals(toStation)) {
				routesTo.add((DirectRoute) directRouteCollection.toArray()[i]);
			}
		}
		return routesTo;
	}
	
	/**
	 * @return	a collection of all routes in <code>directRouteCollection</code>.
	 */
	public Collection<DirectRoute> getAllDirectRoutes(){
		return directRouteCollection;
	}
	
	/**
	 * @return	the number of all routes in <code>directRouteCollection</code>.
	 */	
	public int getDirectRoutesCount(){
		return getAllDirectRoutes().size();
	}
	
	/**
	 * @return The number of stations with service by this TrainCompany.
	 * To be clearer:
	 * - Take the union of all stations (from and to) from this.getAllDirectRoutes()
	 * - Count the unique number of stations (i.e. You only count a station
	 *   once, even if there are multiple routes from/to this station) 
	 */
	public int getStationsCount(){
		// Initialize a list of String that'll hold the names of stations
		Collection<String> uniqueStations = new ArrayList<String>();
		
		DirectRoute[] directRoutes = (DirectRoute[]) directRouteCollection.toArray(new DirectRoute[directRouteCollection.size()]);
		DirectRoute directRoute;

		// Check each route
		for (int i = 0; i < directRoutes.length; i++) {
			directRoute = directRoutes[i];
			
			// If the "fromStation" isn't already in our list of stations, then add it.
			// If the name is already in our list of stations, ignore and move to next part of code
			if (!uniqueStations.contains(directRoute.getFromStation())) {
				uniqueStations.add(directRoute.getFromStation());
			}
			
			// If the "toStation" isn't already in our list of stations, then add it.
			// If the name is already in our list of stations, ignore and move to next part of code
			if (!uniqueStations.contains(directRoute.getToStation())) {
				uniqueStations.add(directRoute.getToStation());
			}
		}
		
		// We should get a list with exactly ONE copy of each unique station name, so just return the size of this list
		return uniqueStations.size();
	}

	private static void checkStrings(String... name) {
		if (name == null) {
			throw new IllegalArgumentException("Names must not be null");
		}
		for (int i = 0; i < name.length; i++) {
			if (name[i] == null) {
				throw new IllegalArgumentException("Names must not be null");
			}
			String s = new String(name[i].trim());
			if (s.isEmpty()) {
				throw new IllegalArgumentException("Names must contain at least one non-whitespace character");
			}
		}
	}
	
	private static void checkPrice(double price) {
		if (price < (double) 0) {
			throw new IllegalArgumentException("Price cannot be nonnegative");
		}
	}

	private void addRoute(DirectRoute route) {
		directRouteCollection.add(route);
	}

	private void updateRoutePrice(DirectRoute route, double price) throws DirectRouteNotFound {
		DirectRoute routeToBeUpdated = getRoute(route.getFromStation(), route.getToStation());
		routeToBeUpdated.setPrice(price);
	}

	private void deleteRoute(DirectRoute route) {
		directRouteCollection.remove(route);
	}

	private DirectRoute getRoute(String fromStation, String toStation) throws DirectRouteNotFound {
		DirectRoute[] directRoutes = (DirectRoute[]) directRouteCollection.toArray(new DirectRoute[directRouteCollection.size()]);
		DirectRoute returnValue = null;
		for (int i = 0; i < directRoutes.length; i++) {
			DirectRoute cmp = directRoutes[i];
			if (cmp.getFromStation().equals(fromStation) && cmp.getToStation().equals(toStation)) {
				returnValue = cmp;
				break;
			}
		}
		if (returnValue == null) {
			throw new DirectRouteNotFound();
		} else {
			return returnValue;
		}
	}
}
