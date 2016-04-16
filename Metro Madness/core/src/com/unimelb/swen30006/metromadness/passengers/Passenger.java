package com.unimelb.swen30006.metromadness.passengers;

import java.util.Stack;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;

public class Passenger {

	private String begining;
	private String destination;
	private Stack<String> stationRoute;
	private float travelTime;
	private boolean reachedDestination;
	
	/**
	 * Passenger constructor
	 * @param start
	 * @param end
	 */
	public Passenger(String start, String end, PassengerRouter router){
		this.begining = start;
		this.destination = end;
		this.stationRoute.push(destination);
		this.stationRoute.push("Flinders St");
		this.reachedDestination = false;
		this.travelTime = 0;
	}
	
	/**
	 * Update travel time.
	 * @param time
	 */
	public void update(float time){
		if(!this.reachedDestination){
			this.travelTime += time;
		}
	}

	public String getDestination(){
		return destination;
	}
	
	public void exit(){
		reachedDestination = true;
	}
}
