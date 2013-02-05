/**
 * <li>文件名：FileMapper.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2013-1-26
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <li>类型名称：
 * <li>说明：文件映射到缓存中
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2013-1-26
 * <li>修改人： 
 * <li>修改日期：
 */
public class FileMapper {
	private ConcurrentHashMap<String,ByteBuffer> fileMaps = new ConcurrentHashMap<String, ByteBuffer>(1000);// 文件映射缓存
	private static FileMapper mapper = new FileMapper();// 映射实例
	public static FileMapper getInstance(){
		return mapper;
	}
	
	private FileMapper(){
		
	}
	
	/**
	 * 
	 * <li>方法名：getBuffer
	 * <li>@param filePath
	 * <li>@return
	 * <li>返回类型：ByteBuffer
	 * <li>说明：获取映射
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2013-1-26
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public ByteBuffer getBuffer(String filePath){
		ByteBuffer buffer = fileMaps.get(filePath);
		
		if(buffer == null){
			File requestedFile = new File(filePath);
			if(requestedFile != null && requestedFile.exists()){
				FileInputStream fileInput = null;
				FileChannel fileChannel = null;
			    try{				    	
			    	// 直接通过文件管道将文件内容输出到客户端
			    	fileInput = new FileInputStream(requestedFile);   
			    	fileChannel = fileInput.getChannel(); 
			    	if(fileChannel.size() < (1024l * 1024 * 10)){// 小于10M的文件都缓存到内存中
				    	MappedByteBuffer fileBuffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
				    	fileBuffer.load();
				    	this.fileMaps.put(filePath, fileBuffer);
				    	buffer = fileBuffer.asReadOnlyBuffer();
			    	}else{
			    		buffer = null;
			    	}
			    }catch(IOException e){   
			      e.printStackTrace(); 			     
			    }finally{
			    	 try{
			    		if(fileInput != null){
			    			fileInput.close();
			    			fileInput = null;
			    		}
			    		
			    		if(fileChannel != null){
			    			fileChannel.close();
			    			fileChannel = null;
			    		}
			    	}catch(IOException ex){
			    		ex.printStackTrace();   
			    	}
			    }
			}
		}
		return buffer;
	}

}
