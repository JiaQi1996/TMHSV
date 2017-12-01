package org.zqrc.tmhs.view.main.centerPanel.setSystemData;

import org.zqrc.tmhs.control.service.helpTypeService;
import org.zqrc.tmhs.control.service.pathService;
import org.zqrc.tmhs.control.service.safeTypeService;
import org.zqrc.tmhs.control.service.setsService;
import org.zqrc.tmhs.control.service.townService;
import org.zqrc.tmhs.control.service.userService;
import org.zqrc.tmhs.view.main.centerPanel.inputBill.InputBill;

/*
 * 此类用于完成系统设置页面与逻辑层进行数据交互
 */

public class SetSystemData {
	private townService ts = new townService();
	private pathService ps = new pathService();
	private helpTypeService hs = new helpTypeService();
	private setsService ss = new setsService();
	private safeTypeService sts = new  safeTypeService();
	private String path1;
	private String path2;
	private InputBill in=new InputBill();
	
	/**
	 * 获取城镇列表
	 * @return
	 */
	public String[] getTown_dec(){
		String[] Town_dec=in.getTown_dec();
		return Town_dec;
	}
	
	/**
	 * 获取
	 * @return
	 */
	public String[] getSafeType_dec(){
		String[] safeType_dec=in.getSafeType_dec();
		return safeType_dec;
	}
	
	public String[] getHelpType_dec(){
		String[] helpType_dec=in.getHelpType_dec();
		return helpType_dec;
	}
	
	public double getStartPay(String helpType){
		double startPay=in.getStartPay(helpType);
		return startPay;
	}
	
	public double getEndPay(String helpType){
		double endPay=in.getEndPay(helpType, "100000");
		return endPay;
	}
	
	public String[] getMoreUse(String helpType){
		String more=in.getMoreUse(helpType);
		String moreUse[]={"是","否"};
		if(more.equals("否")){
			moreUse[0]="否";
			moreUse[1]="是";
		}
		return moreUse;
	}
	
	public double getHelpScale(String helpType){
		double helpScale=in.getHelpScale(helpType);
		return helpScale;
	}
	/*
	 * 以上方法均与InputBill类相连接
	 * 以下方法为设置数据的方法
	 */
	public String getPath1(){
		/*
		 * 用以获得路径1
		 */
		path1 = ps.getpath1();
		
		return path1;
	}
	
	public String getPath2(){
		/*
		 * 用以获得路径1
		 */
		path2 = ps.getpath2();
		return path2;
	}
	
	public void setPath1(String path){
		/*
		 * 设置路径1
		 */
		if(ps.setpath1(path)){
			/*
			 * 设置成功
			 */
		}else{
			/*
			 * 设置失败
			 */
		}
	}
	public void setPath2(String path){
		/*
		 * 设置路径2
		 */
		if(ps.setpath2(path)){
			/*
			 * 设置成功
			 */
		}else{
			/*
			 * 设置失败
			 */
		}
	}
	public void del_town(String town){
		/*
		 * 删除城镇
		 */
		if(ts.del(town)){
			/*
			 * 删除成功
			 */
		}else{
			/*
			 * 删除失败
			 */
		}
		
	}
	
	public boolean add_town(String town){
		/*
		 * 添加城镇
		 */
		return ts.add(town);
	}
	
	public void del_safeType(String safeType){
		/*
		 * 删除参保类型
		 */
		if(sts.del(safeType)){
			/*
			 * 删除成功
			 */
		}else{
			/*
			 * 删除失败
			 */
		}
	}
	
	public boolean add_safeType(String safeType){
		/*
		 * 添加参保类型
		 */
		return sts.add(safeType);
	}
	
	public void del_helpType(String helpType){
		/*
		 * 删除救助类型
		 */
		hs.del(helpType);
	}
	
	public boolean add_helpType(String helpType){
		/*
		 * 添加救助类型
		 */
		return hs.add(helpType);
	}
	
	public void updateHelpData(String helpType,String helpScale,String startPay,String endPay,String moreUse){
		/*
		 * 更新救助表数据
		 */
		ss.update(helpType, helpScale, startPay, endPay, moreUse);
	}
	public double getLastLadderScanleID(){
		return ss.findLastLadderScaleID();
	}
	public void insertLadderScale(double id,double[][] data){
		ss.insertLadderScale(id,data);
	}
}