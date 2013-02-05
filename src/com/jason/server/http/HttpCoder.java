/**
 * <li>文件名：HttpCoder.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.server.http;

import java.io.IOException;
import java.util.Iterator;

import com.jason.server.Response;
import com.jason.server.Client;
import com.jason.server.hander.CoderHandler;

/**
 * <li>类型名称：
 * <li>说明：http协议响应编码器
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
public class HttpCoder extends CoderHandler {	
	public void process(Client sockector) {
		Response msg = sockector.getResponseMsgsNotCode().poll();		
		while(msg != null){
			createHeader(msg);// 创建响应头部信息
			msg.bufferedContent();// 缓存内容
			sockector.getResponseMsgs().add(msg);			
			
			msg = sockector.getResponseMsgsNotCode().poll();
		}
	}
	
	/**
	 * 
	 * <li>方法名：createHeader
	 * <li>
	 * <li>返回类型：void
	 * <li>说明：
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-11-19
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	private void createHeader(Response msg){
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.0 200 OK \r\n")
		.append("\r\n");
		
		msg.setHeader(sb.toString());// 设置头部信息
	}

}
