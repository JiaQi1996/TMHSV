package org.zqrc.tmhs.control.service;

import org.zqrc.tmhs.control.bean.Case;
import org.zqrc.tmhs.control.bean.sets;
import org.zqrc.tmhs.control.dao.caseDao;

/**
 * 病历单事物处理
 * @author JiaQi
 *
 */
public class caseService {
	private billService bs = new billService();
	private caseDao cd = new caseDao();
	private setsService ss =new setsService();
	public String findByIDcard(String IDcard, String helpType) {
		/*
		 * 通过idcard查找
		 */
		return cd.findByIDcard(IDcard,helpType);		
	}
	
	public String findHelpTypeByIDcard(String IDcard) {
		/*
		 * 通过idcard查找救助类型
		 */
		return cd.findHelpTypeByIDcard(IDcard);
	}
	
	public String findCanUse(String helpType,String IDcard){
		/*
		 * 查找是否可以使用
		 */
		return cd.findCanUse(helpType,IDcard);
	}
	
	public Double findCanPay(String helpType,String IDcard){
		/*
		 * 返回封顶线（救助类型封顶线-已用）
		 * (ss.findByHelpType(helpType).getEndPay()-cs.findByIDcard(IDcard).getAllPay())
		 */
		return (Double.parseDouble(findByHelpType(helpType).getEndPay())-Double.parseDouble(findByIDcard(IDcard,helpType)));
	}

	private sets findByHelpType(String helpType) {
		
		return ss.findByHelpType(helpType);
	}

	public String[][] findBillData(String searchType, String searchData,
			String year, String month) {	
		/*
		 * 查询病例单
		 */
		return cd.findBillData(searchType,searchData,year, month);
	}

	public String findSumBill() {
		/*
		 * 查询所有病例单数量
		 */
		return cd.findSumBill();
	}

	public String findSumPeople() {
		/*
		 * 获得病例Data中人数（身份证号重复不计）
		 */
		return cd.findSumPeople();
	}

	public String findSumMoney() {
		/*
		 * 查询救助总金额
		 */
		return cd.findSumMoney();
	}

	public void del_data() {
		/**
		 * 删除往年数据
		 */
		cd.del_data();
	}

	public void addcase(String id,String name, String iDNO, String town, String safeType,
			String helpType, double helpScale1, double startPay1, double endPay1,
			double sumCol2, double sumCol3, double sumCol4, double sumCol5,double sumCol6,double sumCol7,double sumPay1,double helpPay1,String userName,String billNum) {
		/**
		 * 增加病历单
		 */
		String helpScale = String.valueOf(helpScale1);
		String startPay = String.valueOf(startPay1);
		String endPay = String.valueOf(endPay1);
		String payTotal = String.valueOf(sumCol2);
		String account = String.valueOf(sumCol3);
		String selfPay = String.valueOf(sumCol4);
		String bigSafe = String.valueOf(sumCol5);
		String supSafe = String.valueOf(sumCol6);
		String otherPay = String.valueOf(sumCol7);
		String sumPay = String.valueOf(sumPay1);
		String helpPay = String.valueOf(helpPay1);
		cd.addcase(id,name,iDNO,town,safeType,
				helpType,helpScale,startPay,endPay,payTotal,account,
				selfPay,bigSafe,supSafe,otherPay,sumPay,helpPay,
				userName,billNum);
	}
}
