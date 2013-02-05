/**
 * <li>文件名：ResponseStatus.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-5-20
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.server.http.biz;


/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-5-20
 * <li>修改人： 
 * <li>修改日期：
 */
public class Constants {
	// 响应状态码
	public static final String FILE_NOT_EXSIT = "400";//请求的文件不存在：2500	
	
	public static final String FILED_IP = "ip";// ip地址
	public static final String FILED_CLIENT_IP = "clientIp";// ip地址
	
	public static final String FILED_LENGTH = "length";// 文件大小	
	public static final String REQUEST_STREAM = "requestStream";// 请求
	public static final String FILED_FILE_NAME = "fileName";// 文件名
	
	
	public static final String CRLF_STR = "\r\n";// 回车换行
	public static final String DIVIDE_LINE = "\\|\\|";// 双竖分割线
	
	public static final String ACTION_MODE_CLICK = "1";// 点击
	public static final String ACTION_MODE_SHOW = "0";// 展示
	
	public static final String ROOT_DIRECTORY = "mcms/";
	public static final String FILED_TASK_KEY = "taskKey";//时间戳
	public static final String FILED_MSG = "msg";// 消息内容地址
	public static final String HANDSHAKE = "handshake";//握手标识
	
}
