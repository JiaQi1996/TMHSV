package org.zqrc.tmhs.view.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


//import org.zqrc.tmhs.view.main.panel.InputBill;
import org.zqrc.tmhs.view.login.LoginGUI;
import org.zqrc.tmhs.view.main.centerPanel.historyBill.HistoryBillGUI;
import org.zqrc.tmhs.view.main.centerPanel.inputBill.InputBillGUI;
import org.zqrc.tmhs.view.main.centerPanel.setSystemData.SetSystemDataGUI;
import org.zqrc.tmhs.view.main.centerPanel.setUser.SetUserGUI;
import org.zqrc.tmhs.view.method.GetTime;

/*
 * By Gorden @ 2016-10-2
 * 此类主要用于生成主页面框架
 */

public class MainFrame extends JFrame implements ComponentListener,ActionListener{
	private String userName,name,role;
	private JPanel topPanel;//顶部面板
	private JPanel footPanel;//底部面板
	private JPanel infoPanel;//次顶部面板
	private JPanel centerPanel;//菜单面板+主面板
	private JPanel menuPanel;
	private JPanel mainPanel;
	private JLabel copyRight;
	private JTextArea clock;
	private JPanel userPanel;
	private JButton b1,b2,b3,b4,b5,b6;//b1到b7为菜单的第一个按钮到最后一个按钮
	private int message=0;
	/*
	 * 构造函数用以设置窗口的整体属性
	 */
	public MainFrame(String userName,String name,String role){
		super();
		this.userName=userName;
		this.name=name;
		this.role=role;
		this.setTitle("欢迎使用—城乡医疗救助结算系统");
		this.setSize(900,640);
		this.setMinimumSize(new Dimension(800,540));
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		ImageIcon logo=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/minzheng.png"));
		this.setIconImage(logo.getImage());//设置图标为logo
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕大小
		int x=screenSize.width;
		int y=screenSize.height;
		this.setLocation((x-this.getWidth())/2,(y-this.getHeight())/2);
		this.setLayout(null);
		this.setVisible(true);
		this.add(setTopPanel());
		this.add(setInfoJPanel());
		this.add(setFootPanel());
		this.add(setCenterJPanel());
		this.repaint();
		//监听窗口改变事件
		this.addComponentListener(this);
		
	}
	/*
	 * 用以初始化上部面板的位置和大小
	 */
	private JPanel setTopPanel(){
		topPanel=new JPanel();
		topPanel.setBounds(0, 0, this.getWidth(), 100);
		topPanel.setBackground(new Color(255,153,204));
		topPanel.setLayout(null);
		ImageIcon icon=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/minzheng.png"));
		icon.setImage(icon.getImage().getScaledInstance(78, 78, 0));
		JLabel logo=new JLabel(icon);
		logo.setBounds(30,11,78,78);
		this.add(logo);
		JLabel cnTitle=new JLabel("城乡医疗救助结算系统");
		cnTitle.setBounds(118,11,400,53);
		cnTitle.setFont(new Font("微软雅黑",1,40));
		cnTitle.setForeground(Color.white);
		this.add(cnTitle);
		JLabel enTitle=new JLabel("Urban & Rural Medical Assistance System of Settlements");
		enTitle.setBounds(118,65,402,15);
		enTitle.setFont(new Font("微软雅黑",1,14));
		enTitle.setForeground(Color.white);
		this.add(enTitle);
		//电子时钟
		clock=new JTextArea();
		clock.setBounds(this.getWidth()-150,20,122,58);
		clock.setEnabled(false);
		clock.setBackground(new Color(255,153,204));
		clock.setDisabledTextColor(Color.white);
		clock.setFont(new Font("微软雅黑",1,14));
		GetTime time=new GetTime(clock);
		this.add(clock);
		time.start();
		return topPanel;
	}
	/*
	 * 用以初始化次上部面板的位置和大小
	 */
	private JPanel setInfoJPanel(){
		infoPanel=new JPanel();
		infoPanel.setBounds(0, 100, this.getWidth(), 40);
		infoPanel.setBackground(new Color(255,231,255));
		infoPanel.setBorder(BorderFactory.createLineBorder(new Color(255,153,204), 1));
		infoPanel.setLayout(null);
		
		String s="欢迎使用本系统："+name;
		JLabel l1=new JLabel(s);
		l1.setBounds(20, 10, 200, 20);
		l1.setFont(new Font("微软雅黑",1,14));
		infoPanel.add(l1);
		
		userPanel=new JPanel();
		userPanel.setBounds(this.getWidth()-220,1,200,38);
		userPanel.setBackground(null);
		
		ImageIcon icon1=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/user1.png"));
		userPanel.add(new JLabel(icon1));
		JLabel userNameLabel=new JLabel(userName);
		userNameLabel.setFont(new Font("微软雅黑",0,16));
		userPanel.add(userNameLabel);
		
		ImageIcon icon2=new ImageIcon(getClass().getResource("/org/zqrc/tmhs/view/img/role.png"));
		userPanel.add(new JLabel(icon2));
		String role_cn="用户";
		if(role.equals("0"))
			role_cn="管理员";
		JLabel roleNameLabel=new JLabel(role_cn);
		roleNameLabel.setFont(new Font("微软雅黑",0,16));
		userPanel.add(roleNameLabel);
		infoPanel.add(userPanel);
		return infoPanel;
	}
	/*
	 * 用以初始化中央面板的位置和大小
	 */
	private JPanel setCenterJPanel(){
		centerPanel=new JPanel();
		centerPanel.setBounds(0,140,this.getWidth(),this.getHeight()-220);
		centerPanel.setBackground(new Color(255,243,255));
		centerPanel.setLayout(null);
		centerPanel.add(setMenuPanel());
		centerPanel.add(setMainPanel());
		return centerPanel;
	}
	/*
	 * 用以初始化菜单面板
	 */
	private JPanel setMenuPanel(){
		menuPanel=new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBounds(0, 0, 160, centerPanel.getHeight());
		menuPanel.setBackground(new Color(255,243,255));
		JPanel p1=new JPanel();
		p1.setBounds(0,0,160,308);
		p1.setBackground(null);
		p1.setLayout(new GridLayout(7,1));
		b1=new JButton("帐单录入");
		setButtonStyle(b1);
		p1.add(b1);
		b2=new JButton("历史查询");
		setButtonStyle(b2);
		p1.add(b2);
		if(role.equals("0")){
			b3=new JButton("账号管理");
			setButtonStyle(b3);
			p1.add(b3);
			b4=new JButton("参数设置");
			setButtonStyle(b4);
			p1.add(b4);
		}
		b5=new JButton("注销账户");
		setButtonStyle(b5);
		p1.add(b5);
		b6=new JButton("退出系统");
		setButtonStyle(b6);
		p1.add(b6);
		menuPanel.add(p1);
		return menuPanel;
	}
	/*
	 * 设置菜单按钮形状
	 */
	private void setButtonStyle(JButton b){
		b.setBackground(new Color(255,153,204));
		b.setFont(new Font("微软雅黑",1,18));
		b.setForeground(Color.white);
		b.setBorder(BorderFactory.createLineBorder(Color.black,1));
		b.addActionListener(this);
	}
	/*
	 * 用以初始化主功能区面板
	 */
	private JPanel setMainPanel(){
		mainPanel=new InputBillGUI(userName);
		mainPanel.setBounds(161,1,centerPanel.getWidth()-170,centerPanel.getHeight()-1);
		mainPanel.setBackground(new Color(255,250,250));
		mainPanel.setBorder(BorderFactory.createLineBorder(new Color(255,100,100),1));
		return mainPanel;
	}
	/*
	 * 用以初始化底部区域面面板的位置和大小
	 */
	private JPanel setFootPanel(){
		footPanel=new JPanel();
		footPanel.setBounds(0,this.getHeight()-80,this.getWidth(),40);
		footPanel.setBackground(new Color(255,231,255));
		footPanel.setBorder(BorderFactory.createLineBorder(new Color(255,153,204), 1));
		footPanel.setLayout(null);
		copyRight=new JLabel("© 2016-2017 Software Innovator");
		copyRight.setFont(new Font("微软雅黑",0,12));
		copyRight.setBounds((this.getWidth()-200)/2,10,200,20);
		footPanel.add(copyRight);
		return footPanel;
	}
	/*
	 * 当窗口发生改变时重绘面板
	 */
	private void reSize(){
		topPanel.setBounds(0, 0, this.getWidth(), 100);
		infoPanel.setBounds(0, 100, this.getWidth(),40);
		centerPanel.setBounds(0,140,this.getWidth(),this.getHeight()-220);
		footPanel.setBounds(0,this.getHeight()-80,this.getWidth(),40);
		copyRight.setBounds((this.getWidth()-200)/2,10,200,20);
		clock.setBounds(this.getWidth()-150,20,122,58);
		userPanel.setBounds(this.getWidth()-220,1,200,38);
		menuPanel.setBounds(0, 0, 160, centerPanel.getHeight());
		mainPanel.setBounds(161,1,centerPanel.getWidth()-177,centerPanel.getHeight()-1);
		repaint();
	}
	/*
	 * 测试方法
	 */
/*	public static void main(String args[]){
		new MainFrame("admin","超级管理员","0");
	}*/
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		reSize();
	}

	public void componentMoved(ComponentEvent e) {
		reSize();
	}

	public void componentShown(ComponentEvent e) {
		reSize();
	}

	public void componentHidden(ComponentEvent e) {
		reSize();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1){
			mainPanel.setVisible(false);
			mainPanel.removeAll();
			mainPanel.add(new InputBillGUI(userName));
			mainPanel.setVisible(true);
		}
		if(e.getSource()==b2){
			mainPanel.setVisible(false);
			mainPanel.removeAll();
			mainPanel.add(new HistoryBillGUI(userName,role));
			mainPanel.setVisible(true);
			
		}
		if(e.getSource()==b3){
			mainPanel.setVisible(false);
			mainPanel.removeAll();
			mainPanel.add(new SetUserGUI(userName));
			mainPanel.setVisible(true);
		}
		if(e.getSource()==b4){
			mainPanel.setVisible(false);
			mainPanel.removeAll();
			mainPanel.add(new SetSystemDataGUI(userName));
			mainPanel.setVisible(true);
		}
		if(e.getSource()==b5){
			if(JOptionPane.showConfirmDialog(this, "您确定要注销本帐户？")==JOptionPane.YES_OPTION){
				new LoginGUI();
				this.setVisible(false);
				this.removeAll();
				try {
					this.finalize();
					System.gc();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource()==b6){
			if(JOptionPane.showConfirmDialog(this, "您确定要退出本系统？")==JOptionPane.YES_OPTION){
				System.exit(0);
			}	
		}
	}
}
