package com.jason.util.ip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jason.Config;
import com.jason.util.MyStringUtil;


public class IPSeeker {
	
	public static final String[] Provinces ={"北京","天津","河北","山西","内蒙","辽宁","吉林","黑龙江","上海","江苏","浙江","安徽","福建","江西","山东","河南","湖北",
	    "湖南","广东","广西","海南","重庆","四川","贵州","云南","陕西","甘肃","青海","宁夏","新疆","西藏","香港","澳门","台湾"};
	public static final String[] hbCitys ={"石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水"};
	public static final String[] sxCitys ={"太原","大同","阳泉","长治","晋城","朔州"};
	public static final String[] nmgCitys ={"呼和浩特","包头","乌海","赤峰","通辽"};
	public static final String[] llCitys ={"沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"};
	public static final String[] jlCitys ={"长春","","四平","辽源","通化","白山","松原","白城"};
	public static final String[] hljCitys ={"哈尔滨","齐齐哈尔","鸡西","鹤岗","双鸭山","大庆","伊春","佳木斯","七台河","牡丹江","黑河"};
	public static final String[] jsCitys ={"南京","无锡","徐州","常州","苏州","南通","连云港","淮阴","盐城","扬州","镇江","泰州","宿迁"};
	public static final String[] zjCitys ={"杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州"};
	public static final String[] ahCitys ={"合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安"};
	public static final String[] hjCitys ={"福州","厦门","莆田","三明","泉州","漳州","南平","龙岩"};
	public static final String[] jxCitys ={"南昌","景德镇","萍乡","九江","新余","鹰潭","赣州"};
	public static final String[] sdCitys ={"济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城"};
	public static final String[] hnCitys ={"郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳"};
	public static final String[] hubCitys ={"武汉","黄石","十堰","宜昌","襄樊","鄂州","荆门","孝感","荆州","黄冈","咸宁"};
	public static final String[] hunCitys ={"长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底"};
	public static final String[] gdCitys ={"广州","韶关","深圳","珠海","汕头","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远",",东莞","中山","潮州","揭阳","云浮"};
	public static final String[] gxCitys ={"南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林"};
	public static final String[] hainCitys ={"海口","三亚"};
	public static final String[] scCitys ={"成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","宜宾","广安","达州"};
	public static final String[] guizCitys ={"贵阳","六盘水","遵义"};
	public static final String[] ynCitys ={"昆明","曲靖","玉溪","保山","昭通","丽江","思茅","临沧"};
	public static final String[] shanxCitys ={"西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林"};
	public static final String[] gsCitys ={"兰州","嘉峪关","金昌","白银","天水"};
	public static final String[] qhCitys ={"西宁","海东"};
	public static final String[] nxCitys ={"银川","石嘴山","吴忠","固原","中卫"};
	public static final String[] xjCitys ={"乌鲁木齐","克拉玛依","吐鲁番","哈密","阿克苏","喀什","和田","塔城"};
	public static final String[] xzCitys ={"拉萨","日喀则","山南","昌都","那曲","阿里","林芝"};
	public static final String[] cqCitys ={"重庆"};
	public static final String[] shCitys ={"上海"};
	public static final String[] bjCitys ={"北京"};
	public static final String[] tjCitys ={"天津"};
	public static Map<String,String[]> ProvinceCityMap = new HashMap<String,String[]>(); 
    
    public static final String[] ISPs ={"电信","移动","联通","网通","卫通","长城"};
    
    static{
    	ProvinceCityMap.put("重庆",cqCitys);
    	ProvinceCityMap.put("上海",shCitys);
    	ProvinceCityMap.put("北京",bjCitys);
    	ProvinceCityMap.put("天津",tjCitys);
    	
    	ProvinceCityMap.put("河北",hbCitys);
		ProvinceCityMap.put("山西",sxCitys);
		ProvinceCityMap.put("内蒙",nmgCitys);
		ProvinceCityMap.put("辽宁",nmgCitys);
		ProvinceCityMap.put("吉林",jlCitys);
		ProvinceCityMap.put("黑龙江",hljCitys);
		ProvinceCityMap.put("江苏",jsCitys);
		ProvinceCityMap.put("浙江",zjCitys);
		ProvinceCityMap.put("安徽",ahCitys);
		ProvinceCityMap.put("福建",hjCitys);
		ProvinceCityMap.put("江西",jxCitys);
		ProvinceCityMap.put("山东",sdCitys);
		ProvinceCityMap.put("河南",hnCitys);
		ProvinceCityMap.put("湖北",hubCitys);
		ProvinceCityMap.put("湖南",hunCitys);
		ProvinceCityMap.put("广东",gdCitys);
		ProvinceCityMap.put("广西",gxCitys);
		ProvinceCityMap.put("海南",hainCitys);
		ProvinceCityMap.put("四川",scCitys);
		ProvinceCityMap.put("贵州",guizCitys);
		ProvinceCityMap.put("云南",ynCitys);
		ProvinceCityMap.put("陕西",shanxCitys);
		ProvinceCityMap.put("甘肃",gsCitys);
		ProvinceCityMap.put("青海",qhCitys);
		ProvinceCityMap.put("宁夏",nxCitys);
		ProvinceCityMap.put("新疆",xjCitys);
		ProvinceCityMap.put("西藏",xzCitys);
    }
	
