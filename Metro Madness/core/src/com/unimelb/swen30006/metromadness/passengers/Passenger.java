package com.unimelb.swen30006.metromadness.passengers;

import java.util.Stack;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;

public class Passenger implements PassengerRouter{

	private String destination;
	private Stack<String> lineRoute;
	private Stack<String> stationRoute;
	private Stack<String> direction;
	@SuppressWarnings("unused")
	private float travelTime;
	private boolean reachedDestination;
	
	/**
	 * Passenger constructor.
	 * @param destination the Passenger's destination.
	 * @param lineRoute the Passenger's route lines.
	 * @param stationRoute the Passenger's route stations.
	 * @param direction the Passenger's route directions.
	 */
	public Passenger(String destination, Stack<String> lineRoute, Stack<String> stationRoute, Stack<String> direction){
		this.destination = destination;
		this.lineRoute = lineRoute;
		this.stationRoute = stationRoute;
		this.direction = direction;
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
	
	/**
	 * Whether should the Passenger leave the Train at the current Station or not.
	 */
	@Override
	public boolean shouldLeave(String station){
		// Check if the station is the next one in the route.
		if(stationRoute.peek().equals(station)){
			stationRoute.pop();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Whether should the Passenger board the Train servicing the Line in a certain direction or not.
	 */
	@Override
	public boolean shouldBoard(String line, boolean forward){
		// Transform direction to string for comparison
		String direction;
		if(forward)
			direction = "forward";
		else
			direction = "backward";
		// Check if the Train is traveling on the line and direction of the Passenger's route.
		try{
			if(this.lineRoute.peek().equals(line)&&this.direction.peek().equals(direction)){
				this.lineRoute.pop();
				this.direction.pop();
				return true;
			}
			else{
				return false;
			}
		}catch(Exception e){
			// Something bad happened and there are no stations left in stack.
			// Maybe it's a bum trying to get some sleep in that Train.
			return false;
		}
	}

	public String getDestination(){
		return this.destination;
	}
	
	/**
	 * Let the Passenger exit the station, reached destination!
	 */
	public void exit(){
		reachedDestination = true;
	}
}
