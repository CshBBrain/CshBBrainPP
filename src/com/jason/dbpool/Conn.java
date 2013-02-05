/**
 * <li>文件名：Conn.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-5
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.dbpool;

import java.sql.Connection;

/**
 * <li>类型名称：
 * <li>说明：链接信息
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-5
 * <li>修改人： 
 * <li>修改日期：
 */
public class Conn{
	private static final String DB_ORACLE = "oracle";
	private static final String DB_MYSQL = "mysql";
	private static final String DB_SQLSERVER = "sqlserver";
	private static final String DB_SQLLITE = "sqllite";
	private static final String DB_POSTGRESQL = "postgresql";
	
	private static final Integer ORACLE = 1;
	private static final Integer MYSQL = 2;
	private static final Integer SQLSERVER = 3;
	private static final Integer SQLLITE = 4;
	private static final Integer POSTGRESQL = 5;
	
	private Connection conn;// 数据库链接
	private Long currentMinis;
	private Integer type;// 数据库类型
	
	public Conn(Connection conn, String type){
		this.conn = conn;		
		this.currentMinis = System.currentTimeMillis();
		this.setType(type);
	}
	
	private void setType(String type){
		if(DB_ORACLE.equalsIgnoreCase(type)){
			this.type = ORACLE;
		}else if(DB_MYSQL.equalsIgnoreCase(type)){
			this.type = MYSQL;
		}else if(DB_POSTGRESQL.equalsIgnoreCase(type)){
			this.type = POSTGRESQL;
		}else if(DB_SQLLITE.equalsIgnoreCase(type)){
			this.type = SQLLITE;
		}else if(DB_SQLSERVER.equalsIgnoreCase(type)){
			this.type = SQLSERVER;
		}
	}
	
	public void setConn(Connection conn, Long currentMinis){
		this.conn = conn;
		this.currentMinis = currentMinis;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Long getCurrentMinis() {
		return currentMinis;
	}

	public void setCurrentMinis(Long currentMinis) {
		this.currentMinis = currentMinis;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
