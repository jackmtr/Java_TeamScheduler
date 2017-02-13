/**
 * Project : com.contography.teamscheduler 
 * File : Main.java
 * Date : Feb 12, 2017
 * Time : 1:41:23 PM
 */
package com.contography.teamscheduler;

import java.io.File;
import java.io.FileNotFoundException;
import com.contography.teamscheduler.database.Database;

/**
 * @author Jackie
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		File fileTeamMates = new File("team_mates.txt");
		File fileSchedule = new File("dates.txt");
		
		Database database = new Database();
		
		if (database.checkTables("Teammates")){

		}else{
			database.createTeamMatesTable();
			database.insertTeammates(fileTeamMates);
		}
		
		if (database.checkTables("Schedules")){

		}else{
			database.createSchedulesTable();
			database.insertDates(fileSchedule);
		}
		
		if (database.checkTables("Availabilities")){
			//table exists
		}else if(database.checkTables("Teammates") && database.checkTables("Schedules")){
			database.createAvailabilitiesTable();
		}else{
			//throw error that parent tables do not exist
		}
	}
}
