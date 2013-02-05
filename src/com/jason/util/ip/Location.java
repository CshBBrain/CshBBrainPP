/**
 * <li>文件名：Location.java
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-6-21
 * <li>修改人： 
 * <li>修改日期：
 */
package com.jason.util.ip;

import com.jason.util.MyStringUtil;

/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2011-6-21
 * <li>修改人： 
 * <li>修改日期：
 */
public class Location {
	private String country;//国家
    private String province;//省份
    private String city;//城市
    private String address;//地址
    private String ISP;//运营商
    private String ip;//ip 地址
    
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		if(MyStringUtil.isBlank(this.city)){
			return "其他";
		}
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		if(MyStringUtil.isBlank(this.country)){
			return "";
		}
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getISP() {
		if(MyStringUtil.isBlank(this.ISP)){
			return "";
		}
		return ISP;
	}
	public void setISP(String isp) {
		ISP = isp;
	}
	public String getProvince() {
		if(MyStringUtil.isBlank(this.province)){
			return "";
		}
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
    
    
}
