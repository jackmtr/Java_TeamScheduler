/**
 * Project : com.contography.teamscheduler 
 * File : TeamMateDAO.java
 * Date : Feb 12, 2017
 * Time : 1:45:38 PM
 */
package com.contography.teamscheduler.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.contography.teamscheduler.Data.Schedule;
import com.contography.teamscheduler.Data.TeamMate;

/**
 * @author Jackie
 *
 */
public class TeamMateDAO {
	
	public List<String> inputFile(File file) throws FileNotFoundException{
		
		Scanner scanner = new Scanner(file);
		List<String> tokens = new ArrayList();
		
		while(scanner.hasNextLine()){
			tokens.add(scanner.nextLine());
		}
		return tokens;
	}
	
	public List<TeamMate> getTeammates(File file) throws FileNotFoundException{
		
		List<String> tokens = inputFile(file);
		List<TeamMate> team = new LinkedList<TeamMate>();
		
		for (String token : tokens){
			String[] teammate = token.split("\\s*,\\s*");
			
			TeamMate newTeammate = new TeamMate(teammate[0], teammate[1]); 
			team.add(newTeammate);
		}
		return team;
	}
	
	public List<Schedule> getDates(File file) throws FileNotFoundException, ParseException{
		
		List<String> tokens = inputFile(file);
		List<Schedule> schedules = new LinkedList<Schedule>();
		
		for (String token : tokens){
			
			String pattern = "MM/dd/yyyy"; //YYYY-MM-DD
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			
			System.out.println(token);
			
			Date newDate = format.parse(token);
			
			Schedule sc = new Schedule(newDate);

			schedules.add(sc);
		}
		return schedules;
	}
}
