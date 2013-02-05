/**

 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.server.http;

import java.io.IOException;

import com.jason.server.MasterServer;

/**
 * <li>类型名称：
 * <li>说明：广告服务器，负责提供广告信息的下载，广告资源的下载，广告统计数据的上传等。
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
public class Server{

	/**
	 * <li>方法名：main
	 * <li>@param args
	 * <li>返回类型：void
	 * <li>说明：http服务器，设置httpdecoder,httpProcess,httpcoder给服务器
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-11-18
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static void main(String[] args){
		try{
			new MasterServer(new HttpCoder(), new HttpDecoder(), new Processer());
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
}
