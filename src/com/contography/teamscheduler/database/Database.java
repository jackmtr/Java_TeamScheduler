package com.contography.teamscheduler.database;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.contography.teamscheduler.DAO.TeamMateDAO;
import com.contography.teamscheduler.Data.Schedule;
import com.contography.teamscheduler.Data.TeamMate;

/**
 * Project : com.contography.teamscheduler 
 * File : Database.java
 * Date : Feb 12, 2017
 * Time : 2:00:35 PM
 */

/**
 * @author Jackie
 *
 */
public class Database {

	private Connection _connection;
	
	public Database() {
		try{
			connect();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void connect() throws SQLException{
		
		String url = "jdbc:mysql://localhost:3306/teamscheduler?autoReconnect=true&useSSL=false";
		String user = "guest";
		String password= "password";
		
		_connection = DriverManager.getConnection(url, user, password);
	}
	
	public boolean checkTables(String tableName) {
		try{
			DatabaseMetaData databaseMetaData = _connection.getMetaData();
			ResultSet resultSet = databaseMetaData.getTables(_connection.getCatalog(), "%", "%", null);
			String name = null;
			while (resultSet.next()){
				name = resultSet.getString("TABLE_NAME");
				if(name.equalsIgnoreCase(tableName)){
					return true;
				}
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createTeamMatesTable(){
		String query = "CREATE TABLE Teammates (team_mate_id int AUTO_INCREMENT UNIQUE, first_name varchar(50) NOT NULL, last_name varchar(50) NOT NULL, Primary Key(team_mate_id));";
		return createTable(query);
	}
	
	public boolean createSchedulesTable(){
		String query = "CREATE TABLE Schedules (schedule_id int AUTO_INCREMENT UNIQUE, date Date NOT NULL, Primary Key(schedule_id));";
		return createTable(query);
	}
	
	public boolean createAvailabilitiesTable(){
		
		String query = "CREATE TABLE Availabilities (available_id int AUTO_INCREMENT UNIQUE, available int, schedule_id int NOT NULL, PRIMARY KEY(available_id), FOREIGN KEY(schedule_id) REFERENCES Schedules(schedule_id));";
		boolean availableTableCreated = createTable(query);
		String query2 = "CREATE TABLE Availabilities_Teammates (availabilities_teammates_id int AUTO_INCREMENT UNIQUE, team_mate_id int NOT NULL, available_id int NOT NULL, PRIMARY KEY(availabilities_teammates_id), FOREIGN KEY(team_mate_id) REFERENCES Teammates(team_mate_id), FOREIGN KEY(available_id) REFERENCES Availabilities(available_id));";
		boolean bridgeTableCreated = createTable(query2);
		if (availableTableCreated && bridgeTableCreated){
			return true;
		}else{
			return false;
		}
	}

	private boolean createTable(String createQuery){
		try{
			Statement statement = _connection.createStatement();
			return statement.execute(createQuery);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean insertTeammates(File file) throws FileNotFoundException{
		
		TeamMateDAO dao = new TeamMateDAO();
		List<TeamMate> team = dao.getTeammates(file);
		try{
			return insertTeamMateData(team);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean insertDates(File file) throws FileNotFoundException{
		
		TeamMateDAO dao = new TeamMateDAO();
		try{
			List<Schedule> dates = dao.getDates(file);
			return insertDateData(dates);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/*public boolean insertSchedule(LinkedList dates){
		
		return false;
	}*/
	
	private boolean insertTeamMateData(List<TeamMate> team) throws SQLException{
		
		boolean runWell = true;
		
		for (TeamMate tm : team){
			StringBuilder query = new StringBuilder("INSERT INTO TeamMates (first_name, last_name) VALUES('");
			String name = tm.getName();
			String firstName = name.substring(0, name.indexOf(' '));
			String lastName = name.substring(name.indexOf(' ') + 1);

			query.append(firstName).append("','").append(lastName).append("');");
			
			int i = insertData(query);
			if (i == 0)
			{
				System.out.println("Something went wrong");
				runWell = false; 
				break;
			}
		}
		return runWell;
	}
	
	public boolean insertDateData(List<Schedule> dates) throws SQLException{
		
		boolean runWell = true;
		
		for(Schedule sc : dates){
			String pattern = "yyyy-MM-dd"; 
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			
			String dateString = format.format(sc.getDate());
			
			StringBuilder query = new StringBuilder("INSERT INTO Schedules (date) VALUES('").append(dateString).append("');");
			
			int i = insertData(query);
			if (i == 0)
			{
				System.out.println("Something went wrong");
				runWell = false; 
				break;
			}		
		}
		return runWell;
	}
	
	public int insertData(StringBuilder insertQuery) throws SQLException{
		Statement statement = _connection.createStatement();
		String query = insertQuery.toString();
		return statement.executeUpdate(query);
	}		
}
