/**
 * Amy Lawson - 693920
 * Joshua Moors - 542065
 * Ricardo Garcia Rosas - 643952
 */
package com.unimelb.swen30006.metromadness.routers;

public interface PassengerRouter {

	public boolean shouldLeave(String currentStation);
	public boolean shouldBoard(String trainLine, boolean trainDirection);
	
}
