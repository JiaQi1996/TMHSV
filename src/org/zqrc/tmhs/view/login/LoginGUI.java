package org.zqrc.tmhs.view.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalBorders.OptionDialogBorder;
import javax.xml.ws.handler.MessageContext;

import org.zqrc.tmhs.view.main.MainFrame;


/*
 * By Gorden @ 2016-10-1
 * 此类主要实现登录功能的GUI，同时兼具整个应用的入口。
 *  V4.1升级说明
 *  V4.1 修改时间 2017-06-19，本次升级需要增添经办人打印，以及为票据增加“困难群众大病补充保险”
 *  和“其它报销”。救助金额计算需要加入阶梯计算公式。
 */

public class LoginGUI extends JFrame implements MouseListener,FocusListener{
	private String userName,passWord;//定义用户名和密码
	private JTextField user_JTF;//定义用户名输入框
	private JTextField passWord_JPF;//定义密码输入框
	private JButton login_JBT;//定义登录按钮
	private JPanel loginPanel;
	
	/*
	 * 构造函数用来设置整个页面的属性
	 */
	public LoginGUI(){
		super();
		this.setTitle("城乡医疗救助结算系统-登录");
		this.setSize(657, 410);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		ImageIcon logo=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/minzheng.png"));
		this.setIconImage(logo.getImage());//设置图标为logo
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕大小
		int x=screenSize.width;
		int y=screenSize.height;
		this.setLocation((x-657)/2,(y-404)/2);
		this.setResizable(false);
		this.setLayout(null);
		this.add(topPanel());
		this.add(underPanel());
		this.setVisible(true);
	}
	/*
	 * topPanel方法用来创建登陆界面顶部
	 */
	private JPanel topPanel(){
		JPanel topPanel=new JPanel();
		topPanel.setBounds(0, 0, 657, 87);
		topPanel.setBackground(new Color(255,153,204));
		topPanel.setLayout(null);
		//设置logo
		ImageIcon logo=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/minzheng.png"));
		logo.setImage(logo.getImage().getScaledInstance(77, 77, 0));
		JLabel l1=new JLabel(logo);
		l1.setBounds(22, 6, 77, 77);
		topPanel.add(l1);
		//设置汉文标题
		JLabel l2=new JLabel("城乡医疗救助结算系统");
		l2.setBounds(109, 6, 400, 51);
		l2.setFont(new Font("微软雅黑", 1, 40));
		l2.setForeground(Color.white);
		topPanel.add(l2);
		//设置英文标题
		JLabel l3=new JLabel("Urban & Rural Medical Assistance System of Settlements");
		l3.setBounds(108, 57, 402, 19);
		l3.setFont(new Font("微软雅黑", 1, 14));
		l3.setForeground(Color.white);
		topPanel.add(l3);
		return topPanel;
	}
	/*
	 * underPanel方法用以生成下侧的面板
	 */
	private JPanel underPanel(){
		JPanel underPanel=new JPanel();
		underPanel.setBounds(0,87,657,323);
		underPanel.setBackground(new Color(255,250,255));
		underPanel.setLayout(null);
		//设置版权信息
		JLabel l1=new JLabel("© 2016-2017 Software Innovator");
		l1.setBounds(240, 275, 200, 16);
		l1.setForeground(Color.black);
		l1.setFont(new Font("微软雅黑",0,12));
		underPanel.add(l1);
		//设置版本信息
		JLabel l2=new JLabel("V4.1");
		l2.setBounds(604, 271, 43, 24);
		l2.setForeground(new Color(60,60,60));
		l2.setFont(new Font("微软雅黑",1,18));
		underPanel.add(l2);
		
		underPanel.add(loginPanel());
		underPanel.add(tipsPanel());
		
		return underPanel;
	}
	/*
	 * loginPanel方法用以生成登录面板
	 */
	private JPanel loginPanel(){
		loginPanel=new JPanel();
		loginPanel.setBounds(333,16,310,250);
		loginPanel.setBackground(null);
		loginPanel.setBorder(BorderFactory.createLineBorder(new Color(255,153,204),1));
		loginPanel.setBackground(new Color(255,243,255));
		loginPanel.setLayout(null);
		JTextField t1=new JTextField();
		this.add(t1);
		//标题
		JLabel l1=new JLabel("登录系统");
		l1.setBounds(55, 20, 96, 32);
		l1.setFont(new Font("微软雅黑",1,24));
		l1.setForeground(new Color(60,60,60));
		loginPanel.add(l1);
		//用户名
		user_JTF=new JTextField("请输入账号");
		user_JTF.setBounds(55,71,194,35);
		user_JTF.setFont(new Font("微软雅黑",0,15));
		loginPanel.add(user_JTF);
		user_JTF.addFocusListener(this);
		//密码
		passWord_JPF=new JTextField("请输入密码");
		passWord_JPF.setBounds(55,134,194,35);
		passWord_JPF.setFont(new Font("微软雅黑",0,15));
		loginPanel.add(passWord_JPF);
		passWord_JPF.addFocusListener(this);
		//登录按钮
		login_JBT=new JButton("登    录");
		login_JBT.setBounds(55,197,142,36);
		login_JBT.setBorder(BorderFactory.createLineBorder(new Color(60,60,60),1));
		login_JBT.setBackground(new Color(255,153,204));
		login_JBT.setForeground(Color.white);
		login_JBT.setFont(new Font("微软雅黑",1,18));
		loginPanel.add(login_JBT);
		login_JBT.addMouseListener(this);
		//用户图片
		ImageIcon icon1=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/user.png"));
		JLabel l2=new JLabel(icon1);
		l2.setBounds(20,70,35,35);
		loginPanel.add(l2);
		//密码图片
		ImageIcon icon2=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/paw.png"));
		JLabel l3=new JLabel(icon2);
		l3.setBounds(20,134,35,35);
		loginPanel.add(l3);
		return loginPanel;
	}
	/*
	 * tipsPanel方法用以生成提示面板
	 */
	private JPanel tipsPanel(){
		JPanel tipsPanel=new JPanel();
		tipsPanel.setBounds(13,16,310,250);
		tipsPanel.setBackground(null);
		tipsPanel.setLayout(null);
		tipsPanel.setBorder(BorderFactory.createLineBorder(new Color(255,153,204),1));
		tipsPanel.setBackground(new Color(255,243,255));
		
		JPanel tips=new JPanel();
		tips.setLayout(new GridLayout(8, 3));
		tips.setBackground(null);
		tips.setBounds(40, 30, 260, 210);
		tips.add(tips("牢记宗旨"));
		tips.add(tips("规范操作"));
		tips.add(tips(""));
		tips.add(tips(""));
		tips.add(tips("方便群众"));
		tips.add(tips("优质高效"));
		tips.add(tips(""));
		tips.add(tips(""));
		tips.add(tips("慈善关爱"));
		tips.add(tips("排忧解难"));
		tips.add(tips(""));
		tips.add(tips(""));
		tips.add(tips("首问负责"));
		tips.add(tips("勤政廉洁"));
		tipsPanel.add(tips);
		return tipsPanel;
	}
	private JLabel tips(String tip){
		JLabel tips=new JLabel(tip);
		tips.setForeground(Color.RED);
		tips.setFont(new Font("华文行楷",1,24));
		return tips;
	}
	/*
	 * login方法用以检测输入数据是否合法，并负责和逻辑层代码进行通信
	 */
	public void login(String userName,String password){
		Login l=new Login(userName, password);
		if(l.isTure()){
			new MainFrame(userName, l.getName(), l.getRole());
			this.setVisible(false);
			this.removeAll();
			try {
				this.finalize();
				System.gc();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "您输入的账号或密码有误,请重新输入！","系统提示",JOptionPane.ERROR_MESSAGE);
		}
	}
	/*
	 *main方法为整个程序的入口
	 */
	public static void main(String args[]){
		new LoginGUI();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		userName=user_JTF.getText();
		passWord=passWord_JPF.getText();
		//当监听到鼠标点击按钮时
		if(e.getSource()==login_JBT){
			if(!(userName.equals("请输入账号")||userName.equals("")||passWord.equals("请输入密码")||passWord.equals(""))){
				login(userName,passWord);
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
		if(e.getSource()==login_JBT){
			login_JBT.setBackground(new Color(255,183,234));
			this.setCursor(HAND_CURSOR);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==login_JBT){
			login_JBT.setBackground(new Color(255,153,204));
			this.setCursor(DEFAULT_CURSOR);
		}
	}
	public void focusGained(FocusEvent e) {
		userName=user_JTF.getText();
		passWord=passWord_JPF.getText();
		//当监听到账户框获取焦点时
		if(e.getSource()==user_JTF){
			if(userName.equals("请输入账号")){
				user_JTF.setText("");
			}
			if(passWord.equals("")){
				passWord_JPF.setVisible(false);
				passWord_JPF=new JTextField("请输入密码");
				passWord_JPF.setBounds(55,134,194,35);
				passWord_JPF.setFont(new Font("微软雅黑",0,15));
				loginPanel.add(passWord_JPF);
				passWord_JPF.addFocusListener(this);
				repaint();
			}
		}
		//当监听到密码框获取焦点时
		if(e.getSource()==passWord_JPF){
			if(passWord.equals("请输入密码")){
				passWord_JPF.setVisible(false);
				passWord_JPF=new JPasswordField("");
				passWord_JPF.setBounds(55,134,194,35);
				passWord_JPF.setFont(new Font("微软雅黑",0,15));
				loginPanel.add(passWord_JPF);
				passWord_JPF.addFocusListener(this);
				passWord_JPF.requestFocus(true);
				repaint();
			}
			if(userName.equals("")){
				user_JTF.setText("请输入账号");
			}
		}
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		userName=user_JTF.getText();
		passWord=passWord_JPF.getText();
		// TODO Auto-generated method stub
		//当监听到用户框失去焦点时
		if(e.getSource()==user_JTF){
			if(userName.equals("")){
				user_JTF.setText("请输入账号");
			}
		}
		//当监听到密码框失去焦点时
		if(e.getSource()==passWord_JPF){
			if(passWord.equals("")){
				passWord_JPF.setVisible(false);
				passWord_JPF=new JTextField("请输入密码");
				passWord_JPF.setBounds(55,134,194,35);
				passWord_JPF.setFont(new Font("微软雅黑",0,15));
				loginPanel.add(passWord_JPF);
				passWord_JPF.addFocusListener(this);
				repaint();
			}
		}
	}

}
