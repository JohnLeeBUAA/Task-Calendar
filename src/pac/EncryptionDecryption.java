package pac;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

/*
 * ���ܻ���
 */
class EncryptionDecryption {
	private static String strDefaultKey = "morbidinfant";// Ĭ����Կ

	// ��byte����ת��Ϊ16�����ַ���
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

	// ��16�����ַ���ת��Ϊbyte����
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

	private Cipher encryptCipher = null;// ���ܹ���

	private Cipher decryptCipher = null;// ���ܹ���

	// ʹ��Ĭ����Կ�Ĺ��췽��
	public EncryptionDecryption() throws Exception {
		this(strDefaultKey);
	}

	// ʹ��ָ����Կ�Ĺ��췽��
	public EncryptionDecryption(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	// �����ֽ����飬���ؽ��ܺ���ֽ�����
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	// �����ַ��������ؽ��ܺ���ַ���
	public String decrypt(String strIn) throws Exception {
		try {
			return new String(decrypt(hexStr2ByteArr(strIn)));
		} catch (Exception e) {
			return "";
		}
	}

	// �����ַ����飬���ؼ��ܺ���ַ�����
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	// �����ַ��������ؼ��ܺ���ַ���
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	// ��ָ�����ַ�����������Կ��������Կ
	private Key getKey(byte[] arrBTmp) throws Exception {

		byte[] arrB = new byte[8];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}// ����8λ���㣬��8λȡǰ8λ
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	/*
	 * public static void main(String[] args){ try{
	 * 
	 * String test = "����"; EncryptionDecryption des = new
	 * EncryptionDecryption("morbidinfant"); System.out.println("����ǰ��" + test);
	 * System.out.println("���ܺ�" + des.encrypt(test)); EncryptionDecryption desx
	 * = new EncryptionDecryption("abcde"); System.out.println("���ܺ�" +
	 * desx.decrypt(des.encrypt(test))); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

}