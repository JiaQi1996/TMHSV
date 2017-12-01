package org.zqrc.tmhs.control.service;

import java.util.ArrayList;

import org.zqrc.tmhs.control.bean.user;
import org.zqrc.tmhs.control.dao.userDao;

/**
 * 用户事务管理
 * @author 李志飞
 *
 */
public class userService{

	private userDao ud=new userDao();
	
	public boolean login(user u) {
		/**
		 * 登录检测方法
		 */
		if(!ud.LoginChack(u.getId(),u.getPass())){
			return false;
		}else{
		return true;
		}
	}

	public boolean addAdmin(user u) {
		
		return ud.add(u);
	}

	public boolean updataAdmin(user u) {
		
		return ud.updata(u);
	}

	public boolean delAdmin(String id) {
		
		return ud.delById(id);
	}

	public user findById(String id) {
		
		return ud.findById(id);
	}

	public String[][] findAll(String data) {
		
		return ud.findAll(data);
	}

}
