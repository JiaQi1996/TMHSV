package org.zqrc.tmhs.control.bean;
/**
 * 设置城镇字典bean
 * @author JiaQi
 *
 */
public class town {
	//乡镇
	private String town;
	
	public town(){
	}
	
	public town(String town){
		super();
		this.town=town;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
}
