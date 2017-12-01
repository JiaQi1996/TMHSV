package org.zqrc.tmhs.control.service;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.until.PrintTest;

public class printService {

	/**
	 * 打印个人单据
	 * @param userName  用户账号
	 * @param name  救助人姓名
	 * @param iDNO  身份证号
	 * @param town  城镇
	 * @param safeType  参保类型
	 * @param helpType   救助类型
	 * @param helpScale  救助比例
	 * @param startPay  起付线
	 * @param endPay 封顶线
	 * @param value   bill单据集合
	 * @param sumCol2         费用合计
	 * @param sumCol3     医保记账
	 * @param sumCol4     自费费用
	 * @param sumCol5    大病保险
	 * @param sumPay    合理支出
	 * @param helpPay    救助金额
	 * @param message
	 */
	public boolean printBill(String id,String userName, String name, String iDNO,
			String town, String safeType, String helpType, double helpScale,
			double startPay, double endPay, String[][] value, double sumCol2,
			double sumCol3, double sumCol4, double sumCol5,double sumCol6, double sumCol7, double sumPay,
			double helpPay, int message) {//message:useless
		PrintTest pt=new PrintTest(id,userName, name, iDNO, town, safeType, helpType, helpScale, startPay, endPay, value, sumCol2, sumCol3, sumCol4, sumCol5, sumCol6, sumCol7, sumPay, helpPay);
		
		/*
		 * 重新打印数据,如果message=1,表中数据会被改变
		 */
		if(value==null||value.length==0||(value.length==1&&value[0].length==0)){
			//票居为空
			return false;
		}else{
			return pt.printBean();
		}
		
	}

}
