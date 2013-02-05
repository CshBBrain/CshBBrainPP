/**
 * <li>文件名：UUID.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-12-13
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.util;

import java.util.UUID;

/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-12-13
 * <li>修改人： 
 * <li>修改日期：
 */
public class GUID {
	/**
	 * 
	 * <li>方法名：getId
	 * <li>@return 
	 * <li>返回类型：String
	 * <li>说明：產生id
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-12-13
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static String getId(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
