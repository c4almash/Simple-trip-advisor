package csc301.exercise1;

import java.util.ArrayList;
import java.util.Collection;

public class TrainCompany {
	
	//Global static list to keep track of all the names used by train companies so far
	//So that this doesn't happen:
	//TrainCompany c1 = new TrainCompany("Via");
	//TrainCompany c2 = new TrainCompany("Via");
	public static Collection<String> trainCompanyNameList= new ArrayList<String>();
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
		if (this.routeExists(newRoute)) {
			this.updateRouteWithPrice(newRoute, price);
		} else {
			this.addRoute(newRoute);
		}
		return newRoute;
	}
	
	
	/**
	 * Delete the specified route, if it exists.
	 */
	public void deleteDirectRoute(String fromStation, String toStation){
		this.deleteRoute(this.getRouteWithFromStationAndToStation(fromStation, toStation));
	}

	/**
	 * @return null if there is no route from <code>fromStation</code> to
	 * 			<code>toStation</code> with this TrainCompany.
	 */
	public DirectRoute getDirectRoute(String fromStation, String toStation){
		checkError(fromStation, toStation);
		return this.getRouteWithFromStationAndToStation(fromStation, toStation);
	}
	
	public Collection<DirectRoute> getDirectRoutesFrom(String fromStation){
		checkError(fromStation);
		//Initialize a temporary collection
		Collection<DirectRoute> temp = new ArrayList<DirectRoute>();
		
		//Check every DirectRoute for this TrainCompany and if any of them start from "fromStation" then
		//add that DirectRoute object into our temp collection. Then return the temp collection after
		//we checked every DirectRoute object
		for (int i = 0; i < directRouteCollection.size(); i++) {
			if (((DirectRoute) directRouteCollection.toArray()[i]).getFromStation().equals(fromStation.trim())) {
				temp.add((DirectRoute) directRouteCollection.toArray()[i]);
			}
		}
		return temp;
	}
	
	public Collection<DirectRoute> getRoutesTo(String toStation){
		checkError(toStation);
		Collection<DirectRoute> temp = new ArrayList<DirectRoute>();
		for (int i = 0; i < directRouteCollection.size(); i++) {
			if (((DirectRoute) directRouteCollection.toArray()[i]).getToStation().equals(toStation.trim())) {
				temp.add((DirectRoute) directRouteCollection.toArray()[i]);
			}
		}
		return temp;
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
		//Initialize a list of String that'll hold the names of stations
		Collection<String> uniqueStations = new ArrayList<String>();
		
		//Check each route
		for (int i = 0; i < getAllDirectRoutes().size(); i++) {
			
			//If the "fromStation" isn't already in our list of stations, then add it.
			//If the name is already in our list of stations, ignore and move to next part of code
			if (!uniqueStations.contains(((DirectRoute) getAllDirectRoutes().toArray()[i]).getFromStation())) {
				uniqueStations.add(((DirectRoute) getAllDirectRoutes().toArray()[i]).getFromStation());
			}
			
			//If the "toStation" isn't already in our list of stations, then add it.
			//If the name is already in our list of stations, ignore and move to next part of code
			if (!uniqueStations.contains(((DirectRoute) getAllDirectRoutes().toArray()[i]).getToStation())) {
				uniqueStations.add(((DirectRoute) getAllDirectRoutes().toArray()[i]).getToStation());
			}
		}
		
		//We should get a list with exactly ONE copy of each unique station name, so just return the size of this list
		return uniqueStations.size();
	}

	//Error checking helper function
	public static void checkError(String... name) {
		if (name == null) {
			throw new IllegalArgumentException("names must not be null");
		}
		for(int i = 0; i < name.length; i++) {
			if (name[i] == null) {
				throw new IllegalArgumentException("names must not be null");
			}
			name[i] = name[i].trim();
			if (name[i].isEmpty()) {
				throw new IllegalArgumentException("names must contain at least one non-whitespace character");
			}
		}
	}

	private boolean routeExists(DirectRoute route) {
		return directRouteCollection.contains(route);
	}

	private void addRoute(DirectRoute route) {
		directRouteCollection.add(route);
	}

	private void updateRouteWithPrice(DirectRoute route, double price) {
		DirectRoute routeToBeUpdated = getRouteWithFromStationAndToStation(
				route.getFromStation(), route.getToStation());
		routeToBeUpdated.setPrice(price);
	}

	private void deleteRoute(DirectRoute route) {
		directRouteCollection.remove(route);
	}

	private DirectRoute getRouteWithFromStationAndToStation(String fromStation, String toStation) {
		DirectRoute[] directRoutes = (DirectRoute[]) directRouteCollection.toArray();
		DirectRoute returnValue = null;
		for (int i = 0; i < directRoutes.length; i++) {
			DirectRoute cmp = directRoutes[i];
			if (cmp.getFromStation() == fromStation && cmp.getToStation() == toStation) {
				returnValue = cmp;
				break;
			}
		}
		return returnValue;
	}
}
