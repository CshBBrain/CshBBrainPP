/**
 * <li>文件名：HttpDecoder.java
 * <li>说明：
 * <li>创建人：CshBBrain;技术博客：http://cshbbrain.iteye.com/
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.server.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jason.Config;
import com.jason.server.FileTransfer;
import com.jason.server.Request;
import com.jason.server.Client;
import com.jason.server.hander.DecoderHandler;
import com.jason.server.http.HttpConstants.HeaderNames;
import com.jason.server.http.HttpConstants.Methods;
import com.jason.server.http.HttpConstants.RetentionWord;
import com.jason.server.http.biz.Constants;
import com.jason.util.MyStringUtil;
import com.jason.util.CoderUtils;

/**
 * <li>类型名称：
 * <li>说明：http协议解码器
 * <li>创建人：CshBBrain;技术博客：http://cshbbrain.iteye.com/
 * <li>创建日期：2011-11-18
 * <li>修改人： 
 * <li>修改日期：
 */
public class HttpDecoder extends DecoderHandler {
	private static Log log = LogFactory.getLog(HttpDecoder.class);// 日志记录器
	public static final String SERVER_ROOT = "serverRoot";// 服务器文件存放的目录
	public static final String serverRoot = Config.getStr(SERVER_ROOT);// 服务器文件存放的目录
	private static final Pattern PARAM_PATTERN = Pattern.compile("([^=]*)=([^&]*)&*");
	
