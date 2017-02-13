/**
 * Project : com.contography.teamscheduler 
 * File : TeamMate.java
 * Date : Feb 12, 2017
 * Time : 1:46:06 PM
 */
package com.contography.teamscheduler.Data;

/**
 * @author Jackie
 *
 */
public class TeamMate {

	private String name;
	private String gender;
	
	public TeamMate(){
		
	}
	public TeamMate(String name, String gender){
		this.name = name;
		this.gender = gender;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getGender(){
		return this.gender;
	}
}
