/**
 * <li>包名：FileUtil.java
 * <li>JDK version：jdk1.5
 * <li>project version：1.0
 * <li>说明：
 * <li>公司：赢丰无线(yf)
 * <li>创建人： 袁军
 * <li>创建日期：Mar 5, 2012
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * <li>文件名：FileUtil.java
 * <li>说明： 
 * <li>创建人： 袁军
 * <li>创建日期：Mar 5, 2012
 * <li>修改人： 
 * <li>修改日期：
 */
public class FileUtil {
	public static String readContent(String filePath) throws Exception {
		File file = new File(filePath);
		if (file.exists()) {
			StringBuffer sb = new StringBuffer();
			InputStream in = new FileInputStream(file);
			InputStreamReader inReader = new InputStreamReader(in,"UTF-8");
			BufferedReader beReader = new BufferedReader(inReader);
			String line = null;
			while ((line = beReader.readLine()) != null) {
				sb.append(line);
			}
			beReader.close();
			inReader.close();
			in.close();
			return sb.toString();
		} else {		
			throw new IOException("读取文件出错:目标文件不存在,路径：" + file.getAbsolutePath());
		}
	}
	
	/**
	 * 
	 * <li>方法名：unzip
	 * <li>
	 * <li>返回类型：void
	 * <li>说明：解压zip文件
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-12-10
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static String unzip(String path,String fileName,Boolean deleteSource,String ...relativePath){
		String prePox = "";// 附加在文件名上的相对路径，为空为不管
		if(relativePath != null && relativePath.length > 0){
			prePox = relativePath[0];
		}
		
		String fileNames = "";// 图片文件名
		try{
			// 先指定压缩档的位置和档名，建立FileInputStream对象			
			FileInputStream fins = new FileInputStream(path + fileName);
			// 将fins传入ZipInputStream中
			ZipInputStream zins = new ZipInputStream(fins);
			ZipEntry ze = null;
			byte ch[] = new byte[1024];
			while ((ze = zins.getNextEntry()) != null){
				String contentFile = path + ze.getName();
				fileNames += (prePox + ze.getName() + ",");// 组织返回的文件名
				File zfile = new File(contentFile);
				File fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists()){
						zfile.mkdirs();
					}
					zins.closeEntry();
				}else{
					if (!fpath.exists()){
						fpath.createNewFile();
					}
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					while ((i = zins.read(ch)) != -1){
						fouts.write(ch, 0, i);
					}
					zins.closeEntry();
					
					fouts.close();
				}
			}
						
			fins.close();
			zins.close();
			
			if(deleteSource){// 删除原始文件
				File f = new File(path + fileName);
				f.delete();
			}
			
			if(!MyStringUtil.isBlank(fileNames)){
				return fileNames.substring(0, fileNames.length() - 1);
			}else{
				return fileNames;
			}
			
		} catch (Exception e) {
			
			if(deleteSource){// 删除原始文件
				File f = new File(path + fileName);
				f.delete();
			}			
			System.err.println("Extract error:" + e.getMessage());

			if(!MyStringUtil.isBlank(fileNames)){
				return fileNames.substring(0, fileNames.length() - 1);
			}else{
				return fileNames;
			}
		}
	}
}