	/**
	 * 
	 * <li>方法名：readFile
	 * <li>@param buffer
	 * <li>@param fileReceiver
	 * <li>@return
	 * <li>返回类型：Boolean
	 * <li>说明：读取文件内容
	 * <li>创建人：CshBBrain;技术博客：http://cshbbrain.iteye.com/
	 * <li>创建日期：2011-12-13
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static Boolean readFile(ByteBuffer buffer, FileTransfer fileReceiver){
		ByteBuffer readBuffer = buffer.asReadOnlyBuffer();
		buffer.limit(50);// 读取50个字节，解析获取内容的长度，内容长度最大为4个FFFF（4个字节），再加上2个字节\r\n
		String msg = CoderUtils.decode(buffer);
		
		if(!MyStringUtil.isBlank(msg)){// 上传文件的请求头	
			if(fileReceiver.isReadHead()){
				fileReceiver.writeBody(readBuffer, 0, fileReceiver.getSizeWithHead());// 读取文件内容
				fileReceiver.setReadHead(false);
				fileReceiver.setSizeWithHead(0);
			}else{
				String packageHead = msg.split((HttpConstants.RetentionWord.CRLF_STR))[0];				
				try{
					Integer length = Integer.parseInt(packageHead, 16);
					Integer btyeLength = packageHead.getBytes().length + 2;// 加上2个回车换行符
					
					fileReceiver.writeBody(readBuffer, btyeLength, length);// 读取文件内容					
				}catch(NumberFormatException nfe){// 读取不带包头信息的文件
					fileReceiver.writeBody(readBuffer, 0);// 读取文件内容
				}

			}
		}
		
		return null;
	}

	public void process(ByteBuffer buffer, Client sockector) {
		Request requestWithFile = sockector.getRequestWithFile();
		
		if(requestWithFile.isReadFile()){// 读取文件内容，并将文件内容写回到文件中
			if(!requestWithFile.getFileReceiver().finishWrite()){
				readFile(buffer, requestWithFile.getFileReceiver());
			}else{
				requestWithFile.setIsReadFile(false);
			}
			return;
		}else if(requestWithFile.getReadData()){
			String msg = CoderUtils.decode(buffer);
			System.out.println(msg);
			buffer.position(0);// 将数据放入数据缓冲区
			buffer.limit();
			requestWithFile.getDatas().put(buffer);
			if(msg.length() < requestWithFile.getDataSizeLeftLong()){//未读取完成				
				requestWithFile.setDataSizeLeftLong(requestWithFile.getDataSizeLeftLong() - msg.length());								
			}else{
				requestWithFile.setReadData(false);
				requestWithFile.setDataSizeLeftLong(0l);
				requestWithFile.getDatas().flip();
				String msgs = CoderUtils.decode(requestWithFile.getDatas());
				if(requestWithFile.getRequestData() == null){
					requestWithFile.setRequestData(new HashMap<String, String>());
				}
				decodeParams(msgs, requestWithFile.getRequestData());
			}
			return;
		}else{
			ByteBuffer readBuffer = buffer.asReadOnlyBuffer();
			//yuanjun update 由于post提交参数过多时，URL数据会被截断
			//buffer.limit(buffer.limit() > 1000 ? 1000 : buffer.limit());
			String msg = CoderUtils.decode(buffer);
			
			System.out.println("访问URL"+MyStringUtil.getDatetime(new Date())+"："+msg);
			if(MyStringUtil.isBlank(msg)){
				return;
			}
			
			String[] request = msg.split(CoderUtils.BLANK_LINE);// 分为header 和body
			String header = null;
			String body = null;
			if(request.length >0 && request.length < 2){
				header = request[0];
			}else if(request.length == 2){
				header = request[0];
				body = request[1];
			}else if(request.length > 2){
				header = request[0];
				StringBuilder sb = new StringBuilder();
				for(int i = 1; i < request.length; ++i){
					sb.append(request[i]);
				}
				body = sb.toString();
			}
			
			String[] headers = header.split(HttpConstants.RetentionWord.CRLF_STR);// 采用空格将请求第一行分裂开
			
			if(headers.length <= 0){
				return;
			}
			
			HashMap<String,String> requestData = new HashMap<String,String>();//requestWithFile.getRequestData();
			//if(requestData == null){
				//requestData = new HashMap<String,String>();
				sockector.addRequest(requestData);
			//}
			
			// process the request uri
			String[] uris = headers[0].split(CoderUtils.BLANK_SPACE);
			if(uris.length >1){
				processUri(uris[1], requestData);
			}
			
			// 获取请求类型，默认为get,判断是否为post
			if(uris[0].equalsIgnoreCase(Methods.POST)){
				for(String str : headers){
					if(str.toLowerCase().startsWith(HeaderNames.CONTENT_TYPE)){// 上传文件
						System.out.println(str);
						String[] lngth = str.split(RetentionWord.COLON_DOT);
						if(lngth.length >= 2){// 处理读取数据的长度
							
							if(HeaderNames.UPLOAD_TYPE.equalsIgnoreCase(lngth[1].trim())){
								requestWithFile.setReadData(true);
								System.out.println("the body is : " + body);
							}
							/*requestWithFile.setDataSizeLeftInt(Integer.valueOf(lngth[1].trim()));
							if(((MyStringUtil.isBlank(body) && requestWithFile.getDataSizeLeftInt() > 0) || (body != null && body.length() < requestWithFile.getDataSizeLeftInt()))){//读取未完成
								requestWithFile.setReadData(true);
								requestWithFile.setDatas(ByteBuffer.allocate(requestWithFile.getDataSizeLeftInt()));
								
								buffer.position(header.getBytes().length + 4);// 将数据放入数据缓冲区
								buffer.limit();
								requestWithFile.getDatas().put(buffer);
								
								requestWithFile.setDataSizeLeftInt(requestWithFile.getDataSizeLeftInt() - (MyStringUtil.isBlank(body) ? 0 : body.length()));								
							}
							System.out.println("the length of data is : " + requestWithFile.getDataSizeLeftInt());*/
						}
					}else if(str.toLowerCase().startsWith(HeaderNames.CONTENT_LENGTH)){
						System.out.println(str);
						String[] lngth = str.split(RetentionWord.COLON_DOT);
						if(lngth.length >= 2){// 处理读取数据的长度							
							requestData.put(Constants.FILED_LENGTH,lngth[1].trim());
							requestWithFile.setDataSizeLeftLong(Long.valueOf(lngth[1].trim()));
							System.out.println("the length of data is : " + lngth[1].trim());
						}
					}
				}
			}
			
			// process the headers		
			
			// 处理文件传输问题
			if(!MyStringUtil.isBlank(requestData.get(Constants.REQUEST_STREAM))){// 上传文件的请求头			
				Long fileSizeLeft = MyStringUtil.Str2Long(requestData.get(Constants.FILED_LENGTH));
				// 设置剩余没有读取的文件内容大小 
				String fileName = serverRoot + requestData.get(Constants.FILED_FILE_NAME);// zip文件名
				
				FileTransfer fileReceiver = new FileTransfer(fileName, fileSizeLeft);
				
				requestWithFile.setFileReceiver(fileReceiver);// 设置文件接收器
				requestWithFile.setIsReadFile(true);
				
				// 探测是否有传递文件内容或是文件开头
				if(!MyStringUtil.isBlank(body)){					
					String packageHead = body.split((HttpConstants.RetentionWord.CRLF_STR))[0];
					
					try{
						Integer length = Integer.parseInt(body.split((HttpConstants.RetentionWord.CRLF_STR))[0], 16);
						Integer btyeLength = packageHead.getBytes().length + 2;// 加上2个回车换行符
						
						byte[] fileContent = body.getBytes();
						if(fileContent.length == btyeLength){// 只传递了文件发送的开始标识
							fileReceiver.setReadHead(true);// 丢弃了开始发文件标识
							fileReceiver.setSizeWithHead(length);
						}else if(fileContent.length > btyeLength){// 不但有文件发送开始标识，还有文件内容
							//fileReceiver.setReadHead(true);// 丢弃了开始发文件标识
							int contentBegin = header.getBytes().length + 4 + btyeLength;// 4个换行回车字符和4个文件开始标识					
							fileReceiver.writeBody(readBuffer, contentBegin, length);// 读取文件内容
						}
					}catch(NumberFormatException nfe){						
						int contentBegin = header.getBytes().length + 4;// 4个换行回车字符和4个文件开始标识					
						fileReceiver.writeBody(readBuffer, contentBegin);// 读取文件内容
					}
				}
			}			
			
	        return;
		}
	}
	
	/**
	 * 
	 * <li>方法名：processUri
	 * <li>@param msg
	 * <li>@param requestData
	 * <li>返回类型：void
	 * <li>说明：解析请求地址uri中的参数和请求接口
	 * <li>创建人：CshBBrain;技术博客：http://cshbbrain.iteye.com/
	 * <li>创建日期：2011-11-25
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	private static void processUri(String msg, HashMap<String,String> requestData){
		int pathEndPos = msg.indexOf('?');
        if (pathEndPos >= 0){
            requestData.put(HttpConstants.REQUEST, msg.substring(0, pathEndPos));
            decodeParams(msg.substring(pathEndPos + 1), requestData);
        }else{
        	requestData.put(HttpConstants.REQUEST, msg);
        }
	}
	
	/**
	 * 
	 * <li>方法名：decodeParams
	 * <li>@param msg
	 * <li>@param requestData
	 * <li>返回类型：void
	 * <li>说明：解析请求参数键值对
	 * <li>创建人：CshBBrain;技术博客：http://cshbbrain.iteye.com/
	 * <li>创建日期：2011-11-25
	 * <li>修改人： 
	 * <li>修改日期：
	 */
    private static void decodeParams(String msg, HashMap<String,String> requestData) {
    	String requestStr = decodeComponent(msg,CoderUtils.UTF8);// 解码

        Matcher m = PARAM_PATTERN.matcher(requestStr);
        int pos = 0;
        while (m.find(pos)) {
            pos = m.end();            
            requestData.put(m.group(1), m.group(2)); 
        }
    }
    
    /**
     * 
     * <li>方法名：decodeComponent
     * <li>@param s
     * <li>@param charset
     * <li>@return
     * <li>返回类型：String
     * <li>说明：解析传输过程中进行http编码的字符串
     * <li>创建人：CshBBrain;技术博客：http://cshbbrain.iteye.com/
     * <li>创建日期：2011-11-25
     * <li>修改人： 
     * <li>修改日期：
     */
    private static String decodeComponent(String s, String charset){
        if(s == null){
            return "";
        }

        try{
            return URLDecoder.decode(s, charset);
        }catch(UnsupportedEncodingException e){
            throw new UnsupportedCharsetException(charset);
        }
    }
}
