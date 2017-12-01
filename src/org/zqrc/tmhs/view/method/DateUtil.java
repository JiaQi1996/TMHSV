package org.zqrc.tmhs.view.method;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取时间相关类
 * @project TMHS
 * @DescList org.zqrc.tmhs.util
 * @author 李志飞
 *
 * @Date 2016-10-1
 * @UpDate 2016
 */
public class DateUtil {
    
	/**
	 * 唯一标识码
	 * @return
	 */
	public static String getDateUUID(){
		Format format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}
	
	/**
	 * 具体时间获取类
	 * @return
	 */
	public static String getDateTime(){
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	/**
	 * 获取自定义静态时间
	 * yyyy年，MM月，dd日，HH时，mm分，ss秒，SSS毫秒
	 * 属性间可通过嵌入字符,达到相应效果
	 * @param format
	 * @return
	 */
	public static String getDateFormat(String format){
		Format f = new SimpleDateFormat(format);
		return f.format(new Date()); 
	}
}
