package org.zqrc.tmhs.control.until;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 生成Excel工具
 * @project TMHS
 * @DescList org.zqrc.tmhs.view
 * @author 李志飞
 *
 * @Date 2016-10-2
 * @UpDate 2016
 */
public class ExcelFactory{
	/**
	 * @param name	表信息名
	 * @param headtitle	大标题
	 * @param title	单元项----》单元项.length==数据[][].length;
	 * @param data	数据
	 */
	public static boolean createAllXLS(String path,String name,String headtitle,String[]title,String[][]data) {
		int colsLength=0;
		try{
			
			if(title.length==data[0].length){
				colsLength=title.length;
			}else{
				JOptionPane.showMessageDialog(null, "导出参数错误！");
				return false;
			}
		}catch (NullPointerException e) {
			JOptionPane.showConfirmDialog(null, "数据为空！");
			return false;
		}

		try {
			WritableWorkbook book = Workbook.createWorkbook(new File(path+File.separator+name+".xls"));
			WritableSheet sheet=book.createSheet(name,0);//生成名为“第一页”的工作表，参数0表示这是第一页 //WritableSheet sheet1=book.createSheet("个人救助信息1",1);

			//			通用标签
			jxl.write.Label l = null;
			jxl.write.Number n = null;
			jxl.write.DateTime d = null;

			//			格式
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);//字体
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);//字体格式
			headerFormat.setAlignment(Alignment.CENTRE);//上下居中
			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);//左右居中
			headerFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); //BorderLineStyle边框

			//表头样式
			WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
			WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
			titleFormat.setAlignment(Alignment.CENTRE);
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			titleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); //BorderLineStyle边框

			//内容样式
			WritableFont detFont = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
			WritableCellFormat detFormat = new WritableCellFormat(detFont);
			detFormat.setAlignment(Alignment.CENTRE);
			detFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			detFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK);

			//			// 设置各行的高度
			//			sheet.setRowView(0, 400, false);
			//			sheet.setRowView(1, 500, false);
			//			sheet.setRowView(2, 450, false);
			//			sheet.setRowView(3, 480, false);
			//			sheet.setRowView(4, 600, false);
			//			sheet.setRowView(5, 1000, false);
			//			sheet.setRowView(6, 900, false);
			//			sheet.setRowView(7, 550, false);
			//			
			//			// 设置各列列宽
			//			int col=14;
			//			sheet.setColumnView(0, col);
			//			sheet.setColumnView(1, col);
			//			sheet.setColumnView(2, col);
			//			sheet.setColumnView(3, col);
			//			sheet.setColumnView(4, 16);
			//			sheet.setColumnView(5, col);

			// 大标题
			l = new Label(0, 0, headtitle, headerFormat);// 定位(起始横排，起始竖排，目标数据，数据格式)
			sheet.addCell(l);//添加到表中
			sheet.mergeCells(0, 0, colsLength-1, 0);//合并表格    合并(起始横排，起始竖排，终止横排，终止竖排)

			//			标题数据
			for(int i=0;i<title.length;i++){
				l = new Label(i, 1, title[i], titleFormat);
				sheet.addCell(l);//WritableFont bold = new WritableFont(WritableFont.createFont("宋体"),16, WritableFont.BOLD);  
			}

			//			内容数据
			for(int i=0;i<data.length;i++){
				for(int j=0;j<data[i].length;j++){
					l = new Label(j, i+2, data[i][j], detFormat);
					sheet.addCell(l);
				}
			}

			book.write();
			book.close();
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "导出表错误！");
			return false;
		} catch (WriteException e) {
			JOptionPane.showConfirmDialog(null, "导出格式错误！");
			return false;
		}
		return true;
	}

}
