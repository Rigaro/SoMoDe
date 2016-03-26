/** SWEN30006 Software Modelling and Design
 *  Project 1 - Mailroom Blues
 *  @author Ricardo Garcia Rosas 643952 <ricardog@student.unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom.rgr643952;

import com.unimelb.swen30006.mailroom.SelectionStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.NoBoxReadyException;

/**
 * Will wait until there is a full box for delivery. Unless there have been many attempts and
 * no box can be selected.
 */
public class FullBoxSelectionStrategy implements SelectionStrategy {
	private int tries = 0; // Number of times attempted selection.
    @Override
    public String selectNextDelivery(StorageBox.Summary[] summaries) throws NoBoxReadyException {
        
    	// When there are boxes available, look for one that is full.
    	if (summaries.length != 0) {
            for(StorageBox.Summary summary : summaries){
            	// System.out.println(summary);
                if(summary.remainingUnits == 0){
                	tries = 0;
                	//System.out.println("Selected:" + summary);
                    return summary.identifier;
                }
            }
        }
        // Since there was no box selected, increase the number of tries.
        tries++;
        // If too many selection tries, select the box with the most items.
    	if(summaries.length>0 && tries > 100){ 
    		tries = 0;
    		//return summaries[0].identifier;
            int maxNumItems = 0;
            String maxId = null;
            for(StorageBox.Summary summary : summaries){
            	if(summary.numItems>=maxNumItems){
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
    	}
        //}
        // Otherwise no box is ready
        throw new NoBoxReadyException();
    }
}
