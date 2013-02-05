/**
 * <li>文件名：TypeConver.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-5-13
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <li>类型名称：
 * <li>说明：类型转换工具，将字符串转换为指定类型的变量
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-5-13
 * <li>修改人： 
 * <li>修改日期：
 */
public class TypeConver {
	public static String JSTRING  = "java.lang.String";//
	public static String JINTEGER  = "java.lang.Integer";//
	public static String JBOOLEAN  = "java.lang.Boolean";//
	public static String JSHORT  = "java.lang.Short";
	public static String JDOUBLE  = "java.lang.Double";//
	public static String JLONG  = "java.lang.Long";//
	public static String JDATE  = "java.util.Date";//
	
	//根据指定类型进行转换
	public static Object conver(String str,Class type){
		if(JSTRING.equalsIgnoreCase(type.getName())){
			return str;
		}else if(JINTEGER.equalsIgnoreCase(type.getName())){
			return stoInteger(str);
		}else if(JDATE.equalsIgnoreCase(type.getName())){
			return stoDate(str);
		}else if(JDOUBLE.equalsIgnoreCase(type.getName())){
			return stoDouble(str);
		}else if(JBOOLEAN.equalsIgnoreCase(type.getName())){
			return stoBoolean(str);
		}else if(JLONG.equalsIgnoreCase(type.getName())){
			return stoLong(str);
		}else if(JSHORT.equalsIgnoreCase(type.getName())){
			return stoShort(str);
		}
		
		return str;
	}
	
	public static String nullOfString(String str){
		if(str==null){ 
			str = ""; 
		}
		return str; 
	} 

	public static byte stringToByte(String str){ 
		byte b = 0; 
		if(str!=null){ 
			try{ 
				b = Byte.parseByte(str);				
			}catch(Exception e){ 
	
			} 
		} 
		return b; 
	} 

	public static Boolean stoBoolean(String str){ 
		if(str==null){ 
			return false; 
		}else{ 
			if(str.equals("1")){ 
				return true; 
			}else if(str.equals("0")){
				return false; 
			}else{ 
				try{ 
					return Boolean.parseBoolean(str); 
				}catch(Exception e){ 
					return null; 
				} 
			} 
		} 
	} 

	public static Integer stoInteger(String str){ 
		Integer i=0; 
		if(str!=null){ 
			try{ 
				i = Integer.parseInt(str.trim()); 
			}catch(Exception e){ 
				i = null; 
			} 
		}else{ 
			i = null; 
		} 
		return i; 
	}
	
	public static Short stoShort(String str){ 
		Short i=0; 
		if(str!=null){ 
			try{ 
				i = Short.parseShort(str.trim()); 
			}catch(Exception e){ 
				i = null; 
			} 
		}else{ 
			i = null; 
		} 
		return i; 
	} 

	public static Double stoDouble(String str){
		Double i=0.0; 
		if(str!=null){ 
			try{ 
				i = Double.parseDouble(str.trim()); 
			}catch(Exception e){ 
				i = null; 
			} 
		}else{ 
			i = null; 
		} 
		return i; 
	}
	
	public static Long stoLong(String str){
		Long i= null; 
		if(str!=null){ 
			try{ 
				i = Long.parseLong(str.trim()); 
			}catch(Exception e){ 
				i = null; 
			} 
		}else{ 
			i = null; 
		} 
		return i; 
	}
	
	public static Date stoDate(String str){
		Date i = null;
		if(str!=null){ 
			try{ 
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				i = format.parse(str);
				new Date();
			}catch(Exception e){ 
				i = null; 
			} 
		}else{ 
			i = null; 
		} 
		return i; 
	}

	public static String intToString(int i){ 
		String str = ""; 
		try{ 
			str = String.valueOf(i); 
		}catch(Exception e){ 
			str = ""; 
		} 
		return str; 
	} 


	public static long doubleToLong(double d){ 
		long lo=0; 
		try{ 
//		double转换成long前要过滤掉double类型小数点后数据 
			lo = Long.parseLong(String.valueOf(d).substring(0,String.valueOf(d).lastIndexOf("."))); 
		}catch(Exception e){ 
			lo=0; 
		} 
		return lo; 
	} 

	public static int doubleToInt(double d){ 
		int i=0; 
		try{ 
//		double转换成long前要过滤掉double类型小数点后数据 
			i = Integer.parseInt(String.valueOf(d).substring(0,String.valueOf(d).lastIndexOf("."))); 
		}catch(Exception e){ 
			i=0; 
		} 
		return i; 
	}

	public static double longToDouble(long d){ 
		double lo=0; 
		try{ 
			lo = Double.parseDouble(String.valueOf(d)); 
		}catch(Exception e){ 
			lo=0; 
		} 
		return lo; 
	} 

	public static int longToInt(long d){
		int lo=0; 
		try{ 
			lo = Integer.parseInt(String.valueOf(d)); 
		}catch(Exception e){ 
			lo=0; 
		} 
		return lo; 
	}
	
	public static long stringToLong(String str) {
		Long li = new Long(0); 
		try{ 
			li = Long.valueOf(str); 
		}catch(Exception e){ 
//		li = new Long(0); 
		} 
		return li.longValue(); 
	}
	
	public static String longToString(long li) {
		String str = ""; 
		try{ 
			str = String.valueOf(li); 
		}catch(Exception e){ 
		} 
		return str; 
	}
}
