/**
 * <li>文件名：Service.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-27
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.server.http.biz;

import java.io.File;
import java.util.HashMap;

import com.jason.Config;
import com.jason.server.Response;
import com.jason.server.http.HttpConstants;
import com.jason.util.FileUtil;
import com.jason.util.MyStringUtil;

/**
 * <li>类型名称：
 * <li>说明：业务处理类
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-11-27
 * <li>修改人： 
 * <li>修改日期：
 */
public class Service{
	private static final String VANK_PUBLISH_ARTICLE = "/upload";// 上传图片
	
	public static final String SERVER_ROOT = "serverRoot";// 服务器文件存放的目录
	private static final String serverRoot = Config.getStr(SERVER_ROOT);// 服务器根目录
	private static Service service;// 服务单实例	
	
	static{		
		service= new Service();// 服务单实例
	}
	
	public static Service getInstance(){
		return service;
	}
	
	private Service(){
		
	}
	
	/**
	 * 
	 * <li>方法名：compareService
	 * <li>@param request
	 * <li>@param service
	 * <li>@return
	 * <li>返回类型：Boolean
	 * <li>说明：比较服务器接口
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-6-14
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public Boolean compareService(String request, String service){
		return MyStringUtil.isBlank(request) ? false : (request.startsWith(service) || request.endsWith(service));
	}
	/**
	 * 
	 * <li>方法名：service
	 * <li>@param requestData
	 * <li>@return
	 * <li>返回类型：ResponseMessage
	 * <li>说明：业务处理入口方法，对各种接口的请求进行处理
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-12-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	
	public Response service(HashMap<String, String> requestData){
		if(requestData == null){
			return null;
		}
		
		Response responseMessage = null;
		try{
			String request = requestData.get(HttpConstants.REQUEST);// 获取请求接口
			
			if(compareService(request,VANK_PUBLISH_ARTICLE)){//上传图片
				String fileName = requestData.get("fileName");//文件名
				String fileNames = "";
				if(!MyStringUtil.isBlank(fileName)){//如果文件名不为空情况，证明有附件
					fileNames = FileUtil.unzip(SERVER_ROOT,fileName,true,serverRoot);			
				}
				
				responseMessage = Response.msgOnlyBody(fileNames);
			}else{//下载图片
				responseMessage = cretaeResponse(request);			
			}
			return responseMessage;
		}catch(Exception e){
			e.printStackTrace();
			responseMessage = Response.msgOnlyBody("Error 500");
			return responseMessage;
		}
	}
	
	/**
	 * 
	 * <li>方法名：createFileHeader
	 * <li>@param docName
	 * <li>@return
	 * <li>@throws IOException
	 * <li>返回类型：String
	 * <li>说明：创建普通http文件请求响应头
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2012-2-13
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	private Response cretaeResponse(String docName){		
		File requestedFile = new File(serverRoot,docName);		
		if(requestedFile.exists()){			
			return Response.msgOnlyFile(requestedFile.getAbsolutePath());			
		}else{
			return Response.msgOnlyBody(Constants.FILE_NOT_EXSIT);
		}		
	}
}
