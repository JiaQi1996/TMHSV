package org.zqrc.tmhs.control.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.bean.user;
import org.zqrc.tmhs.control.dbo.DBO;
import org.zqrc.tmhs.view.login.Login;

/**
 * user数据操作层
 * @author 李志飞
 *
 */
public class userDao{
	private DBO db = new DBO();
	private caseDao cd = new caseDao();
	
	public user findById(String id) {
		ResultSet rs = db.getRs("SELECT * FROM user WHERE id = '"+id+"'");
		String name = null;
		String pass = null;
		String role = null;
		try {
			name = rs.getString("name");
			pass = rs.getString("pass");
			role = rs.getString("role");
		} catch (SQLException e) {
		}
		db.closed();
		return new user(id,name,pass,role);
	}

	public user findByName(String id) {
		return null;
	}

	public ArrayList<user> findbyRole(String role) {
		return null;
	}

	public boolean add(user u) {
		/**
		 * 增加操作员
		 */
			try{
				db.update("insert into user (id,name,pass,role) values ('"+u.getId()+"','"+u.getName()+"','"+u.getPass()+"','"+u.getRole()+"')");
			}catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "添加用户失败！");
			}
			return true;
		
	}

	
	public boolean delById(String id) {
		/**
		 * 删除操作员
		 */
			try{
				db.update("DELETE FROM user WHERE id = '"+id+"'");
			}catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "删除用户失败！");
			}
		return true;

	}
	
	public boolean updata(user u) {
		
		/**
		 * 修改信息
		 */
		try{
		db.update("UPDATE user SET name = '"+u.getName()+"',pass = '"+u.getPass()+"' WHERE id = '"+u.getId()+"'");
		}catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "修改用户失败！");
		}
		return true;
	}
	
	public boolean LoginChack(String id,String pass){
		/**
		 * 检测登陆账号密码
		 */
		ResultSet rs = db.getRs("SELECT * FROM user WHERE id = '"+id+"'");
		try {
			if(rs.getString("pass").equals(pass)){
				db.closed();

				return true;
			}else{
				db.closed();
				return false;
			}
		} catch (SQLException e) {
			db.closed();
			return false;
		}
	}

	public String[][] findAll(String data) {
		/**
		 * 根据data检索数据库user
		 * 返回二维数组
		 * 取消模糊查询
		 */
		
		if(cd.checkNull(data)){
			return finduserData("SELECT * FROM user");
		}else{

			return finduserData("SELECT * FROM user WHERE id = '"+data+"'");
		}

	}
	
	public String[][] finduserData(String sql){
		/**
		 * 检索user数据库
		 */
		ArrayList<user> uList = new ArrayList<user>();
		
		ResultSet rs = db.getRs(sql);
		
		try {
			while(rs.next()){
				user u = new user();
				u.setId(rs.getString("id"));
				u.setName(rs.getString("name"));
				u.setPass(rs.getString("pass"));
				u.setRole(rs.getString("role"));
				
				uList.add(u);
			}
		} catch (SQLException e) {
		}
		
		db.closed();
		
		int i = 0;
		String[][] userData = new String[uList.size()][4];
		while(i<uList.size()){
			userData[i][0] = uList.get(i).getId();
			
			if(uList.get(i).getRole().equals("0")){
				userData[i][1] = "超级管理员";
			}else if(uList.get(i).getRole().equals("1")){
				userData[i][1] = "管理员";
			}else if(uList.get(i).getRole().equals("2")){
				userData[i][1] = "操作员";
			}
			
			userData[i][2] = uList.get(i).getName();
			userData[i][3] = uList.get(i).getPass();
			i++;
		}
	   return userData;
	}

}
