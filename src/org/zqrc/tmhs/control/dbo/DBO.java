package org.zqrc.tmhs.control.dbo;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteOpenMode;
import org.zqrc.tmhs.control.until.jdbcConfig;

import com.sun.org.apache.bcel.internal.generic.Select;

public class DBO {
	private String URL;
	private String classname;
	
	private Connection con;
	private Statement stm;
	private ResultSet rs;
	
	private String dbdir;//数据路径
	private String dbfile;//数据文件名
	private File backfile;//备份文件路径
	
	/**
	 * 默认构造
	 */
	public DBO(){
		classname = "org.sqlite.JDBC";
		dbfile = "tmhs.db";
		dbdir = jdbcConfig.getConfig()[0] + File.separator + "TMHSystem"
				+ File.separator + "data" + File.separator + dbfile;
		URL = "jdbc:sqlite:" + dbdir;
		backfile = new File(jdbcConfig.getConfig()[1] + File.separator
				+ "TMHSystem" + File.separator + "backup");
		checkDir(dbdir);
	}
	
	/**
	 * 检测路径是否存在不存在，则创建
	 * @param file
	 */
	private void checkDir(String file){
		File f=new File(file);
		if  (!f.getParentFile().exists()  && !f.getParentFile().isDirectory()){
		  f.getParentFile().mkdirs();
		  if(!backfile.exists()){
		  	new File(backfile.getPath()+File.separator+"excels").mkdirs();
		  	new File(backfile.getPath()+File.separator+"docs").mkdirs();
		  }
		  init();
		  
		  new DBOFirst();
		}else{
			if(!backfile.exists()){
		  	new File(backfile.getPath()+File.separator+"excels").mkdirs();
		  	new File(backfile.getPath()+File.separator+"docs").mkdirs();
		  }
			init();
		}
		/*
		 * 检查是否为老版本数据库，若是则进行相应升级
		 */
		try {
			ResultSet rs = getRs("SELECT * FROM cases");
			ResultSetMetaData rsmd = rs.getMetaData();
			int m = rsmd.getColumnCount();
			closed();
			if (m == 18) {
				createDBTable("CREATE TABLE if not exists ladderScale"
						+ "(id integer not null,start integer not null,end integer not null,scale integer not null)");
				createDBTable("ALTER TABLE bill RENAME TO _bill_old;"
						+ "CREATE TABLE bill (id  text NOT NULL,payTotal text NOT NULL,account text NOT NULL,selfPay text NOT NULL,bigSafe text NOT NULL,supSafe text,otherPay  text,caseId text NOT NULL);"
						/*+ "INSERT INTO bill (id, payTotal, account, selfPay, bigSafe, caseId) SELECT id, payTotal, account, selfPay, bigSafe, caseId FROM _bill_old;"*/);
				createDBTable("ALTER TABLE cases RENAME TO _cases_old;"
						+ "CREATE TABLE cases id  text,name text NOT NULL,IDcard text NOT NULL,town text NOT NULL,safeType text NOT NULL,helpType text NOT NULL,helpScale text NOT NULL,startPay text NOT NULL,endPay text NOT NULL,payTotal text NOT NULL,account text NOT NULL,selfPay text NOT NULL,bigSafe text NOT NULL,supSafe text,otherPay text,reaPay text NOT NULL,helpPay text NOT NULL,billNum text NOT NULL,byName text NOT NULL,date text NOT NULL,PRIMARY KEY ( id  ASC) + );"
						/*+ "INSERT INTO cases (id, name, IDcard, town, safeType, helpType, helpScale, startPay, endPay, payTotal, account, selfPay, bigSafe, reaPay, helpPay, billNum, byName, date) SELECT id, name, IDcard, town, safeType, helpType, helpScale, startPay, endPay, payTotal, account, selfPay, bigSafe, reaPay, helpPay, billNum, byName, date FROM _cases_old;"*/);
			}else{
				ResultSet rst = getRs("SELECT COUNT(*) FROM sqlite_master where type='table' and name='_bill_old'");
				rst.next();
				if(rst.getInt(1)>0){
					closed();
					String billCol[]={"id","payTotal","account","selfPay","bigSafe","caseID"};
					writeOldTableToNewTable("_bill_old", "bill", billCol);
					String casesClo[]={"id", "name", "IDcard", "town", "safeType", "helpType", "helpScale", "startPay", "endPay", "payTotal", "account", "selfPay", "bigSafe", "reaPay", "helpPay", "billNum", "byName", "date"};
					writeOldTableToNewTable("_cases_old", "cases", casesClo);
//					createDBTable("DROP TABLE _bill_old");//删除历史表
//					createDBTable("DROP TABLE _cases_old");//删除历史表
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closed();
		}
	}
	
	/**
	 * 初始化数据表
	 * user表属性：id、name、pass、role、
	 * cases表属性：id、name、sex、age、towns、helptype、moneytype、date、byname
	 * bills表属性：id、case_id、money
	 * 现在为 V4.1 版本
	 */
	private void init(){
		/*createDBTable("CREATE TABLE if not exists cases" +
				"(id text primary key,name text not null,IDcard text not null,town text not null,safeType text not null,helpType text not null,helpScale text not null,startPay text not null,endPay text not null,payTotal text not null,account text not null,selfPay text not null,bigSafe text not null,reaPay text not null,helpPay text not null,billNum text not null,byName text not null,date text not null)");
		createDBTable("CREATE TABLE if not exists sets" +
				"(helpType text primary key,helpScale text not null,startPay text not null,endPay text not null,moreUse text not null)");
		createDBTable("CREATE TABLE if not exists user" +
				"(id text primary key,name text not null unique,pass text not null,role text not null)");
		createDBTable("CREATE TABLE if not exists town" +
				"(town text primary key)");
		createDBTable("CREATE TABLE if not exists safeType" +
				"(safeType text primary key)");
		createDBTable("CREATE TABLE if not exists path" +
				"(id text primary key,path text not null)");
		createDBTable("CREATE TABLE if not exists bill" +
				"(id text not null,payTotal text not null,account text text not null,selfPay text not null,bigSafe text not null,caseId text not null)");*/
		//V4.1
		createDBTable("CREATE TABLE if not exists cases"+
				"(id text primary key,name text not null,IDcard text not null,town text not null,safeType text not null,helpType text not null,helpScale text not null,startPay text not null,endPay text not null,payTotal text not null,account text not null,selfPay text not null,bigSafe text not null,supSafe text,otherPay text,reaPay text not null,helpPay text not null,billNum text not null,byName text not null,date text not null)");
		createDBTable("CREATE TABLE if not exists sets" +
				"(helpType text primary key,helpScale text not null,startPay text not null,endPay text not null,moreUse text not null)");
		createDBTable("CREATE TABLE if not exists user" +
				"(id text primary key,name text not null unique,pass text not null,role text not null)");
		createDBTable("CREATE TABLE if not exists town" +
				"(town text primary key)");
		createDBTable("CREATE TABLE if not exists safeType" +
				"(safeType text primary key)");
		createDBTable("CREATE TABLE if not exists path" +
				"(id text primary key,path text not null)");
		createDBTable("CREATE TABLE if not exists path" +
				"(id text primary key,path text not null)");
		createDBTable("CREATE TABLE if not exists bill" +
				"(id text not null,payTotal text not null,account text not null,selfPay text not null,bigSafe text not null,supSafe text,otherPay text,caseId text not null)");
		createDBTable("CREATE TABLE if not exists ladderScale"+
				"(id integer not null,start integer not null,end integer not null,scale integer not null)");
		
	}
	
	/**
	 * 创建数据表
	 * @param sql
	 * as: "CREATE TABLE if not exists test_01(id integer primary key, name char(10))"
	 */
	public void createDBTable(String sql){
		if(sql==null){
			sql="";
		}
		getStm();
		try {
			stm.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据表创建失败！");
		}finally{
			closed();
		}
	}
	
	/**
	 * start driver
	 */
	private void LoadDriver(){
		try{
			Class.forName(classname);
		}catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(null, "加载驱动程序失败！");
		}
	}
	
	/**
	 * get connection
	 * @return
	 */
	private Connection getCon(){
		LoadDriver();
		try{
			con=DriverManager.getConnection(URL);//username,password);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "创建数据库连接失败！");
		}
		return con;
	}
	
	/**
	 * creat connection statement
	 * @return
	 */
	private Statement getStm(){
		getCon();
		try{
			stm=con.createStatement();		
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "创建Statement对象失败！");
		}
		return stm;
	}
	
