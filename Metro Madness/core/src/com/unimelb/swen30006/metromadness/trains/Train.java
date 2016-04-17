package com.unimelb.swen30006.metromadness.trains;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;

public class Train {

	// The state that a train can be in 
	private enum State {
		IN_STATION, READY_DEPART, ON_ROUTE, WAITING_ENTRY, FROM_DEPOT
	}

	// Constants
	private static final int MAX_TRIPS=4;
	private static final Color FORWARD_COLOUR = Color.ORANGE;
	private static final Color BACKWARD_COLOUR = Color.VIOLET;
	private static final float TRAIN_WIDTH=4;
	private static final float TRAIN_LENGTH = 6;
	private static final float TRAIN_SPEED=50f;

	// The line that this is traveling on
	private String trainLine;

	// Passenger Information
	private ArrayList<Passenger> passengers;
	private float departureTimer;
	private int maxPassenger;
	
	// Station and track and position information
	private Station station; 
	private int trackId;
	private Point2D.Float pos;

	// Direction and direction
	private boolean forward;
	private State state;

	// State variables
	private int numTrips;
	private boolean disembarked;
	private boolean embarked;

	/**
	 * Train constructor.
	 * @param trainLine The Line the train is going to service.
	 * @param start The Station where the train will start its service.
	 * @param forward The direction of the train.
	 */
	public Train(String trainLine, Station start, boolean forward, int maxPassenger){
		this.trainLine = trainLine;
		this.station = start;
		this.state = State.FROM_DEPOT;
		this.forward = forward;
		this.maxPassenger = maxPassenger;
		this.passengers = new ArrayList<Passenger>();
	}

	/**
	 * Update train state and simulation.
	 * @param delta the time delta to update the train simulation.
	 */
	public void update(float delta){
		// Update all passengers
		for(Passenger p: this.passengers){
			p.update(delta);
		}
		
		// Update the state
		switch(this.state) {
		case FROM_DEPOT:
			// We have our station initialized we just need to retrieve the next track, enter the
			// current station offically and mark as in station
			try {
				if(this.station.canEnter(this.trainLine)){
					this.station.enter(this);
					this.pos = (Point2D.Float) this.station.getPosition().clone();
					this.state = State.IN_STATION;
					this.disembarked = false;
					this.embarked = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		case IN_STATION:

			// When in station we want to disembark passengers 
			// embark the waiting passenger
			// and wait 10 seconds for incoming passgengers
			if(!this.disembarked){
				this.station.disembark(this.processDisembarking());
				this.departureTimer = this.station.getDepartureTime();
				this.disembarked = true;
				this.embarked = false;
			} else if(!this.embarked){
				// Tell station to process waiting to embark Passengers.
				try {
					this.station.processEmbarking(this);
				}
				catch(Exception e){
					// Critical failure
					return;
				}
				this.embarked = true;
			} else {
				// Count down if departure timer. 
				if(this.departureTimer>0){
					this.departureTimer -= delta;
				} else {
					// We are ready to depart 
					this.state = State.READY_DEPART;
					break;
				}
			}
			break;
		case READY_DEPART:

			// When ready to depart, check that station allows departure
			try{
				if(this.station.canDepart(this)){
					this.station.depart(this);
					this.state = State.ON_ROUTE;
				}		
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		case ON_ROUTE:

			// Checkout if we have reached the new station
			if(this.pos.distance(this.station.getPosition()) < 10 ){
				this.state = State.WAITING_ENTRY;
			} else {
				move(delta);
			}
			break;
		case WAITING_ENTRY:

			// Waiting to enter, we need to check the station has room and if so
			// then we need to enter, otherwise we just wait
			try {
				if(this.station.canEnter(this.trainLine)){
					this.station.enter(this);
					this.pos = (Point2D.Float) this.station.getPosition().clone();
					this.state = State.IN_STATION;
					this.disembarked = false;
					this.embarked = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}


	}

	/**
	 * Move the simulated Train in the travel direction.
	 * @param delta the time delta to update the train simulation.
	 */
	public void move(float delta){
		// Work out where we're going
		float angle = angleAlongLine(this.pos.x,this.pos.y,this.station.getPosition().x,this.station.getPosition().y);
		float newX = this.pos.x + (float)( Math.cos(angle) * delta * TRAIN_SPEED);
		float newY = this.pos.y + (float)( Math.sin(angle) * delta * TRAIN_SPEED);
		this.pos.setLocation(newX, newY);
	}
	
	public boolean canEmbark(){
		if(this.passengers.size()>=this.maxPassenger)
			return false;
		else
			return true;
	}

	/**
	 * Embark a given passenger to the train. Not properly implemented (kind of abstract).
	 * @param p The Passenger to embark the Train.
	 * @throws Exception Train full Exception.
	 */
	public void embark(Passenger passenger) throws Exception {
		if(this.passengers.size()<this.maxPassenger){
			this.passengers.add(passenger);
		}
		else
			throw new Exception();
	}

	/**
	 * Disembarks the Passengers that have the current Station as final or partial destination.
	 * @return The List of Passengers disembarking the Train.
	 */
	public ArrayList<Passenger> processDisembarking(){
		ArrayList<Passenger> disembarking = new ArrayList<Passenger>();
		Iterator<Passenger> iterator = this.passengers.iterator();
		while(iterator.hasNext()){
			Passenger p = iterator.next();
			if(p.shouldLeave(this.station.getName())){
				disembarking.add(p);
				iterator.remove();
			}
		}
		return disembarking;
	}

	@Override
	public String toString() {
		return "Train [line=" + this.trainLine +", departureTimer=" + departureTimer + ", pos=" + pos + ", forward=" + forward + ", state=" + state
				+ ", numTrips=" + numTrips + ", disembarked=" + disembarked + "]";
	}

	/**
	 * Is the train in a Station?
	 * @return whether the train is in a Station (true) or not (false).
	 */
	public boolean inStation(){
		return (this.state == State.IN_STATION || this.state == State.READY_DEPART);
	}
	
	public float angleAlongLine(float x1, float y1, float x2, float y2){	
		return (float) Math.atan2((y2-y1),(x2-x1));
	}

	public void render(ShapeRenderer renderer){
		if(!this.inStation()){
			Color col = this.forward ? FORWARD_COLOUR : BACKWARD_COLOUR;
			renderer.setColor(col);
			renderer.circle(this.pos.x, this.pos.y, TRAIN_WIDTH);
		}
	}
	
	public String getTrainLine(){
		return trainLine;
	}
	
	public boolean getDirection(){
		return forward;
	}
	
	public int getTrackId(){
		return trackId;
	}
	
	public void setDirection(boolean forward){
		this.forward = forward;
	}
	
	public void updateNext(int trackId, Station station){
		this.trackId = trackId;
		this.station = station;
	}
	
}
