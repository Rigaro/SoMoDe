/** SWEN30006 Software Modelling and Design
 *  Project 1 - Mailroom Blues
 *  @author Ricardo Garcia Rosas 643952 <ricardog@student.unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.rgr643952;

import com.unimelb.swen30006.mailroom.*;
import com.unimelb.swen30006.mailroom.exceptions.*;

import java.util.UUID;

/**
 * Fills boxes depending on floor number.
 * Finally, if none are available and there is no room left, it will throw a MailOverflowException
 */
public class FloorBoxesSortingStrategy implements SortingStrategy {
	// Create an index that can hold twice the amount of boxes as floors for large buildings
	floorBoxIndex[] indexes = new floorBoxIndex[400];;
	int indexPointer = 0;
	boolean foundFlag = false;
    @Override
    public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {
    	// Retrieve the summaries of available storage items
        StorageBox.Summary[] available = storage.retrieveSummaries();
        // Check if available boxes are in index.
        if(indexPointer!=0){
        	for(StorageBox.Summary summary : available){
        		System.out.println(summary);
        		for(int i=0; i<indexPointer; i++){
        			// Set flag if found
        			if(summary.identifier.equals(indexes[i].boxIdentifier)){
        				foundFlag = true;
        			}
        		}
    			// If not in index, add it.
        		if(foundFlag = false){
        			indexes[indexPointer] = new floorBoxIndex(summary.identifier);
        			indexPointer++;
        		}
        		// Reset flag.
        		foundFlag = false;
        	}
        }
        else if(available.length>0){
			indexes[indexPointer] = new floorBoxIndex(available[0].identifier);
			indexPointer++;
        }
        
        // Search indexes for same item floor.
        for(int i=0; i<indexPointer; i++){
        	int j = 0;
        	while(indexes[i].boxFloors[j]!=0){
        		// If floor found, return box ID
        		if(item.floor==indexes[i].boxFloors[j]) return indexes[i].boxIdentifier;
        	}
        }

    	// If can't find, assign to some box
        for(StorageBox.Summary summary : available){
            if(summary.remainingUnits >= item.size){
        		for(int i=0; i<indexPointer; i++){
        			// Set flag if found
        			if(summary.identifier.equals(indexes[i].boxIdentifier)){
        	        	indexes[i].addNewFloor(item.floor);
                        return summary.identifier;
        			}
        		}
            }
        }  	
        // If we get to here without returning there is no storage box
        // appropriate so let's try create one and assign it to the item's floor.
        String id = UUID.randomUUID().toString();
        try {
        	// System.out.println("New");
            storage.createBox(id);
            return id;
        } catch (DuplicateIdentifierException e){
            System.out.println(e);
            System.exit(0);
        }
        return null;
    }
    /**
     * Class that holds floor identifier and floor number.
     */
    public class floorBoxIndex {
    	public String boxIdentifier;
    	public int[] boxFloors;
    	private int lastFloorPointer = 0;
    	
    	public floorBoxIndex(String boxIndetifier){
    		this.boxIdentifier = boxIndetifier;
    		this.boxFloors = new int[400];
    	}
    	
    	public void addNewFloor(int floor){
    		this.boxFloors[lastFloorPointer] = floor;
    		lastFloorPointer++;
    	}
    	
    	@Override
    	public String toString(){
    		return ("ID: " + this.boxIdentifier + " Floors: " + this.boxFloors);
    	}
    }
}
