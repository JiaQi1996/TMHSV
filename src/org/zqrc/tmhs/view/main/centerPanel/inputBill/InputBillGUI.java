package org.zqrc.tmhs.view.main.centerPanel.inputBill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.zqrc.tmhs.view.method.IDCard;

/*
 * By Gorden @2016-10-22
 * 此类主要生成帐单录入页面
 * V4.1修改
 */
public class InputBillGUI extends JPanel implements MouseListener,KeyListener,FocusListener,ActionListener{
	private JTextField name_tf,IDCard_tf;//姓名、身份证
	private JComboBox town_cb,safeType_cb,helpType_cb;//乡镇、参保类型、救助烈性
	private JLabel helpScale_lb,startPay_lb,endPay_lb,canUse_lb,sumPay_lb,helpPay_lb,ladder_lb;//救助比例、起付线、封顶线、多次使用、合理支出、救助金额、阶梯比例
	private JTable bill_table;
	private JScrollPane sp;
	private JButton reset_btn,print_btn;//重置按钮、打印按钮
	private Object colname[]={"序号","费用合计（参与补偿费用、合理费用）","医保记帐（补偿费用）","自付费用","大病保险(大额、大额记帐）","困难群众大病补充保险","其它报销项"};
	private String userName;
	private DefaultTableModel model;
	private InputBill inputBill=new InputBill();
	private double helpScale=0.00,startPay=0.00,endPay=0.00,sumPay=0.00,helpPay=0.00;
	private String canUse="否";
	private JMenuItem[] mi;
	private JPopupMenu jpm;
	private int row=1;
	private int message=1;
	private double sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7;
	private double[][] ladderScale = null;//阶梯救助比例参数表
	
