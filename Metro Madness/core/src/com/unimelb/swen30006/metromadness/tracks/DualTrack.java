package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D.Float;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DualTrack extends Track {

	public boolean forwardOccupied;
	public boolean backwardOccupied;
	
	/**
	 * DualTrack constructor.
	 * @param start The start position.
	 * @param end The end position.
	 * @param col the Track's Color.
	 */
	public DualTrack(Float start, Float end, Color col) {
		super(start, end, col);
		this.forwardOccupied = false;
		this.backwardOccupied = false;
	}
	
	public void render(ShapeRenderer renderer){
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, LINE_WIDTH);
		renderer.setColor(new Color(245f/255f,245f/255f,245f/255f,0.5f).lerp(this.trackColour, 0.5f));
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, LINE_WIDTH/3);
		renderer.setColor(this.trackColour);
	}
	
	/**
	 * Lets a Train into the Track in the desired direction.
	 */
	@Override
	public void enter(boolean forward){
		if(forward){
			this.forwardOccupied = true;
		} else {
			this.backwardOccupied = true;
		}
	}
	
	/**
	 * Whether a Train can enter the Track in the desired direction.
	 */
	@Override
	public boolean canEnter(boolean forward) {
		if(forward){
			return !this.forwardOccupied;
		} else {
			return !this.backwardOccupied;
		}
	}

	/**
	 * Let a Train leave the Track in the desired direction.
	 */
	@Override
	public void leave(boolean forward) {
		if(forward){
			this.forwardOccupied = false;
		} else {
			this.backwardOccupied = false;
		}
	}
	
	
	
	
	


}
