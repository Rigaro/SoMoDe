/**
 * Amy Lawson - 693920
 * Joshua Moors - 542065
 * Ricardo Garcia Rosas - 643952
 */
package com.unimelb.swen30006.metromadness.tracks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.stations.Station;

public class Line {
	
	// The colour of this line
	private Color lineColour;
	private Color trackColour;
	
	// The name of this line
	private String name;
	// The stations on this line
	private ArrayList<Station> stations;
	// The tracks on this line between stations
	private ArrayList<Track> tracks;
		
	/**
	 * Line constructor
	 * @param stationColour the Color of the Stations included in the Line.
	 * @param lineColour the Color of the line.
	 * @param name The Line name.
	 */
	public Line(Color stationColour, Color lineColour, String name){
		// Set the line colour
		this.lineColour = stationColour;
		this.trackColour = lineColour;
		this.name = name;
		
		// Create the data structures
		this.stations = new ArrayList<Station>();
		this.tracks = new ArrayList<Track>();
	}
	
	/**
	 * Adds a Station to the end of the Line.
	 * @param s the Station to be added.
	 * @param two_way whether the track servicing the Station is two way or not.
	 */
	public void addStation(Station station, Boolean two_way){
		// We need to build the track if this is adding to existing stations
		if(this.stations.size() > 0){
			// Get the last station
			Station last = this.stations.get(this.stations.size()-1);
			
			// Generate a new track
			Track t;
			if(two_way){
				t = new DualTrack(last.getPosition(), station.getPosition(), this.trackColour);
			} else {
				t = new Track(last.getPosition(), station.getPosition(), this.trackColour);
			}
			this.tracks.add(t);
		}
		
		// Add the station
		station.registerLine(this);
		this.stations.add(station);
	}

	/**
	 * Finds if the given Station is the end of Line (first or last Station).
	 * @param s the Station to check
	 * @return true if the Station is the end of Line.
	 * @throws Exception the Station is not part of the Line.
	 */
	public boolean endOfLine(Station s) throws Exception{
		if(this.stations.contains(s)){
			int index = this.stations.indexOf(s);
			return (index==0 || index==this.stations.size()-1);
		} else {
			throw new Exception();
		}
	}

	/**
	 * Obtains the Track following the given Station in the desired direction (forward).
	 * @param currentStation the current Station for the search.
	 * @param forward the travel direction.
	 * @return the next Track's ID servicing the Line after the given Station.
	 * @throws Exception Index out of Bounds (station not found).
	 */
	public int nextTrack(Station currentStation, boolean forward) throws Exception {
		if(this.stations.contains(currentStation)){
			// Determine the track index
			int curIndex = this.stations.lastIndexOf(currentStation);
			// Increment to retrieve
			if(!forward){ curIndex -=1;}
			
			// Check index is within range
			if((curIndex < 0) || (curIndex > this.tracks.size()-1)){
				throw new Exception();
			} else {
				return this.tracks.get(curIndex).getId();
			}
			
		} else {
			throw new Exception();
		}
	}
	
	/**
	 * Obtains the next Station following a given Station and direction.
	 * @param s the current Station.
	 * @param forward the travel direction.
	 * @return the next Station following the given Station and direction.
	 * @throws Exception Index out of Bounds (station not found)
	 */
	public Station nextStation(Station s, boolean forward) throws Exception{
		if(this.stations.contains(s)){
			int curIndex = this.stations.lastIndexOf(s);
			if(forward){ curIndex+=1;}else{ curIndex -=1;}
			
			// Check index is within range
			if((curIndex < 0) || (curIndex > this.stations.size()-1)){
				throw new Exception();
			} else {
				return this.stations.get(curIndex);
			}
		} else {
			throw new Exception();
		}
	}
	
	/**
	 * Clears a Track when a Train leaves it in a certain direction.
	 * @param trackId the Track's id.
	 * @param forward the travel direction
	 */
	public void clearTrack(int trackId, boolean forward){
		for(Track track : tracks){
			if(track.getId() == trackId){
				track.leave(forward);
				return;
			}
		}		
	}
	
	/**
	 * Checks if a track is available in a certain direction.
	 * @param trackId the Track's id.
	 * @param forward the travel direction
	 */
	public boolean trackAvailable(int trackId, boolean forward){
		for(Track track : tracks){
			if(track.getId() == trackId){
				return track.canEnter(forward);
			}
		}		
		return false;
	}
	
	/**
	 * Occupies a Track when a Train enters it in a certain direction.
	 * @param trackId the Track's id.
	 * @param forward the travel direction
	 */
	public void enterTrack(int trackId, boolean forward){
		for(Track track : tracks){
			if(track.getId() == trackId){
				track.enter(forward);
				return;
			}
		}		
	}
	public void render(ShapeRenderer renderer){
		// Set the color to our line
		renderer.setColor(trackColour);
	
		// Draw all the track sections
		for(Track t: this.tracks){
			t.render(renderer);
		}	
	}

	/**
	 * @return the stations
	 */
	public ArrayList<Station> getStations() {
		return stations;
	}
	
	public Color getLineColour() {
		return lineColour;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString() {
		return "Line [lineColour=" + lineColour + ", trackColour=" + trackColour + ", name=" + name + "]";
	}
	
}