	public InputBillGUI(String userName){
		super();
		this.userName=userName;
		setInputBillPanel();
	}
	/*
	 * 设置本页面的总体值
	 */
	private void setInputBillPanel(){
		this.setBackground(null);
		this.setLayout(new BorderLayout());
		this.add(setInfoPanel(),BorderLayout.NORTH);
		this.add(setTablePanel(),BorderLayout.CENTER);
		this.add(setResultPanel(),BorderLayout.SOUTH);
	}
	/*
	 * 用以生成右键菜单
	 */
	private JPopupMenu setPopuMenu(){
		jpm=new JPopupMenu();
		JMenuItem[] mi={new JMenuItem("添加票据"),new JMenuItem("删除选定票据"),new JMenuItem("打印票据"),new JMenuItem("刷新页面")};
		this.mi=mi;
		for(int i=0;i<4;i++){
			jpm.add(mi[i]);
			mi[i].setFont(new Font("微软雅黑",0,14));
			mi[i].setBackground(new Color(255,250,250));
			mi[i].addActionListener(this);
		}
		return jpm;
	}
	/*
	 * 添加票据
	 */
	private void addRow(){
		model.addRow(new Vector());
		row++;
		bill_table.setValueAt("票据"+row, row-1, 0);
		setMoney();
	}
	/*
	 * 删除票据
	 */
	private void delRow(int r){
		model.removeRow(r-1);
		row--;
		String s[][]=getValue();
		model.setDataVector(s,colname);
		setMoney();
	}
	/*
	 * 改变钱数输出
	 */
	private void setMoney(){
		setSumPay();
		setHelpPay();
		DecimalFormat df=new DecimalFormat("#");
		try{
			sumPay_lb.setText("￥"+Double.parseDouble(df.format(sumPay)));
			sumPay=Double.parseDouble(df.format(sumPay));
			helpPay_lb.setText("￥"+Double.parseDouble(df.format(helpPay)));
			helpPay=Double.parseDouble(df.format(helpPay));
		}catch(Exception ex){
			
		}
		if(message!=0){
			sumPay_lb.setText("ERROR");
			helpPay_lb.setText("ERROR");
			sumPay=-1;
			helpPay=-1;
		}
	}
	/*
	 * 计算救助金额
	 */
	private void setHelpPay() {
		// TODO Auto-generated method stub
		helpPay = 0;
		if(helpScale<100){
			helpPay=sumPay*helpScale/100;
		}else{
			for(int i=0;i<ladderScale.length;i++){
				if(sumPay<=ladderScale[i][0]){
					break;
				}else if(sumPay>=ladderScale[i][0]&&sumPay>=ladderScale[i][1]&&ladderScale[i][1]!=-1){
					helpPay += ((ladderScale[i][1]-ladderScale[i][0])*(ladderScale[i][2]/100));
				}else if(sumPay>=ladderScale[i][0]&&sumPay<ladderScale[i][1]||ladderScale[i][1]==-1){
					helpPay += ((sumPay-ladderScale[i][0])*(ladderScale[i][2]/100));
					break;
				}
			}
		}
		if(sumPay<startPay){
			helpPay=0;
		}
		if(helpPay>endPay){
			helpPay=endPay;
		}
	}
	/*
	 * 计算合理支出
	 */
	private void setSumPay() {
		// TODO Auto-generated method stub
		message=0;
		String[][]s=getValue();
		sumCol2=getColSum(1, s);
		sumCol3=getColSum(2, s);
		sumCol4=getColSum(3, s);
		sumCol5=getColSum(4, s);
		sumCol6=getColSum(5, s);
		sumCol7=getColSum(6, s);
		if(sumCol2>=sumCol3+sumCol4+sumCol5+sumCol6+sumCol7){
			sumPay=sumCol2-sumCol3-sumCol4-sumCol5-sumCol6-sumCol7;
		}else{
			message=1;
		}
		checkRow(s);
	}
	/*
	 * 获得每列和
	 */
	private double getColSum(int r,String s[][]){
		int sum=0;
		for(int i=0;i<row;i++){
			double m=0;
			String str=s[i][r];
			if(null==str||"".equals(str)){
				str="0";
			}
			try{
				m=Double.parseDouble(str);
			}catch(Exception e){
				message=1;
			}
			if(m<0){
				message=1;
			}
			sum+=m;
		}
		return sum;
	}
	/*
	 * 检查每行
	 */
	private void checkRow(String[][] s){
		for(int i=0;i<row;i++){
			double m1 = 0,m2 = 0,m3 = 0,m4 = 0,m5 = 0,m6 = 0;
			for(int j=1;j<=6;j++){
				if(s[i][j].contains(" ")){
					message=1;
				}
			}
			try{
				m1=Double.parseDouble(changeNullZero(s[i][1]));
				m2=Double.parseDouble(changeNullZero(s[i][2]));
				m3=Double.parseDouble(changeNullZero(s[i][3]));
				m4=Double.parseDouble(changeNullZero(s[i][4]));
				m5=Double.parseDouble(changeNullZero(s[i][5]));
				m6=Double.parseDouble(changeNullZero(s[i][6]));
			}catch(Exception e){
				
			}
			if(m1<m2+m3+m4+m5+m6){
				message=1;
			}
		}
	}
	/*
	 * 当单元格为空时使值为0
	 */
	private String changeNullZero(String st){
		String str="";
		if(null==st||"".equals(st)){
			str="0";
		}
		else{
			str=st;
		}
		return str;
	}
	
