/**
 * <li>文件名：Pool.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-5
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.dbpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jason.util.MyStringUtil;
/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-10-5
 * <li>修改人： 
 * <li>修改日期：
 */
public class Pool {
	private ConcurrentLinkedQueue<Conn> pool;// 单个数据库连接池
	private ConcurrentLinkedQueue<Conn> usedPool;// 已经被使用的数据库连接池
	private Thread releaseThread;// 检查释放多余链接的线程
	
	private Integer count;// 数据库连接数量
	private DbPoolConfig singleConfig;// 如果是单个连接池，保存单个连接池配置
	private boolean isReleasing = false;
	
	public Pool(DbPoolConfig singleConfig){		
		this.pool = new ConcurrentLinkedQueue<Conn>();// 创建数组阻塞队列
		this.usedPool = new ConcurrentLinkedQueue<Conn>();// 创建被使用的链接的队列
		this.singleConfig = singleConfig;
		this.count = this.singleConfig.getMinConns();// 初始状态时为最小连接数量
		
		// 初始时创建最小链接数量的链接
		for(int i = 0; i < this.singleConfig.getMinConns(); ++i){
			this.pool.add(createConnection(this.singleConfig));			
		}
		
		this.createReleaseThread();// 启动监听线程
	}
	
	private void createReleaseThread() {
		// 创建请求响应分配线程
		Runnable releaseRunner = new Runnable(){
			public void run(){
				try{
					startRelease();
				}catch(Exception e){
					e.printStackTrace();
				}
			}			
		};
		
		this.releaseThread = new Thread(releaseRunner);
		this.releaseThread.setName(this.singleConfig.getName() + "释放线程");
		this.releaseThread.start();		
	}
		
	protected void startRelease(){		
		while(true){
			if(this.count > this.singleConfig.getMinConns()){
				Conn conn = this.pool.poll();
				if(conn.getCurrentMinis() !=null && this.singleConfig.getMaxFreeInterval() != null && (System.currentTimeMillis() - conn.getCurrentMinis() > this.singleConfig.getMaxFreeInterval())){
					
				}
			}
			
			try{
				this.releaseThread.sleep(200);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	private Conn createConnection(DbPoolConfig singleConfig) {
		Connection con = null;
		try {
			if(MyStringUtil.isBlank(singleConfig.getUser())){
				con = DriverManager.getConnection(singleConfig.getUrl());
			}else{
				con = DriverManager.getConnection(singleConfig.getUrl(), singleConfig.getUser(), singleConfig.getPassword());
			}
			System.out.println("连接池" + singleConfig.getName() + "创建一个新的连接");
		}catch(SQLException e){
			System.out.println("无法创建下列URL的连接: " + singleConfig.getUrl() + " user: " + singleConfig.getUser() + " pd: " + singleConfig.getPassword());
			return null;
		}
		
		return new Conn(con,singleConfig.getType());
	}
	
	/**
	 * 
	 * <li>方法名：getConnection
	 * <li>@return
	 * <li>返回类型：Connection
	 * <li>说明：从连接池中获取链接，如果连接池中有空闲链接立即返回一个，如果没有
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public synchronized  Conn getConnection(){
		try{
			if(isReleasing){// 连接池已经回收完所有链接，正在销毁所有链接
				System.out.println("正在销毁连接池");
				return null;
			}
			
			Conn conn = null;
			if(this.pool.isEmpty()){
				if(this.count < this.singleConfig.getMaxConns()){// 如果少于最大允许链接数量，创建一个新链接
					conn = this.createConnection(singleConfig);				
					++this.count;
				}else{
					int maxTimes = 3 * 1000; // 3秒
					int begin = 0;
					conn = this.pool.poll();
					while(conn == null){
						if(begin > maxTimes){// 等待超过3秒
							break;
						}
						
						try {
							Thread.sleep(20 * 1); // 等待20毫秒再获取
						} catch (InterruptedException e) {
							e.printStackTrace();
						}// 等待200毫秒
						
						begin += 20;
						conn = this.pool.poll();
					}				
				}
			}else{
				conn = this.pool.poll();
			}
			//yuan jun add 如果连接为空
			while(conn == null || conn.getConn()==null || conn.getConn().isClosed()){
				//如果联接为空，新建连接
				conn = this.createConnection(singleConfig);
				if(conn == null || conn.getConn()==null || conn.getConn().isClosed()){
					System.out.println("数据库不能连接，需重复连接，重复连接时间："+MyStringUtil.Date2Str(new Date(),""));
					Thread.sleep(20 * 1); // 等待20毫秒再获取
				}else{
					++this.count;
				}
			}
			if(conn != null){
				this.usedPool.add(conn);
				return conn;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * <li>方法名：releaseConnection
	 * <li>@param con
	 * <li>返回类型：void
	 * <li>说明：释放链接到连接池中
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-5
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public void releaseConnection(Conn con){
		this.usedPool.remove(con);
		
		this.pool.add(con);
	}
	
	/**
	 * 
	 * <li>方法名：shutdown
	 * <li>
	 * <li>返回类型：void
	 * <li>说明：关闭连接池
	 * <li>创建人：陈嗣洪
	 * <li>创建日期：2011-10-6
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public void shutdown(){
		// 保证所有链接都处于空闲状态
		while(this.count > this.pool.size()){
			try {
				Thread.currentThread().sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}// 睡200毫秒
		}
		
		// 设置销毁标记
		this.isReleasing = true;
		
		// 关闭所有链接
		while(!this.pool.isEmpty()){
			try {
				this.pool.poll().getConn().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		this.pool.clear();
		this.usedPool.clear();
	}

}
