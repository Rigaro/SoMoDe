package com.unimelb.swen30006.workshops;

import java.util.*;

/**
 * 
 * @author Ricardo Garcia Rosas 643952
 *
 */
public class Assignment implements SubmissionValidator{
	
	public String name;
	public String description;
	public Date dueDate;
	public int maxAttempts;
	
	public ArrayList<Submission> validSubmissions(){
		ArrayList<Submission> someSubmissions = new ArrayList<Submission>();
		return someSubmissions;
	}
	
	public ArrayList<Submission> invalidSubmissions(){
		ArrayList<Submission> someSubmissions = new ArrayList<Submission>();
		return someSubmissions;
	}

    @Override
    public ValidationError[] validateSubmission(Submission submission) {
        ArrayList<ValidationError> errors = new ArrayList<ValidationError>();
        // Loop through all files and create an error if there are any no pdfs
        File[] files = submission.includedFiles();
        for(File f : files){
            String type = f.fileType();
            if(!type.equals("pdf")){
                ValidationError error = new ValidationError(f);
                error.addError("File Type", "Unsupported filetype.");
                errors.add(error);
            }
        }

        if(errors.size() > 0){
           return errors.toArray(new ValidationError[0]);
        } else {
            return null;
        }
    }
}
