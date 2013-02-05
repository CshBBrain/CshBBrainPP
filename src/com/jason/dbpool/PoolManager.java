/**
 * <li>文件名：PoolManager.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-4
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.dbpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.jason.Config;
import com.jason.util.MyStringUtil;

/**
 * <li>类型名称：
 * <li>说明：连接池管理器，负责连接池的创建，获取，释放，销毁工作
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-4
 * <li>修改人： 
 * <li>修改日期：
 */
public class PoolManager {
	// 连接池配置文件中的配置属性名
	private static final String POP_COUNT = "count";// 连接池数量
	private static final String POP_NAME = "name";// 连接池驱动程序名
	private static final String POP_DRIVER = "driver";// 连接池驱动程序名
	private static final String POP_URL = "url";// 连接池数据库链接地址
	private static final String POP_USER = "user";// 连接池用户名
	private static final String POP_PASSWORD = "password";// 连接池用户密码
	private static final String POP_MAX_CONNS = "maxConns";// 连接池最大链接数量
	private static final String POP_MIN_CONNS = "minConns";// 连接池最小链接数量
	private static final String POP_MAX_FREE_INTERVAL = "maxFreeInterval";// 连接池关闭的最大空闲时间
	private static final String POP_TYPE = "type";// 数据库类型
	
	private static final PoolManager poolManager = new PoolManager();// 连接池管理器单实例
	
	private HashMap<String,Pool> pools;// 当有多个数据库链接时，根据配置的数据库名将连接池映射于此 = new HashMap<String,ArrayBlockingQueue<Connection>>();
	private Pool pool;// 单个数据库连接池
	private Integer count = 0;// 连接池的数量	
	
	private ArrayList<Driver> drivers;
	
	
	private void loadDrivers(String driverClassName) {
		try {
			Driver driver = (Driver) Class.forName(driverClassName).newInstance();
			DriverManager.registerDriver(driver);
			drivers.add(driver);
			System.out.println("成功注册JDBC驱动程序" + driverClassName);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("无法注册JDBC驱动程序: " + driverClassName + ", 错误: " + e);
		}
	}
	
	private void unloadDrivers(){
		for(int i = 0; i < this.drivers.size(); ++i){
			Driver driver = this.drivers.get(i);
			try {				
				DriverManager.deregisterDriver(driver);
				System.out.println("撤销JDBC驱动程序 " + driver.getClass().getName() + "的注册");
			} catch (SQLException e) {
				System.out.println("无法撤销下列JDBC驱动程序的注册: " + driver.getClass().getName());
			}
		}
	}	
	
