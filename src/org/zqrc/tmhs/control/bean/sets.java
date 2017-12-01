package org.zqrc.tmhs.control.bean;
/**
 * 设置参数bean
 */
public class sets {
	//救助类型
	private String helpType;
	//救助比例
	private String helpScale;
	//起付线
	private String startPay;
	//封顶线
	private String endPay;
	//多次使用
	private String moreUse;
	
	public sets(){		
	}
	
	public sets(String helpType,String helpScale,String startPay,String endPay,String moreUse){
		super();
		this.helpType=helpType;
		this.helpScale=helpScale;
		this.startPay=startPay;
		this.endPay=endPay;
		this.moreUse=moreUse;
	}

	public String getHelpType() {
		return helpType;
	}

	public void setHelpType(String helpType) {
		this.helpType = helpType;
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

	public String getMoreUse() {
		return moreUse;
	}

	public void setMoreUse(String moreUse) {
		this.moreUse = moreUse;
	}
	
	public String toString() {
		return "sets [helpType=" + helpType + ",startPay"+startPay+",startPay=" + startPay + 
				", endPay=" + endPay+ ", moreUse=" + moreUse + "]";
	}
}
