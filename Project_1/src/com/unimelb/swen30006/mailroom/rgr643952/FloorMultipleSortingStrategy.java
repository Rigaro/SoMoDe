/** SWEN30006 Software Modelling and Design
 *  Project 1 - Mailroom Blues
 *  @author Ricardo Garcia Rosas 643952 <ricardog@student.unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.rgr643952;

import com.unimelb.swen30006.mailroom.*;
import com.unimelb.swen30006.mailroom.exceptions.*;

import java.util.UUID;

/**
 * A sorting strategy that fills boxes depending on item floor multiples.
 * Computes the mod of the floor to an odd or even number and selects the box accordingly.
 * Finally, if none are available and there is no room left, it will throw a MailOverflowException
 */
public class FloorMultipleSortingStrategy implements SortingStrategy {

    @Override
    public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {
    	int index = 0;
        // Retrieve the summaries of available storage items
        StorageBox.Summary[] available = storage.retrieveSummaries();
        // Try to allocate by odd floor multiples
        for(StorageBox.Summary summary : available){
        	index++;
        	if(summary.remainingUnits>=item.size && item.floor%(2*index+1)==0){
            	//System.out.println(summary.toString());
        		return summary.identifier;
        	}
        }
        
        index = 0;
        // Try to allocate by even floor multiples
        for(StorageBox.Summary summary : available){
        	index++;
        	if(summary.remainingUnits>=item.size && item.floor%(2*index)==0){
            	//System.out.println(summary.toString());
        		return summary.identifier;
        	}
        }
        
        // If we get to here without returning there is no storage box
        // appropriate so let's try create one
        String id = UUID.randomUUID().toString();
        try {
        	//System.out.println("New");
            storage.createBox(id);
            return id;
        } catch (DuplicateIdentifierException e){
            System.out.println(e);
            System.exit(0);
        } 
        return null;
    }
}
