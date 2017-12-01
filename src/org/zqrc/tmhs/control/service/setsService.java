package org.zqrc.tmhs.control.service;
/**
 * 设置事物管理
 */

import org.zqrc.tmhs.control.bean.sets;
import org.zqrc.tmhs.control.dao.setsDao;

public class setsService {
	private setsDao sd = new setsDao();
	
	public boolean update(String helpType,String helpScale,String startPay,String endPay,String moreUse){
		/**
		 * 更改设置
		 */
		if(sd.updata(helpType, helpScale, startPay, endPay, moreUse)){		
			return true;
		}else{
			return false;
		}				
	}
	
	public sets findByHelpType(String helpType){
		/**
		 * 通过救助类型查找	·
		 * 返回对象
		 */		
		return sd.findbyhelpType(helpType);		
	}
	public double findLastLadderScaleID(){
		/***
		 * 获取最后一个阶梯值的ID值
		 */
		return sd.findLastLadderScaleID();
	}

	public void insertLadderScale(double id,double[][] data) {
		// TODO Auto-generated method stub
		sd.insertLadderScale(id,data);
	}

	public double[][] findLadderScale(String id) {
		// TODO Auto-generated method stub
		return sd.findLadderScale(id);
	}
}
