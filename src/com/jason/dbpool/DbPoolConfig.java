/**
 * <li>文件名：SingleConfig.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-4
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.dbpool;

/**
 * <li>类型名称：
 * <li>说明：单个数据库链接配置信息；本数据库连接池，支持分布式数据库
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-4
 * <li>修改人： 
 * <li>修改日期：
 */
public class DbPoolConfig {
	private String name;// 数据库链接名
	private String drivers;// 驱动程序名
	private String url;// 数据库链接地址
	private String user;// 数据库用户名
	private String password;// 数据库用户密码
	private String type;// 数据库类型
	private Integer maxConns;// 最大链接数量
	private Integer minConns;// 最小链接数量
	private Integer maxFreeInterval;// 监听链接不使用的最常时间	
	
	public String getDrivers() {
		return drivers;
	}
	public void setDrivers(String drivers) {
		this.drivers = drivers;
	}
	public Integer getMaxConns() {
		return maxConns;
	}
	public void setMaxConns(Integer maxConns) {
		this.maxConns = maxConns;
	}
	public Integer getMaxFreeInterval() {
		return maxFreeInterval;
	}
	public void setMaxFreeInterval(Integer maxFreeInterval) {
		this.maxFreeInterval = maxFreeInterval;
	}
	public Integer getMinConns() {
		return minConns;
	}
	public void setMinConns(Integer minConns) {
		this.minConns = minConns;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
}