    private static final String IPFILE = "ipFile";//ip地址库文件路径
    
	private static final IPSeeker ipSeeker = new IPSeeker();
	// 纯真IP数据库名
	private String IP_FILE = "QQWry.Dat";
	// 保存的文件夹
	//private String INSTALL_DIR = "/usr/local/cache";
	private String INSTALL_DIR = Config.getStr("ipFile");

	// 一些固定常量，比如记录长度等等
	private static final int IP_RECORD_LENGTH = 7;
	private static final byte REDIRECT_MODE_1 = 0x01;
	private static final byte REDIRECT_MODE_2 = 0x02;

	// 用来做为cache，查询一个ip时首先查看cache，以减少不必要的重复查找
	private Map<String, IPLocation> ipCache;
	private Map<String,Location> locationMap;
	// 随机文件访问类
	private RandomAccessFile ipFile;
	// 内存映射文件
	private MappedByteBuffer mbb;
	// 起始地区的开始和结束的绝对偏移
	private long ipBegin, ipEnd;
	// 为提高效率而采用的临时变量
	private IPLocation loc;
	private byte[] buf;
	private byte[] b4;
	private byte[] b3;
	
	public static IPSeeker getInstance(){
		return ipSeeker;
	}
	
	public static void main(String[] args){
		System.out.println(IPSeeker.getInstance().getCountry("178.70.39.28"));
		System.out.println(IPSeeker.getInstance().getIPLocation("178.70.39.28").getCountry());
	}

	private void setDefaultInstallDir() {
		String path = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		this.INSTALL_DIR = path;//默认放置到class目录下面
	}
	
	private IPSeeker(){
		this(null,null);
	}

