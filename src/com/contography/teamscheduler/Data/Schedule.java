/**
 * Project : com.contography.teamscheduler 
 * File : Schedule.java
 * Date : Feb 12, 2017
 * Time : 4:41:19 PM
 */
package com.contography.teamscheduler.Data;

import java.util.Date;

/**
 * @author Jackie
 *
 */
public class Schedule {

		private Date date;
		
		public Schedule(){
			this.date = new Date();
		}
		
		public Schedule(Date date){
			this.date = date;
		}
		
		public Date getDate(){
			return date;
		}
}
