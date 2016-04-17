package com.unimelb.swen30006.metromadness.stations;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.passengers.PassengerGenerator;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;
import com.unimelb.swen30006.metromadness.trains.Train;

public class Station {
	
	private static final int PLATFORMS=2;
	
	private Point2D.Float position;
	private static final float RADIUS=6;
	private static final int NUM_CIRCLE_STATMENTS=100;
	private static final int MAX_LINES=3;
	private String name;
	private int maxPassenger;
	private ArrayList<Line> lines;
	private ArrayList<Train> trains;
	private static final float DEPARTURE_TIME = 2;
	private PassengerGenerator generator;
	public ArrayList<Passenger> waiting;

	/**
	 * Station constructor.
	 * @param x The x position.
	 * @param y The y position.
	 * @param router The PassengerRouter to be used.
	 * @param name The Station's name.
	 */
	public Station(float x, float y, String name, int maxPassenger){
		this.name = name;
		this.maxPassenger = maxPassenger;
		this.position = new Point2D.Float(x,y);
		this.lines = new ArrayList<Line>();
		this.trains = new ArrayList<Train>();
		this.waiting = new ArrayList<Passenger>();
	}
	
	/**
	 * Register a Line serviced by the Station in its "database".
	 * @param l The Line to be registered.
	 */
	public void registerLine(Line l){
		this.lines.add(l);
	}
	
	public void render(ShapeRenderer renderer){
		float radius = RADIUS;
		for(int i=0; (i<this.lines.size() && i<MAX_LINES); i++){
			Line l = this.lines.get(i);
			renderer.setColor(l.getLineColour());
			renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);
			radius = radius - 1;
		}
		