	public static void main(String[] args){
		Conn con = PoolManager.getInstance().getConnection();
		Connection conn = con.getConn();
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from article ");
			int c = rs.getMetaData().getColumnCount();			
			for(int i = 1; i <= c; ++i){
				System.out.println(rs.getMetaData().getColumnName(i));
			}
			
			while (rs.next()) {
				System.out.print(rs.getString("title"));				
				System.out.print(rs.getString("author"));
				
				
				//System.out.print(rs.getObject("abc"));
				
				System.out.println();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			/*this.closeDB(stmt, rs);
			dbConns.freeConnection("mcms", conn);*/
			try{
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			PoolManager.getInstance().releaseConnection(con);// 释放链接
		}
	}
	
	private PoolManager(){
		init();// 读取初始化数据库连接配置		
	}
	
	/**
	 * 
	 * <li>方法名：getConnection
	 * <li>@return
	 * <li>返回类型：Connection
	 * <li>说明：获取默认的数据库链接
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public Conn getConnection(){
		return this.pool.getConnection();
	}
	
	/**
	 * 
	 * <li>方法名：releaseConnection
	 * <li>@param con
	 * <li>返回类型：void
	 * <li>说明：释放指定的数据库链接
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public void releaseConnection(Conn con){
		this.pool.releaseConnection(con);
	}
	
	/**
	 * 
	 * <li>方法名：getConnection
	 * <li>@param poolName
	 * <li>@return
	 * <li>返回类型：Connection
	 * <li>说明：获取指定的数据库连接池中的数据库链接
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public Conn getConnection(String poolName){
		return this.pools.get(poolName).getConnection();
	}
	
	/**
	 * 
	 * <li>方法名：releaseConnection
	 * <li>@param con
	 * <li>@param poolName
	 * <li>返回类型：void
	 * <li>说明：释放指定的数据库连接池中的数据库链接
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public void releaseConnection(Conn con, String poolName){
		this.pools.get(poolName).releaseConnection(con);
	}

	/**
	 * 
	 * <li>方法名：init
	 * <li>
	 * <li>返回类型：void
	 * <li>说明：读取数据库链接配置文件，根据数据库链接配置文件创建数据库连接池
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	private void init(){		
		// 根据配置信息创建连接池
		String countStr = Config.getStr(POP_COUNT);
		if(MyStringUtil.isBlank(countStr)){
			this.count = 0;
		}else{
			this.count = Integer.valueOf(countStr);
		}
		
		this.drivers = new ArrayList<Driver>(this.count);
		
		// 读取数据库连接池配置信息
		if(this.count <= 0){// 单个数据库链接池配置
			DbPoolConfig sc = this.fetchConfig(0);
			this.loadDrivers(sc.getDrivers());
			this.pool = new Pool(sc);// 获取单个数据库连接池
		}else{// 多个数据库连接池配置
			for(int i = 1; i <= this.count; ++i){
				DbPoolConfig sc = this.fetchConfig(i);
				this.loadDrivers(sc.getDrivers());
				this.pools.put(sc.getName(), new Pool(sc));
			}
		}
		
	}
	
	/**
	 * 
	 * <li>方法名：fetchConfig
	 * <li>@param serelNo
	 * <li>@param props
	 * <li>@return
	 * <li>返回类型：SingleConfig
	 * <li>说明：获取连接池配置信息
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	private DbPoolConfig fetchConfig(Integer serelNo){
		DbPoolConfig singleConfig = new DbPoolConfig();
		String name =null;
		String driver =null; 
		String url =null;
		String user =null;
		String passWord =null;		
		String minConns =null; 
		String maxConns =null; 
		String maxFreeInterval =null;
		String type = null;
				
		if(serelNo > 0){
			name = Config.getStr(POP_NAME + "_" + serelNo);
			driver = Config.getStr(POP_DRIVER + "_" + serelNo); 
			url = Config.getStr(POP_URL + "_" + serelNo);
			user = Config.getStr(POP_USER + "_" + serelNo);
			passWord = Config.getStr(POP_PASSWORD + "_" + serelNo); 
			type =  Config.getStr(POP_TYPE + "_" + serelNo);
			
			minConns = Config.getStr(POP_MIN_CONNS + "_" + serelNo); 
			maxConns = Config.getStr(POP_MAX_CONNS + "_" + serelNo); 
			maxFreeInterval = Config.getStr(POP_MAX_FREE_INTERVAL + "_" + serelNo);			
		}else{
			name = Config.getStr(POP_NAME);
			driver = Config.getStr(POP_DRIVER); 
			url = Config.getStr(POP_URL);
			user = Config.getStr(POP_USER);
			passWord = Config.getStr(POP_PASSWORD); 
			type =  Config.getStr(POP_TYPE);
			
			minConns = Config.getStr(POP_MIN_CONNS); 
			maxConns = Config.getStr(POP_MAX_CONNS); 
			maxFreeInterval = Config.getStr(POP_MAX_FREE_INTERVAL); 			
		}
		
		singleConfig.setName(name);
		singleConfig.setDrivers(driver);
		singleConfig.setUrl(url);
		singleConfig.setUser(user);
		singleConfig.setPassword(passWord);
		singleConfig.setType(type);
		
		if(MyStringUtil.isBlank(minConns)){// 默认最小链接数量为5个
			singleConfig.setMinConns(5);
		}else{
			singleConfig.setMinConns(Integer.valueOf(minConns));
		}
		
		if(MyStringUtil.isBlank(maxConns)){// 默认最大链接数量为10个
			singleConfig.setMaxConns(10);
		}else{
			singleConfig.setMaxConns(Integer.valueOf(maxConns));
		}
		
		if(MyStringUtil.isBlank(maxFreeInterval)){// 默认最大间隔为10分钟
			singleConfig.setMaxFreeInterval(1000 * 60 * 10);
		}else{
			singleConfig.setMaxFreeInterval(Integer.valueOf(maxFreeInterval));
		}
		
		return singleConfig;
	}
	
	/**
	 * 
	 * <li>方法名：getInstance
	 * <li>@return
	 * <li>返回类型：PoolManager
	 * <li>说明：返回连接池管理器实例
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-4
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static PoolManager getInstance(){
		return poolManager;
	}

	@Override
	protected void finalize() throws Throwable {		
		super.finalize();
		this.pool.shutdown();// 关闭连接池
		unloadDrivers();// 卸载驱动程序
	}
	
	
	
	
	
}
