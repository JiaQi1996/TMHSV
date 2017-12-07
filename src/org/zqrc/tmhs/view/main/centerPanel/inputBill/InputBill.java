package org.zqrc.tmhs.view.main.centerPanel.inputBill;
import org.zqrc.tmhs.control.service.billService;
import org.zqrc.tmhs.control.service.caseService;
import org.zqrc.tmhs.control.service.printService;
import org.zqrc.tmhs.control.service.setsService;
import org.zqrc.tmhs.control.service.townService;
import org.zqrc.tmhs.control.service.safeTypeService;
import org.zqrc.tmhs.control.service.helpTypeService;
import org.zqrc.tmhs.view.login.Login;
import org.zqrc.tmhs.view.method.DateUtil;
import org.zqrc.tmhs.view.method.KeyUtil;
/*
 * By Gorden @2016-10-23
 * 此类用于录入账单页面与逻辑层进行数据交换
 */
public class InputBill {
	private printService ps = new printService();
	private setsService ss=new setsService();
	private townService ts = new townService();
	private safeTypeService st = new safeTypeService();
	private helpTypeService ht = new helpTypeService();
	private caseService cs = new caseService();
	private billService bs = new billService();
	private KeyUtil ku = new KeyUtil();
	private DateUtil du = new DateUtil();
	private Login l = new Login();
	private String helpType[];
	
	public String[] getHelpType(String IDCard){
		/*
		 * 救助类型
		 */
		helpType=new String[1];
		String str=cs.findHelpTypeByIDcard(IDCard);
		if(str==""){
			return null;
		}else{
			helpType[0]=str;
		}
		return helpType;
	}
	
	public double getHelpScale(String helpType) {
		/*
		 * 救助比例
		 * 此处为测试数据连接逻辑层时删除
		 */
		
		/*
		 * ？？？？？？？？？？？？？？？？？？
		 * 此处需对应进行修改
		 */
		
		return Double.parseDouble(ss.findByHelpType(helpType).getHelpScale());
	}
	
	public double getStartPay(String helpType) {
		/*
		 * 起付线
		 * 此处为测试数据连接逻辑层时删除
		 */
		return Double.parseDouble(ss.findByHelpType(helpType).getStartPay());
	}
	
	public double getEndPay(String helpType,String IDcard) {
		/*
		 * 封顶线，可多次使用的类型为封顶线减去之前救助总额
		 * 此处为测试数据连接逻辑层时删除
		 */
		return cs.findCanPay(helpType, IDcard);
	}
	
	public String getCanUse(String helpType,String IDcard) {
		/*
		 * 是否符合救助条件
		 * 此处为测试数据连接逻辑层时删除
		 */
		
		return cs.findCanUse(helpType,IDcard);
	}
	/***start启动1 请求参数**************/
	public String[] getTown_dec() {
		/*
		 * 获取城镇字典
		 * 此处为测试数据连接逻辑层时删除
		 */
		return ts.findtown_dec();
	}
	
	public String[] getSafeType_dec() {
		/*
		 * 获取参保类型字典
		 * 此处为测试数据连接逻辑层时删除
		 */
		return st.findsafeType_dec();
	}

	public String[] getHelpType_dec() {
		/*
		 * 获取救助类型字典
		 * 此处为测试数据连接逻辑层时删除
		 */
		return ht.findhelpType_dec();
	}

	/**end启动1 请求参数*******************/
	public boolean printBill(String userName,String name,String IDNO,String town,String safeType,String helpType,
							double helpScale,double startPay,double endPay,String[][] tableValue,
							double sumCol2,double sumCol3,double sumCol4,double sumCol5,double sumCol6,double sumCol7,double sumPay,double helpPay){
		//用户ID、救助人员姓名、救助人员身份证号、所属乡镇、参保类型、救助类型、救助比例、起付线、封顶线、表格中的值、表格第二列的和……表格第七列的和、合理支出、救助金额
		/*
		 * 打印功能写在这里
		 */
		
		//获得唯一标识码
		String id = du.getDateUUID();
		id = ku.getKey(id);
		//打印
		/*
		 * ？？？？？？？？？？？？？？？？？？
		 * 此处可能修改
		 */
		boolean isprint=ps.printBill(id,userName, name, IDNO, town, safeType, helpType, helpScale, startPay, endPay, tableValue, sumCol2, sumCol3, sumCol4, sumCol5, sumCol6, sumCol7,sumPay, helpPay,1 );
		
		//插入数据库
		/*
		 * ？？？？？？？？？？？？？？？？？？
		 * 此处可能修改
		 */
		if(isprint){
			String billNum = String.valueOf(tableValue.length);
			cs.addcase(id,name,IDNO,town,safeType,helpType,helpScale,startPay,endPay,sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7,sumPay,helpPay,userName,billNum);
			bs.addbill(tableValue,id);
		}
		
		return isprint;
	}
	
//	
	public String getMoreUse(String helpType){
		/*
		 * 获取该救助类型能否多次使用
		 * 此处为测试数据连接逻辑层时删除
		 */
		return ss.findByHelpType(helpType).getMoreUse();
	}
	//当救助比例大于100时，救助比例数值为阶梯ID值，获取阶梯比例值
	public double[][]getLadderScale(String id) {
		// TODO Auto-generated method stub
		double [][] ladderScale = ss.findLadderScale(id);//{{0,2000,10},{2000,3000,20},{3000,6000,50},{6000,-1,80}};
		return ladderScale;
	}
}
