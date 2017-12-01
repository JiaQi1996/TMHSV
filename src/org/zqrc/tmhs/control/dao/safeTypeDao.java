package org.zqrc.tmhs.control.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.dbo.DBO;

/**
  *参保类型数据操作层
 * 添加条目方法
 * 删除条目方法
 * @author JiaQi
 *
 */
public class safeTypeDao {
	private DBO db = new DBO();
	
	public boolean add(String safeType){
		/**
		 * 添加条目
		 */
		try{
		db.update("INSERT INTO safeType (safeType) VALUES ('"+safeType+"')");
		}catch (Exception e) {
		}
		return true;
	}
	
	public boolean del(String safeType){
		/**
		 * 删除条目
		 */
		try{
		db.update("DELETE FROM safeType WHERE safeType = ('"+safeType+"')");
		}catch(Exception e){
		}
		return false;
	}

	public String[] findsafeType_dec() {
		/**
		 * 查找参保类型字典
		 */
		ArrayList<String> stList = new ArrayList<String>();
		ResultSet rs = db.getRs("SELECT * FROM safeType");
		try{
			while(rs.next()){
				stList.add(rs.getString("safeType"));
			}
		}catch (Exception e) {
		}
		db.closed();
		String[] safeType_dec = new String[stList.size()];
		int i = 0;
		while(i<stList.size()){
			safeType_dec[i] = stList.get(i);
			i++;
		}
		return safeType_dec;
	}
}
