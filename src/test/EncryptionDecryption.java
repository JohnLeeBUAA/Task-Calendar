package test;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

public class EncryptionDecryption {

	private static String strDefaultKey = "morbidinfant";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
	    int iLen = arrB.length;
	    StringBuffer sb = new StringBuffer(iLen * 2);
	        for (int i = 0; i < iLen; i++) {
	            int intTmp = arrB[i];
	           
	            while (intTmp < 0) {
	                intTmp = intTmp + 256;
	            }
	           
	            if (intTmp < 16) {
	                sb.append("0");
	            }
	            sb.append(Integer.toString(intTmp, 16));
	        }
	        return sb.toString();
	    }
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
	        byte[] arrB = strIn.getBytes();
	        int iLen = arrB.length;
	 
	        byte[] arrOut = new byte[iLen / 2];
	        for (int i = 0; i < iLen; i = i + 2) {
	            String strTmp = new String(arrB, i, 2);
	            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
	        }
	        return arrOut;
	}
	 

	public EncryptionDecryption() throws Exception {
	        this(strDefaultKey);
	    }
	 
	    public EncryptionDecryption(String strKey) throws Exception {
	        Security.addProvider(new com.sun.crypto.provider.SunJCE());
	        Key key = getKey(strKey.getBytes());
	 
	        encryptCipher = Cipher.getInstance("DES");
	        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
	 
	        decryptCipher = Cipher.getInstance("DES");
	        decryptCipher.init(Cipher.DECRYPT_MODE, key);
	    }
	 
	public byte[] encrypt(byte[] arrB) throws Exception {
	        return encryptCipher.doFinal(arrB);
	    }

	public String encrypt(String strIn) throws Exception {
	        return byteArr2HexStr(encrypt(strIn.getBytes()));
	    }
	public byte[] decrypt(byte[] arrB) throws Exception {
	        return decryptCipher.doFinal(arrB);
	    }
	public String decrypt(String strIn) throws Exception {
	    try {
	            return new String(decrypt(hexStr2ByteArr(strIn)));
	    } catch (Exception e) {
	            return "";
	        }
	    }

	private Key getKey(byte[] arrBTmp) throws Exception {
	        
	 byte[] arrB = new byte[8];
	    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
	        arrB[i] = arrBTmp[i];
	        }
	 Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
	 
	        return key;
	    }
	public static void main(String[] args){
	    try{
	    			              
	    		String test = "测试";
	    		EncryptionDecryption des = new EncryptionDecryption("morbidinfant");
	            System.out.println("加密前：" + test);
	            System.out.println("加密后：" + des.encrypt(test));
	            System.out.println("解密后：" + des.decrypt(des.encrypt(test)));
	            EncryptionDecryption desx = new EncryptionDecryption("abcde");
	            System.out.println("解密后：" + desx.decrypt(des.encrypt(test)));
	    		} catch (Exception e) {
	    		                e.printStackTrace();
	    			            }
	    }
	 
	}
