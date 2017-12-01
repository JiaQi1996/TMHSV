package org.zqrc.tmhs.control.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zqrc.tmhs.control.dao.townDao;

/**
 * 乡镇事务处理
 * @author JiaQi
 *
 */
public class townService {
private townDao td=new townDao();
	
	public boolean add(String town){
		/**
		 * 添加救助类型
		 */
		
		if(td.add(town)){
			return true;
		}else{
			return false;
		}	
	}
	
	public boolean del(String t){
		/**
		 * 删除救助类型
		 */
		if(td.del(t)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String[] findtown_dec(){
		/*
		 * 得到城镇字典
		 */
		return td.findtown_dec();
	}
}
