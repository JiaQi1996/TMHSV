package org.zqrc.tmhs.view.main.centerPanel.setUser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
/*
 * By Gorden @2016-10-25
 * 此类主要完成生成用户管理界面
 */
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SetUserGUI extends JPanel implements ActionListener,MouseListener{
	private String userName;
	private JButton searchUser_btn,addUser_btn;
	private JTextField searchUser_tf,userName_tf,name_tf,password_tf;
	private JScrollPane sp;
	private JTable table;
	private String[] col={"用户名","角色","姓名","密码"};
	private String[][] userData;
	private Object[][] tableData;
	private DefaultTableModel model;
	private SetUser set=new SetUser();
	private JMenuItem[] mi;
	private int message=0; 
	private JPopupMenu jpm;
	private int selectedRow;
	public SetUserGUI(String userName){
		super();
		this.userName=userName;
		setThisPanel();
	}
	private void setThisPanel(){
		this.setBackground(new Color(255,250,250));
		this.setLayout(new BorderLayout());
		this.add(topPanel(),BorderLayout.NORTH);
		this.add(tablePanel(),BorderLayout.CENTER);
		this.add(addNewUserPanel(),BorderLayout.SOUTH);
		this.setVisible(true);
	}
	private JPopupMenu setPopuMenu(){
		jpm=new JPopupMenu();
		JMenuItem[] mi={new JMenuItem("修改选定行人员数据"),new JMenuItem("提交修改后的数据"),new JMenuItem("删除选定行人员数据")};
		this.mi=mi;
		for(int i=0;i<3;i++){
			jpm.add(mi[i]);
			mi[i].setFont(new Font("微软雅黑",0,14));
			mi[i].setBackground(new Color(255,250,250));
			mi[i].addActionListener(this);
		}
		return jpm;
	}
	private JPanel topPanel(){
		JPanel topPanel=new JPanel();
		topPanel.setBackground(null);
		searchUser_tf=new JTextField(12);
		searchUser_tf.setFont(new Font("微软雅黑",0,16));
		topPanel.add(searchUser_tf);
		searchUser_btn=new JButton("搜索用户");
		searchUser_btn.setBackground(new Color(255,153,204));
		searchUser_btn.setFont(new Font("微软雅黑",1,16));
		searchUser_btn.setForeground(Color.white);
		searchUser_btn.addActionListener(this);
		topPanel.add(searchUser_btn);
		return topPanel;
	}
	private JScrollPane tablePanel(){
		model=new DefaultTableModel(tableData,col){
			public boolean isCellEditable(int row,int column){
				if(column==2||column==3){
					return true;
				}else{
					return false;
				}
			}
		};
		setTableData(null);
		table=new JTable(model);
		table.setDefaultRenderer(Object.class,new TableStyle());
		table.setRowHeight(24);
		table.getTableHeader().setFont(new Font("微软雅黑",0,14));
		table.setSelectionBackground(new Color(255,250,250));
		table.getTableHeader().setBackground(new Color(255,231,255));
		table.setFont(new Font("微软雅黑",0,14));
		table.getTableHeader().setReorderingAllowed(false);
		sp=new JScrollPane(table);
		sp.addMouseListener(this);
		table.addMouseListener(this);
		return sp;
	}
	private JPanel addNewUserPanel(){
		JPanel addNewUserPanel=new JPanel();
		
		JLabel l1=new JLabel("账号:");
		l1.setFont(new Font("微软雅黑",0,16));
		addNewUserPanel.add(l1);
		
		userName_tf=new JTextField(6);
		userName_tf.setFont(new Font("微软雅黑",0,16));
		addNewUserPanel.add(userName_tf);
		
		JLabel l2=new JLabel("姓名:");
		l2.setFont(new Font("微软雅黑",0,16));
		addNewUserPanel.add(l2);
		
		name_tf=new JTextField(6);
		name_tf.setFont(new Font("微软雅黑",0,16));
		addNewUserPanel.add(name_tf);
		
		JLabel l3=new JLabel("密码:");
		l3.setFont(new Font("微软雅黑",0,16));
		addNewUserPanel.add(l3);
		
		password_tf=new JTextField(6);
		password_tf.setFont(new Font("微软雅黑",0,16));
		addNewUserPanel.add(password_tf);
		
		addNewUserPanel.setBackground(null);
		addUser_btn=new JButton("添加新用户");
		addUser_btn.setBackground(new Color(255,153,204));
		addUser_btn.setFont(new Font("微软雅黑",1,16));
		addUser_btn.setForeground(Color.white);
		addUser_btn.addActionListener(this);
		addNewUserPanel.add(addUser_btn);
		
		return addNewUserPanel;
	}
	private void setTableData(String data){
		userData=set.getUserData(data);
		tableData=new Object[userData.length][4];
		for(int i=0;i<userData.length;i++){
			for(int j=0;j<4;j++){
				tableData[i][j]=userData[i][j];
			}
		}
		model.setDataVector(tableData, col);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		selectedRow=table.getSelectedRow();
		if(e.getSource()==searchUser_btn){
			String data=searchUser_tf.getText();
			setTableData(data);
		}
		try{
			if(e.getSource()==mi[0]){
				if(-1!=table.getSelectedRow()){
					if(message==0){
						selectedRow=table.getSelectedColumn()-1;
						message=1;
					}else{
						if(JOptionPane.showConfirmDialog(this, "您之前修改的数据还未提交，您确定要放弃之前的修改，并选取当前行？")==JOptionPane.YES_OPTION){
							this.setVisible(false);
							this.removeAll();
							setThisPanel();
							this.setVisible(true);
							selectedRow=table.getSelectedColumn()-1;
						}
					}
				}else{
					JOptionPane.showMessageDialog(this,"您未选定任何行！" ,"系统提示",JOptionPane.ERROR_MESSAGE);
				}
			}
			if(e.getSource()==mi[2]){
				if(-1!=table.getSelectedRow()){
					if(message==0){
						if(JOptionPane.showConfirmDialog(this, "您确定要删除用户"+(String)table.getValueAt(selectedRow, 0))==JOptionPane.YES_OPTION){
							if(!table.getValueAt(selectedRow, 0).equals("admin")){
								set.DelectUserData((String)table.getValueAt(selectedRow, 0));
								this.setVisible(false);
								this.removeAll();
								setThisPanel();
								this.setVisible(true);
								JOptionPane.showMessageDialog(this,"删除成功！");
								table.repaint();
							}else{
								JOptionPane.showMessageDialog(this,"您不具有此权限，超级管理员无法被删除！");
							}
						}
					}else{
						if(JOptionPane.showConfirmDialog(this, "您之前修改的数据还未提交，您确定要放弃之前的修改，并选取当前行？")==JOptionPane.YES_OPTION){
							selectedRow=table.getSelectedRow();
							if(JOptionPane.showConfirmDialog(this, "您确定要删除用户"+(String)table.getValueAt(selectedRow, 0))==JOptionPane.YES_OPTION){
								if(!(((String)table.getValueAt(selectedRow, 1))).equals("admin")){
									set.DelectUserData((String)table.getValueAt(selectedRow, 0));
									this.setVisible(false);
									this.removeAll();
									setThisPanel();
									this.setVisible(true);
									JOptionPane.showMessageDialog(this,"删除成功！");
									table.repaint();
								}else{
									JOptionPane.showMessageDialog(this,"系统提示" ,"您不具有此权限，超级管理员无法被删除！",ERROR);
								}
							}
							message=0;
						}
					}
				}else{
					JOptionPane.showMessageDialog(this,"您未选定任何行！" ,"系统提示",JOptionPane.ERROR_MESSAGE);
				}
			}
			if(e.getSource()==mi[1]){
				if(((String)table.getValueAt(selectedRow, 2)).contains(" ")||((String)table.getValueAt(selectedRow, 3)).contains(" ")){
					JOptionPane.showMessageDialog(this, "您输入的数据含有空格！");
				}else{
					if(JOptionPane.showConfirmDialog(this, "您确定要修改用户"+(String)table.getValueAt(selectedRow, 0)+"的信息？")==JOptionPane.YES_OPTION){
						set.updateUserData((String)table.getValueAt(selectedRow, 0),(String)table.getValueAt(selectedRow, 2),(String)table.getValueAt(selectedRow, 3));
						message=0;
					}
					JOptionPane.showMessageDialog(this, "该用户信息已被修改!");
					table.repaint();
				}
			}
		}catch(Exception ex){
			//ex.printStackTrace();
		}
		if(e.getSource()==addUser_btn){
			if(userName_tf.getText().contains(" ")||password_tf.getText().contains(" ")){
				JOptionPane.showMessageDialog(this, "录入数据中含有空格！");
			}else{
				if((!(userName_tf.getText().equals(""))&&null!=userName_tf.getText())&&(!(password_tf.getText().equals(""))&&null!=password_tf.getText())){
					set.addUser(userName_tf.getText(),name_tf.getText(),password_tf.getText());
					setTableData(null);
					this.setVisible(false);
					this.removeAll();
					setThisPanel();
					this.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(this, "录入数据错误，添加失败！");
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int mods=e.getModifiers();
		if((mods&InputEvent.BUTTON1_MASK)==0){
			setPopuMenu();
			jpm.show(e.getComponent(),e.getX(),e.getY());
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