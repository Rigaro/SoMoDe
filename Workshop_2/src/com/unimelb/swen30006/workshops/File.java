package com.unimelb.swen30006.workshops;
import java.util.*;

/**
 * 
 * @author Ricardo Garcia Rosas 643952
 *
 */
// Sample private file class to be replaced by your implementaiton
class File {
	public String fileName;
	public Date createdDate;
	private String fileData;
	
	/**
	 * 
	 * @return the type of file as a String.
	 */
    public String fileType(){
        double num = Math.random();
        if(num<0.5){
            return "pdf";
        } else {
            return "docx";
        }
    }
}