package org.zqrc.tmhs.view.main.centerPanel.setSystemData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;*/
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
/*import javax.swing.JFileChooser;*/
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.zqrc.tmhs.view.main.centerPanel.inputBill.InputBill;

import sun.java2d.loops.ScaledBlit;

/*
 * By Gorden @2016-10-24
 * 此类主要用以生成系统设置面板
 * v4.1修改
 */

public class SetSystemDataGUI extends JPanel implements ActionListener{
	private JPanel mainJPanel;
	private JComboBox town_cb,safeType_cb,helpType_cb,setHelpType_cb,moreUse_cb,ladder_cb;
	private JTextField town_tf,safeType_tf,helpType_tf,startPay_tf,endPay_tf;//,path1_tf,path2_tf;
	public JTextField helpScale_tf;
	private JButton del_town_btn,add_town_btn,del_safeType_btn,add_safeType_btn,del_helpType_btn,add_helpType_btn,submit_btn,reset_btn;//path1_btn,path2_btn;
	private String userName;
	private SetSystemData set=new SetSystemData();
	private int message=0;
	public double[][] LadderScale;
	public double helpScale;
//	参数设置抓转换
	private String setHelpTypeTemp="";
	public SetSystemDataGUI(String userName){
		super();
		this.userName=userName;
		this.setBackground(new Color(255,250,250));
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.add(setThisPanel(),BorderLayout.CENTER);
		setHelpTypeTemp=(String)setHelpType_cb.getSelectedItem();
		setHelpData();
	}
	/*
	 * 设置本类中的面板
	 */
	private JPanel setThisPanel() {
		// TODO Auto-generated method stub
		mainJPanel = new JPanel();
		mainJPanel.setBackground(null);
		mainJPanel.setLayout(new BorderLayout());
		mainJPanel.add(new JScrollPane(setThisMainPanel()),BorderLayout.CENTER);
		mainJPanel.setVisible(true);
		return mainJPanel;
	}
	
