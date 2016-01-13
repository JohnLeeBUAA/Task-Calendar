package pac;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

/*
 * 加密机制
 */
class EncryptionDecryption {
	private static String strDefaultKey = "morbidinfant";// 默认密钥

	// 将byte数组转换为16进制字符串
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

	// 将16进制字符串转化为byte数组
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

	private Cipher encryptCipher = null;// 加密工具

	private Cipher decryptCipher = null;// 解密工具

	// 使用默认密钥的构造方法
	public EncryptionDecryption() throws Exception {
		this(strDefaultKey);
	}

	// 使用指定密钥的构造方法
	public EncryptionDecryption(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	// 解密字节数组，返回解密后的字节数组
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	// 解密字符串，返回解密后的字符串
	public String decrypt(String strIn) throws Exception {
		try {
			return new String(decrypt(hexStr2ByteArr(strIn)));
		} catch (Exception e) {
			return "";
		}
	}

	// 加密字符数组，返回加密后的字符数组
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	// 加密字符串，返回加密后的字符串
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	// 从指定的字符数组生成密钥，返回密钥
	private Key getKey(byte[] arrBTmp) throws Exception {

		byte[] arrB = new byte[8];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}// 不足8位补零，超8位取前8位
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	/*
	 * public static void main(String[] args){ try{
	 * 
	 * String test = "测试"; EncryptionDecryption des = new
	 * EncryptionDecryption("morbidinfant"); System.out.println("加密前：" + test);
	 * System.out.println("加密后：" + des.encrypt(test)); EncryptionDecryption desx
	 * = new EncryptionDecryption("abcde"); System.out.println("解密后：" +
	 * desx.decrypt(des.encrypt(test))); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

}