	private IPSeeker(String fileName, String dir) {
		/*if(fileName!=null && !fileName.equals("")){
			this.IP_FILE = fileName;
		}*/
		
		this.IP_FILE = Config.getStr(IPFILE);
		
		if(dir!=null && !dir.equals("")){
			this.INSTALL_DIR = dir;
		}else{
			//this.setDefaultInstallDir();
		}
		
		ipCache = new HashMap<String, IPLocation>();
		locationMap = new HashMap<String,Location>();
		loc = new IPLocation();
		buf = new byte[100];
		b4 = new byte[4];
		b3 = new byte[3];
		try {
			ipFile = new RandomAccessFile(IP_FILE, "r");
		} catch (FileNotFoundException e) {
			// 如果找不到这个文件，再尝试再当前目录下搜索，这次全部改用小写文件名
			// 因为有些系统可能区分大小写导致找不到ip地址信息文件
			String filename = new File(IP_FILE).getName().toLowerCase();
			File[] files = new File(INSTALL_DIR).listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().toLowerCase().equals(filename)) {
						try {
							ipFile = new RandomAccessFile(files[i], "r");
						} catch (FileNotFoundException e1) {
							e.printStackTrace();
							ipFile = null;
						}
						break;
					}
				}
			}
		}
		// 如果打开文件成功，读取文件头信息
		if (ipFile != null) {
			try {
				ipBegin = readLong4(0);
				ipEnd = readLong4(4);
				if (ipBegin == -1 || ipEnd == -1) {
					ipFile.close();
					ipFile = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				ipFile = null;
			}
		}
	}

	/**
	 * 给定一个地点的不完全名字，得到一系列包含s子串的IP范围记录
	 * 
	 * @param s
	 *            地点子串
	 * @return 包含IPEntry类型的List
	 */
	public List getIPEntriesDebug(String s) {
		List<IPEntry> ret = new ArrayList<IPEntry>();
		long endOffset = ipEnd + 4;
		for (long offset = ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
			// 读取结束IP偏移
			long temp = readLong3(offset);
			// 如果temp不等于-1，读取IP的地点信息
			if (temp != -1) {
				IPLocation ipLoc = getIPLocation(temp);
				// 判断是否这个地点里面包含了s子串，如果包含了，添加这个记录到List中，如果没有，继续
				if (ipLoc.getCountry().indexOf(s) != -1
						|| ipLoc.getArea().indexOf(s) != -1) {
					IPEntry entry = new IPEntry();
					entry.country = ipLoc.getCountry();
					entry.area = ipLoc.getArea();
					// 得到起始IP
					readIP(offset - 4, b4);
					entry.beginIp = Util.getIpStringFromBytes(b4);
					// 得到结束IP
					readIP(temp, b4);
					entry.endIp = Util.getIpStringFromBytes(b4);
					// 添加该记录
					ret.add(entry);
				}
			}
		}
		return ret;
	}

	public IPLocation getIPLocation(String ip) {
		IPLocation location = new IPLocation();
		location.setArea(this.getArea(ip));
		location.setCountry(this.getCountry(ip));
		return location;
	}

	/**
	 * 给定一个地点的不完全名字，得到一系列包含s子串的IP范围记录
	 * 
	 * @param s
	 *            地点子串
	 * @return 包含IPEntry类型的List
	 */
	public List<IPEntry> getIPEntries(String s) {
		List<IPEntry> ret = new ArrayList<IPEntry>();
		try {
			// 映射IP信息文件到内存中
			if (mbb == null) {
				FileChannel fc = ipFile.getChannel();
				mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, ipFile.length());
				mbb.order(ByteOrder.LITTLE_ENDIAN);
			}

			int endOffset = (int) ipEnd;
			for (int offset = (int) ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
				int temp = readInt3(offset);
				if (temp != -1) {
					IPLocation ipLoc = getIPLocation(temp);
					// 判断是否这个地点里面包含了s子串，如果包含了，添加这个记录到List中，如果没有，继续
					if (ipLoc.getCountry().indexOf(s) != -1
							|| ipLoc.getArea().indexOf(s) != -1) {
						IPEntry entry = new IPEntry();
						entry.country = ipLoc.getCountry();
						entry.area = ipLoc.getArea();
						// 得到起始IP
						readIP(offset - 4, b4);
						entry.beginIp = Util.getIpStringFromBytes(b4);
						// 得到结束IP
						readIP(temp, b4);
						entry.endIp = Util.getIpStringFromBytes(b4);
						// 添加该记录
						ret.add(entry);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 从内存映射文件的offset位置开始的3个字节读取一个int
	 * 
	 * @param offset
	 * @return
	 */
	private int readInt3(int offset) {
		mbb.position(offset);
		return mbb.getInt() & 0x00FFFFFF;
	}

	/**
	 * 从内存映射文件的当前位置开始的3个字节读取一个int
	 * 
	 * @return
	 */
	private int readInt3() {
		return mbb.getInt() & 0x00FFFFFF;
	}

	/**
	 * 根据IP得到国家名
	 * 
	 * @param ip
	 *            ip的字节数组形式
	 * @return 国家名字符串
	 */
	public String getCountry(byte[] ip) {
		// 检查ip地址文件是否正常
		if (ipFile == null)
			return Message.bad_ip_file;
		// 保存ip，转换ip字节数组为字符串形式
		String ipStr = Util.getIpStringFromBytes(ip);
		// 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation ipLoc = ipCache.get(ipStr);
			return ipLoc.getCountry();
		} else {
			IPLocation ipLoc = getIPLocation(ip);
			ipCache.put(ipStr, ipLoc.getCopy());
			return ipLoc.getCountry();
		}
	}
	
	private Location createLocal(){
		Location location = new Location();
		
		// 设置默认值
		location.setCountry("中国");
    	location.setProvince("其他");
    	location.setCity("其他");
		location.setAddress("其他");		
        
        return location;
	}
	
	public Location getLocation(String ip){
		if(ip.startsWith("192")){// 处理内网
			ip = "192";
			Location location = this.locationMap.get(ip);
			if(location == null){				
				location = createLocal();
				this.locationMap.put(ip, location);
			}
			
			return location;
		}
		
		// 处理外网
		Location location = this.locationMap.get(ip);
		if(location == null){
			IPLocation ipLoc = getIPLocation(Util.getIpByteArrayFromString(ip));
			location = createLocation(ipLoc);
			location.setIp(ip);
			this.locationMap.put(ip, location);
		}
		
		return location;
	}
	
	private Location createLocation(IPLocation ipLoc){
		Location location = new Location();
		String city = ipLoc.getArea();
		String country = ipLoc.getCountry();
		
		if ((city == null) || (city.indexOf("CZ88.NET") > -1)) {
            city = "";
        }
		// 设置默认值
		location.setCountry(country);
    	location.setProvince(city);
    	location.setCity(city);
		location.setAddress(country + city);
		
        if (!MyStringUtil.isBlank(location.getAddress())) {
            int provinclen = Provinces.length;
            for (int l = 0; l < provinclen; l++) {
                String province = Provinces[l];
                if (location.getAddress().indexOf(province) != -1) {
                	location.setCountry("中国");
                	location.setProvince(province);
                	System.out.println(province);
                    String[] citys = ProvinceCityMap.get(province);
                    
                    if(citys != null){
	                    int citylen = citys.length;
	                    for (int k = 0; k < citylen; k++) {
	                        city = citys[k];
	                        if (location.getAddress().indexOf(city) != -1) {
	                            location.setCity(city);
	                            break;
	                        }
	                    }
                    }else{
                    	location.setProvince("其他");
                    	location.setCity("其他");
                    }
                    break;
                }
            }
            int isplen = ISPs.length;
            for (int l = 0; l < isplen; l++) {
                String isp = ISPs[l];
                if (location.getAddress().indexOf(isp) != -1) {
                    location.setISP(isp);
                    break;
                }
            }
        }
        
        if(MyStringUtil.isBlank(location.getCountry())){
        	location.setCountry("其他");
        }
        
        if(MyStringUtil.isBlank(location.getProvince())){
        	location.setProvince("其他");
        }
        if(MyStringUtil.isBlank(location.getCity())){
        	location.setCity("其他");
        }
        
        return location;
	}
	
	public IPLocation getLocationByIp(String ip) {
		IPLocation ipLoc = null;
		if(ipCache.containsKey(ip)){
			ipLoc = ipCache.get(ip);
		}
		
		if (ipLoc == null) {			
			ipLoc = getIPLocation(Util.getIpByteArrayFromString(ip));
			ipCache.put(ip, ipLoc.getCopy());
		}
		return ipLoc;
	}

	/**
	 * 根据IP得到国家名
	 * 
	 * @param ip
	 *            IP的字符串形式
	 * @return 国家名字符串
	 */
	public String getCountry(String ip) {
		return getCountry(Util.getIpByteArrayFromString(ip));
	}

	/**
	 * 根据IP得到地区名
	 * 
	 * @param ip
	 *            ip的字节数组形式
	 * @return 地区名字符串
	 */
	public String getArea(byte[] ip) {
		// 检查ip地址文件是否正常
		if (ipFile == null)
			return Message.bad_ip_file;
		// 保存ip，转换ip字节数组为字符串形式
		String ipStr = Util.getIpStringFromBytes(ip);
		// 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation ipLoc = ipCache.get(ipStr);
			return ipLoc.getArea();
		} else {
			IPLocation ipLoc = getIPLocation(ip);
			ipCache.put(ipStr, ipLoc.getCopy());
			return ipLoc.getArea();
		}
	}

	/**
	 * 根据IP得到地区名
	 * 
	 * @param ip
	 *            IP的字符串形式
	 * @return 地区名字符串
	 */
	public String getArea(String ip) {
		return getArea(Util.getIpByteArrayFromString(ip));
	}

	/**
	 * 根据ip搜索ip信息文件，得到IPLocation结构，所搜索的ip参数从类成员ip中得到
	 * 
	 * @param ip
	 *            要查询的IP
	 * @return IPLocation结构
	 */
	private IPLocation getIPLocation(byte[] ip) {
		IPLocation info = null;
		long offset = locateIP(ip);
		if (offset != -1)
			info = getIPLocation(offset);
		if (info == null) {
			info = new IPLocation();
			info.setCountry(Message.unknown_country);
			info.setArea(Message.unknown_area);
		}
		return info;
	}

	/**
	 * 从offset位置读取4个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 * 
	 * @param offset
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong4(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ret |= (ipFile.readByte() & 0xFF);
			ret |= ((ipFile.readByte() << 8) & 0xFF00);
			ret |= ((ipFile.readByte() << 16) & 0xFF0000);
			ret |= ((ipFile.readByte() << 24) & 0xFF000000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * 从offset位置读取3个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 * 
	 * @param offset
	 *            整数的起始偏移
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong3(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * 从当前位置读取3个字节转换成long
	 * 
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong3() {
		long ret = 0;
		try {
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * 从offset位置读取四个字节的ip地址放入ip数组中，读取后的ip为big-endian格式，但是
	 * 文件中是little-endian形式，将会进行转换
	 * 
	 * @param offset
	 * @param ip
	 */
	private void readIP(long offset, byte[] ip) {
		try {
			ipFile.seek(offset);
			ipFile.readFully(ip);
			byte temp = ip[0];
			ip[0] = ip[3];
			ip[3] = temp;
			temp = ip[1];
			ip[1] = ip[2];
			ip[2] = temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从offset位置读取四个字节的ip地址放入ip数组中，读取后的ip为big-endian格式，但是
	 * 文件中是little-endian形式，将会进行转换
	 * 
	 * @param offset
	 * @param ip
	 */
	private void readIP(int offset, byte[] ip) {
		mbb.position(offset);
		mbb.get(ip);
		byte temp = ip[0];
		ip[0] = ip[3];
		ip[3] = temp;
		temp = ip[1];
		ip[1] = ip[2];
		ip[2] = temp;
	}

	/**
	 * 把类成员ip和beginIp比较，注意这个beginIp是big-endian的
	 * 
	 * @param ip
	 *            要查询的IP
	 * @param beginIp
	 *            和被查询IP相比较的IP
	 * @return 相等返回0，ip大于beginIp则返回1，小于返回-1。
	 */
	private int compareIP(byte[] ip, byte[] beginIp) {
		for (int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if (r != 0)
				return r;
		}
		return 0;
	}

	/**
	 * 把两个byte当作无符号数进行比较
	 * 
	 * @param b1
	 * @param b2
	 * @return 若b1大于b2则返回1，相等返回0，小于返回-1
	 */
	private int compareByte(byte b1, byte b2) {
		if ((b1 & 0xFF) > (b2 & 0xFF)) // 比较是否大于
			return 1;
		else if ((b1 ^ b2) == 0)// 判断是否相等
			return 0;
		else
			return -1;
	}

	/**
	 * 这个方法将根据ip的内容，定位到包含这个ip国家地区的记录处，返回一个绝对偏移 方法使用二分法查找。
	 * 
	 * @param ip
	 *            要查询的IP
	 * @return 如果找到了，返回结束IP的偏移，如果没有找到，返回-1
	 */
	private long locateIP(byte[] ip) {
		long m = 0;
		int r;
		// 比较第一个ip项
		readIP(ipBegin, b4);
		r = compareIP(ip, b4);
		if (r == 0)
			return ipBegin;
		else if (r < 0)
			return -1;
		// 开始二分搜索
		for (long i = ipBegin, j = ipEnd; i < j;) {
			m = getMiddleOffset(i, j);
			readIP(m, b4);
			r = compareIP(ip, b4);
			// log.debug(Utils.getIpStringFromBytes(b));
			if (r > 0)
				i = m;
			else if (r < 0) {
				if (m == j) {
					j -= IP_RECORD_LENGTH;
					m = j;
				} else
					j = m;
			} else
				return readLong3(m + 4);
		}
		// 如果循环结束了，那么i和j必定是相等的，这个记录为最可能的记录，但是并非
		// 肯定就是，还要检查一下，如果是，就返回结束地址区的绝对偏移
		m = readLong3(m + 4);
		readIP(m, b4);
		r = compareIP(ip, b4);
		if (r <= 0)
			return m;
		else
			return -1;
	}

	/**
	 * 得到begin偏移和end偏移中间位置记录的偏移
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private long getMiddleOffset(long begin, long end) {
		long records = (end - begin) / IP_RECORD_LENGTH;
		records >>= 1;
		if (records == 0)
			records = 1;
		return begin + records * IP_RECORD_LENGTH;
	}

	/**
	 * 给定一个ip国家地区记录的偏移，返回一个IPLocation结构
	 * 
	 * @param offset
	 *            国家记录的起始偏移
	 * @return IPLocation对象
	 */
	private IPLocation getIPLocation(long offset) {
		try {
			// 跳过4字节ip
			ipFile.seek(offset + 4);
			// 读取第一个字节判断是否标志字节
			byte b = ipFile.readByte();
			if (b == REDIRECT_MODE_1) {
				// 读取国家偏移
				long countryOffset = readLong3();
				// 跳转至偏移处
				ipFile.seek(countryOffset);
				// 再检查一次标志字节，因为这个时候这个地方仍然可能是个重定向
				b = ipFile.readByte();
				if (b == REDIRECT_MODE_2) {
					loc.setCountry(readString(readLong3()));
					ipFile.seek(countryOffset + 4);
				} else
					loc.setCountry(readString(countryOffset));
				// 读取地区标志
				loc.setArea(readArea(ipFile.getFilePointer()));
			} else if (b == REDIRECT_MODE_2) {
				loc.setCountry(readString(readLong3()));
				loc.setArea(readArea(offset + 8));
			} else {
				loc.setCountry(readString(ipFile.getFilePointer() - 1));
				loc.setArea(readArea(ipFile.getFilePointer()));
			}
			return loc;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 给定一个ip国家地区记录的偏移，返回一个IPLocation结构，此方法应用与内存映射文件方式
	 * 
	 * @param offset
	 *            国家记录的起始偏移
	 * @return IPLocation对象
	 */
	private IPLocation getIPLocation(int offset) {
		// 跳过4字节ip
		mbb.position(offset + 4);
		// 读取第一个字节判断是否标志字节
		byte b = mbb.get();
		if (b == REDIRECT_MODE_1) {
			// 读取国家偏移
			int countryOffset = readInt3();
			// 跳转至偏移处
			mbb.position(countryOffset);
			// 再检查一次标志字节，因为这个时候这个地方仍然可能是个重定向
			b = mbb.get();
			if (b == REDIRECT_MODE_2) {
				loc.setCountry(readString(readInt3()));
				mbb.position(countryOffset + 4);
			} else
				loc.setCountry(readString(countryOffset));
			// 读取地区标志
			loc.setArea(readArea(mbb.position()));
		} else if (b == REDIRECT_MODE_2) {
			loc.setCountry(readString(readInt3()));
			loc.setArea(readArea(offset + 8));
		} else {
			loc.setCountry(readString(mbb.position() - 1));
			loc.setArea(readArea(mbb.position()));
		}
		return loc;
	}

	/**
	 * 从offset偏移开始解析后面的字节，读出一个地区名
	 * 
	 * @param offset
	 *            地区记录的起始偏移
	 * @return 地区名字符串
	 * @throws IOException
	 */
	private String readArea(long offset) throws IOException {
		ipFile.seek(offset);
		byte b = ipFile.readByte();
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			long areaOffset = readLong3(offset + 1);
			if (areaOffset == 0)
				return Message.unknown_area;
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}

	/**
	 * @param offset
	 *            地区记录的起始偏移
	 * @return 地区名字符串
	 */
	private String readArea(int offset) {
		mbb.position(offset);
		byte b = mbb.get();
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			int areaOffset = readInt3();
			if (areaOffset == 0)
				return Message.unknown_area;
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}

	/**
	 * 从offset偏移处读取一个以0结束的字符串
	 * 
	 * @param offset
	 *            字符串起始偏移
	 * @return 读取的字符串，出错返回空字符串
	 */
	private String readString(long offset) {
		try {
			ipFile.seek(offset);
			int i = 0;
			byte[] buf = new byte[100];
			while ((buf[i] = ipFile.readByte()) != 0) {
				++i;
				if (i >= buf.length) {
					byte[] tmp = new byte[i + 100];
					System.arraycopy(buf, 0, tmp, 0, i);
					buf = tmp;
				}
			}
			// for(i = 0, buf[i] = ipFile.readByte(); buf[i] != 0; buf[++i] =
			// ipFile.readByte());
			if (i != 0)
				return Util.getString(buf, 0, i, "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 从内存映射文件的offset位置得到一个0结尾字符串
	 * 
	 * @param offset
	 *            字符串起始偏移
	 * @return 读取的字符串，出错返回空字符串
	 */
	private String readString(int offset) {
		try {
			mbb.position(offset);
			int i;
			for (i = 0, buf[i] = mbb.get(); buf[i] != 0; buf[++i] = mbb.get())
				;
			if (i != 0)
				return Util.getString(buf, 0, i, "GBK");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return "";
	}
}
