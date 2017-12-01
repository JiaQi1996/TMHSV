package org.zqrc.tmhs.view.main.centerPanel.historyBill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.zqrc.tmhs.view.main.centerPanel.inputBill.InputBill;
import org.zqrc.tmhs.view.method.IDCard;

/*
 * By Gorden @2016-10-22
 * 此类主要生成帐单录入页面
 */
public class AnHistoryBillGUI extends JPanel implements MouseListener,KeyListener,ActionListener{
	private String role,billNO,name,IDNO,town,safeType,helpType,userName;
	private double helpScale,startPay,endPay,sumPay,helpPay;
	private JTable table;
	private JScrollPane sp;
	private JLabel billNO_l,name_l,IDNO_l,town_l,safeType_l,helpType_l,userName_l,helpScale_l,startPay_l,endPay_l,sumPay_l,helpPay_l,ladder_l;
	private JButton rePaint_btn,reChange_btn,back_btn;
	private String billData[];
	private HistoryBill set=new HistoryBill();
	private Object colname[]={"序号","费用合计（参与补偿费用、合理费用）","医保记帐（补偿费用）","自付费用","大病保险(大额、大额记帐）","困难群众大病补充保险","其它报销项"};
	private DefaultTableModel model;
	private String tableData[][];
	private int message=0,message1=0;
	private JMenuItem[] mi;
	private JPopupMenu jpm;
	private int row=1;
	private double sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7;
	private String userNam;
	private double[][] ladderScale = null;//阶梯救助比例参数表
	public AnHistoryBillGUI(String userNam,String role,String billNO){
		super();
		this.role=role;
		this.billNO=billNO;
		setBillData();
		setInputBillPanel();
	}
	private void setInputBillPanel(){
		this.setBackground(null);
		this.setLayout(new BorderLayout());
		this.add(setTopPanel(),BorderLayout.NORTH);
		this.add(setTablePanel(),BorderLayout.CENTER);
		this.add(setFootPanel(),BorderLayout.SOUTH);
	}
	/*
	 * 用以生成右键菜单
	 */
	private JPopupMenu setPopuMenu(){
		jpm=new JPopupMenu();
		JMenuItem[] mi={new JMenuItem("添加票据"),new JMenuItem("删除选定票据"),new JMenuItem("打印并提交")};
		this.mi=mi;
		for(int i=0;i<3;i++){
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
		table.setValueAt("票据"+row, row-1, 0);
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
			sumPay_l.setText("￥"+Double.parseDouble(df.format(sumPay)));
			helpPay_l.setText("￥"+Double.parseDouble(df.format(helpPay)));
			sumPay=Double.parseDouble(df.format(sumPay));
			helpPay=Double.parseDouble(df.format(helpPay));
		}catch(Exception ex){
			
		}
		if(message1!=0){
			sumPay_l.setText("ERROR");
			helpPay_l.setText("ERROR");
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
		message1=0;
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
			message1=1;
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
				message1=1;
			}
			if(m<0){
				message1=1;
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
		row=table.getRowCount();
		String data[][]=new String[row][7];
		for(int i=0;i<row;i++){
			for(int j=0;j<7;j++){
				if(j==0)
					data[i][j]="票据"+(i+1);
				else
					data[i][j]=(String)table.getValueAt(i,j);
				if(null==data[i][j]||"".equals(data[i][j])){
					data[i][j]="0";
				}
			}
		}
		return data;
	}
	private Component setFootPanel() {
		JPanel footPanel=new JPanel();
		footPanel.setBackground(Color.white);
		footPanel.setLayout(new FlowLayout());
		footPanel.setPreferredSize(new Dimension(700,108));
		
		JPanel p3=new JPanel();
		p3.setLayout(new FlowLayout());
		p3.setBackground(null);
		
		ladder_l=new JLabel("");
		ladder_l.setFont(new Font("微软雅黑",0,12));
		ladder_l.setForeground(Color.black);
		p3.add(ladder_l);
		
		JPanel p1=new JPanel();
		p1.setBackground(null);
		p1.setLayout(new FlowLayout());
		p1.add(getTipJLable("救助比例:"));
		if(helpScale<100){
			p1.add(getTipJLable(helpScale_l,helpScale+"%"));
		}else{
			InputBill ib = new InputBill();
			ladderScale = ib.getLadderScale(Integer.toString((int)helpScale));
			String s = "";
			for(int i=0;i<ladderScale.length;i++){
				if(ladderScale[i][1]!=-1){
					s += ladderScale[i][0]+"-"+ladderScale[i][1]+"："+ladderScale[i][2]+"%，";
				}else{
					s += ladderScale[i][0]+"-+∞："+ladderScale[i][2]+"%";
				}
			}
			p1.add(getTipJLable(helpScale_l,"阶梯比例"));
			ladder_l.setText(s);
			
		}
		p1.add(getTipJLable("起付线:"));
		p1.add(getTipJLable(startPay_l,"￥"+startPay));
		p1.add(getTipJLable("封顶线:"));
		p1.add(getTipJLable(endPay_l,"￥"+endPay));
		
		JPanel p2=new JPanel();
		p2.setBackground(null);
		p2.setLayout(new FlowLayout());
		p2.add(getTipJLable("合理支出:"));
		sumPay_l=getTipJLable(sumPay_l,"￥"+sumPay);
		p2.add(sumPay_l);
		p2.add(getTipJLable("救助金额:"));
		helpPay_l=getTipJLable(helpPay_l,"￥"+helpPay);
		p2.add(helpPay_l);
		if(role.equals("0")){
			reChange_btn=new JButton("修改订单");
			getJButton(reChange_btn);
			p2.add(reChange_btn);
		}
		
		footPanel.add(p1);
		footPanel.add(p2);
		footPanel.add(p3);
		// TODO Auto-generated method stub
		return footPanel;
	}
	private Component setTablePanel() {
		model=new DefaultTableModel(tableData,colname);
		table=new JTable(model);
		table.putClientProperty("terminateEditOnFocusLost", true);
		table.setDefaultRenderer(Object.class,new TableStyles());
		table.getTableHeader().setFont(new Font("微软雅黑",0,14));
		table.setSelectionBackground(new Color(255,250,250));
		table.getTableHeader().setBackground(new Color(255,231,255));
		table.setFont(new Font("微软雅黑",0,14));
		table.setRowHeight(25);
		table.setBackground(Color.white);
		table.addMouseListener(this);
		table.setBackground(Color.WHITE);
		table.setEnabled(false);
		table.addKeyListener(this);
		table.getTableHeader().setReorderingAllowed(false);
		
		sp=new JScrollPane(table);
		sp.setBorder(null);
		sp.addMouseListener(this);
		return sp;
	}
	private Component setTopPanel() {
		// TODO Auto-generated method stub
		JPanel topPanel=new JPanel();
		topPanel.setBackground(Color.white);
		topPanel.setLayout(new FlowLayout());
		topPanel.setPreferredSize(new Dimension(700,90));
		
		JPanel p1=new JPanel();
		p1.setBackground(null);
		p1.setLayout(new FlowLayout());
		p1.add(getTipJLable("单号:"));
		p1.add(getTipJLable(billNO_l, billNO));
		p1.add(getTipJLable("姓名:"));
		p1.add(getTipJLable(name_l, name));
		p1.add(getTipJLable("身份证号:"));
		p1.add(getTipJLable(IDNO_l,IDNO));
		topPanel.add(p1);
		
		JPanel p2=new JPanel();
		p2.setBackground(null);
		p2.setLayout(new FlowLayout());
		p2.add(getTipJLable("乡镇(街道):"));
		p2.add(getTipJLable(town_l,town));
		p2.add(getTipJLable("参保类型:"));
		p2.add(getTipJLable(safeType_l,safeType));
		p2.add(getTipJLable("救助类型:"));
		p2.add(getTipJLable(helpType_l,helpType));
		p2.add(getTipJLable("添加者:"));
		p2.add(getTipJLable(userName_l,userName));
		rePaint_btn=new JButton("重新打印");
		getJButton(rePaint_btn);
		p2.add(rePaint_btn);
		back_btn=new JButton("返回上层");
		getJButton(back_btn);
		p2.add(back_btn);
		topPanel.add(p2);
		return topPanel;
	}
	/*
	 * 获取数据
	 */
	private void setBillData(){
		String[][] data=set.getBillData("单号", billNO, null, null);
		this.billData=data[0];
		this.name=billData[1];
		this.IDNO=billData[2];
		this.town=billData[3];
		this.safeType=billData[4];
		this.helpType=billData[5];
		this.helpScale=Double.parseDouble(billData[6]);
		this.startPay=Double.parseDouble(billData[7]);
		this.endPay=Double.parseDouble(billData[8]);
		this.sumPay=Double.parseDouble(billData[15]);
		this.helpPay=Double.parseDouble(billData[16]);
		this.userName=billData[18];
		tableData=set.getBillsData(billNO);
	}
	/*
	 * 生成标签
	 */
	private JLabel getTipJLable(String text){
		JLabel l=new JLabel(text);
		l.setFont(new Font("微软雅黑",0,16));
		return l;
	}
	private JLabel getTipJLable(JLabel l,String text){
		l=new JLabel(text);
		l.setFont(new Font("微软雅黑",1,16));
		l.setForeground(Color.blue);
		return l;
	}
	/*
	 * 设置按钮风格
	 */
	private void getJButton(JButton btn){
		btn.setBackground(new Color(255,153,204));
		btn.setForeground(Color.white);
		btn.setFont(new Font("微软雅黑",1,16));
		btn.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==rePaint_btn){
			setMoney();
			if(helpPay>0){
				if(message1==0){
					try{
						setMoney();
						String data[][]=getValue();
						boolean is=set.printBill(billNO,userName,name,IDNO,town,safeType,helpType,helpScale,startPay,endPay,getValue(),sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7,sumPay,helpPay,message);
						if(is){
							message=0;
							table.setEnabled(false);
						}else{
							if(message==1){
								JOptionPane.showMessageDialog(this, "打印取消，数据未提交！");
							}
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
		if(e.getSource()==back_btn){
			if(message==1){
				if(JOptionPane.showConfirmDialog(this, "您确定要放弃当前录入，并返回上层？")==JOptionPane.YES_OPTION){
					this.setVisible(false);
					this.removeAll();
					this.add(new HistoryBillGUI(userNam,role));
					this.setVisible(true);
				}
			}else{
				this.setVisible(false);
				this.removeAll();
				this.add(new HistoryBillGUI(userNam,role));
				this.setVisible(true);
			}
		}
		if(e.getSource()==reChange_btn){
			this.message=1;
			table.setEnabled(true);
		}
		try{
			if(e.getSource()==mi[0]){
				addRow();
			}
			if(e.getSource()==mi[1]){
				int r=table.getSelectedRow()+1;
				if(r>0){
					delRow(r);
				}else{
					JOptionPane.showMessageDialog(this, "您未选定任何行","系统提示",JOptionPane.WARNING_MESSAGE);
				}
			}
			if(e.getSource()==mi[2]){
				setMoney();
				if(helpPay>0){
					if(message==1&&message1!=1){
						try{
							boolean is=set.printBill(billNO,userName,name,IDNO,town,safeType,helpType,helpScale,startPay,endPay,getValue(),sumCol2,sumCol3,sumCol4,sumCol5,sumCol6,sumCol7,sumPay,helpPay,message);
							if(is){
								message=0;
								table.setEnabled(false);
							}else{
								JOptionPane.showMessageDialog(this, "打印取消，数据未提交！");
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
		}catch(Exception ex){
			
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==10||e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_TAB){
			setMoney();
		}
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
		if(message==1){
			int mods=e.getModifiers();
			if(e.getSource()==table){
				if((mods&InputEvent.BUTTON1_MASK)!=0){
					setMoney();
				}else{
					setPopuMenu();
					jpm.show(e.getComponent(),e.getX(),e.getY());
				}
			}
			if(e.getSource()==sp){
				if((mods&InputEvent.BUTTON1_MASK)!=0){
					setMoney();
				}else{
					setPopuMenu();
					jpm.show(e.getComponent(),e.getX(),e.getY());
				}
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
class TableStyles extends DefaultTableCellRenderer{
	public TableStyles(){
		setHorizontalAlignment(CENTER);
	}
}

