package org.zqrc.tmhs.view.method;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

/*
 * 此类用以生成时钟
 */
public class GetTime extends Thread{
	public JTextArea clock;
	public GetTime(JTextArea clock){
		this.clock=clock;
	}
	public void run(){
		while(true){
			clock.setText(getDate());
			try{
				Thread.sleep(1000);
			}catch (Exception e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}
	}
	public String getDate(){
		String time=null;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日\nEEEE\nHH:mm:ss");
		time=sf.format(new Date());
		return time;
	}
}