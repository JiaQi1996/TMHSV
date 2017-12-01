package org.zqrc.tmhs.control.service;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zqrc.tmhs.control.dao.billDao;
import org.zqrc.tmhs.control.dao.caseDao;
import org.zqrc.tmhs.control.dbo.DBO;
import org.zqrc.tmhs.control.until.DateUtil;
import org.zqrc.tmhs.control.until.ExcelFactory;
import org.zqrc.tmhs.control.until.FilePathDialog;
import org.zqrc.tmhs.control.until.jdbcConfig;
import org.zqrc.tmhs.view.method.GetDate;

public class outPutService {
	private DBO db = new DBO();
	private caseDao cd = new caseDao();
	private billDao bd = new billDao();
	private GetDate gd = new GetDate();
	
	public boolean outPutSelect(String[][] data) {
		
		/**
		 * 导出查询cases
		 * data二维数组表
		 * 导出表中数据
		 */
		String[] titles={"单号","姓名","身份证号","乡镇","参保类型","救助类型","救助比例","起付线","封顶线","费用合计","医保记账","自费费用","大病保险","困难群众大病补充险","其他抵扣项","合理支出","救助金额","票据数","添加人"};
		
		String s=DateUtil.getDateYMD()+"救助信息表";
		boolean temp=ExcelFactory.createAllXLS(FilePathDialog.getPath(),s, s, titles, data);
		if(temp){
			ExcelFactory.createAllXLS(jdbcConfig.getConfig()[1]+File.separator+"TMHSystem"+File.separator+"backup"+File.separator+"excels",s, s, titles, data);
		}
		return temp;
		
	}

	public boolean outPutOld() {
		/**
		 * 导出并清除往年数据
		 * caseData case表
		 */
		String s="往年救助信息表-"+DateUtil.getDateYMD()+"导出";
		//获得cases数据
		String[][] caseData = cd.findData("SELECT * FROM cases WHERE date NOT LIKE '%"+gd.getyear()+"'");
		
		String[] titles={"单号","姓名","身份证号","乡镇","参保类型","救助类型","救助比例","起付线","封顶线","费用合计","医保记账","自费费用","大病保险","困难群众大病补充保险","其他抵扣项","合理支出","救助金额","票据数","添加人"};

		boolean temp=ExcelFactory.createAllXLS(FilePathDialog.getPath(),s, s, titles, caseData);
		
		if(temp){
			ExcelFactory.createAllXLS(jdbcConfig.getConfig()[1]+File.separator+"TMHSystem"+File.separator+"backup"+File.separator+"excels",s, s, titles, caseData);
		}
		
		return temp;
	}
}
