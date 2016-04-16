package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class PassengerGenerator {
	
	// The router
	private PassengerRouter router;
	
	// The lines available
	private ArrayList<Line> lines;
	
	// The max volume
	private float maxVolume;
	
	public PassengerGenerator(ArrayList<Line> lines, float max, PassengerRouter router){
		this.lines = lines;
		this.maxVolume = max;
		this.router = router;
	}
	
	public Passenger[] generatePassengers(Station beginning){
		int count = (int) (Math.random()*maxVolume);
		Passenger[] passengers = new Passenger[count];
		for(int i=0; i<count; i++){
			passengers[i] = generatePassenger(beginning);
		}
		return passengers;
	}
	
	public Passenger generatePassenger(Station beginning){
		// Pick a random line
		Line line = this.lines.get((int)Math.random()*(this.lines.size()-1));
		// Pick a random station from that line
		ArrayList<Station> stations = line.getStations();
		Station destination = stations.get((int)Math.random()*(stations.size()-1));		
		// Create the Passenger
		return new Passenger(beginning.getName(), destination.getName(), this.router);
	}
	
}
