package org.zqrc.tmhs.control.service;

import org.zqrc.tmhs.control.dao.setsDao;


/**
 * 救助类型事物处理
 * @author JiaQi
 *
 */

public class helpTypeService {
	private setsDao sd=new setsDao();
	
	public boolean add(String safeType){
		/**
		 * 添加救助类型
		 */
		
		return sd.add(safeType);
	
	}
	
	public void del(String safeType){
		/**
		 * 删除救助类型
		 */
		sd.del(safeType);
	}
	
	public String[] findhelpType_dec(){
		
		return sd.findhelpType();
	}
}
