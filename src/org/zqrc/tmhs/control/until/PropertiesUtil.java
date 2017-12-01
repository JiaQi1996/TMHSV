package org.zqrc.tmhs.control.until;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

/** 
 * <pre>
 * project : com.olymtech.weixin
 * path : com.olymtech.weixin.util.PropertiesUtil.java
 * class description : properties工具类
 * </pre>
 * @date 2014年10月15日下午5:52:52
 *
 */
public class PropertiesUtil {
	static PropertiesUtil pu;// 创建对象pu
	private static Hashtable register = new Hashtable();
	private static Logger log = Logger.getLogger(PropertiesUtil.class);

	private PropertiesUtil() {
		super();
	}

	/**
	 * 取得PropertiesUtil的一个实例
	 */
	public static PropertiesUtil getInstance() {
		if (pu == null)
			pu = new PropertiesUtil();
		return pu;
	}

	/**
	 * 读取配置文件
	 */
	@SuppressWarnings("unchecked")
	public Properties getProperties(String fileName) {
		InputStream is = null;
		Properties p = null;
		try {
			p = (Properties) register.get(fileName);
			if (p == null) {
				try {
					is = new FileInputStream(fileName);
				} catch (Exception e) {
					if (fileName.startsWith("/"))
						is = PropertiesUtil.class.getResourceAsStream(fileName);
					else
						is = PropertiesUtil.class.getResourceAsStream("/" + fileName);
				}
				if (is == null) {
					log.info("未找到名称为" + fileName + "的资源！");
				}
				p = new Properties();
				p.load(is);
				register.put(fileName, p);
				is.close();
			}
		} catch (Exception e) {
			log.error("读取properties时异常", e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					log.error("读取properties关闭流时异常", e);
				}
		}
		return p;
	}

	public String getPropertyValue(String fileName, String strKey) {
		Properties p = getProperties(fileName);
		try {
			return p.getProperty(strKey);
		} catch (Exception e) {
			log.error("读取properties时异常", e);
		}
		return null;
	}

}
