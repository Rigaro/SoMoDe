/** SWEN30006 Software Modelling and Design
 *  Project 1 - Mailroom Blues
 *  @author Ricardo Garcia Rosas 643952 <ricardog@student.unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.rgr643952;

import com.unimelb.swen30006.mailroom.DeliveryStrategy;
import com.unimelb.swen30006.mailroom.MailItem;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.SourceExhaustedException;

/**
 * Reduces the number of distance between the steps taken for delivery by stepping to the next
 * closest floor to the bot's current floor.
 */
public class NearestFloorDeliveryStrategy implements DeliveryStrategy {

    @Override
    public int chooseNextFloor(int currentFloor, StorageBox box) throws SourceExhaustedException {
        // Make a copy of all the items in the box.
        StorageBox.Summary summary  = box.generateSummary("");
        int numItems = summary.numItems;
        MailItem[] item = new MailItem[numItems];
        try {
        	// Get all the items out of the box, into a temporary analysis array.
        	for(int i=0;i<numItems;i++){
        		item[i] = box.popItem();
        	}
        	// Re-stack all items in box.
        	for(int i=numItems-1;i>=0;i--){
        		box.addItem(item[i]);
        	}
        } catch (Exception e){
            System.out.println(e);
            System.exit(0);
        }
        
        // Look for nearest floor
        int destination = 0;
        int nearestDistance = 10000; // Large value for first comparison
        int difference;
        for(int i=0;i<item.length;i++){
        	// Get floor difference (absolute value)
        	if(currentFloor>item[i].floor){
        		difference = currentFloor-item[i].floor;
        	}else{
        		difference = item[i].floor-currentFloor;
        	}
        	// Select floor if the difference between current floor and analyzed floor
        	// is small than the smallest so far.
        	if(difference < nearestDistance){
        		destination = item[i].floor;
        		nearestDistance = difference;
        	}
        	//System.out.println("Diff:" + difference + "Next:" + nearestDistance);
        }
        //System.out.println("Cur:" + currentFloor + "Next:" + destination);
        return destination;
    }

}
