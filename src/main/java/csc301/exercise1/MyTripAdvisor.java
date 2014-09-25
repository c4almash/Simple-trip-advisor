package csc301.exercise1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


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
		
		helper(graph, allRoutes, fromStation, toStation);
		
		if (graph.containsKey(toStation)) {
			return graph.get(toStation);
		}
		else {
			return -1;
		}
	}
	
	/**
	 * @param graph Graph of our nodes, where each node is a station 
	 * and each edge is the price to move between the two nodes.
	 * @param allRoutes Collection of all routes possible.
	 * @param fromStation Source.
	 * @param toStation Destination.
	 */
	public void helper(HashMap<String, Double> graph, Collection<DirectRoute> allRoutes, String fromStation, String toStation) {
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
				System.out.println(graph + " ||| " + node);
				allRoutes.remove(node);
				route = allRoutes.iterator();
			}
		}
		System.out.println("Remaining routes:" + allRoutes);
		if (allRoutes.isEmpty()) {
			return;
		}
		
		if (!neighbours.isEmpty()) {
			double lowestCost = graph.get(neighbours.get(0));
			
			System.out.println("All neighbours include: " + neighbours);
			
			//Get the lowest cost of all possible routes from "fromStation"
			for (int i = 0; i < neighbours.size(); i++) {
				if (graph.get(neighbours.get(i)) < lowestCost) {
					lowestCost = graph.get(neighbours.get(i));
				}
			}
			
			//System.out.println("Lowest price is: " + lowestCost);
			//System.out.println("Guys to check out: " + neighbours);
			for (int i = 0; i < neighbours.size(); i++) {
				if (graph.get(neighbours.get(i)) != lowestCost) {
					neighbours.remove(i);
				}
				else {
					helper(graph, allRoutes, neighbours.get(i), toStation);				
				}
			}

		}
	}
	
	public static void main (String [] args) {
		MyTripAdvisor w = new MyTripAdvisor();
		TrainCompany e = new TrainCompany("Via");
		/*e.addRoute("A", "B", 10);
		e.addRoute("A", "C", 10);
		e.addRoute("B", "C", 15);
		e.addRoute("B", "D", 20);
		e.addRoute("C", "B", 10);
		e.addRoute("D", "C", 5);*/
		e.addRoute("A", "B", 10);
		e.addRoute("B", "C", 5);
		e.addRoute("B", "D", 20);
		e.addRoute("B", "F", 5);
		e.addRoute("C", "A", 15);
		e.addRoute("D", "F", 20);
		e.addRoute("F", "D", 10);
		w.addTrainCompany(e);
		
		TrainCompany r = new TrainCompany("GO");
		r.addRoute("A", "C", 20);
		//w.addTrainCompany(r);
		System.out.println("The cheapest price is : " + w.getCheapestPrice("A", "D"));
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
