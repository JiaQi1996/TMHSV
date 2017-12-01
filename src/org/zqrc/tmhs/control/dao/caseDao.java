package org.zqrc.tmhs.control.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.bean.Case;
import org.zqrc.tmhs.control.dbo.DBO;
import org.zqrc.tmhs.view.method.GetDate;
import org.zqrc.tmhs.view.method.GetTime;

import sun.tools.jar.Main;
/**
 * 病例 数据操作层
 * @author JiaQi
 *
 */
public class caseDao {
	private GetDate gd = new GetDate();
	private Case cases = new Case();
	private DBO db = new DBO();
	private GetDate gt = new GetDate();
	
	/**
	 * 通过id查询救助类型
	 * @param iDcard
	 * @return
	 */
	public String findHelpTypeByIDcard(String iDcard) {
		ResultSet rs = db.getRs("SELECT helpType FROM cases WHERE IDcard = '"+iDcard+"' AND date LIKE '%"+gd.getyear()+"'");
		String str="";
		try {
			if((rs.next())){
				str=rs.getString("helpType");
			}
		} catch (SQLException e) {
			return "";
		}finally{
			db.closed();
		}
		return str;
	}

	public String findByIDcard(String IDcard, String helpType) {
		/*
		 * 通过idcard 查找
		 * 返回case对象
		 */
		ResultSet rs = db.getRs("SELECT * FROM cases WHERE IDcard = '"+IDcard+"' AND helpType = '"+helpType+"' AND date LIKE '%"+gd.getyear()+"'");
		Double d = 0.0;
		try {
			if(!(rs.next())){

			}else{
				db.closed();
				rs = db.getRs("SELECT * FROM cases WHERE IDcard = '"+IDcard+"' AND helpType = '"+helpType+"' AND date LIKE '%"+gd.getyear()+"'");
				while(rs.next()){
					d =d + Double.parseDouble(rs.getString(17));
				}
			}
		} catch (Exception e) {
		}
		db.closed();
		String payTotal = String.valueOf(d);
		
		return payTotal;
	}

	public String findCanUse(String helpType,String IDcard){
		/*
		 * 通过救助类型 身份证查询是否可以使用
		 * 返回字符串 是 否
		 */
		ResultSet rs = db.getRs("SELECT * FROM sets WHERE helpType = '"+helpType+"'");
		try {
			if(rs.getString("moreUse").equals("是")){
				db.closed();
				return "是";
			}else{
				db.closed();
				ResultSet rs1 = db.getRs("SELECT * FROM cases WHERE IDcard = '"+IDcard+"' and helpType = '"+helpType+"' and date like '%"+gd.getyear()+"'");
				if(!rs1.next()){
					return "是";
				}else{
					return "否";
				}
			}
		} catch (SQLException e) {
			db.closed();
			return "查询失败";
		}
	}

	public Case findByHelpType(String helpType) {
		/*
		 * t通过救助类型查找
		 * 返回case对象
		 */
		try{
			ResultSet rs = db.getRs("SELECT * FROM cases WHERE helpType = '"+helpType+"'");

			cases.setId("id");
			cases.setName("name");
			cases.setIDcard("IDcard");
			cases.setTown("town");
			cases.setSafeType("safeType");
			cases.setHelpType("helpType");
			cases.setHelpScale("helpScale");
			cases.setStartPay("startPay");
			cases.setEndPay("endPay");
			cases.setPayTotal("payTotal");
			cases.setAccount("account");
			cases.setSelfPay("selfPay");
			cases.setBigSafe("bigSafe");
			cases.setReaPay("reaPay");
			cases.setHelpPay("helpPay");
			cases.setBillNum("billNum");
			cases.setByName("byName");
			cases.setDate("date");
			db.closed();
		}catch (Exception e) {
		}
		return  cases;
	}

