package csc301.exercise1;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import csc301.exercise1.util.Utils;


public class MyTripAdvisor {

	private List<TrainCompany> trainCompanyList = new ArrayList<TrainCompany>();
	
	public MyTripAdvisor() {
	}
	
	
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
		Collection<DirectRoute> allRoutes = new ArrayList<DirectRoute>();
		
		Iterator<DirectRoute> route;
		//For each TrainCompany
		for (int i = 0; i < trainCompanyList.size(); i++) {
			
			//Add it's DirectRoutes to allRoutes
			allRoutes.addAll(trainCompanyList.get(i).getAllDirectRoutes());
			
			//Check if it offers a DirectRoute that starts from "fromStation"
			route = trainCompanyList.get(i).getAllDirectRoutes().iterator();
			while (route.hasNext()) {
				if (route.next().getFromStation().equals(fromStation)) {
					
					//Found it! Put the cost to get to "fromStation" from "fromStation" as 0.0
					graph.put(fromStation, 0.0);
					break;
				}
			}
			
			//If we didn't find it, return -1
			if (!graph.containsKey(fromStation)) {
				return -1;
			}
		}

		dijkstra(graph, allRoutes, fromStation, toStation, null);
		
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
		Collection<DirectRoute> allRoutes = new ArrayList<DirectRoute>();
		
		Iterator<DirectRoute> route;
		//For each TrainCompany
		for (int i = 0; i < trainCompanyList.size(); i++) {
			
			//Add it's DirectRoutes to allRoutes
			allRoutes.addAll(trainCompanyList.get(i).getAllDirectRoutes());
			
			//Check if it offers a DirectRoute that starts from "fromStation"
			route = trainCompanyList.get(i).getAllDirectRoutes().iterator();
			while (route.hasNext()) {
				if (route.next().getFromStation().equals(fromStation)) {
					
					//Found it! Put the cost to get to "fromStation" from "fromStation" as 0.0
					graph.put(fromStation, 0.0);
					break;
				}
			}
			
			//If we didn't find it, return -1
			if (!graph.containsKey(fromStation)) {
				return null;
			}
		}
		List<HashMap<DirectRoute, HashMap<String, Double>>> trip = new ArrayList<HashMap<DirectRoute, HashMap<String, Double>>>();
		dijkstra(graph, allRoutes, fromStation, toStation, trip);
		List<DirectRoute> tripList = new ArrayList<DirectRoute>();
		
		
		if (graph.containsKey(toStation)) {
			helper(trip, graph, fromStation, toStation, tripList);
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
		HashMap<String, Double> tempGraph;
		Iterator<DirectRoute> route = allRoutes.iterator();
		List<String> neighbours = new ArrayList<String>();
		DirectRoute node;
		while (route.hasNext()) {
			node = route.next();
			if (node.getFromStation().equals(fromStation)) {
				if (!graph.containsKey(node.getToStation()) ||
						graph.get(node.getToStation()) > graph.get(node.getFromStation()) + node.getPrice()) {
					graph.put(node.getToStation(), graph.get(node.getFromStation()) + node.getPrice());
				}
				if (!neighbours.contains(node.getToStation())) {
					neighbours.add(node.getToStation());
				}
				
				if (trip != null) {
					HashMap<DirectRoute, HashMap<String, Double>> temp = new HashMap<DirectRoute, HashMap<String, Double>>();
					tempGraph = (HashMap<String, Double>) graph.clone();
					temp.put(node, tempGraph);
					trip.add(temp);
				}
				//System.out.println(graph + " ||| " + node);
				allRoutes.remove(node);
				route = allRoutes.iterator();
			}
		}
		//Exit condition
		if (fromStation.equals(toStation) || allRoutes.isEmpty()) {
			return;
		}
		
		if (!neighbours.isEmpty()) {			
			for (int i = 0; i < neighbours.size(); i++) {
				dijkstra(graph, allRoutes, neighbours.get(i), toStation, trip);
			}
		}
	}
	
	public void helper(List<HashMap<DirectRoute, HashMap<String, Double>>> trip, HashMap<String, Double> graph, String fromStation, String toStation, List<DirectRoute> tripList) {
		if (fromStation.equals(toStation)) {
			return;
		}
		//System.out.println("GRAPH IS " + graph);
		for (int i = 0; i < trip.size(); i++) {
			Iterator<DirectRoute> route = trip.get(i).keySet().iterator();
			DirectRoute key = route.next();
			if (key.getToStation().equals(toStation) && trip.get(i).get(key).get(toStation) == graph.get(toStation)) {
				tripList.add(0, key);
				toStation = key.getFromStation();
				break;
				
			}
		}
		helper(trip, graph, fromStation, toStation, tripList);
		//System.out.println(trip);
	}
	
	
	
	
	

	public static void main (String [] args) throws IOException {
		MyTripAdvisor w = new MyTripAdvisor();
		TrainCompany e = new TrainCompany("Via");
		/*e.addRoute("A", "B", 10);
		e.addRoute("A", "C", 10);
		e.addRoute("B", "C", 15);
		e.addRoute("B", "D", 20);
		e.addRoute("C", "B", 10);
		e.addRoute("D", "C", 5);*/
		
		/*e.addRoute("A", "B", 10);
		e.addRoute("B", "C", 5);
		e.addRoute("B", "D", 20);
		e.addRoute("B", "F", 5);
		e.addRoute("C", "A", 15);
		e.addRoute("D", "F", 20);
		e.addRoute("F", "D", 10);*/
		
/*		e.addRoute("A", "B", 10);
		e.addRoute("B", "D", 20);
		e.addRoute("B", "F", 5);
		e.addRoute("F", "D", 10);*/
		
/*		e.addRoute("A", "B", 20);
		e.addRoute("B", "C", 5);
		e.addRoute("D", "B", 10);*/
		
/*		e.addRoute("A", "C", 10);
		e.addRoute("A", "D", 15);
		e.addRoute("B", "C", 25);
		e.addRoute("C", "A", 20);
		e.addRoute("C", "D", 5);
		e.addRoute("C", "F", 15);
		e.addRoute("D", "B", 35);
		e.addRoute("D", "E", 20);
		e.addRoute("E", "D", 15);
		e.addRoute("E", "F", 35);
		e.addRoute("F", "B", 25);
		e.addRoute("F", "C", 10);
		e.addRoute("F", "E", 5);
		e.addRoute("G", "B", 40);*/
		
		w.addTrainCompany(e);
		
		TrainCompany r = new TrainCompany("GO");
		r.addRoute("A", "C", 20);
		//w.addTrainCompany(r);
		//System.out.println("The cheapest price is : " + w.getCheapestPrice("B", "D"));
		//System.out.println("The cheapest trip is : " + w.getCheapestTrip("B", "D"));
		TrainCompany fastTrain = new TrainCompany("FastTrain");
		TrainCompany swiftRail = new TrainCompany("SwiftRail");
		fastTrain.addRoute("Toronto", "Ottawa" ,31);
		fastTrain.addRoute("Ottawa", "Montreal", 25);
		swiftRail.addRoute("Toronto", "Ottawa", 30);
		swiftRail.addRoute("Ottawa", "Montreal", 26);
		MyTripAdvisor advisor = new MyTripAdvisor();
		advisor.addTrainCompany(fastTrain);
		advisor.addTrainCompany(swiftRail);
		System.out.println(advisor.getCheapestTrip("Toronto", "Montreal"));
		//System.out.println(fastTrain);
	}
}

