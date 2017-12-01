package org.zqrc.tmhs.view.main.centerPanel.setUser;

import org.zqrc.tmhs.control.bean.user;
import org.zqrc.tmhs.control.service.userService;

/*
 * By Gorden @2016-10-25
 * 此类用于进行用户管理页面与逻辑层进行通信
 */
public class SetUser {
	private userService us = new userService();
	public String[][] getUserData(String data){
		/*
		 * 获取用户信息，当data=null时检索所有数据，当data不为null时进行模糊搜索
		 * 以下数据为模拟数据可删除
		 */
		
		//String[][] userData={{"admin","管理员","超级管理员","123456"},{"1001","操作员","张三","123456"},{"1002","操作员","","123456"}};
		
		return us.findAll(data);
	}

	public void updateUserData(String userName,String name,String password) {
		/*
		 * 修改用户的信息 账号、姓名、密码
		 */
		user u = new user(userName,name,password,"2");
		us.updataAdmin(u);
	}

	public void DelectUserData(String userName) {
		/*
		 * 删除账户信息 账号
		 */
		us.delAdmin(userName);
	}

	public void addUser(String userName,String name,String password) {
		/*
		 * 添加用户 账号、姓名、密码
		 */
		user u = new user(userName,name,password,"2");
		us.addAdmin(u);
	}
}
