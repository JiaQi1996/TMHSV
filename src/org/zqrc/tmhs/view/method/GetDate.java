package org.zqrc.tmhs.view.method;

import java.util.Calendar;

/**
 * 用于获得系统时间
 * 获得年份时间
 * 获得月份时间
 * @author JiaQi
 *
 */

public class GetDate {
	private Calendar c = Calendar.getInstance();	
	
	
	public String getyear(){
		/**
		 * 获取系统年份
		 */
		return String.valueOf(c.get(Calendar.YEAR));
	}
	
	public String getmonth(){
		/**
		 * 	获取系统月份
		 */
		return String.valueOf(c.get(Calendar.MONTH)+1);
	}
	public String gettime(){
		return String.valueOf(c.get(Calendar.MONTH)+1)+String.valueOf(c.get(Calendar.YEAR));
	}
}
