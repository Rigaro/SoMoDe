/** SWEN30006 Software Modelling and Design
 *  Project 1 - Mailroom Blues
 *  @author Ricardo Garcia Rosas 643952 <ricardog@student.unimelb.edu.au>
 */
package com.unimelb.swen30006.mailroom;

import com.unimelb.swen30006.mailroom.SelectionStrategy;
import com.unimelb.swen30006.mailroom.StorageBox;
import com.unimelb.swen30006.mailroom.exceptions.NoBoxReadyException;

/**
 * Will wait until there is a full box for delivery. Unless there is only one box left and
 * many selection attempts have been performed.
 */
public class FullBoxSelectionStrategy implements SelectionStrategy {
	private int tries = 0; // Number of times attempted selection.
    @Override
    public String selectNextDelivery(StorageBox.Summary[] summaries) throws NoBoxReadyException {
        if (summaries.length != 0) {
            for(StorageBox.Summary summary : summaries){
//                System.out.println(summary);
                if(summary.remainingUnits == 0){
                	tries = 0;
                	//System.out.println("Selected:" + summary);
                    return summary.identifier;
                }
            }
        }
        // If there is only one box left and is not full increases the number of selection tries.
        if (summaries.length == 1){
            tries++;
            // If too many tries, just deliver last box.
        	if(tries > 50){ 
        		return summaries[0].identifier;
        	}
        }
        // Otherwise no box is ready
        throw new NoBoxReadyException();
    }
}
