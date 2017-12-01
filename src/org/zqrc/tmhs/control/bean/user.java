package org.zqrc.tmhs.control.bean;

/**
 * 管理用户对象
 * @author 李志飞
 *
 */
public class user {
//	账号
	private String id;
//	名称
	private String name;
//	密码
	private String pass;
//	角色
	private String role;
	
	public user() {
	}
	
	public user(String id, String name, String pass, String role) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String toString() {
		return "user [id=" + id + ", name=" + name + ", pass=" + pass
				+ ", role=" + role + "]";
	}
	
}
