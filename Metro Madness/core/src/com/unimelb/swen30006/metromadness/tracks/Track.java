/**
 * Amy Lawson - 693920
 * Joshua Moors - 542065
 * Ricardo Garcia Rosas - 643952
 */
package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Track {
	protected static final float DRAW_RADIUS=10f;
	protected static final int LINE_WIDTH=6;
	protected Point2D.Float startPos;
	protected Point2D.Float endPos;
	protected Color trackColour;
	private static int idCount = 0;
	private int id;
	private boolean occupied;
	
	/**
	 * Track constructor.
	 * @param start Track start point.
	 * @param end Track end point.
	 * @param trackCol Track color.
	 */
	public Track(Point2D.Float start, Point2D.Float end, Color trackCol){
		this.startPos = start;
		this.endPos = end;
		this.trackColour = trackCol;
		this.id = Track.idCount;
		increaseIdCount();
		this.occupied = false;
	}
	
	/**
	 * Increases the count of the static id counter.
	 */
	private static void increaseIdCount(){
		Track.idCount++;
	}
	
	/**
	 * Whether a Train can enter the Track or not.
	 * @param forward Entry direction.
	 * @return Whether a Train can enter (true) the Track or not (false).
	 */
	public boolean canEnter(boolean forward){
		return !this.occupied;
	}
	
	/**
	 * Let a Train enter the Track.
	 * @param forward Direction the Train is entering the track.
	 */
	public void enter(boolean forward){
		this.occupied = true;
	}

	/**
	 * Let a Train leave the Track.
	 * @param forward Direction the Train is leaving the track.
	 */
	public void leave(boolean forward){
		this.occupied = false;
	}
	
	public void render(ShapeRenderer renderer){
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, LINE_WIDTH);
	}

	/**
	 * Get Track id.
	 * @return the Track id.
	 */
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Track [startPos=" + startPos + ", endPos=" + endPos + ", trackColour=" + trackColour + ", occupied="
				+ occupied + "]";
	}
}
