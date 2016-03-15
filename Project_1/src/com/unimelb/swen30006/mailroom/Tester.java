package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.samples.SimpleDeliveryStrategy;
import com.unimelb.swen30006.mailroom.samples.SimpleSelectionStrategy;
import com.unimelb.swen30006.mailroom.samples.SimpleSortingStrategy;

public class Tester {
	
	public static void main(){
		// Create the appropriate strategies
        SortingStrategy sortStrategy = new SimpleSortingStrategy();
        SelectionStrategy selectionStrategy = new SimpleSelectionStrategy();
        DeliveryStrategy deliveryStrategy = new SimpleDeliveryStrategy();
        
        // Setup variables for the simulation
        double totalTime = 0;
        double totalFloors = 0;
        double numDeliveries = 0;
	}

}
