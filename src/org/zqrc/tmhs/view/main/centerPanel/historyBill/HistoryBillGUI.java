package org.zqrc.tmhs.view.main.centerPanel.historyBill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * By Gorden @2016-10-26
 */

public class HistoryBillGUI extends JPanel implements ActionListener,MouseListener{
	private String [] colName={"单号","姓名","身份证号","乡镇","参保类型","救助类型","救助比例","起付线","封顶线","费用合计","医保记账","自付费用","大病保险","困难群众大病补充保险","其它报销项","合理支出","救助金额","票据数","添加人"};
	private JScrollPane sp;
	private JTable table;
	private DefaultTableModel model=new DefaultTableModel();
	private JComboBox<String> searchType_cb,year_cb,month_cb;
	private JTextField searchData_tf;
	private JButton search_btn,outPutTableData_btn,out_delHistoryData_btn;
	private JLabel sumBill_l,sumPeople_l,sumMoney_l;
	private String userName,role;
	private HistoryBill set=new HistoryBill();
	private JPanel firstPanel;
	private String data[][];
	
	public HistoryBillGUI(String userName,String role){
		super();
		this.userName=userName;
		this.role=role;
		setFirstPanel();
		this.setLayout(new BorderLayout());
		this.add(firstPanel,BorderLayout.CENTER);
	}
	private void setFirstPanel(){
		firstPanel=new JPanel();
		firstPanel.setBackground(new Color(255,250,250));
		firstPanel.setLayout(new BorderLayout());
		firstPanel.add(topPanel(),BorderLayout.NORTH);
		firstPanel.add(tablePanel(),BorderLayout.CENTER);
		firstPanel.add(footPanel(),BorderLayout.SOUTH);
		firstPanel.setVisible(true);
	}
	private Component footPanel() {
		// TODO Auto-generated method stub
		JPanel footPanel=new JPanel();
		footPanel.setBackground(null);
		footPanel.setLayout(new FlowLayout());
		footPanel.add(getTipJLable("合计救助："));
		sumBill_l=new JLabel(set.getSumBill());
		sumBill_l.setFont(new Font("微软雅黑",1,16));
		sumBill_l.setForeground(Color.blue);
		footPanel.add(sumBill_l);
		footPanel.add(getTipJLable("救助人数："));
		sumPeople_l=new JLabel(set.getSumPeople());
		sumPeople_l.setFont(new Font("微软雅黑",1,16));
		sumPeople_l.setForeground(Color.blue);
		footPanel.add(sumPeople_l);
		footPanel.add(getTipJLable("发放救助金："));
		sumMoney_l=new JLabel(set.getSumMoney());
		sumMoney_l.setFont(new Font("微软雅黑",1,16));
		sumMoney_l.setForeground(Color.blue);
		footPanel.add(sumMoney_l);
		if(role.equals("0")){
			out_delHistoryData_btn=getJButton(out_delHistoryData_btn, "导出并清除往年数据");
			footPanel.add(out_delHistoryData_btn);
		}
		return footPanel;
	}
	private Component tablePanel() {
		data=null;
		model=new DefaultTableModel(data,colName){
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		setTableData(null,null,null,null);
		table=new JTable(model);
		table.setDefaultRenderer(Object.class,new TableStyle());
		table.setRowHeight(24);
		table.getTableHeader().setFont(new Font("微软雅黑",0,14));
		table.setSelectionBackground(new Color(255,250,250));
		table.getTableHeader().setBackground(new Color(255,231,255));
		table.setFont(new Font("微软雅黑",0,14));
		sp=new JScrollPane(table);
		table.addMouseListener(this);
		return sp;
	}
	private void setTableData(String searchType,String searchData,String year,String month) {
		// TODO Auto-generated method stub
		data=set.getBillData(searchType,searchData,year,month);
		model.setDataVector(data, colName);
	}
	private void setJLabelData(){
		sumBill_l.setText(set.getSumBill());
		sumMoney_l.setText(set.getSumMoney());
		sumPeople_l.setText(set.getSumPeople());
	}
	private Component topPanel() {
		// TODO Auto-generated method stub
		JPanel topPanel=new JPanel();
		topPanel.setBackground(null);
		topPanel.setLayout(new FlowLayout());
		topPanel.setPreferredSize(new Dimension(700,90));
		
		JPanel p1=new JPanel();
		p1.setBackground(null);
		p1.setLayout(new FlowLayout());
		String searchType[]={"单号","身份证号","姓名"};
		DefaultComboBoxModel<String> model1=new DefaultComboBoxModel<String>(searchType);
		searchType_cb=getJComboBox(searchType_cb, model1);
		searchType_cb.addActionListener(this);
		p1.add(searchType_cb);
		searchData_tf=getJTextField(searchData_tf);
		p1.add(searchData_tf);
		search_btn=getJButton(search_btn, "搜索");
		p1.add(search_btn);
		
		JPanel p2=new JPanel();
		p2.setBackground(null);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy");
		String y=sf.format(new Date());
		int year=Integer.parseInt(y);
		String years[] = new String[(year-2015+2)];
		
		for(int i=0;i<=year-2015;i++){
			years[i+1]=""+(year-i);
		}
		DefaultComboBoxModel<String> model2=new DefaultComboBoxModel<String>(years);
		year_cb=getJComboBox(year_cb, model2);
		p2.add(year_cb);
		year_cb.addActionListener(this);
		p2.add(getTipJLable("年"));
		
		String monthes[]={"","1","2","3","4","5","6","7","8","9","10","11","12"};
		DefaultComboBoxModel<String> model3=new DefaultComboBoxModel<String>(monthes);
		month_cb=getJComboBox(month_cb, model3);
		p2.add(month_cb);
		month_cb.addActionListener(this);
		p2.add(getTipJLable("月"));
		outPutTableData_btn=getJButton(outPutTableData_btn, "导出表中数据");
		p2.add(outPutTableData_btn);
		
		topPanel.add(p1);
		topPanel.add(p2);
		
		return topPanel;
	}
	/*
	 * 生成标签
	 */
	private JLabel getTipJLable(String text){
		JLabel l=new JLabel(text);
		l.setFont(new Font("微软雅黑",0,16));
		return l;
	}
	/*
	 * 设置按钮风格
	 */
	private JButton getJButton(JButton btn,String text){
		btn=new JButton(text);
		btn.setBackground(new Color(255,153,204));
		btn.setForeground(Color.white);
		btn.setFont(new Font("微软雅黑",1,16));
		btn.addActionListener(this);
		return btn;
	}
	/*
	 * 设置文本框风格
	 */
	private JTextField getJTextField(JTextField tf){
		tf=new JTextField(10);
		tf.setBackground(Color.white);
		tf.setFont(new Font("微软雅黑",0,16));
		return tf;
	}
	/*
	 * 设置下拉选项风格
	 */
	private JComboBox<String> getJComboBox(JComboBox<String> cb,DefaultComboBoxModel<String> model){
		cb=new JComboBox<String>(model);
		cb.setFont(new Font("微软雅黑",0,14));
		cb.setBackground(Color.white);
		return cb;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==search_btn){
			setTableData((String)searchType_cb.getSelectedItem(),searchData_tf.getText(),(String)year_cb.getSelectedItem(),(String)month_cb.getSelectedItem());
			setJLabelData();
		}
		if(e.getSource()==outPutTableData_btn){
			set.outData(data);
		}
		if(e.getSource()==out_delHistoryData_btn){
			if(set.out_delHistoryData()){
				JOptionPane.showMessageDialog(this, "数据导出成功，已清空往年数据！");
			}
		}
		if(e.getSource()==year_cb){
			setTableData((String)searchType_cb.getSelectedItem(),searchData_tf.getText(),(String)year_cb.getSelectedItem(),(String)month_cb.getSelectedItem());
			setJLabelData();
		}
		if(e.getSource()==month_cb){
			setTableData((String)searchType_cb.getSelectedItem(),searchData_tf.getText(),(String)year_cb.getSelectedItem(),(String)month_cb.getSelectedItem());
			setJLabelData();
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){
			try{
				String billNO=(String)table.getValueAt(table.getSelectedRow(), 0);
				firstPanel.setVisible(false);
				this.add(new AnHistoryBillGUI(userName,role, billNO));
			}catch(Exception ex){
				
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
class TableStyle extends DefaultTableCellRenderer{
	public TableStyle(){
		setHorizontalAlignment(CENTER);
	}
}
