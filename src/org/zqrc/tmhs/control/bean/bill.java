package org.zqrc.tmhs.control.bean;
/**
 * 票据 设置bean
 * @author JiaQi
 *
 */

public class bill {
	//票据单号
	private String id;
	//费用合计
	private String payTotal;
	//医保记账
	private String account;
	//自费费用
	private String selfPay;
	//大病保险
	private String bigSafe;
	//补充保险
	private String supSafe;
	//其他抵扣项
	private String otherPay;
	//病例单号
	private String caseid;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPayTotal() {
		return payTotal;
	}
	public void setPayTotal(String payTotal) {
		this.payTotal = payTotal;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSelfPay() {
		return selfPay;
	}
	public void setSelfPay(String selfPay) {
		this.selfPay = selfPay;
	}
	public String getBigSafe() {
		return bigSafe;
	}
	public void setBigSafe(String bigSafe) {
		this.bigSafe = bigSafe;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	
	public bill(){
	}
	
	public bill(String id, String payTotal, String account, String selfPay,
			String bigSafe,String supSafe,String otherPay,String caseid) {
		super();
		this.id = id;
		this.payTotal = payTotal;
		this.account = account;
		this.selfPay = selfPay;
		this.bigSafe = bigSafe;
		this.supSafe = supSafe;
		this.otherPay = otherPay;
	}
	
	public String toString() {
		return "bill [id=" + id + ", payTotal=" + payTotal + ", account="
				+ account + ", selfPay=" + selfPay + ", bigSafe=" + bigSafe
				+",supSafe="+supSafe+",otherPay="+otherPay
				+ ", caseid=" + caseid + "]";
	}
	public void setOtherPay(String otherPay) {
		// TODO Auto-generated method stub
		this.otherPay = otherPay;
	}
	public void setSupSafe(String supSafe) {
		// TODO Auto-generated method stub
		this.supSafe = supSafe;
	}
	public String getSupSafe() {
		// TODO Auto-generated method stub
		return supSafe;
	}
	public String getOtherPay() {
		// TODO Auto-generated method stub
		return otherPay;
	}
}
