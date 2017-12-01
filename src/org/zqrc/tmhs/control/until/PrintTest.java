package org.zqrc.tmhs.control.until;
import java.awt.BasicStroke;  
import java.awt.Color;  
import java.awt.Component;  
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Image;  

import java.awt.Toolkit;  
import java.awt.print.Book;  
import java.awt.print.PageFormat;  
import java.awt.print.Paper;  
import java.awt.print.Printable;  
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;  
import java.awt.print.PrinterJob;  

import javax.swing.JOptionPane;



public class PrintTest implements Printable{
	private String userName;
	
	private String id;
	private String name;//姓名-->内容
	private String iDNO;//身份证号-->内容
	private String town;
	private String safeType;
	private String helpType;
	private double helpScale;
	private double startPay;
	private double endPay;
	private String[][] value;//票据数据.length-->票号
	private double sumCol2;//费用合计-->合理费用
	private double sumCol3;//医保记账-->补偿费用
	private double sumCol4;//自费费用-->自付费用
	private double sumCol5;//大病保险
	private double sumCol6;//补充保险
	private double sumCol7;//其它抵扣
	private double sumPay;//合理支出
	private double helpPay;//救助金额-->医疗救助费用
	/** 
	 * @param Graphic指明打印的图形环境 
	 * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 
	 * @param pageIndex指明页号 
	 **/
	public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {
		//		Component c = null;
		//		转换成Graphics2D
		Graphics2D g2 = (Graphics2D) gra;
		//		设置打印颜色为黑色
		g2.setColor(Color.black);
		//打印起点坐标  
		//		double x = pf.getImageableX();
		//		double y = pf.getImageableY();

		for(int i=0;i<=pageIndex;i++){
			switch(i){
			case 0:
				float[]   dash1   =   {2.0f};
				//设置打印线的属性。
				//1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量  
				g2.setStroke(new BasicStroke(0.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,2.0f,dash1,0.0f));    

				//				普通数据字号
				Font font = new Font("仿宋GB_2312", Font.PLAIN, 12);
				g2.setFont(font);//设置字体 
				//				float heigth = font.getSize2D();//字体高度  

				//				打印资源区*********************************************
				//				凭证名称
				g2.drawString("城乡医疗救助", 119,108);

				//				票据数据
				g2.drawString(name, 75,190);//内容名字

				g2.drawString(""+sumCol2, 170,190);//合理费用
				g2.drawString(""+sumCol3, 260,190);//补偿费用
				g2.drawString(""+sumCol4, 340,190);//自付费用
				g2.drawString(""+helpPay, 420,190);//医疗救助费用
				g2.drawString(""+value.length, 520,190);//票号

				g2.drawString(RMBTransForm.toUP(String.valueOf(helpPay)), 170,250);//大写金额
				
				String strs="￥      "+helpPay;
				g2.drawString(strs, 445,250);//小写金额

				g2.drawString(DateUtil.getYear(), 437,108);//日期年
				g2.drawString(DateUtil.getMonth(), 493,108);//日期月
				g2.drawString(DateUtil.getDay(), 535,108);//日期日

				String str2="郸     :  "+id;
				g2.drawString(str2, 20,60);//单号

//				//				身份证字号
//				font = new Font("新宋体", Font.PLAIN, 9);
//				g2.setFont(font);//设置字体 

//				g2.drawString(iDNO, 60,205);//内容

				return PAGE_EXISTS;
			default:return NO_SUCH_PAGE;
			}
		}
		return PAGE_EXISTS;
	}

	//	打印数据对象
	public boolean printBean(){

		//通俗理解就是书、文档  
		Book book = new Book();  
		//设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT);

		//通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
		Paper p = new Paper();

		p.setSize(800,325);//纸张大小
		p.setImageableArea(10,10,800,325);//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72  
		pf.setPaper(p);

		//把 PageFormat 和 Printable 添加到书中，组成一个页面  
		book.append(this, pf);  

		//获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();        
		//设置打印类  
		job.setPageable(book);  
		try {
			if(job.printDialog()){//可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印  
				job.print();//取消保存时异常	PrinterAbortException
				return true;
			}else{
				JOptionPane.showMessageDialog(null, "打印已取消！");
				return false;
			}
		}catch (Exception e){
			return false;
		}
	}
	/**
	 * 默认构造
	 * @param userName
	 * @param name
	 * @param iDNO
	 * @param town
	 * @param safeType
	 * @param helpType
	 * @param helpScale
	 * @param startPay
	 * @param endPay
	 * @param value
	 * @param sumCol2
	 * @param sumCol3
	 * @param sumCol4
	 * @param sumCol5
	 * @param sumPay
	 * @param helpPay
	 */
	public PrintTest(String id,String userName, String name, String iDNO, String town,
			String safeType, String helpType, double helpScale,
			double startPay, double endPay, String[][] value, double sumCol2,
			double sumCol3, double sumCol4, double sumCol5, double sumCol6, double sumCol7, double sumPay,
			double helpPay) {
		super();
		this.id=id;
		this.userName = userName;
		this.name = name;
		this.iDNO = iDNO;
		this.town = town;
		this.safeType = safeType;
		this.helpType = helpType;
		this.helpScale = helpScale;
		this.startPay = startPay;
		this.endPay = endPay;
		this.value = value;
		this.sumCol2 = sumCol2;
		this.sumCol3 = sumCol3;
		this.sumCol4 = sumCol4;
		this.sumCol5 = sumCol5;
		this.sumCol6 = sumCol6;
		this.sumCol7 = sumCol7;
		this.sumPay = sumPay;
		this.helpPay = helpPay;
	}

}