	public String[][] findBillData(String searchType, String searchData,
			String year, String month) {
		/*
		 * 当都为空或后三项为空时返回整张表	搜索类型 搜索数据 年 月
		 * 查找病历单
		 */
		
		if((checkNull(searchData)&&checkNull(year)&&checkNull(month))){
			//后三项为空
			
			return  findData("SELECT * FROM cases");
		}else{
			//后三项不为空
			if(checkNull(searchData)){
				//如果第二项为空
				if(!checkNull(year)&&checkNull(month)){
					//年份不为空 月份为空
					return findData("SELECT * FROM cases WHERE date LIKE '%"+year+"'");
				}else if(checkNull(year)&&!checkNull(month)){
					//第二项不为空 年份为空
					 return findData("SELECT * FROM cases WHERE date LIKE '"+month+"%'");
				  }else{
					  //依照日期检索
					 return findData("SELECT * FROM cases WHERE date LIKE '"+month+year+"'");
				}
			}else{
				//第二项不为空 依照第二项查询
				if(searchType.equals("单号")){
					
					return findData("SELECT * FROM cases WHERE id = '"+searchData+"'");
				}else if(searchType.equals("身份证号")){
					return findData("SELECT * FROM cases WHERE IDcard = '"+searchData+"'");
				}else if(searchType.equals("姓名")){
					return findData("SELECT * FROM cases WHERE name = '"+searchData+"'");
				}else if(searchType.equals("模糊查询")){
					return findData("SELECT * FROM cases WHERE id LIKE '%"+searchData+"%'");
				}
			}
		} 
		return null;
	}

	/**
	 * 返回false表示不为空
	 * @param str
	 * @return
	 */
	public boolean checkNull(String str) {
		try{
			if(str.isEmpty()){
				return true;
			}
		}catch (Exception e) {
			return true;
		}
		return false;
	}

	public String findSumBill() {
		/*
		 * 查询数据库中所有救助单数
		 */
		ResultSet rs = db.getRs("SELECT * FROM cases");
		int i = 0;
		try {
			while(rs.next()){
				i++;
			}
		} catch (SQLException e) {

		}
		db.closed();
		String sumBill = String.valueOf(i);
		return sumBill;
	}

	public String findSumPeople() {
		/*
		 * 获得病例Data中人数（身份证号重复不计）
		 */
		ResultSet rs = db.getRs("SELECT DISTINCT IDcard FROM cases");
		int i = 0;
		try {
			while(rs.next()){
				i++;
			}
		} catch (SQLException e) {
		}
		db.closed();
		String sumPeople = String.valueOf(i);
		return sumPeople;
	}

	public String findSumMoney() {
		/*
		 * 查询救助总金额
		 */
		Double sm = 0.0;
		ResultSet rs = db.getRs("SELECT * FROM cases");
		try {
			while(rs.next()){
				sm = sm + Double.valueOf(rs.getString("helpPay"));
			}
		} catch (SQLException e) {
		}
		db.closed();
		String sumMoney = String.valueOf(sm);
		return sumMoney;
	}

