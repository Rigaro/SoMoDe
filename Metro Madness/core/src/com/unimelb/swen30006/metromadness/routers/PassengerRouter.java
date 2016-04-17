/**
 * 
 */
package com.unimelb.swen30006.metromadness.routers;

public interface PassengerRouter {

	public boolean shouldLeave(String currentStation);
	public boolean shouldBoard(String trainLine, boolean trainDirection);
	
}
