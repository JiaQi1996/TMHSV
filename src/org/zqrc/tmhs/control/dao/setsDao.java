package org.zqrc.tmhs.control.dao;
/**
 * sets数据操作层
 */

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.bean.sets;
import org.zqrc.tmhs.control.dbo.DBO;
import org.zqrc.tmhs.view.method.GetDate;

public class setsDao {
	private DBO db = new DBO();
	//救助类型
	public sets findbyhelpType(String helpType){
		sets s = new sets();
		
		ResultSet rs = db.getRs("select * from sets where helpType = '"+helpType+"'");
		try {
			s = new sets(rs.getString("helpType"), rs.getString("helpScale"), rs.getString("startPay"), rs.getString("endPay"), rs.getString("moreUse"));
		} catch (SQLException e) {
		}finally{
			db.closed();
		}
		db.closed();  
		return s;
	}
	
	//增加救助类型
	public boolean add(String helpType){
		try{
			db.update("INSERT INTO sets (helpType,helpScale,startPay,endPay,moreUse) VALUES ('"+helpType+"','10','1000','10000','否')");
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, "添加救助类型失败！");
			return false;
		}
		return true;
	}
	
	//删除救助类型
	public void del(String helpType){
		try{
			db.update("DELETE FROM sets WHERE helpType = ('"+helpType+"')");
		}catch(Exception e){
		}

	}
	
	//找到全部救助类型
	public String[] findhelpType() {
		
		ArrayList<String> ftList = new ArrayList<String>();
		ResultSet rs = db.getRs("select * from sets");
		try {
			while(rs.next()){
				ftList.add(rs.getString("helpType"));
			}
		} catch (SQLException e) {
		}finally{
			db.closed();
		}
		db.closed();
		String[] helpType = new String[ftList.size()];
		int i = 0;
		while(i<ftList.size()){
			helpType[i] = ftList.get(i);
			i++;
		}
		return helpType;
	}
	
	//更改
	public boolean updata(String helpType,String helpScale,String startPay,String endPay,String moreUse){
		try{
			db.update("UPDATE sets SET helpScale = '"+helpScale+"', startPay = '"+startPay+"', endPay = '"+endPay+"', moreUse = '"+moreUse+"' WHERE helpType ='"+helpType+"'");
		}catch (Exception e) {
		}
		return true;
	}

	public double findLastLadderScaleID() {
		// TODO Auto-generated method stub
		try{
			ResultSet rs = db.getRs("SELECT MAX(id) FROM ladderScale");
			while(rs.next()){
				return rs.getInt(1);
			}
		}catch (Exception e) {
			return 0;
		}finally{
			db.closed();
		}
		return 0;
	}

	public void insertLadderScale(double id,double[][] data) {
		// TODO Auto-generated method stub
		try{
			int lid = (int)id;
			String sql = "INSERT INTO ladderScale (id,start,end,scale) VALUES";
			for(int i = 0;i<data.length;i++){
				sql += " ('"+lid+"','"+data[i][0]+"','"+data[i][1]+"','"+data[i][2]+"')";
				if(i!=data.length-1){
					sql+=",";
				}
			}
			db.update(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public double[][] findLadderScale(String id){
		double[][] data = null;
		int row = 0;
		try{
			ResultSet rs = db.getRs("SELECT COUNT(*) AS rowCount FROM ladderScale WHERE id = "+id);
			row = rs.getInt("rowCount");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closed();
		}
		try{
			ResultSet rs = db.getRs("SELECT * FROM ladderScale WHERE id = "+id);
			int i=0;
			data = new double[row][3];
			while(rs.next()){
				data[i][0]=rs.getDouble(2);
				data[i][1]=rs.getDouble(3);
				data[i][2]=rs.getDouble(4);
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closed();
		}
		return data;
	}
}