	/*
	 * 返回表格中的数据
	 */
	private String[][] getValue(){
		row=bill_table.getRowCount();
		String data[][]=new String[row][7];
		for(int i=0;i<row;i++){
			for(int j=0;j<7;j++){
				if(j==0)
					data[i][j]="票据"+(i+1);
				else{
					data[i][j]=(String)bill_table.getValueAt(i,j);
					if(null==data[i][j]||"".equals(data[i][j])){
						data[i][j]="0";
					}
				}
			}
		}
		return data;
	}
	/*
	 * 生成底部面板
	 */
	private JPanel setResultPanel() {
		// TODO Auto-generated method stub
		JPanel resultPanel=new JPanel();
		resultPanel.setBackground(null);
		resultPanel.setPreferredSize(new Dimension(700,108));
		resultPanel.setLayout(new FlowLayout());
		
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.setBackground(null);
		
		JLabel l1=new JLabel("救助比例:");
		l1.setFont(new Font("微软雅黑",0,16));
		p1.add(l1);
		
		helpScale_lb=new JLabel("0%");
		helpScale_lb.setFont(new Font("微软雅黑",1,16));
		helpScale_lb.setForeground(Color.blue);
		p1.add(helpScale_lb);
		
		JLabel l2=new JLabel("      起付线:");
		l2.setFont(new Font("微软雅黑",0,16));
		p1.add(l2);
		
		startPay_lb=new JLabel("￥0.00");
		startPay_lb.setFont(new Font("微软雅黑",1,16));
		startPay_lb.setForeground(Color.blue);
		p1.add(startPay_lb);
		
		JLabel l3=new JLabel("       封顶线:");
		l3.setFont(new Font("微软雅黑",0,16));
		p1.add(l3);
		
		endPay_lb=new JLabel("￥0.00");
		endPay_lb.setFont(new Font("微软雅黑",1,16));
		endPay_lb.setForeground(Color.blue);
		p1.add(endPay_lb);
		
		JLabel l4=new JLabel("       可否使用:");
		l4.setFont(new Font("微软雅黑",0,16));
		p1.add(l4);
		
		canUse_lb=new JLabel("否");
		canUse_lb.setFont(new Font("微软雅黑",1,16));
		canUse_lb.setForeground(Color.blue);
		p1.add(canUse_lb);
		
		resultPanel.add(p1);
		
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout());
		p2.setBackground(null);
		
		JLabel l5=new JLabel("合理支出:");
		l5.setFont(new Font("微软雅黑",0,16));
		p2.add(l5);
		
		sumPay_lb=new JLabel("￥0.00");
		sumPay_lb.setFont(new Font("微软雅黑",1,16));
		sumPay_lb.setForeground(Color.red);
		p2.add(sumPay_lb);
		
		JLabel l6=new JLabel("      救助金额:");
		l6.setFont(new Font("微软雅黑",0,16));
		p2.add(l6);
		
		helpPay_lb=new JLabel("￥0.00");
		helpPay_lb.setFont(new Font("微软雅黑",1,16));
		helpPay_lb.setForeground(Color.red);
		p2.add(helpPay_lb);
		
		p2.add(new JLabel("      "));
		
		print_btn=new JButton("提交并打印");
		print_btn.setBackground(new Color(255,153,204));
		print_btn.setForeground(Color.white);
		print_btn.setFont(new Font("微软雅黑",1,16));
		print_btn.addActionListener(this);
		p2.add(print_btn);
		
		resultPanel.add(p2);
		
		JPanel p3=new JPanel();
		p3.setLayout(new FlowLayout());
		p3.setBackground(null);
		
		ladder_lb=new JLabel("");
		ladder_lb.setFont(new Font("微软雅黑",0,12));
		ladder_lb.setForeground(Color.black);
		p3.add(ladder_lb);
		
		resultPanel.add(p3);
		
