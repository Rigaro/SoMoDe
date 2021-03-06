/**
 * Amy Lawson - 693920
 * Joshua Moors - 542065
 * Ricardo Garcia Rosas - 643952
 */
package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;
import java.util.Stack;

import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class PassengerGenerator {
		
	// The lines available
	private ArrayList<Line> lines;
	
	/**
	 * PassengerGenerator constructor.
	 * @param lines the lines that are available in the network.
	 */
	public PassengerGenerator(ArrayList<Line> lines){
		this.lines = lines;
	}
	
	/**
	 * Creates a bunch of new Passengers.
	 * @param beginning the beginning Station.
	 * @return the new group of Passengers.
	 */
	public Passenger[] generatePassengers(Station beginning, int maxVolume){
		int count = (int) (Math.random()*maxVolume);
		Passenger[] passengers = new Passenger[count];
		for(int i=0; i<count; i++){
			passengers[i] = generatePassenger(beginning);
		}
		return passengers;
	}
	
	/**
	 * Creates a new Passenger with a random Route.
	 * @param beginning the beginning Station.
	 * @return the new Passenger.
	 */
	public Passenger generatePassenger(Station beginning){
		// Get the start lines
		ArrayList<Line> startLines = beginning.getLines();
		Line startLine = startLines.get(0);
		// Pick a random line
		Line endLine = this.lines.get((int)(Math.random()*(this.lines.size()-1)));
		// Check if no line change needed
		boolean lineChange = true;
		for(Line line : startLines){
			if(line.equals(endLine)){
				startLine = endLine;
				lineChange = false;
				break;
			}
		}
		// Pick a random station from that line
		ArrayList<Station> stations = endLine.getStations();
		Station destination = stations.get((int)(Math.random()*(stations.size()-1)));
		// Create route
		Stack<String> direction = new Stack<String>();
		Stack<String> lineRoute = new Stack<String>();
		Stack<String> stationRoute = new Stack<String>();
		// And destination to route.
		stationRoute.push(destination.getName());
		lineRoute.push(endLine.getName());
		if(lineChange){
			// Add the 3rd last station to route as the transfer hub since all Lines
			// go through all of the City Loop stations.
			ArrayList<Station> lineStations = endLine.getStations();
			stationRoute.push(lineStations.get(lineStations.size()-4).getName());
			// Add the start line
			lineRoute.push(startLine.getName());
			// Move forward to the transfer hub and then change direction.
			direction.push("backward");
			direction.push("forward");
		}
		else{
			// Decide direction.
			if(endLine.getStations().indexOf(beginning)<endLine.getStations().indexOf(destination)){
				direction.push("forward");
			}
			else{
				direction.push("backward");
			}
		}
		// Create the Passenger
		return new Passenger(destination.getName(),lineRoute,stationRoute,direction);
	}	
}
