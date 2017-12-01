package org.zqrc.tmhs.view.main.centerPanel.historyBill;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.service.billService;
import org.zqrc.tmhs.control.service.caseService;
import org.zqrc.tmhs.control.service.outPutService;
import org.zqrc.tmhs.control.service.printService;

public class HistoryBill{
	/*
	 * By Gorden @ 2016-10-27
	 * 此类用以完成
	 */
	private outPutService os = new outPutService();
	private printService ps = new printService();
	private billService bs = new billService();
	private caseService cs = new caseService();
	
	public String[][] getBillData(String searchType, String searchData, String year,String month) {
		//当都为空或后三项为空时返回整张表	搜索类型 搜索数据 年 月
		//String billData[][]={{"201610101010101012011X","张三","410100194012123826","白马镇","新农合","五保","80","1000","10000","20000","8000","2000","0","10000","8000","5","2016"}};
		
		
		return cs.findBillData(searchType,searchData,year,month);
	}
	
	public String getSumBill(){
		/*
		 * 获得billData中单据数量
		 */
		return cs.findSumBill();
	}
	public String getSumPeople(){
		/*
		 * 获得billData中人数（身份证号重复不计）
		 */
		return cs.findSumPeople();
	}
	public String getSumMoney(){
		/*
		 * 获得billData中救助金额的累加
		 */
		return cs.findSumMoney();
	}
	
//	
	public void outData(String[][] data){
		/*
		 * 导出查询出的数据
		 */
		os.outPutSelect(data);
	}
	
//	
	public boolean out_delHistoryData() {
		
		/*
		 * 导出并删除往年历史数据（不包含本年）
		 */
		boolean b=false;
		if(b=os.outPutOld()){
			cs.del_data();
		}
		return b;
	}
	
	public String[][] getBillsData(String billNO) {
		
		return bs.findBillsData(billNO);
	}
	
	public boolean printBill(String id,String userName, String name, String iDNO,
			String town, String safeType, String helpType, double helpScale,
			double startPay, double endPay, String[][] value, double sumCol2,
			double sumCol3, double sumCol4, double sumCol5,double sumCol6, double sumCol7,double sumPay,
			double helpPay,int message) {
		
		/*
		 * 重新打印数据,如果message=1,表中数据会被改变
		 * sumCol2 费用合计
		 * sumCol3医保记账
		 * sumCol4资费费用
		 * sumCol5大病保险
		 * sumPaye合理支出
		 * helpPay救助金额
		 */
		boolean isprint=false;
		if(message == 1){
			if(isprint=ps.printBill(id, userName, name, iDNO, town, safeType, helpType, helpScale, startPay, endPay, value, sumCol2, sumCol3, sumCol4, sumCol5,sumCol6,sumCol7, sumPay, helpPay, message)){
				bs.update(value,id,sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7,sumPay,helpPay);
			}
		}else{
			isprint=ps.printBill(id, userName, name, iDNO, town, safeType, helpType, helpScale, startPay, endPay, value, sumCol2, sumCol3, sumCol4, sumCol5, sumCol6, sumCol7, sumPay, helpPay, message);
		}
		return isprint;
	}
}