	/*
	 * 生成标题标签
	 */
	private JLabel getTitleJLabel(String text){
		JLabel l=new JLabel(text);
		l.setFont(new Font("微软雅黑",1,20));
		return l;
	}
	/*
	 * 生成标签
	 */
	private JLabel getTipJLable(String text){
		JLabel l=new JLabel(text);
		l.setFont(new Font("微软雅黑",1,16));
		return l;
	}
	/*
	 * 设置按钮风格
	 */
	private void setJButtonStyle(JButton btn){
		btn.setBackground(new Color(255,153,204));
		btn.setForeground(Color.white);
		btn.setFont(new Font("微软雅黑",1,16));
		btn.addActionListener(this);
	}
	/*
	 * 设置文本框风格
	 */
	private void setJTextFieldStyle(JTextField tf){
		tf.setBackground(Color.white);
		tf.setFont(new Font("微软雅黑",0,16));
	}
	/*
	 * 设置下拉选项风格
	 */
	private void setJComboBoxStyle(JComboBox cb){
		cb.setFont(new Font("微软雅黑",0,14));
		cb.setBackground(Color.white);
	}
	/*
	 * 编写主要面板
	 */
	private JPanel setThisMainPanel(){
		JPanel mainPanel=new JPanel();
		mainPanel.setBackground(new Color(255,250,250));
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(setTopThisMainPanel());
		return mainPanel;
	}
	/*
	 * 编写主要面板上部面板
	 */
	private JPanel setTopThisMainPanel(){
		JPanel topPanel=new JPanel();
		topPanel.setBackground(null);
		//topPanel.setLayout(new GridLayout(12,2));
		topPanel.setLayout(new GridLayout(9,2));
		JPanel p1=new JPanel();
		p1.setBackground(null);
		p1.add(getTitleJLabel("乡镇/街道字典设置                   "));
		topPanel.add(p1);
		topPanel.add(new JLabel());
		
		
		JPanel p2=new JPanel();
		p2.setBackground(null);
		town_cb=new JComboBox(set.getTown_dec());
		setJComboBoxStyle(town_cb);
		p2.add(town_cb);
		p2.add(new JLabel("        "));
		del_town_btn=new JButton("删除当前条目");
		setJButtonStyle(del_town_btn);
		p2.add(del_town_btn);
		topPanel.add(p2);
		
		JPanel p3=new JPanel();
		p3.setBackground(null);
		town_tf=new JTextField(8);
		setJTextFieldStyle(town_tf);
		p3.add(town_tf);
		p3.add(new JLabel("        "));
		add_town_btn=new JButton("添加条目");
		setJButtonStyle(add_town_btn);
		p3.add(add_town_btn);
		topPanel.add(p3);
		
		
		JPanel p4=new JPanel();
		p4.setBackground(null);
		p4.add(getTitleJLabel("参保类型字典设置                   "));
		topPanel.add(p4);
		topPanel.add(new JLabel());
		
		JPanel p5=new JPanel();
		p5.setBackground(null);
		safeType_cb=new JComboBox(set.getSafeType_dec());
		setJComboBoxStyle(safeType_cb);
		p5.add(safeType_cb);
		p5.add(new JLabel("        "));
		del_safeType_btn=new JButton("删除当前条目");
		setJButtonStyle(del_safeType_btn);
		p5.add(del_safeType_btn);
		topPanel.add(p5);
		
		JPanel p6=new JPanel();
		p6.setBackground(null);
		safeType_tf=new JTextField(8);
		setJTextFieldStyle(safeType_tf);
		p6.add(safeType_tf);
		p6.add(new JLabel("        "));
		add_safeType_btn=new JButton("添加条目");
		setJButtonStyle(add_safeType_btn);
		p6.add(add_safeType_btn);
		topPanel.add(p6);
		
		
		JPanel p7=new JPanel();
		p7.setBackground(null);
		p7.add(getTitleJLabel("救助类型字典设置                   "));
		topPanel.add(p7);
		topPanel.add(new JLabel());
		
		JPanel p8=new JPanel();
		p8.setBackground(null);
		
		helpType_cb=new JComboBox(set.getHelpType_dec());
		setJComboBoxStyle(helpType_cb);
		p8.add(helpType_cb);
		p8.add(new JLabel("        "));
		del_helpType_btn=new JButton("删除当前条目");
		setJButtonStyle(del_helpType_btn);
		p8.add(del_helpType_btn);
		topPanel.add(p8);
		
		JPanel p9=new JPanel();
		p9.setBackground(null);
		helpType_tf=new JTextField(8);
		setJTextFieldStyle(helpType_tf);
		p9.add(helpType_tf);
		p9.add(new JLabel("        "));
		add_helpType_btn=new JButton("添加条目");
		setJButtonStyle(add_helpType_btn);
		p9.add(add_helpType_btn);
		topPanel.add(p9);
		
		
		JPanel p10=new JPanel();
		p10.setBackground(null);
		p10.add(getTitleJLabel("救助类型参数设置                   "));
		topPanel.add(p10);
		topPanel.add(new JLabel());
		
		JPanel p11=new JPanel();
		p11.setBackground(null);
		setHelpType_cb=new JComboBox(set.getHelpType_dec());
		setHelpType_cb.addActionListener(this);
		setJComboBoxStyle(setHelpType_cb);
		//p11.add(new JLabel("        "));
		p11.add(setHelpType_cb);
		p11.add(getTipJLable("    救助比例(%):"));
		String isLadder[]={"固定比例","阶梯比例"};
		ladder_cb=new JComboBox(isLadder);
		setJComboBoxStyle(ladder_cb);
		ladder_cb.addActionListener(this);
		p11.add(ladder_cb);
		topPanel.add(p11);
		
		JPanel p12=new JPanel();
		p12.setBackground(null);
		helpScale_tf=new JTextField(6);
		setJTextFieldStyle(helpScale_tf);
		p12.add(helpScale_tf);
		p12.add(getTipJLable("    起付线(￥):"));
		startPay_tf=new JTextField(6);
		setJTextFieldStyle(startPay_tf);
		p12.add(startPay_tf);
		topPanel.add(p12);
		
		JPanel p13=new JPanel();
		p13.setBackground(null);
		p13.add(getTipJLable("封顶线(￥):"));
		endPay_tf=new JTextField(6);
		setJTextFieldStyle(endPay_tf);
		p13.add(endPay_tf);
		p13.add(new JLabel("    "));
		p13.add(getTipJLable("多次使用:"));
		String isno[]={"是","否"};
		moreUse_cb=new JComboBox(isno);
		setJComboBoxStyle(moreUse_cb);
		p13.add(moreUse_cb);
		topPanel.add(p13);
		topPanel.add(p13);
		
		JPanel p14=new JPanel();
		p14.setBackground(null);
		submit_btn=new JButton("  确认更改  ");
		reset_btn=new JButton("  放弃更改  ");
		setJButtonStyle(submit_btn);
		setJButtonStyle(reset_btn);
		p14.add(submit_btn);
		p14.add(reset_btn);
		topPanel.add(p14);
		
		/*JPanel p15=new JPanel();
		p15.setBackground(null);
		p15.add(getTitleJLabel("系统文件备份路径设置            "));
		topPanel.add(p15);
		topPanel.add(new JLabel());
		
		JPanel p16=new JPanel();
		p16.setBackground(null);
		p16.add(getTipJLable("路径1："));
		path1_tf=new JTextField(10);
		path1_tf.setEnabled(false);
		setJTextFieldStyle(path1_tf);
		p16.add(path1_tf);
		path1_btn=new JButton("选取路径");
		setJButtonStyle(path1_btn);
		p16.add(path1_btn);
		topPanel.add(p16);
		
		JPanel p17=new JPanel();
		p17.setBackground(null);
		p17.add(getTipJLable("路径2："));
		path2_tf=new JTextField(10);
		path2_tf.setEnabled(false);
		setJTextFieldStyle(path2_tf);
		p17.add(path2_tf);
		path2_btn=new JButton("选取路径");
		setJButtonStyle(path2_btn);
		p17.add(path2_btn);
		topPanel.add(p17);
		setHelpTypeTemp=(String)setHelpType_cb.getSelectedItem();
		setHelpData();*/
		
		return topPanel;
	}
	/*
	 * 用以获取并设定选中救助类型的参数
	 */
	private void setHelpData(){
//		setHelpTypeTemp
		
		
		DecimalFormat df=new DecimalFormat("#.00");
		if(Double.parseDouble(df.format(set.getHelpScale((setHelpTypeTemp))))<100){
			helpScale_tf.setText(""+Double.parseDouble(df.format(set.getHelpScale((setHelpTypeTemp)))));
		}else{
			helpScale_tf.setText(""+Double.parseDouble(df.format(set.getHelpScale((setHelpTypeTemp)))));
			helpScale_tf.setEnabled(false);
			String s[]={"阶梯比例","固定比例"};
			ladder_cb.setModel(new DefaultComboBoxModel<String>(s));
			InputBill ib = new InputBill();
			LadderScale = ib.getLadderScale(Integer.toString(((int)Double.parseDouble(helpScale_tf.getText()))));
		}
		startPay_tf.setText(""+Double.parseDouble(df.format(set.getStartPay((setHelpTypeTemp)))));
		endPay_tf.setText(""+Double.parseDouble(df.format(set.getEndPay((setHelpTypeTemp)))));
		moreUse_cb.setModel(new DefaultComboBoxModel<String>(set.getMoreUse(setHelpTypeTemp)));
		//		下来选项时间
		/*path1_tf.setText(set.getPath1());
		path2_tf.setText(set.getPath2());*/
	}
	/*
	 * 用以生成文件
	 */
	/*private String getFile(String path){
		JFileChooser jfc=new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showDialog(new JLabel(),"选择");
		File file=jfc.getSelectedFile();
		try{
			path=file.getPath();
			message=1;
		}catch(Exception e){
			
		}
		return path;
	}*/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*if(e.getSource()==path1_btn){
			String path=getFile(set.getPath1());
			path1_tf.setText(path);
			set.setPath1(path);
			if(message==1){
				JOptionPane.showMessageDialog(this, "路径设置成功");
				message=0;
			}
		}
		if(e.getSource()==path2_btn){
			String path=getFile(set.getPath2());
			path2_tf.setText(path);
			set.setPath2(path);
			if(message==1){
				JOptionPane.showMessageDialog(this, "路径设置成功");
				message=0;
			}
		}*/
		if(e.getSource()==ladder_cb){
			if(((String)ladder_cb.getSelectedItem()).equals("阶梯比例")){
				helpScale_tf.setEnabled(false);
				mainJPanel.setVisible(false);
				DecimalFormat df=new DecimalFormat("#.00");
				LadderScaleGUI lsg = new LadderScaleGUI(userName, (String)helpType_cb.getSelectedItem(),
						Double.parseDouble(df.format(set.getHelpScale((setHelpTypeTemp)))),
						Double.parseDouble(startPay_tf.getText()),Double.parseDouble(endPay_tf.getText()),
						(String) moreUse_cb.getSelectedItem(),LadderScale,mainJPanel,this);
				this.add(lsg,BorderLayout.CENTER);
				this.repaint();
			}else{
				helpScale_tf.setEnabled(true);
			}
		}
		if(e.getSource()==del_town_btn){
			if(JOptionPane.showConfirmDialog(this, "您确定要删除此条目？")==JOptionPane.YES_OPTION){
				set.del_town((String)town_cb.getSelectedItem());
				JOptionPane.showMessageDialog(this, "删除成功");
				town_cb.setModel(new DefaultComboBoxModel(set.getTown_dec()));
			}
		}
		if(e.getSource()==add_town_btn){
			if(!(town_tf.getText().contains(" ")||town_tf.getText().equals("")||null==town_tf.getText())){
				if(set.add_town(town_tf.getText())){
					JOptionPane.showMessageDialog(this, "添加成功");
					town_cb.setModel(new DefaultComboBoxModel(set.getTown_dec()));
					town_tf.setText("");
				}else{
					JOptionPane.showMessageDialog(this, "未知错误，添加失败！");
				}
			}else{
				JOptionPane.showMessageDialog(this, "输入数据中含有空格或未输入数据！");
			}
			
		}
		if(e.getSource()==del_safeType_btn){
			if(JOptionPane.showConfirmDialog(this, "您确定要删除此条目？")==JOptionPane.YES_OPTION){
				set.del_safeType((String)safeType_cb.getSelectedItem());
				JOptionPane.showMessageDialog(this, "删除成功");
				safeType_cb.setModel(new DefaultComboBoxModel(set.getSafeType_dec()));
			}
		}
		if(e.getSource()==add_safeType_btn){
			if(!(safeType_tf.getText().contains(" ")||safeType_tf.getText().equals("")||null==safeType_tf.getText())){
				if(set.add_safeType(safeType_tf.getText())){
					JOptionPane.showMessageDialog(this, "添加成功");
					safeType_cb.setModel(new DefaultComboBoxModel(set.getSafeType_dec()));
					safeType_tf.setText("");
				}else{
					JOptionPane.showMessageDialog(this, "未知错误，添加失败！");
				}
			}else{
				JOptionPane.showMessageDialog(this, "输入数据中含有空格！");
			}
		}
		if(e.getSource()==del_helpType_btn){
			if(JOptionPane.showConfirmDialog(this, "您确定要删除此条目？")==JOptionPane.YES_OPTION){
				set.del_helpType((String)helpType_cb.getSelectedItem());
				JOptionPane.showMessageDialog(this, "删除成功");
				helpType_cb.setModel(new DefaultComboBoxModel(set.getHelpType_dec()));
				setHelpType_cb.setModel(new DefaultComboBoxModel(set.getHelpType_dec()));
			}else{
				JOptionPane.showMessageDialog(this, "未知错误，添加失败！");
			}
		}
		if(e.getSource()==add_helpType_btn){
			if(!(helpType_tf.getText().contains(" ")||helpType_tf.getText().equals("")||null==helpType_tf.getText())){
				if(set.add_helpType(helpType_tf.getText())){
					JOptionPane.showMessageDialog(this, "添加成功");
					helpType_cb.setModel(new DefaultComboBoxModel(set.getHelpType_dec()));
					setHelpType_cb.setModel(new DefaultComboBoxModel(set.getHelpType_dec()));
					helpType_tf.setText("");
				}
			}else{
				JOptionPane.showMessageDialog(this, "输入数据中含有空格！");
			}
		}
		if(e.getSource()==submit_btn){
			try{
				Double.parseDouble(helpScale_tf.getText());
				double startPay=Double.parseDouble(startPay_tf.getText());
				double endPay=Double.parseDouble(endPay_tf.getText());
				if(((String)ladder_cb.getSelectedItem()).equals("固定比例")&&(Double.parseDouble(helpScale_tf.getText())>=0||Double.parseDouble(helpScale_tf.getText())<=100)){
					set.updateHelpData((String)setHelpType_cb.getSelectedItem(), helpScale_tf.getText(), startPay_tf.getText(), endPay_tf.getText(), (String)moreUse_cb.getSelectedItem());
//					选择执行的方法
					JOptionPane.showMessageDialog(this, "修改成功");
				}else if(((String)ladder_cb.getSelectedItem()).equals("阶梯比例")&&Double.parseDouble(helpScale_tf.getText())>100){
					set.updateHelpData((String)setHelpType_cb.getSelectedItem(), helpScale_tf.getText(), startPay_tf.getText(), endPay_tf.getText(), (String)moreUse_cb.getSelectedItem());
//					选择执行的方法
					JOptionPane.showMessageDialog(this, "修改成功");
				}else {
					JOptionPane.showMessageDialog(this, "输入的值不合法！");
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "输入的值不合法！");
			}
		}
		if(e.getSource()==reset_btn){
			if(JOptionPane.showConfirmDialog(this, "您确定要放弃此更改？")==JOptionPane.YES_OPTION){
				setHelpData();
			}
		}
		if(e.getSource()==setHelpType_cb){
			setHelpTypeTemp=(String)setHelpType_cb.getSelectedItem();
			setHelpData();
		}
	}
}