	//检索数据库  病历单
	public String[][] findData(String sql) {	
		/*
		 * 当都为空或后三项为空时返回整张表	搜索类型 搜索数据 年 月
		 * 查找病历单
		 */
		
		ResultSet rs = db.getRs(sql);
		ArrayList<Case> cList = new ArrayList<Case>();	
		try {
			if(!(rs.next())){
				db.closed();
				return null;
			}else{
				db.closed();
			}
			rs = db.getRs(sql);
			while(rs.next()){
				Case c = new Case();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setIDcard(rs.getString("IDcard"));
				c.setTown(rs.getString("town"));
				c.setSafeType(rs.getString("safeType"));
				c.setHelpType(rs.getString("helpType"));
				c.setHelpScale(rs.getString("helpScale"));
				c.setStartPay(rs.getString("startPay"));
				c.setEndPay(rs.getString("endPay"));
				c.setPayTotal(rs.getString("payTotal"));
				c.setAccount(rs.getString("account"));
				c.setSelfPay(rs.getString("selfPay"));
				c.setBigSafe(rs.getString("bigSafe"));
				c.setSupSafe(rs.getString("supSafe"));
				c.setOtherPay(rs.getString("otherPay"));
				c.setReaPay(rs.getString("reaPay"));
				c.setHelpPay(rs.getString("helpPay"));
				c.setBillNum(rs.getString("billNum"));
				c.setByName(rs.getString("byName"));
				c.setDate(rs.getString("date"));

				cList.add(c);
			}
		} catch (SQLException e) {
		}
		String[][] billData = new String[cList.size()][19];
		int i = 0;
		while(i<cList.size()){
			billData[i][0] = cList.get(i).getId();
			billData[i][1] = cList.get(i).getName();
			billData[i][2] = cList.get(i).getIDcard();
			billData[i][3] = cList.get(i).getTown();
			billData[i][4] = cList.get(i).getSafeType();
			billData[i][5] = cList.get(i).getHelpType();
			billData[i][6] = cList.get(i).getHelpScale();
			billData[i][7] = cList.get(i).getStartPay();
			billData[i][8] = cList.get(i).getEndPay();
			billData[i][9] = cList.get(i).getPayTotal();
			billData[i][10] = cList.get(i).getAccount();
			billData[i][11] = cList.get(i).getSelfPay();
			billData[i][12] = cList.get(i).getBigSafe();
			billData[i][13] = cList.get(i).getSupSafe();
			billData[i][14] = cList.get(i).getOtherPay();
			billData[i][15] = cList.get(i).getReaPay();
			billData[i][16] = cList.get(i).getHelpPay();
			billData[i][17] = cList.get(i).getBillNum();
			billData[i][18] = cList.get(i).getByName();
			i++;
		}
		//String billData[][]={{"201610101010101012011X","张三","410100194012123826","白马镇","新农合","五保","80","1000","10000","20000","8000","2000","0","10000","8000","5","2016"}};
		return billData;
	}

	public void del_data() {
		/**
		 * 删除往年数据
		 * 除今年外
		 */
		ArrayList<String> cList = new ArrayList<String>();
		ResultSet rs = db.getRs("SELECT * FROM cases WHERE date NOT LIKE '%"+gt.getyear()+"'");
		try {
			while(rs.next()){
				cList.add(rs.getString("id"));
			}
		} catch (SQLException e) {
		}
		db.closed();
		try{
			db.update("DELETE FROM cases WHERE date NOT LIKE '%"+gt.getyear()+"'");
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "更新单据失败！");
		}
		int i = 0;
		while(i<cList.size()){
			try{
				db.update("DELETE FROM bill WHERE caseId = '"+cList.get(i)+"'");
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "更新单据失败！");
			}
			i++;
		}
	}

	public void addcase(String id, String name, String iDNO, String town,
			String safeType, String helpType, String helpScale,
			String startPay, String endPay,String payTotal,String account,
			String selfPay,String bigSafe,String supSafe,String otherPay,String sumPay,String helpPay,
			String userName,String billNum) {
		//增加病历单
		try {
		db.update("insert into cases (id,name,IDcard,town,safeType,helpType,helpScale,startPay,endPay,payTotal,account,selfPay,bigSafe,supSafe,otherPay,reaPay,helpPay,billNum,byName,date) values ('"+
				id+"','"+
				name+"','"+
				iDNO+"','"+
				town+"','"+
				safeType+"','" +
				helpType+"','"+
				helpScale+"','"+
				startPay+"','"+
				endPay+"','"+
				payTotal+"','"+
				account+"','"+
				selfPay+"','"+
				bigSafe+"','"+
				supSafe+"','"+
				otherPay+"','"+
				sumPay+"','"+
				helpPay+"','"+
				billNum+"','"+
				userName+"','"+
				gt.gettime()+"')");
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "更新单据失败！");
		}
	}
}