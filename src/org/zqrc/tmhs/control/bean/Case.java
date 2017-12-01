package org.zqrc.tmhs.control.bean;

public class Case {
	private String id;
	private String name;
	private String IDcard;
	private String safeType;
	private String helpType;
	private String town;
	private String helpScale;
	private String startPay;
	private String endPay;
	//添加人
	private String byName;
	//添加日期
	private String date;
	//救助金额
	private String helpPay;
	//费用合计
	private String payTotal;
	//自费费用
	private String selfPay;
	//票据数
	private String billNum;
	//合理支出
	private String reaPay;
	//大病保险
	private String bigSafe;
	//医保记账
	private String account;
	//困难群众补充医疗险
	private String supSafe;
	//其它抵扣项
	private String otherPay;
	
	public String getSupSafe(){
		return supSafe;
	}
	public void setSupSafe(String supSafe){
		this.supSafe=supSafe;
	}
	public String getOtherPay(){
		return otherPay;
	}
	public void setOtherPay(String otherPay){
		this.otherPay=otherPay;
	}
	
	public String getHelpScale() {
		return helpScale;
	}
	public void setHelpScale(String helpScale) {
		this.helpScale = helpScale;
	}
	public String getStartPay() {
		return startPay;
	}
	public void setStartPay(String startPay) {
		this.startPay = startPay;
	}
	public String getEndPay() {
		return endPay;
	}
	public void setEndPay(String endPay) {
		this.endPay = endPay;
	}
	public String getPayTotal() {
		return payTotal;
	}
	public void setPayTotal(String payTotal) {
		this.payTotal = payTotal;
	}
	public String getSelfPay() {
		return selfPay;
	}
	public void setSelfPay(String selfPay) {
		this.selfPay = selfPay;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public String getReaPay() {
		return reaPay;
	}
	public void setReaPay(String reaPay) {
		this.reaPay = reaPay;
	}
	public String getBigSafe() {
		return bigSafe;
	}
	public void setBigSafe(String bigSafe) {
		this.bigSafe = bigSafe;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}


	public void setHelpPay(String helpPay) {
		this.helpPay = helpPay;
	}
	public String getHelpPay() {
		return helpPay;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIDcard() {
		return IDcard;
	}

	public void setIDcard(String iDcard) {
		IDcard = iDcard;
	}

	public String getSafeType() {
		return safeType;
	}

	public void setSafeType(String safeType) {
		this.safeType = safeType;
	}

	public String getHelpType() {
		return helpType;
	}

	public void setHelpType(String helpType) {
		this.helpType = helpType;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getByName() {
		return byName;
	}

	public void setByName(String byName) {
		this.byName = byName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Case(){	
	}
	
	public Case(String id, String name, String IDcard,String town, String safeType,
			String helpType, String helpScale, String startPay,
			String endPay,String payTotal,String account, String selfPay,
			String bigSafe, String supSafe,String otherPay,String reaPay,String helpPay,String billNum,
			  String byName, String date) {
		super();
		this.id = id;
		this.name = name;
		this.IDcard = IDcard;
		this.safeType = safeType;
		this.helpType = helpType;
		this.town = town;
		this.helpScale = helpScale;
		this.startPay = startPay;
		this.endPay = endPay;
		this.byName = byName;
		this.date = date;
		this.helpPay = helpPay;
		this.payTotal = payTotal;
		this.selfPay = selfPay;
		this.billNum = billNum;
		this.reaPay = reaPay;
		this.bigSafe = bigSafe;
		this.supSafe = supSafe;
		this.otherPay = otherPay;
		this.account = account;
	}

	public String toString() {
		return "Case [id=" + id + ", name=" + name + ", IDcard=" + IDcard
				+ ", safeType=" + safeType + ", helpType=" + helpType
				+ ", town=" + town + ", helpScale=" + helpScale + ", startPay="
				+ startPay + ", endPay=" + endPay + ", byName=" + byName
				+ ", date=" + date + ", helpPay=" + helpPay + ", payTotal="
				+ payTotal + ", selfPay=" + selfPay + ", billNum=" + billNum
				+ ", reaPay=" + reaPay + ", bigSafe=" + bigSafe + ", account="
				+ account + "]";
	}


}
