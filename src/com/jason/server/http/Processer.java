/**
 * <li>文件名：AdProcesser.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.server.http;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jason.server.Response;
import com.jason.server.Client;
import com.jason.server.hander.ProcessHandler;
import com.jason.server.http.biz.Constants;
import com.jason.server.http.biz.Service;

/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
public class Processer extends ProcessHandler {

	public void process(Client sockector) {
		ConcurrentLinkedQueue<HashMap<String,String>> msgs = sockector.getBizObjects();
		if(msgs.isEmpty()){
			return;
		}
		
		HashMap<String,String> msg = msgs.poll();
		while(msg != null){
			msg.put(Constants.FILED_IP, sockector.getIp());
			Response rm = Service.getInstance().service(msg);
			if(rm != null){
				sockector.addResponseMsg(rm);
			}
			
			msg = msgs.poll();
		}
	}

}
