package org.zqrc.tmhs.control.dbo;

import javax.swing.JOptionPane;

public class DBOFirst {
	private DBO db = new DBO();

	public DBOFirst() {
		//街道字典
		int i = 0;
		String town_dec[]={"洛北街道","新城街道","吴台街道","南丰镇","白马镇","宁平镇","宜路镇","钱店镇","汲冢镇","城郊乡","虎岗乡","汲水乡","张完乡","丁村乡","双楼乡","秋渠乡","东风乡","石槽镇","巴集乡","李楼乡","胡集乡"};

		//参保类型字典
		int j = 0;
		String safeType_dec[]={"农合","医保","未参保(合)"};

		//设置默认账号
		try{
			db.update("insert into user (id,name,pass,role) values ('admin','超级管理员','1234','0')");
		}catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "初始化用户失败！");
		}

		while(i<town_dec.length){
			//设置城镇
			try{
				db.update("insert into town (town) values ('"+town_dec[i]+"')");
			}catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "初始化城镇失败！");
			}
			i++;
		}

		while(j<safeType_dec.length){
			//设置参保类型
			try{
				db.update("insert into safeType (safeType) values('"+safeType_dec[j]+"')");
			}catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "初始化参保类型失败！");
			}
			j++;
		}

		//设置缓存路径
		try{
			db.update("insert into path (id,path) values ('1','D:\\')");
			db.update("insert into path (id,path) values ('2','C:\\')");
		}catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "初始化保存路径失败！");
		}

		//设置救助类型
		try{
			db.update("insert into sets (helpType,helpScale,startPay,endPay,moreUse) values ('五保','10','2000','3000','是')");
			db.update("insert into sets (helpType,helpScale,startPay,endPay,moreUse) values ('低保','20','2000','4000','否')");
			db.update("insert into sets (helpType,helpScale,startPay,endPay,moreUse) values ('因病致贫','10','1000','2000','是')");
		}catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "初始化救助类型失败！");
		}
	}

}
