package org.zqrc.tmhs.view.main.centerPanel.setSystemData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.helpers.CyclicBuffer;
import org.omg.CORBA.REBIND;
import org.zqrc.tmhs.view.login.LoginGUI;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

/*
 *  by Gorden 2017-6-20
 *  此类为设置阶梯救助比例的页面
 *  此类V4.1新添补丁
 */

public class LadderScaleGUI extends JPanel implements ActionListener,MouseListener,KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private JButton submit,reset,cancle;
	private JTable dataTable;
	private String user,helpType,moreUse;
	private double helpScale,startPay,endPay;
	private JPanel lastPanel;
	private Object[] colname={"区间头(￥)","区间尾(￥)","区间救助比例(%)"};
	private DefaultTableModel model;
	private double[][] data;
	private JLabel checkData;
	private SetSystemDataGUI ssdg;
	public LadderScaleGUI(String user,String helpType,double helpScale,
			double startPay,double endPay,String moreUse,double data[][],JPanel lastPanel,SetSystemDataGUI ssdg) {
		// TODO Auto-generated constructor stub
		super();
		this.user = user;
		this.helpScale = helpScale;
		this.lastPanel=lastPanel;
		this.helpType = helpType;
		this.startPay = startPay;
		this.endPay = endPay;
		this.moreUse = moreUse;
		this.data = data;
		this.ssdg = ssdg;
		this.addMouseListener(this);
		this.addKeyListener(this);
		setThisPanel();
	}
	private void setThisPanel() {
		// TODO Auto-generated method stub
		this.setBackground(new Color(255,250,250));
		this.setLayout(new BorderLayout());
		this.add(setThisMainPanel(),BorderLayout.CENTER);
		this.setVisible(true);
		checkData();
	}
	private JPanel setThisMainPanel() {
		// TODO Auto-generated method stub
		JPanel mainPanel=new JPanel();
		mainPanel.setBackground(null);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add("North",setHeadPanel());
		mainPanel.add("Center",setCenterPanel());
		mainPanel.add("South",setBootPanel());
		return mainPanel;
	}
	private JPanel setBootPanel() {
		// TODO Auto-generated method stub
		JPanel bootJPanel = new JPanel();
		bootJPanel.setBackground(null);
		bootJPanel.setLayout(new FlowLayout());
		
		submit=new JButton("确认修改并返回上层");
		setJButtonStyle(submit);
		bootJPanel.add(submit);
		
		cancle=new JButton("放弃修改并返回上层");
		setJButtonStyle(cancle);
		bootJPanel.add(cancle);
		
		reset=new JButton("初始化数据");
		setJButtonStyle(reset);
		bootJPanel.add(reset);
		
		JLabel l1 = new JLabel("数据校验:");
		l1.setFont(new Font("微软雅黑",1,16));
		
		checkData = new JLabel("校验通过");
		checkData.setFont(new Font("微软雅黑",1,16));
		checkData.setForeground(new Color(0,150,0));
		
		bootJPanel.add(l1);
		bootJPanel.add(checkData);
		
		return bootJPanel;
	}
	private JScrollPane setCenterPanel() {
		// TODO Auto-generated method stub
		JPanel centerJPanel = new JPanel();
		centerJPanel.setBackground(null);
		centerJPanel.setLayout(new BorderLayout());
		String s[][]=null;
		if(helpScale<100){
			s = new String[1][3];
			s[0][0] = "0";
		}else{
			try{
				s = arraryDoubleToString(data);
			}catch(Exception e){
				s = new String[1][3];
				s[0][0] = "0";
			}
		}
		model = new DefaultTableModel(s,colname){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int cow){//重写此方法，实现第一列不能被编辑
				if(cow==0){
					return false;
				}
				return true;
			}
		};
		dataTable = new JTable(model);
		dataTable.putClientProperty("terminateEditOnFocusLost", true);
		dataTable.setDefaultRenderer(Object.class,new TableStyle());
		dataTable.getTableHeader().setFont(new Font("微软雅黑",0,14));
		dataTable.setSelectionBackground(new Color(255,250,250));
		dataTable.getTableHeader().setBackground(new Color(255,231,255));
		dataTable.setFont(new Font("微软雅黑",0,14));
		dataTable.setRowHeight(25);
		dataTable.setBackground(Color.white);
		dataTable.addMouseListener(this);
		dataTable.setBackground(Color.WHITE);
		//dataTable.setEnabled(false);
		dataTable.addKeyListener(this);
		dataTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane sp=new JScrollPane(dataTable);
		sp.setBorder(null);
		sp.addMouseListener(this);
		sp.addKeyListener(this);
		
		return sp;
	}
	private JPanel setHeadPanel() {
		// TODO Auto-generated method stub
		JPanel headJPanel = new JPanel();
		headJPanel.setBackground(null);
		headJPanel.setLayout(new FlowLayout());
		
		JLabel title = new JLabel("救助类型："+helpType+"    起付线：￥"+startPay+"    封顶线：￥"+endPay+"    可否多次使用："+moreUse);
		title.setFont(new Font("微软雅黑", 1, 16));
		headJPanel.add(title);
		
		return headJPanel;
	}
	private void setJButtonStyle(JButton btn){
		btn.setBackground(new Color(255,153,204));
		btn.setForeground(Color.white);
		btn.setFont(new Font("微软雅黑",1,16));
		btn.addActionListener(this);
	}
	/*
	 * 此方法用以将double数组转换为string数组
	 */
	private String[][] arraryDoubleToString(double[][] data){
		String[][] s = new String[data.length][3];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<3;j++){
				if(0 != data[i][j]){
					s[i][j]=String.valueOf(data[i][j]);
				}
				if(-1==data[i][j]){
					s[i][j]="";
				}
			}
		}
		s[0][0]="0";
		return s;
	}
	/*
	 * 此方法用以将String数组转换为double数组
	 */
	private double[][] arraryStringToDouble(String[][] data){
		if(null == data) return null;
		double[][] d = new double[data.length][3];
		try{
			for(int i=0;i<data.length;i++){
				for(int j=0;j<3;j++){
					d[i][j] = Double.parseDouble(data[i][j]);
				}
			}
		}catch(Exception e){
			return null;
		}
		return d;
	}
	/*
	 * 此方法用以获得表格中相应的值
	 */
	private String[][] getTableValue(){
		String data[][] = new String[dataTable.getRowCount()][3];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<3;j++){
				try{
					if(i==(dataTable.getRowCount()-1)&&j==1){
						data[i][j] = "-1";
					}else{
						data[i][j] = (String)dataTable.getValueAt(i, j);
						if(data[i][j].equals("null")){
							return null;
						}
					}
				}catch(Exception e){
					return null;
				}
			}
		}
		return data;
	}
	/*
	 * 此方法用来检测输入数据是否合法并将结果反馈给用户
	 */
	private void checkData(){
		checkData.setText("校验通过");
		checkData.setForeground(new Color(0,150,0));
		double[][] data = arraryStringToDouble(getTableValue());
		if(data == null){
			checkData.setText("数据非法");
			checkData.setForeground(Color.RED);
			return;
		}else for(int i = 0;i<dataTable.getRowCount();i++){
			if((data[i][0]>=data[i][1]&&data[i][1]!=-1)||data[i][2]<0||data[i][2]>100){
				checkData.setText("数据非法");
				checkData.setForeground(Color.RED);
				return;	
			}	
		}
	} 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		checkData();
		if(e.getSource()==cancle){
			if(JOptionPane.showConfirmDialog(this, "您确定要放弃当前修改并返回上层？")==JOptionPane.YES_OPTION){
				this.setVisible(false);
				this.removeAll();
				try {
					lastPanel.setVisible(true);
					this.finalize();
					System.gc();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource()==reset){
			if(JOptionPane.showConfirmDialog(this, "您确定要放弃当前修改并初始化数据？")==JOptionPane.YES_OPTION){
				String s[][] = {{"0","",""}};
				if(helpScale>100){
					s = arraryDoubleToString(data);
				}
				model.setDataVector(s,colname);
			}
		}
		if(e.getSource()==submit){
			double[][] newdata = arraryStringToDouble(getTableValue());
			int message=0;
			if(newdata.length == data.length){
				for(int i=0;i<data.length;i++){
					if(Arrays.equals(data[i], newdata[i])){
						message=1;
					}else{
						message=0;
						break;
					}
				}
			}
			if(checkData.getText().equals("校验通过")&&getTableValue().length!=1&&message==0){
				if(JOptionPane.showConfirmDialog(this, "您确定要提交当前数据？")==JOptionPane.YES_OPTION){
					this.setVisible(false);
					this.removeAll();
					ssdg.LadderScale = arraryStringToDouble(getTableValue());
					SetSystemData ssd = new SetSystemData();
					if(ssd.getLastLadderScanleID() == 0){
						ssdg.helpScale = 101;
						ssdg.helpScale_tf.setText(ssdg.helpScale+"");
					}else{
						ssdg.helpScale = ssd.getLastLadderScanleID()+1;
						ssdg.helpScale_tf.setText(ssdg.helpScale+"");
					}
					lastPanel.setVisible(true);
					ssd.insertLadderScale(ssd.getLastLadderScanleID()+1, arraryStringToDouble(getTableValue()));
					ssd.updateHelpData(helpType, Integer.toString((int)ssdg.helpScale),  Double.toString(startPay),  Double.toString(endPay), moreUse);
					try {	
						this.finalize();
						System.gc();
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}else{
				JOptionPane.showMessageDialog(this, "数据非法，无法提交！");
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		checkData();
		try {
			try {
				if (Double.parseDouble((String) dataTable.getValueAt(
						dataTable.getRowCount() - 1, 1)) > 0
						&& !checkData.getText().equals("数据非法")) {
					String newRow[] = {
							(String) dataTable.getValueAt(
									dataTable.getRowCount() - 1, 1), "", "" };
					model.addRow(newRow);
				}
			} catch (Exception exc) {
				int row = dataTable.getRowCount();
				for (int i = 1; i < row; i++) {
					dataTable.setValueAt(dataTable.getValueAt(i - 1, 1), i, 0);
					if (dataTable.getValueAt(i, 0).toString().equals("")) {
						for (int j = i; j < row; j++) {
							model.removeRow(i);
						}
					}
				}
			}
			int row = dataTable.getRowCount();
			for (int i = 1; i < row; i++) {
				dataTable.setValueAt(dataTable.getValueAt(i - 1, 1), i, 0);
			}
		} catch (Exception exc) {

		}
		checkData();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		checkData();
		try {
			try {
				if (Double.parseDouble((String) dataTable.getValueAt(
						dataTable.getRowCount() - 1, 1)) > 0
						&& !checkData.getText().equals("数据非法")) {
					String newRow[] = {
							(String) dataTable.getValueAt(
									dataTable.getRowCount() - 1, 1), "", "" };
					model.addRow(newRow);
				}
			} catch (Exception exc) {
				int row = dataTable.getRowCount();
				for (int i = 1; i < row; i++) {
					dataTable.setValueAt(dataTable.getValueAt(i - 1, 1), i, 0);
					if (dataTable.getValueAt(i, 0).toString().equals("")) {
						for (int j = i; j < row; j++) {
							model.removeRow(i);
						}
					}
				}
			}
			int row = dataTable.getRowCount();
			for (int i = 1; i < row; i++) {
				dataTable.setValueAt(dataTable.getValueAt(i - 1, 1), i, 0);
			}
		} catch (Exception exc) {

		}
		checkData();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
class TableStyle extends DefaultTableCellRenderer{
	public TableStyle(){
		setHorizontalAlignment(CENTER);
	}
}
