package cn.itcast.core.permission;

import cn.itcast.nsfw.user.entity.User;
/**
 * 
 * @author ��������
 * ���user�û��Ƿ��ĳ��ģ�����
 *user �û�
 *code ��Ӧ��Ȩ��ֵ
 */
public interface PermissionCheck {
	public abstract boolean isAccessble(User user,String code);

}
