package csc301.exercise1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
		//Use a HashMap (a dictionary) to implement Dijkstra's algorithm
		//HashMap<String, Double>
		//Key: (String) Name of station
		//Value: (Double) Price to get from "fromStation" to Key
		HashMap<String, Double> graph = new HashMap<String, Double>();
		
		//Stores all the DirectRoute objects from each TrainCompany in MyTripAdvisor	
		Collection<DirectRoute> allRoutes = getDirectRoutes(fromStation, toStation);
		if (invalidFromStation(allRoutes, graph, fromStation)) {
			//Invalid fromStation, return -1, don't apply dijkstra's algorithm
			return -1;
		}
		
		//Run Dijkstra's algorithm
		//null :- Don't generate resources used for getCheapestTrip
		dijkstra(graph, allRoutes, fromStation, toStation, null);
		
		//Found a route
		if (graph.containsKey(toStation)) {
			return graph.get(toStation);
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Return a cheapest trip from <code>fromStation</code> to <code>toStation</code>,
	 * as a list of DirectRoute objects.
	 * Return null, if there is no trip between the two specified stations.
	 */
	public List<DirectRoute> getCheapestTrip(String fromStation, String toStation){
		//Use a HashMap (a dictionary) to implement Dijkstra's algorithm
		//HashMap<String, Double>
		//Key: (String) Name of station
		//Value: (Double) Price to get from "fromStation" to Key
		HashMap<String, Double> graph = new HashMap<String, Double>();
		
		//Stores all the DirectRoute objects from each TrainCompany in MyTripAdvisor	
		Collection<DirectRoute> allRoutes = getDirectRoutes(fromStation, toStation);
		if (invalidFromStation(allRoutes, graph, fromStation)) {
			//Invalid fromStation, return null, don't apply dijkstra's algorithm
			return null;
		}
		
		//Run Dijkstra's algorithm
		//Generate resources used in cheapestTripHelper and store them in "trip".
		//"trip" is a List of HashMaps, Lists can be ordered whereas HashMaps cannot. We want to
		//have an ordered List of HashMaps. This is crucial because we will traverse through this List in order.
		//These HashMaps maps DirectRoute to graphs (which are implemented as HashMaps)
		List<HashMap<DirectRoute, HashMap<String, Double>>> trip = new ArrayList<HashMap<DirectRoute, HashMap<String, Double>>>();
		dijkstra(graph, allRoutes, fromStation, toStation, trip);

		if (graph.containsKey(toStation)) {
			List<DirectRoute> tripList = new ArrayList<DirectRoute>();
			cheapestTripHelper(trip, graph, fromStation, toStation, tripList);
			return tripList;
		}
		else {
			return null;
		}
	}	
	
	/**
	 * @param graph Graph of our nodes, where each node is a station 
	 * and each edge is the price to move between the two nodes.
	 * @param allRoutes Collection of all routes possible.
	 * @param fromStation Source.
	 * @param toStation Destination.
	 */
	@SuppressWarnings("unchecked")
	public void dijkstra(HashMap<String, Double> graph, Collection<DirectRoute> allRoutes, String fromStation, String toStation, List<HashMap<DirectRoute, HashMap<String, Double>>> trip) {
		//Temporary graph used as a clone of graph
		HashMap<String, Double> tempGraph;
		List<String> neighbours = new ArrayList<String>();
		DirectRoute node;
		Iterator<DirectRoute> route = allRoutes.iterator();
		//Loops through every DirectRoute
		while (route.hasNext()) {
			node = route.next();
			
			//Find a station that starts from our "fromStation"
			if (node.getFromStation().equals(fromStation)) {
				
				//Update our graph with the info provided by this DirectRoute
				if (!graph.containsKey(node.getToStation()) ||
						graph.get(node.getToStation()) > graph.get(node.getFromStation()) + node.getPrice()) {
					graph.put(node.getToStation(), graph.get(node.getFromStation()) + node.getPrice());
				}
				
				//Keep track of neighbouring stations
				if (!neighbours.contains(node.getToStation())) {
					neighbours.add(node.getToStation());
				}
				
				//After checking this DirectRoute, we remove it from our list of remaining DirectRoutes to check
				allRoutes.remove(node);
				route = allRoutes.iterator();
				
				//Generate resources used for the cheapestTripHelper function
				if (trip != null) {
					HashMap<DirectRoute, HashMap<String, Double>> temp = new HashMap<DirectRoute, HashMap<String, Double>>();
					tempGraph = (HashMap<String, Double>) graph.clone();
					temp.put(node, tempGraph);
					trip.add(temp);
				}
			}
		}
		
		//Exit condition
		if (fromStation.equals(toStation) || allRoutes.isEmpty()) {
			return;
		}
		
		//Recursive call for each neighbouring station
		if (!neighbours.isEmpty()) {			
			for (int i = 0; i < neighbours.size(); i++) {
				dijkstra(graph, allRoutes, neighbours.get(i), toStation, trip);
			}
		}
	}
	
	public void cheapestTripHelper(List<HashMap<DirectRoute, HashMap<String, Double>>> trip, HashMap<String, Double> graph, String fromStation, String toStation, List<DirectRoute> tripList) {
		//Exit condition
		if (fromStation.equals(toStation)) {
			return;
		}
		
		//Find the first DirectRoute of all our DirectRoutes which has the same toStation as "toStation" argument
		//AND is the best possible route to get to "toStation".
		//Set the fromStation of this DirectRoute as the new "toStation" and recurse.
		for (int i = 0; i < trip.size(); i++) {
			Iterator<DirectRoute> route = trip.get(i).keySet().iterator();
			DirectRoute key = route.next();
			if (key.getToStation().equals(toStation) && trip.get(i).get(key).get(toStation) == graph.get(toStation)) {
				tripList.add(0, key);
				toStation = key.getFromStation();
				break;
			}
		}
		cheapestTripHelper(trip, graph, fromStation, toStation, tripList);
	}
	
	public Collection<DirectRoute> getDirectRoutes(String fromStation, String toStation) {	
		//Stores all the DirectRoute objects from each TrainCompany in MyTripAdvisor
		Collection<DirectRoute> allRoutes = new ArrayList<DirectRoute>();
		
		//For each TrainCompany
		for (int i = 0; i < trainCompanyList.size(); i++) {
			
			//Add it's DirectRoutes to allRoutes
			allRoutes.addAll(trainCompanyList.get(i).getAllDirectRoutes());
		}
		return allRoutes;
	}
	
	public boolean invalidFromStation(Collection<DirectRoute> allRoutes, HashMap<String, Double> graph, String fromStation) {
		//Iterator for the allRoutes Collection
		Iterator<DirectRoute> route = allRoutes.iterator();

		while (route.hasNext()) {
			if (route.next().getFromStation().equals(fromStation)) {
				
				//Found it! Put the cost to get to "fromStation" from "fromStation" as 0.0
				graph.put(fromStation, 0.0);
				break;
			}
		}
		return !graph.containsKey(fromStation);
	}
}

