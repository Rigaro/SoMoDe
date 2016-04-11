package com.unimelb.swen30006.metromadness.stations;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

public class Station {
	
	public static final int PLATFORMS=2;
	
	public Point2D.Float position;
	public static final float RADIUS=6;
	public static final int NUM_CIRCLE_STATMENTS=100;
	public static final int MAX_LINES=3;
	public String name;
	public ArrayList<Line> lines;
	public ArrayList<Train> trains;
	public static final float DEPARTURE_TIME = 2;
	public PassengerRouter router;

	/**
	 * Station constructor.
	 * @param x The x position.
	 * @param y The y position.
	 * @param router The PassengerRouter to be used.
	 * @param name The Station's name.
	 */
	public Station(float x, float y, PassengerRouter router, String name){
		this.name = name;
		this.router = router;
		this.position = new Point2D.Float(x,y);
		this.lines = new ArrayList<Line>();
		this.trains = new ArrayList<Train>();
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
			renderer.setColor(l.lineColour);
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
	 * Lets a Train enter the Station.
	 * @param t The Train entering the Station.
	 * @throws Exception Station Full
	 */
	public void enter(Train t) throws Exception {
		if(trains.size() >= PLATFORMS){
			throw new Exception();
		} else {
			this.trains.add(t);
		}
	}
	
	/**
	 * Lets a Train leave the Station.
	 * @param t the Train leaving the Station.
	 * @throws Exception Train not in Station.
	 */
	public void depart(Train t) throws Exception {
		if(this.trains.contains(t)){
			this.trains.remove(t);
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
	public boolean canEnter(Line l) throws Exception {
		return trains.size() < PLATFORMS;
	}

	// Returns departure time in seconds
	public float getDepartureTime() {
		return DEPARTURE_TIME;
	}

	/**
	 * Checks if a given Passenger should leave the Train at the Station.
	 * @param p the Passenger.
	 * @return true if the Passenger should leave.
	 */
	public boolean shouldLeave(Passenger p) {
		return this.router.shouldLeave(this, p);
	}

	@Override
	public String toString() {
		return "Station [position=" + position + ", name=" + name + ", trains=" + trains.size()
				+ ", router=" + router + "]";
	}

	/**
	 * Create a new passenger.
	 * @param s the Station where the Passenger is entering.
	 * @return the created Passenger.
	 */
	public Passenger generatePassenger(Station s) {
		return new Passenger(this, s);
	}
	
	
}
