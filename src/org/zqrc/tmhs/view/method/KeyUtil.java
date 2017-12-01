package org.zqrc.tmhs.view.method;

/**
 * 获取流水号的Key值
 * @project TMHS
 * @DescList org.zqrc.tmhs.util
 * @author 李志飞
 *
 * @Date 2016-10-3
 * @UpDate 2016
 */
public class KeyUtil {
	
	/**
	 * key是唯一的值，0~9和X，
	 * id是21位
	 * 返回22位
	 * @return
	 */
	public static String getKey(String id){
		String num="";
		if(id.trim().length()==17){
			int temp=add(id);
			num=String.valueOf(temp%11);
			switch (Integer.parseInt(num)) {
			case 1:num="1";
				break;
			case 2:num="2";
				break;
			case 3:num="3";
				break;
			case 4:num="4";
				break;
			case 5:num="5";
				break;
			case 6:num="6";
				break;
			case 7:num="7";
				break;
			case 8:num="8";
				break;
			case 9:num="9";
				break;
			case 10:num="X";
				break;
			case 0:num="0";
				break;

			default:
				break;
			}
		}else{
			return "";
		}
		return id+num;
	}
	
	/**
	 * 检查流水账号是否合法
	 * id为22位
	 * @return
	 */
	public static boolean checkKey(String id){
		String num="";
		if(id.trim().length()==18){
			int temp=add(id);
			num=String.valueOf(temp%11);
			switch (Integer.parseInt(num)) {
			case 1:num="1";
				break;
			case 2:num="2";
				break;
			case 3:num="3";
				break;
			case 4:num="4";
				break;
			case 5:num="5";
				break;
			case 6:num="6";
				break;
			case 7:num="7";
				break;
			case 8:num="8";
				break;
			case 9:num="9";
				break;
			case 10:num="X";
				break;
			case 0:num="0";
				break;
			default:
				break;
			}
			if(num.equals(String.valueOf(id.charAt(21)-'0'))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 累加程序
	 * @return
	 */
	private static int add(String str){
		int temp=0;
		if(str.trim().length()==17||str.length()==18){
			for(int i=0;i<17;i++){
				temp+=(str.charAt(i)-'0');
			}
			return temp;
		}else{
			return -1;
		}
	}
	
}
