package csc301.exercise1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import csc301.exercise1.util.Utils;

class DirectRouteNotFound extends Exception
{
      public DirectRouteNotFound() {}

      public DirectRouteNotFound(String message)
      {
         super(message);
      }
 }

public class TrainCompany {
	public static Collection<String> trainCompanyNameList = new ArrayList<String>();
	private Collection<DirectRoute> directRouteCollection = new ArrayList<DirectRoute>();
	private String name;

	public TrainCompany(String name) {
		setName(name);
	}
		
	@Override
	public String toString() {
		return String.format("%s, offering %d routes between %d stations", 
				getName(), getDirectRoutesCount(), getStationsCount());
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		checkError(name);
		if (trainCompanyNameList.contains(name.trim())) {
			throw new IllegalArgumentException("Two instances of TrainCompany cannot have the same name");
		}
		this.name = name.trim();
		trainCompanyNameList.add(this.name);
	}
	
	/**
	 * @return The DirectRoute object that was created/updated.
	 */
	public DirectRoute createOrUpdateDirectRoute(String fromStation, String toStation, double price){
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
	 */
	public void deleteDirectRoute(String fromStation, String toStation){
		checkError(fromStation, toStation);
		try {
			this.deleteRoute(this.getRoute(fromStation, toStation));
		} catch (DirectRouteNotFound e) {
		}
	}

	/**
	 * @return null if there is no route from <code>fromStation</code> to
	 * 			<code>toStation</code> with this TrainCompany.
	 */
	public DirectRoute getDirectRoute(String fromStation, String toStation){
		checkError(fromStation, toStation);
		try {
			return this.getRoute(fromStation, toStation);
		} catch (DirectRouteNotFound e) {
			return null;
		}
	}
	
	public Collection<DirectRoute> getDirectRoutesFrom(String fromStation){
		checkError(fromStation);

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
	
	public Collection<DirectRoute> getRoutesTo(String toStation){
		checkError(toStation);

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
	
	public Collection<DirectRoute> getAllDirectRoutes(){
		return directRouteCollection;
	}
		
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

	private static void checkError(String... name) {
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
