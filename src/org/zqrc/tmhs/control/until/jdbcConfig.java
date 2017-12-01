package org.zqrc.tmhs.control.until;

import java.util.Properties;



public class jdbcConfig {
	public static String[] getConfig(){
		String[]str=new String[2];
		String path= PropertiesUtil.getInstance().getPropertyValue("config.properties","dataPath");
		String updataPath= PropertiesUtil.getInstance().getPropertyValue("config.properties","updataPath");
		str[0]=path;
		str[1]=updataPath;
		return str;
	}
}
