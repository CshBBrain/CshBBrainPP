package com.jason.util;

import java.math.BigInteger;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 陈嗣洪
 * <li>创建日期：2010-2-1
 * <li>修改人： DES改进加密工具，可提供可逆加密和非可逆加密
 * <li>修改日期：
 */
public class DESUtils {
	private static  String keyString="cdstscdsts";
	private   static   final   String   Algorithm = "DES";
	SecretKey key;
	private Cipher p_Cipher; 
   /**
    * 
    * <li>方法名：getKey
    * <li>@param strKey
    * <li>返回类型：void
    * <li>说明：加密的key
    * <li>创建人：陈嗣洪
    * <li>创建日期：2010-2-1
    * <li>修改人： 
    * <li>修改日期：
    */
   public void getKey(String strKey) {
    try {
    	DESKeySpec dks = new DESKeySpec(strKey.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
		key = keyFactory.generateSecret( dks );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

 
  /**
   * @param byteS
   * @return
   */
  	private byte[] getEncCode(byte[] byteS) {
    byte[] byteFina = null;
    Cipher cipher;
    try {
      cipher = Cipher.getInstance(Algorithm+"/ECB/NoPadding"); 
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byteFina = cipher.doFinal(byteS);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      cipher = null;
    }
    return byteFina;
  }

  /**
   * @param byteD
   */
  	public byte[] getDesCode(byte[] byteD) {
    Cipher cipher;
    byte[] byteFina = null;
    try {
      cipher = Cipher.getInstance(Algorithm+"/ECB/NoPadding"); 
      cipher.init(Cipher.DECRYPT_MODE, key);
      byteFina = cipher.doFinal(byteD);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      cipher = null;
    }
    return byteFina;
  }

  public static void main(String args[]) {
	  DESUtils des=DESUtils.init();
	  System.out.println(des.CalcMAC("12345678".getBytes()));//不可逆的加密
	//String rowData=des.ClsEncCode("00000011");//加密
	String rowData=des.ClsEncCode("dfdd");//加密

	System.out.println("====012001======"+des.ClsEncCode("00012001"));
	System.out.println("====012002======"+des.ClsEncCode("00012002"));
	System.out.println("====012003======"+des.ClsEncCode("00012003"));
	System.out.println("====012======"+des.ClsEncCode("00000012"));
	System.out.println("====123======"+des.ClsEncCode("123"));
	System.out.println("====15======"+des.ClsDESCode("BA6366EA6B9145B4"));
	
	System.out.println("====nc======"+des.ClsEncCode("nc"));
    System.out.println(des.CalcMAC("1234".getBytes()));
    
    System.out.println(des.ClsDESCode("E3FB5E04105BEE9C"));
    //byte b[]=des.getEncCode("9510000111".getBytes());
}
  private byte[] fillEightByte(byte[] rawByte){
	  int rawByteLength=rawByte.length;
	  int byteamount=rawByteLength/8;
	  //byte amount
	  if(rawByteLength%8!=0){
		  byteamount=byteamount+1;
	  }
	  byte[] rawbytebyeightbyte=new byte[byteamount*8];
	  //
	  for(int i=0;i<rawbytebyeightbyte.length;i++){
		  if(i+1>rawByteLength){
			  rawbytebyeightbyte[i]=Integer.decode("0x00").byteValue();
		  }
		  else{
			  rawbytebyeightbyte[i]=rawByte[i];
		  }
	  }
	  return rawbytebyeightbyte;
  }
  public static DESUtils init(){
	  Security.addProvider(new com.sun.crypto.provider.SunJCE());
	  DESUtils des=new DESUtils();
	  des.getKey(des.getKeyString());
	  return des;
  } 
  
  public synchronized byte[] getCryptotext(String raw){
	 byte[] rawbyte=raw.getBytes();
	 byte[] eightbytegroup=this.fillEightByte(rawbyte);
	 int byteamount=eightbytegroup.length/8;
	 byte[] result=new byte[byteamount*8];
	 //
	  for(int i=0;i<byteamount;i++){
		  byte[] perbyte=new byte[8];
		  for(int j=0;j<8;j++){
			  perbyte[j]=eightbytegroup[i*8+j];
		  }
		 perbyte=this.getEncCode(perbyte);
		 for(int j=0;j<8;j++){
			 result[i*8+j]=perbyte[j];
		 }
	  }
	  return result;
  }
  
  public synchronized byte[] macEncry(String str){
	 byte[] raw=str.getBytes();
	 byte[] result=new byte[8];
	 byte[] eightgroup=this.fillEightByte(raw);
	 byte[] temp=new byte[8];
	 int amount=eightgroup.length/8;
	 //the first des 
	 for(int j=0;j<8;j++){
		 temp[j]=eightgroup[j];
	 }
	 result=this.getEncCode(temp);
	 for(int i=1;i<amount;i++){
		 byte[] next=new byte[8];
		 for(int j=0;j<8;j++){
			 next[j]=eightgroup[i*8+j];
		 }
		BigInteger resultBig=new BigInteger(result);
		BigInteger nextBig=new BigInteger(next);
		next=resultBig.xor(nextBig).toByteArray();
		if(next.length!=8)
			next=this.fillEightByte(next);
		result=this.getEncCode(next);
	 }
	 return result;
  }
  public synchronized String convertByteToHexString(byte[] raw){
	  String hexString="";
	  for(int i=0;i<raw.length;i++){
		  String hex=Integer.toHexString(raw[i]);
		  if(hex.length()<2){
			  hex="0"+hex;
		  }
			 hex=hex.substring(hex.length()-2);
		  hexString=hexString+hex;
	  }
	  return hexString;
  }
  public static String getKeyString() {
	return keyString;
  }
  public static void setKeyString(String keyString) {
	DESUtils.keyString = keyString;
  }
  
  public String CalcMAC(byte strInput[]) {
		int i, ii;
		int lenInput;
		byte newMAC[] = new byte[16];
		byte tempmac[] = new byte[8];
		byte strMAC[] = new byte[8];
		byte tempstr3[] = new byte[8];
		int j, k;
		byte temp1[] = new byte[1], temp2[] = new byte[1];

		lenInput = strInput.length;
		i = lenInput % 8;
		if(i!=0){
			k = lenInput + (8 - i);
		}
		else{
			k=lenInput;
		}
		byte strInputBack[] = new byte[k];

		
		for (j = 0; j < k; j++) {
			if (j < lenInput)
				strInputBack[j] = strInput[j];
			else
				strInputBack[j] = 0x00;
		}
		lenInput = k;
		
		for (i = 0; i < 8; i++) {
			strMAC[i] = '0';
		}
		for (i = 0; i < lenInput; i += 8) {

			for (j = i; j < i + 8; j++) {
				tempmac[j % 8] = strInputBack[j];
			}

			for (int m = 0; m < 16; m++) {
				newMAC[m] = 0;
			}

			for (ii = 0; ii < 8; ii++) {
				temp1[0] = strMAC[ii];
				temp2[0] = tempmac[ii];
				BigInteger t1 = new BigInteger(temp1);
				BigInteger t2 = new BigInteger(temp2);
				BigInteger t3 = t1.xor(t2);
				tempstr3[ii] = t3.byteValue();
			}
			tempstr3 = this.getEncCode(tempstr3);
			System.arraycopy(tempstr3, 0, strMAC, 0, 8);
		}

		return this.convertByteToHexString(tempstr3);
	}
	
  public static String ClsDESCode(String hex){
	  DESUtils des=DESUtils.init();
	  String out="";
		try{
			byte[] dec = des.getDesCode(des.hex2byte(hex)); //
			out=new String(dec);
			int i = out.indexOf(0x00);
			if(i != -1){
				out=out.substring(0,i);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			out=null;
		}
		return out;
	}
	public static String ClsEncCode(String input){
		DESUtils des=DESUtils.init();
		try{
		byte[] enc = des.getEncCode(des.fillEightByte(input.getBytes())); 
		String hexenc = des.byte2hex(enc); //���ʮ����Ƽ����ļ� 
		return hexenc;
		}catch(Exception e){
			return null;
		}
	}
	
	public String byte2hex(byte[] b) { 
		String hs = ""; 
		String stmp = ""; 
		for (int i = 0; i < b.length; i++) { 
		stmp = Integer.toHexString(b[i] & 0xFF); 
		if (stmp.length() == 1) { 
		hs += "0" + stmp; 
		} 
		else { 
		hs += stmp; 
		} 
		} 
		return hs.toUpperCase(); 
		} 
	
	public byte[] hex2byte(String hex) throws IllegalArgumentException { 
		if (hex.length() % 2 != 0) { 
		throw new IllegalArgumentException(); 
		} 
		char[] arr = hex.toCharArray(); 
		byte[] b = new byte[hex.length() / 2]; 
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) { 
		String swap = "" + arr[i++] + arr[i]; 
		int byteint = Integer.parseInt(swap, 16) & 0xFF; 
		b[j] = new Integer(byteint).byteValue(); 
		} 
		return b; 
	} 
}
