package org.zqrc.tmhs.control.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.dbo.DBO;


/**
 * 缓存路径数据操作层
 * @author JiaQi
 *
 */
public class pathDao {
	private DBO db = new DBO();
	public String getpath1() {
		/*
		 * 获得路径1
		 */
		String path1 = null;
		//path1 = "C:/jia";
		ResultSet rs = db.getRs("select * from path where id = '1'");
		//获得数据库中的 path
		try {
			path1 = rs.getString("path");
		} catch (SQLException e) {
		}
		db.closed();
		return path1;
	}

	public String getpath2() {
		/*
		 * 获得路径2
		 */
		//path2 = "C:/qi";
		String path2 = null;
		ResultSet rs = db.getRs("select * from path where id = '2'");
		//获得数据库中的 path
		try {
			path2 = rs.getString("path");
		} catch (SQLException e) {
		}
		db.closed();
		return path2;
	}
	
	public boolean setpath1(String path1){
		/*
		 * 设置路径1
		 */
		try{
		db.update("update path set path = '"+path1+"' where id = '1'");
		}catch (Exception e) {
		}
		return true;
	}
	
	public boolean setpath2(String path2){
		/*
		 * 设置路径2
		 */
		try{
		db.update("update path set path = '"+path2+"' where id = '2'");
		}catch (Exception e) {
		}
		return true;
	}
}
