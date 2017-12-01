package org.zqrc.tmhs.control.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.zqrc.tmhs.control.bean.bill;
import org.zqrc.tmhs.control.dbo.DBO;

/**
 * 票据 数据操作 Dao
 * @author JiaQi
 *
 */
public class billDao {

	DBO db = new DBO();
	public String[][] findCasesData(String caseid) {
		/*
		 * 
		 * 通过所属病例查询票据
		 */
		
		ArrayList<bill> bList = new ArrayList<bill>();
		int i = 0;
		
		ResultSet rs = db.getRs("SELECT * FROM bill WHERE caseid = '"+caseid+"'");
		try {
			while(rs.next()){
				bill b = new bill();
				b.setId(rs.getString("id"));
				b.setPayTotal(rs.getString("payTotal"));
				b.setAccount(rs.getString("account"));
				b.setSelfPay(rs.getString("selfPay"));
				b.setBigSafe(rs.getString("bigSafe"));
				b.setSupSafe(rs.getString("supSafe"));
				b.setOtherPay(rs.getString("otherPay"));
				bList.add(b);
			}
		} catch (SQLException e) {
		}
		db.closed();
		String[][] billsData = new String[bList.size()][7];
		
		while(i<bList.size()){
			billsData[i][0] = bList.get(i).getId();
			billsData[i][1] = bList.get(i).getPayTotal();
			billsData[i][2] = bList.get(i).getAccount();
			billsData[i][3] = bList.get(i).getSelfPay();
			billsData[i][4] = bList.get(i).getBigSafe();
			billsData[i][5] = bList.get(i).getSupSafe();
			billsData[i][6] = bList.get(i).getOtherPay();
			i++;
		}
		
		return billsData;		
	}
	
	
	public void addbill(String[][] tableValue, String id) {
		/**
		 * 添加账单
		 */
		int i = 0;
		while(i<tableValue.length){
			try {
				db.update("insert into bill (id,payTotal,account,selfPay,bigSafe,supSafe,otherPay,caseId) values ('"+tableValue[i][0]+"','"+tableValue[i][1]+"','"+tableValue[i][2]+"','"+tableValue[i][3]+"','"+tableValue[i][4]+"','"+tableValue[i][5]+"','"+tableValue[i][6]+"','"+id+"')");
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "添加单据失败！");
			}
			i++;
		}
	}


	public void updata(String[][] value, String id,String sumCol2,String sumCol3,String sumCol4,String sumCol5,String sumCol6,String sumCol7,String sumPay,String helpPay) {
		/**
		 * 更新数据
		 * sumCol2 费用合计
		 * sumCol3医保记账
		 * sumCol4资费费用
		 * sumCol5大病保险
		 * sumCol6补充保险
		 * sumCol7其它抵扣
		 * sumPaye合理支出
		 * helpPay救助金额
		 */
		int i = 0;
		try {
			db.update("update cases set billNum = '"+value.length+"',payTotal = '"+sumCol2+"',account = '"+sumCol3+"',selfPay = '"+sumCol4+"',bigSafe = '"+sumCol5+"',supSafe='"+sumCol6+"',otherPay='"+sumCol7+"',reaPay = '"+sumPay+"',helpPay = '"+helpPay+"' where id = '"+id+"'");
			db.update("DELETE FROM bill WHERE caseId = '"+id+"' ");
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "更新单据失败！");
		}
		
		while(i<value.length){
			
			try {
				db.update("INSERT INTO bill (id, payTotal, account,selfPay,bigSafe,caseId) VALUES ('"+value[i][0]+"', '"+value[i][1]+"', '"+value[i][2]+"', '"+value[i][3]+"','"+value[i][4]+"','"+id+"')");
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "更新单据失败！");
			}
			i++;
		}
	}

}
