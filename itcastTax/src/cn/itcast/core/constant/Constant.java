package cn.itcast.core.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {

	//ϵͳ��session�еı�ʶ��
	public static String USER="SYS_USER";
	/*------------------------ϵͳȨ�޼���----------------------------*/
	public static String PRIVILEGE_XZGL = "xzgl";
	public static String PRIVILEGE_HQFW = "hqfw";
	public static String PRIVILEGE_ZXXX = "zxxx";
	public static String PRIVILEGE_NSFW = "nsfw";
	public static String PRIVILEGE_SPACE = "space";
	
    public static Map<String,String> PRIVATE_MAP;
	static {
		PRIVATE_MAP = new HashMap<String, String>();
		PRIVATE_MAP.put(PRIVILEGE_XZGL, "��������");
		PRIVATE_MAP.put(PRIVILEGE_HQFW, "���ڷ���");
		PRIVATE_MAP.put(PRIVILEGE_ZXXX, "����ѧϰ");
		PRIVATE_MAP.put(PRIVILEGE_NSFW, "��˰����");
		PRIVATE_MAP.put(PRIVILEGE_SPACE, "�ҵĿռ�");
	}
}
