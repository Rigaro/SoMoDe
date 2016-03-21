package com.unimelb.swen30006.workshops;

/**
 * 
 * @author Ricardo Garcia Rosas 643952
 *
 */
public class Student extends Person{

	public String studentID;
	public String email;
	
	/**
	 * Sends an email message to student.
	 * @param msg
	 */
	public void sendEmailMsg(String msg){
		System.out.println(msg);
	}
	
	/**
	 * @return The student's total grade.
	 */
	public float totalGrade(){
		return (float)Math.random();
	}
}
