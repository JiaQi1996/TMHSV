package org.zqrc.tmhs.view.login;

import org.zqrc.tmhs.control.bean.user;
import org.zqrc.tmhs.control.service.userService;

/*import org.zqrc.tmhs.bean.user;
import org.zqrc.tmhs.service.loginCheck;
*/

/*
 * By Gorden @ 2016-10-2
 * 此类用于和逻辑层代码进行沟通
 */
public class Login {
	private String userName,passWord,name,role;
	private Boolean b=false;
	
	private userService us=new userService();
	public Login(String userName,String passWord){
		this.userName=userName;
		this.passWord=passWord;
	}
	public Login() {
	}
	public String getName(){
		return name;
	}
	public String getRole(){
		return role;
	}
	public String getUserName(){
		return userName;
	}

	/**
	 * 在此对可修改参数b进行赋值
	 */
	public void auth(){
		if(us.login(new user(userName, name, passWord, role))){
			user u=us.findById(userName);
			name=u.getName();
			role=u.getRole();
			b=true;
		}else{
			b = false;
		}
		
	}
	public Boolean isTure(){
		auth();
		return b;
	}
}
