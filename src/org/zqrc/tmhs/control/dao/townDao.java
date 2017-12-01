package org.zqrc.tmhs.control.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.bean.town;
import org.zqrc.tmhs.control.dbo.DBO;

/**
 * 城镇类型数据操作层
 * @author JiaQi
 *
 */
public class townDao {
	private DBO db = new DBO();
	
	public boolean add(String town){
		/**
		 * 添加条目
		 */
		try{
			db.update("INSERT INTO town (town) VALUES ('"+town+"')");
			return true;
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, "添加失败！");
			return false;
		}
		
	}
	
	public boolean del(String town){
		/**
		 * 删除条目
		 */
		try{
			db.update("DELETE FROM town WHERE town = ('"+town+"')");
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, "删除失败！");
			return false;
		}
		return true;
	}
	public String[] findtown_dec(){
		/*
		 * 得到城镇字典
		 */
		ArrayList<String> townList = new ArrayList<String>();
		ResultSet rs = db.getRs("select * from town");
		try {
			//遍历数据库
			while(rs.next()){
				townList.add(rs.getString("town"));
			}
			
		} catch (SQLException e) {
		}
		db.closed();
		
		String[] town_dec = new String[townList.size()];
		//将集合遍历至数组
		int i = 0;
		while(i<townList.size()){
			town_dec[i]= townList.get(i);
			i++;
		}
		//返回城镇字典
		return town_dec;
	}
}