	/**
	 * 数据库查询，需要手动关闭
	 * @param sql
	 * @return
	 */
	public ResultSet getRs(String sql){
		if(sql==null){
			sql="";
		}
		getStm();
		try{
			rs=stm.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "DBO层查询数据库失败！");
		}
		return rs;		
	}
	
	/**
	 * 数据库更新，执行增删改操作，并返回受影响的行数。自动closed
	 * 返回1代表更新成功，-1代表失败
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	public int update(String sql) throws Exception{
		if(sql==null){
			sql="";
		}
		getStm();
		int i=-1;
		try{
			i=stm.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
//			JOptionPane.showConfirmDialog(null, "操作失败!");
			i=-1;
			throw new Exception("操作失败！");
		}finally{
			closed();
		}
		return i;
	}
	
	/**
	 * 关闭数据连接
	 */
	public void closed(){
		try{
			if(this.rs!=null){
				rs.close();
				rs = null;
			}
			if(this.stm!=null){
				stm.close();
				stm = null;
			}
			if(this.con!=null){
				con.close();
				con = null;
			}
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, "数据流关闭异常！");
		}
	}
	public void writeOldTableToNewTable(String oldTable,String newTable,String[] colName){
		try{
			ResultSet rs = getRs("SELECT * FROM "+oldTable);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			int i = 0;
			String val="";
			while(rs.next()){
				val+="(";
				for(int j = 1;j<=colCount;j++){
					val+="'"+rs.getString(j)+"'";
					if(j!=colCount){
						val+=",";
					};
				}
				val+="),";
				i++;
			}
			val = val.substring(0, val.length()-1);
			closed();
			String sql = "INSERT INTO "+newTable+" (";
			for(i = 0;i<colName.length;i++){
				sql+= colName[i];
				if(i!=colName.length-1){
					sql+=",";
				}
			}
			sql+=") VALUES"+val;
			System.out.println(sql);
			update(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closed();
		}
	}
	
}
