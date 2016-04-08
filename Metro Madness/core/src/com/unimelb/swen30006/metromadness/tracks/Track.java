package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.trains.Train;

public class Track {
	public static final float DRAW_RADIUS=10f;
	public static final int LINE_WIDTH=6;
	public Point2D.Float startPos;
	public Point2D.Float endPos;
	public Color trackColour;
	public boolean occupied;
	
	/**
	 * Track constructor
	 * @param start Track start point.
	 * @param end Track end point.
	 * @param trackCol Track color.
	 */
	public Track(Point2D.Float start, Point2D.Float end, Color trackCol){
		this.startPos = start;
		this.endPos = end;
		this.trackColour = trackCol;
		this.occupied = false;
	}
	
	public void render(ShapeRenderer renderer){
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, LINE_WIDTH);
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
	 * @param t the Train entering the Track.
	 */
	public void enter(Train t){
		this.occupied = true;
	}
	
	@Override
	public String toString() {
		return "Track [startPos=" + startPos + ", endPos=" + endPos + ", trackColour=" + trackColour + ", occupied="
				+ occupied + "]";
	}

	/**
	 * Let a Train leave the Track.
	 * @param t the Train leaving the Track.
	 */
	public void leave(Train t){
		this.occupied = false;
	}
}
