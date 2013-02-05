package com.jason.util.ip;

public class IPtest {
	public static void main(String args[]) {
		/*IPtest ipTest = new IPtest();
		String path = ipTest.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(1, path.indexOf("/WEB-INF/"));
		}
		path+="/qqwry";*/
		// 指定纯真数据库的文件名，所在文件夹
		//IPSeeker ip = new IPSeeker("QQWry.Dat",null);
		//IPSeeker ip = new IPSeeker();
		// 测试IP 58.20.43.13
		//System.out.println(path);
		String ipAddress = "222.178.134.193";
		Location l = IPSeeker.getInstance().getLocation(ipAddress);
		System.out.println(l.getCountry() + " : " + l.getProvince() + " : " + l.getCity() + " : " + l.getISP() + " : " + l.getAddress());
		/*System.out.println(IPSeeker.getInstance().getLocationByIp(ipAddress).getCountry());
		System.out.println(IPSeeker.getInstance().getLocationByIp(ipAddress).getArea());*/
		
	}
}
