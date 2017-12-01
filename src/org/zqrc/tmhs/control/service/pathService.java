package org.zqrc.tmhs.control.service;

import org.zqrc.tmhs.control.dao.pathDao;

/**
 * 路径事物处理层
 * @author JiaQi
 *
 */

public class pathService {
	private pathDao pd=new pathDao();
	private String path1;
	private String path2;
	
	public String getpath1(){
		
		path1 = pd.getpath1();
		
		return path1;
	}
	
	public String getpath2(){
		
		path2 = pd.getpath2();
		return path2;
	}
	
	public boolean setpath1(String path1){
		if(pd.setpath1(path1)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean setpath2(String path2){
		if(pd.setpath2(path2)){
			return true;
		}else{
			return false;
		}
	}
}
