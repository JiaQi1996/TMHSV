package org.zqrc.tmhs.control.bean;
/**
 * 设置参保类型bean
 * @author JiaQi
 *
 */
public class safeType {
	//参保类型
	private String safeType;
	
	public safeType(){
	}
	
	public safeType(String safeType){
		super();
		this.safeType=safeType;
	}

	public String getSafeType() {
		return safeType;
	}

	public void setSafeType(String safeType) {
		this.safeType = safeType;
	}
}
