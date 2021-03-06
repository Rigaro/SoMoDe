/** SWEN30006 Software Modelling and Design
 *  Project 1 - Mailroom Blues
 *  @author Ricardo Garcia Rosas 643952 <ricardog@student.unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.rgr643952;

import com.unimelb.swen30006.mailroom.*;
import com.unimelb.swen30006.mailroom.exceptions.*;

import java.util.UUID;

/**
 * A sorting strategy that fills Storage boxes first. Looks for the StorageBox with the most
 * number of Items stored and returns its identifier if the item size fits.
 * Finally, if none are available and there is no room left, it will throw a MailOverflowException
 */
public class FillBoxesSortingStrategy implements SortingStrategy {

    @Override
    public String assignStorage(MailItem item, MailStorage storage) throws MailOverflowException {

        // Retrieve the summaries of available storage items
        StorageBox.Summary[] available = storage.retrieveSummaries();
        // Search for the box with the most numItems with enough remaining units
        // to fit the item size.
        int maxNumItems = 0;
        String maxId = null;
        for(StorageBox.Summary summary : available){
        	if(summary.numItems>=maxNumItems && summary.remainingUnits >= item.size){
            	//System.out.println(summary.toString());
        		maxNumItems = summary.numItems;
        		maxId =summary.identifier;
        	}
        }
        // Return the id of the box with most items.
        if(maxId != null){
        	//System.out.println("Max" + maxNumItems);
        	return maxId;
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