		// Calculate the percentage
		float t = this.trains.size()/(float)PLATFORMS;
		Color c = Color.WHITE.cpy().lerp(Color.DARK_GRAY, t);
		renderer.setColor(c);
		renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);		
	}

	/**
	 * Searches for a Line in the registered lines by its name.
	 * @param lineName the Line name.
	 * @return the Line when found.
	 */
	private Line getLine(String lineName) throws Exception{
		for(Line line : this.lines){
			if(line.getName() == lineName){
				return line;
			}
		}
		throw new Exception();
	}
	
	/**
	 * Lets a Train enter the Station.
	 * @param t The Train entering the Station.
	 * @throws Exception Station Full
	 */
	public void enter(Train incomingTrain) throws Exception {
		// Check that there is an available platform
		if(trains.size() >= PLATFORMS){
			throw new Exception();
		} else {
			// Add Train to platforms and clear the Track it was using.
			this.trains.add(incomingTrain);
			Line trainLine = getLine(incomingTrain.getTrainLine());
			trainLine.clearTrack(incomingTrain.getTrackId(), incomingTrain.getDirection());
		}
	}
	
	/**
	 * Processes the Passengers waiting to embark for a given Train.
	 * @param embarkingTrain the embarking Train.
	 * @throws Exception Train is full.
	 */
	public void processEmbarking(Train embarkingTrain){
		Line trainLine;
		boolean direction = embarkingTrain.getDirection();
		// If this station is the end of line for the train line, tell passengers
		// that the train is going in the opposite direction.
		try{
			trainLine = this.getLine(embarkingTrain.getTrainLine());
			if(trainLine.endOfLine(this))
				direction = !direction;
		}
		catch(Exception e){
			System.out.println("Station not found");
		}
		// Add the waiting Passengers
		ArrayList<Passenger> embarking = new ArrayList<Passenger>();
		for(Passenger passenger : this.waiting){
			// Check that the Train is not full
			if(!embarkingTrain.canEmbark()){
				return;
			}
			// If Passenger should board, embark it.
			if(passenger.shouldBoard(embarkingTrain.getTrainLine(), direction)){
				try{
					embarkingTrain.embark(passenger);
					embarking.add(passenger);
				}catch(Exception e){
					// Train full, break
					break;
				}
			}
		}
		// Remove from waiting
		for(Passenger passenger : embarking){
			waiting.remove(passenger);			
		}
		// Create a new set of passengers and check if they can board
		Passenger[] ps = this.generator.generatePassengers(this,this.maxPassenger);
		for(Passenger p: ps){
			// If can embark check if the passenger wants to board that train
			if(embarkingTrain.canEmbark()){
				if(p.shouldBoard(embarkingTrain.getTrainLine(), direction)){
					try{
						embarkingTrain.embark(p);
					}
					catch(Exception e){
						// Train full, break
						break;
					}
				}
				else{
					this.waiting.add(p);
				}
			}else{
				this.waiting.add(p);
			}
		}
	}
	
	/**
	 * Processes the disembarking Passengers.
	 * @param disembarking the disembarking Passengers.
	 */
	public void disembark(ArrayList<Passenger> disembarking){
		int exited = 0;
		int waited = 0;
		for(Passenger passenger : disembarking){
			// if Passenger reached destination let it out, otherwise return to waiting.
			if(passenger.getDestination().equals(this.name)){
				exited++;
				passenger.exit();
			}
			else{
				waited++;
				waiting.add(passenger);
			}
		}
		System.out.println(exited + " Passengers exited at " + this.name);
		System.out.println(waited + " Passengers waiting at " + this.name);
	}
	
	/**
	 * Checks if a Train can leave the Station.
	 * @param t the Train leaving the Station.
	 * @throws Exception Train not in Station.
	 */
	public boolean canDepart(Train departingTrain) throws Exception {
		// Check that the train is actually in the station.
		if(this.trains.contains(departingTrain)){
			// Get the direction and the Line the Train is servicing.
			boolean direction = departingTrain.getDirection();
			Line trainLine = getLine(departingTrain.getTrainLine());
			// Change direction if this Station is the end of Line.
			if(trainLine.endOfLine(this)){
				direction = !direction;
			}
			// Get the new 
			int nextTrackId = trainLine.nextTrack(this, direction);
			if(trainLine.trackAvailable(nextTrackId, direction)){
				trainLine.enterTrack(nextTrackId, direction);
				return true;
			}
			else
				return false;
		} else {
			throw new Exception();
		}
	}
	/**
	 * Lets a Train leave the Station.
	 * @param t the Train leaving the Station.
	 * @throws Exception Train not in Station.
	 */
	public void depart(Train departingTrain) throws Exception {
		// Check that the train is actually in the station.
		if(this.trains.contains(departingTrain)){
			// Get the direction and the Line the Train is servicing.
			boolean direction = departingTrain.getDirection();
			Line trainLine = getLine(departingTrain.getTrainLine());
			// Change direction if this Station is the end of Line.
			if(trainLine.endOfLine(this)){
				departingTrain.setDirection(!direction);
				direction = !direction;
			}
			// Get the information about the next destination and update it.
			int nextTrackId = trainLine.nextTrack(this, direction);
			Station nextStation = trainLine.nextStation(this, direction);
			departingTrain.updateNext(nextTrackId,nextStation);
			// Enter the Track and leave the Station.
			trainLine.enterTrack(nextTrackId, direction);
			this.trains.remove(departingTrain);
		} else {
			throw new Exception();
		}
	}
	
	/**
	 * Checks if a platform at the Station servicing the given Line is available.
	 * @param l the given Line.
	 * @return true if there are available platforms for the Line.
	 * @throws Exception Line not serviced by Station.
	 */
	public boolean canEnter(String line) throws Exception {
		return trains.size() < PLATFORMS;
	}

	// Returns departure time in seconds
	public float getDepartureTime() {
		return DEPARTURE_TIME;
	}

	public void setGenerator(PassengerGenerator generator){
		this.generator = generator;
	}

	@Override
	public String toString() {
		return "Station [position=" + position + ", name=" + name + ", trains=" + trains.size()
				+ ", generator=" + generator + "]";
	}

	/**
	 * @return the position
	 */
	public Point2D.Float getPosition() {
		return position;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<Line> getLines(){
		return lines;
	}
	
}
