package org.zqrc.tmhs.control.until;

/**
 * 人民币转换
 * @project TMHS
 * @DescList org.zqrc.tmhs.util
 * @author 李志飞
 *
 * @Date 2016-10-2
 * @UpDate 2016
 */
public class RMBTransForm {
	//壹、贰、叁、肆、伍、陆、柒、捌、玖		拾、佰、仟、万、亿		元、角、分、零、整（正）
//private static String[] NUM={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
//private static String[] MP={"拾","佰","仟","万","亿"};
//private static String[] UNIT={"元","角","分"};
	
	/**
	 * 人民币大写转换，最大转换面额：千亿
	 * @param rmb
	 * @return
	 */
	public static String toUP(String rmb){
		String[]str=rmb.split("\\.");
		if(str.length==1){
			String[] temp={str[0],""};
			str=temp;
		}
		return transUPMax(str[0])+""+transUPMin(str[1]);
	}
	
	private static String transUPMax(String str){
		String temp="";
		int len=str.length();
		int j=0;
		for(int i=len;i>0;i--){
			switch (i) {
			case 1:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"元";
				}else{
					temp+="元";
				}
				break;
			case 2:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"拾";
				}
				break;
			case 3:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"佰";
				}
				break;
			case 4:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"仟";
				}
				break;
			case 5:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"万";
				}else{
					temp+="万";
				}
				break;
			case 6:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"拾";
				}
				break;
			case 7:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"佰";
				}
				break;
			case 8:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"仟";
				}
				break;
			case 9:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"亿";
				}else{
					temp+="亿";
				}
				break;
			case 10:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"拾";
				}
				break;
			case 11:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"佰";
				}
				break;
			case 12:
				if(!getChar(str.charAt(j)).equals("零")){
					temp+=getChar(str.charAt(j))+"仟";
				}
				break;
			default:
				break;
			}
			j++;
		}
		return temp;
	}
	
	private static String transUPMin(String str){
		String temp="";
		int len=str.length();
		for(int i=0;i<=len;i++){
			switch (i) {
			case 1:
				if(!getChar(str.charAt(i-1)).equals("零")){
					temp+=getChar(str.charAt(i-1))+"角";
				}
				break;
			case 2:
				if(!getChar(str.charAt(i-1)).equals("零")){
					temp+=getChar(str.charAt(i-1))+"分";
				}
				break;
			default:
				break;
			}
		}
		if(temp.equals("")){
			temp="整";
		}
			return temp;
	}
	
	private static String getChar(char str){
		String temp="";
		switch (str) {
		case '1':temp="壹";
			break;
		case '2':temp="贰";
			break;
		case '3':temp="叁";
			break;
		case '4':temp="肆";
			break;
		case '5':temp="伍";
			break;
		case '6':temp="陆";
			break;
		case '7':temp="柒";
			break;
		case '8':temp="捌";
			break;
		case '9':temp="玖";
			break;
		case '0':temp="零";
			break;
		default:
			break;
		}
		return temp;
	}
}
