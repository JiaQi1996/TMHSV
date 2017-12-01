package org.zqrc.tmhs.control.service;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.dao.billDao;

/**
 * 票据 事务处理
 * @author JiaQi
 *
 */
public class billService {
	
	private billDao bd = new billDao();

	public String[][] findBillsData(String billNO) {
		/*
		 * 通过所属病例查询票据
		 */
		return bd.findCasesData(billNO);
	}

	public void addbill(String[][] tableValue, String id) {
		/**
		 * 增加数据库中bill
		 */
		bd.addbill(tableValue,id);
	}

	public void update(String[][] value, String id,Double sumCol2,Double sumCol3,Double sumCol4,Double sumCol5,Double sumCol6,Double sumCol7,Double sumPay,Double helpPay) {
		/**
		 * 删除并重新插入数据
		 */
		
		if(value==null||value.length==0||(value.length==1&&value[0].length==0)){
			JOptionPane.showConfirmDialog(null, "票据为空,请检查！");
		}else{
			bd.updata(value,id,String.valueOf(sumCol2),String.valueOf(sumCol3),String.valueOf(sumCol4),String.valueOf(sumCol5),String.valueOf(sumCol6),String.valueOf(sumCol7),String.valueOf(sumPay),String.valueOf(helpPay));
		}
	}

}
