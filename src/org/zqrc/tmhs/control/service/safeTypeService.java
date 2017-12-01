package org.zqrc.tmhs.control.service;

import org.zqrc.tmhs.control.dao.safeTypeDao;
/**
 * 参保类型事物处理
 * @author JiaQi
 *
 */
public class safeTypeService {
	private safeTypeDao sd=new safeTypeDao();
	
	public boolean add(String safeType){
		/**
		 * 添加救助类型
		 */
		
		if(sd.add(safeType)){
			return true;
		}
		else{
			return false;
		}	
	}
	
	public boolean del(String safeType){
		/**
		 * 删除救助类型
		 */
		if(sd.del(safeType)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public String[] findsafeType_dec(){
		/*
		 * 得到参保类型字典
		 */
		return sd.findsafeType_dec();	
	}
}