		return resultPanel;
	}
	/*
	 * 生成表格面板
	 */
	private JScrollPane setTablePanel(){
		String [][] data=new String[1][7];
		data[0][0]="票据1";
		model=new DefaultTableModel(data,colname);
		bill_table=new JTable(model);
		bill_table.putClientProperty("terminateEditOnFocusLost", true);
		bill_table.setDefaultRenderer(Object.class,new TableStyle());
		bill_table.getTableHeader().setFont(new Font("微软雅黑",0,14));
		bill_table.setSelectionBackground(new Color(255,250,250));
		bill_table.getTableHeader().setBackground(new Color(255,231,255));
		bill_table.setFont(new Font("微软雅黑",0,14));
		bill_table.setRowHeight(25);
		bill_table.setBackground(Color.white);
		bill_table.addMouseListener(this);
		bill_table.setBackground(Color.WHITE);
		bill_table.setEnabled(false);
		bill_table.addKeyListener(this);
		bill_table.getTableHeader().setReorderingAllowed(false);
		sp=new JScrollPane(bill_table);
		sp.setBorder(null);
		sp.addMouseListener(this);
		return sp;
	}
	/*
	 * 生成顶部面板
	 */
	private JPanel setInfoPanel() {
		// TODO Auto-generated method stub
		
		JPanel infoPanel=new JPanel();
		infoPanel.setBackground(null);
		infoPanel.setLayout(new FlowLayout());
		infoPanel.setPreferredSize(new Dimension(700,90));
		
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p1.setBackground(null);
		
		JLabel l2=new JLabel("身份证号:");
		l2.setFont(new Font("微软雅黑",0,16));
		p1.add(l2);
		
		IDCard_tf=new JTextField(12);
		IDCard_tf.setBackground(Color.white);
		IDCard_tf.setSize(100,24);
		IDCard_tf.setFont(new Font("微软雅黑",0,18));
		IDCard_tf.addFocusListener(this);
		IDCard_tf.addKeyListener(this);
		p1.add(IDCard_tf);
		
		JLabel l1=new JLabel("姓名:");
		l1.setFont(new Font("微软雅黑",0,16));
		p1.add(l1);
		
		name_tf=new JTextField(6);
		name_tf.setBackground(Color.white);
		name_tf.setSize(100,24);
		name_tf.setFont(new Font("微软雅黑",0,18));
		p1.add(name_tf);
		
		JPanel p2=new JPanel();
		p2.setLayout(new FlowLayout());
		p2.setBackground(null);
		
		JLabel l3=new JLabel("乡镇/街道:");
		l3.setFont(new Font("微软雅黑",0,16));
		p2.add(l3);
		
		String[] town=inputBill.getTown_dec();
		town_cb=new JComboBox(town);
		setCB(town_cb);
		p2.add(town_cb);
		
		JLabel l4=new JLabel("参保类型:");
		l4.setFont(new Font("微软雅黑",0,16));
		p2.add(l4);
		
		String safeType[]=inputBill.getSafeType_dec();
		safeType_cb=new JComboBox(safeType);
		setCB(safeType_cb);
		p2.add(safeType_cb);
		
		JLabel l5=new JLabel("救助类型:");
		l5.setFont(new Font("微软雅黑",0,16));
		p2.add(l5);
		
		String helpType[]=inputBill.getHelpType_dec();
		helpType_cb=new JComboBox(helpType);
		setCB(helpType_cb);
		helpType_cb.addActionListener(this);
		p2.add(helpType_cb);
		
		p2.add(new JLabel("              "));
		
		reset_btn=new JButton("重置数据");
		reset_btn.setBackground(new Color(255,153,204));
		reset_btn.setForeground(Color.white);
		reset_btn.setFont(new Font("微软雅黑",1,16));
		reset_btn.addActionListener(this);
		p2.add(reset_btn);
		
		infoPanel.add(p1);
		infoPanel.add(p2);
		return infoPanel;
	}
	/*
	 * 用以设置下拉选项类型
	 */
	private void setCB(JComboBox cb){
		cb.setSize(80,24);
		cb.setFont(new Font("微软雅黑",0,14));
		cb.setBackground(Color.white);
	}
	/*
	 * 判断是否符合救助条件
	 */
	private void payIsTrue(String IDNO){
		try{
			if(!(inputBill.getHelpType(IDNO)[0].equals("")||
					inputBill.getHelpType(IDNO)[0].equals("null")||
					null==inputBill.getHelpType(IDNO)[0])){
				DefaultComboBoxModel<String> aModel=new DefaultComboBoxModel<String>(inputBill.getHelpType(IDNO));
				helpType_cb.setModel(aModel);
			}
		}catch (Exception e) {
//			有异常
		}
		String helpType=(String)helpType_cb.getSelectedItem();
		DecimalFormat df=new DecimalFormat("#.00");
		if(null!=IDNO&&!"".equals(IDNO)){
			if((IDCard.IDCardValidate(IDNO).equals("YES"))){
				if(inputBill.getCanUse(helpType, IDNO).equals("是")){
					bill_table.setEnabled(true);
					canUse="是";
					endPay=inputBill.getEndPay(helpType, IDCard_tf.getText());
					helpScale=inputBill.getHelpScale(helpType);
					startPay=inputBill.getStartPay(helpType);
				}else{
					bill_table.setEnabled(false);
					canUse="否";
					endPay=0;
					helpScale=0;
					startPay=0;
				}
			}else{
				JOptionPane.showMessageDialog(this, "您输入的身份证号不合法","系统提示",JOptionPane.WARNING_MESSAGE);
				bill_table.setEnabled(false);
				canUse="否";
				endPay=0;
				helpScale=0;
				startPay=0;
			}
		}else{
			bill_table.setEnabled(false);
			canUse="否";
			endPay=0;
			helpScale=0;
			startPay=0;
		}
		if(helpScale>100){
			ladderScale = inputBill.getLadderScale(Integer.toString((int)helpScale));
		}
		setJLabelText(canUse_lb, canUse);
		setJLabelText(endPay_lb,"￥"+Double.parseDouble(df.format(endPay)));
		if(helpScale<100){
			setJLabelText(helpScale_lb, Double.parseDouble(df.format(helpScale))+"%");
			setJLabelText(ladder_lb, "");
		}
		else{
			String s = "";
			for(int i=0;i<ladderScale.length;i++){
				if(ladderScale[i][1]!=-1){
					s += ladderScale[i][0]+"-"+ladderScale[i][1]+"："+ladderScale[i][2]+"%，";
				}else{
					s += ladderScale[i][0]+"-+∞："+ladderScale[i][2]+"%";
				}
			}
			setJLabelText(helpScale_lb, "阶梯比例");
			setJLabelText(ladder_lb, s);
		}
		setJLabelText(startPay_lb, "￥"+Double.parseDouble(df.format(startPay)));
	}
	/*
	 * 更改JLabel显示内容
	 */
	private void setJLabelText(JLabel l,String text){
		l.setText(text);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==IDCard_tf){
			String IDNO=IDCard_tf.getText()+e.getKeyChar();
			if(IDNO.length()==18){
				try{
					if(e.getKeyChar()!='x'&&e.getKeyChar()!='X'){
						String s=""+e.getKeyChar();
						Float.parseFloat(s);
					}
					payIsTrue(IDNO);
				}catch(Exception ex){
					
				}
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
		if(e.getKeyCode()==10||e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_TAB){
			setMoney();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int mods=e.getModifiers();
		
		if(e.getSource()==bill_table){
			if(canUse.equals("是")){
				if((mods&InputEvent.BUTTON1_MASK)!=0){
					setMoney();
				}else{
					setPopuMenu();
					jpm.show(e.getComponent(),e.getX(),e.getY());
				}
			}else{
				JOptionPane.showMessageDialog(this, "请输入合法的身份证号以解锁票据录入！","系统提示",JOptionPane.WARNING_MESSAGE);
			}
		}
		if(e.getSource()==sp){
			if(canUse.equals("是")){
				if((mods&InputEvent.BUTTON1_MASK)!=0){
					setMoney();
				}else{
					setPopuMenu();
					jpm.show(e.getComponent(),e.getX(),e.getY());
				}
			}else{
				JOptionPane.showMessageDialog(this, "请输入合法的身份证号以解锁票据录入！","系统提示",JOptionPane.WARNING_MESSAGE);
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
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==IDCard_tf){
			payIsTrue(IDCard_tf.getText());
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==reset_btn){
			if(JOptionPane.showConfirmDialog(this, "您确定要放弃当前录入，并刷新页面？")==JOptionPane.YES_OPTION){
				this.setVisible(false);
				this.removeAll();
				setInputBillPanel();
				this.setVisible(true);
			}
		}
		if(e.getSource()==helpType_cb){
			payIsTrue(IDCard_tf.getText());
			try{
				setMoney();
			}catch (Exception exc) {
				
			}
		}
		try{
			if(e.getSource()==mi[0]){
				addRow();
			}
			if(e.getSource()==mi[1]){
				int r=bill_table.getSelectedRow()+1;
				if(r>0){
					delRow(r);
				}else{
					JOptionPane.showMessageDialog(this, "您未选定任何行","系统提示",JOptionPane.WARNING_MESSAGE);
				}
			}
			if(e.getSource()==mi[2]){
				setMoney();
				if(helpPay>0){
					if(name_tf.getText().contains(" ")||name_tf.getText().equals("")||null==name_tf.getText()){
						message=1;
						JOptionPane.showMessageDialog(this, "姓名输入不合法！","系统提示",JOptionPane.WARNING_MESSAGE);
					}

					if(message!=1&&canUse.equals("是")){
						try{
							boolean is=inputBill.printBill(userName,name_tf.getText(),IDCard_tf.getText(),(String)town_cb.getSelectedItem(),
									(String)safeType_cb.getSelectedItem(),(String)helpType_cb.getSelectedItem(),
									helpScale,startPay,endPay,getValue(),sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7,sumPay,helpPay);
							if(is){
								this.setVisible(false);
								this.removeAll();
								setInputBillPanel();
								this.setVisible(true);
							}
						}catch(NullPointerException e1){
							JOptionPane.showMessageDialog(this, "请输入有效的数据！");
						}
					}else{
						JOptionPane.showMessageDialog(this, "请检查输入数据是否合法！");
					}
				}else{
					JOptionPane.showMessageDialog(this, "不符合救助条件无法提交和打印！");
				}
			}
			if(e.getSource()==mi[3]){
				if(JOptionPane.showConfirmDialog(this, "是否要刷新页面？")==JOptionPane.YES_OPTION){
					this.setVisible(false);
					this.removeAll();
					setInputBillPanel();
					this.setVisible(true);
				}
			}
		}catch(Exception ex){
			
		}
		if(e.getSource()==print_btn){
			setMoney();
			if(helpPay>0){
				if(name_tf.getText().contains(" ")||name_tf.getText().equals("")||null==name_tf.getText()){
					message=1;
					JOptionPane.showMessageDialog(this, "姓名输入不合法！","系统提示",JOptionPane.WARNING_MESSAGE);
				}
				if(message!=1&&canUse.equals("是")){
					try{
						boolean is=inputBill.printBill(userName,name_tf.getText(),IDCard_tf.getText(),(String)town_cb.getSelectedItem(),
								(String)safeType_cb.getSelectedItem(),(String)helpType_cb.getSelectedItem(),
								helpScale,startPay,endPay,getValue(),sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7,sumPay,helpPay);
						if(is){
							this.setVisible(false);
							this.removeAll();
							setInputBillPanel();
							this.setVisible(true);
						}
					}catch(NullPointerException e1){
						JOptionPane.showMessageDialog(this, "请输入有效的数据！");
					}
				}else{
					JOptionPane.showMessageDialog(this, "请检查输入数据是否合法！");
				}
			}else{
				JOptionPane.showMessageDialog(this, "不符合救助条件无法提交和打印！");
			}
		}
	}
	
}
class TableStyle extends DefaultTableCellRenderer{
	public TableStyle(){
		setHorizontalAlignment(CENTER);
	}
}
